package com.springboot.intelllij.config;

import com.springboot.intelllij.constant.AuthConstant;
import com.springboot.intelllij.utils.TokenUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String header = request.getHeader(AuthConstant.AUTH_HEADER);
        try {
            if(handler != null) {
                String token = TokenUtils.getTokenFromHeader(header);
                if(TokenUtils.isValidToken(token))
                    return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("/error/unauthorized");
        return false;
    }
}
