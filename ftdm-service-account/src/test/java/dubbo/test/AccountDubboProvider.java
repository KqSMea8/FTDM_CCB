package dubbo.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Dubbo服务类测试接口；
 * 正式开启的服务用Dubbo提供的main方法
 */
public class AccountDubboProvider {

	private static final Log log = LogFactory.getLog(AccountDubboProvider.class);

	public static void main(String[] args) {
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
			context.start();
		} catch (Exception e) {
			log.error("== AccountDubboProvider context start error:",e);
		}
		synchronized (AccountDubboProvider.class) {
			while (true) {
				try {
					AccountDubboProvider.class.wait();
				} catch (InterruptedException e) {
					log.error("== synchronized error:",e);
				}
			}
		}
	}
}