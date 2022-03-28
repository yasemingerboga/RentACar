package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.CardInformationService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.CardInformation.CardInformationListDto;
import com.turkcell.rentACar.business.dtos.CardInformation.GetCardInformationDto;
import com.turkcell.rentACar.business.requests.CardInformation.CreateCardInformationRequest;
import com.turkcell.rentACar.business.requests.CardInformation.UpdateCardInformationRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
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
		List<CardInformationListDto> response = result.stream().map(
				cardInformation -> this.modelMapperService.forDto().map(cardInformation, CardInformationListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<CardInformationListDto>>(response,
				BusinessMessages.CARD_INFORMATION_LIST_SUCCESSFULLY);
	}

	@Override
	public Result add(CreateCardInformationRequest createCardInformationRequest) {

		CardInformation cardInformation = this.modelMapperService.forRequest().map(createCardInformationRequest,
				CardInformation.class);

		cardInformation.setId(0);
		cardInformationDao.save(cardInformation);

		return new SuccessResult(BusinessMessages.CARD_INFORMATION_SAVE_SUCCESSFULLY);
	}

	@Override
	public DataResult<GetCardInformationDto> getById(int id) {

		checkIfCardInformationExists(id);

		CardInformation cardInformation = this.cardInformationDao.getById(id);

		GetCardInformationDto response = this.modelMapperService.forDto().map(cardInformation,
				GetCardInformationDto.class);

		return new SuccessDataResult<GetCardInformationDto>(response,
				BusinessMessages.CARD_INFORMATION_GET_SUCCESSFULLY);
	}

	@Override
	public Result delete(int id) {
		checkIfCardInformationExists(id);

		this.cardInformationDao.deleteById(id);

		return new SuccessResult(BusinessMessages.CARD_INFORMATION_DELETE_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateCardInformationRequest updateCardInformationRequest) {
		checkIfCardInformationExists(updateCardInformationRequest.getId());

		CardInformation cardInformation = this.modelMapperService.forRequest().map(updateCardInformationRequest,
				CardInformation.class);

		cardInformationDao.save(cardInformation);

		return new SuccessResult(BusinessMessages.CARD_INFORMATION_UPDATE_SUCCESSFULLY);
	}

	private void checkIfCardInformationExists(int id) {

		if (!cardInformationDao.existsById(id)) {
			throw new BusinessException(BusinessMessages.CARD_INFORMATION_NOT_FOUND);
		}
	}
}
