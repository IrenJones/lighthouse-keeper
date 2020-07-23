package me.keeper.convert;

import me.keeper.dto.CommentDto;
import me.keeper.dto.PostFullDto;
import me.keeper.model.Comment;
import me.keeper.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class PostConverter implements Converter<Post, PostFullDto> {

	@Autowired
	private CommentConverter commentConverter;

	@Override
	public PostFullDto convert(Post post) {
		PostFullDto dto = new PostFullDto();
		dto.setContent(post.getContent());
		dto.setDescription(post.getDescription());
		dto.setTitle(post.getTitle());

		List<CommentDto> list = new LinkedList<>();
		for(Comment comment: post.getComments()){
			list.add(commentConverter.convert(comment));
		}
		dto.setComments(list);
		return dto;
	}
}
