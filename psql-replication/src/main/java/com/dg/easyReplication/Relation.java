package com.dg.easyReplication;

import lombok.Data;

import java.util.HashMap;

@Data
public class Relation {
    private int id;
    private String namespace;
    private String name;
    private char replicaIdentity;
    private short numColumns;
    private HashMap<Integer, Column> columns = new HashMap<Integer, Column>();

    public void putColumn(Integer position, Column column) {
        this.columns.put(position, column);
    }
    public Column getColumn(Integer position) {
        return this.columns.get(position);
    }

    public String getFullName() {
        return (namespace != null) ? namespace + "." + name : "pg_catalog." + name;
    }
}
