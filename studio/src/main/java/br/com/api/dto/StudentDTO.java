package br.com.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.api.entity.Person;
import br.com.api.enums.SchoolLevelEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class StudentDTO {

	private Integer identifier;
	private Person person;
	private PersonDTO responsible;
	private PersonDTO school;
	private SchoolLevelEnum schoolLevel;
	private Boolean medicinContinuous;
	private String medicinNotes;
	private Boolean allergiesContinuous;
	private String allergiesNotes;
	private String responsibleEmail;
	private String responsiblePhone1;
	private String responsiblePhone2;
	private String responsibleInstagram;
	private String responsibleFacebook;
}
