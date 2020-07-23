package me.keeper.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/blog")
public class BlogController {

	private String greeting = "Hello";

	@GetMapping(value = "/main")
	public ResponseEntity<String> hello(){
		return new ResponseEntity<>(greeting, HttpStatus.OK);
	}
}
