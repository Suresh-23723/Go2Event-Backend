package com.suresh.go2event.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suresh.go2event.model.User;

public interface UserRepository extends JpaRepository<User,Long> {
	
	User findByEmail(String email);

}
