package br.com.api.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface Utils {

	public static final int TIME_IN_MILLISECONDS_30_MIN = 1800000;

	public static Locale getLocale(List<Locale> localesList) {
		Locale locale = Locale.US;
		if (Objects.isNull(localesList) || localesList.isEmpty())
			return locale;

		return localesList.stream().findFirst().orElse(locale);
	}

	public static Double getRounded(Double value, int place, RoundingMode roundingMode) {
		if (place < 0)
			place = 0;

		BigDecimal bdValue = BigDecimal.valueOf(value);
		bdValue = bdValue.setScale(place, roundingMode);

		return bdValue.doubleValue();
	}

	public static String toStringJSON(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(object);
			System.out.println(json);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	public static String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		return getStrJWT(bearerToken);
	}

	public static String getStrJWT(String strAuthorization) {
		if (StringUtils.isNotEmpty(strAuthorization)) {
			strAuthorization = strAuthorization.replace("%20", " ");
			if (strAuthorization.startsWith("Bearer ")) {
				return strAuthorization.substring(7, strAuthorization.length());
			}
		}
		return null;
	}

	public static List<LocalDate> between(LocalDate initDate2, LocalDate endDate2) {
		DateRange dateRange = new DateRange(initDate2, endDate2, 1);
		
		return dateRange.toList();
	}

	public class DateRange implements Iterable<LocalDate> {

		private final LocalDate startDate;
		private final LocalDate endDate;
		private int step = 1;

		public DateRange(LocalDate startDate, LocalDate endDate, int step) {
			// check that range is valid (null, start < end)
			this.startDate = startDate;
			this.endDate = endDate;
			this.step = step;
		}

		@Override
		public Iterator<LocalDate> iterator() {
			return stream().iterator();
		}

		public Stream<LocalDate> stream() {
			return Stream.iterate(startDate, d -> d.plusDays(this.step)).limit(ChronoUnit.DAYS.between(startDate, endDate) + 1);
		}

		public List<LocalDate> toList() { // could also be built from the stream() method
			List<LocalDate> dates = new ArrayList<>();
			for (LocalDate d = startDate; !d.isAfter(endDate); d = d.plusDays(1)) {
				dates.add(d);
			}
			return dates;
		}

	}
}
