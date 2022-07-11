package br.com.api.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import br.com.api.dto.BillToReceiveDTO;
import br.com.api.dto.MatriculationDTO;
import br.com.api.entity.BillToReceive;
import br.com.api.entity.Matriculation;

@Mapper(componentModel = "spring")
public interface BillToReceiveMapper {

	public BillToReceive toEntity(BillToReceiveDTO dto);

	@Mapping(source = "matriculation", target = "matriculation", qualifiedByName = "toMatriculationDTO")
	public BillToReceiveDTO toDTO(BillToReceive entity);

	@Named("toMatriculationDTO")
	public default MatriculationDTO toMatriculationDTO(Matriculation source) {
		if (source == null) {
			return null;
		}

		MatriculationDTO target = new MatriculationDTO();
		target.setIdentifier(source.getIdentifier());
		return target;
	}
}
