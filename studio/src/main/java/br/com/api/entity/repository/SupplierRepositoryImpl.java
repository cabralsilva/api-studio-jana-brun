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

import br.com.api.entity.Supplier;

@Repository
public class SupplierRepositoryImpl extends RepositoryCustom {

	@PersistenceContext
	protected EntityManager entityManager;

	public Predicate filters(SupplierFilter filter, CriteriaBuilder criteriaBuilder, Root<Supplier> entity) {

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

	public Long count(SupplierFilter filter) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> counterCq = criteriaBuilder.createQuery(Long.class);
		Root<Supplier> root = counterCq.from(Supplier.class);

		counterCq.multiselect(criteriaBuilder.countDistinct(root));
		counterCq.where(this.filters(filter, criteriaBuilder, root));
		return entityManager.createQuery(counterCq).getSingleResult();
	}

	public Page<Supplier> findByFilter(SupplierFilter filter, Pageable page)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException {

		super.findByFilter(Supplier.class, filter);

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteriaQueryTuple = criteriaBuilder.createQuery(Tuple.class);
		Root<Supplier> root = criteriaQueryTuple.from(Supplier.class);

		Long total = count(filter);

		Predicate conditions = this.filters(filter, criteriaBuilder, root);
		List<Selection<?>> selectionsList = super.selections(this, Supplier.class, filter.getColumnList(),
				"supplier.", root, criteriaQueryTuple, criteriaBuilder);

		criteriaQueryTuple.multiselect(selectionsList);
		criteriaQueryTuple.where(conditions);

		criteriaQueryTuple.orderBy(this.orderBy(filter, root, Supplier.class, criteriaBuilder));

		TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteriaQueryTuple);
		int iPage = filter.getCurrentPage();
		int iPageSize = filter.getSizePage();

		typedQuery.setFirstResult(iPage * iPageSize);
		typedQuery.setMaxResults(iPageSize);

		List<Supplier> rootList = new ArrayList<>();
		for (Tuple tuple : typedQuery.getResultList()) {
			rootList.add(
					(Supplier) super.generateEntity(Supplier.class, filter.getColumnList(), "supplier.", tuple));
		}
		return new PageImpl<>(rootList, page, total);
	}

	public List<Supplier> findByFilter(SupplierFilter filter) throws NoSuchMethodException, SecurityException,
			IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {

		super.findByFilter(Supplier.class, filter);

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteriaQueryTuple = criteriaBuilder.createQuery(Tuple.class);
		Root<Supplier> root = criteriaQueryTuple.from(Supplier.class);
		Predicate conditions = this.filters(filter, criteriaBuilder, root);

		List<Selection<?>> selectionsList = super.selections(this, Supplier.class, filter.getColumnList(),
				"supplier.", root, criteriaQueryTuple, criteriaBuilder);

		criteriaQueryTuple.multiselect(selectionsList);
		criteriaQueryTuple.where(conditions);

		criteriaQueryTuple.orderBy(this.orderBy(filter, root, Supplier.class, criteriaBuilder));

		TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteriaQueryTuple);

		List<Supplier> rootList = new ArrayList<>();
		for (Tuple tuple : typedQuery.getResultList()) {
			rootList.add(
					(Supplier) super.generateEntity(Supplier.class, filter.getColumnList(), "supplier.", tuple));
		}
		return rootList;
	}

}
