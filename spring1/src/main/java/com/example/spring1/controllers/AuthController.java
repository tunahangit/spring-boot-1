package com.example.spring1.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.spring1.entities.User;
import com.example.spring1.requests.UserRequest;
import com.example.spring1.responses.AuthResponse;
import com.example.spring1.security.JwtTokenProvider;
import com.example.spring1.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private AuthenticationManager authenticationManager;
	private JwtTokenProvider jwtTokenProvider;
	private UserService userService;
	private PasswordEncoder passwordEncoder;
	
	
	
	 public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
			UserService userService, PasswordEncoder passwordEncoder) {

		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/login")
	 public AuthResponse Login(@RequestBody UserRequest loginRequest) {
		 UsernamePasswordAuthenticationToken  authToken = new  UsernamePasswordAuthenticationToken
				 (loginRequest.getUsername(), loginRequest.getPassword());
		 Authentication auth = authenticationManager.authenticate(authToken);
		 SecurityContextHolder.getContext().setAuthentication(auth);
		 String jwtToken = jwtTokenProvider.generateJwtToken(auth);
		 User user = userService.getUserByUserName(loginRequest.getUsername());
		 AuthResponse authResponse  = new AuthResponse();
		 authResponse.setMessage("Bearer " +  jwtToken);
		 authResponse.setUserId(user.getId());
		 return authResponse ;
	 }
	 
	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@RequestBody UserRequest registerRequest){
		AuthResponse authResponse = new AuthResponse();
		if(userService.getUserByUserName(registerRequest.getUsername()) != null) {
			   authResponse.setMessage("Username already in use ");
				return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
		}
		User user =new User();
		user.setUsername(registerRequest.getUsername());
		user.setPassword( passwordEncoder.encode(registerRequest.getPassword()) );
		userService.saveUser(user);
		 authResponse.setMessage("Username already in use ");
		return new ResponseEntity<>(authResponse , HttpStatus.CREATED);
	}
}
