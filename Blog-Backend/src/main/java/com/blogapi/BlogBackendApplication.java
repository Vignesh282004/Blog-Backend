package com.blogapi;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blogapi.configs.AppConstants;
import com.blogapi.entity.Role;
import com.blogapi.repository.RoleRepo;
	

@SpringBootApplication
public class BlogBackendApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(BlogBackendApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
			System.out.println(this.passwordEncoder.encode("xyz"));
			List<Role> roles = List.of(
					new Role(AppConstants.NORMAL_USER,"ROLE_NORMAL")
					,new Role(AppConstants.ADMIN_USER,"ROLE_ADMIN"));
			this.roleRepo.saveAll(roles);
	}
}
