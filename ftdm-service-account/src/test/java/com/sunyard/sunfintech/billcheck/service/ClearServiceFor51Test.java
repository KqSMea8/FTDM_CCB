//package com.sunyard.sunfintech.billcheck.service;
//
//import com.sunyard.sunfintech.billcheck.handler.AccountUpdateHandler;
//import com.sunyard.sunfintech.billcheck.handler.QueryListHandler;
//import com.sunyard.sunfintech.billcheck.handler.node.TransListNode;
//import com.sunyard.sunfintech.billcheck.model.ClearModel;
//import com.sunyard.sunfintech.billcheck.model.bo.ClearAccount;
//import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;
//import org.mockito.Mockito;
//
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * Created by djh on 2017/12/20.
// */
//public class ClearServiceFor51Test {
//
//    //@Mock
//    //CustEaccAccountamtlistMapper custEaccAccountamtlistMapper;
//    @org.junit.Test
//    public void testTryClear() {
//
//        ClearModel clearModel = new ClearModel("","","");
//        //clearModel.incClearTimes();
//        ConcurrentHashMap<String, ClearAccount> accounts = clearModel.getAccounts();
//        QueryListHandler transListHandler = clearModel.getTransListHandler();
//        if (transListHandler == null) {
//            //初次清算，记录太多
//            //custEaccAccountamtlistMapper.getTransScope(clearModel.getPlat_no());
//            Map<String, Long> idScope = Mockito.mock(Map.class);
//            Mockito.when(idScope.get("minId")).thenReturn(1L);
//            Mockito.when(idScope.get("maxId")).thenReturn(15000000L);
//
//            transListHandler = new QueryListHandler(idScope.get("minId"), idScope.get("maxId"), clearModel, TransListNode.class);
//            //transListHandler.asynExecute();//多线程异步执行
////            int[] nodeState = transListHandler.getNodeState();
////            for (int i = 0; i < nodeState.length; i++) {
////                List<EaccAccountamtlist> transList = transListHandler.getNodes().get(i).getHandData();
////                //clearTrans(accounts, transList);
////            }
//        } else {
//            //当没有再次清算没有新流水，还有账户未清算，不可能清算成功了，存入清算差错账户（clear_account_error）
//            long start = clearModel.getMaxTransId();
//            if (start == -1) {
//                for (Map.Entry<String, ClearAccount> entry : accounts.entrySet()) {
//                    accounts.remove(entry.getKey());
//                    //accountClearService.insertClearCheckError(entry.getValue(), clearModel);
//                }
//                return;
//            } else {
//                //List<EaccAccountamtlist> eaccList = custEaccAccountamtlistMapper.getTransferList(clearModel.getPlat_no(), start + 1, start + 1000000);
//                List<EaccAccountamtlist> eaccList = Mockito.mock(List.class);
//                Mockito.when(eaccList.size()).thenReturn(1);
//                if (eaccList.size() == 0 && accounts.size() != 0) {
//                    for (Map.Entry<String, ClearAccount> entry : accounts.entrySet()) {
//                        accounts.remove(entry.getKey());
//                        //accountClearService.insertClearCheckError(entry.getValue(), clearModel);
//                    }
//                }
//                clearModel.getTransListNew().addAll(eaccList);//加入新查询流水，2017/12/20
//                //clearTrans(accounts, eaccList);
//            }
//        }
//        //清算资金
//        AccountUpdateHandler accountUpdateHandler = Mockito.mock(AccountUpdateHandler.class);
//        accountUpdateHandler.asynExecute();
//    }
//
//    public static void main(String[] args) {
//        System.out.println("Hello world");
//    }
//}
