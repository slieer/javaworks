package com.zhai.entity;

import lombok.Data;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Data
@JsonPropertyOrder({"code","name","chineseName","enameName","nameDescription",
        "parentCode","typeCode","typeName","isValid","dataSource","importDate"})
public class DicCodeImport {
    public String code ;    //字典值
    public String name ;    //字典名称
    public String chineseName ;    //字典中文名称
    public String enameName ;    //字典英文名称
    public String nameDescription ;    //字典枚举描述
    public String parentCode ;    //父级字典值
    public String typeCode ;    //类型代码
    public String typeName ;    //类型名称
    public String isValid ;    //是否有效
    public String dataSource ;    //数据来源 0：新系统 1：老系统
    public String importDate ;    //导入时间
}
