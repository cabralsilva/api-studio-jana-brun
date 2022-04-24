package br.com.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TypeOfCurrencyEnum {

	CASH("label.cash"),
	CHECK("label.check"),
	DEBIT_CARD("label.debit.card"),
	CREDIT_CARD("label.credit.card"),
	CREDIT("label.credit"),
	TRADE_NOTE("label.trade.note"),
	BONUS("label.bonus"),
	PIX("label.pix");

	private final String i18n;
}
