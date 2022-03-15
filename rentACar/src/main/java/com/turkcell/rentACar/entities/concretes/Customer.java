package com.turkcell.rentACar.entities.concretes;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "customers")
@NoArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Customer extends User {

	@OneToMany(mappedBy = "customer")
	private List<Invoice> invoices;

	@OneToMany(mappedBy = "customer")
	private List<RentalCar> rentalCars;

}
