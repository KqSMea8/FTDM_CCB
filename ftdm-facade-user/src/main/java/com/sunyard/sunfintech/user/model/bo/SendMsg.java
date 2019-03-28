package com.sunyard.sunfintech.user.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 【功能描述】
 *
 * @author wyc  2018/1/24.
 */

@AllArgsConstructor

public class SendMsg {
 private   String mobile="";
 private   String mallNo="";
 private   String merNo="";
 private   String transCode="";
 private   String transName="";
 private String content="";
 public SendMsg(){}
 public String getMobile() {
  return mobile;
 }

 public void setMobile(String mobile) {
  this.mobile = mobile;
 }

 public String getMallNo() {
  return mallNo;
 }

 public void setMallNo(String mallNo) {
  this.mallNo = mallNo;
 }

 public String getMerNo() {
  return merNo;
 }

 public void setMerNo(String merNo) {
  this.merNo = merNo;
 }

 public String getTransCode() {
  return transCode;
 }

 public void setTransCode(String transCode) {
  this.transCode = transCode;
 }

 public String getTransName() {
  return transName;
 }

 public void setTransName(String transName) {
  this.transName = transName;
 }

 public String getContent() {
  return content;
 }

 public void setContent(String content) {
  this.content = content;
 }
}
