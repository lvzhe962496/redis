package com.bawei.ssm.util;

import java.util.HashMap;
import java.util.Map;

import com.bawei.ssm.common.Page;
import com.bawei.ssm.common.PageHandle;

import net.sf.json.JSONObject;

public abstract class JsonUtil {

	private static final String SUCCESS_CODE = "200";
	private static final String ERROR_CODE = "400";

	private JsonUtil() {
	}

	public static String success() {
		Map<String, String> map = new HashMap<String, String>(2);
		map.put("code", SUCCESS_CODE);
		return JSONObject.fromObject(map).toString();
	}

	public static String success(Object obj) {
		Map<String, Object> map = new HashMap<String, Object>(4);
		map.put("code", SUCCESS_CODE);
		map.put("data", obj);
		Page page = PageHandle.getAndRemPage();
		if (page.isPage()) {
			map.put("pageSize", page.getPageSize());
			map.put("pageNum", page.getPageNum());
			map.put("pages", page.getPages());
		}
		return JSONObject.fromObject(map).toString();
	}

	public static String error(String msg) {
		Map<String, String> map = new HashMap<String, String>(4);
		map.put("code", ERROR_CODE);
		map.put("msg", msg);
		return JSONObject.fromObject(map).toString();
	}
}
