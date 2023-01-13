package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@DataJpaTest
@AutoConfigureMockMvc
public class UserRepositoryTest {
	
	@Autowired
	UserRepository userRepo;
	
	private User user =new User();
	
	
	@BeforeEach
	public void init()
	{
		user.setId(111);
		user.setUsername("john");
		user.setPassword("john");		
	}
	
	
	@Test
	public void addUserSuccess() throws Exception
	{
		User u1=null;
		
		userRepo.save(user);
//		System.out.println(user);
		u1=userRepo.findById(user.getId()).get();
		
		assertEquals(user.getUsername(), u1.getUsername());
	}
	
	@Test
	public void addUserFailure() throws Exception
	{
		User u1=null;
		if(userRepo.findAll().toString().isEmpty())
		{
			userRepo.save(user);
			u1= userRepo.findById(user.getId()).get();
		}
		assertNull(u1);
	}
	
	
	

}
