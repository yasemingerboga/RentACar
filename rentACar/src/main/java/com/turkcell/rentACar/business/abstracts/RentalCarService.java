package com.turkcell.rentACar.business.abstracts;

import java.time.LocalDate;
import java.util.List;

import com.turkcell.rentACar.api.controllers.models.RentalCar.CreateRentalModel;
import com.turkcell.rentACar.api.controllers.models.RentalCar.UpdateRentalModel;
import com.turkcell.rentACar.business.dtos.RentalCar.GetRentalCarDto;
import com.turkcell.rentACar.business.dtos.RentalCar.RentalCarListDto;
import com.turkcell.rentACar.business.requests.RentalCar.EndOfRent;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.concretes.RentalCar;

public interface RentalCarService {

	DataResult<RentalCar> addForCorporateCustomer(CreateRentalModel rentalModel);

	DataResult<RentalCar> addForIndividualCustomer(CreateRentalModel rentalModel);

	DataResult<GetRentalCarDto> getById(int id);

	DataResult<List<RentalCarListDto>> getAll();

	DataResult<List<RentalCarListDto>> getByCarId(int carId);

	Result update(UpdateRentalModel updateRentalModel);

	Result delete(int id);

	Result endOfRent(EndOfRent endOfRent);

	Long calculateTotalRentDay(LocalDate startingDate, LocalDate endDate);

	Double calculateTotalPrice(int carId, Long totalRentDay, Double additionalPrice);

	boolean saveNewRentalCarAfterPayingExtra(RentalCar rentalCar);

	public boolean checkIfIsRightTime(RentalCar rentalCar);
}
