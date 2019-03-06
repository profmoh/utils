package com.datazord;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class CollectionsUtils {

	public static boolean isEmptyMap(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}

	public static boolean isEmptyCollection(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}

	public static <T> Set<Object> getDistinctFieldByFieldName(final List<T> objList, final String fieldName) {
		if (objList == null || objList.size() == 0 || StringUtils.isBlank(fieldName))
			return Sets.newHashSet();

		final Class<?> tClass = objList.get(0).getClass();

		return Sets.newHashSet(Lists.transform(objList, new Function<T, Object>() {
			public Object apply(T t) {
				try {
					return tClass.getDeclaredField(fieldName).get(t);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		}));
	}

	public static <T, O> Set<O> getDistinctFieldByGetterName(final List<T> objList, final String getterName) {
		if(objList == null || objList.size() == 0 || StringUtils.isBlank(getterName))
			return Sets.newHashSet();

		final Class<?> tClass = objList.get(0).getClass();

		return Sets.newHashSet(
				Lists.transform(objList, new Function<T, O>() {
					@SuppressWarnings("unchecked")
					public O apply(T t) {
						try {
							return (O) tClass.getDeclaredMethod(getterName).invoke(t);
						} catch (Exception e) {
							e.printStackTrace();
							return null;
						}
					}
				}));
	}

	public static <T extends Comparable<T>, R> Map<T, R> sortMapByKey(Map<T, R> map) {
		// Creating LinkedhashMap for storing entries after SORTING by Keys using Stream in Java8
		Map<T, R> keyMap = new LinkedHashMap<T, R>();

		// Sorting by Keys using the 'comparingByKey()' method
		map.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByKey())
				.forEachOrdered(c -> keyMap.put(c.getKey(), c.getValue()));

		return keyMap;
	}
}
