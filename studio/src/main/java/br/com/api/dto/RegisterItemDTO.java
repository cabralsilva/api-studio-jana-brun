package br.com.api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class RegisterItemDTO {

	private Integer identifier;
	private RegisterDTO register;
	private ProductDTO product;
    private List<GrateItemDTO> grateItemList;
	private Double unitPrice;
	private Double quantity;
	private Double totalPrice;
	private Double additionPercent;
	private Double finalPrice;
}
