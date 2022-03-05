package br.com.api.dto;

import br.com.api.enums.SchoolLevelEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDTO {

	private Integer identifier;
	private IndividualDTO schoolChild;
	private IndividualDTO responsible;
	private IndividualDTO school;
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
