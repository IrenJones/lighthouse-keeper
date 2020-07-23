package me.keeper.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PostShortDto {

	@NotNull
	private String title;

	@NotNull
	private String description;

	@NotNull
	private String content;
}
