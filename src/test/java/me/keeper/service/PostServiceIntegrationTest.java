package me.keeper.service;

import me.keeper.dto.CommentDto;
import me.keeper.dto.PostFullDto;
import me.keeper.dto.PostShortDto;
import me.keeper.utils.PostCreatingUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.AllOf.allOf;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
public class PostServiceIntegrationTest {

	@Autowired
	private PostService postService;

	@Test
	public void successfulCreationWhenParametersValid(){
		// given & when
		PostShortDto dto = PostCreatingUtils.createPostShortDto();

		// action
		PostFullDto result = postService.addPost(dto);

		// then
		assertThat(result, allOf(
				hasProperty("title", equalTo("NEWS")),
				hasProperty("description", equalTo("Fantastic news from Finland!")),
				hasProperty("content", equalTo("Hard Rock Hallelujah!")),
				hasProperty("comments", emptyCollectionOf(CommentDto.class))
		));
	}
}
