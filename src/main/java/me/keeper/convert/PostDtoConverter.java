package me.keeper.convert;

import me.keeper.dto.PostShortDto;
import me.keeper.model.Post;
import org.springframework.stereotype.Component;

@Component
public class PostDtoConverter{

	public <T> Post convert(T postDto) {
		Post post = new Post();
		PostShortDto dto = (PostShortDto) postDto;
		post.setTitle(dto.getTitle());
		post.setDescription(dto.getDescription());
		post.setContent(dto.getContent());
		return post;
	}
}
