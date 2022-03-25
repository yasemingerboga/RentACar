package com.turkcell.rentACar.business.requests.Invoice;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceRequest {

	private int id;

	private String invoiceNumber;

	private LocalDate creationDate; 

	private int customerUserId;

	private int rentalCarId;

	private LocalDate startDate;

	private LocalDate endDate;

	private Double totalPrice;

	private Long totalRentDay;

}
