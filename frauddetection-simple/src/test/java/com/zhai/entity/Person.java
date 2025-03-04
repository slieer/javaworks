package com.zhai.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"name", "age"})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    String name;
    int age;
}