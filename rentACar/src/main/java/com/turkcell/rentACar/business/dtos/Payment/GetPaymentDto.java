package com.turkcell.rentACar.business.dtos.Payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPaymentDto {

	private int id;
	private int rentId;
	private int invoiceId;
}