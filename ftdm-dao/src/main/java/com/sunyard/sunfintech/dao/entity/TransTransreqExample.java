package com.sunyard.sunfintech.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransTransreqExample {
    /**
     * trans_transreq
     */
    protected String orderByClause;

    /**
     * trans_transreq
     */
    protected boolean distinct;

    /**
     * trans_transreq
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2018-01-23
     */
    public TransTransreqExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *
     * @mbggenerated 2018-01-23
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *
     * @mbggenerated 2018-01-23
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *
     * @mbggenerated 2018-01-23
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *
     * @mbggenerated 2018-01-23
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *
     * @mbggenerated 2018-01-23
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *
     * @mbggenerated 2018-01-23
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *
     * @mbggenerated 2018-01-23
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *
     * @mbggenerated 2018-01-23
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
     * @mbggenerated 2018-01-23
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *
     * @mbggenerated 2018-01-23
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * trans_transreq 2018-01-23
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
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

        public Criteria andTrans_serialIsNull() {
            addCriterion("trans_serial is null");
            return (Criteria) this;
        }

        public Criteria andTrans_serialIsNotNull() {
            addCriterion("trans_serial is not null");
            return (Criteria) this;
        }

        public Criteria andTrans_serialEqualTo(String value) {
            addCriterion("trans_serial =", value, "trans_serial");
            return (Criteria) this;
        }

        public Criteria andTrans_serialNotEqualTo(String value) {
            addCriterion("trans_serial <>", value, "trans_serial");
            return (Criteria) this;
        }

        public Criteria andTrans_serialGreaterThan(String value) {
            addCriterion("trans_serial >", value, "trans_serial");
            return (Criteria) this;
        }

        public Criteria andTrans_serialGreaterThanOrEqualTo(String value) {
            addCriterion("trans_serial >=", value, "trans_serial");
            return (Criteria) this;
        }

        public Criteria andTrans_serialLessThan(String value) {
            addCriterion("trans_serial <", value, "trans_serial");
            return (Criteria) this;
        }

        public Criteria andTrans_serialLessThanOrEqualTo(String value) {
            addCriterion("trans_serial <=", value, "trans_serial");
            return (Criteria) this;
        }

        public Criteria andTrans_serialLike(String value) {
            addCriterion("trans_serial like", value, "trans_serial");
            return (Criteria) this;
        }

        public Criteria andTrans_serialNotLike(String value) {
            addCriterion("trans_serial not like", value, "trans_serial");
            return (Criteria) this;
        }

        public Criteria andTrans_serialIn(List<String> values) {
            addCriterion("trans_serial in", values, "trans_serial");
            return (Criteria) this;
        }

        public Criteria andTrans_serialNotIn(List<String> values) {
            addCriterion("trans_serial not in", values, "trans_serial");
            return (Criteria) this;
        }

        public Criteria andTrans_serialBetween(String value1, String value2) {
            addCriterion("trans_serial between", value1, value2, "trans_serial");
            return (Criteria) this;
        }

        public Criteria andTrans_serialNotBetween(String value1, String value2) {
            addCriterion("trans_serial not between", value1, value2, "trans_serial");
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

        public Criteria andTrans_codeIsNull() {
            addCriterion("trans_code is null");
            return (Criteria) this;
        }

        public Criteria andTrans_codeIsNotNull() {
            addCriterion("trans_code is not null");
            return (Criteria) this;
        }

        public Criteria andTrans_codeEqualTo(String value) {
            addCriterion("trans_code =", value, "trans_code");
            return (Criteria) this;
        }

        public Criteria andTrans_codeNotEqualTo(String value) {
            addCriterion("trans_code <>", value, "trans_code");
            return (Criteria) this;
        }

        public Criteria andTrans_codeGreaterThan(String value) {
            addCriterion("trans_code >", value, "trans_code");
            return (Criteria) this;
        }

        public Criteria andTrans_codeGreaterThanOrEqualTo(String value) {
            addCriterion("trans_code >=", value, "trans_code");
            return (Criteria) this;
        }

        public Criteria andTrans_codeLessThan(String value) {
            addCriterion("trans_code <", value, "trans_code");
            return (Criteria) this;
        }

        public Criteria andTrans_codeLessThanOrEqualTo(String value) {
            addCriterion("trans_code <=", value, "trans_code");
            return (Criteria) this;
        }

        public Criteria andTrans_codeLike(String value) {
            addCriterion("trans_code like", value, "trans_code");
            return (Criteria) this;
        }

        public Criteria andTrans_codeNotLike(String value) {
            addCriterion("trans_code not like", value, "trans_code");
            return (Criteria) this;
        }

        public Criteria andTrans_codeIn(List<String> values) {
            addCriterion("trans_code in", values, "trans_code");
            return (Criteria) this;
        }

        public Criteria andTrans_codeNotIn(List<String> values) {
            addCriterion("trans_code not in", values, "trans_code");
            return (Criteria) this;
        }

        public Criteria andTrans_codeBetween(String value1, String value2) {
            addCriterion("trans_code between", value1, value2, "trans_code");
            return (Criteria) this;
        }

        public Criteria andTrans_codeNotBetween(String value1, String value2) {
            addCriterion("trans_code not between", value1, value2, "trans_code");
            return (Criteria) this;
        }

        public Criteria andTrans_nameIsNull() {
            addCriterion("trans_name is null");
            return (Criteria) this;
        }

        public Criteria andTrans_nameIsNotNull() {
            addCriterion("trans_name is not null");
            return (Criteria) this;
        }

        public Criteria andTrans_nameEqualTo(String value) {
            addCriterion("trans_name =", value, "trans_name");
            return (Criteria) this;
        }

        public Criteria andTrans_nameNotEqualTo(String value) {
            addCriterion("trans_name <>", value, "trans_name");
            return (Criteria) this;
        }

        public Criteria andTrans_nameGreaterThan(String value) {
            addCriterion("trans_name >", value, "trans_name");
            return (Criteria) this;
        }

        public Criteria andTrans_nameGreaterThanOrEqualTo(String value) {
            addCriterion("trans_name >=", value, "trans_name");
            return (Criteria) this;
        }

        public Criteria andTrans_nameLessThan(String value) {
            addCriterion("trans_name <", value, "trans_name");
            return (Criteria) this;
        }

        public Criteria andTrans_nameLessThanOrEqualTo(String value) {
            addCriterion("trans_name <=", value, "trans_name");
            return (Criteria) this;
        }

        public Criteria andTrans_nameLike(String value) {
            addCriterion("trans_name like", value, "trans_name");
            return (Criteria) this;
        }

        public Criteria andTrans_nameNotLike(String value) {
            addCriterion("trans_name not like", value, "trans_name");
            return (Criteria) this;
        }

        public Criteria andTrans_nameIn(List<String> values) {
            addCriterion("trans_name in", values, "trans_name");
            return (Criteria) this;
        }

        public Criteria andTrans_nameNotIn(List<String> values) {
            addCriterion("trans_name not in", values, "trans_name");
            return (Criteria) this;
        }

        public Criteria andTrans_nameBetween(String value1, String value2) {
            addCriterion("trans_name between", value1, value2, "trans_name");
            return (Criteria) this;
        }

        public Criteria andTrans_nameNotBetween(String value1, String value2) {
            addCriterion("trans_name not between", value1, value2, "trans_name");
            return (Criteria) this;
        }

        public Criteria andTrans_dateIsNull() {
            addCriterion("trans_date is null");
            return (Criteria) this;
        }

        public Criteria andTrans_dateIsNotNull() {
            addCriterion("trans_date is not null");
            return (Criteria) this;
        }

        public Criteria andTrans_dateEqualTo(String value) {
            addCriterion("trans_date =", value, "trans_date");
            return (Criteria) this;
        }

        public Criteria andTrans_dateNotEqualTo(String value) {
            addCriterion("trans_date <>", value, "trans_date");
            return (Criteria) this;
        }

        public Criteria andTrans_dateGreaterThan(String value) {
            addCriterion("trans_date >", value, "trans_date");
            return (Criteria) this;
        }

        public Criteria andTrans_dateGreaterThanOrEqualTo(String value) {
            addCriterion("trans_date >=", value, "trans_date");
            return (Criteria) this;
        }

        public Criteria andTrans_dateLessThan(String value) {
            addCriterion("trans_date <", value, "trans_date");
            return (Criteria) this;
        }

        public Criteria andTrans_dateLessThanOrEqualTo(String value) {
            addCriterion("trans_date <=", value, "trans_date");
            return (Criteria) this;
        }

        public Criteria andTrans_dateLike(String value) {
            addCriterion("trans_date like", value, "trans_date");
            return (Criteria) this;
        }

        public Criteria andTrans_dateNotLike(String value) {
            addCriterion("trans_date not like", value, "trans_date");
            return (Criteria) this;
        }

        public Criteria andTrans_dateIn(List<String> values) {
            addCriterion("trans_date in", values, "trans_date");
            return (Criteria) this;
        }

        public Criteria andTrans_dateNotIn(List<String> values) {
            addCriterion("trans_date not in", values, "trans_date");
            return (Criteria) this;
        }

        public Criteria andTrans_dateBetween(String value1, String value2) {
            addCriterion("trans_date between", value1, value2, "trans_date");
            return (Criteria) this;
        }

        public Criteria andTrans_dateNotBetween(String value1, String value2) {
            addCriterion("trans_date not between", value1, value2, "trans_date");
            return (Criteria) this;
        }

        public Criteria andTrans_timeIsNull() {
            addCriterion("trans_time is null");
            return (Criteria) this;
        }

        public Criteria andTrans_timeIsNotNull() {
            addCriterion("trans_time is not null");
            return (Criteria) this;
        }

        public Criteria andTrans_timeEqualTo(String value) {
            addCriterion("trans_time =", value, "trans_time");
            return (Criteria) this;
        }

        public Criteria andTrans_timeNotEqualTo(String value) {
            addCriterion("trans_time <>", value, "trans_time");
            return (Criteria) this;
        }

        public Criteria andTrans_timeGreaterThan(String value) {
            addCriterion("trans_time >", value, "trans_time");
            return (Criteria) this;
        }

        public Criteria andTrans_timeGreaterThanOrEqualTo(String value) {
            addCriterion("trans_time >=", value, "trans_time");
            return (Criteria) this;
        }

        public Criteria andTrans_timeLessThan(String value) {
            addCriterion("trans_time <", value, "trans_time");
            return (Criteria) this;
        }

        public Criteria andTrans_timeLessThanOrEqualTo(String value) {
            addCriterion("trans_time <=", value, "trans_time");
            return (Criteria) this;
        }

        public Criteria andTrans_timeLike(String value) {
            addCriterion("trans_time like", value, "trans_time");
            return (Criteria) this;
        }

        public Criteria andTrans_timeNotLike(String value) {
            addCriterion("trans_time not like", value, "trans_time");
            return (Criteria) this;
        }

        public Criteria andTrans_timeIn(List<String> values) {
            addCriterion("trans_time in", values, "trans_time");
            return (Criteria) this;
        }

        public Criteria andTrans_timeNotIn(List<String> values) {
            addCriterion("trans_time not in", values, "trans_time");
            return (Criteria) this;
        }

        public Criteria andTrans_timeBetween(String value1, String value2) {
            addCriterion("trans_time between", value1, value2, "trans_time");
            return (Criteria) this;
        }

        public Criteria andTrans_timeNotBetween(String value1, String value2) {
            addCriterion("trans_time not between", value1, value2, "trans_time");
            return (Criteria) this;
        }

        public Criteria andReturn_codeIsNull() {
            addCriterion("return_code is null");
            return (Criteria) this;
        }

        public Criteria andReturn_codeIsNotNull() {
            addCriterion("return_code is not null");
            return (Criteria) this;
        }

        public Criteria andReturn_codeEqualTo(String value) {
            addCriterion("return_code =", value, "return_code");
            return (Criteria) this;
        }

        public Criteria andReturn_codeNotEqualTo(String value) {
            addCriterion("return_code <>", value, "return_code");
            return (Criteria) this;
        }

        public Criteria andReturn_codeGreaterThan(String value) {
            addCriterion("return_code >", value, "return_code");
            return (Criteria) this;
        }

        public Criteria andReturn_codeGreaterThanOrEqualTo(String value) {
            addCriterion("return_code >=", value, "return_code");
            return (Criteria) this;
        }

        public Criteria andReturn_codeLessThan(String value) {
            addCriterion("return_code <", value, "return_code");
            return (Criteria) this;
        }

        public Criteria andReturn_codeLessThanOrEqualTo(String value) {
            addCriterion("return_code <=", value, "return_code");
            return (Criteria) this;
        }

        public Criteria andReturn_codeLike(String value) {
            addCriterion("return_code like", value, "return_code");
            return (Criteria) this;
        }

        public Criteria andReturn_codeNotLike(String value) {
            addCriterion("return_code not like", value, "return_code");
            return (Criteria) this;
        }

        public Criteria andReturn_codeIn(List<String> values) {
            addCriterion("return_code in", values, "return_code");
            return (Criteria) this;
        }

        public Criteria andReturn_codeNotIn(List<String> values) {
            addCriterion("return_code not in", values, "return_code");
            return (Criteria) this;
        }

        public Criteria andReturn_codeBetween(String value1, String value2) {
            addCriterion("return_code between", value1, value2, "return_code");
            return (Criteria) this;
        }

        public Criteria andReturn_codeNotBetween(String value1, String value2) {
            addCriterion("return_code not between", value1, value2, "return_code");
            return (Criteria) this;
        }

        public Criteria andReturn_msgIsNull() {
            addCriterion("return_msg is null");
            return (Criteria) this;
        }

        public Criteria andReturn_msgIsNotNull() {
            addCriterion("return_msg is not null");
            return (Criteria) this;
        }

        public Criteria andReturn_msgEqualTo(String value) {
            addCriterion("return_msg =", value, "return_msg");
            return (Criteria) this;
        }

        public Criteria andReturn_msgNotEqualTo(String value) {
            addCriterion("return_msg <>", value, "return_msg");
            return (Criteria) this;
        }

        public Criteria andReturn_msgGreaterThan(String value) {
            addCriterion("return_msg >", value, "return_msg");
            return (Criteria) this;
        }

        public Criteria andReturn_msgGreaterThanOrEqualTo(String value) {
            addCriterion("return_msg >=", value, "return_msg");
            return (Criteria) this;
        }

        public Criteria andReturn_msgLessThan(String value) {
            addCriterion("return_msg <", value, "return_msg");
            return (Criteria) this;
        }

        public Criteria andReturn_msgLessThanOrEqualTo(String value) {
            addCriterion("return_msg <=", value, "return_msg");
            return (Criteria) this;
        }

        public Criteria andReturn_msgLike(String value) {
            addCriterion("return_msg like", value, "return_msg");
            return (Criteria) this;
        }

        public Criteria andReturn_msgNotLike(String value) {
            addCriterion("return_msg not like", value, "return_msg");
            return (Criteria) this;
        }

        public Criteria andReturn_msgIn(List<String> values) {
            addCriterion("return_msg in", values, "return_msg");
            return (Criteria) this;
        }

        public Criteria andReturn_msgNotIn(List<String> values) {
            addCriterion("return_msg not in", values, "return_msg");
            return (Criteria) this;
        }

        public Criteria andReturn_msgBetween(String value1, String value2) {
            addCriterion("return_msg between", value1, value2, "return_msg");
            return (Criteria) this;
        }

        public Criteria andReturn_msgNotBetween(String value1, String value2) {
            addCriterion("return_msg not between", value1, value2, "return_msg");
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

        public Criteria andBatch_order_noIsNull() {
            addCriterion("batch_order_no is null");
            return (Criteria) this;
        }

        public Criteria andBatch_order_noIsNotNull() {
            addCriterion("batch_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andBatch_order_noEqualTo(String value) {
            addCriterion("batch_order_no =", value, "batch_order_no");
            return (Criteria) this;
        }

        public Criteria andBatch_order_noNotEqualTo(String value) {
            addCriterion("batch_order_no <>", value, "batch_order_no");
            return (Criteria) this;
        }

        public Criteria andBatch_order_noGreaterThan(String value) {
            addCriterion("batch_order_no >", value, "batch_order_no");
            return (Criteria) this;
        }

        public Criteria andBatch_order_noGreaterThanOrEqualTo(String value) {
            addCriterion("batch_order_no >=", value, "batch_order_no");
            return (Criteria) this;
        }

        public Criteria andBatch_order_noLessThan(String value) {
            addCriterion("batch_order_no <", value, "batch_order_no");
            return (Criteria) this;
        }

        public Criteria andBatch_order_noLessThanOrEqualTo(String value) {
            addCriterion("batch_order_no <=", value, "batch_order_no");
            return (Criteria) this;
        }

        public Criteria andBatch_order_noLike(String value) {
            addCriterion("batch_order_no like", value, "batch_order_no");
            return (Criteria) this;
        }

        public Criteria andBatch_order_noNotLike(String value) {
            addCriterion("batch_order_no not like", value, "batch_order_no");
            return (Criteria) this;
        }

        public Criteria andBatch_order_noIn(List<String> values) {
            addCriterion("batch_order_no in", values, "batch_order_no");
            return (Criteria) this;
        }

        public Criteria andBatch_order_noNotIn(List<String> values) {
            addCriterion("batch_order_no not in", values, "batch_order_no");
            return (Criteria) this;
        }

        public Criteria andBatch_order_noBetween(String value1, String value2) {
            addCriterion("batch_order_no between", value1, value2, "batch_order_no");
            return (Criteria) this;
        }

        public Criteria andBatch_order_noNotBetween(String value1, String value2) {
            addCriterion("batch_order_no not between", value1, value2, "batch_order_no");
            return (Criteria) this;
        }

        public Criteria andEnabledIsNull() {
            addCriterion("enabled is null");
            return (Criteria) this;
        }

        public Criteria andEnabledIsNotNull() {
            addCriterion("enabled is not null");
            return (Criteria) this;
        }

        public Criteria andEnabledEqualTo(String value) {
            addCriterion("enabled =", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledNotEqualTo(String value) {
            addCriterion("enabled <>", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledGreaterThan(String value) {
            addCriterion("enabled >", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledGreaterThanOrEqualTo(String value) {
            addCriterion("enabled >=", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledLessThan(String value) {
            addCriterion("enabled <", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledLessThanOrEqualTo(String value) {
            addCriterion("enabled <=", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledLike(String value) {
            addCriterion("enabled like", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledNotLike(String value) {
            addCriterion("enabled not like", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledIn(List<String> values) {
            addCriterion("enabled in", values, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledNotIn(List<String> values) {
            addCriterion("enabled not in", values, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledBetween(String value1, String value2) {
            addCriterion("enabled between", value1, value2, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledNotBetween(String value1, String value2) {
            addCriterion("enabled not between", value1, value2, "enabled");
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

        public Criteria andCreate_byIsNull() {
            addCriterion("create_by is null");
            return (Criteria) this;
        }

        public Criteria andCreate_byIsNotNull() {
            addCriterion("create_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreate_byEqualTo(String value) {
            addCriterion("create_by =", value, "create_by");
            return (Criteria) this;
        }

        public Criteria andCreate_byNotEqualTo(String value) {
            addCriterion("create_by <>", value, "create_by");
            return (Criteria) this;
        }

        public Criteria andCreate_byGreaterThan(String value) {
            addCriterion("create_by >", value, "create_by");
            return (Criteria) this;
        }

        public Criteria andCreate_byGreaterThanOrEqualTo(String value) {
            addCriterion("create_by >=", value, "create_by");
            return (Criteria) this;
        }

        public Criteria andCreate_byLessThan(String value) {
            addCriterion("create_by <", value, "create_by");
            return (Criteria) this;
        }

        public Criteria andCreate_byLessThanOrEqualTo(String value) {
            addCriterion("create_by <=", value, "create_by");
            return (Criteria) this;
        }

        public Criteria andCreate_byLike(String value) {
            addCriterion("create_by like", value, "create_by");
            return (Criteria) this;
        }

        public Criteria andCreate_byNotLike(String value) {
            addCriterion("create_by not like", value, "create_by");
            return (Criteria) this;
        }

        public Criteria andCreate_byIn(List<String> values) {
            addCriterion("create_by in", values, "create_by");
            return (Criteria) this;
        }

        public Criteria andCreate_byNotIn(List<String> values) {
            addCriterion("create_by not in", values, "create_by");
            return (Criteria) this;
        }

        public Criteria andCreate_byBetween(String value1, String value2) {
            addCriterion("create_by between", value1, value2, "create_by");
            return (Criteria) this;
        }

        public Criteria andCreate_byNotBetween(String value1, String value2) {
            addCriterion("create_by not between", value1, value2, "create_by");
            return (Criteria) this;
        }

        public Criteria andCreate_timeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreate_timeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreate_timeEqualTo(Date value) {
            addCriterion("create_time =", value, "create_time");
            return (Criteria) this;
        }

        public Criteria andCreate_timeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "create_time");
            return (Criteria) this;
        }

        public Criteria andCreate_timeGreaterThan(Date value) {
            addCriterion("create_time >", value, "create_time");
            return (Criteria) this;
        }

        public Criteria andCreate_timeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "create_time");
            return (Criteria) this;
        }

        public Criteria andCreate_timeLessThan(Date value) {
            addCriterion("create_time <", value, "create_time");
            return (Criteria) this;
        }

        public Criteria andCreate_timeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "create_time");
            return (Criteria) this;
        }

        public Criteria andCreate_timeIn(List<Date> values) {
            addCriterion("create_time in", values, "create_time");
            return (Criteria) this;
        }

        public Criteria andCreate_timeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "create_time");
            return (Criteria) this;
        }

        public Criteria andCreate_timeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "create_time");
            return (Criteria) this;
        }

        public Criteria andCreate_timeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "create_time");
            return (Criteria) this;
        }

        public Criteria andUpdate_byIsNull() {
            addCriterion("update_by is null");
            return (Criteria) this;
        }

        public Criteria andUpdate_byIsNotNull() {
            addCriterion("update_by is not null");
            return (Criteria) this;
        }

        public Criteria andUpdate_byEqualTo(String value) {
            addCriterion("update_by =", value, "update_by");
            return (Criteria) this;
        }

        public Criteria andUpdate_byNotEqualTo(String value) {
            addCriterion("update_by <>", value, "update_by");
            return (Criteria) this;
        }

        public Criteria andUpdate_byGreaterThan(String value) {
            addCriterion("update_by >", value, "update_by");
            return (Criteria) this;
        }

        public Criteria andUpdate_byGreaterThanOrEqualTo(String value) {
            addCriterion("update_by >=", value, "update_by");
            return (Criteria) this;
        }

        public Criteria andUpdate_byLessThan(String value) {
            addCriterion("update_by <", value, "update_by");
            return (Criteria) this;
        }

        public Criteria andUpdate_byLessThanOrEqualTo(String value) {
            addCriterion("update_by <=", value, "update_by");
            return (Criteria) this;
        }

        public Criteria andUpdate_byLike(String value) {
            addCriterion("update_by like", value, "update_by");
            return (Criteria) this;
        }

        public Criteria andUpdate_byNotLike(String value) {
            addCriterion("update_by not like", value, "update_by");
            return (Criteria) this;
        }

        public Criteria andUpdate_byIn(List<String> values) {
            addCriterion("update_by in", values, "update_by");
            return (Criteria) this;
        }

        public Criteria andUpdate_byNotIn(List<String> values) {
            addCriterion("update_by not in", values, "update_by");
            return (Criteria) this;
        }

        public Criteria andUpdate_byBetween(String value1, String value2) {
            addCriterion("update_by between", value1, value2, "update_by");
            return (Criteria) this;
        }

        public Criteria andUpdate_byNotBetween(String value1, String value2) {
            addCriterion("update_by not between", value1, value2, "update_by");
            return (Criteria) this;
        }

        public Criteria andUpdate_timeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdate_timeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdate_timeEqualTo(Date value) {
            addCriterion("update_time =", value, "update_time");
            return (Criteria) this;
        }

        public Criteria andUpdate_timeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "update_time");
            return (Criteria) this;
        }

        public Criteria andUpdate_timeGreaterThan(Date value) {
            addCriterion("update_time >", value, "update_time");
            return (Criteria) this;
        }

        public Criteria andUpdate_timeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "update_time");
            return (Criteria) this;
        }

        public Criteria andUpdate_timeLessThan(Date value) {
            addCriterion("update_time <", value, "update_time");
            return (Criteria) this;
        }

        public Criteria andUpdate_timeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "update_time");
            return (Criteria) this;
        }

        public Criteria andUpdate_timeIn(List<Date> values) {
            addCriterion("update_time in", values, "update_time");
            return (Criteria) this;
        }

        public Criteria andUpdate_timeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "update_time");
            return (Criteria) this;
        }

        public Criteria andUpdate_timeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "update_time");
            return (Criteria) this;
        }

        public Criteria andUpdate_timeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "update_time");
            return (Criteria) this;
        }

        public Criteria andNotify_urlIsNull() {
            addCriterion("notify_url is null");
            return (Criteria) this;
        }

        public Criteria andNotify_urlIsNotNull() {
            addCriterion("notify_url is not null");
            return (Criteria) this;
        }

        public Criteria andNotify_urlEqualTo(String value) {
            addCriterion("notify_url =", value, "notify_url");
            return (Criteria) this;
        }

        public Criteria andNotify_urlNotEqualTo(String value) {
            addCriterion("notify_url <>", value, "notify_url");
            return (Criteria) this;
        }

        public Criteria andNotify_urlGreaterThan(String value) {
            addCriterion("notify_url >", value, "notify_url");
            return (Criteria) this;
        }

        public Criteria andNotify_urlGreaterThanOrEqualTo(String value) {
            addCriterion("notify_url >=", value, "notify_url");
            return (Criteria) this;
        }

        public Criteria andNotify_urlLessThan(String value) {
            addCriterion("notify_url <", value, "notify_url");
            return (Criteria) this;
        }

        public Criteria andNotify_urlLessThanOrEqualTo(String value) {
            addCriterion("notify_url <=", value, "notify_url");
            return (Criteria) this;
        }

        public Criteria andNotify_urlLike(String value) {
            addCriterion("notify_url like", value, "notify_url");
            return (Criteria) this;
        }

        public Criteria andNotify_urlNotLike(String value) {
            addCriterion("notify_url not like", value, "notify_url");
            return (Criteria) this;
        }

        public Criteria andNotify_urlIn(List<String> values) {
            addCriterion("notify_url in", values, "notify_url");
            return (Criteria) this;
        }

        public Criteria andNotify_urlNotIn(List<String> values) {
            addCriterion("notify_url not in", values, "notify_url");
            return (Criteria) this;
        }

        public Criteria andNotify_urlBetween(String value1, String value2) {
            addCriterion("notify_url between", value1, value2, "notify_url");
            return (Criteria) this;
        }

        public Criteria andNotify_urlNotBetween(String value1, String value2) {
            addCriterion("notify_url not between", value1, value2, "notify_url");
            return (Criteria) this;
        }

        public Criteria andExt1IsNull() {
            addCriterion("ext1 is null");
            return (Criteria) this;
        }

        public Criteria andExt1IsNotNull() {
            addCriterion("ext1 is not null");
            return (Criteria) this;
        }

        public Criteria andExt1EqualTo(String value) {
            addCriterion("ext1 =", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1NotEqualTo(String value) {
            addCriterion("ext1 <>", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1GreaterThan(String value) {
            addCriterion("ext1 >", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1GreaterThanOrEqualTo(String value) {
            addCriterion("ext1 >=", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1LessThan(String value) {
            addCriterion("ext1 <", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1LessThanOrEqualTo(String value) {
            addCriterion("ext1 <=", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1Like(String value) {
            addCriterion("ext1 like", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1NotLike(String value) {
            addCriterion("ext1 not like", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1In(List<String> values) {
            addCriterion("ext1 in", values, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1NotIn(List<String> values) {
            addCriterion("ext1 not in", values, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1Between(String value1, String value2) {
            addCriterion("ext1 between", value1, value2, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1NotBetween(String value1, String value2) {
            addCriterion("ext1 not between", value1, value2, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt2IsNull() {
            addCriterion("ext2 is null");
            return (Criteria) this;
        }

        public Criteria andExt2IsNotNull() {
            addCriterion("ext2 is not null");
            return (Criteria) this;
        }

        public Criteria andExt2EqualTo(String value) {
            addCriterion("ext2 =", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2NotEqualTo(String value) {
            addCriterion("ext2 <>", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2GreaterThan(String value) {
            addCriterion("ext2 >", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2GreaterThanOrEqualTo(String value) {
            addCriterion("ext2 >=", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2LessThan(String value) {
            addCriterion("ext2 <", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2LessThanOrEqualTo(String value) {
            addCriterion("ext2 <=", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2Like(String value) {
            addCriterion("ext2 like", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2NotLike(String value) {
            addCriterion("ext2 not like", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2In(List<String> values) {
            addCriterion("ext2 in", values, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2NotIn(List<String> values) {
            addCriterion("ext2 not in", values, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2Between(String value1, String value2) {
            addCriterion("ext2 between", value1, value2, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2NotBetween(String value1, String value2) {
            addCriterion("ext2 not between", value1, value2, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt3IsNull() {
            addCriterion("ext3 is null");
            return (Criteria) this;
        }

        public Criteria andExt3IsNotNull() {
            addCriterion("ext3 is not null");
            return (Criteria) this;
        }

        public Criteria andExt3EqualTo(String value) {
            addCriterion("ext3 =", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3NotEqualTo(String value) {
            addCriterion("ext3 <>", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3GreaterThan(String value) {
            addCriterion("ext3 >", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3GreaterThanOrEqualTo(String value) {
            addCriterion("ext3 >=", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3LessThan(String value) {
            addCriterion("ext3 <", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3LessThanOrEqualTo(String value) {
            addCriterion("ext3 <=", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3Like(String value) {
            addCriterion("ext3 like", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3NotLike(String value) {
            addCriterion("ext3 not like", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3In(List<String> values) {
            addCriterion("ext3 in", values, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3NotIn(List<String> values) {
            addCriterion("ext3 not in", values, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3Between(String value1, String value2) {
            addCriterion("ext3 between", value1, value2, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3NotBetween(String value1, String value2) {
            addCriterion("ext3 not between", value1, value2, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt4IsNull() {
            addCriterion("ext4 is null");
            return (Criteria) this;
        }

        public Criteria andExt4IsNotNull() {
            addCriterion("ext4 is not null");
            return (Criteria) this;
        }

        public Criteria andExt4EqualTo(String value) {
            addCriterion("ext4 =", value, "ext4");
            return (Criteria) this;
        }

        public Criteria andExt4NotEqualTo(String value) {
            addCriterion("ext4 <>", value, "ext4");
            return (Criteria) this;
        }

        public Criteria andExt4GreaterThan(String value) {
            addCriterion("ext4 >", value, "ext4");
            return (Criteria) this;
        }

        public Criteria andExt4GreaterThanOrEqualTo(String value) {
            addCriterion("ext4 >=", value, "ext4");
            return (Criteria) this;
        }

        public Criteria andExt4LessThan(String value) {
            addCriterion("ext4 <", value, "ext4");
            return (Criteria) this;
        }

        public Criteria andExt4LessThanOrEqualTo(String value) {
            addCriterion("ext4 <=", value, "ext4");
            return (Criteria) this;
        }

        public Criteria andExt4Like(String value) {
            addCriterion("ext4 like", value, "ext4");
            return (Criteria) this;
        }

        public Criteria andExt4NotLike(String value) {
            addCriterion("ext4 not like", value, "ext4");
            return (Criteria) this;
        }

        public Criteria andExt4In(List<String> values) {
            addCriterion("ext4 in", values, "ext4");
            return (Criteria) this;
        }

        public Criteria andExt4NotIn(List<String> values) {
            addCriterion("ext4 not in", values, "ext4");
            return (Criteria) this;
        }

        public Criteria andExt4Between(String value1, String value2) {
            addCriterion("ext4 between", value1, value2, "ext4");
            return (Criteria) this;
        }

        public Criteria andExt4NotBetween(String value1, String value2) {
            addCriterion("ext4 not between", value1, value2, "ext4");
            return (Criteria) this;
        }

        public Criteria andExt5IsNull() {
            addCriterion("ext5 is null");
            return (Criteria) this;
        }

        public Criteria andExt5IsNotNull() {
            addCriterion("ext5 is not null");
            return (Criteria) this;
        }

        public Criteria andExt5EqualTo(String value) {
            addCriterion("ext5 =", value, "ext5");
            return (Criteria) this;
        }

        public Criteria andExt5NotEqualTo(String value) {
            addCriterion("ext5 <>", value, "ext5");
            return (Criteria) this;
        }

        public Criteria andExt5GreaterThan(String value) {
            addCriterion("ext5 >", value, "ext5");
            return (Criteria) this;
        }

        public Criteria andExt5GreaterThanOrEqualTo(String value) {
            addCriterion("ext5 >=", value, "ext5");
            return (Criteria) this;
        }

        public Criteria andExt5LessThan(String value) {
            addCriterion("ext5 <", value, "ext5");
            return (Criteria) this;
        }

        public Criteria andExt5LessThanOrEqualTo(String value) {
            addCriterion("ext5 <=", value, "ext5");
            return (Criteria) this;
        }

        public Criteria andExt5Like(String value) {
            addCriterion("ext5 like", value, "ext5");
            return (Criteria) this;
        }

        public Criteria andExt5NotLike(String value) {
            addCriterion("ext5 not like", value, "ext5");
            return (Criteria) this;
        }

        public Criteria andExt5In(List<String> values) {
            addCriterion("ext5 in", values, "ext5");
            return (Criteria) this;
        }

        public Criteria andExt5NotIn(List<String> values) {
            addCriterion("ext5 not in", values, "ext5");
            return (Criteria) this;
        }

        public Criteria andExt5Between(String value1, String value2) {
            addCriterion("ext5 between", value1, value2, "ext5");
            return (Criteria) this;
        }

        public Criteria andExt5NotBetween(String value1, String value2) {
            addCriterion("ext5 not between", value1, value2, "ext5");
            return (Criteria) this;
        }

        public Criteria andOrigin_order_noIsNull() {
            addCriterion("origin_order_no is null");
            return (Criteria) this;
        }

        public Criteria andOrigin_order_noIsNotNull() {
            addCriterion("origin_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andOrigin_order_noEqualTo(String value) {
            addCriterion("origin_order_no =", value, "origin_order_no");
            return (Criteria) this;
        }

        public Criteria andOrigin_order_noNotEqualTo(String value) {
            addCriterion("origin_order_no <>", value, "origin_order_no");
            return (Criteria) this;
        }

        public Criteria andOrigin_order_noGreaterThan(String value) {
            addCriterion("origin_order_no >", value, "origin_order_no");
            return (Criteria) this;
        }

        public Criteria andOrigin_order_noGreaterThanOrEqualTo(String value) {
            addCriterion("origin_order_no >=", value, "origin_order_no");
            return (Criteria) this;
        }

        public Criteria andOrigin_order_noLessThan(String value) {
            addCriterion("origin_order_no <", value, "origin_order_no");
            return (Criteria) this;
        }

        public Criteria andOrigin_order_noLessThanOrEqualTo(String value) {
            addCriterion("origin_order_no <=", value, "origin_order_no");
            return (Criteria) this;
        }

        public Criteria andOrigin_order_noLike(String value) {
            addCriterion("origin_order_no like", value, "origin_order_no");
            return (Criteria) this;
        }

        public Criteria andOrigin_order_noNotLike(String value) {
            addCriterion("origin_order_no not like", value, "origin_order_no");
            return (Criteria) this;
        }

        public Criteria andOrigin_order_noIn(List<String> values) {
            addCriterion("origin_order_no in", values, "origin_order_no");
            return (Criteria) this;
        }

        public Criteria andOrigin_order_noNotIn(List<String> values) {
            addCriterion("origin_order_no not in", values, "origin_order_no");
            return (Criteria) this;
        }

        public Criteria andOrigin_order_noBetween(String value1, String value2) {
            addCriterion("origin_order_no between", value1, value2, "origin_order_no");
            return (Criteria) this;
        }

        public Criteria andOrigin_order_noNotBetween(String value1, String value2) {
            addCriterion("origin_order_no not between", value1, value2, "origin_order_no");
            return (Criteria) this;
        }

        public Criteria andService_nameIsNull() {
            addCriterion("service_name is null");
            return (Criteria) this;
        }

        public Criteria andService_nameIsNotNull() {
            addCriterion("service_name is not null");
            return (Criteria) this;
        }

        public Criteria andService_nameEqualTo(String value) {
            addCriterion("service_name =", value, "service_name");
            return (Criteria) this;
        }

        public Criteria andService_nameNotEqualTo(String value) {
            addCriterion("service_name <>", value, "service_name");
            return (Criteria) this;
        }

        public Criteria andService_nameGreaterThan(String value) {
            addCriterion("service_name >", value, "service_name");
            return (Criteria) this;
        }

        public Criteria andService_nameGreaterThanOrEqualTo(String value) {
            addCriterion("service_name >=", value, "service_name");
            return (Criteria) this;
        }

        public Criteria andService_nameLessThan(String value) {
            addCriterion("service_name <", value, "service_name");
            return (Criteria) this;
        }

        public Criteria andService_nameLessThanOrEqualTo(String value) {
            addCriterion("service_name <=", value, "service_name");
            return (Criteria) this;
        }

        public Criteria andService_nameLike(String value) {
            addCriterion("service_name like", value, "service_name");
            return (Criteria) this;
        }

        public Criteria andService_nameNotLike(String value) {
            addCriterion("service_name not like", value, "service_name");
            return (Criteria) this;
        }

        public Criteria andService_nameIn(List<String> values) {
            addCriterion("service_name in", values, "service_name");
            return (Criteria) this;
        }

        public Criteria andService_nameNotIn(List<String> values) {
            addCriterion("service_name not in", values, "service_name");
            return (Criteria) this;
        }

        public Criteria andService_nameBetween(String value1, String value2) {
            addCriterion("service_name between", value1, value2, "service_name");
            return (Criteria) this;
        }

        public Criteria andService_nameNotBetween(String value1, String value2) {
            addCriterion("service_name not between", value1, value2, "service_name");
            return (Criteria) this;
        }
    }

    /**
     * trans_transreq
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * trans_transreq 2018-01-23
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