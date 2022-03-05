package br.com.api.dto;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import br.com.api.enums.OftenEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleDetailDTO {

	private Integer identifier;
	private LocalDate initDate;
	private LocalTime time;
	private Duration duration;
	private OftenEnum often;
	private ClassDTO cClass;
	private ScheduleDTO schedule;
}
