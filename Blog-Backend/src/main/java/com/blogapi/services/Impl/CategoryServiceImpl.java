package com.blogapi.services.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapi.Daos.CategoryDao;
import com.blogapi.entity.Category;
import com.blogapi.exceptions.ResourceException;
import com.blogapi.repository.CategoryRepo;
import com.blogapi.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDao createCategory(CategoryDao categoryDao) {
		Category cat = this.modelMapper.map(categoryDao, Category.class);
		Category savedCat = this.categoryRepo.save(cat); 
		
		return this.modelMapper.map(savedCat, CategoryDao.class);
	}

	@Override
	public void deleteCategory(int categoryId) {
		// TODO Auto-generated method stub
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceException("Category","Category Id", categoryId));
		this.categoryRepo.delete(category);
		
	}

	@Override
	public CategoryDao updateCategory(int categoryId, CategoryDao categoryDao) {
		Category cat = this.categoryRepo.findById(categoryId).
				orElseThrow(()-> new ResourceException("Category","Category Id", categoryId));
		
		categoryDao.setCategoryId(categoryId);
		cat=this.modelMapper.map(categoryDao, Category.class);
		Category updatedCategory = this.categoryRepo.save(cat);
		
	    return this.modelMapper.map(updatedCategory,CategoryDao.class);
	}

	@Override
	public CategoryDao getSingleCategory(int categoryId) {
		Category cat = this.categoryRepo.findById(categoryId).
				orElseThrow(()-> new ResourceException("Category","Id", categoryId));
	
		
		return this.modelMapper.map(cat, CategoryDao.class);
	}

	@Override
	public List<CategoryDao> getCategory() {
		List<Category> allCat = this.categoryRepo.findAll();
		
		List<CategoryDao> allCatDto = allCat.stream().map(cat-> this.modelMapper.map(cat, CategoryDao.class)).collect(Collectors.toList());
		return allCatDto;
	}
	
	
}
