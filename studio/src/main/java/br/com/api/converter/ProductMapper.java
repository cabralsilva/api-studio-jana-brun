package br.com.api.converter;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import br.com.api.dto.GrateDTO;
import br.com.api.dto.ProductDTO;
import br.com.api.entity.Grate;
import br.com.api.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

	public Product toEntity(ProductDTO dto);

	@Mapping(source="grateList", target = "grateList", qualifiedByName = "toGrateListDTO")
	public ProductDTO toDTO(Product entity);
	
	@Named("toGrateListDTO")
    public default List<GrateDTO> toGrateListDTO(List<Grate> list) {
		if ( list == null ) {
            return null;
        }

        List<GrateDTO> list1 = new ArrayList<GrateDTO>( list.size() );
        for ( Grate grateDTO : list ) {
            list1.add( grateToGrateDTO( grateDTO ) );
        }

        return list1;
    }
	
	
	public default GrateDTO grateToGrateDTO(Grate grate) {
        if ( grate == null ) {
            return null;
        }

        GrateDTO grateDTO = new GrateDTO();

        grateDTO.setIdentifier( grate.getIdentifier() );
        grateDTO.setCode( grate.getCode() );
        grateDTO.setDescription( grate.getDescription() );
        grateDTO.setTypeOfValue( grate.getTypeOfValue() );
        grateDTO.setStatus( grate.getStatus() );

        return grateDTO;
    }
}
