package br.com.api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.api.enums.StatusActiveEnum;
import br.com.api.enums.TypeOfValueEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class GrateDTO {

	private Integer identifier;
	private String code;
	private String description;
	private TypeOfValueEnum typeOfValue;
	private StatusActiveEnum status;
	private List<GrateItemDTO> itemList;
}
