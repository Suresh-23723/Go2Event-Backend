package com.suresh.go2event.controller;

import com.suresh.go2event.model.Event;
import com.suresh.go2event.model.Ticket;
import com.suresh.go2event.model.User;
import com.suresh.go2event.respository.EventRepository;
import com.suresh.go2event.respository.TicketRepository;
import com.suresh.go2event.respository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/tickets")
public class TicketController {

    final private TicketRepository ticketRepository;
    final private UserRepository userRepository;
    final private EventRepository eventRepository;

    public TicketController(TicketRepository tr,EventRepository er,UserRepository ur) {
        this.ticketRepository = tr;
        this.eventRepository = er;
        this.userRepository = ur;
    }

    @PostMapping("/new/user/{user_id}/event/{event_id}")
    public ResponseEntity<Object> createTicket(@RequestBody Ticket ticket, @PathVariable Long user_id, @PathVariable Long event_id) {
        User user = userRepository.findById(user_id).orElse(null);
        Event event = eventRepository.findById(event_id).orElse(null);
        if(user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
        }
        if(event == null) {
            return new ResponseEntity<>("Event not found", HttpStatus.BAD_REQUEST);
        }
        ticket.setUser(user);
        ticket.setEvent(event);
        ticketRepository.save(ticket);
        user.getTickets().add(ticket);
        event.getTickets().add(ticket);
        event.setAvailableSeats(event.getAvailableSeats() - ticket.getNumberOfSeats());
        userRepository.save(user);
        eventRepository.save(event);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{ticket_id}")
    public User deleteTicket(@PathVariable Long ticket_id) {
        Ticket ticket = ticketRepository.findById(ticket_id).orElse(null);
        User user = ticket.getUser();
        Event event = ticket.getEvent();
        event.setAvailableSeats(event.getAvailableSeats() + ticket.getNumberOfSeats());
        user.getTickets().remove(ticket);
        event.getTickets().remove(ticket);
        userRepository.save(user);
        eventRepository.save(event);
        ticketRepository.delete(ticket);
        return user;
    }

}
