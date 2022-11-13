package com.my.document.generator.app.interceptor;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {

    private String authKey = null;
    public AuthInterceptor setAuthKey(String key){
        this.authKey = key;
        return this;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.out.println("[AuthInterceptor] preHandle from "+request.getRequestURL());
        String authorization = request.getHeader("authorization");
        if(!checkAuth(authorization)){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            throw new AuthException("You have no auth!!!");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        System.out.println("[AuthInterceptor] postHandle from "+request.getRequestURL());
    }

    private boolean checkAuth(String authorization){
//        System.out.println("[AuthInterceptor] checkAuth with ["+authKey+"]");
//        System.out.println("[AuthInterceptor] authorization => ["+authorization+"]");

        if(authKey != null && !authKey.isEmpty() && !authKey.equals(authorization)){
            return false;
        }

        return true;
    }

}
