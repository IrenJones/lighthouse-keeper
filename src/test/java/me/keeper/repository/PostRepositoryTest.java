package me.keeper.repository;

import me.keeper.dto.CommentDto;
import me.keeper.model.Post;
import me.keeper.utils.PostCreatingUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private PostRepository postRepository;

	@Test
	public void addPostReturnSavedPost() {
		// given
		Post post = PostCreatingUtils.createPost(null, "wohoo");
		entityManager.persist(post);

		// when
		Optional<Post> result = postRepository.findById(1L);

		// then
		assertTrue(result.isPresent());
		assertThat(result.get(), allOf(
				hasProperty("id", equalTo(1L)),
				hasProperty("title", equalTo("wohoo")),
				hasProperty("description", equalTo("Fantastic news from Finland!")),
				hasProperty("content", equalTo("Hard Rock Hallelujah!")),
				hasProperty("comments", emptyCollectionOf(CommentDto.class))
		));
	}
}
