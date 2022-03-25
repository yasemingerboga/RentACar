package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.CardInformationService;
import com.turkcell.rentACar.business.dtos.CardInformation.CardInformationListDto;
import com.turkcell.rentACar.business.dtos.CardInformation.GetCardInformationDto;
import com.turkcell.rentACar.business.requests.CardInformation.CreateCardInformationRequest;
import com.turkcell.rentACar.business.requests.CardInformation.UpdateCardInformationRequest;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CardInformationDao;
import com.turkcell.rentACar.entities.concretes.CardInformation;

@Service
public class CardInformationManager implements CardInformationService {

	private CardInformationDao cardInformationDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public CardInformationManager(CardInformationDao cardInformationDao, ModelMapperService modelMapperService) {
		super();
		this.cardInformationDao = cardInformationDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<CardInformationListDto>> getAll() {
		List<CardInformation> result = this.cardInformationDao.findAll();
		List<CardInformationListDto> response = result.stream()
				.map(cardInformation -> this.modelMapperService.forDto().map(cardInformation, CardInformationListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<CardInformationListDto>>(response, "Card informations listed successfully.");
	}

	@Override
	public Result add(CreateCardInformationRequest createCardInformationRequest) {
		CardInformation cardInformation = this.modelMapperService.forRequest().map(createCardInformationRequest,
				CardInformation.class);
		cardInformation.setId(0);
		cardInformationDao.save(cardInformation);
		return new SuccessResult("Card information added successfully.");
	}

	@Override
	public DataResult<GetCardInformationDto> getById(int id) {
		CardInformation cardInformation = this.cardInformationDao.getById(id);
		GetCardInformationDto response = this.modelMapperService.forDto().map(cardInformation,
				GetCardInformationDto.class);
		return new SuccessDataResult<GetCardInformationDto>(response, "Getting card informations by id.");
	}

	@Override
	public Result delete(int id) {
		this.cardInformationDao.deleteById(id);
		return new SuccessResult("Card information deleted successfully.");
	}

	@Override
	public Result update(UpdateCardInformationRequest updateCardInformationRequest) {
		CardInformation cardInformation = this.modelMapperService.forRequest().map(updateCardInformationRequest,
				CardInformation.class);
		cardInformationDao.save(cardInformation);
		return new SuccessResult("Card information updated successfully.");
	}

}
