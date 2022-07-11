package br.com.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TypeOfSupplierEnum {

	MERCHANDISE("label.merchandise.supplier"), 
	ELETRICITY("label.eletricity.supplier"), 
	WATER("label.water.supplier"), 
	CARRYING("label.carrying.supplier"), 
	RENT("label.rent"), 
	OTHER("label.other");


	private final String i18n;
}
