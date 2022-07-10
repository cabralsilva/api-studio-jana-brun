package br.com.api.dto;

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
public class ScheduleDetailClassDTO {

	private Integer identifier;
	private OftenEnum often;
	private String oftenDay;
	private LocalTime initTime;
	private LocalTime endTime;
	private ClassroomDTO classroom;
	private ClassDTO clazz;
}
