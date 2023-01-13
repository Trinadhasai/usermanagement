package com.example.demo.dao;

import java.util.List;

import com.example.demo.model.User;

public interface IUserService {
	
	public List<User> getAllUsers();
	
	public User addUser(User user);
	
	public boolean validateUser(String Username,String password);
	
	
	

}
