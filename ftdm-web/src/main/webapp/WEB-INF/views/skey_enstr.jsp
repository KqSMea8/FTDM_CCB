<%@page language="java" pageEncoding="utf-8" import="ocx.*"%>
<%
 String  sKey = GetRandom.generateString(32);
 String enStr = AESWithJCE.getCipher(sKey,sKey);
 out.clear();
 out.print(sKey + "," + enStr.trim());
%> 