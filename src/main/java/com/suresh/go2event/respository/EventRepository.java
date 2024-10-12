package com.suresh.go2event.respository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.suresh.go2event.model.Event;
import com.suresh.go2event.model.User;

public interface EventRepository extends JpaRepository<Event,Long>{

	Set<User> findUsersById(Long event_id);

}
