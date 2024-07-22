package com.blogapi.services.Impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogapi.Daos.UserDao;
import com.blogapi.configs.AppConstants;
import com.blogapi.entity.Role;
import com.blogapi.entity.User;
import com.blogapi.exceptions.ResourceException;
import com.blogapi.repository.RoleRepo;
import com.blogapi.repository.UserRepo;
import com.blogapi.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public UserDao createAdmin(UserDao userDao) {
		User user = this.modelMapper.map(userDao, User.class);
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		user.setRoles(Set.of(this.roleRepo.findById(AppConstants.ADMIN_USER).get()));
		User createUser = this.userRepo.save(user);
		System.out.println(user.getPassword());
		return this.modelMapper.map(createUser,UserDao.class);
	}

	@Override
	public UserDao createUser(UserDao user) {
		User user2 = this.dtoToUser(user);
		user2.setPassword(this.passwordEncoder.encode(user.getPassword()));
		user2.setRoles(Set.of(this.roleRepo.findById(AppConstants.NORMAL_USER).get()));
		User savedUser = this.userRepo.save(user2);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDao updateUser(UserDao user, int id) {
		User user2 = this.userRepo.findById(id).orElseThrow(() -> new ResourceException("User","id", id));
		user2.setName(user.getName());
		user2.setPassword(user.getPassword());
		user2.setEmail(user.getEmail());
		user2.setAbout(user.getAbout());
		
		User updatedUser = this.userRepo.save(user2);
		return this.userToDto(updatedUser);
	}

	@Override
	public UserDao getUserById(int id) {
		User user = this.userRepo.findById(id).orElseThrow(() -> new ResourceException("User", "id", id));
		return this.userToDto(user);
	}

	@Override
	public List<UserDao> getAllUsers() {
		List<User> users = this.userRepo.findAll();	
		List<UserDao> userDtos = users.stream().map(user-> this.userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(int id) {
		User user = this.userRepo.findById(id).orElseThrow(() -> new ResourceException("User","id",id));
		user.getRoles().clear();
		this.userRepo.delete(user);
	//	this.userRepo.save(user);
	}
	
	
	
	private User dtoToUser(UserDao userDao)
	{
	    User user = this.modelMapper.map(userDao, User.class);	    
	    return user;
	}
	
	
	private UserDao userToDto(User user)
	{
		UserDao userDao= this.modelMapper.map(user, UserDao.class);
		return userDao;
	}
	
}
