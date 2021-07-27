package com.korea.dbapp.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class SessionInterceptor implements HandlerInterceptor {
    // return이 true이면??? 함수 진입, false이면? 진입 못함!!!
    // 우리는? 세션이 있으면 true로 함수 진입하게 함
    // => 세션체크가 공통관심사다!!! AOP (핵심로직이 아님)

    // delete :/post/1
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute("principal") == null) {
            // 최초 주소가 안바뀌고 로그인페이지로 보냄
            // delete :/post/1 아래 함수 사용하면? 덮어씌워서 처음 요청 주소 post/1이 남는다!! 이거 사용안함
            // RequestDispatcher dis = request.getRequestDispatcher("auth/loginform");
            // dis.forward(request, response);
            response.sendRedirect("/auth/loginForm");
            return false;
        }
        return true;
    }
}
