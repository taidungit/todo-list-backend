package com.example.todo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.domain.ApiResponse;
import com.example.todo.domain.User;
import com.example.todo.service.UserService;

@RestController
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/users")
	public ResponseEntity<ApiResponse<User>> createUser(@RequestBody User user) {
		User created = userService.createUser(user);
		var result = new ApiResponse<>(HttpStatus.CREATED, "createUser", created, null);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	@GetMapping("/users")
	public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
		var result=new ApiResponse<>(HttpStatus.OK, "getAllUsers", userService.getAllUsers(), null);
		return ResponseEntity.ok().body(result);
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Long id) {
		return userService.getUserById(id).map(user->{
			var response=new ApiResponse<>(HttpStatus.OK, "getUserById", user, null);
			return ResponseEntity.ok(response);
		}).orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Long id, @RequestBody User user) {
		User updated = userService.updateUser(id, user);
		var result= new ApiResponse<>(HttpStatus.OK, "updateUser", updated, null);
		return ResponseEntity.ok(result);
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<ApiResponse<User>> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);	
		ApiResponse result = new ApiResponse<>(HttpStatus.NO_CONTENT, "deleteUser", null, null);
		return ResponseEntity.ok(result);
	}
	
}