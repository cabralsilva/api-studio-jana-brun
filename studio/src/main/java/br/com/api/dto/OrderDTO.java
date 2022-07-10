package br.com.api.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.api.enums.OrderStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class OrderDTO {

	private Integer identifier;
	private PersonDTO customer;
	private LocalDateTime creationDateTime;
    private List<OrderItemDTO> orderItemList;
	private OrderStatusEnum status;
	private String observation;
	private List<BillToReceiveDTO> billToReceiveList;

}
