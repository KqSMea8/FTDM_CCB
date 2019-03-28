package com.sunyard.sunfintech.dao.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlatCardinfoExample {
    /**
     * plat_cardinfo
     */
    protected String orderByClause;

    /**
     * plat_cardinfo
     */
    protected boolean distinct;

    /**
     * plat_cardinfo
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2017-06-01
     */
    public PlatCardinfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *
     * @mbggenerated 2017-06-01
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *
     * @mbggenerated 2017-06-01
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *
     * @mbggenerated 2017-06-01
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *
     * @mbggenerated 2017-06-01
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *
     * @mbggenerated 2017-06-01
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *
     * @mbggenerated 2017-06-01
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *
     * @mbggenerated 2017-06-01
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *
     * @mbggenerated 2017-06-01
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
     * @mbggenerated 2017-06-01
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *
     * @mbggenerated 2017-06-01
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * plat_cardinfo 2017-06-01
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

        public Criteria andMall_noIsNull() {
            addCriterion("mall_no is null");
            return (Criteria) this;
        }

        public Criteria andMall_noIsNotNull() {
            addCriterion("mall_no is not null");
            return (Criteria) this;
        }

        public Criteria andMall_noEqualTo(String value) {
            addCriterion("mall_no =", value, "mall_no");
            return (Criteria) this;
        }

        public Criteria andMall_noNotEqualTo(String value) {
            addCriterion("mall_no <>", value, "mall_no");
            return (Criteria) this;
        }

        public Criteria andMall_noGreaterThan(String value) {
            addCriterion("mall_no >", value, "mall_no");
            return (Criteria) this;
        }

        public Criteria andMall_noGreaterThanOrEqualTo(String value) {
            addCriterion("mall_no >=", value, "mall_no");
            return (Criteria) this;
        }

        public Criteria andMall_noLessThan(String value) {
            addCriterion("mall_no <", value, "mall_no");
            return (Criteria) this;
        }

        public Criteria andMall_noLessThanOrEqualTo(String value) {
            addCriterion("mall_no <=", value, "mall_no");
            return (Criteria) this;
        }

        public Criteria andMall_noLike(String value) {
            addCriterion("mall_no like", value, "mall_no");
            return (Criteria) this;
        }

        public Criteria andMall_noNotLike(String value) {
            addCriterion("mall_no not like", value, "mall_no");
            return (Criteria) this;
        }

        public Criteria andMall_noIn(List<String> values) {
            addCriterion("mall_no in", values, "mall_no");
            return (Criteria) this;
        }

        public Criteria andMall_noNotIn(List<String> values) {
            addCriterion("mall_no not in", values, "mall_no");
            return (Criteria) this;
        }

        public Criteria andMall_noBetween(String value1, String value2) {
            addCriterion("mall_no between", value1, value2, "mall_no");
            return (Criteria) this;
        }

        public Criteria andMall_noNotBetween(String value1, String value2) {
            addCriterion("mall_no not between", value1, value2, "mall_no");
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

        public Criteria andCard_noIsNull() {
            addCriterion("card_no is null");
            return (Criteria) this;
        }

        public Criteria andCard_noIsNotNull() {
            addCriterion("card_no is not null");
            return (Criteria) this;
        }

        public Criteria andCard_noEqualTo(String value) {
            addCriterion("card_no =", value, "card_no");
            return (Criteria) this;
        }

        public Criteria andCard_noNotEqualTo(String value) {
            addCriterion("card_no <>", value, "card_no");
            return (Criteria) this;
        }

        public Criteria andCard_noGreaterThan(String value) {
            addCriterion("card_no >", value, "card_no");
            return (Criteria) this;
        }

        public Criteria andCard_noGreaterThanOrEqualTo(String value) {
            addCriterion("card_no >=", value, "card_no");
            return (Criteria) this;
        }

        public Criteria andCard_noLessThan(String value) {
            addCriterion("card_no <", value, "card_no");
            return (Criteria) this;
        }

        public Criteria andCard_noLessThanOrEqualTo(String value) {
            addCriterion("card_no <=", value, "card_no");
            return (Criteria) this;
        }

        public Criteria andCard_noLike(String value) {
            addCriterion("card_no like", value, "card_no");
            return (Criteria) this;
        }

        public Criteria andCard_noNotLike(String value) {
            addCriterion("card_no not like", value, "card_no");
            return (Criteria) this;
        }

        public Criteria andCard_noIn(List<String> values) {
            addCriterion("card_no in", values, "card_no");
            return (Criteria) this;
        }

        public Criteria andCard_noNotIn(List<String> values) {
            addCriterion("card_no not in", values, "card_no");
            return (Criteria) this;
        }

        public Criteria andCard_noBetween(String value1, String value2) {
            addCriterion("card_no between", value1, value2, "card_no");
            return (Criteria) this;
        }

        public Criteria andCard_noNotBetween(String value1, String value2) {
            addCriterion("card_no not between", value1, value2, "card_no");
            return (Criteria) this;
        }

        public Criteria andCard_nameIsNull() {
            addCriterion("card_name is null");
            return (Criteria) this;
        }

        public Criteria andCard_nameIsNotNull() {
            addCriterion("card_name is not null");
            return (Criteria) this;
        }

        public Criteria andCard_nameEqualTo(String value) {
            addCriterion("card_name =", value, "card_name");
            return (Criteria) this;
        }

        public Criteria andCard_nameNotEqualTo(String value) {
            addCriterion("card_name <>", value, "card_name");
            return (Criteria) this;
        }

        public Criteria andCard_nameGreaterThan(String value) {
            addCriterion("card_name >", value, "card_name");
            return (Criteria) this;
        }

        public Criteria andCard_nameGreaterThanOrEqualTo(String value) {
            addCriterion("card_name >=", value, "card_name");
            return (Criteria) this;
        }

        public Criteria andCard_nameLessThan(String value) {
            addCriterion("card_name <", value, "card_name");
            return (Criteria) this;
        }

        public Criteria andCard_nameLessThanOrEqualTo(String value) {
            addCriterion("card_name <=", value, "card_name");
            return (Criteria) this;
        }

        public Criteria andCard_nameLike(String value) {
            addCriterion("card_name like", value, "card_name");
            return (Criteria) this;
        }

        public Criteria andCard_nameNotLike(String value) {
            addCriterion("card_name not like", value, "card_name");
            return (Criteria) this;
        }

        public Criteria andCard_nameIn(List<String> values) {
            addCriterion("card_name in", values, "card_name");
            return (Criteria) this;
        }

        public Criteria andCard_nameNotIn(List<String> values) {
            addCriterion("card_name not in", values, "card_name");
            return (Criteria) this;
        }

        public Criteria andCard_nameBetween(String value1, String value2) {
            addCriterion("card_name between", value1, value2, "card_name");
            return (Criteria) this;
        }

        public Criteria andCard_nameNotBetween(String value1, String value2) {
            addCriterion("card_name not between", value1, value2, "card_name");
            return (Criteria) this;
        }

        public Criteria andDedust_noIsNull() {
            addCriterion("dedust_no is null");
            return (Criteria) this;
        }

        public Criteria andDedust_noIsNotNull() {
            addCriterion("dedust_no is not null");
            return (Criteria) this;
        }

        public Criteria andDedust_noEqualTo(String value) {
            addCriterion("dedust_no =", value, "dedust_no");
            return (Criteria) this;
        }

        public Criteria andDedust_noNotEqualTo(String value) {
            addCriterion("dedust_no <>", value, "dedust_no");
            return (Criteria) this;
        }

        public Criteria andDedust_noGreaterThan(String value) {
            addCriterion("dedust_no >", value, "dedust_no");
            return (Criteria) this;
        }

        public Criteria andDedust_noGreaterThanOrEqualTo(String value) {
            addCriterion("dedust_no >=", value, "dedust_no");
            return (Criteria) this;
        }

        public Criteria andDedust_noLessThan(String value) {
            addCriterion("dedust_no <", value, "dedust_no");
            return (Criteria) this;
        }

        public Criteria andDedust_noLessThanOrEqualTo(String value) {
            addCriterion("dedust_no <=", value, "dedust_no");
            return (Criteria) this;
        }

        public Criteria andDedust_noLike(String value) {
            addCriterion("dedust_no like", value, "dedust_no");
            return (Criteria) this;
        }

        public Criteria andDedust_noNotLike(String value) {
            addCriterion("dedust_no not like", value, "dedust_no");
            return (Criteria) this;
        }

        public Criteria andDedust_noIn(List<String> values) {
            addCriterion("dedust_no in", values, "dedust_no");
            return (Criteria) this;
        }

        public Criteria andDedust_noNotIn(List<String> values) {
            addCriterion("dedust_no not in", values, "dedust_no");
            return (Criteria) this;
        }

        public Criteria andDedust_noBetween(String value1, String value2) {
            addCriterion("dedust_no between", value1, value2, "dedust_no");
            return (Criteria) this;
        }

        public Criteria andDedust_noNotBetween(String value1, String value2) {
            addCriterion("dedust_no not between", value1, value2, "dedust_no");
            return (Criteria) this;
        }

        public Criteria andPay_noIsNull() {
            addCriterion("pay_no is null");
            return (Criteria) this;
        }

        public Criteria andPay_noIsNotNull() {
            addCriterion("pay_no is not null");
            return (Criteria) this;
        }

        public Criteria andPay_noEqualTo(String value) {
            addCriterion("pay_no =", value, "pay_no");
            return (Criteria) this;
        }

        public Criteria andPay_noNotEqualTo(String value) {
            addCriterion("pay_no <>", value, "pay_no");
            return (Criteria) this;
        }

        public Criteria andPay_noGreaterThan(String value) {
            addCriterion("pay_no >", value, "pay_no");
            return (Criteria) this;
        }

        public Criteria andPay_noGreaterThanOrEqualTo(String value) {
            addCriterion("pay_no >=", value, "pay_no");
            return (Criteria) this;
        }

        public Criteria andPay_noLessThan(String value) {
            addCriterion("pay_no <", value, "pay_no");
            return (Criteria) this;
        }

        public Criteria andPay_noLessThanOrEqualTo(String value) {
            addCriterion("pay_no <=", value, "pay_no");
            return (Criteria) this;
        }

        public Criteria andPay_noLike(String value) {
            addCriterion("pay_no like", value, "pay_no");
            return (Criteria) this;
        }

        public Criteria andPay_noNotLike(String value) {
            addCriterion("pay_no not like", value, "pay_no");
            return (Criteria) this;
        }

        public Criteria andPay_noIn(List<String> values) {
            addCriterion("pay_no in", values, "pay_no");
            return (Criteria) this;
        }

        public Criteria andPay_noNotIn(List<String> values) {
            addCriterion("pay_no not in", values, "pay_no");
            return (Criteria) this;
        }

        public Criteria andPay_noBetween(String value1, String value2) {
            addCriterion("pay_no between", value1, value2, "pay_no");
            return (Criteria) this;
        }

        public Criteria andPay_noNotBetween(String value1, String value2) {
            addCriterion("pay_no not between", value1, value2, "pay_no");
            return (Criteria) this;
        }

        public Criteria andReal_time_balanceIsNull() {
            addCriterion("real_time_balance is null");
            return (Criteria) this;
        }

        public Criteria andReal_time_balanceIsNotNull() {
            addCriterion("real_time_balance is not null");
            return (Criteria) this;
        }

        public Criteria andReal_time_balanceEqualTo(BigDecimal value) {
            addCriterion("real_time_balance =", value, "real_time_balance");
            return (Criteria) this;
        }

        public Criteria andReal_time_balanceNotEqualTo(BigDecimal value) {
            addCriterion("real_time_balance <>", value, "real_time_balance");
            return (Criteria) this;
        }

        public Criteria andReal_time_balanceGreaterThan(BigDecimal value) {
            addCriterion("real_time_balance >", value, "real_time_balance");
            return (Criteria) this;
        }

        public Criteria andReal_time_balanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("real_time_balance >=", value, "real_time_balance");
            return (Criteria) this;
        }

        public Criteria andReal_time_balanceLessThan(BigDecimal value) {
            addCriterion("real_time_balance <", value, "real_time_balance");
            return (Criteria) this;
        }

        public Criteria andReal_time_balanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("real_time_balance <=", value, "real_time_balance");
            return (Criteria) this;
        }

        public Criteria andReal_time_balanceIn(List<BigDecimal> values) {
            addCriterion("real_time_balance in", values, "real_time_balance");
            return (Criteria) this;
        }

        public Criteria andReal_time_balanceNotIn(List<BigDecimal> values) {
            addCriterion("real_time_balance not in", values, "real_time_balance");
            return (Criteria) this;
        }

        public Criteria andReal_time_balanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("real_time_balance between", value1, value2, "real_time_balance");
            return (Criteria) this;
        }

        public Criteria andReal_time_balanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("real_time_balance not between", value1, value2, "real_time_balance");
            return (Criteria) this;
        }

        public Criteria andToday_amtIsNull() {
            addCriterion("today_amt is null");
            return (Criteria) this;
        }

        public Criteria andToday_amtIsNotNull() {
            addCriterion("today_amt is not null");
            return (Criteria) this;
        }

        public Criteria andToday_amtEqualTo(BigDecimal value) {
            addCriterion("today_amt =", value, "today_amt");
            return (Criteria) this;
        }

        public Criteria andToday_amtNotEqualTo(BigDecimal value) {
            addCriterion("today_amt <>", value, "today_amt");
            return (Criteria) this;
        }

        public Criteria andToday_amtGreaterThan(BigDecimal value) {
            addCriterion("today_amt >", value, "today_amt");
            return (Criteria) this;
        }

        public Criteria andToday_amtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("today_amt >=", value, "today_amt");
            return (Criteria) this;
        }

        public Criteria andToday_amtLessThan(BigDecimal value) {
            addCriterion("today_amt <", value, "today_amt");
            return (Criteria) this;
        }

        public Criteria andToday_amtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("today_amt <=", value, "today_amt");
            return (Criteria) this;
        }

        public Criteria andToday_amtIn(List<BigDecimal> values) {
            addCriterion("today_amt in", values, "today_amt");
            return (Criteria) this;
        }

        public Criteria andToday_amtNotIn(List<BigDecimal> values) {
            addCriterion("today_amt not in", values, "today_amt");
            return (Criteria) this;
        }

        public Criteria andToday_amtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("today_amt between", value1, value2, "today_amt");
            return (Criteria) this;
        }

        public Criteria andToday_amtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("today_amt not between", value1, value2, "today_amt");
            return (Criteria) this;
        }

        public Criteria andYesterday_amtIsNull() {
            addCriterion("yesterday_amt is null");
            return (Criteria) this;
        }

        public Criteria andYesterday_amtIsNotNull() {
            addCriterion("yesterday_amt is not null");
            return (Criteria) this;
        }

        public Criteria andYesterday_amtEqualTo(BigDecimal value) {
            addCriterion("yesterday_amt =", value, "yesterday_amt");
            return (Criteria) this;
        }

        public Criteria andYesterday_amtNotEqualTo(BigDecimal value) {
            addCriterion("yesterday_amt <>", value, "yesterday_amt");
            return (Criteria) this;
        }

        public Criteria andYesterday_amtGreaterThan(BigDecimal value) {
            addCriterion("yesterday_amt >", value, "yesterday_amt");
            return (Criteria) this;
        }

        public Criteria andYesterday_amtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("yesterday_amt >=", value, "yesterday_amt");
            return (Criteria) this;
        }

        public Criteria andYesterday_amtLessThan(BigDecimal value) {
            addCriterion("yesterday_amt <", value, "yesterday_amt");
            return (Criteria) this;
        }

        public Criteria andYesterday_amtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("yesterday_amt <=", value, "yesterday_amt");
            return (Criteria) this;
        }

        public Criteria andYesterday_amtIn(List<BigDecimal> values) {
            addCriterion("yesterday_amt in", values, "yesterday_amt");
            return (Criteria) this;
        }

        public Criteria andYesterday_amtNotIn(List<BigDecimal> values) {
            addCriterion("yesterday_amt not in", values, "yesterday_amt");
            return (Criteria) this;
        }

        public Criteria andYesterday_amtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("yesterday_amt between", value1, value2, "yesterday_amt");
            return (Criteria) this;
        }

        public Criteria andYesterday_amtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("yesterday_amt not between", value1, value2, "yesterday_amt");
            return (Criteria) this;
        }

        public Criteria andQuery_dateIsNull() {
            addCriterion("query_date is null");
            return (Criteria) this;
        }

        public Criteria andQuery_dateIsNotNull() {
            addCriterion("query_date is not null");
            return (Criteria) this;
        }

        public Criteria andQuery_dateEqualTo(Date value) {
            addCriterion("query_date =", value, "query_date");
            return (Criteria) this;
        }

        public Criteria andQuery_dateNotEqualTo(Date value) {
            addCriterion("query_date <>", value, "query_date");
            return (Criteria) this;
        }

        public Criteria andQuery_dateGreaterThan(Date value) {
            addCriterion("query_date >", value, "query_date");
            return (Criteria) this;
        }

        public Criteria andQuery_dateGreaterThanOrEqualTo(Date value) {
            addCriterion("query_date >=", value, "query_date");
            return (Criteria) this;
        }

        public Criteria andQuery_dateLessThan(Date value) {
            addCriterion("query_date <", value, "query_date");
            return (Criteria) this;
        }

        public Criteria andQuery_dateLessThanOrEqualTo(Date value) {
            addCriterion("query_date <=", value, "query_date");
            return (Criteria) this;
        }

        public Criteria andQuery_dateIn(List<Date> values) {
            addCriterion("query_date in", values, "query_date");
            return (Criteria) this;
        }

        public Criteria andQuery_dateNotIn(List<Date> values) {
            addCriterion("query_date not in", values, "query_date");
            return (Criteria) this;
        }

        public Criteria andQuery_dateBetween(Date value1, Date value2) {
            addCriterion("query_date between", value1, value2, "query_date");
            return (Criteria) this;
        }

        public Criteria andQuery_dateNotBetween(Date value1, Date value2) {
            addCriterion("query_date not between", value1, value2, "query_date");
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
    }

    /**
     * plat_cardinfo
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * plat_cardinfo 2017-06-01
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