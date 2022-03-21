package com.demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.Users;
import com.demo.repository.UserRepository;

@RestController
public class UserController {

	private final static Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
    private UserRepository userRepository;
 
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

    
    @PostMapping("/api/signup")
    public void signUp(@RequestBody Users user) {
    	logger.info("new user "+user);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}