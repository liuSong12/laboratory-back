package com.study.labsystem.intercepter;

import com.study.labsystem.config.JwtConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;


@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtConfig jwtConfig;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();// 这个只包括路劲
        //StringBuffer requestURL = request.getRequestURL(); //这个包括主机+端口号
        if (requestURI.contains("/login")){
            return true;
        }
        String token = request.getHeader(jwtConfig.getHeader());
        if (StringUtils.isEmpty(token)){
            token = request.getParameter(jwtConfig.getHeader());
        }
        if (StringUtils.isEmpty(token)){
            //token为空
            sendError(response,"token为空");
        }
        try {
            jwtConfig.geTokenClim(token);
        }catch (Exception e){
            //token失效
            sendError(response,"token失效");
        }
        String userId = jwtConfig.getSubject(token);
        String newToken = jwtConfig.createToken(userId);
        response.setHeader(jwtConfig.getHeader(),newToken);
        return true;
    }

    private void sendError(HttpServletResponse response,String msg){
        response.setStatus(401);
        throw new RuntimeException(msg);
//        response.setContentType("application/json;charset=utf-8");
//        response.setCharacterEncoding("utf-8");
    }


}




