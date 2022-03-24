package com.turkcell.rentACar.business.requests.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceRequestForPayment {

	@JsonIgnore
	private int rentalCarId;

}
