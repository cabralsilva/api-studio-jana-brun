package br.com.api.utils;

import java.io.Serializable;

import br.com.api.enums.LevelReport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
public class ReportTech {

	private LevelReport level;
	private String code;
	private String message;

	private Serializable exception;
}
