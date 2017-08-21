package com.bawei.ssm.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Used to copy the value between two objects
 * 
 * @author liuzeke
 * @since 1.0
 */
public abstract class BeanUtil {

	/**
	 * Can not be inherited
	 */
	private BeanUtil() {
	}

	/**
	 * Object-><T>T
	 * 
	 * @author liuzeke
	 * @param src
	 * @param tar
	 * @param strs
	 * @return <T>T
	 */
	@SuppressWarnings({ "unchecked" })
	public static <T> T copyProperty(final Object src, T tar, String... strs) {

		Assert.isNotBlank(src);
		try {
			tar = tar == null ? (T) src.getClass().newInstance() : tar;
		} catch (Exception e) {
			throw new BeanCopyException("Can not call newInstance() on the Class for java.lang.Class");
		}

		final Map<String, Method> gets = getMethods(src.getClass()).gets;
		for (Map.Entry<String, Method> e : getMethods(tar.getClass()).sets.entrySet()) {
			Method m = gets.get(e.getKey());
			if (m == null || isIgnore(e.getKey(), strs))
				continue;
			try {
				Object o = m.invoke(src);
				if (o != null)
					e.getValue().invoke(tar, o);
			} catch (Exception e1) {
				throw new BeanCopyException("the specified object is null and the method is an instance method.");
			}
		}
		return tar;
	}

	/**
	 * Object-><T>T
	 * 
	 * @author liuzeke
	 * @param src
	 * @param clazz
	 * @param strs
	 * @return <T>T
	 */
	public static <T> T copyProperty(final Object src, final Class<T> clazz, String... strs) {

		Assert.isNotBlankAll(src, clazz);
		try {
			return copyProperty(src, (T) clazz.newInstance(), strs);
		} catch (Exception e) {
			throw new BeanCopyException("Can not call newInstance() on the Class for java.lang.Class");
		}
	}

	/**
	 * @author liuzeke
	 * @param clazz
	 * @return
	 */
	private static Methods getMethods(final Class<?> clazz) {

		final Methods cm = new Methods();
		for (Method m : clazz.getMethods()) {
			String methodName = m.getName();
			if ((methodName.startsWith("get") || methodName.startsWith("is")) && m.getParameterTypes().length == 0)
				cm.gets.put(getAttribute(methodName), m);
			if (methodName.startsWith("set") && m.getParameterTypes().length == 1)
				cm.sets.put(getAttribute(methodName), m);
		}
		cm.gets.remove("class");
		return cm;
	}

	/**
	 * @author liuzeke
	 * @param methodName
	 * @return
	 */
	private static String getAttribute(final String methodName) {

		StringBuilder sb = new StringBuilder();
		if (methodName.startsWith("is")) {
			sb.append(Character.toLowerCase(methodName.charAt(2)));
			sb.append(methodName.substring(3));
		} else {
			sb.append(Character.toLowerCase(methodName.charAt(3)));
			sb.append(methodName.substring(4));
		}
		return sb.toString();
	}

	/**
	 * @author liuzeke
	 * @param src
	 * @param dest
	 * @return
	 */
	private static boolean isIgnore(final String src, final String[] dest) {

		if (BlankUtil.isBlank(dest))
			return false;

		for (String s : dest)
			if (src.equals(s))
				return true;

		return false;
	}

	/**
	 * @author liuzeke
	 * @since 1.0
	 */
	private final static class Methods {

		private final Map<String, Method> gets = new HashMap<String, Method>();
		private final Map<String, Method> sets = new HashMap<String, Method>();

		private Methods() {
		}
	}
}

/**
 * @author liuzeke
 * @since 1.0
 */
class BeanCopyException extends RuntimeException {

	private static final long serialVersionUID = 1245502832297828442L;

	BeanCopyException() {
		super();
	}

	BeanCopyException(String message) {
		super(message);
	}

	BeanCopyException(Throwable cause) {
		super(cause);
	}

	BeanCopyException(String message, Throwable cause) {
		super(message, cause);
	}
}
