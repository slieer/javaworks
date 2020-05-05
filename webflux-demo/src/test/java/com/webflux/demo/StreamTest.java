package com.webflux.demo;

import com.webflux.demo.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static com.webflux.demo.ReactorTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class StreamTest {
    @Test
    public void collector() {
        //转换成连接字符串
        String strName = list.stream().map(Employee::getName).collect(Collectors.joining(","));
        log.info("strName:{}", strName);
        //转换成Set集合
        Set<String> setName = list.stream().map(Employee::getName).collect(Collectors.toSet());
        log.info("setName:{}", strName);
        //转换成Vector
        Vector<String> vectorName = list.stream().map(Employee::getName).collect(Collectors.toCollection(Vector::new));
        //转换成List
        List<String> listName = list.stream().map(Employee::getName).collect(Collectors.toList());
        //转换成Map
        Map<Integer, String> mapName = list.stream()
                .collect(Collectors.toMap(employee -> employee.getId(), employee -> employee.getName(), (n1, n2) -> {return n1;}));
    }

    @Test
    public void minMaxAverage() {
        Employee employeeMaX = list.stream().max(Comparator.comparing(Employee::getSalary)).orElse(null);
        assertEquals("Michael", employeeMaX.getName());
        Employee employeeMin = list.stream().min(Comparator.comparing(Employee::getSalary)).orElse(null);
        assertEquals("Alex", employeeMin.getName());
        double dMax = list.stream().mapToDouble(e -> e.getSalary()).max().orElse(0.00);
        assertTrue(dMax == 2000);
        double dMin = list.stream().mapToDouble(e -> e.getSalary()).min().orElse(0.00);
        assertTrue(dMin == 1000);
        double dAverage = list.stream().mapToDouble(e -> e.getSalary()).average().orElse(0.00);
        log.info("dAverage:{}", dAverage);
    }

    protected static final List<List<Employee>> listFlat = Arrays.asList(
            Arrays.asList(new Employee(1, "Alex", 1000),
                    new Employee(2, "Michael", 2000)),
            Arrays.asList(new Employee(3, "Jack", 1500),
                    new Employee(4, "Owen", 1500)),
            Arrays.asList(new Employee(5, "Denny", 2000)));
}
