package com.demo.configurations;

import static java.util.Collections.emptyList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.model.Users;
import com.demo.repository.UserRepository;

@Service
public class UsersDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = userRepo.findByUserName(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return new User(user.getUserName(), user.getPassword(), emptyList());
	}
}
