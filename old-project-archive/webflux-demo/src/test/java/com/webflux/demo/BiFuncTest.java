package com.webflux.demo;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

public class BiFuncTest {
    @Test
    void test0(){
        BiFunction<Integer, Integer, Integer> biFunc = (a, b) -> {
            return Integer.compare(a, b);
        };
        biFunc.apply(1,3);
    }
    @Test
    void test1() {
        BinaryOperator<Integer> add = (n1, n2) -> n1 + n2;
        //apply方法用于接收参数，并返回BinaryOperator中的Integer类型
        System.out.println(add.apply(3, 4));
    }
    @Test
    void test2() {
        BinaryOperator<String> addStr = (n1, n2) -> n1 + "===" + n2;
        //apply方法用于接收参数，并返回BinaryOperator中的String类型
        System.out.println(addStr.apply("3", "4"));
    }
    @Test
    void tes3() {
        BinaryOperator<Integer> bi = BinaryOperator.minBy(Comparator.naturalOrder());
        System.out.println(bi.apply(2, 3));

    }
    @Test
    void test4() {
        BinaryOperator<Integer> bi = BinaryOperator.minBy(Comparator.naturalOrder());
        System.out.println(bi.apply(2, 3));
    }
}
