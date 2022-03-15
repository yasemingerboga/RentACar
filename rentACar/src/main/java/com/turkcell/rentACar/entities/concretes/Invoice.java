package com.turkcell.rentACar.entities.concretes;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invoices")
public class Invoice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "invoice_id")
	private int id;

	@Column(name = "invoice_number")
	private String invoiceNumber;

	@Column(name = "creation_date")
	private LocalDate creationDate;

	@Column(name = "total_rent_day")
	private int totalRentDay;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Customer customer;

	@OneToOne
	@JoinColumn(name = "rental_car_id")
	private RentalCar rentalCar;
}
