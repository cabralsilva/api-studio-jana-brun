package br.com.api.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.api.enums.MatriculationStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class MatriculationDTO {

	private Integer identifier;
	private StudentDTO student;
	private PersonDTO responsibleFinancial;
	private LocalDateTime creationDateTime;
	private LocalDateTime effectiveDateTime;
	private Integer dayOfMonthToPayment;
    private List<MatriculationItemDTO> matriculationItemList;
    private List<ClassDTO> classList;
	private MatriculationStatusEnum status;
	private List<BillToReceiveDTO> billToReceiveList;
	private String observation;

}
