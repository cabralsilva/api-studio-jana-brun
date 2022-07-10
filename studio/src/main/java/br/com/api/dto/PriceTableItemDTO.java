package br.com.api.dto;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "identifier")
public class PriceTableItemDTO {

	private Integer identifier;
	private ProductDTO product;
	private List<GrateItemDTO> grateItemList;
	private Double price;
}
