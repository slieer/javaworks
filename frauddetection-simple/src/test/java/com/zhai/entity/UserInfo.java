package com.zhai.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor()
@NoArgsConstructor
public class UserInfo extends User{
    private String pwd;
    private String email;
    private double balance;

    public UserInfo(Long id, String name, String alias, int age, String pwd, String email, double balance) {
        super(id, name, alias, age);
        this.pwd = pwd;
        this.email = email;
        this.balance = balance;
    }

    public UserInfo(Long id, String name, int age, String email, double balance) {
        super(id, name, null, age);
        this.pwd = pwd;
        this.email = email;
        this.balance = balance;
    }
}