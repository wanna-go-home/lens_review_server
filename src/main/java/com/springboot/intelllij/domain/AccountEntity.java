package com.springboot.intelllij.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "account")
public class AccountEntity {

    @Id
    @Column(name = "account_id")
    private String accountId;

    @Column(name = "account_pw")
    private String accountPw;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_num")
    private String phoneNum;
}
