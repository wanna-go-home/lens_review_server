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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "id")
    private String id;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "pw")
    private String pw;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_num")
    private String phoneNum;
}
