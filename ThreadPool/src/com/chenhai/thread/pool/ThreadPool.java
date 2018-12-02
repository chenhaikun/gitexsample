package com.chenhai.thread.pool;

import java.util.LinkedList;
import java.util.List;

public final class ThreadPool {

	private static  int worker_num=5;
	private WorkThread[] workThreads;
	
	private static volatile int finished_task=0;
	
	private List<Runnable> taskQueue=new LinkedList<Runnable>();
	
	private static ThreadPool threadPool;
	
	private ThreadPool(){
		this(5);
	}
	private ThreadPool(int worker_num){
		
		ThreadPool.worker_num=worker_num;
		workThreads=new WorkThread[worker_num];
		for (int i = 0; i <worker_num; i++) {
			this is chenhaikun 提交。
			workThreads[i].start();
水水水水水
			this is test2;
			this is guohuaxin test1;ss
		}

	}
	
	public static ThreadPool getThreadPool() {
		return getThreadPool(worker_num);
		
	}
	
	public static ThreadPool getThreadPool(int worker_num1) {
		if(worker_num1<0){
			worker_num1=ThreadPool.worker_num;
		}
		if(threadPool==null){
			threadPool=new ThreadPool(worker_num1);
		}
		return threadPool;
		
	}
	
	public void  excute(Runnable task) {
		synchronized (taskQueue) {
			//任务进入队列，处于被唤醒状态，什么时候被使用取决于线程池中 的空闲线程数量。
			taskQueue.add(task);
			task.notify();
			
		}
		
	}
	public void excute(Runnable[] tasks) {
		synchronized (taskQueue) {
			for (int i = 0; i < tasks.length; i++) {
				taskQueue.add(tasks[i]);
				taskQueue.notify();
			}
			
		}
	
	}
	
	public void excute(List<Runnable> tasks) {
		synchronized (taskQueue) {
			for (Runnable runnable : tasks) {
				taskQueue.add(runnable);
				runnable.notify();
			}
			
		}
		
	}
	
	public void destroy() {
		
		while (!taskQueue.isEmpty()) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO: handle exception
				e.printStackTrace();
				
			}
			
			
		}
		//没有任务
	for (int i = 0; i < worker_num; i++) {
		workThreads[i].stopWorker();
		workThreads[i]=null;
		
	}
	threadPool=null;
	taskQueue.clear();
		
	}
	
	public int getWorkThreadNum(){
		return worker_num;
	}
	
	public int getFinishedTaskNum() {
		return finished_task;
		
	}
	
	public int getTaskQueueSize() {
		return taskQueue.size();
	}
	
	@Override
	public String toString(){
		return "WorkIngThreadNum:"+worker_num+"finishiedTaskNum"+finished_task+"waiingTaskNum:"+getTaskQueueSize();
	}

	private class WorkThread extends Thread{
		private Boolean isRunning=true;
		@Override
		public void run() {
			
			Runnable r=null;
			while(isRunning){
				//防止多线程并发
				synchronized (taskQueue) {
					while(isRunning&&taskQueue.isEmpty()){
						try {
							taskQueue.wait(20);
						} catch (InterruptedException e) {
							// TODO: handle exception
							e.printStackTrace();
						}
						
					}
					if(!taskQueue.isEmpty()){
						r = taskQueue.remove(0);
					}
					
				}
				if(r!=null){
					r.run();
				
				}
				finished_task++;
				r=null;
			}
			
		}
		
		private void stopWorker(){
			isRunning=false;
		}
	
	}
	

	
}
