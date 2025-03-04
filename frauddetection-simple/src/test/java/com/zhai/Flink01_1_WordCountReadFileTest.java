package com.zhai;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.connector.file.src.FileSource;
import org.apache.flink.connector.file.src.reader.TextLineInputFormat;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

/**
 *
 *
 * SourceFunction
 */
@Slf4j
public class Flink01_1_WordCountReadFileTest {
    public static final String DATA_PATH = "/opt/workspace/frauddetection/data";
    final static String first = DATA_PATH + "/flink01_test.txt";

    @BeforeAll
    static void initFlink(){

    }

    @Test
    void readFile() throws IOException {
        var lines = Files.readAllLines(Path.of(first));
        lines.forEach(log::info);
    }

    /**
     * 从文件获取数据
     * @throws Exception
     */
    @Test
    void WordCountTest() throws Exception {
//      StreamExecutionEnvironment env = StreamExecutionEnvironment.createRemoteEnvironment("192.168.13.107", 8081, "E:/study-flink-1.0-SNAPSHOT.jar");
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //指定为批处理模式(默认流处理模式)，否则由于（.slotSharingGroup）会导致程序一直hang.
        env.setRuntimeMode(RuntimeExecutionMode.BATCH);
        //读取文件
//      DataStreamSource<String> txtDataSource = env.socketTextStream("192.168.13.107", 7777);
        DataStreamSource<String> txtDataSource = env.readTextFile(first);
        //转换数据格式
        SingleOutputStreamOperator<Tuple2<String, Long>> singleOutputStreamOperator = txtDataSource
                .flatMap((String line, Collector<String> words) -> {
                    Arrays.stream(line.split(" ")).forEach(words::collect);
                })

                //不同之处
                .slotSharingGroup("1")
                .returns(Types.STRING)
                .map(word -> Tuple2.of(word, 1L))
                .returns(Types.TUPLE(Types.STRING, Types.LONG));// lambda 使用泛型，由于泛型擦除，需要显示的声明类型信息
        //分组
        KeyedStream<Tuple2<String, Long>, String> tuple2StringKeyedStream = singleOutputStreamOperator.keyBy(t -> t.f0);
        //求和
        SingleOutputStreamOperator<Tuple2<String, Long>> sum = tuple2StringKeyedStream.sum(1);
        //打印
        sum.print();
        //执行
        env.execute();
    }

    /**
     * 从文件获取数据， 和WordCountTest代码 略微不同
     * @throws Exception
     */
    @Test
    void WordCountBatchTest() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //读取文件
        FileSource<String> txtDataSource = FileSource.forRecordStreamFormat(
                        new TextLineInputFormat(), new org.apache.flink.core.fs.Path(first))
                .build();

        DataStreamSource<String> txtStream = env.fromSource(txtDataSource, WatermarkStrategy.noWatermarks(), "file source");
        //转换数据格式
        SingleOutputStreamOperator<Tuple2<String, Long>> singleOutputStreamOperator = txtStream
                .flatMap((String line, Collector<String> words) -> {
                    Arrays.stream(line.split(" ")).forEach(words::collect);
                })
                .returns(Types.STRING)
                .map(word -> Tuple2.of(word, 1L))
                //lambda 使用泛型，由于泛型擦除，需要显示的声明类型信息
                .returns(Types.TUPLE(Types.STRING, Types.LONG));
        //分组
        KeyedStream<Tuple2<String, Long>, String> tuple2StringKeyedStream = singleOutputStreamOperator.keyBy(t -> t.f0);
        //求和 (sum、min、max 可以用字段名称，也可以用字段顺序)
        SingleOutputStreamOperator<Tuple2<String, Long>> sum = tuple2StringKeyedStream.sum(1);
        //打印
        sum.print();
        //执行
        env.execute();
    }

    public static class WordWithCount {
        //两个变量存储输入的单词及其数量
        public String word;
        public long count;

        //无参构造必须要有的
        public WordWithCount() {}

        public WordWithCount(String word, long count) {
            this.word = word;
            this.count = count;
        }

        @Override
        public String toString() {
            return "[ " + word + " : " + count +" ]";
        }
    }

}