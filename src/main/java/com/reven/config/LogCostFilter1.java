package com.reven.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

@WebFilter(urlPatterns = "/demoInterceptor/*", filterName = "logFilter1")
public class LogCostFilter1 implements Filter {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        long start = System.currentTimeMillis();
        filterChain.doFilter(servletRequest, servletResponse);
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        logger.warn("请求接口：{}，请求参数：{}", request.getRequestURI(), JSON.toJSONString(request.getParameterMap()));
        logger.info("LogFilter1 Execute cost=" + (System.currentTimeMillis() - start));
    }

    @Override
    public void destroy() {

    }
}