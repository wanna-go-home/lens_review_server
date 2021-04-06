package com.springboot.intelllij.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account_b")
@Getter
@Setter
@NoArgsConstructor
public class AccountBEntity {

    @Id
    @Column(name = "phone_num")
    private String phoneNum;

    @Column(name = "pin_num")
    private String pinNum;

    @Column(name = "nickname")
    private String nickname;
}
