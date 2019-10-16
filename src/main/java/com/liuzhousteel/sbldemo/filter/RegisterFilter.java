package com.liuzhousteel.sbldemo.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liuzhousteel.sbldemo.domain.User;
import com.liuzhousteel.sbldemo.model.ResultModel;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 用户过滤器，用于过滤绕过前端验证机制提交到后端的注册数据的格式
 */
@WebFilter(urlPatterns = "/auth/register", filterName = "registerFilter")
public class RegisterFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 有了前端的表单验证还是否需要后台的过滤器来再次验证提交的数据
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        if (httpServletRequest.getMethod().equals("POST")) {
            String param = servletRequest.getParameter("param");
            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(param, User.class);
            int ul = user.getUsername().length();
            int pl = user.getPassword().length();
            if ( (ul < 4 || ul > 12) || (pl < 6 || pl > 16) || !user.getEmail().matches("[A-z0-9_-]*@[A-z0-9]+\\.[A-z]+")) {
                servletResponse.getWriter().write(new ObjectMapper().writeValueAsString(ResultModel.error(0))); // 如果注册用户名和密码都不和要求则返回这个JSON字符串到Ajax
            } else {
                filterChain.doFilter(servletRequest, servletResponse); // 使用过滤器链继续过滤请求.
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
