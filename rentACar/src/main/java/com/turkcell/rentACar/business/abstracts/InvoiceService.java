package com.turkcell.rentACar.business.abstracts;

import java.time.LocalDate;
import java.util.List;

import com.turkcell.rentACar.business.dtos.Invoice.GetInvoiceDto;
import com.turkcell.rentACar.business.dtos.Invoice.InvoiceListDto;
import com.turkcell.rentACar.business.requests.Invoice.CreateInvoiceRequest;
import com.turkcell.rentACar.business.requests.Invoice.UpdateInvoiceRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.concretes.Invoice;

public interface InvoiceService {

	DataResult<List<InvoiceListDto>> getAll();

	DataResult<Invoice> add(CreateInvoiceRequest createInvoiceRequest);

	DataResult<GetInvoiceDto> getById(int id);

	Result delete(int id);

	Result update(UpdateInvoiceRequest updateInvoiceRequest);

	DataResult<List<InvoiceListDto>> getByCustomerId(int id);

	DataResult<List<InvoiceListDto>> getAllByBetweenStartDateAndEndDate(LocalDate startDate, LocalDate endDate);

	DataResult<List<InvoiceListDto>> getbyRentalCarId(int id);
}
