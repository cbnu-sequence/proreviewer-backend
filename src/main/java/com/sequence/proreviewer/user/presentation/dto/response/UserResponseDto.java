package com.sequence.proreviewer.user.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserResponseDto {

	private Long id;
	private String name;
	private String email;
	private String description;
}
