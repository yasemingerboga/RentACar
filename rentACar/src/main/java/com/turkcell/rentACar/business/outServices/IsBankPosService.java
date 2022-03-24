package com.turkcell.rentACar.business.outServices;

import org.springframework.stereotype.Service;

@Service
public class IsBankPosService {

	public boolean makePayment(String creditCardNo, String cvv, String holderName, String month, String year) {

		return true;
	}

}
