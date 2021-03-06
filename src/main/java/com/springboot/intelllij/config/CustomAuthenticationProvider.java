package com.springboot.intelllij.config;

import com.springboot.intelllij.domain.AccountEntity;
import com.springboot.intelllij.repository.AccountRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @NonNull
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)authentication;
        String userEmail = token.getName();
        String userPw = (String)token.getCredentials();
        AccountEntity account = accountRepository.findByAccountEmail(userEmail).orElseThrow(() -> new IllegalArgumentException(userEmail + " not exist"));
        if(!passwordEncoder.matches(userPw, account.getAccountPw())) {
            throw new BadCredentialsException(userEmail + " Invalid password");
        }
        UsernamePasswordAuthenticationToken tokenWithDetails = new UsernamePasswordAuthenticationToken(
                account, userPw
        );
        tokenWithDetails.setDetails(account);
        return tokenWithDetails;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
