package com.blogapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

@RestController
@RequestMapping("/blog")
public class MainController {

	@GetMapping("/check")
	public String check() {
		return "ok";
	}
	
	@GetMapping("/go")
	public String go() {
		return "go";
	}
	
}
