package com.zhai;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.connector.datagen.table.DataGenConnectorOptions;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.*;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.junit.jupiter.api.Test;

@Slf4j
public class Flink04_1_SQL {
    @Test
    void testTable_1() {
        val settings = EnvironmentSettings.newInstance()
                .build();
        // Create a TableEnvironment for batch or streaming execution.
        // See the "Create a TableEnvironment" section for details.
        TableEnvironment tableEnv = TableEnvironment.create(settings);

        // Create a source table
        tableEnv.createTemporaryTable("SourceTable", TableDescriptor.forConnector("datagen")
                .schema(Schema.newBuilder()
                        .column("f0", DataTypes.STRING())
                        .build())
                .option(DataGenConnectorOptions.ROWS_PER_SECOND, 100L)
                .build());

        // Create a sink table (using SQL DDL)
        tableEnv.executeSql("CREATE TEMPORARY TABLE SinkTable WITH ('connector' = 'blackhole') LIKE SourceTable (EXCLUDING OPTIONS) ");

        // Create a Table object from a Table API query
        Table table1 = tableEnv.from("SourceTable");

        // Create a Table object from a SQL query
        Table table2 = tableEnv.sqlQuery("SELECT * FROM SourceTable");

        // Emit a Table API result Table to a TableSink, same for SQL result
        TableResult tableResult = table1.insertInto("SinkTable").execute();
        tableResult.print();
    }

    /**
     * https://nightlies.apache.org/flink/flink-docs-master/zh/docs/connectors/table/formats/csv/
     *
     * @throws Exception
     */
    @Test
    public void testTable_2() throws Exception {
        // TODO 1. 获取流执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setRuntimeMode(RuntimeExecutionMode.BATCH);
        env.setParallelism(1);
        // TODO 2. 获取表执行环境
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);
        // TODO 3. 读取CSV文件 创建临时表
        String sql = """
                CREATE TABLE dim_dic_code_import (
                	code STRING,
                	name STRING,
                	chinese_name STRING,
                	ename_name STRING,
                	name_description STRING,
                	parent_code STRING,
                	type_code STRING,
                	type_name STRING,
                	data_source STRING,
                	is_valid STRING,
                	import_date String
                )
                WITH ('connector' = 'filesystem',
                    'path' = 'data/data.csv',
                    'format' = 'csv',
                    'csv.allow-comments' = 'true',
                    'csv.field-delimiter' = ',',
                    'csv.ignore-first-line' = 'true',
                    'csv.ignore-parse-errors' ='true'
                    )
                
                """;
        ;

        tableEnv.executeSql(sql);

        var query = """
                SELECT type_code, COUNT(1)
                FROM dim_dic_code_import
                WHERE code <> '字典值'
                GROUP BY type_code
                """;
        var tableResult = tableEnv.executeSql(query);
        tableResult.print();
    }

    /**
     * https://www.modb.pro/db/128805
     * @throws Exception
     */
    @Test
    public void testTable_3() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        env.setRestartStrategy(RestartStrategies.noRestart());
        log.info(env.getConfig().toString());
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        String sourceDDL = """
                CREATE TABLE csv_source (
                    user_id INT,
                    product STRING,
                    ts timestamp(3),
                    watermark for ts as ts - interval '5' second)
                WITH (
                 'connector' = 'kafka',
                 'scan.startup.mode' = 'latest-offset',
                 'topic' = 'csv_source',
                 'properties.bootstrap.servers' = 'felixzh:9092',
                 'properties.group.id' = 'testGroup',
                 'format' = 'csv',
                 'csv.allow-comments' = 'true',
                 'csv.field-delimiter' = '|',
                 'csv.ignore-parse-errors' = 'false'
                )
                
                """;


        String sinkDDL = """
                CREATE TABLE sink (
                    user_id INT,
                    product STRING)
                WITH (
                 'connector' = 'kafka',
                 'topic' = 'sink',
                 'properties.bootstrap.servers' = 'felixzh:9092',
                 'format' = 'csv'
                )
                """;

        String transformSQL = "insert into sink(user_id,product) SELECT user_id,product FROM csv_source ";
        tableEnv.executeSql(sourceDDL);
        tableEnv.executeSql(sinkDDL);
        tableEnv.executeSql(transformSQL);

    }

}
