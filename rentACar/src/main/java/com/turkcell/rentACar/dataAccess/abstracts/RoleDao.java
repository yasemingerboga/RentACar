package com.turkcell.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.rentACar.entities.concretes.Role;

public interface RoleDao extends JpaRepository<Role, Integer> {
	Role findByName(String name);

	Role findById(int id);

	boolean existsByName(String name);
}
