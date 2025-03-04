package com.zhai;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.flink.api.common.functions.CoGroupFunction;
import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.*;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;
import org.apache.flink.userDefined.EndOfStreamWindows;
import org.apache.flink.util.Collector;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.apache.flink.table.api.Expressions.$;

/**
 * https://nightlies.apache.org/flink/flink-docs-release-1.19/zh/docs/dev/datastream/dataset_migration
 */
@Slf4j
public class Flink02_5_WindowTest {
    @Test
    public void unionTest() throws Exception {
        var env = StreamExecutionEnvironment.getExecutionEnvironment();

        List<String> info1 = new ArrayList<>();
        info1.add("team A");
        info1.add("team B");

        List<String> info2 = new ArrayList<>();
        info2.add("team C");
        info2.add("team D");

        List<String> info3 = new ArrayList<>();
        info3.add("team E");
        info3.add("team F");

        List<String> info4 = new ArrayList<>();
        info4.add("team G");
        info4.add("team H");

        DataStream<String> source1 = env.fromData(info1);
        DataStream<String> source2 = env.fromData(info2);
        DataStream<String> source3 = env.fromData(info3);
        DataStream<String> source4 = env.fromData(info4);

        source1
                .union(source2)
                .union(source3)
                .union(source4)
                .print();
        env.execute();
    }

    @Test
    public void cross_1_Test() {
         // 初始化数据
         List<String> info1 = new ArrayList<>();
         info1.add("team A");
         info1.add("team B");

         List<Tuple2<String, Integer>> info2 = new ArrayList<>();
         info2.add(new Tuple2<>("W", 3));
         info2.add(new Tuple2<>("D", 1));
         info2.add(new Tuple2<>("L", 0));

         // 获取执行环境
         EnvironmentSettings settings = EnvironmentSettings.newInstance().inBatchMode().build();
         TableEnvironment tableEnv = TableEnvironment.create(settings);

         // 创建表
         Table table1 = tableEnv.fromValues(DataTypes.STRING(), info1.stream().map(Row::of).toArray(Row[]::new));
         Table table2 = tableEnv.fromValues(DataTypes.ROW(
                 DataTypes.FIELD("col1", DataTypes.STRING()),
                 DataTypes.FIELD("col2", DataTypes.INT())
         ), info2.stream().map(t -> Row.of(t.f0, t.f1)).toArray(Row[]::new));

         // 计算笛卡尔积
         Table result = table1.join(table2);
        result.execute().print();

    }

    @Test
    public void cross_2_test(){
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Tuple2<Integer, String>> stream1 = env.fromData(
                new Tuple2<>(1, "hello"),
                new Tuple2<>(10, "Full sized helicopter"));
        DataStream<Tuple2<Integer, String>> stream2 = env.fromData(
                new Tuple2<>(1, "hello"));


        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);
        Table table11 = tEnv.fromDataStream(stream1, Schema.newBuilder()
                .column("f0", DataTypes.INT()) // 定义第一个字段，类型为字符串
                .column("f1", DataTypes.STRING())
                .build());
        Table table21 = tEnv.fromDataStream(stream2, Schema.newBuilder()
                .column("f0", DataTypes.INT()) // 定义第一个字段，类型为字符串
                .column("f1", DataTypes.STRING())
                .build());

        Table tableResult = table11
                .where($("f1").like("F%"))
                .unionAll(table21);
        tableResult.execute().print();

        log.info("------------------------------------------------------------------------------------");
//        {
//            StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);
//            // Table API
//            Table table1 = tEnv.fromDataStream(stream1, $("count"), $("word"));
//            Table table2 = tEnv.fromDataStream(stream2, $("count"), $("word"));
//            Table tableResult1 = table1
//                    .where($("word").like("F%"))
//                    .unionAll(table2);
//
//            //System.out.println(table.explain());
//            tableResult1.execute().print();
//        }
    }

    @Test
    public void outerJoinTest() throws Exception {
        // Outjoin,跟sql语句中的left join,right join,full join意思一样
        // leftOuterJoin,跟join一样，但是左边集合的没有关联上的结果也会取出来,没关联上的右边为null
        // rightOuterJoin,跟join一样,但是右边集合的没有关联上的结果也会取出来,没关联上的左边为null
        // fullOuterJoin,跟join一样,但是两个集合没有关联上的结果也会取出来,没关联上的一边为null
        List<Tuple2<Integer, String>> info1 = new ArrayList<>();
        info1.add(new Tuple2<>(1, "shenzhen"));
        info1.add(new Tuple2<>(2, "guangzhou"));
        info1.add(new Tuple2<>(3, "shanghai"));
        info1.add(new Tuple2<>(4, "chengdu"));

        List<Tuple2<Integer, String>> info2 = new ArrayList<>();
        info2.add(new Tuple2<>(1, "深圳"));
        info2.add(new Tuple2<>(2, "广州"));
        info2.add(new Tuple2<>(3, "上海"));
        info2.add(new Tuple2<>(5, "杭州"));

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        var data1 = env.fromData(info1);
        var data2 = env.fromData(info2);

        //leftOuterJoin
        data1.coGroup(data2)
                .where(value -> value.f0).equalTo(value -> value.f0)
                .window(EndOfStreamWindows.get())
                .apply(new CoGroupFunction<Tuple2<Integer, String>, Tuple2<Integer, String>, List<Tuple2<Integer, String>>>() {
                    @Override
                    public void coGroup(Iterable<Tuple2<Integer, String>> first, Iterable<Tuple2<Integer, String>> second, Collector<List<Tuple2<Integer, String>>> out) throws Exception {
                        Iterator<Tuple2<Integer, String>> firstIterator = first.iterator();
                        Iterator<Tuple2<Integer, String>> secondiIterator = second.iterator();

                        if(!secondiIterator.hasNext()){
                            out.collect(Lists.newArrayList(first.iterator()));
                        }else{
                            var list = Stream.of(first.iterator(), second.iterator()).flatMap(t -> StreamSupport.stream(
                                    Spliterators.spliteratorUnknownSize(t, Spliterator.ORDERED),
                                    false)).collect(Collectors.toList());
                            out.collect(list);
                        }

                    }
                }).print();

        env.execute();

        //rightOuterJoin
//        data1.coGroup(data2).where(0).equalTo(0).with(new JoinFunction<Tuple2<Integer, String>, Tuple2<Integer, String>, Tuple3<Integer, String, String>>() {
//
//            @Override
//            public Tuple3<Integer, String, String> join(Tuple2<Integer, String> first, Tuple2<Integer, String> second) throws Exception {
//                Tuple3<Integer, String, String> tuple = new Tuple3();
//                if (first == null) {
//                    tuple.setField(second.f0, 0);
//                    tuple.setField("--", 1);
//                    tuple.setField(second.f1, 2);
//                } else {
//                    // 另外一种赋值方式，和直接用构造函数赋值相同
//                    tuple.setField(first.f0, 0);
//                    tuple.setField(first.f1, 1);
//                    tuple.setField(second.f1, 2);
//                }
//                return tuple;
//            }
//        }).print("right join");
    }

    @Test
    public void joinFunction() throws Exception {
        List<Tuple2<Integer, String>> info1 = new ArrayList<>();
        info1.add(new Tuple2<>(1, "shenzhen"));
        info1.add(new Tuple2<>(2, "guangzhou"));
        info1.add(new Tuple2<>(3, "shanghai"));
        info1.add(new Tuple2<>(4, "chengdu"));

        List<Tuple2<Integer, String>> info2 = new ArrayList<>();
        info2.add(new Tuple2<>(1, "深圳"));
        info2.add(new Tuple2<>(2, "广州"));
        info2.add(new Tuple2<>(3, "上海"));
        info2.add(new Tuple2<>(5, "杭州"));

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        var data1 = env.fromData(info1);
        var data2 = env.fromData(info2);

        data1.join(data2)
                .where(value -> value.f0).equalTo(value -> value.f0)
                .window(EndOfStreamWindows.get())
                .apply(new JoinFunction<Tuple2<Integer, String>, Tuple2<Integer, String>, List<Tuple2<Integer, String>>>(){
                    @Override
                    public List<Tuple2<Integer, String>> join(Tuple2<Integer, String> first, Tuple2<Integer, String> second) throws Exception {
                        return Stream.of(first, second).collect(Collectors.toList());
                    }
                }).print();

        env.execute();

        //
////        join2:2> (3,上海,shanghai)
////        join2:7> (1,深圳,shenzhen)
////        join2:16> (2,广州,guangzhou)
//        DataSet<Tuple3<Integer, String, String>> data3 = data1.join(data2).where(0).equalTo(0)
//                .with(new JoinFunction<Tuple2<Integer, String>, Tuple2<Integer, String>, Tuple3<Integer, String, String>>() {
//
//                    @Override
//                    public Tuple3<Integer, String, String> join(Tuple2<Integer, String> first, Tuple2<Integer, String> second) throws Exception {
//                        return new Tuple3<Integer, String, String>(first.f0, second.f1, first.f1);
//                    }
//                });
//        data3.print("join2");
//
    }
}
