package com.datazord;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;

public class GeneralUtils {

	public static int getRandomNumberInts(int min, int max) {

		return new Random().ints(min, (max + 1)).findFirst().getAsInt();
	}

	public static String listToString(List<? extends Object> list, String getterMethodName)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if(CollectionsUtils.isEmptyCollection(list))
			return "";

		Class<? extends Object> c = list.get(0).getClass();
		Method method = c.getDeclaredMethod(getterMethodName);

		return list.stream().map(l -> {
			try {
				return method.invoke(l).toString();
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}

			return getterMethodName;
		}).reduce((x, y) -> x.concat(y)).get();
	}

	@SuppressWarnings("unchecked")
	public static <T, O> T getObjectValueByFieldName(O object, String fieldName, Class<T> t) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Class<? extends Object> oClass = object.getClass();

		Field field = oClass.getField(fieldName);

		return (T) field.get(object);
	}

	public static Double addNewMarkToOldMark(Double oldMark, Double newChildMark, Integer childSize) {
		return (newChildMark + (oldMark * childSize)) / childSize;
	}

	public static Long limitLongValue(Long value, Long limit) {
		if(value == null || value < limit)
			return value;

		return limit;
	}

	public static Double roundDouble(Double value, int places) {
		double scale = Math.pow(10, places);

		return Math.round(value * scale) / scale;
	}
}
