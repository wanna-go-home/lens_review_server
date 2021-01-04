package com.springboot.intelllij.utils;

import com.springboot.intelllij.domain.AccountEntity;
import com.springboot.intelllij.exceptions.NotFoundException;
import com.springboot.intelllij.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.springboot.intelllij.exceptions.EntityNotFoundExceptionEnum.USER_NOT_FOUND;

@Component
public class UserUtils {

    private static AccountRepository accountRepo;

    @Autowired
    private AccountRepository initAccountRepo;

    @PostConstruct
    private void setInitAccountRepo() {
        accountRepo = this.initAccountRepo;
    }

    public static AccountEntity getUserEntity() {
        AccountEntity user = accountRepo.findById(getUserIdFromSecurityContextHolder()).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        return user;
    }

    public static String getUserStringFromSecurityContextHolder() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.toString();
    }

    public static Integer getUserIdFromSecurityContextHolder() {
        return (Integer)SecurityContextHolder.getContext().getAuthentication().getDetails();
    }
}
