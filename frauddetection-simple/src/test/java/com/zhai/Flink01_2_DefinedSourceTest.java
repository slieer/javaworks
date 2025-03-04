package com.zhai;

import com.zhai.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.ParallelSourceFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.util.Collector;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 旧方式定义数据源
 */
public class Flink01_2_DefinedSourceTest {
    @Test
    void fromDataTest() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<User> dataStreamSource = env.fromData(
                new User("zs", "张三", 22),
                new User("ls", "李四", 23),
                new User("ww", "王五", 21),
                new User("ww", "王五", 22),
                new User("zl", "赵六", 27)
        );

        // map映射转换, filter过滤
        SingleOutputStreamOperator<String> operator = dataStreamSource
                .map(User::getName)
                .filter("ww"::equals)
                .flatMap(new FlatMapFunction<String, String>() {
                    @Override
                    public void flatMap(String value, Collector<String> out) throws Exception {
                        out.collect(value);
                    }
                });

        operator.print();
        env.execute();
    }

    /**
     * 从集合获取数据
     * @throws Exception
     */
    @Test
    void collectionSourceTest() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //fromCollection
        DataStreamSource<User> stringDataStreamSource = env.fromData(listUsers());
        stringDataStreamSource.print();
        env.execute();
    }

    /**
     * 并行度为3测试, 每一个线程调用数据源时，要确保存获取的数据不一样，避免重复处理
     * @throws Exception
     */
    @Test
    void parallelismTest() throws Exception {
//        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        DataStreamSource<User> stringDataStreamSource = env.addSource(new ParallelismSourceFunction());
        stringDataStreamSource.setParallelism(3);
        stringDataStreamSource.print();

        env.execute();
    }

    /**
     * 并行度为1测试
     * @throws Exception
     */
    @Test
    void singleParallelismTest() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        DataStreamSource<String> stringDataStreamSource = env.addSource(new SingleParallelismSourceFunction());

        stringDataStreamSource.print();
        env.execute();
    }


    /**
     * 源被调用三次，要确保每次获取的数据不一样。
     */
    @Slf4j
    public static class ParallelismSourceFunction implements ParallelSourceFunction<User> {
        private volatile boolean isRunning = true;


        @Override
        public void run(SourceContext<User> ctx) throws Exception {
            log.warn("MySourceFunction2.run start");
            for (int index = 0; index < 3 && isRunning; index++) {
                String s = RandomStringUtils.randomAlphabetic(4);
                User user = new User(s, s, 20 + index);
                ctx.collect(user);
            }
            log.warn("MySourceFunction2.run end");
        }

        @Override
        public void cancel() {
            log.warn("MySourceFunction2.run cancel");
            isRunning = false;
        }
    }

    /**
     * 并行度为1的source
     */
    @Slf4j
    public static class SingleParallelismSourceFunction implements SourceFunction<String> {
        private volatile boolean isRunning = true;

        @Override
        public void run(SourceContext<String> ctx) throws Exception {
            log.warn("MySourceFunction.run start");
            for (int index = 0; index < 100 && isRunning; index++) {
                Thread.sleep(50);
                ctx.collect(RandomStringUtils.randomAlphanumeric(4));
            }
            log.warn("MySourceFunction.run end");
        }

        /**
         * 界面点取消任务的时候执行
         */
        @Override
        public void cancel() {
            isRunning = false;
            log.warn("MySourceFunction.cancel");
        }

    }

    private static List<User> listUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("zs", "张三", 22));
        users.add(new User("ls", "李四", 23));
        users.add(new User("ww", "王五", 21));
        return users;
    }
}
