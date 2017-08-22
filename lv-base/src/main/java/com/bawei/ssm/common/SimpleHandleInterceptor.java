package com.bawei.ssm.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.bawei.ssm.util.BlankUtil;

/**
 * @author lvzhe
 * @version 1.0
 * @date 2017年8月17日
 * @time 下午2:43:20
 */
public class SimpleHandleInterceptor implements HandlerInterceptor {

	/**
	 * @author lvzhe
	 * @date 2017年8月17日
	 * @time 下午2:44:42
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=utf-8");
		page(request);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	/**
	 * isDesc=1 表示true；isDesc=0表示false
	 * 
	 * @author lvzhe
	 * @date 2017年8月19日
	 * @time 上午9:18:25
	 * @param request
	 */
	private void page(HttpServletRequest request) {

		String pageSize = request.getParameter("pageSize");
		String pageNum = request.getParameter("pageNum");
		String isDesc = request.getParameter("isDesc");

		if (BlankUtil.httpNull(pageSize) || BlankUtil.httpNull(pageNum)) {
			PageHandle.setPage(new Page(false));
			return;
		}

		boolean _isDesc = BlankUtil.httpNotNull(isDesc) ? (isDesc.equals(1) ? true : false) : false;
		Page page = new Page(Integer.parseInt(pageSize), Integer.parseInt(pageNum), _isDesc);
		PageHandle.setPage(page);
	}

}
