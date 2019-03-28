//package com.sunyard.mock;
//
//import org.junit.Test;
//import org.mockito.Mockito;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//
//import java.util.ArrayList;
//
///**
// * Created by djh on 2017/12/19.
// */
//public class MockTest {
//
//    @Test(expected = RuntimeException.class)
//    public void test(){
//        new ArrayList<>().get(5);
//        //PowerMockito
//
//    }
//    public void doGetTest(){
//        PowerMockito.mockStatic(MyTestService.class);
//        Mockito.when(MyTestService.doGet("test")).thenReturn("abc");
//
//    }
//}
