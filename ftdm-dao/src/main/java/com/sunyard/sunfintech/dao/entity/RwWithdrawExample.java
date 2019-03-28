package com.sunyard.sunfintech.dao.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RwWithdrawExample {
    /**
     * rw_withdraw
     */
    protected String orderByClause;

    /**
     * rw_withdraw
     */
    protected boolean distinct;

    /**
     * rw_withdraw
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2018-01-29
     */
    public RwWithdrawExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *
     * @mbggenerated 2018-01-29
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *
     * @mbggenerated 2018-01-29
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *
     * @mbggenerated 2018-01-29
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *
     * @mbggenerated 2018-01-29
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *
     * @mbggenerated 2018-01-29
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *
     * @mbggenerated 2018-01-29
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *
     * @mbggenerated 2018-01-29
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *
     * @mbggenerated 2018-01-29
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
     * @mbggenerated 2018-01-29
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *
     * @mbggenerated 2018-01-29
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * rw_withdraw 2018-01-29
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

        public Criteria andProd_idIsNull() {
            addCriterion("prod_id is null");
            return (Criteria) this;
        }

        public Criteria andProd_idIsNotNull() {
            addCriterion("prod_id is not null");
            return (Criteria) this;
        }

        public Criteria andProd_idEqualTo(String value) {
            addCriterion("prod_id =", value, "prod_id");
            return (Criteria) this;
        }

        public Criteria andProd_idNotEqualTo(String value) {
            addCriterion("prod_id <>", value, "prod_id");
            return (Criteria) this;
        }

        public Criteria andProd_idGreaterThan(String value) {
            addCriterion("prod_id >", value, "prod_id");
            return (Criteria) this;
        }

        public Criteria andProd_idGreaterThanOrEqualTo(String value) {
            addCriterion("prod_id >=", value, "prod_id");
            return (Criteria) this;
        }

        public Criteria andProd_idLessThan(String value) {
            addCriterion("prod_id <", value, "prod_id");
            return (Criteria) this;
        }

        public Criteria andProd_idLessThanOrEqualTo(String value) {
            addCriterion("prod_id <=", value, "prod_id");
            return (Criteria) this;
        }

        public Criteria andProd_idLike(String value) {
            addCriterion("prod_id like", value, "prod_id");
            return (Criteria) this;
        }

        public Criteria andProd_idNotLike(String value) {
            addCriterion("prod_id not like", value, "prod_id");
            return (Criteria) this;
        }

        public Criteria andProd_idIn(List<String> values) {
            addCriterion("prod_id in", values, "prod_id");
            return (Criteria) this;
        }

        public Criteria andProd_idNotIn(List<String> values) {
            addCriterion("prod_id not in", values, "prod_id");
            return (Criteria) this;
        }

        public Criteria andProd_idBetween(String value1, String value2) {
            addCriterion("prod_id between", value1, value2, "prod_id");
            return (Criteria) this;
        }

        public Criteria andProd_idNotBetween(String value1, String value2) {
            addCriterion("prod_id not between", value1, value2, "prod_id");
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

        public Criteria andOut_amtIsNull() {
            addCriterion("out_amt is null");
            return (Criteria) this;
        }

        public Criteria andOut_amtIsNotNull() {
            addCriterion("out_amt is not null");
            return (Criteria) this;
        }

        public Criteria andOut_amtEqualTo(BigDecimal value) {
            addCriterion("out_amt =", value, "out_amt");
            return (Criteria) this;
        }

        public Criteria andOut_amtNotEqualTo(BigDecimal value) {
            addCriterion("out_amt <>", value, "out_amt");
            return (Criteria) this;
        }

        public Criteria andOut_amtGreaterThan(BigDecimal value) {
            addCriterion("out_amt >", value, "out_amt");
            return (Criteria) this;
        }

        public Criteria andOut_amtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("out_amt >=", value, "out_amt");
            return (Criteria) this;
        }

        public Criteria andOut_amtLessThan(BigDecimal value) {
            addCriterion("out_amt <", value, "out_amt");
            return (Criteria) this;
        }

        public Criteria andOut_amtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("out_amt <=", value, "out_amt");
            return (Criteria) this;
        }

        public Criteria andOut_amtIn(List<BigDecimal> values) {
            addCriterion("out_amt in", values, "out_amt");
            return (Criteria) this;
        }

        public Criteria andOut_amtNotIn(List<BigDecimal> values) {
            addCriterion("out_amt not in", values, "out_amt");
            return (Criteria) this;
        }

        public Criteria andOut_amtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("out_amt between", value1, value2, "out_amt");
            return (Criteria) this;
        }

        public Criteria andOut_amtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("out_amt not between", value1, value2, "out_amt");
            return (Criteria) this;
        }

        public Criteria andSubjectIsNull() {
            addCriterion("subject is null");
            return (Criteria) this;
        }

        public Criteria andSubjectIsNotNull() {
            addCriterion("subject is not null");
            return (Criteria) this;
        }

        public Criteria andSubjectEqualTo(String value) {
            addCriterion("subject =", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotEqualTo(String value) {
            addCriterion("subject <>", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectGreaterThan(String value) {
            addCriterion("subject >", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectGreaterThanOrEqualTo(String value) {
            addCriterion("subject >=", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectLessThan(String value) {
            addCriterion("subject <", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectLessThanOrEqualTo(String value) {
            addCriterion("subject <=", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectLike(String value) {
            addCriterion("subject like", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotLike(String value) {
            addCriterion("subject not like", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectIn(List<String> values) {
            addCriterion("subject in", values, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotIn(List<String> values) {
            addCriterion("subject not in", values, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectBetween(String value1, String value2) {
            addCriterion("subject between", value1, value2, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotBetween(String value1, String value2) {
            addCriterion("subject not between", value1, value2, "subject");
            return (Criteria) this;
        }

        public Criteria andSub_subjectIsNull() {
            addCriterion("sub_subject is null");
            return (Criteria) this;
        }

        public Criteria andSub_subjectIsNotNull() {
            addCriterion("sub_subject is not null");
            return (Criteria) this;
        }

        public Criteria andSub_subjectEqualTo(String value) {
            addCriterion("sub_subject =", value, "sub_subject");
            return (Criteria) this;
        }

        public Criteria andSub_subjectNotEqualTo(String value) {
            addCriterion("sub_subject <>", value, "sub_subject");
            return (Criteria) this;
        }

        public Criteria andSub_subjectGreaterThan(String value) {
            addCriterion("sub_subject >", value, "sub_subject");
            return (Criteria) this;
        }

        public Criteria andSub_subjectGreaterThanOrEqualTo(String value) {
            addCriterion("sub_subject >=", value, "sub_subject");
            return (Criteria) this;
        }

        public Criteria andSub_subjectLessThan(String value) {
            addCriterion("sub_subject <", value, "sub_subject");
            return (Criteria) this;
        }

        public Criteria andSub_subjectLessThanOrEqualTo(String value) {
            addCriterion("sub_subject <=", value, "sub_subject");
            return (Criteria) this;
        }

        public Criteria andSub_subjectLike(String value) {
            addCriterion("sub_subject like", value, "sub_subject");
            return (Criteria) this;
        }

        public Criteria andSub_subjectNotLike(String value) {
            addCriterion("sub_subject not like", value, "sub_subject");
            return (Criteria) this;
        }

        public Criteria andSub_subjectIn(List<String> values) {
            addCriterion("sub_subject in", values, "sub_subject");
            return (Criteria) this;
        }

        public Criteria andSub_subjectNotIn(List<String> values) {
            addCriterion("sub_subject not in", values, "sub_subject");
            return (Criteria) this;
        }

        public Criteria andSub_subjectBetween(String value1, String value2) {
            addCriterion("sub_subject between", value1, value2, "sub_subject");
            return (Criteria) this;
        }

        public Criteria andSub_subjectNotBetween(String value1, String value2) {
            addCriterion("sub_subject not between", value1, value2, "sub_subject");
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

        public Criteria andFee_amtIsNull() {
            addCriterion("fee_amt is null");
            return (Criteria) this;
        }

        public Criteria andFee_amtIsNotNull() {
            addCriterion("fee_amt is not null");
            return (Criteria) this;
        }

        public Criteria andFee_amtEqualTo(BigDecimal value) {
            addCriterion("fee_amt =", value, "fee_amt");
            return (Criteria) this;
        }

        public Criteria andFee_amtNotEqualTo(BigDecimal value) {
            addCriterion("fee_amt <>", value, "fee_amt");
            return (Criteria) this;
        }

        public Criteria andFee_amtGreaterThan(BigDecimal value) {
            addCriterion("fee_amt >", value, "fee_amt");
            return (Criteria) this;
        }

        public Criteria andFee_amtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("fee_amt >=", value, "fee_amt");
            return (Criteria) this;
        }

        public Criteria andFee_amtLessThan(BigDecimal value) {
            addCriterion("fee_amt <", value, "fee_amt");
            return (Criteria) this;
        }

        public Criteria andFee_amtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("fee_amt <=", value, "fee_amt");
            return (Criteria) this;
        }

        public Criteria andFee_amtIn(List<BigDecimal> values) {
            addCriterion("fee_amt in", values, "fee_amt");
            return (Criteria) this;
        }

        public Criteria andFee_amtNotIn(List<BigDecimal> values) {
            addCriterion("fee_amt not in", values, "fee_amt");
            return (Criteria) this;
        }

        public Criteria andFee_amtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("fee_amt between", value1, value2, "fee_amt");
            return (Criteria) this;
        }

        public Criteria andFee_amtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("fee_amt not between", value1, value2, "fee_amt");
            return (Criteria) this;
        }

        public Criteria andAdvance_amtIsNull() {
            addCriterion("advance_amt is null");
            return (Criteria) this;
        }

        public Criteria andAdvance_amtIsNotNull() {
            addCriterion("advance_amt is not null");
            return (Criteria) this;
        }

        public Criteria andAdvance_amtEqualTo(BigDecimal value) {
            addCriterion("advance_amt =", value, "advance_amt");
            return (Criteria) this;
        }

        public Criteria andAdvance_amtNotEqualTo(BigDecimal value) {
            addCriterion("advance_amt <>", value, "advance_amt");
            return (Criteria) this;
        }

        public Criteria andAdvance_amtGreaterThan(BigDecimal value) {
            addCriterion("advance_amt >", value, "advance_amt");
            return (Criteria) this;
        }

        public Criteria andAdvance_amtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("advance_amt >=", value, "advance_amt");
            return (Criteria) this;
        }

        public Criteria andAdvance_amtLessThan(BigDecimal value) {
            addCriterion("advance_amt <", value, "advance_amt");
            return (Criteria) this;
        }

        public Criteria andAdvance_amtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("advance_amt <=", value, "advance_amt");
            return (Criteria) this;
        }

        public Criteria andAdvance_amtIn(List<BigDecimal> values) {
            addCriterion("advance_amt in", values, "advance_amt");
            return (Criteria) this;
        }

        public Criteria andAdvance_amtNotIn(List<BigDecimal> values) {
            addCriterion("advance_amt not in", values, "advance_amt");
            return (Criteria) this;
        }

        public Criteria andAdvance_amtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("advance_amt between", value1, value2, "advance_amt");
            return (Criteria) this;
        }

        public Criteria andAdvance_amtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("advance_amt not between", value1, value2, "advance_amt");
            return (Criteria) this;
        }

        public Criteria andOppo_accountIsNull() {
            addCriterion("oppo_account is null");
            return (Criteria) this;
        }

        public Criteria andOppo_accountIsNotNull() {
            addCriterion("oppo_account is not null");
            return (Criteria) this;
        }

        public Criteria andOppo_accountEqualTo(String value) {
            addCriterion("oppo_account =", value, "oppo_account");
            return (Criteria) this;
        }

        public Criteria andOppo_accountNotEqualTo(String value) {
            addCriterion("oppo_account <>", value, "oppo_account");
            return (Criteria) this;
        }

        public Criteria andOppo_accountGreaterThan(String value) {
            addCriterion("oppo_account >", value, "oppo_account");
            return (Criteria) this;
        }

        public Criteria andOppo_accountGreaterThanOrEqualTo(String value) {
            addCriterion("oppo_account >=", value, "oppo_account");
            return (Criteria) this;
        }

        public Criteria andOppo_accountLessThan(String value) {
            addCriterion("oppo_account <", value, "oppo_account");
            return (Criteria) this;
        }

        public Criteria andOppo_accountLessThanOrEqualTo(String value) {
            addCriterion("oppo_account <=", value, "oppo_account");
            return (Criteria) this;
        }

        public Criteria andOppo_accountLike(String value) {
            addCriterion("oppo_account like", value, "oppo_account");
            return (Criteria) this;
        }

        public Criteria andOppo_accountNotLike(String value) {
            addCriterion("oppo_account not like", value, "oppo_account");
            return (Criteria) this;
        }

        public Criteria andOppo_accountIn(List<String> values) {
            addCriterion("oppo_account in", values, "oppo_account");
            return (Criteria) this;
        }

        public Criteria andOppo_accountNotIn(List<String> values) {
            addCriterion("oppo_account not in", values, "oppo_account");
            return (Criteria) this;
        }

        public Criteria andOppo_accountBetween(String value1, String value2) {
            addCriterion("oppo_account between", value1, value2, "oppo_account");
            return (Criteria) this;
        }

        public Criteria andOppo_accountNotBetween(String value1, String value2) {
            addCriterion("oppo_account not between", value1, value2, "oppo_account");
            return (Criteria) this;
        }

        public Criteria andOpen_branchIsNull() {
            addCriterion("open_branch is null");
            return (Criteria) this;
        }

        public Criteria andOpen_branchIsNotNull() {
            addCriterion("open_branch is not null");
            return (Criteria) this;
        }

        public Criteria andOpen_branchEqualTo(String value) {
            addCriterion("open_branch =", value, "open_branch");
            return (Criteria) this;
        }

        public Criteria andOpen_branchNotEqualTo(String value) {
            addCriterion("open_branch <>", value, "open_branch");
            return (Criteria) this;
        }

        public Criteria andOpen_branchGreaterThan(String value) {
            addCriterion("open_branch >", value, "open_branch");
            return (Criteria) this;
        }

        public Criteria andOpen_branchGreaterThanOrEqualTo(String value) {
            addCriterion("open_branch >=", value, "open_branch");
            return (Criteria) this;
        }

        public Criteria andOpen_branchLessThan(String value) {
            addCriterion("open_branch <", value, "open_branch");
            return (Criteria) this;
        }

        public Criteria andOpen_branchLessThanOrEqualTo(String value) {
            addCriterion("open_branch <=", value, "open_branch");
            return (Criteria) this;
        }

        public Criteria andOpen_branchLike(String value) {
            addCriterion("open_branch like", value, "open_branch");
            return (Criteria) this;
        }

        public Criteria andOpen_branchNotLike(String value) {
            addCriterion("open_branch not like", value, "open_branch");
            return (Criteria) this;
        }

        public Criteria andOpen_branchIn(List<String> values) {
            addCriterion("open_branch in", values, "open_branch");
            return (Criteria) this;
        }

        public Criteria andOpen_branchNotIn(List<String> values) {
            addCriterion("open_branch not in", values, "open_branch");
            return (Criteria) this;
        }

        public Criteria andOpen_branchBetween(String value1, String value2) {
            addCriterion("open_branch between", value1, value2, "open_branch");
            return (Criteria) this;
        }

        public Criteria andOpen_branchNotBetween(String value1, String value2) {
            addCriterion("open_branch not between", value1, value2, "open_branch");
            return (Criteria) this;
        }

        public Criteria andPayee_nameIsNull() {
            addCriterion("payee_name is null");
            return (Criteria) this;
        }

        public Criteria andPayee_nameIsNotNull() {
            addCriterion("payee_name is not null");
            return (Criteria) this;
        }

        public Criteria andPayee_nameEqualTo(String value) {
            addCriterion("payee_name =", value, "payee_name");
            return (Criteria) this;
        }

        public Criteria andPayee_nameNotEqualTo(String value) {
            addCriterion("payee_name <>", value, "payee_name");
            return (Criteria) this;
        }

        public Criteria andPayee_nameGreaterThan(String value) {
            addCriterion("payee_name >", value, "payee_name");
            return (Criteria) this;
        }

        public Criteria andPayee_nameGreaterThanOrEqualTo(String value) {
            addCriterion("payee_name >=", value, "payee_name");
            return (Criteria) this;
        }

        public Criteria andPayee_nameLessThan(String value) {
            addCriterion("payee_name <", value, "payee_name");
            return (Criteria) this;
        }

        public Criteria andPayee_nameLessThanOrEqualTo(String value) {
            addCriterion("payee_name <=", value, "payee_name");
            return (Criteria) this;
        }

        public Criteria andPayee_nameLike(String value) {
            addCriterion("payee_name like", value, "payee_name");
            return (Criteria) this;
        }

        public Criteria andPayee_nameNotLike(String value) {
            addCriterion("payee_name not like", value, "payee_name");
            return (Criteria) this;
        }

        public Criteria andPayee_nameIn(List<String> values) {
            addCriterion("payee_name in", values, "payee_name");
            return (Criteria) this;
        }

        public Criteria andPayee_nameNotIn(List<String> values) {
            addCriterion("payee_name not in", values, "payee_name");
            return (Criteria) this;
        }

        public Criteria andPayee_nameBetween(String value1, String value2) {
            addCriterion("payee_name between", value1, value2, "payee_name");
            return (Criteria) this;
        }

        public Criteria andPayee_nameNotBetween(String value1, String value2) {
            addCriterion("payee_name not between", value1, value2, "payee_name");
            return (Criteria) this;
        }

        public Criteria andClient_propertyIsNull() {
            addCriterion("client_property is null");
            return (Criteria) this;
        }

        public Criteria andClient_propertyIsNotNull() {
            addCriterion("client_property is not null");
            return (Criteria) this;
        }

        public Criteria andClient_propertyEqualTo(String value) {
            addCriterion("client_property =", value, "client_property");
            return (Criteria) this;
        }

        public Criteria andClient_propertyNotEqualTo(String value) {
            addCriterion("client_property <>", value, "client_property");
            return (Criteria) this;
        }

        public Criteria andClient_propertyGreaterThan(String value) {
            addCriterion("client_property >", value, "client_property");
            return (Criteria) this;
        }

        public Criteria andClient_propertyGreaterThanOrEqualTo(String value) {
            addCriterion("client_property >=", value, "client_property");
            return (Criteria) this;
        }

        public Criteria andClient_propertyLessThan(String value) {
            addCriterion("client_property <", value, "client_property");
            return (Criteria) this;
        }

        public Criteria andClient_propertyLessThanOrEqualTo(String value) {
            addCriterion("client_property <=", value, "client_property");
            return (Criteria) this;
        }

        public Criteria andClient_propertyLike(String value) {
            addCriterion("client_property like", value, "client_property");
            return (Criteria) this;
        }

        public Criteria andClient_propertyNotLike(String value) {
            addCriterion("client_property not like", value, "client_property");
            return (Criteria) this;
        }

        public Criteria andClient_propertyIn(List<String> values) {
            addCriterion("client_property in", values, "client_property");
            return (Criteria) this;
        }

        public Criteria andClient_propertyNotIn(List<String> values) {
            addCriterion("client_property not in", values, "client_property");
            return (Criteria) this;
        }

        public Criteria andClient_propertyBetween(String value1, String value2) {
            addCriterion("client_property between", value1, value2, "client_property");
            return (Criteria) this;
        }

        public Criteria andClient_propertyNotBetween(String value1, String value2) {
            addCriterion("client_property not between", value1, value2, "client_property");
            return (Criteria) this;
        }

        public Criteria andCity_codeIsNull() {
            addCriterion("city_code is null");
            return (Criteria) this;
        }

        public Criteria andCity_codeIsNotNull() {
            addCriterion("city_code is not null");
            return (Criteria) this;
        }

        public Criteria andCity_codeEqualTo(String value) {
            addCriterion("city_code =", value, "city_code");
            return (Criteria) this;
        }

        public Criteria andCity_codeNotEqualTo(String value) {
            addCriterion("city_code <>", value, "city_code");
            return (Criteria) this;
        }

        public Criteria andCity_codeGreaterThan(String value) {
            addCriterion("city_code >", value, "city_code");
            return (Criteria) this;
        }

        public Criteria andCity_codeGreaterThanOrEqualTo(String value) {
            addCriterion("city_code >=", value, "city_code");
            return (Criteria) this;
        }

        public Criteria andCity_codeLessThan(String value) {
            addCriterion("city_code <", value, "city_code");
            return (Criteria) this;
        }

        public Criteria andCity_codeLessThanOrEqualTo(String value) {
            addCriterion("city_code <=", value, "city_code");
            return (Criteria) this;
        }

        public Criteria andCity_codeLike(String value) {
            addCriterion("city_code like", value, "city_code");
            return (Criteria) this;
        }

        public Criteria andCity_codeNotLike(String value) {
            addCriterion("city_code not like", value, "city_code");
            return (Criteria) this;
        }

        public Criteria andCity_codeIn(List<String> values) {
            addCriterion("city_code in", values, "city_code");
            return (Criteria) this;
        }

        public Criteria andCity_codeNotIn(List<String> values) {
            addCriterion("city_code not in", values, "city_code");
            return (Criteria) this;
        }

        public Criteria andCity_codeBetween(String value1, String value2) {
            addCriterion("city_code between", value1, value2, "city_code");
            return (Criteria) this;
        }

        public Criteria andCity_codeNotBetween(String value1, String value2) {
            addCriterion("city_code not between", value1, value2, "city_code");
            return (Criteria) this;
        }

        public Criteria andBank_idIsNull() {
            addCriterion("bank_id is null");
            return (Criteria) this;
        }

        public Criteria andBank_idIsNotNull() {
            addCriterion("bank_id is not null");
            return (Criteria) this;
        }

        public Criteria andBank_idEqualTo(String value) {
            addCriterion("bank_id =", value, "bank_id");
            return (Criteria) this;
        }

        public Criteria andBank_idNotEqualTo(String value) {
            addCriterion("bank_id <>", value, "bank_id");
            return (Criteria) this;
        }

        public Criteria andBank_idGreaterThan(String value) {
            addCriterion("bank_id >", value, "bank_id");
            return (Criteria) this;
        }

        public Criteria andBank_idGreaterThanOrEqualTo(String value) {
            addCriterion("bank_id >=", value, "bank_id");
            return (Criteria) this;
        }

        public Criteria andBank_idLessThan(String value) {
            addCriterion("bank_id <", value, "bank_id");
            return (Criteria) this;
        }

        public Criteria andBank_idLessThanOrEqualTo(String value) {
            addCriterion("bank_id <=", value, "bank_id");
            return (Criteria) this;
        }

        public Criteria andBank_idLike(String value) {
            addCriterion("bank_id like", value, "bank_id");
            return (Criteria) this;
        }

        public Criteria andBank_idNotLike(String value) {
            addCriterion("bank_id not like", value, "bank_id");
            return (Criteria) this;
        }

        public Criteria andBank_idIn(List<String> values) {
            addCriterion("bank_id in", values, "bank_id");
            return (Criteria) this;
        }

        public Criteria andBank_idNotIn(List<String> values) {
            addCriterion("bank_id not in", values, "bank_id");
            return (Criteria) this;
        }

        public Criteria andBank_idBetween(String value1, String value2) {
            addCriterion("bank_id between", value1, value2, "bank_id");
            return (Criteria) this;
        }

        public Criteria andBank_idNotBetween(String value1, String value2) {
            addCriterion("bank_id not between", value1, value2, "bank_id");
            return (Criteria) this;
        }

        public Criteria andBrabank_nameIsNull() {
            addCriterion("brabank_name is null");
            return (Criteria) this;
        }

        public Criteria andBrabank_nameIsNotNull() {
            addCriterion("brabank_name is not null");
            return (Criteria) this;
        }

        public Criteria andBrabank_nameEqualTo(String value) {
            addCriterion("brabank_name =", value, "brabank_name");
            return (Criteria) this;
        }

        public Criteria andBrabank_nameNotEqualTo(String value) {
            addCriterion("brabank_name <>", value, "brabank_name");
            return (Criteria) this;
        }

        public Criteria andBrabank_nameGreaterThan(String value) {
            addCriterion("brabank_name >", value, "brabank_name");
            return (Criteria) this;
        }

        public Criteria andBrabank_nameGreaterThanOrEqualTo(String value) {
            addCriterion("brabank_name >=", value, "brabank_name");
            return (Criteria) this;
        }

        public Criteria andBrabank_nameLessThan(String value) {
            addCriterion("brabank_name <", value, "brabank_name");
            return (Criteria) this;
        }

        public Criteria andBrabank_nameLessThanOrEqualTo(String value) {
            addCriterion("brabank_name <=", value, "brabank_name");
            return (Criteria) this;
        }

        public Criteria andBrabank_nameLike(String value) {
            addCriterion("brabank_name like", value, "brabank_name");
            return (Criteria) this;
        }

        public Criteria andBrabank_nameNotLike(String value) {
            addCriterion("brabank_name not like", value, "brabank_name");
            return (Criteria) this;
        }

        public Criteria andBrabank_nameIn(List<String> values) {
            addCriterion("brabank_name in", values, "brabank_name");
            return (Criteria) this;
        }

        public Criteria andBrabank_nameNotIn(List<String> values) {
            addCriterion("brabank_name not in", values, "brabank_name");
            return (Criteria) this;
        }

        public Criteria andBrabank_nameBetween(String value1, String value2) {
            addCriterion("brabank_name between", value1, value2, "brabank_name");
            return (Criteria) this;
        }

        public Criteria andBrabank_nameNotBetween(String value1, String value2) {
            addCriterion("brabank_name not between", value1, value2, "brabank_name");
            return (Criteria) this;
        }

        public Criteria andIs_advanceIsNull() {
            addCriterion("is_advance is null");
            return (Criteria) this;
        }

        public Criteria andIs_advanceIsNotNull() {
            addCriterion("is_advance is not null");
            return (Criteria) this;
        }

        public Criteria andIs_advanceEqualTo(String value) {
            addCriterion("is_advance =", value, "is_advance");
            return (Criteria) this;
        }

        public Criteria andIs_advanceNotEqualTo(String value) {
            addCriterion("is_advance <>", value, "is_advance");
            return (Criteria) this;
        }

        public Criteria andIs_advanceGreaterThan(String value) {
            addCriterion("is_advance >", value, "is_advance");
            return (Criteria) this;
        }

        public Criteria andIs_advanceGreaterThanOrEqualTo(String value) {
            addCriterion("is_advance >=", value, "is_advance");
            return (Criteria) this;
        }

        public Criteria andIs_advanceLessThan(String value) {
            addCriterion("is_advance <", value, "is_advance");
            return (Criteria) this;
        }

        public Criteria andIs_advanceLessThanOrEqualTo(String value) {
            addCriterion("is_advance <=", value, "is_advance");
            return (Criteria) this;
        }

        public Criteria andIs_advanceLike(String value) {
            addCriterion("is_advance like", value, "is_advance");
            return (Criteria) this;
        }

        public Criteria andIs_advanceNotLike(String value) {
            addCriterion("is_advance not like", value, "is_advance");
            return (Criteria) this;
        }

        public Criteria andIs_advanceIn(List<String> values) {
            addCriterion("is_advance in", values, "is_advance");
            return (Criteria) this;
        }

        public Criteria andIs_advanceNotIn(List<String> values) {
            addCriterion("is_advance not in", values, "is_advance");
            return (Criteria) this;
        }

        public Criteria andIs_advanceBetween(String value1, String value2) {
            addCriterion("is_advance between", value1, value2, "is_advance");
            return (Criteria) this;
        }

        public Criteria andIs_advanceNotBetween(String value1, String value2) {
            addCriterion("is_advance not between", value1, value2, "is_advance");
            return (Criteria) this;
        }

        public Criteria andPay_statusIsNull() {
            addCriterion("pay_status is null");
            return (Criteria) this;
        }

        public Criteria andPay_statusIsNotNull() {
            addCriterion("pay_status is not null");
            return (Criteria) this;
        }

        public Criteria andPay_statusEqualTo(String value) {
            addCriterion("pay_status =", value, "pay_status");
            return (Criteria) this;
        }

        public Criteria andPay_statusNotEqualTo(String value) {
            addCriterion("pay_status <>", value, "pay_status");
            return (Criteria) this;
        }

        public Criteria andPay_statusGreaterThan(String value) {
            addCriterion("pay_status >", value, "pay_status");
            return (Criteria) this;
        }

        public Criteria andPay_statusGreaterThanOrEqualTo(String value) {
            addCriterion("pay_status >=", value, "pay_status");
            return (Criteria) this;
        }

        public Criteria andPay_statusLessThan(String value) {
            addCriterion("pay_status <", value, "pay_status");
            return (Criteria) this;
        }

        public Criteria andPay_statusLessThanOrEqualTo(String value) {
            addCriterion("pay_status <=", value, "pay_status");
            return (Criteria) this;
        }

        public Criteria andPay_statusLike(String value) {
            addCriterion("pay_status like", value, "pay_status");
            return (Criteria) this;
        }

        public Criteria andPay_statusNotLike(String value) {
            addCriterion("pay_status not like", value, "pay_status");
            return (Criteria) this;
        }

        public Criteria andPay_statusIn(List<String> values) {
            addCriterion("pay_status in", values, "pay_status");
            return (Criteria) this;
        }

        public Criteria andPay_statusNotIn(List<String> values) {
            addCriterion("pay_status not in", values, "pay_status");
            return (Criteria) this;
        }

        public Criteria andPay_statusBetween(String value1, String value2) {
            addCriterion("pay_status between", value1, value2, "pay_status");
            return (Criteria) this;
        }

        public Criteria andPay_statusNotBetween(String value1, String value2) {
            addCriterion("pay_status not between", value1, value2, "pay_status");
            return (Criteria) this;
        }

        public Criteria andAcct_stateIsNull() {
            addCriterion("acct_state is null");
            return (Criteria) this;
        }

        public Criteria andAcct_stateIsNotNull() {
            addCriterion("acct_state is not null");
            return (Criteria) this;
        }

        public Criteria andAcct_stateEqualTo(String value) {
            addCriterion("acct_state =", value, "acct_state");
            return (Criteria) this;
        }

        public Criteria andAcct_stateNotEqualTo(String value) {
            addCriterion("acct_state <>", value, "acct_state");
            return (Criteria) this;
        }

        public Criteria andAcct_stateGreaterThan(String value) {
            addCriterion("acct_state >", value, "acct_state");
            return (Criteria) this;
        }

        public Criteria andAcct_stateGreaterThanOrEqualTo(String value) {
            addCriterion("acct_state >=", value, "acct_state");
            return (Criteria) this;
        }

        public Criteria andAcct_stateLessThan(String value) {
            addCriterion("acct_state <", value, "acct_state");
            return (Criteria) this;
        }

        public Criteria andAcct_stateLessThanOrEqualTo(String value) {
            addCriterion("acct_state <=", value, "acct_state");
            return (Criteria) this;
        }

        public Criteria andAcct_stateLike(String value) {
            addCriterion("acct_state like", value, "acct_state");
            return (Criteria) this;
        }

        public Criteria andAcct_stateNotLike(String value) {
            addCriterion("acct_state not like", value, "acct_state");
            return (Criteria) this;
        }

        public Criteria andAcct_stateIn(List<String> values) {
            addCriterion("acct_state in", values, "acct_state");
            return (Criteria) this;
        }

        public Criteria andAcct_stateNotIn(List<String> values) {
            addCriterion("acct_state not in", values, "acct_state");
            return (Criteria) this;
        }

        public Criteria andAcct_stateBetween(String value1, String value2) {
            addCriterion("acct_state between", value1, value2, "acct_state");
            return (Criteria) this;
        }

        public Criteria andAcct_stateNotBetween(String value1, String value2) {
            addCriterion("acct_state not between", value1, value2, "acct_state");
            return (Criteria) this;
        }

        public Criteria andWithdraw_noIsNull() {
            addCriterion("withdraw_no is null");
            return (Criteria) this;
        }

        public Criteria andWithdraw_noIsNotNull() {
            addCriterion("withdraw_no is not null");
            return (Criteria) this;
        }

        public Criteria andWithdraw_noEqualTo(String value) {
            addCriterion("withdraw_no =", value, "withdraw_no");
            return (Criteria) this;
        }

        public Criteria andWithdraw_noNotEqualTo(String value) {
            addCriterion("withdraw_no <>", value, "withdraw_no");
            return (Criteria) this;
        }

        public Criteria andWithdraw_noGreaterThan(String value) {
            addCriterion("withdraw_no >", value, "withdraw_no");
            return (Criteria) this;
        }

        public Criteria andWithdraw_noGreaterThanOrEqualTo(String value) {
            addCriterion("withdraw_no >=", value, "withdraw_no");
            return (Criteria) this;
        }

        public Criteria andWithdraw_noLessThan(String value) {
            addCriterion("withdraw_no <", value, "withdraw_no");
            return (Criteria) this;
        }

        public Criteria andWithdraw_noLessThanOrEqualTo(String value) {
            addCriterion("withdraw_no <=", value, "withdraw_no");
            return (Criteria) this;
        }

        public Criteria andWithdraw_noLike(String value) {
            addCriterion("withdraw_no like", value, "withdraw_no");
            return (Criteria) this;
        }

        public Criteria andWithdraw_noNotLike(String value) {
            addCriterion("withdraw_no not like", value, "withdraw_no");
            return (Criteria) this;
        }

        public Criteria andWithdraw_noIn(List<String> values) {
            addCriterion("withdraw_no in", values, "withdraw_no");
            return (Criteria) this;
        }

        public Criteria andWithdraw_noNotIn(List<String> values) {
            addCriterion("withdraw_no not in", values, "withdraw_no");
            return (Criteria) this;
        }

        public Criteria andWithdraw_noBetween(String value1, String value2) {
            addCriterion("withdraw_no between", value1, value2, "withdraw_no");
            return (Criteria) this;
        }

        public Criteria andWithdraw_noNotBetween(String value1, String value2) {
            addCriterion("withdraw_no not between", value1, value2, "withdraw_no");
            return (Criteria) this;
        }

        public Criteria andPay_timeIsNull() {
            addCriterion("pay_time is null");
            return (Criteria) this;
        }

        public Criteria andPay_timeIsNotNull() {
            addCriterion("pay_time is not null");
            return (Criteria) this;
        }

        public Criteria andPay_timeEqualTo(String value) {
            addCriterion("pay_time =", value, "pay_time");
            return (Criteria) this;
        }

        public Criteria andPay_timeNotEqualTo(String value) {
            addCriterion("pay_time <>", value, "pay_time");
            return (Criteria) this;
        }

        public Criteria andPay_timeGreaterThan(String value) {
            addCriterion("pay_time >", value, "pay_time");
            return (Criteria) this;
        }

        public Criteria andPay_timeGreaterThanOrEqualTo(String value) {
            addCriterion("pay_time >=", value, "pay_time");
            return (Criteria) this;
        }

        public Criteria andPay_timeLessThan(String value) {
            addCriterion("pay_time <", value, "pay_time");
            return (Criteria) this;
        }

        public Criteria andPay_timeLessThanOrEqualTo(String value) {
            addCriterion("pay_time <=", value, "pay_time");
            return (Criteria) this;
        }

        public Criteria andPay_timeLike(String value) {
            addCriterion("pay_time like", value, "pay_time");
            return (Criteria) this;
        }

        public Criteria andPay_timeNotLike(String value) {
            addCriterion("pay_time not like", value, "pay_time");
            return (Criteria) this;
        }

        public Criteria andPay_timeIn(List<String> values) {
            addCriterion("pay_time in", values, "pay_time");
            return (Criteria) this;
        }

        public Criteria andPay_timeNotIn(List<String> values) {
            addCriterion("pay_time not in", values, "pay_time");
            return (Criteria) this;
        }

        public Criteria andPay_timeBetween(String value1, String value2) {
            addCriterion("pay_time between", value1, value2, "pay_time");
            return (Criteria) this;
        }

        public Criteria andPay_timeNotBetween(String value1, String value2) {
            addCriterion("pay_time not between", value1, value2, "pay_time");
            return (Criteria) this;
        }

        public Criteria andFinish_timeIsNull() {
            addCriterion("finish_time is null");
            return (Criteria) this;
        }

        public Criteria andFinish_timeIsNotNull() {
            addCriterion("finish_time is not null");
            return (Criteria) this;
        }

        public Criteria andFinish_timeEqualTo(String value) {
            addCriterion("finish_time =", value, "finish_time");
            return (Criteria) this;
        }

        public Criteria andFinish_timeNotEqualTo(String value) {
            addCriterion("finish_time <>", value, "finish_time");
            return (Criteria) this;
        }

        public Criteria andFinish_timeGreaterThan(String value) {
            addCriterion("finish_time >", value, "finish_time");
            return (Criteria) this;
        }

        public Criteria andFinish_timeGreaterThanOrEqualTo(String value) {
            addCriterion("finish_time >=", value, "finish_time");
            return (Criteria) this;
        }

        public Criteria andFinish_timeLessThan(String value) {
            addCriterion("finish_time <", value, "finish_time");
            return (Criteria) this;
        }

        public Criteria andFinish_timeLessThanOrEqualTo(String value) {
            addCriterion("finish_time <=", value, "finish_time");
            return (Criteria) this;
        }

        public Criteria andFinish_timeLike(String value) {
            addCriterion("finish_time like", value, "finish_time");
            return (Criteria) this;
        }

        public Criteria andFinish_timeNotLike(String value) {
            addCriterion("finish_time not like", value, "finish_time");
            return (Criteria) this;
        }

        public Criteria andFinish_timeIn(List<String> values) {
            addCriterion("finish_time in", values, "finish_time");
            return (Criteria) this;
        }

        public Criteria andFinish_timeNotIn(List<String> values) {
            addCriterion("finish_time not in", values, "finish_time");
            return (Criteria) this;
        }

        public Criteria andFinish_timeBetween(String value1, String value2) {
            addCriterion("finish_time between", value1, value2, "finish_time");
            return (Criteria) this;
        }

        public Criteria andFinish_timeNotBetween(String value1, String value2) {
            addCriterion("finish_time not between", value1, value2, "finish_time");
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

        public Criteria andHost_req_serial_noIsNull() {
            addCriterion("host_req_serial_no is null");
            return (Criteria) this;
        }

        public Criteria andHost_req_serial_noIsNotNull() {
            addCriterion("host_req_serial_no is not null");
            return (Criteria) this;
        }

        public Criteria andHost_req_serial_noEqualTo(String value) {
            addCriterion("host_req_serial_no =", value, "host_req_serial_no");
            return (Criteria) this;
        }

        public Criteria andHost_req_serial_noNotEqualTo(String value) {
            addCriterion("host_req_serial_no <>", value, "host_req_serial_no");
            return (Criteria) this;
        }

        public Criteria andHost_req_serial_noGreaterThan(String value) {
            addCriterion("host_req_serial_no >", value, "host_req_serial_no");
            return (Criteria) this;
        }

        public Criteria andHost_req_serial_noGreaterThanOrEqualTo(String value) {
            addCriterion("host_req_serial_no >=", value, "host_req_serial_no");
            return (Criteria) this;
        }

        public Criteria andHost_req_serial_noLessThan(String value) {
            addCriterion("host_req_serial_no <", value, "host_req_serial_no");
            return (Criteria) this;
        }

        public Criteria andHost_req_serial_noLessThanOrEqualTo(String value) {
            addCriterion("host_req_serial_no <=", value, "host_req_serial_no");
            return (Criteria) this;
        }

        public Criteria andHost_req_serial_noLike(String value) {
            addCriterion("host_req_serial_no like", value, "host_req_serial_no");
            return (Criteria) this;
        }

        public Criteria andHost_req_serial_noNotLike(String value) {
            addCriterion("host_req_serial_no not like", value, "host_req_serial_no");
            return (Criteria) this;
        }

        public Criteria andHost_req_serial_noIn(List<String> values) {
            addCriterion("host_req_serial_no in", values, "host_req_serial_no");
            return (Criteria) this;
        }

        public Criteria andHost_req_serial_noNotIn(List<String> values) {
            addCriterion("host_req_serial_no not in", values, "host_req_serial_no");
            return (Criteria) this;
        }

        public Criteria andHost_req_serial_noBetween(String value1, String value2) {
            addCriterion("host_req_serial_no between", value1, value2, "host_req_serial_no");
            return (Criteria) this;
        }

        public Criteria andHost_req_serial_noNotBetween(String value1, String value2) {
            addCriterion("host_req_serial_no not between", value1, value2, "host_req_serial_no");
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

        public Criteria andQuery_no_numIsNull() {
            addCriterion("query_no_num is null");
            return (Criteria) this;
        }

        public Criteria andQuery_no_numIsNotNull() {
            addCriterion("query_no_num is not null");
            return (Criteria) this;
        }

        public Criteria andQuery_no_numEqualTo(Integer value) {
            addCriterion("query_no_num =", value, "query_no_num");
            return (Criteria) this;
        }

        public Criteria andQuery_no_numNotEqualTo(Integer value) {
            addCriterion("query_no_num <>", value, "query_no_num");
            return (Criteria) this;
        }

        public Criteria andQuery_no_numGreaterThan(Integer value) {
            addCriterion("query_no_num >", value, "query_no_num");
            return (Criteria) this;
        }

        public Criteria andQuery_no_numGreaterThanOrEqualTo(Integer value) {
            addCriterion("query_no_num >=", value, "query_no_num");
            return (Criteria) this;
        }

        public Criteria andQuery_no_numLessThan(Integer value) {
            addCriterion("query_no_num <", value, "query_no_num");
            return (Criteria) this;
        }

        public Criteria andQuery_no_numLessThanOrEqualTo(Integer value) {
            addCriterion("query_no_num <=", value, "query_no_num");
            return (Criteria) this;
        }

        public Criteria andQuery_no_numIn(List<Integer> values) {
            addCriterion("query_no_num in", values, "query_no_num");
            return (Criteria) this;
        }

        public Criteria andQuery_no_numNotIn(List<Integer> values) {
            addCriterion("query_no_num not in", values, "query_no_num");
            return (Criteria) this;
        }

        public Criteria andQuery_no_numBetween(Integer value1, Integer value2) {
            addCriterion("query_no_num between", value1, value2, "query_no_num");
            return (Criteria) this;
        }

        public Criteria andQuery_no_numNotBetween(Integer value1, Integer value2) {
            addCriterion("query_no_num not between", value1, value2, "query_no_num");
            return (Criteria) this;
        }

        public Criteria andPaymessageIsNull() {
            addCriterion("paymessage is null");
            return (Criteria) this;
        }

        public Criteria andPaymessageIsNotNull() {
            addCriterion("paymessage is not null");
            return (Criteria) this;
        }

        public Criteria andPaymessageEqualTo(String value) {
            addCriterion("paymessage =", value, "paymessage");
            return (Criteria) this;
        }

        public Criteria andPaymessageNotEqualTo(String value) {
            addCriterion("paymessage <>", value, "paymessage");
            return (Criteria) this;
        }

        public Criteria andPaymessageGreaterThan(String value) {
            addCriterion("paymessage >", value, "paymessage");
            return (Criteria) this;
        }

        public Criteria andPaymessageGreaterThanOrEqualTo(String value) {
            addCriterion("paymessage >=", value, "paymessage");
            return (Criteria) this;
        }

        public Criteria andPaymessageLessThan(String value) {
            addCriterion("paymessage <", value, "paymessage");
            return (Criteria) this;
        }

        public Criteria andPaymessageLessThanOrEqualTo(String value) {
            addCriterion("paymessage <=", value, "paymessage");
            return (Criteria) this;
        }

        public Criteria andPaymessageLike(String value) {
            addCriterion("paymessage like", value, "paymessage");
            return (Criteria) this;
        }

        public Criteria andPaymessageNotLike(String value) {
            addCriterion("paymessage not like", value, "paymessage");
            return (Criteria) this;
        }

        public Criteria andPaymessageIn(List<String> values) {
            addCriterion("paymessage in", values, "paymessage");
            return (Criteria) this;
        }

        public Criteria andPaymessageNotIn(List<String> values) {
            addCriterion("paymessage not in", values, "paymessage");
            return (Criteria) this;
        }

        public Criteria andPaymessageBetween(String value1, String value2) {
            addCriterion("paymessage between", value1, value2, "paymessage");
            return (Criteria) this;
        }

        public Criteria andPaymessageNotBetween(String value1, String value2) {
            addCriterion("paymessage not between", value1, value2, "paymessage");
            return (Criteria) this;
        }

        public Criteria andProvince_codeIsNull() {
            addCriterion("province_code is null");
            return (Criteria) this;
        }

        public Criteria andProvince_codeIsNotNull() {
            addCriterion("province_code is not null");
            return (Criteria) this;
        }

        public Criteria andProvince_codeEqualTo(String value) {
            addCriterion("province_code =", value, "province_code");
            return (Criteria) this;
        }

        public Criteria andProvince_codeNotEqualTo(String value) {
            addCriterion("province_code <>", value, "province_code");
            return (Criteria) this;
        }

        public Criteria andProvince_codeGreaterThan(String value) {
            addCriterion("province_code >", value, "province_code");
            return (Criteria) this;
        }

        public Criteria andProvince_codeGreaterThanOrEqualTo(String value) {
            addCriterion("province_code >=", value, "province_code");
            return (Criteria) this;
        }

        public Criteria andProvince_codeLessThan(String value) {
            addCriterion("province_code <", value, "province_code");
            return (Criteria) this;
        }

        public Criteria andProvince_codeLessThanOrEqualTo(String value) {
            addCriterion("province_code <=", value, "province_code");
            return (Criteria) this;
        }

        public Criteria andProvince_codeLike(String value) {
            addCriterion("province_code like", value, "province_code");
            return (Criteria) this;
        }

        public Criteria andProvince_codeNotLike(String value) {
            addCriterion("province_code not like", value, "province_code");
            return (Criteria) this;
        }

        public Criteria andProvince_codeIn(List<String> values) {
            addCriterion("province_code in", values, "province_code");
            return (Criteria) this;
        }

        public Criteria andProvince_codeNotIn(List<String> values) {
            addCriterion("province_code not in", values, "province_code");
            return (Criteria) this;
        }

        public Criteria andProvince_codeBetween(String value1, String value2) {
            addCriterion("province_code between", value1, value2, "province_code");
            return (Criteria) this;
        }

        public Criteria andProvince_codeNotBetween(String value1, String value2) {
            addCriterion("province_code not between", value1, value2, "province_code");
            return (Criteria) this;
        }

        public Criteria andPayment_statusIsNull() {
            addCriterion("payment_status is null");
            return (Criteria) this;
        }

        public Criteria andPayment_statusIsNotNull() {
            addCriterion("payment_status is not null");
            return (Criteria) this;
        }

        public Criteria andPayment_statusEqualTo(String value) {
            addCriterion("payment_status =", value, "payment_status");
            return (Criteria) this;
        }

        public Criteria andPayment_statusNotEqualTo(String value) {
            addCriterion("payment_status <>", value, "payment_status");
            return (Criteria) this;
        }

        public Criteria andPayment_statusGreaterThan(String value) {
            addCriterion("payment_status >", value, "payment_status");
            return (Criteria) this;
        }

        public Criteria andPayment_statusGreaterThanOrEqualTo(String value) {
            addCriterion("payment_status >=", value, "payment_status");
            return (Criteria) this;
        }

        public Criteria andPayment_statusLessThan(String value) {
            addCriterion("payment_status <", value, "payment_status");
            return (Criteria) this;
        }

        public Criteria andPayment_statusLessThanOrEqualTo(String value) {
            addCriterion("payment_status <=", value, "payment_status");
            return (Criteria) this;
        }

        public Criteria andPayment_statusLike(String value) {
            addCriterion("payment_status like", value, "payment_status");
            return (Criteria) this;
        }

        public Criteria andPayment_statusNotLike(String value) {
            addCriterion("payment_status not like", value, "payment_status");
            return (Criteria) this;
        }

        public Criteria andPayment_statusIn(List<String> values) {
            addCriterion("payment_status in", values, "payment_status");
            return (Criteria) this;
        }

        public Criteria andPayment_statusNotIn(List<String> values) {
            addCriterion("payment_status not in", values, "payment_status");
            return (Criteria) this;
        }

        public Criteria andPayment_statusBetween(String value1, String value2) {
            addCriterion("payment_status between", value1, value2, "payment_status");
            return (Criteria) this;
        }

        public Criteria andPayment_statusNotBetween(String value1, String value2) {
            addCriterion("payment_status not between", value1, value2, "payment_status");
            return (Criteria) this;
        }

        public Criteria andPayment_dateIsNull() {
            addCriterion("payment_date is null");
            return (Criteria) this;
        }

        public Criteria andPayment_dateIsNotNull() {
            addCriterion("payment_date is not null");
            return (Criteria) this;
        }

        public Criteria andPayment_dateEqualTo(String value) {
            addCriterion("payment_date =", value, "payment_date");
            return (Criteria) this;
        }

        public Criteria andPayment_dateNotEqualTo(String value) {
            addCriterion("payment_date <>", value, "payment_date");
            return (Criteria) this;
        }

        public Criteria andPayment_dateGreaterThan(String value) {
            addCriterion("payment_date >", value, "payment_date");
            return (Criteria) this;
        }

        public Criteria andPayment_dateGreaterThanOrEqualTo(String value) {
            addCriterion("payment_date >=", value, "payment_date");
            return (Criteria) this;
        }

        public Criteria andPayment_dateLessThan(String value) {
            addCriterion("payment_date <", value, "payment_date");
            return (Criteria) this;
        }

        public Criteria andPayment_dateLessThanOrEqualTo(String value) {
            addCriterion("payment_date <=", value, "payment_date");
            return (Criteria) this;
        }

        public Criteria andPayment_dateLike(String value) {
            addCriterion("payment_date like", value, "payment_date");
            return (Criteria) this;
        }

        public Criteria andPayment_dateNotLike(String value) {
            addCriterion("payment_date not like", value, "payment_date");
            return (Criteria) this;
        }

        public Criteria andPayment_dateIn(List<String> values) {
            addCriterion("payment_date in", values, "payment_date");
            return (Criteria) this;
        }

        public Criteria andPayment_dateNotIn(List<String> values) {
            addCriterion("payment_date not in", values, "payment_date");
            return (Criteria) this;
        }

        public Criteria andPayment_dateBetween(String value1, String value2) {
            addCriterion("payment_date between", value1, value2, "payment_date");
            return (Criteria) this;
        }

        public Criteria andPayment_dateNotBetween(String value1, String value2) {
            addCriterion("payment_date not between", value1, value2, "payment_date");
            return (Criteria) this;
        }

        public Criteria andCard_typeIsNull() {
            addCriterion("card_type is null");
            return (Criteria) this;
        }

        public Criteria andCard_typeIsNotNull() {
            addCriterion("card_type is not null");
            return (Criteria) this;
        }

        public Criteria andCard_typeEqualTo(String value) {
            addCriterion("card_type =", value, "card_type");
            return (Criteria) this;
        }

        public Criteria andCard_typeNotEqualTo(String value) {
            addCriterion("card_type <>", value, "card_type");
            return (Criteria) this;
        }

        public Criteria andCard_typeGreaterThan(String value) {
            addCriterion("card_type >", value, "card_type");
            return (Criteria) this;
        }

        public Criteria andCard_typeGreaterThanOrEqualTo(String value) {
            addCriterion("card_type >=", value, "card_type");
            return (Criteria) this;
        }

        public Criteria andCard_typeLessThan(String value) {
            addCriterion("card_type <", value, "card_type");
            return (Criteria) this;
        }

        public Criteria andCard_typeLessThanOrEqualTo(String value) {
            addCriterion("card_type <=", value, "card_type");
            return (Criteria) this;
        }

        public Criteria andCard_typeLike(String value) {
            addCriterion("card_type like", value, "card_type");
            return (Criteria) this;
        }

        public Criteria andCard_typeNotLike(String value) {
            addCriterion("card_type not like", value, "card_type");
            return (Criteria) this;
        }

        public Criteria andCard_typeIn(List<String> values) {
            addCriterion("card_type in", values, "card_type");
            return (Criteria) this;
        }

        public Criteria andCard_typeNotIn(List<String> values) {
            addCriterion("card_type not in", values, "card_type");
            return (Criteria) this;
        }

        public Criteria andCard_typeBetween(String value1, String value2) {
            addCriterion("card_type between", value1, value2, "card_type");
            return (Criteria) this;
        }

        public Criteria andCard_typeNotBetween(String value1, String value2) {
            addCriterion("card_type not between", value1, value2, "card_type");
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
    }

    /**
     * rw_withdraw
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * rw_withdraw 2018-01-29
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