package me.keeper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.keeper.dto.CommentDto;
import me.keeper.dto.PostFullDto;
import me.keeper.dto.PostShortDto;
import me.keeper.exception.RestExceptionHandler;
import me.keeper.utils.PostCreatingUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.AllOf.allOf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostControllerIntegrationTest {

	@Autowired
	private PostController postController;

	@Autowired
	FilterChainProxy springSecurityFilterChain;

	MockMvc mockMvc;

	@BeforeEach
	public void setup() throws Exception {
		this.mockMvc = standaloneSetup(postController)
				.setControllerAdvice(new RestExceptionHandler())
				.apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain))
				.build();
	}

	@Test
	public void whenAddNewPostReturnPost() throws Exception {
		// given
		PostShortDto dto = PostCreatingUtils.createPostShortDto();
		ObjectMapper mapper = new ObjectMapper();

		// when
		mockMvc.perform(MockMvcRequestBuilders.post("/blog/posts/add")
				.with(user("admin").password("pass").roles("ADMIN"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(dto)))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.title", is("NEWS")))
				.andExpect(jsonPath("$.description", is("Fantastic news from Finland!")))
				.andExpect(jsonPath("$.content", is("Hard Rock Hallelujah!")))
				.andDo(print());
	}

	@Sql("classpath:dataset/post-repository.sql")
	@Test
	@Transactional
	public void whenUpdatePostReturnUpdatedPost() throws Exception {
		// given
		PostShortDto dto = PostCreatingUtils.createPostShortDto("news");
		ObjectMapper mapper = new ObjectMapper();

		// when
		mockMvc.perform(MockMvcRequestBuilders.put("/blog/posts/edit/1")
				.with(user("admin").password("pass").roles("ADMIN"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(dto)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.title", is("news")))
				.andExpect(jsonPath("$.description", is("Fantastic news from Finland!")))
				.andExpect(jsonPath("$.content", is("Hard Rock Hallelujah!")))
				.andDo(print());
	}

	@Sql("classpath:dataset/post-repository.sql")
	@Test
	public void whenUpdatePostReturnError() throws Exception {
		// given
		PostShortDto dto = PostCreatingUtils.createPostShortDto("NEWS");
		ObjectMapper mapper = new ObjectMapper();

		// when
		mockMvc.perform(MockMvcRequestBuilders.put("/blog/posts/edit/2")
				.with(user("admin").password("pass").roles("ADMIN"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(dto)))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message", is("Entity not found. Post id 2")))
				.andDo(print());
	}
}
