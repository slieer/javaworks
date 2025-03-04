package com.zhai;

import com.zhai.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class Flink02_1_MapTest {

    @Test
    void mapTest_3() throws Exception {
        var env = StreamExecutionEnvironment.getExecutionEnvironment();
        SingleOutputStreamOperator<UserInfo> sink = sourcePojo(env).map((MapFunction<UserInfo, UserInfo>) user -> {
            user.setAge(user.getAge() + 5);
            user.setBalance(user.getBalance() * 2);

            return user;
        })
                .filter(user -> user.getAge() > 20);
        sink.print();

        env.execute();
    }

    @Test
    void flatMapTest_1() throws Exception {
        var env = StreamExecutionEnvironment.getExecutionEnvironment();
        //create ds
        val ds = env.fromData("hadoop is good", "spark is fast", "flink is better");
        ds.flatMap((FlatMapFunction<String, String>) (value, out) -> {
                    var arr = value.split(" ");
                    Arrays.stream(arr).forEach(out::collect);
                })
                .returns(String.class)
                .filter((FilterFunction<String>) value -> value.length() > 4)
                .map(StringBuilder::new)
                .map((MapFunction<StringBuilder, String>) value -> {
                    String firstUpperCase = String.valueOf(value.charAt(0)).toUpperCase();
                    return value.replace(0,1, firstUpperCase).toString();
                })
                .print();

        env.execute();

    }

    @Test
    void flatMapTest_2() throws Exception {
        var env = StreamExecutionEnvironment.getExecutionEnvironment();
        var source = sourcePojo(env);
        source.flatMap((FlatMapFunction<UserInfo, String>) (t, out) -> {
                    out.collect(t.getName());
                    out.collect(t.getAlias());
                    out.collect(t.getEmail());
                }).returns(String.class)
                .print();

        env.execute();
    }

    // 构造User数据源
    public static DataStreamSource<UserInfo> sourcePojo(StreamExecutionEnvironment env) {
        List<UserInfo> list = Arrays.asList(
                new UserInfo(1L, "alan1", "alias1", 10, "11", "1@1.com", 1000),
                new UserInfo(2L, "alan2", "alias2", 19, "20", "2@2.com", 200),
                new UserInfo(3L, "alan3", "alias3", 28, "22", "3@3.com", 1500),
                new UserInfo(5L, "alan4", "alias4", 15, "20", "5@5.com", 500),
                new UserInfo(4L, "alan5", "alias2", 30, "2020", "4@4.com", 400));
        var source = env.fromData(list);
        return source;
    }
}

