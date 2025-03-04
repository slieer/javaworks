package com.zhai;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static spendreport.FraudDetectionJob.fraudDetection;

/**
 *  简单的原则：
 *  BATCH处理批量数据，STREAMING 处理流式数据。
 *  数据有界的时候输出结果更加高效；数据无界的时候只能选择STREAMING流处理。
 *
 *  flink 是事件驱动的，只有数据真正到来才会进行计算，这也被称为延迟执行或懒执行。我们需要显示地执行execute()方法，来触发程序执行。
 *  execute 方法将一直等待作业完成，然后返回一个JobExecutionResult(内部包含JobId 等信息)。
 */
@Slf4j
public class Flink01_0_Test {
    /**
     * 自定义数据源：
     * 旧： {@link org.apache.flink.streaming.api.functions.source.SourceFunction}
     * 新： {@link org.apache.flink.api.connector.source.Source}
     *
     * */
    @Test
    void fraudTest() throws Exception {
        fraudDetection();
    }
}
