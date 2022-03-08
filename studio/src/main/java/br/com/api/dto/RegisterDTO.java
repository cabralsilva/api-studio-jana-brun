package br.com.api.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.api.entity.Product;
import br.com.api.enums.RegisterStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class RegisterDTO {

	private Integer identifier;
	private StudentDTO student;
	private PersonDTO responsibleFinancial;
	private LocalDateTime creationDateTime;
	private LocalDateTime effectiveDateTime;
	private Integer dayOfMonthToPayment;
	private List<Product> productList;
	private RegisterStatusEnum status;

}
