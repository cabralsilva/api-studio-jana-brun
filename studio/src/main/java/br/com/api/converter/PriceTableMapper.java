package br.com.api.converter;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import br.com.api.dto.GrateDTO;
import br.com.api.dto.GrateItemDTO;
import br.com.api.dto.GrateItemDTO.GrateItemDTOBuilder;
import br.com.api.dto.PriceTableDTO;
import br.com.api.dto.PriceTableItemDTO;
import br.com.api.dto.ProductDTO;
import br.com.api.entity.Grate;
import br.com.api.entity.GrateItem;
import br.com.api.entity.PriceTable;
import br.com.api.entity.PriceTableItem;
import br.com.api.entity.Product;

@Mapper(componentModel = "spring")
public interface PriceTableMapper {

	public PriceTable toEntity(PriceTableDTO dto);
	
	@Mapping(source="itemList", target = "itemList", qualifiedByName = "toPriceTableItemListDTO")
    public PriceTableDTO toDTO(PriceTable entity);
	
	@Mapping(target = "grateList", ignore = true)
	public ProductDTO productToProductDTO(Product source);
	
	@Mapping(target = "itemList", ignore = true)
	public GrateDTO grateToGrateDTO(Grate source);

	@Named("toPriceTableItemListDTO")
	@IterableMapping(qualifiedByName="toPriceTableItemDTO")
	public List<PriceTableItemDTO> toPriceTableItemListDTO(List<PriceTableItem> source);

	@Named("toGrateItemListDTO")
	@IterableMapping(qualifiedByName="toGrateItemDTO")
	public List<GrateItemDTO> toGrateItemListDTO(List<GrateItem> source);

	@Named("toPriceTableItemDTO")
	public default PriceTableItemDTO toPriceTableItemDTO(PriceTableItem source) {
		if ( source == null ) {
            return null;
        }

        PriceTableItemDTO target = new PriceTableItemDTO();

        target.setIdentifier( source.getIdentifier() );
        target.setProduct( productToProductDTO( source.getProduct() ) );
        target.setPrice( source.getPrice() );
        target.setGrateItemList( toGrateItemListDTO(source.getGrateItemList() ));

        return target;
	}
	
	@Named("toGrateItemDTO")
	public default GrateItemDTO toGrateItemDTO(GrateItem source) {
        if ( source == null ) {
            return null;
        }

        GrateItemDTOBuilder<?, ?> target = GrateItemDTO.builder();

        target.identifier( source.getIdentifier() );
        target.value( source.getValue() );
        target.grate( grateToGrateDTO( source.getGrate() ) );

        return target.build();
    }
}
