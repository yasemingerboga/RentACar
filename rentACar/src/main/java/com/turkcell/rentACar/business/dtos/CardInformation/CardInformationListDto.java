package com.turkcell.rentACar.business.dtos.CardInformation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardInformationListDto {
	private int id;

	private String cardNo;

	private String cardHolder;

	private String month;

	private String year;

	private String cvv;

	private int userId;
}
