drop table if exists acc_openconfig;

/*==============================================================*/
/* Table: acc_openconfig                                        */
/*==============================================================*/
create table acc_openconfig
(
   id                   int(20)                        not null,
   mall_no              varchar(32)                    null,
   plat_no              varchar(32)                    null,
   account_type         varchar(2)                     null,
   account_name         varchar(32)                    null,
   subject              varchar(2)                     null,
   sub_subject          varchar(2)                     null,
   enabled              char(1)                        null,
   remark               varchar(255)                   null,
   create_by            varchar(50)                    null,
   create_time          datetime                       null,
   update_by            varchar(50)                    null,
   update_time          datetime                       null,
   constraint PK_ACC_OPENCONFIG primary key clustered (id)
);

delete from `sun_ftdm`.`acc_openconfig` where `mall_no` = 'BOB-FENGJR-C-20170509';

/*************用户账户*************/
INSERT INTO `sun_ftdm`.`acc_openconfig` (`mall_no`, `plat_no`, `account_type`, `subject`, `sub_subject`, `enabled`) VALUES ('BOB-FENGJR-C-20170509', 'BOB-FENGJR-ZS-C-20170509', '01', '01','01',  '1');

INSERT INTO `sun_ftdm`.`acc_openconfig` (`mall_no`, `plat_no`, `account_type`, `subject`, `sub_subject`, `enabled`) VALUES ('BOB-FENGJR-C-20170509', 'BOB-FENGJR-ZS-C-20170509', '01', '02','01',  '1');

INSERT INTO `sun_ftdm`.`acc_openconfig` (`mall_no`, `plat_no`, `account_type`, `subject`, `sub_subject`, `enabled`) VALUES ('BOB-FENGJR-C-20170509', 'BOB-FENGJR-ZS-C-20170509', '01',  '01','02', '1');

INSERT INTO `sun_ftdm`.`acc_openconfig` (`mall_no`, `plat_no`, `account_type`, `subject`, `sub_subject`, `enabled`) VALUES ('BOB-FENGJR-C-20170509', 'BOB-FENGJR-ZS-C-20170509', '01',  '02','02', '1');

INSERT INTO `sun_ftdm`.`acc_openconfig` (`mall_no`, `plat_no`, `account_type`, `subject`, `sub_subject`, `enabled`) VALUES ('BOB-FENGJR-C-20170509', 'BOB-FENGJR-ZX-C-20170509', '01', '01','01',  '1');

INSERT INTO `sun_ftdm`.`acc_openconfig` (`mall_no`, `plat_no`, `account_type`, `subject`, `sub_subject`, `enabled`) VALUES ('BOB-FENGJR-C-20170509', 'BOB-FENGJR-ZX-C-20170509', '01','02', '01',  '1');

INSERT INTO `sun_ftdm`.`acc_openconfig` (`mall_no`, `plat_no`, `account_type`, `subject`, `sub_subject`, `enabled`) VALUES ('BOB-FENGJR-C-20170509', 'BOB-FENGJR-ZX-C-20170509', '01', '01','02',  '1');

INSERT INTO `sun_ftdm`.`acc_openconfig` (`mall_no`, `plat_no`, `account_type`, `subject`, `sub_subject`, `enabled`) VALUES ('BOB-FENGJR-C-20170509', 'BOB-FENGJR-ZX-C-20170509', '01', '02','02',  '1');

/*************电子账户*************/
INSERT INTO `sun_ftdm`.`acc_openconfig` (`mall_no`, `plat_no`, `account_type`, `subject`, `sub_subject`, `enabled`) VALUES ('BOB-FENGJR-C-20170509', 'BOB-FENGJR-C-20170509', '04', '01', '03', '1');

INSERT INTO `sun_ftdm`.`acc_openconfig` (`mall_no`, `plat_no`, `account_type`, `subject`, `sub_subject`, `enabled`) VALUES ('BOB-FENGJR-C-20170509', 'BOB-FENGJR-C-20170509', '04', '02', '03','1');


/*************标的账户*************/
INSERT INTO `sun_ftdm`.`acc_openconfig` (`mall_no`, `plat_no`, `account_type`, `subject`, `sub_subject`, `enabled`) VALUES ('BOB-FENGJR-C-20170509', 'BOB-FENGJR-ZS-C-20170509', '03', '01','18',  '1');

INSERT INTO `sun_ftdm`.`acc_openconfig` (`mall_no`, `plat_no`, `account_type`, `subject`, `sub_subject`, `enabled`) VALUES ('BOB-FENGJR-C-20170509', 'BOB-FENGJR-ZS-C-20170509', '03','02', '18',  '1');

INSERT INTO `sun_ftdm`.`acc_openconfig` (`mall_no`, `plat_no`, `account_type`, `subject`, `sub_subject`, `enabled`) VALUES ('BOB-FENGJR-C-20170509', 'BOB-FENGJR-ZX-C-20170509', '03', '01','18',  '1');

INSERT INTO `sun_ftdm`.`acc_openconfig` (`mall_no`, `plat_no`, `account_type`, `subject`, `sub_subject`, `enabled`) VALUES ('BOB-FENGJR-C-20170509', 'BOB-FENGJR-ZX-C-20170509', '03','02', '18',  '1');


