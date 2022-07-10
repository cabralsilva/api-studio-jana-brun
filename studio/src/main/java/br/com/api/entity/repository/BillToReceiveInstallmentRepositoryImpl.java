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
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.api.dto.PersonDTO;
import br.com.api.entity.BillToReceiveInstallment;

@Repository
public class BillToReceiveInstallmentRepositoryImpl extends RepositoryCustom {

	@PersistenceContext
	protected EntityManager entityManager;

	public Predicate filters(BillToReceiveInstallmentFilter filter, CriteriaBuilder criteriaBuilder, Root<BillToReceiveInstallment> entity) {

		Predicate predicate = criteriaBuilder.conjunction();

		Join<?, ?> billToReceiveJoin = null;
		Join<?, ?> personJoin = null;
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
		
		if (Objects.nonNull(filter.getStatusInList()) && !filter.getStatusInList().isEmpty()) {
			predicate = criteriaBuilder.and(predicate,
					criteriaBuilder.or(entity.get("status").in(filter.getStatusInList())));
		}

		if (Objects.nonNull(filter.getPersonInList()) && !filter.getPersonInList().isEmpty()) {
			billToReceiveJoin = super.setJoin(entity, "billToReceive", JoinType.LEFT);
			personJoin = super.setJoin(billToReceiveJoin, "customer", JoinType.LEFT);

			predicate = criteriaBuilder.and(predicate, criteriaBuilder.or(
					personJoin.get("identifier").in(filter.getPersonInList().stream().map(PersonDTO::getIdentifier).toArray())));
		}

		if (filter.getInitTargetDate() != null) {
			predicate = criteriaBuilder.and(predicate,
					criteriaBuilder.greaterThanOrEqualTo(entity.get("targetDate"), filter.getInitTargetDate()));
		}

		if (filter.getEndTargetDate() != null) {
			predicate = criteriaBuilder.and(predicate,
					criteriaBuilder.lessThanOrEqualTo(entity.get("targetDate"), filter.getEndTargetDate()));
		}

		/* end other filters */

		return predicate;
	}

	public Long count(BillToReceiveInstallmentFilter filter) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> counterCq = criteriaBuilder.createQuery(Long.class);
		Root<BillToReceiveInstallment> root = counterCq.from(BillToReceiveInstallment.class);

		counterCq.multiselect(criteriaBuilder.countDistinct(root));
		counterCq.where(this.filters(filter, criteriaBuilder, root));
		return entityManager.createQuery(counterCq).getSingleResult();
	}

	public Page<BillToReceiveInstallment> findByFilter(BillToReceiveInstallmentFilter filter, Pageable page)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException {

		super.findByFilter(BillToReceiveInstallment.class, filter);

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteriaQueryTuple = criteriaBuilder.createQuery(Tuple.class);
		Root<BillToReceiveInstallment> root = criteriaQueryTuple.from(BillToReceiveInstallment.class);

		Long total = count(filter);

		Predicate conditions = this.filters(filter, criteriaBuilder, root);
		List<Selection<?>> selectionsList = super.selections(this, BillToReceiveInstallment.class, filter.getColumnList(),
				"billToReceiveInstallment.", root, criteriaQueryTuple, criteriaBuilder);

		criteriaQueryTuple.multiselect(selectionsList);
		criteriaQueryTuple.where(conditions);

		criteriaQueryTuple.orderBy(this.orderBy(filter, root, BillToReceiveInstallment.class, criteriaBuilder));

		TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteriaQueryTuple);
		int iPage = filter.getCurrentPage();
		int iPageSize = filter.getSizePage();

		typedQuery.setFirstResult(iPage * iPageSize);
		typedQuery.setMaxResults(iPageSize);

		List<BillToReceiveInstallment> rootList = new ArrayList<>();
		for (Tuple tuple : typedQuery.getResultList()) {
			rootList.add(
					(BillToReceiveInstallment) super.generateEntity(BillToReceiveInstallment.class, filter.getColumnList(), "billToReceiveInstallment.", tuple));
		}
		return new PageImpl<>(rootList, page, total);
	}

	public List<BillToReceiveInstallment> findByFilter(BillToReceiveInstallmentFilter filter) throws NoSuchMethodException, SecurityException,
			IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {

		super.findByFilter(BillToReceiveInstallment.class, filter);

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteriaQueryTuple = criteriaBuilder.createQuery(Tuple.class);
		Root<BillToReceiveInstallment> root = criteriaQueryTuple.from(BillToReceiveInstallment.class);
		Predicate conditions = this.filters(filter, criteriaBuilder, root);

		List<Selection<?>> selectionsList = super.selections(this, BillToReceiveInstallment.class, filter.getColumnList(),
				"billToReceiveInstallment.", root, criteriaQueryTuple, criteriaBuilder);

		criteriaQueryTuple.multiselect(selectionsList);
		criteriaQueryTuple.where(conditions);

		criteriaQueryTuple.orderBy(this.orderBy(filter, root, BillToReceiveInstallment.class, criteriaBuilder));

		TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteriaQueryTuple);

		List<BillToReceiveInstallment> rootList = new ArrayList<>();
		for (Tuple tuple : typedQuery.getResultList()) {
			rootList.add(
					(BillToReceiveInstallment) super.generateEntity(BillToReceiveInstallment.class, filter.getColumnList(), "billToReceiveInstallment.", tuple));
		}
		return rootList;
	}

}
