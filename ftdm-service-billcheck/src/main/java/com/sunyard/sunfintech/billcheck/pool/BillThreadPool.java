package com.sunyard.sunfintech.billcheck.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.*;

/**
 * 并行执行多个任务
 * @author djh
 *
 */
public class BillThreadPool {


	private static BillThreadPool threadPool = new BillThreadPool();

	private static int threadNum = 50;//最大支持200线程，没任务不保持任何活跃线程
	private BillThreadPool(){}//禁止创建多个池对象

	static {
		threadPool.pool = new ThreadPoolExecutor(threadNum, threadNum,
				300L,TimeUnit.SECONDS,
				new LinkedTransferQueue<Runnable>());
	}

	private ThreadPoolExecutor pool;
    public static Logger logger = LogManager.getLogger(BillThreadPool.class);
    private CountDownLatch latch = null;//new CountDownLatch(2);
	
	//public static LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<Object>();
	/**
	 * 获取线程池
	 * @return BillThreadPool
	 */
	public static BillThreadPool getThreadPool() {
		return threadPool;
	}
	
	/**
	 * 将任务添加到线程池执行
	 * 如果调用了futue.get()方法，会造成主线程阻塞，直到线程池执行完成
	 * @param task
	 * @return  Future
	 */
	public <T> Future<T> addTask(Callable<T> task) {
		return pool.submit(task);
	}
	/**
	 * 将任务添加到线程池执行
	 * 如果调用了futue.get()方法，会造成主线程阻塞，直到线程池执行完成
	 * @param task
	 * @return
	 */
	public void addTask(Runnable task) {
		pool.execute(task);
	}

	public void setTaskNum(int num){
        latch = new CountDownLatch(num);
    }
	/**
	 * 获取执行器
	 * @return
	 */
	public ThreadPoolExecutor getPool(){
		return pool;
	}

	/**
	 * 线程池中任务自减1
	 */
	public void countDownTask(){
		latch.countDown();
	}

	/**
	 * 阻塞当前线程，知道所有任务都执行完
	 */
	public void await(){
		try {
			latch.await(20L,TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		BillThreadPool.getThreadPool().addTask(new Runnable() {
			@Override
			public void run() {
				while (true){
					System.out.println(BillThreadPool.getThreadPool().getPool());
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		BillThreadPool.getThreadPool().setTaskNum(10);
		System.out.println("vvvvvvvvvvvvvvvv"+BillThreadPool.getThreadPool().latch.getCount());
		BillThreadPool.getThreadPool().addTask(new Runnable() {
			@Override
			public void run() {
				System.out.println(BillThreadPool.getThreadPool().getPool());
				while (true){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}finally {
						threadPool.countDownTask();
						System.out.println("task num::"+threadPool.latch.getCount());
					}
				}
			}
		});

		BillThreadPool.getThreadPool().addTask(new Runnable() {
			@Override
			public void run() {
				System.out.println(BillThreadPool.getThreadPool().getPool());
					try {
						Thread.sleep(2000) ;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}finally {
						threadPool.countDownTask();
						System.out.println("task num::"+threadPool.latch.getCount());
					}
			}
		});
		BillThreadPool.getThreadPool().await();
		System.out.println("==========end");
	}

	
}
