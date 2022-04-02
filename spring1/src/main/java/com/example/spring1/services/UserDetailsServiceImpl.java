package com.example.spring1.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.spring1.entities.User;
import com.example.spring1.repos.UserRepository;
import com.example.spring1.security.JwtUserDetails;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private UserRepository userRepository;

	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		return JwtUserDetails.create(user);
	}
	
	public UserDetails loadUsersById(Long id) {
		User user =userRepository.findById(id).get();
		return JwtUserDetails.create(user);
	}

}
