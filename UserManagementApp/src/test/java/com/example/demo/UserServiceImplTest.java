package com.example.demo;

import static org.mockito.Mockito.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.dao.UserServiceImpl;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;



public class UserServiceImplTest {

	@Mock
	private UserRepository userRepo;
	
	@InjectMocks
	private UserServiceImpl userService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	public void init()
	{
		MockitoAnnotations.initMocks(this);
		
		mockMvc = MockMvcBuilders.standaloneSetup(userService).build();
	}
	
	private List<User> userList = new ArrayList<>();
	
	@Test
	public void getAllUserSuccess() throws Exception
	{
		when(userRepo.findAll()).thenReturn(null);
		
		User user = new User();
		
		user.setId(101);
		user.setUsername("john");
		user.setPassword("john");	
		userList.add(user);
		when(userRepo.findAll()).thenReturn(userList);
		
		List<User> uList = userService.getAllUsers();
		
		assertEquals(userList, uList);
		
	}
	
	
	@Test
	public void getAllUserFailure() throws Exception
	{
		when(userRepo.findAll()).thenReturn(null);
		
				
		List<User> uList = userService.getAllUsers();
		
		assertNull( uList);
		
	}
	
	@Test
	public void addUserSuccess() throws Exception
	{
		User user = new User();
		
		user.setId(101);
		user.setUsername("john");
		user.setPassword("john");	
		userList.add(user);
		
		when(userRepo.saveAndFlush(any())).thenReturn(user);
		
		User u1 = userService.addUser(user);
		
		assertEquals(user, u1);
	}
	
	
	@Test
	public void addUserFailure() throws Exception
	{
		User user = new User();
		
		user.setId(101);
		user.setUsername("john");
		user.setPassword("john");	
		userList.add(user);
		
		when(userRepo.save(any())).thenReturn(null);
		
		User u1 = userService.addUser(user);
		
		assertNull( u1);
		
		assertNotEquals(user,u1);
		
	}
	
}
