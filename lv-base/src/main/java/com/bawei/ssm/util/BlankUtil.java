package com.bawei.ssm.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * To determine whether the object is null or empty
 * 
 * @author liuzeke
 * @since 1.0
 */
public abstract class BlankUtil {

	/**
	 * Can not be inherited
	 */
	private BlankUtil() {
	}

	/**
	 * @author liuzeke
	 * @param o
	 * @return
	 */
	public static boolean isBlank(Object o) {

		if (o == null)
			return true;

		if (o instanceof String)
			return ((String) o).trim().length() == 0 ? true : false;

		if (o.getClass().isArray())
			return Array.getLength(o) == 0 ? true : false;

		if (o instanceof Collection)
			return ((Collection<?>) o).size() == 0 ? true : false;

		if (o instanceof Map)
			return ((Map<?, ?>) o).isEmpty() ? true : false;

		return false;
	}

	/**
	 * @author liuzeke
	 * @param o
	 * @return
	 */
	public static boolean isNotBlank(Object o) {
		return !isBlank(o);
	}

	/**
	 * @author liuzeke
	 * @param o
	 * @return
	 */
	public static boolean isBlankAll(Object... o) {

		if (o == null)
			return true;

		for (Object obj : o)
			if (isNotBlank(obj))
				return false;

		return true;
	}

	/**
	 * @author liuzeke
	 * @param o
	 * @return
	 */
	public static boolean isNotBlankAll(Object... o) {

		if (o == null)
			return false;

		for (Object obj : o)
			if (isBlank(obj))
				return false;

		return true;
	}

	/**
	 * @author liuzeke
	 * @param str
	 * @return
	 */
	public static boolean httpNull(String str) {
		return str == null || "".equals(str.trim()) || "null".equalsIgnoreCase(str)
				|| "undefined".equalsIgnoreCase(str);
	}

	/**
	 * @author liuzeke
	 * @param str
	 * @return
	 */
	public static boolean httpNotNull(String str) {
		return !httpNull(str);
	}
}
