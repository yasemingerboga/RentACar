package com.turkcell.rentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.api.controllers.models.Payment.PaymentModel;
import com.turkcell.rentACar.business.abstracts.PaymentService;
import com.turkcell.rentACar.business.dtos.Payment.PaymentListDto;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {

	private PaymentService paymentService;

	@Autowired
	public PaymentsController(PaymentService paymentService) {
		super();
		this.paymentService = paymentService;
	}

	@PostMapping("/addForCorporateCustomer")
	Result addForCorporateCustomer(@RequestBody @Valid PaymentModel paymentModel) {

		return this.paymentService.addForCorporateCustomer(paymentModel);
	}

	@PostMapping("/addForIndividualCustomer")
	Result addForIndividualCustomer(@RequestBody @Valid PaymentModel paymentModel) {

		return this.paymentService.addForIndividualCustomer(paymentModel);
	}

	@GetMapping("/getall")
	public DataResult<List<PaymentListDto>> getAll() {
		return this.paymentService.getAll();
	}
}
