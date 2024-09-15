package slieer.guava.thread.minitor;


import com.google.common.util.concurrent.Monitor;
 
import java.util.concurrent.atomic.AtomicInteger;
 
/**
 * 原文地址：https://gist.github.com/bbejeck/1369371
 * User: bbejeck
 */
public class MonitorExample {
 
    private final Monitor monitor = new Monitor();
    private volatile boolean condition = true;
    private int taskDoneCounter;
    //AtomicInteger：线程安全的加减操作
    private AtomicInteger taskSkippedCounter = new AtomicInteger(0);
    private int stopTaskCount;
 
    private Monitor.Guard conditionGuard = new Monitor.Guard(monitor) {
        @Override
        public boolean isSatisfied() {
            return condition;
        }
    };
 
    public void demoTryEnterIf() throws InterruptedException {
        if (monitor.tryEnterIf(conditionGuard)) {
            try {
                simulatedWork();
                taskDoneCounter++;
            } finally {
                monitor.leave();
            }
        } else {
            //自增加1
            taskSkippedCounter.incrementAndGet();
        }
    }
 
    public void demoEnterIf() throws InterruptedException {
        if (monitor.enterIf(conditionGuard)) {
            try {
                taskDoneCounter++;
                if (taskDoneCounter == stopTaskCount) {
                    condition = false;
                }
            } finally {
                monitor.leave();
            }
        } else {
            taskSkippedCounter.incrementAndGet();
        }
 
    }
 
    public void demoEnterWhen() throws InterruptedException {
        monitor.enterWhen(conditionGuard);
        try {
            taskDoneCounter++;
            if (taskDoneCounter == stopTaskCount) {
                condition = false;
            }
        } finally {
            monitor.leave();
        }
    }
 
    private void simulatedWork() throws InterruptedException {
        Thread.sleep(250);
    }
 
//    public void reEvaluateGuardCondition() {
//        monitor.reevaluateGuards();
//    }
 
    public int getStopTaskCount() {
        return stopTaskCount;
    }
 
    public void setStopTaskCount(int stopTaskCount) {
        this.stopTaskCount = stopTaskCount;
    }
 
    public void setCondition(boolean condition) {
        this.condition = condition;
    }
 
    public int getTaskSkippedCounter() {
        return taskSkippedCounter.get();
    }
 
    public int getTaskDoneCounter() {
        return taskDoneCounter;
    }
}
