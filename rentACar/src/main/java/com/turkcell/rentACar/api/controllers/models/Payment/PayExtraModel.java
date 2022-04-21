package com.turkcell.rentACar.api.controllers.models.Payment;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.turkcell.rentACar.business.requests.Invoice.CreateInvoiceRequestForPayment;
import com.turkcell.rentACar.business.requests.Payment.CreatePaymentRequest;
import com.turkcell.rentACar.business.requests.Payment.CreatePosServiceRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayExtraModel {
	@NotNull
	private CreatePosServiceRequest createPosServiceRequest;
	@Positive
	private int rentalCarId;
}
