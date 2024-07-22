package com.blogapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapi.Daos.ApiResponse;
import com.blogapi.Daos.CategoryDao;
import com.blogapi.services.Impl.CategoryServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
//@SecurityRequirement(name = "bearer-jwt")
public class CategoryController {

	@Autowired
	private CategoryServiceImpl categoryServiceImpl;
	
	@PostMapping("/")
	public ResponseEntity<CategoryDao> createCategory(@Valid @RequestBody CategoryDao categoryDao)
	{
		CategoryDao createCategory = this.categoryServiceImpl.createCategory(categoryDao);
		
		return new ResponseEntity<CategoryDao>(createCategory,HttpStatus.CREATED);
		
	}
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDao> getCategory(@PathVariable int categoryId)
	{
		CategoryDao cat = this.categoryServiceImpl.getSingleCategory(categoryId);
		
		return ResponseEntity.ok(cat);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDao>> getCategory()
	{
		return ResponseEntity.ok(this.categoryServiceImpl.getCategory());
	}
	
	
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDao> updateCategory(@Valid @RequestBody CategoryDao categoryDto,@PathVariable int categoryId)
	{
		CategoryDao cat= this.categoryServiceImpl.updateCategory(categoryId, categoryDto);
		
		return ResponseEntity.ok(cat);
	}
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable int categoryId)
	{
		this.categoryServiceImpl.deleteCategory(categoryId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully",true),HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
