package br.com.api.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import br.com.api.dto.RegisterDTO;
import br.com.api.dto.RegisterItemDTO;
import br.com.api.entity.Register;
import br.com.api.entity.RegisterItem;

@Mapper
public interface RegisterItemMapper {

	public RegisterItem toEntity(RegisterItemDTO dto);

	@Mapping(source = "register", target = "register", qualifiedByName = "entityRegisterToDTOSingle")
	public RegisterItemDTO toDTO(RegisterItem entity);

	@Named("entityRegisterToDTOSingle")
	public static RegisterDTO entityRegisterToDTOSingle(Register input) {
		return RegisterDTO.builder().identifier(input.getIdentifier()).build();
	}

}
