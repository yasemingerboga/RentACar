package com.turkcell.rentACar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACar.entities.concretes.RentalCar;

@Repository
public interface RentalCarDao extends JpaRepository<RentalCar, Integer>{
	
	List<RentalCar> getByCar_Id(int carId);

	RentalCar getById(int rentalId);

}
