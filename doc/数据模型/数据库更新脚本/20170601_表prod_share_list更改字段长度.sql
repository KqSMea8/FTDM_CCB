-- 将prod_share_list的fee_priority字段原长度1改为2
alter table prod_share_list modify column fee_priority varchar(2);