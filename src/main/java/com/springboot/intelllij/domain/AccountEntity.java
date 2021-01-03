package com.springboot.intelllij.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "account")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "account_email")
    private String accountEmail;

    @Column(name = "account_pw")
    private String accountPw;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "phone_num")
    private String phoneNum;

    @Column(name = "active")
    private boolean active;
}
