package com.zhai.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends Person{
    Long id;
    String alias;

    public User(Long id, String name, String alias, int age) {
        this.id = id;
        super.name = name;
        this.alias = alias;
        super.age = age;
    }

    public User(String name, String alias, int age) {
        this.id = null;
        super.name = name;
        this.alias = alias;
        super.age = age;
    }
}