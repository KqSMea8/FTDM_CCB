package prod.dubbo.test;

import com.sunyard.sunfintech.billcheck.provider.IBillCheckService;
import com.sunyard.sunfintech.billcheck.provider.IClearService;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.util.DateUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

/**
 * Created by djh on 2017/7/6.
 *
 */
public class DubboClient {
    public static void main(String[] args) {
        try {
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/test-spring-client.xml");
            context.start();
            IClearService clearService = context.getBean(IClearService.class);
            IBillCheckService billCheckService = context.getBean(IBillCheckService.class);
            System.out.println("============");
            /**
             *
             * 清算数据修改数据还原

             垫付账户  2017102923524471536872
             */
            BaseResponse baseResponse = clearService.doClear("BOB-SUNYARD-20180303","20180201");
            //BaseResponse baseResponse = billCheckService.checkCTList(DateUtils.parseDate("20180301","yyyyMMdd"));

            System.out.println("===========清算结果："+baseResponse);
//            context.close();
//            context.destroy();
            //System.exit(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
//        synchronized (BillCheckDubboProvider.class) {
//            try {
//                BillCheckDubboProvider.class.wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

    }
}
