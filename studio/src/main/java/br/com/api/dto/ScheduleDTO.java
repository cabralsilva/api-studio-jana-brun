package br.com.api.dto;

import java.time.LocalDate;

import br.com.api.enums.StatusActiveEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleDTO {

	private Integer identifier;
	private LocalDate initialDate;
	private LocalDate endDate;
	private StatusActiveEnum status;
	private ClassroomDTO classroom;
}
