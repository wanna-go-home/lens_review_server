package com.springboot.intelllij.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class AccountEntity {

    @Id
    @Column(name = "account_id")
    private String account;

    @Column(name = "account_pw")
    private String accountPw;

    @Column(name = "name")
    private String nickname;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_num")
    private String phoneNum;
}
