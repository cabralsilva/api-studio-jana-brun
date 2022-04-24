package br.com.api.utils;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.api.enums.StatusResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@Builder
public class ResponseAPI<T> implements Serializable {

	private static final long serialVersionUID = -4521521671669354355L;

	private StatusResponse status;
	private List<String> friendlyMessagesList;
	private T data;
	private ReportTech reportTech;

}