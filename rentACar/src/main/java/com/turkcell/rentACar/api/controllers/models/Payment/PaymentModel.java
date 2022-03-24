package com.turkcell.rentACar.api.controllers.models.Payment;

import javax.validation.constraints.NotNull;

import com.turkcell.rentACar.api.controllers.models.RentalCar.CreateRentalModel;
import com.turkcell.rentACar.business.requests.Invoice.CreateInvoiceRequestForPayment;
import com.turkcell.rentACar.business.requests.Payment.CreatePaymentRequest;
import com.turkcell.rentACar.business.requests.Payment.CreatePosServiceRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentModel {

	@NotNull
	private CreatePosServiceRequest createPosServiceRequest;

	@NotNull
	private CreateRentalModel createRentalModel;

	private CreateInvoiceRequestForPayment createInvoiceRequest;

	private CreatePaymentRequest createPaymentRequest;
}
