package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.api.controllers.models.Payment.PayExtraModel;
import com.turkcell.rentACar.api.controllers.models.Payment.PaymentModel;
import com.turkcell.rentACar.business.dtos.Payment.PaymentListDto;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface PaymentService {

	Result addForIndividualCustomer(PaymentModel paymentModel);

	Result addForCorporateCustomer(PaymentModel paymentModel);

	DataResult<List<PaymentListDto>> getAll();

	Result payExtraForIndividualCustomer(PayExtraModel payExtraModel);

	Result payExtraForCorporateCustomer(PayExtraModel payExtraModel);

}
