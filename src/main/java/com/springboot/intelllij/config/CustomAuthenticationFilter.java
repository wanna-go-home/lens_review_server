package com.springboot.intelllij.config;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest;
        String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Map<String,String> jsonMap = new Gson().fromJson(body,HashMap.class);
        authRequest = new UsernamePasswordAuthenticationToken(jsonMap.get("account"), jsonMap.get("pw"));
        setDetails(request,authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
