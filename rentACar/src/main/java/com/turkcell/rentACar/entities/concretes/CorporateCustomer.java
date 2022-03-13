package com.turkcell.rentACar.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "corporateCustomers")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class CorporateCustomer extends Customer {

	@Column(name = "company_name")
	private String companyName;

	@Column(name = "tax_number")
	private String taxNumber;

}
