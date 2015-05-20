package com.slieer.spring.hello;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.slieer.spring.hello.service.BeanConfig;
import com.slieer.spring.hello.service.ManufactureService;
import com.slieer.spring.hello.service.ManufactureService.Fruit;
import com.slieer.spring.hello.service.MessageService;


/**
 * 
 * @author dev
 *
 *Spring 2.5 引入了 @Autowired 注释，它可以对类成员变量、方法及构造函数进行标注，完成自动装配的工作。
 *@Autowired 可用于field, construction, setter.
 *
 *@Qualifier 注释指定注入 Bean 的名称
 *<br>
 *@Resource 的作用相当于 @Autowired，
 *只不过 @Autowired 按 byType 自动注入，面@Resource 默认按 byName 自动注入。
 *@Resource 有两个属性是比较重要的，分别是 name 和 type，
 *Spring 将@Resource 注释的 name 属性解析为 Bean 的名字，而 type 属性则解析为 Bean 的类型。
 *所以如果使用 name 属性，则使用 byName 的自动注入策略，而使用 type 属性时则使用 byType 自动注入策略。

*如果既不指定 name 也不指定 type 属性，这时将通过反射机制使用 byName 自动注入策略。
 *
 */
@Component
public class MessagePrinter {
	private static Logger logger = LogManager.getLogger(MessagePrinter.class.getName());
	
	@Autowired
    private ManufactureService manuService;

	@Resource(name="msgImpl")
	private ManufactureService manuImpl;
    
	@Resource
    private MessageService service;

	@Resource
	BeanConfig.User user;

    public void printMessage() {
    	
    	logger.info(service.getMessage());
        
    	logger.info(manuService.produce(Fruit.香蕉));
    	
    	logger.info(manuImpl.produce(Fruit.APPLE));
    	
    	logger.info("user:" + user);
    }
}