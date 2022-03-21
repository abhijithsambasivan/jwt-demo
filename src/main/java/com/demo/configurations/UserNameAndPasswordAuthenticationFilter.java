package com.demo.configurations;

import static com.demo.constants.Constants.EXPIRATION_TIME;
import static com.demo.constants.Constants.KEY;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.demo.model.Users;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class UserNameAndPasswordAuthenticationFilter  extends UsernamePasswordAuthenticationFilter {
	 final static Logger logger = LoggerFactory.getLogger(UserNameAndPasswordAuthenticationFilter.class);

	private AuthenticationManager authenticationManager;
	
	   public UserNameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
	        this.authenticationManager = authenticationManager;
	    }

	
	  @Override
	    public Authentication attemptAuthentication(HttpServletRequest req,
	                                                HttpServletResponse res) throws AuthenticationException {
	        try {
	            Users user = new ObjectMapper().readValue(req.getInputStream(), Users.class);
	            logger.info("Attempting authentication with user "+user);
	            return authenticationManager.authenticate(
	                    new UsernamePasswordAuthenticationToken(user.getUserName(),
	                            user.getPassword(), new ArrayList<>())
	            );
	        } catch (IOException e) {
	            throw new RuntimeException(e);
	        }
	    }
	  
	   @Override
	    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
	                                            Authentication auth) throws IOException, ServletException {

		   logger.info("Successfully authenticated");
		   logger.info("Generating token....");
		   
		   Date exp = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
	        
	        Key key = Keys.hmacShaKeyFor(KEY.getBytes());
	        
	        Claims claims = Jwts.claims().setSubject(((User) auth.getPrincipal()).getUsername());
	        
	        String token = Jwts.builder()
	        		.setClaims(claims)
	        		.signWith(key, SignatureAlgorithm.HS512)
	        		.setExpiration(exp)
	        		.compact();
	        
	        logger.info("Token generated");
	        logger.info("JWT is: "+token);
	       
	        logger.info("Token will expire at "+exp);
	        res.addHeader("token", token);


	    }
	  
	  
	  
}
