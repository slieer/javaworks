package com.zhai;

import com.zhai.entity.Person;
import com.zhai.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.AllWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.userDefined.EndOfStreamWindows;
import org.apache.flink.util.Collector;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class Flink02_3_KeyByTest {
    @Test
    void keyByTest() throws Exception {
        var env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<Tuple2> dataStreamSource = sourceTuple2StringDigit(env);

//        dataStreamSource.keyBy(r -> r.f0).sum("f1").print();
//        dataStreamSource.keyBy(r -> r.f0).maxBy("f1").print();
        dataStreamSource.keyBy(r -> r.f0).max("f1").print();

        env.execute();
    }

    @Test
    void keyByTest_1() throws Exception {
        var env = StreamExecutionEnvironment.getExecutionEnvironment();
        var source = sourceStringLine(env);

        KeyedStream<String, String> keyedStream =source.keyBy((KeySelector<String, String>) s -> s.split(",")[0]);
        keyedStream.print();
        env.execute("Flink Filter Example");
    }

    @Test
    void keyByTest_2() throws Exception {
        var env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> stringList = sourceStringLine(env);
        DataStream<Person> mapToObj = stringList.map((MapFunction<String, Person>) s -> {
            String[] array = s.split(",");
            Person person = new Person();
            person.setName(array[0]);
            person.setAge(Integer.parseInt(array[1]));
            return person;
        });
        KeyedStream<Person, String> groupBy = mapToObj.keyBy((KeySelector<Person, String>) Person::getName);
        groupBy.maxBy("age")
                .print();
        env.execute("Flink Filter Example");
    }

    @Test
    void keyByTest_3() throws Exception {
        var env = StreamExecutionEnvironment.getExecutionEnvironment();
        final var tupleStream = sourceTuple3Digit(env);

        val sumStream = tupleStream
                .keyBy((KeySelector<Tuple3<Integer, Integer, Integer>, Object>) value -> value.f0)
                .sum(2)
                .print();

        env.execute();
    }

    @Test
    void keyByReduceMapTest_4() throws Exception {
        var env = StreamExecutionEnvironment.getExecutionEnvironment();
        var source = sourceTuple(env);
        source.keyBy((KeySelector<Tuple3<String, String, Integer>, String>) tuple3 -> tuple3.f0)
                .reduce((ReduceFunction<Tuple3<String, String, Integer>>) (value1, value2) -> {
                    value2.f2 = value2.f2 + value1.f2;
                    return value2;
                })
                .map(t -> Tuple2.of(t.f0, t.f2))
                .returns(Types.TUPLE(Types.STRING, Types.INT))
                .print()
        ;
        env.execute();
    }

    @Test
    public void distinctFunction() throws Exception {
        var list = new ArrayList<Tuple3<Integer, Integer, Integer>>();
        list.add(new Tuple3<>(0, 3, 6));
        list.add(new Tuple3<>(0, 2, 5));
        list.add(new Tuple3<>(0, 3, 6));
        list.add(new Tuple3<>(1, 1, 9));
        list.add(new Tuple3<>(1, 2, 8));
        list.add(new Tuple3<>(1, 2, 8));
        list.add(new Tuple3<>(1, 3, 9));

        var env = StreamExecutionEnvironment.getExecutionEnvironment();
        var dataStream = env.fromData(list);
        // 去除tuple3中元素完全一样的
        var keyedStream = dataStream.keyBy(new KeySelector<Tuple3<Integer, Integer, Integer>, Object>  () {
                    @Override
                    public Object getKey(Tuple3<Integer, Integer, Integer> value) throws Exception {
                        return Objects.hash(value.f0, value.f1, value.f2);
                    }
                })
                .reduce((value1, value2) -> value1);

        keyedStream.print();
        env.execute();
    }

    @Test
    public void distinctFunction2() throws Exception {
        List<UserInfo> list = Arrays.asList(
                new UserInfo(1L, "alan1", 18, "1@1.com", 3000),
                new UserInfo(2L, "alan2", 19, "2@2.com", 200),
                new UserInfo(3L, "alan1", 18, "3@3.com", 1000),
                new UserInfo(5L, "alan1", 28, "5@5.com", 1500),
                new UserInfo(4L, "alan2", 20, "4@4.com", 300));

        var env = StreamExecutionEnvironment.getExecutionEnvironment();
        var dataStream = env.fromData(list);
        var keyedStream = dataStream.keyBy(new KeySelector<UserInfo, String>() {
                    @Override
                    public String getKey(UserInfo value) throws Exception {
                        return value.getName();
                    }
                })
                .reduce((value1, value2) -> value1);

        keyedStream.print();
        env.execute();
    }

    /**
     * first N
     * @throws Exception
     */
    @Test
    public void first_N_Function() throws Exception {
        List<Tuple2<Integer, String>> info = new ArrayList<>();
        info.add(new Tuple2<>(1, "Hadoop"));
        info.add(new Tuple2<>(1, "Spark"));
        info.add(new Tuple2<>(1, "Flink"));
        info.add(new Tuple2<>(2, "Scala"));
        info.add(new Tuple2<>(2, "Java"));
        info.add(new Tuple2<>(2, "Python"));
        info.add(new Tuple2<>(3, "Linux"));
        info.add(new Tuple2<>(3, "Window"));
        info.add(new Tuple2<>(3, "MacOS"));

        var env = StreamExecutionEnvironment.getExecutionEnvironment();
        var dataSet = env.fromData(info);

        AtomicInteger i = new AtomicInteger(0);
        var firstN = 3;
        dataSet.windowAll(EndOfStreamWindows.get())
                .apply(new AllWindowFunction<>(){
                    @Override
                    public void apply(TimeWindow window, Iterable<Tuple2<Integer, String>> values, Collector<Object> out) throws Exception {
                        //                            log.warn("start：{}, end:{}", window.getStart(), window.getEnd());
                        for (Iterator<Tuple2<Integer, String>> iterator = values.iterator(); iterator.hasNext(); ) {
                            Tuple2<Integer, String> t = iterator.next();
                            log.warn("-------：{}, firstN:{}", i.get(), firstN);
                            if (i.get() < firstN) {
                                out.collect(t);
                            }
                            i.getAndIncrement();
                        }
                    }
                });
        dataSet.print();
        // 按照tpule2的第一个元素进行分组，并按照倒序排列，查出每组的前2个
        //dataSet.groupBy(0).sortGroup(1, Order.DESCENDING).first(2).print();
        env.execute();
    }

    private static DataStreamSource<Tuple2> sourceTuple2StringDigit(StreamExecutionEnvironment env) {
        DataStreamSource<Tuple2> dataStreamSource = env.fromData(
                Tuple2.of("a", 1),
                Tuple2.of("a", 2),
                Tuple2.of("a", 3),
                Tuple2.of("b", 5),
                Tuple2.of("b", 10),
                Tuple2.of("c", 3),
                Tuple2.of("c", 6),
                Tuple2.of("d", 4),
                Tuple2.of("d", 8)
        );
        return dataStreamSource;
    }

    /**
     * example:  tom,23
     * @param env
     * @return
     */
    private static DataStreamSource<String> sourceStringLine(StreamExecutionEnvironment env) {
        DataStreamSource<String> stringList = env.fromData(List.of(
                "tom,23",
                "rose,28",
                "tom,25",
                "tom,23",
                "rose,48",
                "tom,28",
                "tom,30",
                "rose,50",
                "tom,35"
        ));
        return stringList;
    }
    public static DataStreamSource<Tuple3<Integer, Integer, Integer>> sourceTuple3Digit(StreamExecutionEnvironment env) {
        val tupleStream = env.fromData(
                Tuple3.of(0, 0, 0),
                Tuple3.of(0, 1, 1),
                Tuple3.of(0, 2, 2),
                Tuple3.of(1, 0, 6),
                Tuple3.of(1, 1, 7),
                Tuple3.of(1, 2, 8)
        );
        return tupleStream;
    }

    public static DataStreamSource<Tuple3<String, String, Integer>> sourceTuple(StreamExecutionEnvironment env) {
        List<Tuple3<String, String, Integer>> list = Arrays.asList(Tuple3.of("Li", "Chinese", 90),
                Tuple3.of("Wang", "Chinese", 88),
                Tuple3.of("Li", "Math", 85),
                Tuple3.of("Wang", "Math", 92),
                Tuple3.of("Liu", "Math", 91),
                Tuple3.of("Liu", "Chinese", 87));
        val source = env.fromData(list);
        return source;
    }
}
