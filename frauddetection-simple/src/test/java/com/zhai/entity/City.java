package com.zhai.entity;

import lombok.Data;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;

/**
 * https://simplemaps.com/static/data/world-cities/sample/fr.csv
 */
@Data
@JsonPropertyOrder({"city","city_ascii","lat","lng","country","iso2","iso3","admin_name","capital","population","id"})
public class City {
    public String city;
    public String city_ascii;
    public BigDecimal lat;
    public BigDecimal lng;
    public String country;
    public String iso2;
    public String iso3;
    public String admin_name;
    public String capital;
    public long population;
    public long id;
}