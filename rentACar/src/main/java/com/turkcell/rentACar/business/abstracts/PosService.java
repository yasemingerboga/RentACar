package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.requests.Payment.CreatePosServiceRequest;

public interface PosService {

	boolean pay(CreatePosServiceRequest createPosServiceRequest);
}