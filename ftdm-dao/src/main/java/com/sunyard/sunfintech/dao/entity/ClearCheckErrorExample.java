package com.sunyard.sunfintech.dao.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ClearCheckErrorExample {
    /**
     * clear_check_error
     */
    protected String orderByClause;

    /**
     * clear_check_error
     */
    protected boolean distinct;

    /**
     * clear_check_error
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2017-07-04
     */
    public ClearCheckErrorExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *
     * @mbggenerated 2017-07-04
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *
     * @mbggenerated 2017-07-04
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *
     * @mbggenerated 2017-07-04
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *
     * @mbggenerated 2017-07-04
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *
     * @mbggenerated 2017-07-04
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *
     * @mbggenerated 2017-07-04
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *
     * @mbggenerated 2017-07-04
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *
     * @mbggenerated 2017-07-04
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     *
     * @mbggenerated 2017-07-04
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *
     * @mbggenerated 2017-07-04
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * clear_check_error 2017-07-04
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andClear_dateIsNull() {
            addCriterion("clear_date is null");
            return (Criteria) this;
        }

        public Criteria andClear_dateIsNotNull() {
            addCriterion("clear_date is not null");
            return (Criteria) this;
        }

        public Criteria andClear_dateEqualTo(String value) {
            addCriterion("clear_date =", value, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateNotEqualTo(String value) {
            addCriterion("clear_date <>", value, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateGreaterThan(String value) {
            addCriterion("clear_date >", value, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateGreaterThanOrEqualTo(String value) {
            addCriterion("clear_date >=", value, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateLessThan(String value) {
            addCriterion("clear_date <", value, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateLessThanOrEqualTo(String value) {
            addCriterion("clear_date <=", value, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateLike(String value) {
            addCriterion("clear_date like", value, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateNotLike(String value) {
            addCriterion("clear_date not like", value, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateIn(List<String> values) {
            addCriterion("clear_date in", values, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateNotIn(List<String> values) {
            addCriterion("clear_date not in", values, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateBetween(String value1, String value2) {
            addCriterion("clear_date between", value1, value2, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateNotBetween(String value1, String value2) {
            addCriterion("clear_date not between", value1, value2, "clear_date");
            return (Criteria) this;
        }

        public Criteria andPlat_noIsNull() {
            addCriterion("plat_no is null");
            return (Criteria) this;
        }

        public Criteria andPlat_noIsNotNull() {
            addCriterion("plat_no is not null");
            return (Criteria) this;
        }

        public Criteria andPlat_noEqualTo(String value) {
            addCriterion("plat_no =", value, "plat_no");
            return (Criteria) this;
        }

        public Criteria andPlat_noNotEqualTo(String value) {
            addCriterion("plat_no <>", value, "plat_no");
            return (Criteria) this;
        }

        public Criteria andPlat_noGreaterThan(String value) {
            addCriterion("plat_no >", value, "plat_no");
            return (Criteria) this;
        }

        public Criteria andPlat_noGreaterThanOrEqualTo(String value) {
            addCriterion("plat_no >=", value, "plat_no");
            return (Criteria) this;
        }

        public Criteria andPlat_noLessThan(String value) {
            addCriterion("plat_no <", value, "plat_no");
            return (Criteria) this;
        }

        public Criteria andPlat_noLessThanOrEqualTo(String value) {
            addCriterion("plat_no <=", value, "plat_no");
            return (Criteria) this;
        }

        public Criteria andPlat_noLike(String value) {
            addCriterion("plat_no like", value, "plat_no");
            return (Criteria) this;
        }

        public Criteria andPlat_noNotLike(String value) {
            addCriterion("plat_no not like", value, "plat_no");
            return (Criteria) this;
        }

        public Criteria andPlat_noIn(List<String> values) {
            addCriterion("plat_no in", values, "plat_no");
            return (Criteria) this;
        }

        public Criteria andPlat_noNotIn(List<String> values) {
            addCriterion("plat_no not in", values, "plat_no");
            return (Criteria) this;
        }

        public Criteria andPlat_noBetween(String value1, String value2) {
            addCriterion("plat_no between", value1, value2, "plat_no");
            return (Criteria) this;
        }

        public Criteria andPlat_noNotBetween(String value1, String value2) {
            addCriterion("plat_no not between", value1, value2, "plat_no");
            return (Criteria) this;
        }

        public Criteria andPlatcustIsNull() {
            addCriterion("platcust is null");
            return (Criteria) this;
        }

        public Criteria andPlatcustIsNotNull() {
            addCriterion("platcust is not null");
            return (Criteria) this;
        }

        public Criteria andPlatcustEqualTo(String value) {
            addCriterion("platcust =", value, "platcust");
            return (Criteria) this;
        }

        public Criteria andPlatcustNotEqualTo(String value) {
            addCriterion("platcust <>", value, "platcust");
            return (Criteria) this;
        }

        public Criteria andPlatcustGreaterThan(String value) {
            addCriterion("platcust >", value, "platcust");
            return (Criteria) this;
        }

        public Criteria andPlatcustGreaterThanOrEqualTo(String value) {
            addCriterion("platcust >=", value, "platcust");
            return (Criteria) this;
        }

        public Criteria andPlatcustLessThan(String value) {
            addCriterion("platcust <", value, "platcust");
            return (Criteria) this;
        }

        public Criteria andPlatcustLessThanOrEqualTo(String value) {
            addCriterion("platcust <=", value, "platcust");
            return (Criteria) this;
        }

        public Criteria andPlatcustLike(String value) {
            addCriterion("platcust like", value, "platcust");
            return (Criteria) this;
        }

        public Criteria andPlatcustNotLike(String value) {
            addCriterion("platcust not like", value, "platcust");
            return (Criteria) this;
        }

        public Criteria andPlatcustIn(List<String> values) {
            addCriterion("platcust in", values, "platcust");
            return (Criteria) this;
        }

        public Criteria andPlatcustNotIn(List<String> values) {
            addCriterion("platcust not in", values, "platcust");
            return (Criteria) this;
        }

        public Criteria andPlatcustBetween(String value1, String value2) {
            addCriterion("platcust between", value1, value2, "platcust");
            return (Criteria) this;
        }

        public Criteria andPlatcustNotBetween(String value1, String value2) {
            addCriterion("platcust not between", value1, value2, "platcust");
            return (Criteria) this;
        }

        public Criteria andAccount_typeIsNull() {
            addCriterion("account_type is null");
            return (Criteria) this;
        }

        public Criteria andAccount_typeIsNotNull() {
            addCriterion("account_type is not null");
            return (Criteria) this;
        }

        public Criteria andAccount_typeEqualTo(String value) {
            addCriterion("account_type =", value, "account_type");
            return (Criteria) this;
        }

        public Criteria andAccount_typeNotEqualTo(String value) {
            addCriterion("account_type <>", value, "account_type");
            return (Criteria) this;
        }

        public Criteria andAccount_typeGreaterThan(String value) {
            addCriterion("account_type >", value, "account_type");
            return (Criteria) this;
        }

        public Criteria andAccount_typeGreaterThanOrEqualTo(String value) {
            addCriterion("account_type >=", value, "account_type");
            return (Criteria) this;
        }

        public Criteria andAccount_typeLessThan(String value) {
            addCriterion("account_type <", value, "account_type");
            return (Criteria) this;
        }

        public Criteria andAccount_typeLessThanOrEqualTo(String value) {
            addCriterion("account_type <=", value, "account_type");
            return (Criteria) this;
        }

        public Criteria andAccount_typeLike(String value) {
            addCriterion("account_type like", value, "account_type");
            return (Criteria) this;
        }

        public Criteria andAccount_typeNotLike(String value) {
            addCriterion("account_type not like", value, "account_type");
            return (Criteria) this;
        }

        public Criteria andAccount_typeIn(List<String> values) {
            addCriterion("account_type in", values, "account_type");
            return (Criteria) this;
        }

        public Criteria andAccount_typeNotIn(List<String> values) {
            addCriterion("account_type not in", values, "account_type");
            return (Criteria) this;
        }

        public Criteria andAccount_typeBetween(String value1, String value2) {
            addCriterion("account_type between", value1, value2, "account_type");
            return (Criteria) this;
        }

        public Criteria andAccount_typeNotBetween(String value1, String value2) {
            addCriterion("account_type not between", value1, value2, "account_type");
            return (Criteria) this;
        }

        public Criteria andAccount_nameIsNull() {
            addCriterion("account_name is null");
            return (Criteria) this;
        }

        public Criteria andAccount_nameIsNotNull() {
            addCriterion("account_name is not null");
            return (Criteria) this;
        }

        public Criteria andAccount_nameEqualTo(String value) {
            addCriterion("account_name =", value, "account_name");
            return (Criteria) this;
        }

        public Criteria andAccount_nameNotEqualTo(String value) {
            addCriterion("account_name <>", value, "account_name");
            return (Criteria) this;
        }

        public Criteria andAccount_nameGreaterThan(String value) {
            addCriterion("account_name >", value, "account_name");
            return (Criteria) this;
        }

        public Criteria andAccount_nameGreaterThanOrEqualTo(String value) {
            addCriterion("account_name >=", value, "account_name");
            return (Criteria) this;
        }

        public Criteria andAccount_nameLessThan(String value) {
            addCriterion("account_name <", value, "account_name");
            return (Criteria) this;
        }

        public Criteria andAccount_nameLessThanOrEqualTo(String value) {
            addCriterion("account_name <=", value, "account_name");
            return (Criteria) this;
        }

        public Criteria andAccount_nameLike(String value) {
            addCriterion("account_name like", value, "account_name");
            return (Criteria) this;
        }

        public Criteria andAccount_nameNotLike(String value) {
            addCriterion("account_name not like", value, "account_name");
            return (Criteria) this;
        }

        public Criteria andAccount_nameIn(List<String> values) {
            addCriterion("account_name in", values, "account_name");
            return (Criteria) this;
        }

        public Criteria andAccount_nameNotIn(List<String> values) {
            addCriterion("account_name not in", values, "account_name");
            return (Criteria) this;
        }

        public Criteria andAccount_nameBetween(String value1, String value2) {
            addCriterion("account_name between", value1, value2, "account_name");
            return (Criteria) this;
        }

        public Criteria andAccount_nameNotBetween(String value1, String value2) {
            addCriterion("account_name not between", value1, value2, "account_name");
            return (Criteria) this;
        }

        public Criteria andClear_codeIsNull() {
            addCriterion("clear_code is null");
            return (Criteria) this;
        }

        public Criteria andClear_codeIsNotNull() {
            addCriterion("clear_code is not null");
            return (Criteria) this;
        }

        public Criteria andClear_codeEqualTo(String value) {
            addCriterion("clear_code =", value, "clear_code");
            return (Criteria) this;
        }

        public Criteria andClear_codeNotEqualTo(String value) {
            addCriterion("clear_code <>", value, "clear_code");
            return (Criteria) this;
        }

        public Criteria andClear_codeGreaterThan(String value) {
            addCriterion("clear_code >", value, "clear_code");
            return (Criteria) this;
        }

        public Criteria andClear_codeGreaterThanOrEqualTo(String value) {
            addCriterion("clear_code >=", value, "clear_code");
            return (Criteria) this;
        }

        public Criteria andClear_codeLessThan(String value) {
            addCriterion("clear_code <", value, "clear_code");
            return (Criteria) this;
        }

        public Criteria andClear_codeLessThanOrEqualTo(String value) {
            addCriterion("clear_code <=", value, "clear_code");
            return (Criteria) this;
        }

        public Criteria andClear_codeLike(String value) {
            addCriterion("clear_code like", value, "clear_code");
            return (Criteria) this;
        }

        public Criteria andClear_codeNotLike(String value) {
            addCriterion("clear_code not like", value, "clear_code");
            return (Criteria) this;
        }

        public Criteria andClear_codeIn(List<String> values) {
            addCriterion("clear_code in", values, "clear_code");
            return (Criteria) this;
        }

        public Criteria andClear_codeNotIn(List<String> values) {
            addCriterion("clear_code not in", values, "clear_code");
            return (Criteria) this;
        }

        public Criteria andClear_codeBetween(String value1, String value2) {
            addCriterion("clear_code between", value1, value2, "clear_code");
            return (Criteria) this;
        }

        public Criteria andClear_codeNotBetween(String value1, String value2) {
            addCriterion("clear_code not between", value1, value2, "clear_code");
            return (Criteria) this;
        }

        public Criteria andClear_nameIsNull() {
            addCriterion("clear_name is null");
            return (Criteria) this;
        }

        public Criteria andClear_nameIsNotNull() {
            addCriterion("clear_name is not null");
            return (Criteria) this;
        }

        public Criteria andClear_nameEqualTo(String value) {
            addCriterion("clear_name =", value, "clear_name");
            return (Criteria) this;
        }

        public Criteria andClear_nameNotEqualTo(String value) {
            addCriterion("clear_name <>", value, "clear_name");
            return (Criteria) this;
        }

        public Criteria andClear_nameGreaterThan(String value) {
            addCriterion("clear_name >", value, "clear_name");
            return (Criteria) this;
        }

        public Criteria andClear_nameGreaterThanOrEqualTo(String value) {
            addCriterion("clear_name >=", value, "clear_name");
            return (Criteria) this;
        }

        public Criteria andClear_nameLessThan(String value) {
            addCriterion("clear_name <", value, "clear_name");
            return (Criteria) this;
        }

        public Criteria andClear_nameLessThanOrEqualTo(String value) {
            addCriterion("clear_name <=", value, "clear_name");
            return (Criteria) this;
        }

        public Criteria andClear_nameLike(String value) {
            addCriterion("clear_name like", value, "clear_name");
            return (Criteria) this;
        }

        public Criteria andClear_nameNotLike(String value) {
            addCriterion("clear_name not like", value, "clear_name");
            return (Criteria) this;
        }

        public Criteria andClear_nameIn(List<String> values) {
            addCriterion("clear_name in", values, "clear_name");
            return (Criteria) this;
        }

        public Criteria andClear_nameNotIn(List<String> values) {
            addCriterion("clear_name not in", values, "clear_name");
            return (Criteria) this;
        }

        public Criteria andClear_nameBetween(String value1, String value2) {
            addCriterion("clear_name between", value1, value2, "clear_name");
            return (Criteria) this;
        }

        public Criteria andClear_nameNotBetween(String value1, String value2) {
            addCriterion("clear_name not between", value1, value2, "clear_name");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andSerial_idIsNull() {
            addCriterion("serial_id is null");
            return (Criteria) this;
        }

        public Criteria andSerial_idIsNotNull() {
            addCriterion("serial_id is not null");
            return (Criteria) this;
        }

        public Criteria andSerial_idEqualTo(String value) {
            addCriterion("serial_id =", value, "serial_id");
            return (Criteria) this;
        }

        public Criteria andSerial_idNotEqualTo(String value) {
            addCriterion("serial_id <>", value, "serial_id");
            return (Criteria) this;
        }

        public Criteria andSerial_idGreaterThan(String value) {
            addCriterion("serial_id >", value, "serial_id");
            return (Criteria) this;
        }

        public Criteria andSerial_idGreaterThanOrEqualTo(String value) {
            addCriterion("serial_id >=", value, "serial_id");
            return (Criteria) this;
        }

        public Criteria andSerial_idLessThan(String value) {
            addCriterion("serial_id <", value, "serial_id");
            return (Criteria) this;
        }

        public Criteria andSerial_idLessThanOrEqualTo(String value) {
            addCriterion("serial_id <=", value, "serial_id");
            return (Criteria) this;
        }

        public Criteria andSerial_idLike(String value) {
            addCriterion("serial_id like", value, "serial_id");
            return (Criteria) this;
        }

        public Criteria andSerial_idNotLike(String value) {
            addCriterion("serial_id not like", value, "serial_id");
            return (Criteria) this;
        }

        public Criteria andSerial_idIn(List<String> values) {
            addCriterion("serial_id in", values, "serial_id");
            return (Criteria) this;
        }

        public Criteria andSerial_idNotIn(List<String> values) {
            addCriterion("serial_id not in", values, "serial_id");
            return (Criteria) this;
        }

        public Criteria andSerial_idBetween(String value1, String value2) {
            addCriterion("serial_id between", value1, value2, "serial_id");
            return (Criteria) this;
        }

        public Criteria andSerial_idNotBetween(String value1, String value2) {
            addCriterion("serial_id not between", value1, value2, "serial_id");
            return (Criteria) this;
        }

        public Criteria andOrder_noIsNull() {
            addCriterion("order_no is null");
            return (Criteria) this;
        }

        public Criteria andOrder_noIsNotNull() {
            addCriterion("order_no is not null");
            return (Criteria) this;
        }

        public Criteria andOrder_noEqualTo(String value) {
            addCriterion("order_no =", value, "order_no");
            return (Criteria) this;
        }

        public Criteria andOrder_noNotEqualTo(String value) {
            addCriterion("order_no <>", value, "order_no");
            return (Criteria) this;
        }

        public Criteria andOrder_noGreaterThan(String value) {
            addCriterion("order_no >", value, "order_no");
            return (Criteria) this;
        }

        public Criteria andOrder_noGreaterThanOrEqualTo(String value) {
            addCriterion("order_no >=", value, "order_no");
            return (Criteria) this;
        }

        public Criteria andOrder_noLessThan(String value) {
            addCriterion("order_no <", value, "order_no");
            return (Criteria) this;
        }

        public Criteria andOrder_noLessThanOrEqualTo(String value) {
            addCriterion("order_no <=", value, "order_no");
            return (Criteria) this;
        }

        public Criteria andOrder_noLike(String value) {
            addCriterion("order_no like", value, "order_no");
            return (Criteria) this;
        }

        public Criteria andOrder_noNotLike(String value) {
            addCriterion("order_no not like", value, "order_no");
            return (Criteria) this;
        }

        public Criteria andOrder_noIn(List<String> values) {
            addCriterion("order_no in", values, "order_no");
            return (Criteria) this;
        }

        public Criteria andOrder_noNotIn(List<String> values) {
            addCriterion("order_no not in", values, "order_no");
            return (Criteria) this;
        }

        public Criteria andOrder_noBetween(String value1, String value2) {
            addCriterion("order_no between", value1, value2, "order_no");
            return (Criteria) this;
        }

        public Criteria andOrder_noNotBetween(String value1, String value2) {
            addCriterion("order_no not between", value1, value2, "order_no");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andPay_codeIsNull() {
            addCriterion("pay_code is null");
            return (Criteria) this;
        }

        public Criteria andPay_codeIsNotNull() {
            addCriterion("pay_code is not null");
            return (Criteria) this;
        }

        public Criteria andPay_codeEqualTo(String value) {
            addCriterion("pay_code =", value, "pay_code");
            return (Criteria) this;
        }

        public Criteria andPay_codeNotEqualTo(String value) {
            addCriterion("pay_code <>", value, "pay_code");
            return (Criteria) this;
        }

        public Criteria andPay_codeGreaterThan(String value) {
            addCriterion("pay_code >", value, "pay_code");
            return (Criteria) this;
        }

        public Criteria andPay_codeGreaterThanOrEqualTo(String value) {
            addCriterion("pay_code >=", value, "pay_code");
            return (Criteria) this;
        }

        public Criteria andPay_codeLessThan(String value) {
            addCriterion("pay_code <", value, "pay_code");
            return (Criteria) this;
        }

        public Criteria andPay_codeLessThanOrEqualTo(String value) {
            addCriterion("pay_code <=", value, "pay_code");
            return (Criteria) this;
        }

        public Criteria andPay_codeLike(String value) {
            addCriterion("pay_code like", value, "pay_code");
            return (Criteria) this;
        }

        public Criteria andPay_codeNotLike(String value) {
            addCriterion("pay_code not like", value, "pay_code");
            return (Criteria) this;
        }

        public Criteria andPay_codeIn(List<String> values) {
            addCriterion("pay_code in", values, "pay_code");
            return (Criteria) this;
        }

        public Criteria andPay_codeNotIn(List<String> values) {
            addCriterion("pay_code not in", values, "pay_code");
            return (Criteria) this;
        }

        public Criteria andPay_codeBetween(String value1, String value2) {
            addCriterion("pay_code between", value1, value2, "pay_code");
            return (Criteria) this;
        }

        public Criteria andPay_codeNotBetween(String value1, String value2) {
            addCriterion("pay_code not between", value1, value2, "pay_code");
            return (Criteria) this;
        }

        public Criteria andTrans_amtIsNull() {
            addCriterion("trans_amt is null");
            return (Criteria) this;
        }

        public Criteria andTrans_amtIsNotNull() {
            addCriterion("trans_amt is not null");
            return (Criteria) this;
        }

        public Criteria andTrans_amtEqualTo(BigDecimal value) {
            addCriterion("trans_amt =", value, "trans_amt");
            return (Criteria) this;
        }

        public Criteria andTrans_amtNotEqualTo(BigDecimal value) {
            addCriterion("trans_amt <>", value, "trans_amt");
            return (Criteria) this;
        }

        public Criteria andTrans_amtGreaterThan(BigDecimal value) {
            addCriterion("trans_amt >", value, "trans_amt");
            return (Criteria) this;
        }

        public Criteria andTrans_amtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("trans_amt >=", value, "trans_amt");
            return (Criteria) this;
        }

        public Criteria andTrans_amtLessThan(BigDecimal value) {
            addCriterion("trans_amt <", value, "trans_amt");
            return (Criteria) this;
        }

        public Criteria andTrans_amtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("trans_amt <=", value, "trans_amt");
            return (Criteria) this;
        }

        public Criteria andTrans_amtIn(List<BigDecimal> values) {
            addCriterion("trans_amt in", values, "trans_amt");
            return (Criteria) this;
        }

        public Criteria andTrans_amtNotIn(List<BigDecimal> values) {
            addCriterion("trans_amt not in", values, "trans_amt");
            return (Criteria) this;
        }

        public Criteria andTrans_amtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("trans_amt between", value1, value2, "trans_amt");
            return (Criteria) this;
        }

        public Criteria andTrans_amtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("trans_amt not between", value1, value2, "trans_amt");
            return (Criteria) this;
        }

        public Criteria andPay_amtIsNull() {
            addCriterion("pay_amt is null");
            return (Criteria) this;
        }

        public Criteria andPay_amtIsNotNull() {
            addCriterion("pay_amt is not null");
            return (Criteria) this;
        }

        public Criteria andPay_amtEqualTo(BigDecimal value) {
            addCriterion("pay_amt =", value, "pay_amt");
            return (Criteria) this;
        }

        public Criteria andPay_amtNotEqualTo(BigDecimal value) {
            addCriterion("pay_amt <>", value, "pay_amt");
            return (Criteria) this;
        }

        public Criteria andPay_amtGreaterThan(BigDecimal value) {
            addCriterion("pay_amt >", value, "pay_amt");
            return (Criteria) this;
        }

        public Criteria andPay_amtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("pay_amt >=", value, "pay_amt");
            return (Criteria) this;
        }

        public Criteria andPay_amtLessThan(BigDecimal value) {
            addCriterion("pay_amt <", value, "pay_amt");
            return (Criteria) this;
        }

        public Criteria andPay_amtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("pay_amt <=", value, "pay_amt");
            return (Criteria) this;
        }

        public Criteria andPay_amtIn(List<BigDecimal> values) {
            addCriterion("pay_amt in", values, "pay_amt");
            return (Criteria) this;
        }

        public Criteria andPay_amtNotIn(List<BigDecimal> values) {
            addCriterion("pay_amt not in", values, "pay_amt");
            return (Criteria) this;
        }

        public Criteria andPay_amtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("pay_amt between", value1, value2, "pay_amt");
            return (Criteria) this;
        }

        public Criteria andPay_amtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("pay_amt not between", value1, value2, "pay_amt");
            return (Criteria) this;
        }
    }

    /**
     * clear_check_error
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * clear_check_error 2017-07-04
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}