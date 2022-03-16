package com.turkcell.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACar.entities.concretes.CarDamage;

@Repository
public interface CarDamageDao extends JpaRepository<CarDamage, Integer> {

}
