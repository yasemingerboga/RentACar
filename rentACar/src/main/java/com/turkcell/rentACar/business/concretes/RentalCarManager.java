package com.turkcell.rentACar.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turkcell.rentACar.api.controllers.models.RentalCar.CreateRentalModel;
import com.turkcell.rentACar.api.controllers.models.RentalCar.UpdateRentalModel;
import com.turkcell.rentACar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentACar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentACar.business.abstracts.InvoiceService;
import com.turkcell.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentACar.business.abstracts.RentalCarService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.CarMaintenance.CarMaintenanceListDto;
import com.turkcell.rentACar.business.dtos.Invoice.InvoiceListDto;
import com.turkcell.rentACar.business.dtos.RentalCar.GetRentalCarDto;
import com.turkcell.rentACar.business.dtos.RentalCar.RentalCarListDto;
import com.turkcell.rentACar.business.requests.RentalCar.EndOfRent;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.ErrorResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.RentalCarDao;
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
	private InvoiceService invoiceService;

	@Autowired
	public RentalCarManager(RentalCarDao rentalCarDao, ModelMapperService modelMapperService,
			CarMaintenanceService carMaintenanceService,
			OrderedAdditionalServiceService orderedAdditionalServiceService,
			AdditionalServiceService additionalServiceService, IndividualCustomerService individualCustomerService,
			CorporateCustomerService corporateCustomerService, CarService carService, InvoiceService invoiceService) {
		this.rentalCarDao = rentalCarDao;
		this.modelMapperService = modelMapperService;
		this.carMaintenanceService = carMaintenanceService;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
		this.additionalServiceService = additionalServiceService;
		this.individualCustomerService = individualCustomerService;
		this.corporateCustomerService = corporateCustomerService;
		this.carService = carService;
		this.invoiceService = invoiceService;
	}

	@Override
	public DataResult<List<RentalCarListDto>> getAll() {

		List<RentalCar> resultList = this.rentalCarDao.findAll();

		List<RentalCarListDto> responseList = resultList.stream()
				.map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, RentalCarListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<RentalCarListDto>>(responseList,
				BusinessMessages.RENTAL_CAR_LIST_SUCCESSFULLY);
	}

	@Override
	public DataResult<GetRentalCarDto> getById(int id) {

		checkIfRentalCarExists(id);

		RentalCar result = this.rentalCarDao.getById(id);

		GetRentalCarDto response = this.modelMapperService.forDto().map(result, GetRentalCarDto.class);

		return new SuccessDataResult<GetRentalCarDto>(response, BusinessMessages.RENTAL_CAR_GET_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<RentalCarListDto>> getByCarId(int carId) {

		checkIfCarExists(carId);

		List<RentalCar> result = this.rentalCarDao.getByCar_Id(carId);

		List<RentalCarListDto> response = result.stream()
				.map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, RentalCarListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<RentalCarListDto>>(response, BusinessMessages.RENTAL_CAR_LIST_SUCCESSFULLY);
	}

	@Override
	@Transactional
	public DataResult<RentalCar> addForCorporateCustomer(CreateRentalModel rentalModel) {

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

		rentalCar.setOrderedAdditionalServices(mappingOrderedAdditionalService(orderedAdditionalServices, rentalCar));

		rentalCar.setOrderedAdditionalServices(orderedAdditionalServices);

		rentalCar.equals(checkIfAdditionalPrice(rentalCar));
		rentalCar.equals(checkIfOrderedAdditionalServicesExists(rentalCar));

		rentalCar.setTotalRentDay(calculateTotalRentDay(rentalCar.getStartingDate(), rentalCar.getEndDate()));

		rentalCar.setTotalPrice(calculateTotalPrice(rentalCar.getCar().getId(), rentalCar.getTotalRentDay(),
				rentalCar.getAdditionalPrice()));

		rentalCar.setStartingKilometer(carService.getById(rentalCar.getCar().getId()).getData().getKilometer());

		RentalCar savedRentalCar = this.rentalCarDao.save(rentalCar);

		return new SuccessDataResult<RentalCar>(savedRentalCar, BusinessMessages.RENTAL_CAR_SAVE_SUCCESSFULLY);
	}

	public Double calculateTotalPrice(int carId, Long totalRentDay, Double additionalPrice) {

		Double dailyPrice = carService.getById(carId).getData().getDailyPrice();

		return additionalPrice + (dailyPrice * totalRentDay);
	}

	@Override
	@Transactional
	public DataResult<RentalCar> addForIndividualCustomer(CreateRentalModel rentalModel) {

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

		rentalCar.setOrderedAdditionalServices(mappingOrderedAdditionalService(orderedAdditionalServices, rentalCar));

		rentalCar.equals(checkIfAdditionalPrice(rentalCar));
		rentalCar.equals(checkIfOrderedAdditionalServicesExists(rentalCar));

		rentalCar.setTotalRentDay(calculateTotalRentDay(rentalCar.getStartingDate(), rentalCar.getEndDate()));

		rentalCar.setTotalPrice(calculateTotalPrice(rentalCar.getCar().getId(), rentalCar.getTotalRentDay(),
				rentalCar.getAdditionalPrice()));

		rentalCar.setStartingKilometer(carService.getById(rentalCar.getCar().getId()).getData().getKilometer());

		RentalCar savedRentalCar = this.rentalCarDao.save(rentalCar);

		return new SuccessDataResult<RentalCar>(savedRentalCar, BusinessMessages.RENTAL_CAR_SAVE_SUCCESSFULLY);
	}

	private void checkIfIndividualCustomerExists(int id) {
		individualCustomerService.checkIfIndividualCustomerExists(id);
	}

	private void checkIfCorporateCustomerExists(int id) {
		corporateCustomerService.checkIfCorporateCustomerExists(id);
	}

	private List<OrderedAdditionalService> mappingOrderedAdditionalService(
			List<OrderedAdditionalService> orderedAdditionalServices, RentalCar rentalCar) {

		checkIfAdditionalServiceExists(orderedAdditionalServices);

		for (int i = 0; i < orderedAdditionalServices.size(); i++) {
			orderedAdditionalServices.get(i).setId(0);
			orderedAdditionalServices.get(i).setRentalCar(rentalCar);
		}

		return orderedAdditionalServices;
	}

	private void checkIfAdditionalServiceExists(List<OrderedAdditionalService> orderedAdditionalServices) {

		for (OrderedAdditionalService orderedAdditionalService : orderedAdditionalServices) {

			additionalServiceService
					.checkIfAdditionalServiceExists(orderedAdditionalService.getAdditionalService().getId());
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
				Double additionalServicePrice = additionalServiceService
						.getById(orderedAdditionalService.getAdditionalService().getId()).getData().getPrice();
				rentalCar.setAdditionalPrice(rentalCar.getAdditionalPrice() + additionalServicePrice);
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

				throw new BusinessException(BusinessMessages.CAR_CANNOT_RENT_BECAUSE_MAINTENANCE);
			}

			if (carMaintenanceDto.getReturnDate() == null) {

				throw new BusinessException(BusinessMessages.RENTAL_CAR_CANNOT_BEACUSE_MAINTENANCE_OR_NULL_DATE);
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
					throw new BusinessException(BusinessMessages.RENTED_ALREADY);
				}
			}

		}

	}

	@Override
	public Result update(UpdateRentalModel updateRentalModel) {

		checkIfRentalCarExists(updateRentalModel.getUpdateRentalCarRequest().getId());

		orderedAdditionalServiceService.deleteByRentalCarId(updateRentalModel.getUpdateRentalCarRequest().getId());

		RentalCar updatedRentalCar = this.modelMapperService.forRequest()
				.map(updateRentalModel.getUpdateRentalCarRequest(), RentalCar.class);

		checkIfInMaintenance(updatedRentalCar);
		checkIfInRent(updatedRentalCar);

		List<OrderedAdditionalService> orderedAdditionalServices = updateRentalModel
				.getOrderedAdditionalServiceRequests().stream().map(additionalService -> this.modelMapperService
						.forRequest().map(additionalService, OrderedAdditionalService.class))
				.collect(Collectors.toList());

		updatedRentalCar.setOrderedAdditionalServices(
				mappingOrderedAdditionalService(orderedAdditionalServices, updatedRentalCar));

		updatedRentalCar.equals(checkIfAdditionalPrice(updatedRentalCar));

		updatedRentalCar.setOrderedAdditionalServices(orderedAdditionalServices);
		updatedRentalCar.equals(checkIfOrderedAdditionalServicesExists(updatedRentalCar));

		updatedRentalCar.setTotalRentDay(
				calculateTotalRentDay(updatedRentalCar.getStartingDate(), updatedRentalCar.getEndDate()));

		updatedRentalCar.setTotalPrice(calculateTotalPrice(updatedRentalCar.getCar().getId(),
				updatedRentalCar.getTotalRentDay(), updatedRentalCar.getAdditionalPrice()));

		updatedRentalCar
				.setStartingKilometer(carService.getById(updatedRentalCar.getCar().getId()).getData().getKilometer());

		this.rentalCarDao.save(updatedRentalCar);

		return new SuccessResult(BusinessMessages.RENTAL_CAR_UPDATE_SUCCESSFULLY);
	}

	public void checkIfCarExists(int carId) {
		carService.checkIfCarExists(carId);
	}

	@Override
	public Result delete(int id) {

		checkIfRentalCarExists(id);

		this.rentalCarDao.deleteById(id);

		return new SuccessResult(BusinessMessages.RENTAL_CAR_DELETE_SUCCESSFULLY);
	}

	public void checkIfRentalCarExists(int rentalCarId) {

		if (!rentalCarDao.existsById(rentalCarId)) {
			throw new BusinessException(BusinessMessages.RENTAL_CAR_NOT_FOUND);
		}
	}

	public Long calculateTotalRentDay(LocalDate startingDate, LocalDate endDay) {
		return ChronoUnit.DAYS.between(startingDate, endDay) + 1;
	}

	@Override
	public Result endOfRent(EndOfRent endOfRent) {

		checkIfRentalCarExists(endOfRent.getId());

		RentalCar rentalCar = rentalCarDao.getById(endOfRent.getId());

		rentalCar.setEndingKilometer(endOfRent.getEndingKilometer());

		carService.updateKilometer(rentalCar.getCar().getId(), rentalCar.getEndingKilometer());

		rentalCarDao.save(rentalCar);

		List<InvoiceListDto> invoices = invoiceService.getbyRentalCarId(rentalCar.getId()).getData();

		Double totalInvoicePrice = 0.0;

		for (InvoiceListDto invoice : invoices) {
			totalInvoicePrice += invoice.getTotalPrice();
		}
		Double difference = 0.0;
		if (!checkIfRentStarts(rentalCar)) {
			return new ErrorResult(BusinessMessages.RENT_NOT_STARTED);
		}

		if (!checkIfIsRightTime(rentalCar)) {
			difference = calculateTotalPrice(rentalCar.getCar().getId(),
					calculateTotalRentDay(rentalCar.getStartingDate(), LocalDate.now()), rentalCar.getAdditionalPrice())
					- totalInvoicePrice;
		} else {
			difference = calculateTotalPrice(rentalCar.getCar().getId(),
					calculateTotalRentDay(rentalCar.getStartingDate(), rentalCar.getEndDate()),
					rentalCar.getAdditionalPrice()) - totalInvoicePrice;
		}

		if (difference > 0) {

			return new ErrorResult("You should pay extra " + difference.toString() + " liras.");
		}

		if (difference < 0) {

			difference = Math.abs(difference);
			return new ErrorResult("The campany will pay extra " + difference.toString() + " liras to you.");
		}

		return new SuccessResult(BusinessMessages.RENTAL_ENDS_SUCCESSFULLY);

	}

	@Override
	public boolean saveNewRentalCarAfterPayingExtra(RentalCar rentalCar) {
		rentalCarDao.save(rentalCar);

		return true;
	}

	@Override
	public boolean checkIfIsRightTime(RentalCar rentalCar) {

		if (!rentalCar.getEndDate().equals(LocalDate.now())) {
			return false;
		}
		return true;
	}

	public boolean checkIfRentStarts(RentalCar rentalCar) {
		if (rentalCar.getStartingDate().isAfter(LocalDate.now())) {
			return false;
		}
		return true;
	}
}
