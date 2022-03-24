package com.turkcell.rentACar.business.adapters.posAdapters;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.PosService;
import com.turkcell.rentACar.business.outServices.IsBankPosService;
import com.turkcell.rentACar.business.requests.Payment.CreatePosServiceRequest;

@Service
@Primary
public class IsBankPosAdapter implements PosService {

	@Override
	public boolean pay(CreatePosServiceRequest createPosServiceRequest) {
		IsBankPosService isBankPosService = new IsBankPosService();
		return isBankPosService.makePayment(createPosServiceRequest.getCardNo(), createPosServiceRequest.getCvv(),
				createPosServiceRequest.getCardHolder(), createPosServiceRequest.getMonth(),
				createPosServiceRequest.getYear());
	}

}
