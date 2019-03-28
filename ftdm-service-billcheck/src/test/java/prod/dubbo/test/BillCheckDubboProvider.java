package prod.dubbo.test;

import com.sunyard.sunfintech.billcheck.service.BillCheckService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Dubbo服务类测试接口；
 * 正式开启的服务用Dubbo提供的main方法
 */
public class BillCheckDubboProvider {
	
	private static final Logger log = LogManager.getLogger(BillCheckDubboProvider.class);

	public static void main(String[] args) throws InterruptedException {
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/spring-context.xml");
			context.start();
			BillCheckService billCheckService = context.getBean(BillCheckService.class);
			billCheckService.makePayCodeClearCT("BOB-U51-51-C-20170821","20180128");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("== BillCheckDubboProvider context start error:",e);
		}
		synchronized (BillCheckDubboProvider.class) {
			BillCheckDubboProvider.class.wait();
		}
	}
}