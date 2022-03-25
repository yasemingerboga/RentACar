package com.turkcell.rentACar.entities.concretes;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "rental_car_id")
	private int id;

	@Column(name = "starting_date")
	private LocalDate startingDate;

	@Column(name = "end_date")
	private LocalDate endDate;

	@Column(name = "total_rent_day")
	private Long totalRentDay;

	@Column(name = "total_price")
	private Double totalPrice;

	@Column(name = "additional_price")
	private Double additionalPrice = 0.0;

	@Column(name = "starting_kilometer")
	private Double startingKilometer;

	@Column(name = "ending_kilometer")
	private Double endingKilometer;

	@ManyToOne
	@JoinColumn(name = "car_id")
	private Car car;

	@OneToMany(mappedBy = "rentalCar", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<OrderedAdditionalService> orderedAdditionalServices;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "rented_city_id")
	private City rentedCity;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "drop_off_city_id")
	private City dropOffCity;

	@OneToMany(mappedBy = "rentalCar")
	private List<Invoice> invoices;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Customer customer;

}
