package br.com.api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.api.entity.Grate;
import br.com.api.enums.StatusActiveEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class ProductDTO {

	private Integer identifier;
	private String code;
	private String description;
	private Double unitPrice;
	private StatusActiveEnum status;
	private List<GrateDTO> grateList;
}
