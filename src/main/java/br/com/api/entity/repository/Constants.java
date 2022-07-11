package br.com.api.entity.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Constants {

	public static final List<Class<?>> PRIMITIVE_CLASSES = Arrays.asList(Integer.class, String.class, Boolean.class,
			byte.class, Double.class, Date.class, LocalDate.class, LocalTime.class, LocalDateTime.class);

}
