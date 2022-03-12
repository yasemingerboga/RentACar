package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.Color.ColorListDto;
import com.turkcell.rentACar.business.dtos.Color.GetColorDto;
import com.turkcell.rentACar.business.requests.Color.CreateColorRequest;
import com.turkcell.rentACar.business.requests.Color.UpdateColorRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface ColorService {

	DataResult<List<ColorListDto>> getAll();
	Result add(CreateColorRequest createColorRequest);
	DataResult<GetColorDto> getById(int id);
	Result delete(int id);
	Result update(UpdateColorRequest updateColorRequest);
}
