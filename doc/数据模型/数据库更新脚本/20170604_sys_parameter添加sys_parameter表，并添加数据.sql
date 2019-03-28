/*
Navicat MySQL Data Transfer

Source Server         : 服务器
Source Server Version : 50703
Source Host           : 118.178.123.188:3306
Source Database       : sun_ftdm

Target Server Type    : MYSQL
Target Server Version : 50703
File Encoding         : 65001

Date: 2017-06-04 18:46:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_parameter`
-- ----------------------------
DROP TABLE IF EXISTS `sys_parameter`;
CREATE TABLE `sys_parameter` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `mall_no` varchar(64) NOT NULL,
  `mer_no` varchar(64) NOT NULL,
  `parameter_key` varchar(32) NOT NULL,
  `parameter_value` varchar(512) DEFAULT NULL,
  `enabled` char(1) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `create_by` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_parameter
-- ----------------------------
INSERT INTO `sys_parameter` VALUES ('1', 'BOB-FENGJR-ZS-C-20170509', 'BOB-FENGJR-ZS-C-20170509', 'epay_server', 'http://172.16.10.58:9082', '1', '', '', null, null, null);
INSERT INTO `sys_parameter` VALUES ('2', 'BOB-FENGJR-ZS-C-20170509', 'BOB-FENGJR-ZS-C-20170509', 'quick_pay_url', 'eis/yunpay/epay_bank_collection_apply', '1', null, null, null, null, null);
INSERT INTO `sys_parameter` VALUES ('3', 'BOB-FENGJR-ZS-C-20170509', 'BOB-FENGJR-ZS-C-20170509', 'confirm_quick_pay_url', 'eis/yunpay/epay_bank_collection_confirm', '1', null, null, null, null, null);
INSERT INTO `sys_parameter` VALUES ('4', 'BOB-FENGJR-ZS-C-20170509', 'BOB-FENGJR-ZS-C-20170509', 'pwd_set', 'eis/yunpay/cashier_pwd_set', '1', null, null, null, null, null);
INSERT INTO `sys_parameter` VALUES ('5', 'BOB-FENGJR-ZS-C-20170509', 'BOB-FENGJR-ZS-C-20170509', 'pwd_verify', 'eis/yunpay/cashier_pwd_verify', '1', null, null, null, null, null);
INSERT INTO `sys_parameter` VALUES ('6', 'BOB-FENGJR-ZS-C-20170509', 'BOB-FENGJR-ZS-C-20170509', 'open_account', 'eis/yunpay/cashier_open_account', '1', null, null, null, null, null);
INSERT INTO `sys_parameter` VALUES ('7', 'BOB-FENGJR-ZS-C-20170509', 'BOB-FENGJR-ZS-C-20170509', 'epay_bank_account_verify', 'eis/yunpay/epay_bank_account_verify', '1', null, null, null, null, null);
INSERT INTO `sys_parameter` VALUES ('8', 'BOB-FENGJR-ZS-C-20170509', 'BOB-FENGJR-ZS-C-20170509', 'gateway_pay', 'eis/yunpay/epay_gateway_pay', '1', null, null, null, null, null);
INSERT INTO `sys_parameter` VALUES ('9', 'BOB-FENGJR-ZS-C-20170509', 'BOB-FENGJR-ZS-C-20170509', 'query_pay_status', 'eis/yunpay/epay_query_pay_status', '1', null, null, null, null, null);
INSERT INTO `sys_parameter` VALUES ('10', 'BOB-FENGJR-ZS-C-20170509', 'BOB-FENGJR-ZS-C-20170509', 'pay_result_notify', 'eis/yunpay/epay_pay_result_notify', '1', null, null, null, null, null);
INSERT INTO `sys_parameter` VALUES ('11', 'BOB-FENGJR-ZS-C-20170509', 'BOB-FENGJR-ZS-C-20170509', 'bind_real_nomsg', 'eis/yunpay/epay_bind_real_nomsg', '1', null, null, null, null, null);
INSERT INTO `sys_parameter` VALUES ('12', 'BOB-FENGJR-ZS-C-20170509', 'BOB-FENGJR-ZS-C-20170509', 'bind_real', 'eis/yunpay/epay_bind_real', '1', null, null, null, null, null);
INSERT INTO `sys_parameter` VALUES ('13', 'BOB-FENGJR-ZS-C-20170509', 'BOB-FENGJR-ZS-C-20170509', 'unbind_real', 'eis/yunpay/epay_unbind_real', '1', null, null, null, null, null);
INSERT INTO `sys_parameter` VALUES ('14', 'BOB-FENGJR-ZS-C-20170509', 'BOB-FENGJR-ZS-C-20170509', 'bind_qry', 'eis/yunpay/epay_bind_qry', '1', null, null, null, null, null);
INSERT INTO `sys_parameter` VALUES ('15', 'BOB-FENGJR-ZS-C-20170509', 'BOB-FENGJR-ZS-C-20170509', 'agent_collection_verify', 'eis/yunpay/epay_agent_collection_verify', '1', null, null, null, null, null);
INSERT INTO `sys_parameter` VALUES ('16', 'BOB-FENGJR-ZS-C-20170509', 'BOB-FENGJR-ZS-C-20170509', 'apply_check_file', 'eis/yunpay/epay_apply_check_file', '1', null, null, null, null, null);
INSERT INTO `sys_parameter` VALUES ('17', 'BOB-FENGJR-ZS-C-20170509', 'BOB-FENGJR-ZS-C-20170509', 'refund_check_file', 'eis/yunpay/epay_apply_refund_check_file', '1', null, null, null, null, null);
INSERT INTO `sys_parameter` VALUES ('18', 'BOB-FENGJR-ZS-C-20170509', 'BOB-FENGJR-ZS-C-20170509', 'refund_apply', 'eis/yunpay/epay_refund_apply', '1', null, null, null, null, null);
INSERT INTO `sys_parameter` VALUES ('19', 'BOB-FENGJR-ZS-C-20170509', 'BOB-FENGJR-ZS-C-20170509', 'batch_collection', 'eis/yunpay/epay_batch_collection', '1', null, null, null, null, null);
INSERT INTO `sys_parameter` VALUES ('20', 'BOB-FENGJR-ZS-C-20170509', 'BOB-FENGJR-ZS-C-20170509', 'batch_pay', 'eis/yunpay/epay_batch_pay', '1', null, null, null, null, null);
INSERT INTO `sys_parameter` VALUES ('21', 'BOB-FENGJR-ZS-C-20170509', 'BOB-FENGJR-ZS-C-20170509', 'batch_query', 'eis/yunpay/epay_batch_query', '1', null, null, null, null, null);
INSERT INTO `sys_parameter` VALUES ('22', 'BOB-FENGJR-ZS-C-20170509', 'BOB-FENGJR-ZS-C-20170509', 'agent_pay_real', 'eis/yunpay/epay_agent_pay_real', '1', null, null, null, null, null);
INSERT INTO `sys_parameter` VALUES ('23', 'BOB-FENGJR-ZS-C-20170509', 'BOB-FENGJR-ZS-C-20170509', 'cardbins_query', 'eis/yunpay/epay_cardbins_query', '1', null, null, null, null, null);
