package br.com.api.utils;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public interface Utils {

	public static Locale getLocale(List<Locale> localesList) {
		Locale locale = Locale.US;
		if (Objects.isNull(localesList) || localesList.isEmpty())
			return locale;

		return localesList.stream().findFirst().orElse(locale);
	}
}
