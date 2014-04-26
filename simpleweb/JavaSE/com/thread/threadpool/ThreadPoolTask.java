package com.thread.threadpool;
import java.io.Serializable;

public class ThreadPoolTask implements Runnable, Serializable {
	private static final long serialVersionUID = -2380080691182560569L;
	private Object attachData;

	ThreadPoolTask(Object tasks) {
		this.attachData = tasks;
	}

	public void run() {
		
		System.out.println("开始执行任务：" + attachData);
		
		attachData = null;
	}

	public Object getTask() {
		return this.attachData;
	}
}