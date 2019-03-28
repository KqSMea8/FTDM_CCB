//package com.sunyard.sunfintech.billcheck.handler;
//
//import org.junit.Assert;
//import org.junit.Test;
//
///**
// * Created by djh on 2017/11/23.
// */
//public class ChargeListHandlerTest {
//
//    @Test
//    public void test(){
//        QueryListHandler handler = new QueryListHandler(1,200032,null,null);
//        //System.out.println(handler.getHandDatas().size());
//        Assert.assertEquals(21,handler.getNodes().size());
//        Assert.assertEquals(21,handler.getNodeState().length);
//        Assert.assertEquals(210001L,handler.getMaxId());
//
//        QueryListHandler handler1 = new QueryListHandler(1,200000,null,null);
//        //System.out.println(handler.getHandDatas().size());
//        Assert.assertEquals(20,handler1.getNodes().size());
//        Assert.assertEquals(20,handler1.getNodeState().length);
//        Assert.assertEquals(200001L,handler1.getMaxId());
//
//        QueryListHandler handler2 = new QueryListHandler(1,2,null,null);
//        //System.out.println(handler.getHandDatas().size());
//        Assert.assertEquals(1,handler2.getNodes().size());
//        Assert.assertEquals(1,handler2.getNodeState().length);
//        Assert.assertEquals(100001L,handler2.getMaxId());
//        //handler.get
//    }
//
////    public static void main(String[] args) {
////        QueryListHandler handler = new QueryListHandler(1,200021);
////    }
//}
