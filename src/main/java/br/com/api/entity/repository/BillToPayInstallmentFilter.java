package br.com.api.entity.repository;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.api.dto.BillToPayInstallmentDTO;
import br.com.api.dto.PersonDTO;
import br.com.api.enums.InstallmentStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class BillToPayInstallmentFilter extends AbstractFilter<BillToPayInstallmentDTO> {

	private static final long serialVersionUID = 1972999281351295270L;
	
	private List<InstallmentStatusEnum> statusInList;
	private List<PersonDTO> personInList;
	private LocalDate initTargetDate;
	private LocalDate endTargetDate;
}
