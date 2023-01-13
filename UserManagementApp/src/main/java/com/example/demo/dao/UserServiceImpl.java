package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
@Service
public class UserServiceImpl implements IUserService{

	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<User> getAllUsers() {
		List<User> userList=userRepository.findAll();
		if(userList!=null && userList.size()>0)
		{
			System.out.println(userList);
			return userList;
		}
		else
		return null;
	}

	@Override
	public User addUser(User user) {
	
		return userRepository.saveAndFlush(user);
	}

	@Override
	public boolean validateUser(String Username, String password) {
		User user=userRepository.validateUser(Username, password);
		
		if(user!=null)
		{
			return true;
		}
		else
			return false;
	}

}
