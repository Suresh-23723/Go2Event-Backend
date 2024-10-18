package com.suresh.go2event.controller;


import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suresh.go2event.model.Event;
import com.suresh.go2event.model.User;
import com.suresh.go2event.respository.EventRepository;

@RestController
@CrossOrigin
@RequestMapping("/events")
public class EventController {
	
	@Autowired
	private EventRepository eventRepository;
	
	@PostMapping("/new")
	public Event createEvent(@RequestBody Event event) {
		eventRepository.save(event);
		return event;
	}
	
	@GetMapping("/all")
	public List<Event> getAllEvents() {
		return eventRepository.findAll();
	}

}
