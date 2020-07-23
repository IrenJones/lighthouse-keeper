package me.keeper.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CommentDto {

	@NotNull
	private String nickName;

	@NotNull
	private String content;
}
