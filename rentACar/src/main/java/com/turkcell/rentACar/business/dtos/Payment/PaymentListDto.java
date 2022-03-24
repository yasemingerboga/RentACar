package com.turkcell.rentACar.business.dtos.Payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentListDto {

	private int id;
	private int rentalCarId;
	private int invoiceId;
}
