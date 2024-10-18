package com.suresh.go2event.respository;

import com.suresh.go2event.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TicketRepository extends JpaRepository<Ticket,Long> {

}
