package me.keeper.controller;

import me.keeper.dto.PostFullDto;
import me.keeper.dto.PostShortDto;
import me.keeper.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping(value = "/admin/blog/post")
public class AdminPostController {

	@Autowired
	private PostService postService;

	@PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PostFullDto> addPost(@Valid @RequestBody PostShortDto dto){
		return new ResponseEntity<>(postService.addPost(dto), HttpStatus.CREATED);
	}

	@PutMapping(value = "/edit/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PostFullDto> editPost(@Valid @RequestBody PostShortDto dto,
												@PathVariable(value = "id") Long id){
		return new ResponseEntity<>(postService.updatePost(id, dto), HttpStatus.OK);
	}

	@DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PostFullDto> deletePost(@PathVariable(value = "id") Long id){
		postService.deletePost(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
