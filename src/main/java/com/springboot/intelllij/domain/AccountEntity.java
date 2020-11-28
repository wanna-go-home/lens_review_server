package com.springboot.intelllij.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class AccountEntity {

    @Id
    @Column(name = "account_id")
    private String accountEmail;

    @Column(name = "account_pw")
    private String accountPw;

    @Column(name = "name")
    private String nickname;

    @Column(name = "phone_num")
    private String phoneNum;
}
