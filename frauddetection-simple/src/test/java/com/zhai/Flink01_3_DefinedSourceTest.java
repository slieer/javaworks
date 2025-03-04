package com.zhai;

import com.zhai.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.connector.source.SourceReaderContext;
import org.apache.flink.api.connector.source.lib.NumberSequenceSource;
import org.apache.flink.api.connector.source.util.ratelimit.RateLimiterStrategy;
import org.apache.flink.connector.datagen.source.DataGeneratorSource;
import org.apache.flink.connector.datagen.source.GeneratorFunction;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.junit.jupiter.api.Test;

/**
 *
 */
@Slf4j
public class Flink01_3_DefinedSourceTest {
    @Test
    public void firstSourceStyleTest() throws Exception {
        // 1.获取执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(3);

        // 生成序列型数据源
        NumberSequenceSource source = new NumberSequenceSource(0, 10);
        env.fromSource(source
                        , WatermarkStrategy.noWatermarks()  // 指定水位线生成策略
                        , "data-generator")
                .map(t -> t * t)
                .print();
        env.execute();
    }
    /**
     *
     * DataGeneratorSource
     * 创建一个`可并行的生成测试数据的数据源`，它支持自定义生成数据的类型、生成数据的行数、生成数据的速率，能够很好的模拟真实的数据源，
     * 常被用来做flink流任务测试和性能测试
     * @throws Exception
     */
    @Test
    public void secondSourceStyleTest() throws Exception {
        // 1.获取执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(3);

        // 2.自定义数据生成器Source
        /*
         *  TODO DataGeneratorSource(
         *       GeneratorFunction<Long, OUT> generatorFunction
         *      ,long count
         *      ,RateLimiterStrategy rateLimiterStrategy
         *      ,TypeInformation<OUT> typeInfo)
         *   参数说明：
         *      @generatorFunction   ： 指定 GeneratorFunction 实现类(生成数据的具体实现类)
         *      @count               ： 指定输出数据的总行数(如果想一直输出，可以设置为Long.MAX_VALUE)
         *      @rateLimiterStrategy ： 指定发射速率(每秒发射的记录数)
         *      @typeInfo            ： 指定返回值类型
         * */
        DataGeneratorSource<User> source = new DataGeneratorSource<>(
                // 指定 GeneratorFunction 实现类
                new MyGeneratorFunction(),
                // 指定 输出数据的总行数
                100,
                // 指定 每秒发射的记录数
                RateLimiterStrategy.perSecond(100),
                // 指定返回值类型
                TypeInformation.of(User.class) // 将java的FlinkUser封装成到TypeInformation
        );

        // 3.读取 dataGeneratorSource 中的数据
        env.fromSource(source
                        , WatermarkStrategy.noWatermarks()  // 指定水位线生成策略
                        , "data-generator")
                .print();
        /*
         * 注意：生成的dataGeneratorSource为可并行算子
         *      生成的数据会均匀的分配到并行子任务中
         * */

        env.execute();
    }

    /**
     *
     *  TODO GeneratorFunction<T, O>接口说明：
     *      功能说明：
     *          数据生成器函数的基本接口，用来定义生成数据的核心逻辑
     *      泛型说明：
     *          T ： 输入数据的类型(默认为Long)，表示数据生成器的自增ID
     *          O ： 输出数据的类型，要指定flink的数据类型(TypeInformation)
     *      实现方法：
     *          open   ： 创建对象时，调用一次，用来做资源初始化
     *          close  ： 销毁对象时，调用一次，用来做资源关闭
     *          map    ： 数据的生成逻辑，每生成一次数据调用一次，参数为自增ID
     * */
    @Slf4j
    public static class MyGeneratorFunction implements GeneratorFunction<Long, User> {
        // 定义随机数数据生成器
        public RandomDataGenerator generator;

        // 初始化随机数数据生成器
        @Override
        public void open(SourceReaderContext readerContext) throws Exception {
            generator = new RandomDataGenerator();
        }

        @Override
        public User map(Long value) throws Exception {
            log.warn("value: {}", value);

            // 使用 随机数数据生成器 来创建 FlinkUser实例
            var year = Long.valueOf(System.currentTimeMillis() / (365L * 24 * 3600 * 1000)).intValue();

            var user = new User(value
                    , generator.nextHexString(5)
                    , generator.nextHexString(4) // 生成随机的4位字符串
                    , year
            );
            return user;
        }

    }

}
