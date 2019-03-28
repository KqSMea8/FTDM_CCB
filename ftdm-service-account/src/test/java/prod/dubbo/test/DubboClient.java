//package prod.dubbo.test;
//
//import com.sunyard.sunfintech.account.provider.IClearService;
//import com.sunyard.sunfintech.core.base.BaseResponse;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
///**
// * Created by djh on 2017/7/6.
// *
// */
//public class DubboClient {
//    public static void main(String[] args) {
//        try {
//            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/test-spring-client.xml");
//            context.start();
//            IClearService clearService = context.getBean(IClearService.class);
//            System.out.println("============");
//            /**
//             *
//             * 清算数据修改数据还原
//
//             垫付账户  2017102923524471536872
//             */
//            BaseResponse baseResponse = clearService.doClear("BOB-FENGJR-B-20170509","20180201");
//            System.out.println("===========清算结果："+baseResponse);
////            context.close();
////            context.destroy();
//            //System.exit(1);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
////        synchronized (AccountDubboProvider.class) {
////            try {
////                AccountDubboProvider.class.wait();
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
////        }
//
//    }
//}
