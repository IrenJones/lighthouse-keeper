package me.keeper.convert;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.keeper.dto.CommentDto;
import me.keeper.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter implements Converter<Comment, CommentDto> {

	@Autowired
	private ObjectMapper mapper;

	@Override
	public CommentDto convert(Comment comment) {
		return mapper.convertValue(comment, CommentDto.class);
	}
}
