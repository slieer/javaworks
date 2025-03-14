package com.dg.easyReplication;

import lombok.Data;

import java.util.LinkedList;

@Data
public class Event {

    private LinkedList<String> data;
    private Long lastLSN;
    private boolean isSimpleEvent;
    private boolean hasBeginCommit;
    private boolean isSnapshot;

    public Event(LinkedList<String> data, Long lsn, boolean isSimple, boolean hasBeginCommit, boolean isSnap) {
        this.data = data;
        this.lastLSN = lsn;
        this.isSimpleEvent = isSimple;
        this.hasBeginCommit = hasBeginCommit;
        this.isSimpleEvent = isSnap;
    }
}
