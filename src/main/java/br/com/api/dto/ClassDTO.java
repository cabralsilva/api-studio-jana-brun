package br.com.api.dto;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.api.entity.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class ClassDTO {

	private Integer identifier;
	private String code;
	private String description;
	private String inviteWhatsAppGroup;
	private List<ScheduleDetailClassDTO> scheduleDetailClassList;
	private List<RolePaymentDTO> rolePaymentList;
	private Set<Student> studentList;
	private Calendar creationDate;
	private Calendar startDate;
	private Calendar endDate;
	private ProductDTO product;
}
