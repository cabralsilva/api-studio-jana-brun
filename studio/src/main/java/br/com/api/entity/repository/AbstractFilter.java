package br.com.api.entity.repository;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public abstract class AbstractFilter<L> implements Serializable {

	private static final long serialVersionUID = -7527780382676027451L;
	protected Integer currentPage = 0;
	protected Integer sizePage = 10;
	protected Boolean pageable = true;
	protected Integer total;
	protected Integer totalPages;
	protected Boolean last;
	protected String orderColumn = "identifier";
	protected Boolean asc = false;
	protected Boolean noOrder = false;
	protected String globalFilter;
	private Boolean customSearch = false;

	protected List<String> columnList;

	protected L example;
	protected List<L> result;
	protected Boolean resultUnique = false;

}
