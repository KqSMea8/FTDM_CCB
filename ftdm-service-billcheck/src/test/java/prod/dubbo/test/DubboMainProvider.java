package prod.dubbo.test;
import org.apache.commons.logging.Log;
//
//import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Dubbo服务类测试接口；
 * 正式开启的服务用Dubbo提供的main方法
 */
public class DubboMainProvider {
	
	private static final Log log = LogFactory.getLog(DubboMainProvider.class);

	public static void main(String[] args) {
		com.alibaba.dubbo.container.Main.main(args);
	}
}