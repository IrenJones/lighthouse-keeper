package me.keeper.service;

import me.keeper.convert.PostConverter;
import me.keeper.convert.PostDtoConverter;
import me.keeper.dto.CommentDto;
import me.keeper.dto.PostFullDto;
import me.keeper.dto.PostShortDto;
import me.keeper.model.Post;
import me.keeper.repository.PostRepository;
import me.keeper.service.impl.PostServiceImpl;
import me.keeper.utils.PostCreatingUtils;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.AllOf.allOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PostServiceUnitTest {

	@Mock
	private PostRepository postRepository;

	@Mock
	private PostDtoConverter dtoConverter;

	@Mock
	private PostConverter converter;

	@InjectMocks
	private PostService postService = new PostServiceImpl();

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void successfulCreationWhenParametersValid(){
		// given
		Post post = PostCreatingUtils.createPost(1L, "NEWS");
		PostFullDto dto = PostCreatingUtils.createPostFullDto();
		PostShortDto inputDto = PostCreatingUtils.createPostShortDto();

		// when
		when(dtoConverter.convert(any(PostShortDto.class))).thenReturn(post);
		when(postRepository.save(any(Post.class))).thenReturn(post);
		when(converter.convert(any(Post.class))).thenReturn(dto);

		// action
		PostFullDto result = postService.addPost(inputDto);

		// then
		assertThat(result, allOf(
				hasProperty("title", equalTo("NEWS")),
				hasProperty("description", equalTo("Fantastic news from Finland!")),
				hasProperty("content", equalTo("Hard Rock Hallelujah!")),
				hasProperty("comments", emptyCollectionOf(CommentDto.class))
		));
	}
}
