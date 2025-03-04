package com.zhai;

import com.zhai.entity.City;
import com.zhai.entity.DicCodeImport;
import com.zhai.entity.Person;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.connector.file.src.FileSource;
import org.apache.flink.core.fs.Path;
import org.apache.flink.formats.csv.CsvReaderFormat;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.dataformat.csv.CsvMapper;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.function.SerializableFunction;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.zhai.Flink01_1_WordCountReadFileTest.DATA_PATH;

public class Flink03_3_CSV {

    @Test
    public void test_1() {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        CsvReaderFormat<DicCodeImport> csvFormat =
                CsvReaderFormat.<DicCodeImport>forSchema(
                        CsvMapper::new,
                        mapper -> mapper.schemaFor(DicCodeImport.class).withColumnSeparator(','),
                        TypeInformation.of(DicCodeImport.class)
                );

        FileSource<DicCodeImport> source =
                FileSource.forRecordStreamFormat(csvFormat, Path.fromLocalFile(new File(DATA_PATH + "/data.csv"))).build();
        DataStreamSource<DicCodeImport> csvSource = env.fromSource(source, WatermarkStrategy.noWatermarks(), "CsvSource");
        csvSource.print();

        try {
            env.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void keyByTest_2() throws Exception {
        String pathname = DATA_PATH + "/flink_02_1.csv";
        CsvReaderFormat<Person> csvFormat = CsvReaderFormat.forPojo(Person.class);

        FileSource<Person> source =
                FileSource.forRecordStreamFormat(csvFormat, Path.fromLocalFile(new File(pathname))).build();

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<Person> dataStreamSource = env.fromSource(source, WatermarkStrategy.noWatermarks(), "csv");

        DataStream<Tuple2<String, Integer>> mapOp = dataStreamSource.map(new MapFunction<Person, Tuple2<String, Integer>>() {
            @Override
            public Tuple2<String, Integer> map(Person s) throws Exception {
                return new Tuple2<>(s.getName(), s.getAge());
            }
        });
        KeyedStream<Tuple2<String, Integer>, String> groupByOp = mapOp.keyBy(new KeySelector<Tuple2<String, Integer>, String>() {
            @Override
            public String getKey(Tuple2<String, Integer> tp) throws Exception {
                return tp.f0;
            }
        });

        groupByOp.print();
        env.execute("Flink Filter Example");
    }

    @Test
    void test_3() throws Exception {
        String pathname = DATA_PATH + "/city.csv";

        SerializableFunction<CsvMapper, CsvSchema> schemaGenerator = mapper ->
                mapper.schemaFor(City.class)
                        //.withSkipFirstDataRow(true)
                        .withQuoteChar('"')
                        .withColumnSeparator('|')
                        .withHeader()
                ;
        CsvReaderFormat<City> csvFormat =
                CsvReaderFormat.forSchema(CsvMapper::new, schemaGenerator, TypeInformation.of(City.class));

        FileSource<City> source =
                FileSource.forRecordStreamFormat(csvFormat, Path.fromLocalFile(new File(pathname))).build();

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<City> dataStreamSource = env.fromSource(source, WatermarkStrategy.noWatermarks(), "csv");

//        dataStreamSource.map(t -> t.city)
//                .returns(String.class)
//                .print();

        dataStreamSource.map(t -> t)
                .print();

        env.execute("test-job");
    }

}
