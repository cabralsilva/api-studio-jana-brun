package br.com.api.entity.repository;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.api.dto.BillToReceiveInstallmentDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class BillToReceiveInstallmentFilter extends AbstractFilter<BillToReceiveInstallmentDTO> {

	private static final long serialVersionUID = 1972999281351295270L;

}
