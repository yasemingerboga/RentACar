package com.turkcell.rentACar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACar.entities.concretes.Car;

@Repository
public interface CarDao extends JpaRepository<Car, Integer> {
	Car findById(int id);

	@Query("select c from Car c where c.dailyPrice <= :dailyPrice")
	List<Car> getByDailyPrice(@Param("dailyPrice") double dailyPrice);
}
