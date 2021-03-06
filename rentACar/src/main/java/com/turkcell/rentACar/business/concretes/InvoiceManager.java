package com.turkcell.rentACar.business.concretes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turkcell.rentACar.business.abstracts.InvoiceService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.Invoice.GetInvoiceDto;
import com.turkcell.rentACar.business.dtos.Invoice.InvoiceListDto;
import com.turkcell.rentACar.business.requests.Invoice.CreateInvoiceRequest;
import com.turkcell.rentACar.business.requests.Invoice.UpdateInvoiceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.InvoiceDao;
import com.turkcell.rentACar.entities.concretes.Invoice;

@Service
public class InvoiceManager implements InvoiceService {
	private InvoiceDao invoiceDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public InvoiceManager(InvoiceDao invoiceDao, ModelMapperService modelMapperService) {

		super();
		this.invoiceDao = invoiceDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<InvoiceListDto>> getAll() {

		List<Invoice> result = this.invoiceDao.findAll();

		List<InvoiceListDto> response = result.stream()
				.map(color -> this.modelMapperService.forDto().map(color, InvoiceListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<InvoiceListDto>>(response, BusinessMessages.INVOICE_LIST_SUCCESSFULLY);
	}

	@Override
	@Transactional
	public DataResult<Invoice> add(CreateInvoiceRequest createInvoiceRequest) {

		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);

		Invoice savedInvoice = this.invoiceDao.save(invoice);

		return new SuccessDataResult<Invoice>(savedInvoice, BusinessMessages.INVOICE_SAVE_SUCCESSFULLY);
	}

	@Override
	public DataResult<GetInvoiceDto> getById(int id) {

		checkIfInvoiceExists(id);

		Invoice invoice = invoiceDao.getById(id);

		GetInvoiceDto response = this.modelMapperService.forDto().map(invoice, GetInvoiceDto.class);

		return new SuccessDataResult<GetInvoiceDto>(response, BusinessMessages.INVOICE_GET_SUCCESSFULLY);
	}

	@Override
	public Result delete(int id) {

		checkIfInvoiceExists(id);

		this.invoiceDao.deleteById(id);

		return new SuccessResult(BusinessMessages.INVOICE_DELETE_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateInvoiceRequest updateInvoiceRequest) {

		checkIfInvoiceExists(updateInvoiceRequest.getId());

		Invoice invoice = this.modelMapperService.forRequest().map(updateInvoiceRequest, Invoice.class);

		this.invoiceDao.save(invoice);

		return new SuccessResult(BusinessMessages.INVOICE_UPDATE_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<InvoiceListDto>> getByCustomerId(int id) {

		List<Invoice> result = invoiceDao.getByCustomer_Id(id);

		List<InvoiceListDto> response = result.stream()
				.map(color -> this.modelMapperService.forDto().map(color, InvoiceListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<InvoiceListDto>>(response, BusinessMessages.INVOICE_LIST_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<InvoiceListDto>> getAllByBetweenStartDateAndEndDate(LocalDate startDate, LocalDate endDate) {

		List<Invoice> result = this.invoiceDao.getAllByBetweenStartDateAndEndDate(startDate, endDate);
		List<InvoiceListDto> response = result.stream()
				.map(invoice -> modelMapperService.forDto().map(invoice, InvoiceListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<InvoiceListDto>>(response, BusinessMessages.INVOICE_LIST_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<InvoiceListDto>> getbyRentalCarId(int id) {

		List<Invoice> result = invoiceDao.getByRentalCar_Id(id);
		List<InvoiceListDto> response = result.stream()
				.map(color -> this.modelMapperService.forDto().map(color, InvoiceListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<InvoiceListDto>>(response, BusinessMessages.INVOICE_LIST_SUCCESSFULLY);
	}

	private void checkIfInvoiceExists(int id) {

		if (!invoiceDao.existsById(id)) {
			throw new BusinessException(BusinessMessages.INVOICE_NOT_FOUND);
		}
	}

}
