package com.example.crud.query;

import com.example.crud.model.annotation.Condition;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConditionConverter {
    public static <T> Predicate toPredicate(Root<T> root, Object conditionAnnotationObj, CriteriaBuilder cb) {
        if (conditionAnnotationObj == null) {
            return cb.conjunction();
        }
        List<Predicate> predicates = new ArrayList<>();
        Class<?> clazz = conditionAnnotationObj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            Condition condition = field.getAnnotation(Condition.class);
            if (condition == null) {
                continue;
            }
            field.setAccessible(true);
            Object value = getFieldValue(field, conditionAnnotationObj);
            String propName = StringUtils.isNotBlank(condition.propName()) ? condition.propName() : field.getName();
            Path<?> path = buildPath(root, condition.joinName(), propName);
            if (value == null) {
                if (condition.ignoreNull()) {
                    continue;
                } else {
                    predicates.add(path.isNull());
                    continue;
                }
            }
            if (isEmpty(value)) {
                continue;
            }
            switch (condition.type()) {
                case EQUAL:
                    predicates.add(cb.equal(path, value));
                    break;
                case INNER_LIKE:
                    String text = value.toString().trim();
                    if (!text.isEmpty()) {
                        predicates.add(cb.like(cb.lower(path.as(String.class)), "%" + text.toLowerCase() + "%"));
                    }
                    break;
                case BETWEEN:
                    Object[] bounds = toRange(value);
                    if (bounds != null && bounds.length == 2 && bounds[0] != null && bounds[1] != null) {
                        @SuppressWarnings({"rawtypes", "unchecked"})
                        Expression<? extends Comparable> expression = (Expression<? extends Comparable>) path;
                        predicates.add(cb.between(expression, (Comparable) bounds[0], (Comparable) bounds[1]));
                    }
                    break;
            }
        }
        return predicates.isEmpty() ? cb.conjunction() : cb.and(predicates.toArray(new Predicate[0]));
    }

    private static boolean isEmpty(Object value) {
        if (value instanceof String str) {
            return StringUtils.isBlank(str);
        }
        if (value instanceof Collection<?> collection) {
            return collection.isEmpty();
        }
        if (value.getClass().isArray()) {
            return Array.getLength(value) == 0;
        }
        return false;
    }

    private static Object getFieldValue(Field field, Object target) {
        try {
            return field.get(target);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Cannot read query criteria field: " + field.getName(), e);
        }
    }

    private static <T> Path<?> buildPath(Root<T> root, String joinName, String propName) {
        Path<?> path = root;
        if (StringUtils.isNotBlank(joinName)) {
            String[] joinParts = joinName.split("\\.");
            for (String part : joinParts) {
                if (path instanceof From<?, ?> from) {
                    path = from.join(part, JoinType.LEFT);
                }
            }
        }
        if (StringUtils.isNotBlank(propName)) {
            String[] propParts = propName.split("\\.");
            for (String part : propParts) {
                path = path.get(part);
            }
        }
        return path;
    }

    private static Object[] toRange(Object value) {
        if (value instanceof List<?> list && list.size() >= 2) {
            return new Object[]{list.get(0), list.get(1)};
        }
        if (value != null && value.getClass().isArray() && Array.getLength(value) >= 2) {
            return new Object[]{Array.get(value, 0), Array.get(value, 1)};
        }
        return null;
    }
}

