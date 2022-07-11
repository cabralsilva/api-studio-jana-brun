package br.com.api.entity.repository;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.ReflectionUtils;

import br.com.api.dto.ClassDTO;
import br.com.api.dto.ScheduleDetailClassDTO;
import br.com.api.entity.Class;
import br.com.api.entity.ScheduleDetailClass;

@Repository
public class ClassRepositoryImpl extends RepositoryCustom {

	@PersistenceContext
	protected EntityManager entityManager;

	@Autowired
	private ScheduleDetailClassRepositoryImpl scheduleDetailClassRepositoryImpl;

	public Predicate filters(ClassFilter filter, CriteriaBuilder criteriaBuilder, Root<Class> entity) {

		Predicate predicate = criteriaBuilder.conjunction();

		/* filter.getExample() */

		if (Objects.nonNull(filter.getExample())) {

			if (Objects.nonNull(filter.getExample().getIdentifier())) {
				predicate = criteriaBuilder.equal(entity.get("identifier"), filter.getExample().getIdentifier());
			}

			if (Objects.nonNull(filter.getExample().getDescription())) {
				predicate = criteriaBuilder.and(predicate,
						criteriaBuilder.like(entity.get("description"), "%" + filter.getExample().getDescription() + "%"));
			}
		}

		/* end filter.getExample() */

		/* other filters */

		if (StringUtils.isNotEmpty(filter.getGlobalFilter())) {
			Predicate name = criteriaBuilder.like(entity.get("name"), "%" + filter.getGlobalFilter() + "%");

			predicate = criteriaBuilder.and(predicate, criteriaBuilder.or(name));

		}
		
		if (Objects.nonNull(filter.getGreaterThanEndDate())) {
			predicate = criteriaBuilder.and(predicate,
					criteriaBuilder.greaterThanOrEqualTo(entity.get("endDate"), filter.getGreaterThanEndDate()));
		}

		/* end other filters */

		return predicate;
	}

	public Long count(ClassFilter filter) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> counterCq = criteriaBuilder.createQuery(Long.class);
		Root<Class> root = counterCq.from(Class.class);

		counterCq.multiselect(criteriaBuilder.countDistinct(root));
		counterCq.where(this.filters(filter, criteriaBuilder, root));
		return entityManager.createQuery(counterCq).getSingleResult();
	}

	public Page<Class> findByFilter(ClassFilter filter, Pageable page) throws NoSuchMethodException, SecurityException,
			IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {

		super.findByFilter(Class.class, filter);

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteriaQueryTuple = criteriaBuilder.createQuery(Tuple.class);
		Root<Class> root = criteriaQueryTuple.from(Class.class);

		Long total = count(filter);

		Predicate conditions = this.filters(filter, criteriaBuilder, root);
		List<Selection<?>> selectionsList = super.selections(this, Class.class, filter.getColumnList(), "class.", root,
				criteriaQueryTuple, criteriaBuilder);

		criteriaQueryTuple.multiselect(selectionsList);
		criteriaQueryTuple.where(conditions);

		criteriaQueryTuple.orderBy(this.orderBy(filter, root, Class.class, criteriaBuilder));

		TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteriaQueryTuple);
		int iPage = filter.getCurrentPage();
		int iPageSize = filter.getSizePage();

		typedQuery.setFirstResult(iPage * iPageSize);
		typedQuery.setMaxResults(iPageSize);

		List<Class> rootList = new ArrayList<>();
		for (Tuple tuple : typedQuery.getResultList()) {
			rootList.add(this.generateEntity(filter.getColumnList(), "class.", tuple));
		}
		return new PageImpl<>(rootList, page, total);
	}

	public List<Class> findByFilter(ClassFilter filter) throws NoSuchMethodException, SecurityException,
			IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {

		super.findByFilter(Class.class, filter);

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteriaQueryTuple = criteriaBuilder.createQuery(Tuple.class);
		Root<Class> root = criteriaQueryTuple.from(Class.class);
		Predicate conditions = this.filters(filter, criteriaBuilder, root);

		List<Selection<?>> selectionsList = super.selections(this, Class.class, filter.getColumnList(), "class.", root,
				criteriaQueryTuple, criteriaBuilder);

		criteriaQueryTuple.multiselect(selectionsList);
		criteriaQueryTuple.where(conditions);

		criteriaQueryTuple.orderBy(this.orderBy(filter, root, Class.class, criteriaBuilder));

		TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteriaQueryTuple);

		List<Class> rootList = new ArrayList<>();
		for (Tuple tuple : typedQuery.getResultList()) {
			rootList.add(this.generateEntity(filter.getColumnList(), "class.", tuple));
		}
		return rootList;
	}
	
	public Class generateEntity(List<String> propertiesRequired, String propertySource, Tuple tuple)
			throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException,
			IllegalArgumentException, InvocationTargetException {

		Class entity = (Class) super.generateEntity(Class.class, propertiesRequired, propertySource, tuple);

		if (ObjectUtils.isNotEmpty(entity)) {
			getScheduleDetailClassList(propertiesRequired, entity);
		}

		return entity;
	}

	private void getScheduleDetailClassList(List<String> propertiesRequired, Class entity)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		final String target = "scheduleDetailClassList";
		List<String> itemList = propertiesRequired.stream().filter(p -> p.contains(target)).collect(Collectors.toList());

		if (!itemList.isEmpty()) {
			ScheduleDetailClassFilter filter = ScheduleDetailClassFilter.builder().example(ScheduleDetailClassDTO.builder()
					.clazz(ClassDTO.builder().identifier(entity.getIdentifier()).build()).build()).build();

			filter.setColumnList(itemList.stream().filter(p -> p.contains(target + "."))
					.map(p -> p.replace(target + ".", "")).collect(Collectors.toList()));

			List<ScheduleDetailClass> industryProductImageEntity = scheduleDetailClassRepositoryImpl.findByFilter(filter);
			Field field = ReflectionUtils.findField(entity.getClass(), target);
			field.setAccessible(true);
			ReflectionUtils.setField(field, entity, industryProductImageEntity);

		}
	}

}
