package com.bawei.ssm.common;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.bawei.ssm.util.JsonUtil;
@Component
public class SimpleHandlerExceptionResolver implements HandlerExceptionResolver{

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.println(JsonUtil.error("server error!"));
		} catch (IOException e) {
			//
		} finally {
			out.close();
		}
		return null;
	}
}
