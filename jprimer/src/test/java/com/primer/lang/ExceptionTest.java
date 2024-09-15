package com.primer.lang;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

@Slf4j
public class ExceptionTest {
    @Test
    public void exceptionTest() {
        try {
            throw new RuntimeException("");
        } catch (Exception e) {
            StackWalker walker = StackWalker.getInstance(StackWalker.Option.SHOW_REFLECT_FRAMES);
            walker.walk(s -> s.limit(1).collect(Collectors.toList()));
            walker.forEach(t -> log.info(t.toString()));
        }
    }

    ExceptionTest t = new ExceptionTest();
    @Test
    public void test(){
        System.out.println(t.get1());
    }

    @Test
    public void test1(){
        int b = t.get();
        System.out.println(b);    // 2
    }

    int get1() {
        int x = 1;
        try {
            return x;
        } finally {
            ++x;
        }
    }

    //finally block does not complete normally
    int get() {
        try {
            return 1;
        } finally {
            return 2;
        }
    }

}
