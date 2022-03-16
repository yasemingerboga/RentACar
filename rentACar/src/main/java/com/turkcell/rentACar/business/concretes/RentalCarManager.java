package com.turkcell.rentACar.business.concretes;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.api.controllers.models.CreateRentalModel;
import com.turkcell.rentACar.api.controllers.models.UpdateRentalModel;
import com.turkcell.rentACar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentACar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentACar.business.abstracts.RentalCarService;
import com.turkcell.rentACar.business.dtos.AdditionalService.GetAdditionalServiceDto;
import com.turkcell.rentACar.business.dtos.CarMaintenance.CarMaintenanceListDto;
import com.turkcell.rentACar.business.dtos.RentalCar.GetRentalCarDto;
import com.turkcell.rentACar.business.dtos.RentalCar.RentalCarListDto;
import com.turkcell.rentACar.business.requests.RentalCar.EndOfRent;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.RentalCarDao;
import com.turkcell.rentACar.entities.concretes.AdditionalService;
import com.turkcell.rentACar.entities.concretes.Car;
import com.turkcell.rentACar.entities.concretes.OrderedAdditionalService;
import com.turkcell.rentACar.entities.concretes.RentalCar;

@Service
public class RentalCarManager implements RentalCarService {

	private RentalCarDao rentalCarDao;
	private ModelMapperService modelMapperService;
	private CarMaintenanceService carMaintenanceService;
	private OrderedAdditionalServiceService orderedAdditionalServiceService;
	private AdditionalServiceService additionalServiceService;
	private IndividualCustomerService individualCustomerService;
	private CorporateCustomerService corporateCustomerService;
	private CarService carService;

	@Autowired
	public RentalCarManager(RentalCarDao rentalCarDao, ModelMapperService modelMapperService,
			CarMaintenanceService carMaintenanceService,
			OrderedAdditionalServiceService orderedAdditionalServiceService,
			AdditionalServiceService additionalServiceService, IndividualCustomerService individualCustomerService,
			CorporateCustomerService corporateCustomerService, CarService carService) {
		this.rentalCarDao = rentalCarDao;
		this.modelMapperService = modelMapperService;
		this.carMaintenanceService = carMaintenanceService;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
		this.additionalServiceService = additionalServiceService;
		this.individualCustomerService = individualCustomerService;
		this.corporateCustomerService = corporateCustomerService;
		this.carService = carService;
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
	public DataResult<GetRentalCarDto> getById(int id) {

		RentalCar result = this.rentalCarDao.getById(id);

		GetRentalCarDto response = this.modelMapperService.forDto().map(result, GetRentalCarDto.class);

		return new SuccessDataResult<GetRentalCarDto>(response, "Rental car has been received successfully.");
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
	public Result addForCorporateCustomer(CreateRentalModel rentalModel) {

		checkIfCorporateCustomerExists(rentalModel.getCreateRentalCarRequest().getCustomerUserId());

		checkIfCarExists(rentalModel.getCreateRentalCarRequest().getCarId());

		RentalCar rentalCar = this.modelMapperService.forRequest().map(rentalModel.getCreateRentalCarRequest(),
				RentalCar.class);

		checkIfInMaintenance(rentalCar);
		checkIfInRent(rentalCar);

		List<OrderedAdditionalService> orderedAdditionalServices = rentalModel
				.getCreateOrderedAdditionalServiceRequest().stream().map(additionalService -> this.modelMapperService
						.forRequest().map(additionalService, OrderedAdditionalService.class))
				.collect(Collectors.toList());

		orderedAdditionalServices.equals(mappingOrderedAdditionalService(orderedAdditionalServices));
		rentalCar.setOrderedAdditionalServices(orderedAdditionalServices);

		rentalCar.equals(checkIfAdditionalPrice(rentalCar));
		rentalCar.equals(checkIfOrderedAdditionalServicesExists(rentalCar));

		rentalCar.setTotalRentDay(calculateTotalRentDay(rentalCar));

		rentalCar.setTotalPrice(calculateTotalPrice(rentalCar));

		rentalCar.setStartingKilometer(carService.getById(rentalCar.getCar().getId()).getData().getKilometer());

		this.rentalCarDao.save(rentalCar);

		return new SuccessResult("Rental car saved successfully.");
	}

	private Double calculateTotalPrice(RentalCar rentalCar) {
		Double dailyPrice = carService.getById(rentalCar.getCar().getId()).getData().getDailyPrice();
		return rentalCar.getAdditionalPrice() + (dailyPrice * rentalCar.getTotalRentDay());
	}

	@Override
	public Result addForIndividualCustomer(CreateRentalModel rentalModel) {

		checkIfIndividualCustomerExists(rentalModel.getCreateRentalCarRequest().getCustomerUserId());

		checkIfCarExists(rentalModel.getCreateRentalCarRequest().getCarId());

		RentalCar rentalCar = this.modelMapperService.forRequest().map(rentalModel.getCreateRentalCarRequest(),
				RentalCar.class);

		checkIfInMaintenance(rentalCar);
		checkIfInRent(rentalCar);

		List<OrderedAdditionalService> orderedAdditionalServices = rentalModel
				.getCreateOrderedAdditionalServiceRequest().stream().map(additionalService -> this.modelMapperService
						.forRequest().map(additionalService, OrderedAdditionalService.class))
				.collect(Collectors.toList());

		orderedAdditionalServices.equals(mappingOrderedAdditionalService(orderedAdditionalServices));
		rentalCar.setOrderedAdditionalServices(orderedAdditionalServices);

		rentalCar.equals(checkIfAdditionalPrice(rentalCar));
		rentalCar.equals(checkIfOrderedAdditionalServicesExists(rentalCar));

		rentalCar.setTotalRentDay(calculateTotalRentDay(rentalCar));

		rentalCar.setTotalPrice(calculateTotalPrice(rentalCar));

		rentalCar.setStartingKilometer(rentalCar.getCar().getKilometer());

		this.rentalCarDao.save(rentalCar);

		return new SuccessResult("Rental car saved successfully.");
	}

	private void checkIfIndividualCustomerExists(int id) {
		if (!individualCustomerService.existById(id).isSuccess()) {
			throw new BusinessException("There is no individual customer with the specified id.");
		}
	}

	private void checkIfCorporateCustomerExists(int id) {

		if (!corporateCustomerService.existById(id).isSuccess()) {

			throw new BusinessException("There is no corporate customer with the specified id.");
		}
	}

	private List<OrderedAdditionalService> mappingOrderedAdditionalService(
			List<OrderedAdditionalService> orderedAdditionalServices) {

		checkIfAdditionalServiceExists(orderedAdditionalServices);

		List<GetAdditionalServiceDto> getAdditionalServiceDtos = new ArrayList<>();

		orderedAdditionalServices.forEach(orderedAdditionalService -> {
			getAdditionalServiceDtos.add(additionalServiceService
					.getById(orderedAdditionalService.getAdditionalService().getId()).getData());
		});

		List<AdditionalService> additionalServices = getAdditionalServiceDtos.stream()
				.map(additionalService -> this.modelMapperService.forRequest().map(additionalService,
						AdditionalService.class))
				.collect(Collectors.toList());
		for (int i = 0; i < orderedAdditionalServices.size(); i++) {
			orderedAdditionalServices.get(i).setAdditionalService(additionalServices.get(i));
			orderedAdditionalServices.get(i).setId(0);
		}
		return orderedAdditionalServices;
	}

	private void checkIfAdditionalServiceExists(List<OrderedAdditionalService> orderedAdditionalServices) {

		for (OrderedAdditionalService orderedAdditionalService : orderedAdditionalServices) {

			if (!additionalServiceService.existById(orderedAdditionalService.getAdditionalService().getId())
					.isSuccess()) {

				throw new BusinessException("There is no additional service with the specified id.");
			}
		}
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

	private void checkIfInRent(RentalCar rentalCar) {

		List<RentalCar> rentalCarList = this.rentalCarDao.getByCar_Id(rentalCar.getCar().getId());

		if (rentalCarList == null) {
			return;
		}
		for (RentalCar rental : rentalCarList) {
			if (rentalCar.getId() != rental.getId()) {
				if (rentalCar.getStartingDate().isBefore(rental.getEndDate())
						&& rentalCar.getStartingDate().isAfter(rental.getStartingDate())) {
					throw new BusinessException("This car is rented already.");
				}
			}

		}

	}

	@Override
	public Result update(UpdateRentalModel updateRentalModel) {

		orderedAdditionalServiceService.deleteByRentalCarId(updateRentalModel.getUpdateRentalCarRequest().getId());

		RentalCar updatedRentalCar = this.modelMapperService.forRequest()
				.map(updateRentalModel.getUpdateRentalCarRequest(), RentalCar.class);

		checkIfInMaintenance(updatedRentalCar);
		checkIfInRent(updatedRentalCar);

		List<OrderedAdditionalService> orderedAdditionalServices = updateRentalModel
				.getOrderedAdditionalServiceRequests().stream().map(additionalService -> this.modelMapperService
						.forRequest().map(additionalService, OrderedAdditionalService.class))
				.collect(Collectors.toList());

		orderedAdditionalServices.equals(mappingOrderedAdditionalService(orderedAdditionalServices));

		updatedRentalCar.equals(checkIfAdditionalPrice(updatedRentalCar));

		updatedRentalCar.setOrderedAdditionalServices(orderedAdditionalServices);
		updatedRentalCar.equals(checkIfOrderedAdditionalServicesExists(updatedRentalCar));

		updatedRentalCar.setTotalRentDay(calculateTotalRentDay(updatedRentalCar));

		updatedRentalCar.setTotalPrice(calculateTotalPrice(updatedRentalCar));

		this.rentalCarDao.save(updatedRentalCar);

		return new SuccessResult("Rental car is updated.");
	}

	public void checkIfCarExists(int carId) {

		if (!carService.existsById(carId).isSuccess()) {

			throw new BusinessException("There is no car with the specified id.");
		}
	}

	@Override
	public Result delete(int id) {

		checkIfRentalCarExists(id);

		this.rentalCarDao.deleteById(id);

		return new SuccessResult("Deleted successfully.");
	}

	public void checkIfRentalCarExists(int rentalCarId) {

		if (!rentalCarDao.existsById(rentalCarId)) {

			throw new BusinessException("There is no rental car with the specified id.");

		}
	}

	private int calculateTotalRentDay(RentalCar rentalCar) {

		LocalDate startingDay = LocalDate.of(rentalCar.getStartingDate().getYear(),
				rentalCar.getStartingDate().getMonthValue(), rentalCar.getStartingDate().getDayOfMonth());
		LocalDate endDay = LocalDate.of(rentalCar.getEndDate().getYear(), rentalCar.getEndDate().getMonthValue(),
				rentalCar.getEndDate().getDayOfMonth());

		Period period = Period.between(startingDay, endDay);
		return Math.abs(period.getDays());
	}

	@Override
	public Result endOfRent(EndOfRent endOfRent) {

		RentalCar rentalCar = rentalCarDao.getById(endOfRent.getId());

		rentalCar.setEndingKilometer(endOfRent.getEndingKilometer());

		carService.updateKilometer(rentalCar.getCar().getId(), rentalCar.getEndingKilometer());

		rentalCarDao.save(rentalCar);

		return new SuccessResult("Rent ends.");
	}

}
