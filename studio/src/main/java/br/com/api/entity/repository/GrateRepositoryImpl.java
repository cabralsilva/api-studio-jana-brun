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

import br.com.api.dto.GrateDTO;
import br.com.api.dto.GrateItemDTO;
import br.com.api.entity.Grate;
import br.com.api.entity.GrateItem;

@Repository
public class GrateRepositoryImpl extends RepositoryCustom {

	@PersistenceContext
	protected EntityManager entityManager;

	@Autowired
	private GrateItemRepositoryImpl grateItemRepositoryImpl;

	public Predicate filters(GrateFilter filter, CriteriaBuilder criteriaBuilder, Root<Grate> entity) {

		Predicate predicate = criteriaBuilder.conjunction();

		/* filter.getExample() */

		if (Objects.nonNull(filter.getExample())) {

			if (Objects.nonNull(filter.getExample().getIdentifier())) {
				predicate = criteriaBuilder.equal(entity.get("identifier"), filter.getExample().getIdentifier());
			}

			if (Objects.nonNull(filter.getExample().getDescription())) {
				predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(entity.get("description"),
						"%" + filter.getExample().getDescription() + "%"));
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

	public Long count(GrateFilter filter) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> counterCq = criteriaBuilder.createQuery(Long.class);
		Root<Grate> root = counterCq.from(Grate.class);

		counterCq.multiselect(criteriaBuilder.countDistinct(root));
		counterCq.where(this.filters(filter, criteriaBuilder, root));
		return entityManager.createQuery(counterCq).getSingleResult();
	}

	public Page<Grate> findByFilter(GrateFilter filter, Pageable page) throws NoSuchMethodException, SecurityException,
			IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {

		super.findByFilter(Grate.class, filter);

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteriaQueryTuple = criteriaBuilder.createQuery(Tuple.class);
		Root<Grate> root = criteriaQueryTuple.from(Grate.class);

		Long total = count(filter);

		Predicate conditions = this.filters(filter, criteriaBuilder, root);
		List<Selection<?>> selectionsList = super.selections(this, Grate.class, filter.getColumnList(), "grate.", root,
				criteriaQueryTuple, criteriaBuilder);

		criteriaQueryTuple.multiselect(selectionsList);
		criteriaQueryTuple.where(conditions);

		criteriaQueryTuple.orderBy(this.orderBy(filter, root, Grate.class, criteriaBuilder));

		TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteriaQueryTuple);
		int iPage = filter.getCurrentPage();
		int iPageSize = filter.getSizePage();

		typedQuery.setFirstResult(iPage * iPageSize);
		typedQuery.setMaxResults(iPageSize);

		List<Grate> rootList = new ArrayList<>();
		for (Tuple tuple : typedQuery.getResultList()) {
			rootList.add(this.generateEntity(filter.getColumnList(), "grate.", tuple));
		}
		return new PageImpl<>(rootList, page, total);
	}

	public List<Grate> findByFilter(GrateFilter filter) throws NoSuchMethodException, SecurityException,
			IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {

		super.findByFilter(Grate.class, filter);

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteriaQueryTuple = criteriaBuilder.createQuery(Tuple.class);
		Root<Grate> root = criteriaQueryTuple.from(Grate.class);
		Predicate conditions = this.filters(filter, criteriaBuilder, root);

		List<Selection<?>> selectionsList = super.selections(this, Grate.class, filter.getColumnList(), "grate.", root,
				criteriaQueryTuple, criteriaBuilder);

		criteriaQueryTuple.multiselect(selectionsList);
		criteriaQueryTuple.where(conditions);

		criteriaQueryTuple.orderBy(this.orderBy(filter, root, Grate.class, criteriaBuilder));

		TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteriaQueryTuple);

		List<Grate> rootList = new ArrayList<>();
		for (Tuple tuple : typedQuery.getResultList()) {
			rootList.add(this.generateEntity(filter.getColumnList(), "grate.", tuple));
		}
		return rootList;
	}

	public Grate generateEntity(List<String> propertiesRequired, String propertySource, Tuple tuple)
			throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException,
			IllegalArgumentException, InvocationTargetException {

		Grate entity = (Grate) super.generateEntity(Grate.class, propertiesRequired, propertySource, tuple);

		if (ObjectUtils.isNotEmpty(entity)) {
			getItemList(propertiesRequired, entity);
		}

		return entity;
	}

	private void getItemList(List<String> propertiesRequired, Grate entity)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		final String target = "itemList";
		List<String> itemList = propertiesRequired.stream().filter(p -> p.contains(target)).collect(Collectors.toList());

		if (!itemList.isEmpty()) {
			GrateItemFilter filter = GrateItemFilter.builder().example(GrateItemDTO.builder()
					.grate(GrateDTO.builder().identifier(entity.getIdentifier()).build()).build()).build();

			filter.setColumnList(itemList.stream().filter(p -> p.contains(target + "."))
					.map(p -> p.replace(target + ".", "")).collect(Collectors.toList()));

			List<GrateItem> industryProductImageEntity = grateItemRepositoryImpl.findByFilter(filter);
			Field field = ReflectionUtils.findField(entity.getClass(), target);
			field.setAccessible(true);
			ReflectionUtils.setField(field, entity, industryProductImageEntity);

		}
	}

}
