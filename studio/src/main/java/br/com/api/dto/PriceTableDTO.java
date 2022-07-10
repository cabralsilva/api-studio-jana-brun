package br.com.api.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PriceTableDTO {

	private Integer identifier;
	private String code;
	private String description;
	private LocalDateTime creationDateTime;
	private LocalDateTime initDateTime;
	private LocalDateTime endDateTime;
	private List<PriceTableItemDTO> itemList;
}
