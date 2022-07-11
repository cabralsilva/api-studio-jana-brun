package br.com.api.converter;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import br.com.api.dto.ClassDTO;
import br.com.api.dto.GrateDTO;
import br.com.api.dto.GrateDTO.GrateDTOBuilder;
import br.com.api.dto.GrateItemDTO;
import br.com.api.dto.GrateItemDTO.GrateItemDTOBuilder;
import br.com.api.dto.MatriculationDTO;
import br.com.api.dto.MatriculationItemDTO;
import br.com.api.dto.MatriculationItemDTO.MatriculationItemDTOBuilder;
import br.com.api.dto.ProductDTO;
import br.com.api.dto.ProductDTO.ProductDTOBuilder;
import br.com.api.entity.Grate;
import br.com.api.entity.GrateItem;
import br.com.api.entity.Matriculation;
import br.com.api.entity.MatriculationItem;
import br.com.api.entity.Product;

@Mapper(componentModel = "spring")
public interface MatriculationMapper {

	public Matriculation toEntity(MatriculationDTO dto);

	@Mapping(source = "matriculationItemList", target = "matriculationItemList", qualifiedByName = "toMatriculationItemDTOList")
	@Mapping(source = "classList", target = "classList", qualifiedByName = "toClassListDTO")
	public MatriculationDTO toDTO(Matriculation entity);

	@Named("toClassListDTO")
	@IterableMapping(qualifiedByName = "toClassDTO")
	public List<ClassDTO> toClassListDTO(List<br.com.api.entity.Class> source);

	@Named("toClassDTO")
	public default ClassDTO toClassDTO(br.com.api.entity.Class source) {
		if (source == null) {
			return null;
		}

		ClassDTO target = ClassDTO.builder().build();

		target.setIdentifier(source.getIdentifier());
		target.setCode(source.getCode());
		target.setDescription(source.getDescription());

		return target;
	}

	@Named("toMatriculationItemDTOList")
	public default List<MatriculationItemDTO> matriculationItemListToMatriculationItemDTOList(
			List<MatriculationItem> list) {
		if (list == null) {
			return null;
		}

		List<MatriculationItemDTO> list1 = new ArrayList<MatriculationItemDTO>(list.size());
		for (MatriculationItem matriculationItem : list) {
			list1.add(matriculationItemToMatriculationItemDTO(matriculationItem));
		}

		return list1;
	}

	public default MatriculationItemDTO matriculationItemToMatriculationItemDTO(MatriculationItem matriculationItem) {
		if (matriculationItem == null) {
			return null;
		}

		MatriculationItemDTOBuilder<?, ?> matriculationItemDTO = MatriculationItemDTO.builder();

		matriculationItemDTO.identifier(matriculationItem.getIdentifier());
		matriculationItemDTO.product(productToProductDTO(matriculationItem.getProduct()));
		matriculationItemDTO.grateItemList(grateItemListToGrateItemDTOList(matriculationItem.getGrateItemList()));
		matriculationItemDTO.unitPrice(matriculationItem.getUnitPrice());
		matriculationItemDTO.quantity(matriculationItem.getQuantity());
		matriculationItemDTO.totalPrice(matriculationItem.getTotalPrice());
		matriculationItemDTO.additionPercent(matriculationItem.getAdditionPercent());
		matriculationItemDTO.finalPrice(matriculationItem.getFinalPrice());

		return matriculationItemDTO.build();
	}

	public default List<GrateItemDTO> grateItemListToGrateItemDTOList(List<GrateItem> list) {
		if (list == null) {
			return null;
		}

		List<GrateItemDTO> list1 = new ArrayList<GrateItemDTO>(list.size());
		for (GrateItem grateItem : list) {
			list1.add(grateItemToGrateItemDTO(grateItem));
		}

		return list1;
	}

	public default GrateItemDTO grateItemToGrateItemDTO(GrateItem grateItem) {
		if (grateItem == null) {
			return null;
		}

		GrateItemDTOBuilder<?, ?> grateItemDTO = GrateItemDTO.builder();

		grateItemDTO.identifier(grateItem.getIdentifier());
		grateItemDTO.value(grateItem.getValue());
		grateItemDTO.grate(grateToGrateDTO(grateItem.getGrate()));

		return grateItemDTO.build();
	}

	public default GrateDTO grateToGrateDTO(Grate grate) {
		if (grate == null) {
			return null;
		}

		GrateDTOBuilder<?, ?> grateDTO = GrateDTO.builder();

		grateDTO.identifier(grate.getIdentifier());
		grateDTO.code(grate.getCode());
		grateDTO.description(grate.getDescription());
		grateDTO.typeOfValue(grate.getTypeOfValue());
		grateDTO.status(grate.getStatus());
//        grateDTO.itemList( grateItemListToGrateItemDTOList( grate.getItemList() ) );

		return grateDTO.build();
	}

	public default ProductDTO productToProductDTO(Product product) {
		if (product == null) {
			return null;
		}

		ProductDTOBuilder<?, ?> productDTO = ProductDTO.builder();

		productDTO.identifier(product.getIdentifier());
		productDTO.code(product.getCode());
		productDTO.category(product.getCategory());
		productDTO.description(product.getDescription());
		productDTO.unitPrice(product.getUnitPrice());
		productDTO.status(product.getStatus());

		return productDTO.build();
	}
}
