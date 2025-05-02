package com.example.todo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.domain.Todo;

@RestController
public class HelloController {
    @GetMapping("/")
	public ResponseEntity<String> index() {
		// return "Hello World from Spring Boot with dungmount wwith update devtools";
		return ResponseEntity.ok().body("Hello World from Spring Boot with dungmount");
	}
	@GetMapping("/dungmount")
	public ResponseEntity<Todo> dungmount() {
		Todo test=new Todo("Tai Dung",false);
		// return test;
		return ResponseEntity.ok().body(test);
	}
}
