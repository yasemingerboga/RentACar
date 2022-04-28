package com.turkcell.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.rentACar.entities.concretes.User;

public interface UserDao extends JpaRepository<User, Integer> {
	User findByEmail(String email);

	boolean existsByEmail(String email);
}
