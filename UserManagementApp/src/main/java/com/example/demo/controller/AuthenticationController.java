package com.example.demo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.UserServiceImpl;
import com.example.demo.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("auth/v1/user")
public class AuthenticationController {
	
	private Map<String,String> map=new HashMap<String,String>();
	
	private UserServiceImpl userServiceImpl;
	
	@Autowired
	public AuthenticationController(UserServiceImpl userServiceImpl)
	{
		super();
	
		this.userServiceImpl=userServiceImpl;
		
	}
	
	@GetMapping("/getUserById")
	public String serviceStarted()
	{
		return "Authentication service started";
		
	}
	
	public String generateToken(String username,String password) throws Exception
	{
		String jwtToken="";
		if(username==null && password==null)
		{
			System.out.println("No user and Password");
			throw new Exception("Please provide valid credentials");
		}
		boolean flag=userServiceImpl.validateUser(username, password);
		System.out.println(flag);
		
		if(!flag)
		{
			throw new Exception("credentials are incorrect");
		}
		else
		{
			jwtToken=Jwts.builder().setSubject(username).setIssuedAt(new Date())
					.setExpiration(new Date(System.currentTimeMillis()+300000))
					.signWith(SignatureAlgorithm.HS256,"secret key").compact();
		}
		
		return jwtToken;
	}
	
	@PostMapping(value="/addUser",consumes="application/json;charset=utf-8")
	public ResponseEntity<?> addUser(@RequestBody User user)
	{
		if(userServiceImpl.addUser(user)!=null)
		{
			return new ResponseEntity<User>(user,HttpStatus.CREATED);
		}
		return new ResponseEntity<String>("Data is not inserted",HttpStatus.CONFLICT);
	}
	
	@PostMapping(value="/login",consumes="application/json;charset=utf-8")
	public ResponseEntity<?> addLogin(@RequestBody User user)
	{
		try
		{
			String jwtToken=generateToken(user.getUsername(),user.getPassword());
			map.put("message", "user successfully logged in");
			map.put("token", jwtToken);
		}
		catch(Exception e)
		{
			map.put("message", "user login unsuccessfull");
			map.put("token", null);
			return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

}
