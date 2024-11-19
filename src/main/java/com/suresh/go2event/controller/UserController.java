package com.suresh.go2event.controller;

import java.util.List;


import com.suresh.go2event.model.Ticket;
import com.suresh.go2event.respository.TicketRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.suresh.go2event.model.Event;
import com.suresh.go2event.model.User;
import com.suresh.go2event.respository.EventRepository;
import com.suresh.go2event.respository.UserRepository;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

	final private UserRepository userRepository;

	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@PostMapping("/new")
	public User createUser(@RequestBody User user) {
		userRepository.save(user);
		return user;
	}
	
	@GetMapping("/all")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	} 
	
	@GetMapping("/getUserByEmail")
	public User getUserByEmail(@RequestParam String email) {
		return userRepository.findByEmail(email);
	}
	
	@GetMapping("/getUserById/{id}")
	public User getUserById(@PathVariable Long id) {
		return userRepository.findById(id).orElse(null);
	}
	
	@GetMapping("/{user_id}/tickets")
	public ResponseEntity<Object> getAllTickets(@PathVariable Long user_id) {
		User user = userRepository.findById(user_id).orElse(null);
		if(user == null) {
			return new ResponseEntity<>("User Not Found",HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(user.getTickets(), HttpStatus.OK);
	}

	@PutMapping("/{user_id}/update")
	public ResponseEntity<Object> updateUser(@PathVariable Long user_id,@RequestBody User newUser) {
		User user = userRepository.findById(user_id).orElse(null);
		if (user != null) {
			user.setEmail(newUser.getEmail());
			user.setName(newUser.getName());
			user.setPassword(newUser.getPassword());
			userRepository.save(user);
			return new ResponseEntity<>(user,HttpStatus.OK);
		}
		return new ResponseEntity<>("User Not Found",HttpStatus.BAD_REQUEST);
	}
}
