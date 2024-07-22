package com.blogapi.services;

import java.util.List;

import com.blogapi.Daos.CategoryDao;

public interface CategoryService {

	CategoryDao createCategory(CategoryDao categoryDao);
	
	void deleteCategory(int categoryId);
	
	CategoryDao updateCategory(int categoryId,CategoryDao categoryDao);
	
	CategoryDao getSingleCategory(int categoryId);
	
	List<CategoryDao> getCategory();
}
