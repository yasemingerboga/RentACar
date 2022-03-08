package com.turkcell.rentACar.entities.concretes;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rental_cars")
public class RentalCar {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rental_car_id")
	private int id;

	@Column(name = "starting_date")
	private LocalDate startingDate;

	@Column(name = "end_date")
	private LocalDate endDate;

	@ManyToOne
	@JoinColumn(name = "car_id")
	private Car car;
}
