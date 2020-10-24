package com.springboot.intelllij.config;

import com.springboot.intelllij.constant.AuthConstant;
import com.springboot.intelllij.domain.AccountEntity;
import com.springboot.intelllij.repository.AccountRepository;
import com.springboot.intelllij.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TokenUtils tokenUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        AccountEntity account = (AccountEntity)authentication.getPrincipal();
        String token = tokenUtils.generateJwt(account);
        response.addHeader(AuthConstant.AUTH_HEADER,AuthConstant.TOKEN_TYPE + " " + token);
    }
}
