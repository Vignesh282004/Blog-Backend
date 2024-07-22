package com.blogapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapi.Daos.JwtRequest;
import com.blogapi.Daos.JwtResponse;
import com.blogapi.Daos.UserDao;
import com.blogapi.exceptions.AppException;
import com.blogapi.security.JwtConstructor;
import com.blogapi.services.Impl.UserServiceImpl;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private JwtConstructor jwtConstructor;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> createToken(@RequestBody JwtRequest request)  {
		this.authenticate(request.getUsername(),request.getPassword());
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.jwtConstructor.genToken(userDetails);
		JwtResponse jwtResponse = new JwtResponse();
		jwtResponse.setToken(token);
		
		return new ResponseEntity<JwtResponse>(jwtResponse,HttpStatus.OK);
	}
	
	public void authenticate(String username,String password) 
	{
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		try {
			this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		} catch (Exception e) {
			throw new AppException("invalid username or password....!");
		}
	}
	

	@PostMapping("/register")
	public ResponseEntity<UserDao> registerNewUser(@RequestBody UserDao userDao){
		UserDao newUser = this.userServiceImpl.createAdmin(userDao);
		return new ResponseEntity<UserDao>(newUser,HttpStatus.CREATED);
	} 
	
	
	
	
	
	
}
