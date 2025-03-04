package com.zhai.entity;

import lombok.Data;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Data
@JsonPropertyOrder({"timestamp", "id", "prdCode", "code", "email", "province", "ts", "jsonField"})
public class UserClickLog {
    public String timestamp; //时间
    public String id; //用户ID
    public String prdCode;//产品代号
    public String code;//点击功能代号
    public String email;//邮箱
    public String province;//省份
    public double ts; //耗时
    public JsonFile jsonField; // 参数详情
}