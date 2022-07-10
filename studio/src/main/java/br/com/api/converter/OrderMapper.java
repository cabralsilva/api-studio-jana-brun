package br.com.api.converter;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import br.com.api.dto.GrateDTO;
import br.com.api.dto.GrateDTO.GrateDTOBuilder;
import br.com.api.dto.GrateItemDTO;
import br.com.api.dto.GrateItemDTO.GrateItemDTOBuilder;
import br.com.api.dto.OrderDTO;
import br.com.api.dto.OrderItemDTO;
import br.com.api.dto.OrderItemDTO.OrderItemDTOBuilder;
import br.com.api.dto.ProductDTO;
import br.com.api.dto.ProductDTO.ProductDTOBuilder;
import br.com.api.entity.Grate;
import br.com.api.entity.GrateItem;
import br.com.api.entity.Order;
import br.com.api.entity.OrderItem;
import br.com.api.entity.Product;

@Mapper(componentModel = "spring")
public interface OrderMapper {

	public Order toEntity(OrderDTO dto);

	@Mapping(source = "orderItemList", target = "orderItemList", qualifiedByName = "toOrderItemListDTO")
	public OrderDTO toDTO(Order source);

	@Named("toOrderItemListDTO")
	@IterableMapping(qualifiedByName = "toOrderItemDTO")
	public List<OrderItemDTO> toOrderItemListDTO(List<OrderItem> source);
	
	@Named("toGrateItemListDTO")
	@IterableMapping(qualifiedByName = "toGrateItemDTO")
	public List<GrateItemDTO> toGrateItemListDTO(List<GrateItem> source);

	@Named("toOrderItemDTO")
	@Mapping(source = "product", target = "product", qualifiedByName = "toProductDTO")
	@Mapping(source = "grateItemList", target = "grateItemList", qualifiedByName = "toGrateItemListDTO")
	public default OrderItemDTO orderItemToOrderItemDTO(OrderItem source) {
		if (source == null) {
			return null;
		}

		OrderItemDTOBuilder<?, ?> target = OrderItemDTO.builder();

		target.identifier(source.getIdentifier());
		target.unitPrice(source.getUnitPrice());
		target.quantity(source.getQuantity());
		target.totalPrice(source.getTotalPrice());

		return target.build();
	}

	@Named("toProductDTO")
	public default ProductDTO toProductDTO(Product source) {
		if (source == null) {
			return null;
		}

		ProductDTOBuilder<?, ?> target = ProductDTO.builder();

		target.identifier(source.getIdentifier());
		target.code(source.getCode());
		target.description(source.getDescription());
		target.unitPrice(source.getUnitPrice());
		target.status(source.getStatus());

		return target.build();
	}
	
	@Named("toGrateItemDTO")
	@Mapping(source = "grate", target = "grate", qualifiedByName = "toGrateDTO")
	public default GrateItemDTO toGrateItemDTO(GrateItem source) {
		if ( source == null ) {
            return null;
        }

        GrateItemDTOBuilder<?, ?> target = GrateItemDTO.builder();

        target.identifier( source.getIdentifier() );
        target.value( source.getValue() );

        return target.build();
	}
	
	@Named("toGrateDTO")
	@Mapping(target = "itemList", ignore = true)
	public default GrateDTO toGrateDTO(Grate source) {
        if ( source == null ) {
            return null;
        }

        GrateDTOBuilder<?, ?> target = GrateDTO.builder();

        target.identifier( source.getIdentifier() );
        target.code( source.getCode() );
        target.description( source.getDescription() );
        target.typeOfValue( source.getTypeOfValue() );
        target.status( source.getStatus() );

        return target.build();
    }
}
