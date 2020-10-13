package com.github.codingdebugallday.logindemo.interceptors;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * <p>
 * description
 * </p>
 *
 * @author isaac 2020/09/22 10:08
 * @since 1.0.0
 */
public class LoginInterceptor implements HandlerInterceptor {

    @SuppressWarnings("unchecked")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, String> user = (Map<String, String>) request.getSession().getAttribute("user");
        String username;
        String password;
        boolean addToSession = false;
        if (null != user) {
            username = user.get("username");
            password = user.get("password");
        } else {
            username = request.getParameter("username");
            password = request.getParameter("password");
            addToSession = true;
        }
        // 用户名密码写死
        if ("admin".equals(username) && "admin".equals(password)) {
            if (addToSession && !StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
                Map<String, String> map = new HashMap<>();
                map.put("username", username);
                map.put("password", password);
                request.getSession().setAttribute("user", map);
            }
            return true;
        } else {
            // 重定向到登录页面 即首页
            response.sendRedirect("/");
            return false;
        }
    }
}
