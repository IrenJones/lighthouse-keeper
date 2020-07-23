package me.keeper.service;

import me.keeper.dto.PostFullDto;
import me.keeper.dto.PostShortDto;

import java.util.List;

public interface PostService {

	PostFullDto addPost(PostShortDto dto);

	List<PostFullDto> getAllPosts();

	PostFullDto updatePost(Long id, PostShortDto dto);

	void deletePost(Long id);
}
