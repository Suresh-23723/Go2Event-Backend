package com.suresh.go2event.controller;

import java.util.List;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.suresh.go2event.model.Event;
import com.suresh.go2event.model.User;
import com.suresh.go2event.respository.EventRepository;
import com.suresh.go2event.respository.UserRepository;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

	final private UserRepository userRepository;
	final private EventRepository eventRepository;

	public UserController(UserRepository userRepository,EventRepository eventRepository) {
		this.userRepository = userRepository;
		this.eventRepository = eventRepository;
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
	
	@PutMapping("/{user_id}/addEvent/{event_id}")
	public User addEventToUser(@PathVariable Long user_id,@PathVariable Long event_id) {
		User user = userRepository.findById(user_id).orElse(null);
		Event event = eventRepository.findById(event_id).orElse(null);
		if((user != null && event != null) && !user.getEvents().contains(event)) {
			user.getEvents().add(event);
			event.setAvailableSeats(event.getAvailableSeats() - 1);
			userRepository.save(user);
			eventRepository.save(event);
		}
		return user;
	}
	
	@PutMapping("/{user_id}/cancelEvent/{event_id}")
	public User removeEventToUser(@PathVariable Long user_id,@PathVariable Long event_id) {
		User user = userRepository.findById(user_id).orElse(null);
		Event event = eventRepository.findById(event_id).orElse(null);
		if((user != null && event != null) && user.getEvents().contains(event)) {
			user.getEvents().remove(event);
			event.setAvailableSeats(event.getAvailableSeats() + 1);
			userRepository.save(user);
			eventRepository.save(event);
		}
		return user;
	}
	
	@PutMapping("/{user_id}/update")
	public User updateUser(@PathVariable Long user_id,@RequestBody User newUser) {
		User user = userRepository.findById(user_id).orElse(null);
		if (user != null) {
			user.setEmail(newUser.getEmail());
			user.setName(newUser.getName());
			user.setPassword(newUser.getPassword());
			user.setEvents(newUser.getEvents());
			userRepository.save(user);
			return newUser;
		}
		return newUser;
	}
}
