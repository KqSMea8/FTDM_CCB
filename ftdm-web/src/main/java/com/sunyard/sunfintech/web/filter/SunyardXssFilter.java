package com.sunyard.sunfintech.web.filter;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * XSS安全过滤器
 * @author Matrix
 * @version 2018/3/19
 */
@Deprecated
public class SunyardXssFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(SunyardXssFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        XsslHttpServletRequestWrapper xssRequest = new XsslHttpServletRequestWrapper((HttpServletRequest)request);
        chain.doFilter(xssRequest , response);
    }

    @Override
    public void destroy() {

    }
}
