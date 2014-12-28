package com.guava.thread.minitor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
 
import java.lang.reflect.Method;
import java.util.concurrent.*;
 
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
 
/**
 * 原文地址：https://gist.github.com/bbejeck/1369371
 * User: bbejeck
 */
public class MonitorExampleTest {
 
    private MonitorExample monitorExample;
    private ExecutorService executorService;
    private int numberThreads = 10;
    // CountDownLatch：同步辅助类，允许一个或多个线程等待其他线程所执行的一组操作完成
    private CountDownLatch startSignal;
    private CountDownLatch doneSignal;
 
    @Before
    public void setUp() throws Exception {
        monitorExample = new MonitorExample();
        executorService = Executors.newFixedThreadPool(numberThreads);
        startSignal = new CountDownLatch(1);
        doneSignal = new CountDownLatch(numberThreads);
    }
 
    @After
    public void tearDown() {
        executorService.shutdownNow();
    }
 
    /**
     * 第一个线程会进入Monitor调用simulatedWork()后线程等待
     * 其余9个线程则会进入else，对taskSkippedCounter自增
     *
     * @throws Exception
     */
    @Test
    public void testDemoTryEnterIf() throws Exception {
        setUpThreadsForTestingMethod("demoTryEnterIf");
        startAllThreadsForTest();
        waitForTestThreadsToFinish();
        int expectedTaskCount = 1;
        int expectedSkippedTasks = 9;
        assertThat(monitorExample.getTaskDoneCounter(), is(expectedTaskCount));
        assertThat(monitorExample.getTaskSkippedCounter(), is(expectedSkippedTasks));
    }
 
    /**
     * 前5个线程会等待Monitor，因为Guard的isSatisfied()为true
     * 但是一旦isSatisfied()变为false，剩余的线程会进入else，
     * 对taskSkippedCounter自增
     *
     * @throws Exception
     */
    @Test
    public void testDemoEnterIfOnlyFiveTasksComplete() throws Exception {
        monitorExample.setStopTaskCount(5);
        setUpThreadsForTestingMethod("demoEnterIf");
 
        startAllThreadsForTest();
        waitForTestThreadsToFinish();
        int expectedTaskCount = 5;
        int expectedSkippedTasks = 5;
 
        assertThat(monitorExample.getTaskDoneCounter(), is(expectedTaskCount));
        assertThat(monitorExample.getTaskSkippedCounter(), is(expectedSkippedTasks));
 
    }
 
    /**
     * 所有10个线程都会进入Monitor，因为在整个时间内Guard的isSatisfied()为true
     *
     * @throws Exception
     */
    @Test
    public void testDemoEnterIfAllTasksComplete() throws Exception {
        monitorExample.setStopTaskCount(Integer.MAX_VALUE);
        setUpThreadsForTestingMethod("demoEnterIf");
 
        startAllThreadsForTest();
        waitForTestThreadsToFinish();
        int expectedTaskCount = 10;
        int expectedSkippedTasks = 0;
 
        assertThat(monitorExample.getTaskDoneCounter(), is(expectedTaskCount));
        assertThat(monitorExample.getTaskSkippedCounter(), is(expectedSkippedTasks));
 
    }
 
    /**
     * Guard的isSatisfied()初始化为true，但是所有10个线程会进入Monitor
     *
     * @throws Exception
     */
    @Test
    public void testDemoEnterWhen() throws Exception {
        monitorExample.setStopTaskCount(Integer.MAX_VALUE);
        monitorExample.setCondition(false);
        setUpThreadsForTestingMethod("demoEnterWhen");
        startAllThreadsForTest();
        int expectedCompletedCount = 0;
        int completedCount = monitorExample.getTaskDoneCounter();
        assertThat(completedCount, is(expectedCompletedCount));
 
        monitorExample.setCondition(true);
 
        waitForTestThreadsToFinish();
        expectedCompletedCount = 10;
        completedCount = monitorExample.getTaskDoneCounter();
        assertThat(completedCount, is(expectedCompletedCount));
    }
 
    /**
     * 在3个线程完成工作后，人为的设置Guard的isSatisfied()为false
     * 以证明剩余的7个线程将等待，直到isSatisfied()变为true
     * 然后才会进入Monitor.
     *
     * @throws Exception
     */
    @Test
    public void testDemoEnterWhenAllTasksCompleteEvenWhenConditionChanges() throws Exception {
        monitorExample.setCondition(true);
        monitorExample.setStopTaskCount(3);
        setUpThreadsForTestingMethod("demoEnterWhen");
        startAllThreadsForTest();
 
        //验证最初只有3个线程工作, 重新设定Guard的isSatisfied()为true
        FutureTask<Integer> checkInitialTasksCompleted = new FutureTask<Integer>(
                new Callable<Integer>() {
                    public Integer call() {
                        int initialCompletedTasks = monitorExample.getTaskDoneCounter();
                        monitorExample.setCondition(true);
//                        monitorExample.reEvaluateGuardCondition();
                        return initialCompletedTasks;
 
                    }
                });
 
        new Thread(checkInitialTasksCompleted).start();
 
        int expectedCompletedCount = 3;
        int completedCount = checkInitialTasksCompleted.get();
        assertThat(completedCount, is(expectedCompletedCount));
 
        waitForTestThreadsToFinish();
        assertThat(completedCount, is(expectedCompletedCount));
        expectedCompletedCount = 10;
        completedCount = monitorExample.getTaskDoneCounter();
        assertThat(completedCount, is(expectedCompletedCount));
    }
 
    private void waitForTestThreadsToFinish() throws InterruptedException {
        doneSignal.await(1000l, TimeUnit.MILLISECONDS);
    }
 
    private void startAllThreadsForTest() {
        startSignal.countDown();
    }
 
    private Method getMethodUnderTest(String methodName) throws Exception {
        return monitorExample.getClass().getDeclaredMethod(methodName);
    }
 
 
    private void setUpThreadsForTestingMethod(String methodName) throws Exception {
        final Method testMethod = getMethodUnderTest(methodName);
        for (int i = 0; i < numberThreads; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        startSignal.await();
                        testMethod.invoke(monitorExample);
                    } catch (Exception e) {
                        //异常无须处理
                    } finally {
                        doneSignal.countDown();
                    }
                }
            });
        }
    }
 
}