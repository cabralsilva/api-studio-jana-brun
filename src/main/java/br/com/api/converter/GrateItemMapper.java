package br.com.api.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import br.com.api.dto.GrateDTO;
import br.com.api.dto.GrateItemDTO;
import br.com.api.entity.Grate;
import br.com.api.entity.GrateItem;

@Mapper(componentModel = "spring")
public interface GrateItemMapper {

	public GrateItem toEntity(GrateItemDTO dto);

	@Mapping(source="grate", target = "grate", qualifiedByName = "toGrateDTO")
	public GrateItemDTO toDTO(GrateItem entity);
	

	@Named("toGrateDTO")
    public default GrateDTO toGrateDTO(Grate grate) {
		if ( grate == null ) {
            return null;
        }

        GrateDTO grateDTO = new GrateDTO();
        grateDTO.setIdentifier(grate.getIdentifier());
        grateDTO.setDescription(grate.getDescription());

        return grateDTO;
    }
}
