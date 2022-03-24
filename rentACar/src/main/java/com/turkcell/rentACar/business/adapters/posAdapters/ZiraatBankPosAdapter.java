package com.turkcell.rentACar.business.adapters.posAdapters;

import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.PosService;
import com.turkcell.rentACar.business.outServices.ZiraatBankPosService;
import com.turkcell.rentACar.business.requests.Payment.CreatePosServiceRequest;

@Service
public class ZiraatBankPosAdapter implements PosService {

	@Override
	public boolean pay(CreatePosServiceRequest createPosServiceRequest) {
		ZiraatBankPosService ziraatBankPosService = new ZiraatBankPosService();
		return ziraatBankPosService.makePayment(createPosServiceRequest.getCardNo(), createPosServiceRequest.getCvv(),
				createPosServiceRequest.getCardHolder(), createPosServiceRequest.getMonth(),
				createPosServiceRequest.getYear());
	}

}
