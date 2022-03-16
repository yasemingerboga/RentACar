package com.turkcell.rentACar.entities.concretes;

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

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cars")
public class Car {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "car_id")
	private int id;

	@Column(name = "daily_price")
	private double dailyPrice;

	@Column(name = "model_year")
	private int modelYear;

	@Column(name = "description")
	private String description;

	@Column(name = "kilometer")
	private Double kilometer;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "brand_id")
	private Brand brand;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "color_id")
	private Color color;

	@OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<CarMaintenance> carMaintenances;

	@OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<CarDamage> carDamages;

}
