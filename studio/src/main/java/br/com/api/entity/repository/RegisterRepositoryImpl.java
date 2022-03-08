package br.com.api.entity.repository;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.api.entity.Register;

@Repository
public class RegisterRepositoryImpl extends RepositoryCustom {

	@PersistenceContext
	protected EntityManager entityManager;

	public Predicate filters(RegisterFilter filter, CriteriaBuilder criteriaBuilder, Root<Register> entity) {

		Predicate predicate = criteriaBuilder.conjunction();

		/* filter.getExample() */

		if (Objects.nonNull(filter.getExample())) {

			if (Objects.nonNull(filter.getExample().getIdentifier())) {
				predicate = criteriaBuilder.equal(entity.get("identifier"), filter.getExample().getIdentifier());
			}
		}

		/* end filter.getExample() */

		/* other filters */

		if (StringUtils.isNotEmpty(filter.getGlobalFilter())) {
			Predicate name = criteriaBuilder.like(entity.get("name"), "%" + filter.getGlobalFilter() + "%");

			predicate = criteriaBuilder.and(predicate, criteriaBuilder.or(name));

		}

		/* end other filters */

		return predicate;
	}

	public Long count(RegisterFilter filter) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> counterCq = criteriaBuilder.createQuery(Long.class);
		Root<Register> root = counterCq.from(Register.class);

		counterCq.multiselect(criteriaBuilder.countDistinct(root));
		counterCq.where(this.filters(filter, criteriaBuilder, root));
		return entityManager.createQuery(counterCq).getSingleResult();
	}

	public Page<Register> findByFilter(RegisterFilter filter, Pageable page)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException {

		super.findByFilter(Register.class, filter);

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteriaQueryTuple = criteriaBuilder.createQuery(Tuple.class);
		Root<Register> root = criteriaQueryTuple.from(Register.class);

		Long total = count(filter);

		Predicate conditions = this.filters(filter, criteriaBuilder, root);
		List<Selection<?>> selectionsList = super.selections(this, Register.class, filter.getColumnList(),
				"register.", root, criteriaQueryTuple, criteriaBuilder);

		criteriaQueryTuple.multiselect(selectionsList);
		criteriaQueryTuple.where(conditions);

		criteriaQueryTuple.orderBy(this.orderBy(filter, root, Register.class, criteriaBuilder));

		TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteriaQueryTuple);
		int iPage = filter.getCurrentPage();
		int iPageSize = filter.getSizePage();

		typedQuery.setFirstResult(iPage * iPageSize);
		typedQuery.setMaxResults(iPageSize);

		List<Register> rootList = new ArrayList<>();
		for (Tuple tuple : typedQuery.getResultList()) {
			rootList.add(
					(Register) super.generateEntity(Register.class, filter.getColumnList(), "register.", tuple));
		}
		return new PageImpl<>(rootList, page, total);
	}

	public List<Register> findByFilter(RegisterFilter filter) throws NoSuchMethodException, SecurityException,
			IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {

		super.findByFilter(Register.class, filter);

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteriaQueryTuple = criteriaBuilder.createQuery(Tuple.class);
		Root<Register> root = criteriaQueryTuple.from(Register.class);
		Predicate conditions = this.filters(filter, criteriaBuilder, root);

		List<Selection<?>> selectionsList = super.selections(this, Register.class, filter.getColumnList(),
				"register.", root, criteriaQueryTuple, criteriaBuilder);

		criteriaQueryTuple.multiselect(selectionsList);
		criteriaQueryTuple.where(conditions);

		criteriaQueryTuple.orderBy(this.orderBy(filter, root, Register.class, criteriaBuilder));

		TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteriaQueryTuple);

		List<Register> rootList = new ArrayList<>();
		for (Tuple tuple : typedQuery.getResultList()) {
			rootList.add(
					(Register) super.generateEntity(Register.class, filter.getColumnList(), "register.", tuple));
		}
		return rootList;
	}

}
