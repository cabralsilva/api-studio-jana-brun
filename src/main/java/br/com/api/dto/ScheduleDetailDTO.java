package br.com.api.dto;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.api.enums.OftenEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class ScheduleDetailDTO {

	private Integer identifier;
	private LocalDate initDate;
	private LocalTime time;
	private Duration duration;
	private OftenEnum often;
	private ClassDTO cClass;
	private ScheduleDTO schedule;
}
