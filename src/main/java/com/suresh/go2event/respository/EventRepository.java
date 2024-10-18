package com.suresh.go2event.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suresh.go2event.model.Event;

public interface EventRepository extends JpaRepository<Event,Long>{

}
