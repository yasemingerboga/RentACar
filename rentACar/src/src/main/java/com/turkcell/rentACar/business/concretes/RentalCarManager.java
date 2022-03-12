package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.abstracts.OrderedAdditionalProductService;
import com.turkcell.rentACar.business.abstracts.RentalCarService;
import com.turkcell.rentACar.business.dtos.CarMaintenance.CarMaintenanceListDto;
import com.turkcell.rentACar.business.dtos.RentalCar.GetRentalCarDto;
import com.turkcell.rentACar.business.dtos.RentalCar.RentalCarListDto;
import com.turkcell.rentACar.business.requests.RentalCar.CreateRentalCarRequest;
import com.turkcell.rentACar.business.requests.RentalCar.UpdateRentalCarRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.RentalCarDao;
import com.turkcell.rentACar.entities.concretes.RentalCar;

@Service
public class RentalCarManager implements RentalCarService {

	private RentalCarDao rentalCarDao;
	private ModelMapperService modelMapperService;
	private CarMaintenanceService carMaintenanceService;
	private CarService carService;
	private OrderedAdditionalProductService orderedAdditionalProductService;

	@Autowired
	public RentalCarManager(RentalCarDao rentalCarDao, ModelMapperService modelMapperService,
			CarMaintenanceService carMaintenanceService, CarService carService,
			OrderedAdditionalProductService orderedAdditionalProductService) {
		this.rentalCarDao = rentalCarDao;
		this.modelMapperService = modelMapperService;
		this.carMaintenanceService = carMaintenanceService;
		this.carService = carService;
		this.orderedAdditionalProductService = orderedAdditionalProductService;
	}

	@Override
	public Result add(CreateRentalCarRequest createRentalCarRequest) {

		checkIfCarExist(createRentalCarRequest.getCarId());

		RentalCar rentalCar = this.modelMapperService.forRequest().map(createRentalCarRequest, RentalCar.class);

		checkIfInMaintenance(rentalCar);

		rentalCar.equals(checkIfAdditionalPrice(rentalCar));
		rentalCar.equals(checkIfOrderedAdditionalServicesExists(rentalCar));

		this.rentalCarDao.save(rentalCar);

		return new SuccessResult("Rental car saved successfully.");
	}

	private RentalCar checkIfAdditionalPrice(RentalCar rentalCar) {

		if (rentalCar.getRentedCity().getId() != rentalCar.getDropOffCity().getId()) {
			rentalCar.setAdditionalPrice(750.0);
		}
		return rentalCar;
	}

	private RentalCar checkIfOrderedAdditionalServicesExists(RentalCar rentalCar) {

		if (!rentalCar.getOrderedAdditionalServices().isEmpty()) {
			rentalCar.getOrderedAdditionalServices().forEach(orderedAdditionalService -> {
				rentalCar.setAdditionalPrice(
						rentalCar.getAdditionalPrice() + orderedAdditionalService.getAdditionalService().getPrice());
				orderedAdditionalService.setRentalCar(rentalCar);
			});
		}

		return rentalCar;
	}

	private void checkIfInMaintenance(RentalCar rentalCar) {

		List<CarMaintenanceListDto> result = this.carMaintenanceService.getByCarId(rentalCar.getCar().getId())
				.getData();

		if (result == null) {
			return;
		}

		for (CarMaintenanceListDto carMaintenanceDto : result) {
			if ((carMaintenanceDto.getReturnDate() != null)
					&& (rentalCar.getStartingDate().isBefore(carMaintenanceDto.getReturnDate())
							|| rentalCar.getEndDate().isBefore(carMaintenanceDto.getReturnDate()))) {
				throw new BusinessException("This car cannot be rented as it is under maintenance.");
			}
			if (carMaintenanceDto.getReturnDate() == null) {
				throw new BusinessException(
						"This car cannot be rented as it is under maintenance / return date equals null.");
			}
		}
	}

	@Override
	public DataResult<GetRentalCarDto> getById(int id) {

		RentalCar result = this.rentalCarDao.getById(id);

		GetRentalCarDto response = this.modelMapperService.forDto().map(result, GetRentalCarDto.class);

		return new SuccessDataResult<GetRentalCarDto>(response, "Rental car has been received successfully.");
	}

	@Override
	public DataResult<List<RentalCarListDto>> getAll() {

		List<RentalCar> resultList = this.rentalCarDao.findAll();

		List<RentalCarListDto> responseList = resultList.stream()
				.map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, RentalCarListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<RentalCarListDto>>(responseList, "Rental cars listed successfully.");
	}

	@Override
	public DataResult<List<RentalCarListDto>> getByCarId(int carId) {

		List<RentalCar> result = this.rentalCarDao.getByCar_Id(carId);

		List<RentalCarListDto> response = result.stream()
				.map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, RentalCarListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<RentalCarListDto>>(response, "Rental cars listed successfully.");
	}

	@Override
	public Result update(UpdateRentalCarRequest updateRentalCarRequest) {
		RentalCar rentalCar = this.modelMapperService.forRequest().map(updateRentalCarRequest, RentalCar.class);

		rentalCar.equals(checkIfOrderedAdditionalServicesExistsInUpdate(rentalCar));

		rentalCar.setId(updateRentalCarRequest.getRentalId());

		this.rentalCarDao.save(rentalCar);

		return new SuccessResult("Rental car is updated.");
	}

	private RentalCar checkIfOrderedAdditionalServicesExistsInUpdate(RentalCar rentalCar) {

		orderedAdditionalProductService.getAllByRentalCarId(rentalCar.getId()).getData().forEach(ordered -> {
			orderedAdditionalProductService.delete(ordered.getId());
		});

		rentalCar.getOrderedAdditionalServices().forEach(orderedAdditionalService -> {
			rentalCar.setAdditionalPrice(
					rentalCar.getAdditionalPrice() + orderedAdditionalService.getAdditionalService().getPrice());
			orderedAdditionalService.setRentalCar(rentalCar);
		});

		return rentalCar;
	}

	public boolean checkIfCarExist(int carId) {

		if (this.carService.getById(carId) == null) {
			throw new BusinessException("There is no car with a specified id.");
		}

		return true;
	}

	@Override
	public Result delete(int id) {

		checkIfRentalCarExists(id);
		this.rentalCarDao.deleteById(id);
		return new SuccessResult("Deleted successfully.");
	}

	public boolean checkIfRentalCarExists(int rentalId) {

		if (this.rentalCarDao.getById(rentalId) == null) {
			throw new BusinessException("The rental car with this ID is not available.");
		}

		return true;
	}

}
