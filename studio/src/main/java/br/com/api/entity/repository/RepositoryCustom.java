package br.com.api.entity.repository;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.ReflectionUtils;

import lombok.NonNull;

@Repository
public class RepositoryCustom {

	@Autowired
	private BeanFactory beanFactory;

	public void findByFilter(Class<?> clazz, @NonNull AbstractFilter<?> filter) {

		if (Objects.isNull(filter.getColumnList()) || filter.getColumnList().isEmpty()) {
			List<Field> fieldList = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
			fieldList.removeIf(i -> !Constants.PRIMITIVE_CLASSES.contains(i.getType()) && !i.getType().isEnum());
			filter.setColumnList(fieldList.stream().map(Field::getName).collect(Collectors.toList()));
		}
	}

	public Join<?, ?> setJoin(Root<?> entity, String alias, JoinType type) {

		if (entity.getJoins().stream().noneMatch(j -> j.getAlias().equals(alias))) {
			return (Join<?, ?>) entity.join(alias, type).alias(alias);
		}

		return entity.getJoins().stream().filter(j -> j.getAlias().equals(alias)).findFirst().get();
	}

	public Join<?, ?> setJoin(Join<?, ?> entity, String alias, JoinType type) {

		if (!entity.getJoins().stream().anyMatch(j -> j.getAlias().equals(alias))) {
			return (Join<?, ?>) entity.join(alias, type).alias(alias);
		}

		return entity.getJoins().stream().filter(j -> j.getAlias().equals(alias)).findFirst().get();
	}

	public Optional<Join<?, ?>> getJoin(Root<?> root, String alias) {

		Optional<?> join = root.getJoins().stream().filter(j -> j.getAlias().equals(alias)).findAny();

		return (Optional<Join<?, ?>>) join;
	}

	public List<Order> orderBy(AbstractFilter<?> filter, Root<?> root, Class<?> entity,
			CriteriaBuilder criteriaBuilder) {

		List<Order> orderList = new ArrayList<>();
		if (Boolean.TRUE.equals(filter.getNoOrder()))
			return orderList;

		if (StringUtils.isNotEmpty(filter.getOrderColumn())) {

			List<String> orderSplit = new ArrayList<String>(Arrays.asList(filter.getOrderColumn().split(",")));

			for (String orderField : orderSplit) {
				if (StringUtils.startsWith(orderField, "groupBy")) {
					List<String> property = new ArrayList<String>(Arrays.asList(orderField.split("groupBy")));

					Field field = ReflectionUtils.findField(entity, property.get(1).split("\\.")[0]);
					if (field.getType().equals(Date.class) || field.getType().equals(Calendar.class)) {
						if (filter.getAsc()) {
							orderList.add(criteriaBuilder.asc(root.get(property.get(1)).as(java.sql.Date.class)));
						} else {
							orderList.add(criteriaBuilder.desc(root.get(property.get(1)).as(java.sql.Date.class)));
						}
					} else {

						List<String> splitAux = new ArrayList<String>(Arrays.asList(orderField.split("\\.")));
						if (splitAux.size() > 1) {

							Optional<Join<?, ?>> join = this.getJoin(root, splitAux.get(0));

							if (join.isPresent()) {

								if (filter.getAsc()) {
									orderList.add(criteriaBuilder.asc(join.get().get(splitAux.get(1))));
								} else {
									orderList.add(criteriaBuilder.desc(join.get().get(splitAux.get(1))));
								}
							}

						} else {

							if (filter.getAsc()) {
								orderList.add(criteriaBuilder.asc(root.get(property.get(1))));
							} else {
								orderList.add(criteriaBuilder.desc(root.get(property.get(1))));
							}
						}
					}
				} else {

					List<String> splitAux = new ArrayList<String>(Arrays.asList(orderField.split("\\.")));

					if (splitAux.size() > 1) {

						Optional<Join<?, ?>> join = this.getJoin(root, splitAux.get(0));

						if (join.isPresent()) {

							if (filter.getAsc()) {
								orderList.add(criteriaBuilder.asc(join.get().get(splitAux.get(1))));
							} else {
								orderList.add(criteriaBuilder.desc(join.get().get(splitAux.get(1))));
							}
						}

					} else {

						if (filter.getAsc()) {
							orderList.add(criteriaBuilder.asc(root.get(orderField)));
						} else {
							orderList.add(criteriaBuilder.desc(root.get(orderField)));
						}
					}
				}
			}
		}
		return orderList;
	}

	public List<Selection<?>> selectionsJoin(Object repository, Class<?> entity, String propertiesRequired,
			String propertySource, Join<?, ?> joinOrigin, CriteriaQuery<Tuple> cq)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, InstantiationException {

		List<Selection<?>> selectionsList = new ArrayList<Selection<?>>();

		List<String> propertySplit = new ArrayList<String>(Arrays.asList(propertiesRequired.split("\\.")));

		if (Boolean.FALSE.equals(StringUtils.contains(propertySplit.get(0), "list"))) {
			if (StringUtils.startsWith(propertySplit.get(0), "groupBy")) {
				List<String> propertyGroupBySplit = new ArrayList<String>(
						Arrays.asList(propertySplit.get(0).split("groupBy")));

				if (propertySplit.size() == 1) {

					Field field = ReflectionUtils.findField(joinOrigin.getModel().getBindableJavaType(),
							propertyGroupBySplit.get(1));

					if (field.getType().equals(Date.class) || field.getType().equals(Calendar.class)) {
						cq.groupBy(joinOrigin.get(propertyGroupBySplit.get(1)).as(java.sql.Date.class));
						selectionsList.add(joinOrigin.get(propertyGroupBySplit.get(1)).as(java.sql.Date.class)
								.alias(propertySource + "." + propertyGroupBySplit.get(1)));
					} else {
						cq.groupBy(joinOrigin.get(propertyGroupBySplit.get(1)));
						selectionsList.add(joinOrigin.get(propertyGroupBySplit.get(1))
								.alias(propertySource + "." + propertyGroupBySplit.get(1)));
					}
				} else if (propertySplit.size() > 1) {

					String aliasAux = propertyGroupBySplit.get(1);
					String base = (propertySource + "." + aliasAux);

					if (!joinOrigin.getJoins().stream().anyMatch(j -> j.getAlias().equals(aliasAux)))
						joinOrigin.join(aliasAux, JoinType.LEFT).alias(aliasAux);

					List<String> propertySplitAux = Arrays
							.asList(propertiesRequired.substring(propertiesRequired.indexOf(".") + 1));
					propertySplitAux.set(0, "groupBy" + propertySplitAux.get(0));
					String strProperty = String.join(".", propertySplitAux);

					Field entityJoin = ReflectionUtils.findField(entity, propertySplit.get(0).replace("groupBy", ""));
					Object entityJoinRepository = null;
					try {
						StringBuilder sb = new StringBuilder(entityJoin.getType().getSimpleName());
						sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));

						entityJoinRepository = beanFactory.getBean(sb.toString() + "RepositoryImpl");
					} catch (BeansException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					selectionsList.addAll(this.selectionsJoin(entityJoinRepository, entityJoin.getType(), strProperty,
							base,
							joinOrigin.getJoins().stream().filter(j -> j.getAlias().equals(aliasAux)).findFirst().get(),
							cq));
				}

			} else if (propertySplit.size() == 1) {
				String alias = propertySource + "." + propertiesRequired;
				Path<Object> column = (Path<Object>) joinOrigin.get(propertiesRequired).alias(alias);
				selectionsList.add(column);
			} else if (propertySplit.size() > 1) {

				if (!joinOrigin.getJoins().stream().anyMatch(j -> j.getAlias().equals(propertySplit.get(0))))
					joinOrigin.join(propertySplit.get(0), JoinType.LEFT).alias(propertySplit.get(0));

				String propertySplitAux = propertiesRequired.substring(propertiesRequired.indexOf(".") + 1);

				Field entityJoin = ReflectionUtils.findField(entity, propertySplit.get(0));
				Object entityJoinRepository = null;
				try {
					StringBuilder sb = new StringBuilder(entityJoin.getType().getSimpleName());
					sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));

					entityJoinRepository = beanFactory.getBean(sb.toString() + "RepositoryImpl");
				} catch (BeansException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				selectionsList.addAll(this.selectionsJoin(entityJoinRepository, entityJoin.getType(), propertySplitAux,
						propertySource + "." + propertySplit.get(0), joinOrigin.getJoins().stream()
								.filter(j -> j.getAlias().equals(propertySplit.get(0))).findFirst().get(),
						cq));

			}
		}

		return selectionsList;
	}

	public List<Selection<?>> selections(Object clazz, Class<?> entity, List<String> propertiesRequired,
			String propertySource, Root<?> root, CriteriaQuery<Tuple> cq, CriteriaBuilder cb)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException {

		List<Selection<?>> selectionsList = new ArrayList<Selection<?>>();

		for (String propertyRequired : propertiesRequired) {
			String alias = "";
			List<String> propertySplit = new ArrayList<String>(Arrays.asList(propertyRequired.split("\\.")));

			if (StringUtils.startsWith(propertySplit.get(0), "groupBy")) {

				List<String> propertyGroupBySplit = new ArrayList<String>(
						Arrays.asList(propertySplit.get(0).split("groupBy")));

				Field field = ReflectionUtils.findField(entity, propertyGroupBySplit.get(1));
				if (field.getType().equals(Date.class) || field.getType().equals(Calendar.class)) {
					cq.groupBy(root.get(propertyGroupBySplit.get(1)).as(java.sql.Date.class));
					selectionsList.add(root.get(propertyGroupBySplit.get(1)).as(java.sql.Date.class)
							.alias(propertySource + propertyGroupBySplit.get(1)));

				} else {
					if (propertySplit.size() > 1) {

						String aliasAux = propertyGroupBySplit.get(1);
						String base = (propertySource + aliasAux);

						if (!root.getJoins().stream().anyMatch(j -> j.getAlias().equals(aliasAux)))
							root.join(aliasAux, JoinType.LEFT).alias(aliasAux);

						List<String> propertySplitAux = Arrays
								.asList(propertyRequired.substring(propertyRequired.indexOf(".") + 1));

						propertySplitAux.set(0, "groupBy" + propertySplitAux.get(0));
						String strProperty = String.join(".", propertySplitAux);
						Field entityJoin = ReflectionUtils.findField(entity,
								propertySplit.get(0).replace("groupBy", ""));
						Object entityJoinRepository = null;
						try {
							StringBuilder sb = new StringBuilder(entityJoin.getType().getSimpleName());
							sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));

							entityJoinRepository = beanFactory.getBean(sb.toString() + "RepositoryImpl");
						} catch (BeansException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						selectionsList.addAll(this.selectionsJoin(entityJoinRepository, entityJoin.getType(),
								strProperty, base,
								root.getJoins().stream().filter(j -> j.getAlias().equals(aliasAux)).findFirst().get(),
								cq));
					} else {
						cq.groupBy(root.get(propertyGroupBySplit.get(1)));
						selectionsList.add(root.get(propertyGroupBySplit.get(1))
								.alias(propertySource + propertyGroupBySplit.get(1)));
					}
				}

			} else if (StringUtils.startsWith(propertySplit.get(0), "sumAsDouble")) {
				List<String> sumAsDoubleSplit = new ArrayList<String>(
						Arrays.asList(propertyRequired.split("sumAsDouble")));
				selectionsList.add(cb.coalesce(cb.sumAsDouble(root.get(sumAsDoubleSplit.get(1))), 0D)
						.alias(propertySource + sumAsDoubleSplit.get(1)));

			} else if (StringUtils.startsWith(propertySplit.get(0), "countBy")) {
				List<String> countSplit = new ArrayList<String>(Arrays.asList(propertyRequired.split("count")));
				selectionsList.add(cb.countDistinct(root).alias(propertySource + countSplit.get(1)));

			} else if (!StringUtils.containsIgnoreCase(propertySplit.get(0), "list")) {
				alias = (propertySource + propertyRequired);
				if (propertySplit.size() == 1) {
					Field field = ReflectionUtils.findField(entity, propertyRequired);
					if (!field.getType().equals(List.class)) {
						Path<Object> column = (Path<Object>) root.get(propertySplit.get(0)).alias(alias);
						selectionsList.add(column);
					}
				} else if (propertySplit.size() > 1) {
					Field entityTarget = ReflectionUtils.findField(entity, propertySplit.get(0));
					if (!entityTarget.getType().equals(List.class)) {

						if (!root.getJoins().stream().anyMatch(j -> j.getAlias().equals(propertySplit.get(0))))
							root.join(propertySplit.get(0), JoinType.LEFT).alias(propertySplit.get(0));

						String propertySplitAux = propertyRequired.substring(propertyRequired.indexOf(".") + 1);

						Field entityJoin = ReflectionUtils.findField(entity, propertySplit.get(0));
						Object entityJoinRepository = null;
						try {
							StringBuilder sb = new StringBuilder(entityJoin.getType().getSimpleName());
							sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));

							entityJoinRepository = beanFactory.getBean(sb.toString() + "RepositoryImpl");
						} catch (BeansException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						selectionsList.addAll(this.selectionsJoin(entityJoinRepository, entityJoin.getType(),
								propertySplitAux, propertySource + propertySplit.get(0), root.getJoins().stream()
										.filter(j -> j.getAlias().equals(propertySplit.get(0))).findFirst().get(),
								cq));
					}

				}
			}
		}

		return selectionsList;
	}

	public Object generateEntity(Class<?> clazz, List<String> propertiesRequired, String propertySource, Tuple tuple)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {

		Object entity = null;
		for (String propertyRequired : propertiesRequired) {

			List<String> propertySplit = new ArrayList<String>(Arrays.asList(propertyRequired.split("\\.")));

			if (StringUtils.startsWith(propertySplit.get(0), "groupBy"))
				propertyRequired = propertyRequired.split("groupBy")[1];
			if (StringUtils.startsWith(propertySplit.get(0), "sumAsDouble"))
				propertyRequired = propertyRequired.split("sumAsDouble")[1];
			if (StringUtils.startsWith(propertySplit.get(0), "countBy"))
				propertyRequired = propertyRequired.split("countBy")[1];

			if (propertySplit.size() == 1) {

				Field field = ReflectionUtils.findField(clazz, propertyRequired);
				if (!field.getType().equals(List.class)) {
					String alias = (propertySource + field.getName());
					field.setAccessible(true);
					Object value = tuple.get(alias, field.getType());

					if (value != null) {
						if (entity == null)
							entity = clazz.getDeclaredConstructor().newInstance();

						ReflectionUtils.setField(field, entity, tuple.get(alias, field.getType()));
					}
				}
			} else if (propertySplit.size() > 1) {
				if (!StringUtils.containsIgnoreCase(propertyRequired, "list")) {

					Field field = ReflectionUtils.findField(clazz, propertySplit.get(0).replace("groupBy", ""));
					field.setAccessible(true);

					String alias = (propertySource + propertyRequired);

					entity = instanceEntity(entity, clazz, propertyRequired, tuple, alias);
				}
			}
		}

		return entity;
	}

	@SuppressWarnings("deprecation")
	public Object instanceEntity(Object object, Class<?> clazz, String property, Tuple tuple, String alias)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {

		List<String> propertySplit = new ArrayList<String>(Arrays.asList(property.split("\\.")));
		Field field = ReflectionUtils.findField(clazz, propertySplit.get(0));
		field.setAccessible(true);

		Object obj = null;
		if (object != null)
			obj = field.get(object);

		if (propertySplit.size() == 1) {
			Object value = tuple.get(alias, field.getType());
			if (value != null) {
				if (object == null) {
					object = clazz.getDeclaredConstructor().newInstance();
				}
				ReflectionUtils.setField(field, object, value);
			}
		}
		propertySplit.remove(0);

		if (!propertySplit.isEmpty()) {
			Object objectAux = instanceEntity(obj, field.getType(), String.join(".", propertySplit), tuple, alias);
			if (objectAux != null && object == null) {
				object = clazz.getDeclaredConstructor().newInstance();
			}
			if (object != null)
				ReflectionUtils.setField(field, object, objectAux);

		}

		return object;
	}

}
