<%@page language="java" pageEncoding="utf-8" import="ocx.GetRandom"%>
<%
 String  mcrypt_key=GetRandom.generateString(32);
 session.setAttribute("mcrypt_key",mcrypt_key);
 out.clear();
 out.print(mcrypt_key);
%>