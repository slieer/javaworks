package com.zhai;

import com.zhai.entity.Person;
import com.zhai.entity.UserInfo;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class Flink02_4_KeybyReduceAggregationsTest {
    @Test
    public void reduceTest() throws Exception {
        var env = StreamExecutionEnvironment.getExecutionEnvironment();
//		env.setParallelism(4);// 设置数据分区数量
        var source = source(env);

        KeyedStream<UserInfo, String> userInfoKeyBy = source.keyBy(Person::getName);
        SingleOutputStreamOperator<UserInfo> sink = userInfoKeyBy.reduce((userInfo1, userInfo2) -> {
            userInfo1.setBalance(userInfo1.getBalance() + userInfo2.getBalance());
            return userInfo1;
        });
        sink.print();
        env.execute();
    }

    @Test
    public void aggregationsTest() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        aggregationsFunction(env);
        env.execute();
    }

    @Test
    public void aggregations_1_Test() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        aggregationsFunction2(env);
        env.execute();
    }

    // 构造UserInfo数据源
    public static DataStreamSource<UserInfo> source(StreamExecutionEnvironment env) {
        DataStreamSource<UserInfo> source = env.fromCollection(Arrays.asList(
                new UserInfo(1L, "alan1", 12,"1@1.com", 1000),
                new UserInfo(2L, "alan2", 19,"2@2.com", 200),
                new UserInfo(3L, "alan1", 28,"3@3.com", 1500),
                new UserInfo(5L, "alan1", 15,"5@5.com", 500),
                new UserInfo(4L, "alan2", 30,"4@4.com", 400)));
        return source;
    }

    //分组统计sum、max、min、maxby、minby
    public void aggregationsFunction(StreamExecutionEnvironment env) throws Exception {
        DataStreamSource<UserInfo> source = source(env);

        KeyedStream<UserInfo, String> UserInfoTemp=	source.keyBy(UserInfo->UserInfo.getName());
        DataStream sink = null;

        sink = UserInfoTemp.sum("balance");
        sink = UserInfoTemp.max("balance");//1@1.com-3000 --  2@2.com-300
        sink = UserInfoTemp.min("balance");
        sink = UserInfoTemp.maxBy("balance");
        sink = UserInfoTemp.minBy("balance");

        sink.print();
    }

    public static void aggregationsFunction2(StreamExecutionEnvironment env) throws Exception {
        var list = new ArrayList<Tuple3<Integer, Integer, Integer>>();
        list.add(new Tuple3<>(0,3,6));
        list.add(new Tuple3<>(0,2,5));
        list.add(new Tuple3<>(0,1,6));
        list.add(new Tuple3<>(0,4,3));
        list.add(new Tuple3<>(1,1,9));
        list.add(new Tuple3<>(1,2,8));
        list.add(new Tuple3<>(1,3,10));
        list.add(new Tuple3<>(1,2,9));
        list.add(new Tuple3<>(1,5,7));
        DataStreamSource<Tuple3<Integer, Integer, Integer>> source = env.fromCollection(list);
        KeyedStream<Tuple3<Integer, Integer, Integer>, Integer> tTemp=  source.keyBy(t->t.f0);
        DataStream<Tuple3<Integer, Integer, Integer>> sink =null;

        //按照分区，以第一个Tuple3的元素为基础进行第三列值比较，如果第三列值小于第一个tuple3的第三列值，则进行第三列值替换，其他的不变
//        12> (0,3,6)
//        11> (1,1,9)
//        11> (1,1,8)
//        12> (0,3,5)
//        11> (1,1,8)
//        12> (0,3,5)
//        11> (1,1,8)
//        12> (0,3,3)
//        11> (1,1,7)
        sink =  tTemp.min(2);

//     按照数据分区，以第一个tuple3的元素为基础进行第三列值比较，如果第三列值小于第一个tuple3的第三列值，则进行整个tuple3的替换
//     12> (0,3,6)
//     11> (1,1,9)
//     12> (0,2,5)
//     11> (1,2,8)
//     12> (0,2,5)
//     11> (1,2,8)
//     12> (0,4,3)
//     11> (1,2,8)
//     11> (1,5,7)
        sink = tTemp.minBy(2);
        sink.print();
    }
}
