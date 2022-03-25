package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.CardInformation.CardInformationListDto;
import com.turkcell.rentACar.business.dtos.CardInformation.GetCardInformationDto;
import com.turkcell.rentACar.business.requests.CardInformation.CreateCardInformationRequest;
import com.turkcell.rentACar.business.requests.CardInformation.UpdateCardInformationRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface CardInformationService {

	DataResult<List<CardInformationListDto>> getAll();

	Result add(CreateCardInformationRequest createCardInformationRequest);

	DataResult<GetCardInformationDto> getById(int id);

	Result delete(int id);

	Result update(UpdateCardInformationRequest updateCardInformationRequest);
}
