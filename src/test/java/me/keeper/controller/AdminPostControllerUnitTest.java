package me.keeper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.keeper.dto.PostFullDto;
import me.keeper.dto.PostShortDto;
import me.keeper.service.PostService;
import me.keeper.utils.PostCreatingUtils;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = PostController.class)
public class AdminPostControllerUnitTest {

	@MockBean
	private PostService postService;

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private PostController postController;

	@BeforeEach
	public void init(){
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void whenAddNewPostReturnPost() throws Exception {
		// given
		ObjectMapper mapper = new ObjectMapper();
		PostShortDto dto = PostCreatingUtils.createPostShortDto();
		PostFullDto post = PostCreatingUtils.createPostFullDto();

		// when
		when(postService.addPost(any(PostShortDto.class))).thenReturn(post);

		// actual & then
		mockMvc.perform(MockMvcRequestBuilders.post("/blog/posts/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(dto)))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.title", is("NEWS")))
				.andExpect(jsonPath("$.description", is("Fantastic news from Finland!")))
				.andExpect(jsonPath("$.content", is("Hard Rock Hallelujah!")))
				.andDo(print());
	}
}
