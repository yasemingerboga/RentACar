package com.turkcell.rentACar.business.requests.Invoice;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInvoiceRequest {

	private int id;

	private String invoiceNumber;

	private LocalDate creationDate;

	private int totalRentDay;
	
	private int customerUserId;

	private int rentalCarId;
}
