package com.turkcell.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACar.entities.concretes.Color;

@Repository
public interface ColorDao extends JpaRepository<Color, Integer>{

	Color findByColorName(String name);
	boolean existsByColorName(String name);
	Color findById(int id);
}
