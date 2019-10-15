package com.liuzhousteel.sbldemo.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 后台拦截器
 */
public class DashboardInterceptor implements HandlerInterceptor {

    /**
     * 在请求前执行
     * @return  session中是否存在名为"user"的对象 ? true : false
     * @throws IOException 重定向中可能抛出的异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        HttpSession session = request.getSession();
        if (null == session.getAttribute("user")) {
            response.sendRedirect("/");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 请求结束执行当且仅当preHandle方法返回true
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 视图渲染完成后执行当且仅当preHandle方法返回true，通常用于清理资源等工作
    }
}
