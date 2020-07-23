package me.keeper.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostFullDto extends PostShortDto {

	private List<CommentDto> comments;
}
