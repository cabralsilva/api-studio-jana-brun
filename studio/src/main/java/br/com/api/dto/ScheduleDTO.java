package br.com.api.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.api.enums.StatusActiveEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class ScheduleDTO {

	private Integer identifier;
	private LocalDate initialDate;
	private LocalDate endDate;
	private StatusActiveEnum status;
	private ClassroomDTO classroom;
}
