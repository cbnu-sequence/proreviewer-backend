package com.sequence.proreviewer.auth.application;

import com.sequence.proreviewer.auth.application.oauth2.OAuth2;
import com.sequence.proreviewer.auth.application.util.JwtProvider;
import com.sequence.proreviewer.auth.domain.Auth;
import com.sequence.proreviewer.auth.domain.Provider;
import com.sequence.proreviewer.auth.domain.UserInfo;
import com.sequence.proreviewer.auth.domain.repository.AuthRepository;
import com.sequence.proreviewer.auth.presentation.dto.request.LoginRequestDto;
import com.sequence.proreviewer.auth.presentation.dto.response.AuthTokens;
import com.sequence.proreviewer.user.domain.User;
import com.sequence.proreviewer.user.domain.repository.UserRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    private final ApplicationContext context;

    public AuthTokens login(Provider provider, LoginRequestDto dto) {
        OAuth2 oAuth2 = getOAuth2ConcreteClassBean(provider);

        UserInfo userInfo = oAuth2.getUserInfo(dto.getCode());

        User user = getUser(userInfo);
        if (!isAuthExist(userInfo)) {
            createAuth(user, userInfo, provider);
        }

        return AuthTokens.builder()
            .accessToken(createAccessToken(user.getId()))
            .refreshToken(createRefreshToken())
            .build();
    }

    private String createAccessToken(Long userId) {
        return jwtProvider.accessToken(String.valueOf(userId));
    }

    private String createRefreshToken() {
        return UUID.randomUUID().toString();
    }

    private void createAuth(User user, UserInfo userInfo, Provider provider) {
        Auth auth = Auth.builder()
            .user(user)
            .providerKey(userInfo.getProviderKey())
            .provider(provider)
            .build();
        authRepository.saveAuth(auth);
    }

    private User getUser(UserInfo userInfo) {
        Optional<User> optionalUser = userRepository.findByEmail(userInfo.getEmail());
        return optionalUser.orElseGet(() -> createUser(userInfo));
    }

    private User createUser(UserInfo userInfo) {
        User user = User.builder()
            .email(userInfo.getEmail())
            .build();
        return userRepository.save(user);
    }

    private boolean isAuthExist(UserInfo userInfo) {
        Optional<Auth> optionalAuth = authRepository.findByProviderKey(userInfo.getId());
        return optionalAuth.isPresent();
    }

    private OAuth2 getOAuth2ConcreteClassBean(Provider provider) {
        return context.getBean(provider.getOAuth2ConcreteClass());
    }
}
