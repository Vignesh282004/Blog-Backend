package com.blogapi.services;

import com.blogapi.Daos.UserDao;
import java.util.List;
public interface UserService {

	
	UserDao createAdmin(UserDao userDao);
	
	UserDao createUser(UserDao user);
	
	UserDao updateUser(UserDao user,int id);
	
	UserDao getUserById(int id);
	
	List<UserDao> getAllUsers();
	
	void deleteUser(int id);
}
