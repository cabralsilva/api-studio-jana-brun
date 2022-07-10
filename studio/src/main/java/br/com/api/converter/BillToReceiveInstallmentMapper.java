package br.com.api.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import br.com.api.dto.BillToReceiveInstallmentDTO;
import br.com.api.entity.BillToReceiveInstallment;

@Mapper(componentModel = "spring")
public interface BillToReceiveInstallmentMapper {

	public BillToReceiveInstallment toEntity(BillToReceiveInstallmentDTO dto);

	public BillToReceiveInstallmentDTO toDTO(BillToReceiveInstallment entity);
	
	@Named("toBillToReceiveDTO")
	public default BillToReceiveInstallmentDTO toBillToReceiveDTO(BillToReceiveInstallment source) {
		if ( source == null ) {
            return null;
        }

		BillToReceiveInstallmentDTO target = new BillToReceiveInstallmentDTO();

        target.setIdentifier( source.getIdentifier() );
        
        return target;
	}
}
