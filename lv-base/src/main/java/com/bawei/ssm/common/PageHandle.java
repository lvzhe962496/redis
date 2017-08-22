package com.bawei.ssm.common;

/**
 * @author lvzhe
 * @version 1.0
 * @date 2017年8月21日
 * @time 上午9:10:57
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class PageHandle {

	private static final ThreadLocal PAGE_LOCAL = new ThreadLocal();

	private PageHandle() {
	}

	public static void setPage(Page page) {
		PAGE_LOCAL.set(page);
	}

	public static Page getPage() {
		return (Page) PAGE_LOCAL.get();
	}

	public static Page getAndRemPage() {
		Page page = (Page) PAGE_LOCAL.get();
		try {
			return page;
		} finally {
			PAGE_LOCAL.remove();
		}
	}
}
