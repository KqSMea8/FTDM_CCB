DELETE FROM account_subject_info WHERE plat_no IN('BOB-SUNYARD-20180303');
DELETE FROM clear_account_error WHERE plat_no IN('BOB-SUNYARD-20180303');

#账户

INSERT INTO account_subject_info (update_time,`platcust`, `plat_no`, `subject`, `sub_subject`, `t_balance`, `n_balance`) VALUES (NOW(),'1', 'BOB-SUNYARD-20180303', '01', '01', '0', '0');
INSERT INTO account_subject_info (update_time,`platcust`, `plat_no`, `subject`, `sub_subject`, `t_balance`, `n_balance`) VALUES (NOW(),'1', 'BOB-SUNYARD-20180303', '01', '02', '0.0000', '0.0000');
INSERT INTO account_subject_info (update_time,`platcust`, `plat_no`, `subject`, `sub_subject`, `t_balance`, `n_balance`) VALUES (NOW(),'1', 'BOB-SUNYARD-20180303', '02', '01', '0.0000', '0.0000');
INSERT INTO account_subject_info (update_time,`platcust`, `plat_no`, `subject`, `sub_subject`, `t_balance`, `n_balance`) VALUES (NOW(),'1', 'BOB-SUNYARD-20180303', '02', '02', '0.0000', '0.0000');
INSERT INTO account_subject_info (update_time,`platcust`, `plat_no`, `subject`, `sub_subject`, `t_balance`, `n_balance`) VALUES (NOW(),'2', 'BOB-SUNYARD-20180303', '01', '01', '0.0000', '0.0000');
INSERT INTO account_subject_info (update_time,`platcust`, `plat_no`, `subject`, `sub_subject`, `t_balance`, `n_balance`) VALUES (NOW(),'2', 'BOB-SUNYARD-20180303', '01', '02', '0.0000', '0.0000');
INSERT INTO account_subject_info (update_time,`platcust`, `plat_no`, `subject`, `sub_subject`, `t_balance`, `n_balance`) VALUES (NOW(),'2', 'BOB-SUNYARD-20180303', '02', '01', '0.0000', '0.0000');
INSERT INTO account_subject_info (update_time,`platcust`, `plat_no`, `subject`, `sub_subject`, `t_balance`, `n_balance`) VALUES (NOW(),'2', 'BOB-SUNYARD-20180303', '02', '02', '0.0000', '0.0000');
INSERT INTO account_subject_info (update_time,`platcust`, `plat_no`, `subject`, `sub_subject`, `t_balance`, `n_balance`) VALUES (NOW(),'3', 'BOB-SUNYARD-20180303', '01', '01', '0.0000', '0.0000');
INSERT INTO account_subject_info (update_time,`platcust`, `plat_no`, `subject`, `sub_subject`, `t_balance`, `n_balance`) VALUES (NOW(),'3', 'BOB-SUNYARD-20180303', '01', '02', '0.0000', '0.0000');
INSERT INTO account_subject_info (update_time,`platcust`, `plat_no`, `subject`, `sub_subject`, `t_balance`, `n_balance`) VALUES (NOW(),'3', 'BOB-SUNYARD-20180303', '02', '01', '0.0000', '0.0000');
INSERT INTO account_subject_info (update_time,`platcust`, `plat_no`, `subject`, `sub_subject`, `t_balance`, `n_balance`) VALUES (NOW(),'3', 'BOB-SUNYARD-20180303', '02', '02', '0.0000', '0.0000');
INSERT INTO account_subject_info (update_time,`platcust`, `plat_no`, `subject`, `sub_subject`, `t_balance`, `n_balance`) VALUES (NOW(),'4', 'BOB-SUNYARD-20180303', '01', '01', '0', '0');
INSERT INTO account_subject_info (update_time,`platcust`, `plat_no`, `subject`, `sub_subject`, `t_balance`, `n_balance`) VALUES (NOW(),'4', 'BOB-SUNYARD-20180303', '01', '02', '0.0000', '0.0000');
INSERT INTO account_subject_info (update_time,`platcust`, `plat_no`, `subject`, `sub_subject`, `t_balance`, `n_balance`) VALUES (NOW(),'4', 'BOB-SUNYARD-20180303', '02', '01', '0.000', '0.000');
INSERT INTO account_subject_info (update_time,`platcust`, `plat_no`, `subject`, `sub_subject`, `t_balance`, `n_balance`) VALUES (NOW(),'4', 'BOB-SUNYARD-20180303', '02', '02', '0.0000', '0.0000');

DELETE FROM eacc_accountamtlist WHERE plat_no IN('BOB-SUNYARD-20180303');

#交易流水
INSERT INTO eacc_accountamtlist (`trans_serial`, `plat_no`, `platcust`, `subject`, `sub_subject`, `trans_code`, `trans_name`, `trans_date`, `trans_time`, `amt_type`, `oppo_plat_no`, `oppo_platcust`, `oppo_subject`, `oppo_sub_subject`, `amt`, `account_date`, `enabled`, `create_time`, `order_no`,pay_code) VALUES (NULL, 'BOB-SUNYARD-20180303', '1', '02', '01', 'C00011', NULL, NULL, NULL, '0', NULL, NULL, NULL, NULL, '100.0000', '2018-02-01', '1', NULL, UUID_SHORT(),'001');
UPDATE account_subject_info SET t_balance=t_balance+100,n_balance=n_balance+100 WHERE plat_no='BOB-SUNYARD-20180303' AND platcust='1' AND SUBJECT='02' AND sub_subject='01';
INSERT INTO eacc_accountamtlist (`trans_serial`, `plat_no`, `platcust`, `subject`, `sub_subject`, `trans_code`, `trans_name`, `trans_date`, `trans_time`, `amt_type`, `oppo_plat_no`, `oppo_platcust`, `oppo_subject`, `oppo_sub_subject`, `amt`, `account_date`, `enabled`, `create_time`, `order_no`,pay_code) VALUES (NULL, 'BOB-SUNYARD-20180303', '2', '02', '01', 'C00011', NULL, NULL, NULL, '0', NULL, NULL, NULL, NULL, '500.0000', '2018-02-01', '1', NULL, UUID_SHORT(),'001');
UPDATE account_subject_info SET t_balance=t_balance+500,n_balance=n_balance+500 WHERE plat_no='BOB-SUNYARD-20180303' AND platcust='2' AND SUBJECT='02' AND sub_subject='01';
INSERT INTO eacc_accountamtlist (`trans_serial`, `plat_no`, `platcust`, `subject`, `sub_subject`, `trans_code`, `trans_name`, `trans_date`, `trans_time`, `amt_type`, `oppo_plat_no`, `oppo_platcust`, `oppo_subject`, `oppo_sub_subject`, `amt`, `account_date`, `enabled`, `create_time`, `order_no`,pay_code) VALUES (NULL, 'BOB-SUNYARD-20180303', '3', '02', '01', 'C00011', NULL, NULL, NULL, '0', NULL, NULL, NULL, NULL, '600.0000', '2018-02-01', '1', NULL, UUID_SHORT(),'002');
UPDATE account_subject_info SET t_balance=t_balance+600,n_balance=n_balance+600 WHERE plat_no='BOB-SUNYARD-20180303' AND platcust='3' AND SUBJECT='02' AND sub_subject='01';
INSERT INTO eacc_accountamtlist (`trans_serial`, `plat_no`, `platcust`, `subject`, `sub_subject`, `trans_code`, `trans_name`, `trans_date`, `trans_time`, `amt_type`, `oppo_plat_no`, `oppo_platcust`, `oppo_subject`, `oppo_sub_subject`, `amt`, `account_date`, `enabled`, `create_time`, `order_no`,pay_code) VALUES (NULL, 'BOB-SUNYARD-20180303', '3', '02', '01', 'C00011', NULL, NULL, NULL, '0', NULL, NULL, NULL, NULL, '800.0000', '2018-02-01', '1', NULL, UUID_SHORT(),'002');
UPDATE account_subject_info SET t_balance=t_balance+800,n_balance=n_balance+800 WHERE plat_no='BOB-SUNYARD-20180303' AND platcust='3' AND SUBJECT='02' AND sub_subject='01';
INSERT INTO eacc_accountamtlist (`trans_serial`, `plat_no`, `platcust`, `subject`, `sub_subject`, `trans_code`, `trans_name`, `trans_date`, `trans_time`, `amt_type`, `oppo_plat_no`, `oppo_platcust`, `oppo_subject`, `oppo_sub_subject`, `amt`, `account_date`, `enabled`, `create_time`, `order_no`,pay_code) VALUES (NULL, 'BOB-SUNYARD-20180303', '1', '02', '01', 'P00001', NULL, NULL, NULL, '1', 'BOB-SUNYARD-20180303', '2', '02', '01', '100.0000', '2018-02-01', '1', NULL,UUID_SHORT(),'');
INSERT INTO eacc_accountamtlist (`trans_serial`, `plat_no`, `platcust`, `subject`, `sub_subject`, `trans_code`, `trans_name`, `trans_date`, `trans_time`, `amt_type`, `oppo_plat_no`, `oppo_platcust`, `oppo_subject`, `oppo_sub_subject`, `amt`, `account_date`, `enabled`, `create_time`, `order_no`,pay_code) VALUES (NULL, 'BOB-SUNYARD-20180303', '2', '02', '01', 'P00001', NULL, NULL, NULL, '0', 'BOB-SUNYARD-20180303', '1', '02', '01', '100.0000', '2018-02-01', '1', NULL,UUID_SHORT(),'');
UPDATE account_subject_info SET t_balance=t_balance-100,n_balance=n_balance-100 WHERE plat_no='BOB-SUNYARD-20180303' AND platcust='1' AND SUBJECT='02' AND sub_subject='01';
UPDATE account_subject_info SET t_balance=t_balance+100,n_balance=n_balance+100 WHERE plat_no='BOB-SUNYARD-20180303' AND platcust='2' AND SUBJECT='02' AND sub_subject='01';
INSERT INTO eacc_accountamtlist (`trans_serial`, `plat_no`, `platcust`, `subject`, `sub_subject`, `trans_code`, `trans_name`, `trans_date`, `trans_time`, `amt_type`, `oppo_plat_no`, `oppo_platcust`, `oppo_subject`, `oppo_sub_subject`, `amt`, `account_date`, `enabled`, `create_time`, `order_no`,pay_code) VALUES (NULL, 'BOB-SUNYARD-20180303', '3', '02', '01', 'P00001', NULL, NULL, NULL, '1', 'BOB-SUNYARD-20180303', '4', '02', '01', '1000.0000', '2018-02-01', '1', NULL, UUID_SHORT(),'');
INSERT INTO eacc_accountamtlist (`trans_serial`, `plat_no`, `platcust`, `subject`, `sub_subject`, `trans_code`, `trans_name`, `trans_date`, `trans_time`, `amt_type`, `oppo_plat_no`, `oppo_platcust`, `oppo_subject`, `oppo_sub_subject`, `amt`, `account_date`, `enabled`, `create_time`, `order_no`,pay_code) VALUES (NULL, 'BOB-SUNYARD-20180303', '4', '02', '01', 'P00001', NULL, NULL, NULL, '0', 'BOB-SUNYARD-20180303', '3', '02', '01', '1000.0000', '2018-02-01', '1', NULL, UUID_SHORT(),'');
UPDATE account_subject_info SET t_balance=t_balance-1000,n_balance=n_balance-1000 WHERE plat_no='BOB-SUNYARD-20180303' AND platcust='3' AND SUBJECT='02' AND sub_subject='01';
UPDATE account_subject_info SET t_balance=t_balance+1000,n_balance=n_balance+1000 WHERE plat_no='BOB-SUNYARD-20180303' AND platcust='4' AND SUBJECT='02' AND sub_subject='01';
INSERT INTO eacc_accountamtlist (`trans_serial`, `plat_no`, `platcust`, `subject`, `sub_subject`, `trans_code`, `trans_name`, `trans_date`, `trans_time`, `amt_type`, `oppo_plat_no`, `oppo_platcust`, `oppo_subject`, `oppo_sub_subject`, `amt`, `account_date`, `enabled`, `create_time`, `order_no`,pay_code) VALUES (NULL, 'BOB-SUNYARD-20180303', '4', '02', '01', 'P00001', NULL, NULL, NULL, '1', 'BOB-SUNYARD-20180303', '1', '02', '01', '500.0000', '2018-02-01', '1', NULL, UUID_SHORT(),'');
INSERT INTO eacc_accountamtlist (`trans_serial`, `plat_no`, `platcust`, `subject`, `sub_subject`, `trans_code`, `trans_name`, `trans_date`, `trans_time`, `amt_type`, `oppo_plat_no`, `oppo_platcust`, `oppo_subject`, `oppo_sub_subject`, `amt`, `account_date`, `enabled`, `create_time`, `order_no`,pay_code) VALUES (NULL, 'BOB-SUNYARD-20180303', '1', '02', '01', 'P00001', NULL, NULL, NULL, '0', 'BOB-SUNYARD-20180303', '4', '02', '01', '500.0000', '2018-02-01', '1', NULL,UUID_SHORT(),'');
UPDATE account_subject_info SET t_balance=t_balance-500,n_balance=n_balance-500 WHERE plat_no='BOB-SUNYARD-20180303' AND platcust='4' AND SUBJECT='02' AND sub_subject='01';
UPDATE account_subject_info SET t_balance=t_balance+500,n_balance=n_balance+500 WHERE plat_no='BOB-SUNYARD-20180303' AND platcust='1' AND SUBJECT='02' AND sub_subject='01';

DELETE FROM clear_result WHERE plat_no IN('BOB-SUNYARD-20180303');

#清算流水
INSERT INTO clear_result (`plat_no`, `pay_code`, `clear_date`, `clear_status`, `liquidation`) VALUES ('BOB-SUNYARD-20180303', '001', '2018-02-01', '3', '0');
INSERT INTO clear_result (`plat_no`, `pay_code`, `clear_date`, `clear_status`, `liquidation`) VALUES ('BOB-SUNYARD-20180303', '002', '2018-02-01', '3', '0');

DELETE FROM plat_paycode WHERE plat_no IN('BOB-SUNYARD-20180303');

##  支付商户名称
INSERT INTO plat_paycode (mall_no,`plat_no`, `pay_code`, `payment_plat_no`) VALUES ('BOB-SUNYARD-20180303','BOB-SUNYARD-20180303', '001', 'BOB-JUMI-C-20170815');
INSERT INTO plat_paycode (mall_no,`plat_no`, `pay_code`, `payment_plat_no`) VALUES ('BOB-SUNYARD-20180303','BOB-SUNYARD-20180303', '002', 'BOB-JUMI-C-20170815');

DELETE FROM plat_cardinfo WHERE plat_no IN('BOB-SUNYARD-20180303');

## 平台账户卡号及名称
INSERT INTO plat_cardinfo (`plat_no`, dedust_no,`card_type`, `card_name`, `card_no`) VALUES ('BOB-SUNYARD-20180303',    '','01', '凤凰金融', '33050161883509201701');




DELETE FROM plat_platinfo WHERE mall_no='BOB-SUNYARD-20180303';
#初始化平台信息
INSERT INTO plat_platinfo (`mall_no`, `plat_no`, `plat_name`, `enabled`) VALUES ('BOB-SUNYARD-20180303', 'BOB-SUNYARD-20180303', 'SUNYARD', '1');

DROP TABLE IF EXISTS `clear_yu_accountamtlist`;
CREATE TABLE `clear_yu_accountamtlist` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `trans_id` BIGINT(20) DEFAULT NULL COMMENT '关联的交易流水',
  `plat_no` VARCHAR(32) DEFAULT NULL COMMENT '平台编号',
  `switch_amt` DECIMAL(19,4) DEFAULT NULL COMMENT '总清算金额（必定小于amt）',
  `amt` DECIMAL(19,4) DEFAULT NULL COMMENT '交易金额',
  `account_date` DATE DEFAULT NULL COMMENT '账期',
  `ext1` VARCHAR(20) DEFAULT NULL,
  `ext2` VARCHAR(40) DEFAULT NULL,
  `ext3` VARCHAR(60) DEFAULT NULL,
  `ext4` VARCHAR(80) DEFAULT NULL,
  `ext5` VARCHAR(100) DEFAULT NULL,
  `enabled` CHAR(1) DEFAULT NULL,
  `ramerk` VARCHAR(255) DEFAULT NULL,
  `create_by` VARCHAR(50) DEFAULT NULL,
  `create_time` DATETIME DEFAULT NULL,
  `update_by` VARCHAR(50) DEFAULT NULL,
  `update_time` DATETIME DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `clear_yu_account`;
CREATE TABLE `clear_yu_account` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `plat_no` VARCHAR(32) DEFAULT NULL,
  `platcust` VARCHAR(64) DEFAULT NULL,
  `sub_subject` VARCHAR(5) DEFAULT NULL,
  `clear_date` VARCHAR(12) DEFAULT NULL COMMENT '清算时间',
  `n_balance` DECIMAL(19,4) DEFAULT '0.0000' COMMENT '清算金额可用',
  `f_balance` DECIMAL(19,4) DEFAULT '0.0000' COMMENT '冻结',
  `cur_n_balance` DECIMAL(19,4) DEFAULT '0.0000' COMMENT '清算金额账户',
  `cur_f_balance` DECIMAL(19,4) DEFAULT '0.0000',
  `status` VARCHAR(1) DEFAULT '0' COMMENT '暂时无用',
  `remark` VARCHAR(255) DEFAULT NULL COMMENT '保留',
  `create_by` VARCHAR(50) DEFAULT NULL,
  `create_time` DATETIME DEFAULT NULL,
  `update_by` VARCHAR(50) DEFAULT NULL,
  `update_time` DATETIME DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;