package com.bawei.ssm.common;
@SuppressWarnings({"rawtypes","unchecked"})
public abstract class PageHandle {
	
	
	
	private static final ThreadLocal PAGE_LOCAL = new ThreadLocal();
	
	private PageHandle(){
		
	}
	
	public static void setPage(Page page) {
		PAGE_LOCAL.set(page);
	}
	
	public static Page getPage(){
		return (Page) PAGE_LOCAL.get();
	}

	public static Page getAndRemPage(){
		Page page = (Page) PAGE_LOCAL.get();
		try {
			return page;
		} finally{
			PAGE_LOCAL.remove();
		}
	}
}
