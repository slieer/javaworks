package com.file;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Slf4j
public class FilesTest {
    @Test
    void fileLineCount() throws IOException {
        var file = "/opt/workspace/javaworks/jprimer/src/test/resources/log4j2.xml";

        //一次性读完文件
        log.info("fileCout:{}", Files.readAllLines(Path.of(file)).stream().count());

        //lines的标准用法
        //lines String 类里也有提供。
        try(Stream<String> stream = Files.lines(Paths.get(file))){
            log.info("fileCout:{}", stream.count());
        } catch (IOException e){
            log.error("get content from{} error,{}",file, e.getMessage());
        }

        //JDK1.7的时候就提出了一种自动关闭资源方法，实现AutoCloseable接口, 就可以try关闭资源。
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(file)))) {
            // 读取数据的操作
            log.info("fileCout:{}", reader.lines().count());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
