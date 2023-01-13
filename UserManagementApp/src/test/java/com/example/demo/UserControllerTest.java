package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.controller.UserController;
import com.example.demo.dao.UserServiceImpl;
import com.example.demo.model.User;


import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest 
{
	@Mock
	private UserServiceImpl userService;
	
	@InjectMocks
	private UserController userController;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	public void init()
	{
		MockitoAnnotations.initMocks(this);
		
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}
	
	private List<User> userList = new ArrayList<>();
	
	@Test
	public void getAllUserSuccess() throws Exception
	{		
		User user = new User();
		
		user.setId(101);
		user.setUsername("Mark");
		user.setPassword("Mark");
		userList.add(user);
		when(userService.getAllUsers()).thenReturn(userList);
		
	//	List<User> uList = userService.getAllUsers();
		
		assertEquals(1, userList.size());
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v2/getAllUsers").contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	
	
	@Test
	public void getAllUserFailure() throws Exception
	{

		userList.clear();
		
		when(userService.getAllUsers()).thenReturn(userList);
		
	//	List<User> uList = userService.getAllUsers();
		
		assertEquals(0, userList.size());
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v2/getAllUsers").contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void addUserSuccess() throws Exception
	{
		User user = new User();
		
		user.setId(101);
		user.setUsername("Mark");
		user.setPassword("Mark");
		userList.add(user);
		when(userService.addUser(any())).thenReturn(user);
		
	//	List<User> uList = userService.getAllUsers();
		
		assertEquals(1, userList.size());
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v2/addUser").contentType(MediaType.APPLICATION_JSON)
		.content(new ObjectMapper().writeValueAsString(user)))
		.andExpect(MockMvcResultMatchers.status().isCreated());
		
	}
	
	@Test
	public void addUserFailure() throws Exception
	{
		User user = new User();
		
		user.setId(101);
		user.setUsername("Mark");
		user.setUsername("Mark");
		userList.add(user);
		when(userService.addUser(any())).thenReturn(null);
		
		userList.clear();
	//	List<User> uList = userService.getAllUsers();
//		User u1 = userService.addUser(user);
		
		assertEquals(0, userList.size());
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v2/addUser").contentType(MediaType.APPLICATION_JSON)
		.content(new ObjectMapper().writeValueAsString(user)))
		.andExpect(MockMvcResultMatchers.status().isInternalServerError());
		
	}
}
