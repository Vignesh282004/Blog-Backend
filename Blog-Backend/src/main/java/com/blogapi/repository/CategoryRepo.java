package com.blogapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapi.entity.Category;

public interface CategoryRepo  extends JpaRepository<Category,Integer>{

}
