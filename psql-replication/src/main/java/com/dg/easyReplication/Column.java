package com.dg.easyReplication;

import lombok.Data;

@Data
public class Column {
    private int position;
    private char isKey;
    private String name;
    private int dataTypeId;
    private String dataTypeName;
    private int typeModifier;
}
