package com.chenhai.thread.pool;


/**
 * 线程池测试类
 * @author admin
 *
 */
public class ThreadPoolTest {

	public static void main(String[] args) {
		ThreadPool t=ThreadPool.getThreadPool(3);
		
		t.excute(new Runnable[]{new Task(),new Task(),new Task()});
		t.excute(new Runnable[]{new Task(),new Task(),new Task()});
		System.out.println(t);
		t.destroy();
		System.out.println(t);
	}
	
	static class Task implements Runnable{
		
	private static volatile int i=1;
	@Override
	public void run() {
		System.out.println("当前线程名称:"+Thread.currentThread().getName());
		System.out.println("执行任务:"+(i++)+"完成");
		
		
	}
	
	}
}
