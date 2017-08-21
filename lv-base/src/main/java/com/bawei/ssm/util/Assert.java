package com.bawei.ssm.util;

/**
 * @author liuzeke
 * @since 1.0
 */
public abstract class Assert {

	/**
	 * Can not be inherited
	 */
	private Assert() {
	}

	/**
	 * @author liuzeke
	 * @param o
	 */
	public static void isBlank(Object o) {
		isBlank(o, "the object argument must be blank.");
	}

	/**
	 * @author liuzeke
	 * @param o
	 * @param msg
	 */
	public static void isBlank(Object o, String msg) {
		if (BlankUtil.isNotBlank(o))
			throw new IllegalArgumentException(msg);
	}

	/**
	 * @author liuzeke
	 * @param o
	 */
	public static void isNotBlank(Object o) {
		isNotBlank(o, "the object argument must not be blank.");
	}

	/**
	 * @author liuzeke
	 * @param o
	 * @param msg
	 */
	public static void isNotBlank(Object o, String msg) {
		if (BlankUtil.isBlank(o))
			throw new IllegalArgumentException(msg);
	}

	/**
	 * @author liuzeke
	 * @param msg
	 * @param o
	 */
	public static void isBlankAll(Object... o) {
		if (!BlankUtil.isBlankAll(o))
			throw new IllegalArgumentException("the object arguments must be blank.");
	}

	/**
	 * @author liuzeke
	 * @param msg
	 * @param o
	 */
	public static void isNotBlankAll(Object... o) {
		if (!BlankUtil.isNotBlankAll(o))
			throw new IllegalArgumentException("the object arguments must be blank.");
	}

	/**
	 * @author liuzeke
	 * @param expression
	 */
	public static void isTrue(boolean expression) {
		isTrue(expression, "this expression must be true");
	}

	/**
	 * @author liuzeke
	 * @param expression
	 * @param message
	 */
	public static void isTrue(boolean expression, String message) {
		if (!expression)
			throw new IllegalArgumentException(message);
	}

	/**
	 * @author liuzeke
	 * @param expression
	 */
	public static void isFalse(boolean expression) {
		isFalse(expression, "this expression must be false");
	}

	/**
	 * @author liuzeke
	 * @param expression
	 * @param message
	 */
	public static void isFalse(boolean expression, String message) {
		if (expression)
			throw new IllegalArgumentException(message);
	}
}
