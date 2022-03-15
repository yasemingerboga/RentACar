package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.Invoice.GetInvoiceDto;
import com.turkcell.rentACar.business.dtos.Invoice.InvoiceListDto;
import com.turkcell.rentACar.business.requests.Invoice.CreateInvoiceRequest;
import com.turkcell.rentACar.business.requests.Invoice.UpdateInvoiceRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface InvoiceService {
	
	DataResult<List<InvoiceListDto>> getAll();

	Result add(CreateInvoiceRequest createBrandRequest);

	DataResult<GetInvoiceDto> getById(int id);

	Result delete(int id);

	Result update(UpdateInvoiceRequest updateBrandRequest);
}
