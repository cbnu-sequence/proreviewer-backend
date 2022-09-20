package com.sequence.proreviewer.user.presentation;

import com.sequence.proreviewer.user.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
}
