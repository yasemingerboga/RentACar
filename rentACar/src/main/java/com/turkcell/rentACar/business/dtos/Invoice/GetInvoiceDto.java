package com.turkcell.rentACar.business.dtos.Invoice;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetInvoiceDto {

	private int id;

	private String invoiceNumber;

	private LocalDate creationDate;

	private double totalPrice;

	private int customerUserId;

	private int rentalCarId;

	private int rentalCarTotalRentDay;

	private LocalDate rentalCarStartingDate;

	private LocalDate rentalCarEndDate;

	private Double rentalCarAdditionalPrice;

}
