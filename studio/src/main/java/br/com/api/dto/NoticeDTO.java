package br.com.api.dto;

import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.api.enums.StatusActiveEnum;
import br.com.api.enums.TypeOfNoticeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class NoticeDTO {

	private Integer identifier;
	private String title;
	private String description;
	private Integer level;
	private TypeOfNoticeEnum type;
	private Calendar startDate;
	private Calendar endDate;
	private StatusActiveEnum status;
}
