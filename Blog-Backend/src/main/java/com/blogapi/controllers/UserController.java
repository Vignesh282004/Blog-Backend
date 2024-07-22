package com.blogapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapi.Daos.ApiResponse;
import com.blogapi.Daos.UserDao;
import com.blogapi.services.Impl.UserServiceImpl;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@PostMapping("/")
	public ResponseEntity<UserDao> certeUser(@Valid @RequestBody UserDao userDao)  
	{
		UserDao userDao2 = this.userServiceImpl.createUser(userDao);
		return new ResponseEntity<UserDao>(userDao2,HttpStatus.OK);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDao> updateUser(@Valid @RequestBody UserDao userDao,@PathVariable("userId") int userId)  
	{
		return ResponseEntity.ok(this.userServiceImpl.updateUser(userDao, userId));
	} 
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") int uid) 
	{
		this.userServiceImpl.deleteUser(uid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted successfully",true),HttpStatus.OK);
	}
	
	
	@GetMapping("/")
	public ResponseEntity<List<UserDao>> getUsers()
	{
		return ResponseEntity.ok(this.userServiceImpl.getAllUsers());
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDao> getUser(@PathVariable("userId") int uid)
	{
		return ResponseEntity.ok(this.userServiceImpl.getUserById(uid));
	}
	
	
	
	
	
	
}
