package br.com.api.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import br.com.api.dto.PayrollDTO;
import br.com.api.dto.PayrollDetailDTO;
import br.com.api.entity.Payroll;
import br.com.api.entity.PayrollDetail;

@Mapper(componentModel = "spring")
public interface PayrollDetailMapper {

	public PayrollDetail toEntity(PayrollDetailDTO dto);

	@Mapping(source="payroll", target = "payroll", qualifiedByName = "toPayrollDTO")
	public PayrollDetailDTO toDTO(PayrollDetail entity);
	

	@Named("toPayrollDTO")
    public default PayrollDTO toPayrollDTO(Payroll payroll) {
		if ( payroll == null ) {
            return null;
        }

		PayrollDTO payrollDTO = new PayrollDTO();
        payrollDTO.setIdentifier(payroll.getIdentifier());
        payrollDTO.setDescription(payroll.getDescription());

        return payrollDTO;
    }
}
