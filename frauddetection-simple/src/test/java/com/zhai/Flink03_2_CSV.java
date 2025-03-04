package com.zhai;


import com.alibaba.fastjson2.JSONObject;
import com.zhai.entity.JsonFile;
import com.zhai.entity.UserClickLog;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.connector.file.src.FileSource;
import org.apache.flink.connector.file.src.reader.TextLineInputFormat;
import org.apache.flink.core.fs.Path;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.zhai.Flink01_1_WordCountReadFileTest.DATA_PATH;


/**
 *<br>Flink 的分布在不同节点上的 Task 的数据传输必须经过序列化/反序列化，因此序列化/反序列化也是影响 Flink 性能的一个重要因素。
 *<br>Flink 自有一套类型体系，即 Flink 有自己的类型描述类（TypeInformation）。
 *<br>Flink 希望能够掌握尽可能多的进出 operator 的数据类型信息，并使用 TypeInformation 来描述，这样做主要有以下 2 个原因：
 *
 *<li>类型信息知道的越多，Flink 可以选取更好的序列化方式，并使得 Flink 对内存的使用更加高效；
 *<li>TypeInformation 内部封装了自己的序列化器，可通过 createSerializer() 获取，<br>
 *这样可以让用户不再操心序列化框架的使用（例如如何将他们自定义的类型注册到序列化框架中，尽管用户的定制化和注册可以提高性能）。
 *
 *<p></p>
 *<br>总体上来说，Flink 推荐我们在 operator 间传递的数据是 POJOs 类型，对于 POJOs 类型，Flink 默认会使用 Flink 自身的 PojoSerializer 进行序列化，
 *<br>而对于 Flink 无法自己描述或推断的数据类型，Flink 会将其识别为 GenericType，并使用 Kryo 进行序列化。
 *<br>Flink 在处理 POJOs 时更高效，此外 POJOs 类型会使得 stream 的 grouping/joining/aggregating 等操作变得简单。
 *
 *
 *
 *<br>WatermarkStrategy 接口继承了 TimestampAssignerSupplier 和 WatermarkGeneratorSupplier，即相当于包含了两者，具体地：
 *
 *<li>TimestampAssigner 用于从消息记录中提取事件时间戳
 *<li>WatermarkGenerator 用于根据事件时间戳生成 watermark
 *
 *<br>水位管理策略包含： 事件时间戳生成器， 水位生成器。
 *
 *<br>WatermarkStrategy 可以在 Flink 应用程序中的两处使用，第一种是在数据源上使用，第二种是在非数据源的操作之后使用。
 *<br>第一种方式更好，因为数据源可以利用 watermark 生成逻辑中有关分片/分区（shards/partitions/splits）的信息，
 *<br>数据源可以更精准地跟踪 watermark，整体 watermark 生成将更精确；直接在源上指定 WatermarkStrategy 意味着必须使用特定数据源接口
 *
 *<br>仅当无法直接在数据源上设置策略时，才应该使用第二种方式（在任意转换操作之后设置 WatermarkStrategy）
 *
 *
 *<br>WatermarkStrategy的实践建议
 *<br>选择合适的WatermarkStrategy：根据数据流的特点选择合适的WatermarkStrategy。
 *<br>如果事件时间戳是单调递增的，可以选择ForMonotonicTimestampsWatermarkStrategy；如果事件时间戳可能存在乱序，可以选择BoundedOutOfOrdernessWatermarkStrategy。
 *<br>合理设置延迟阈值：对于BoundedOutOfOrdernessWatermarkStrategy，合理设置延迟阈值可以避免过早触发窗口计算，同时也不会延迟太久导致延迟敏感型任务受到影响。
 *<br>监控Watermark生成情况：通过Flink提供的监控指标，可以观察Watermark的生成情况，以便及时发现并解决问题。
 */
public class Flink03_2_CSV {

    @Test
    public void test() {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        String gbkFilePath = DATA_PATH + "/simple.etl.csv";
        String utf8FilePath = DATA_PATH + "/converted_simple.etl.csv"; 

        convertAndReadGbkFile(gbkFilePath, utf8FilePath);

        File file = new File(utf8FilePath);
        var textFile = FileSource.forRecordStreamFormat(new TextLineInputFormat(), Path.fromLocalFile(file)).build();
        DataStreamSource<String> data = env.fromSource(textFile, WatermarkStrategy.noWatermarks(), "txt-file");
        SingleOutputStreamOperator<UserClickLog> flatMapSource1 = getUserClickLogSingleOutputStreamOperator_(data);

        //flatMapSource.print();
        SingleOutputStreamOperator<UserClickLog> stream = flatMapSource1.assignTimestampsAndWatermarks(WatermarkStrategy.<UserClickLog>forBoundedOutOfOrderness(Duration.ofSeconds(5))
                .withTimestampAssigner(new SerializableTimestampAssigner<UserClickLog>() {
                    @Override
                    public long extractTimestamp(UserClickLog userClickLog, long l) {
                        String[] parts = userClickLog.getTimestamp().split("\\.");
                        String[] timeParts = parts[0].split(":");
                        if (timeParts[2].length() == 1) {
                            timeParts[2] = "0" + timeParts[2];
                        }else if(Integer.parseInt(timeParts[2]) >= 60){
                            String temp = Integer.parseInt(timeParts[2]) - 60 + "";
                            if(temp.length() == 1) timeParts[2] = temp + "0";
                            else timeParts[2] = temp;
                            timeParts[1] = Integer.parseInt(timeParts[1]) + 1 + "";
                        }
                        String dateTimeStr = String.join(":", timeParts) + "." + parts[1];
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeStr, formatter);
                        // ��LocalDateTimeת��Ϊ����
                        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

                        long timestamp = date.getTime();
                        return timestamp;
                    }
                }));
//        stream.print();
        stream.map((MapFunction<UserClickLog, Tuple2<Integer, Long>>) userClickLog -> Tuple2.of(userClickLog.jsonField.getLevel(), 1L))
                .returns(Types.TUPLE(Types.INT, Types.LONG))
                .keyBy(tuple -> tuple.f0)
                .window(TumblingEventTimeWindows.of(Duration.ofSeconds(3)))
                .process(new ProcessWindowFunction<Tuple2<Integer, Long>, String, Integer, TimeWindow>() {
                    @Override
                    public void process(Integer integer, ProcessWindowFunction<Tuple2<Integer, Long>, String, Integer, TimeWindow>.Context context, Iterable<Tuple2<Integer, Long>> iterable, Collector<String> collector) throws Exception {
                        long count = 0L;
                        for (Tuple2<Integer, Long> integerLongTuple2 : iterable) {
                            count++;
                        }
                        long start = context.window().getStart();
                        collector.collect("TimeWindow: " + start + " level: " + integer + " count: " + count);
                    }
                })
                .print();

        //new File(utf8FilePath).delete();
        try {
            env.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static SingleOutputStreamOperator<UserClickLog> getUserClickLogSingleOutputStreamOperator_(DataStreamSource<String> dataStreamSource) {
        SingleOutputStreamOperator<UserClickLog> flatMapSource = dataStreamSource.flatMap(new FlatMapFunction<String, UserClickLog>() {
            @Override
            public void flatMap(String value, Collector<UserClickLog> collector) throws Exception {
                if (value.contains("{") && value.charAt(value.length() - 1) != ',') {
                    String[] split = value.split(",", value.indexOf("{"));
                    String jsonStr;
                    jsonStr = value.substring(value.indexOf("{"), value.lastIndexOf("}") + 1);
                    String jsonData = repairJsonStr(jsonStr);
                    ObjectMapper mapper = new ObjectMapper();
                    JsonFile jsonFile = mapper.readValue(jsonData, JsonFile.class);
                    UserClickLog data = new UserClickLog();
                    //data.jsonField = jsonStr;
                    if (split.length > 0) {
                        data.timestamp = split[0];
                        data.id = split[1];
                        data.prdCode = split[2];
                        data.code = split[3];
                        data.email = split[4];
                        data.province = split[5];
                        data.ts = Double.parseDouble(split[6]);
                        data.jsonField = jsonFile;
                    }
                    collector.collect(data);
                }
            }
        });
        return flatMapSource;
    }

    @Test
    public void strSplit() {
        String line = "2021-12-31 17:35:0.325,23460191,PCM,7894,tigxbuncztlx5u3@gmail.com,山东,188.81,\"{\\\"ismain\\\":1\",level:34,ts:77,ver:29}";
        String jsonPart = line.substring(line.indexOf("{"), line.lastIndexOf("}") + 1); // 提取JSON字符串
        jsonPart = jsonPart.replace("\\\"", "\""); // 解决转义字符问题

//        ObjectMapper objectMapper = new ObjectMapper();
//        Map<String, Object> jsonMap = objectMapper.readValue(jsonPart, HashMap.class); // 解析为Map
//        String formattedJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonMap); // 格式化为缩进的JSON字符串
    }

    public static String repairJsonStr(String jsonStr){
        try {
            String correctedJson = jsonStr.replaceAll("\"", "")
                    .replaceAll("\\{", "")
                    .replaceAll("\\}", "");

            JSONObject jsonObject = new JSONObject();
            String[] keyValuePairs = correctedJson.split(",");
            for (String pair : keyValuePairs) {
                String[] keyValue = pair.split(":");
                String key = keyValue[0].trim(); // �Ƴ�����Χ��˫����
                String value = keyValue[1].trim();
                jsonObject.put(key, value);
            }
            String fixedJson = jsonObject.toString();
            //System.out.println(fixedJson);
            return fixedJson;
        } catch (Exception e) {
            throw new RuntimeException("Error fixing JSON string", e);
        }

    }
    public static void convertAndReadGbkFile(String gbkFilePath, String utf8FilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(gbkFilePath, Charset.forName("GBK")));
             BufferedWriter writer = new BufferedWriter(new FileWriter(utf8FilePath, StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}