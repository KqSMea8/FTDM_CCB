package com.sunyard.sunfintech.dao.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SunbobTaskUnionpayExample {
    /**
     * sunbob_task_unionpay
     */
    protected String orderByClause;

    /**
     * sunbob_task_unionpay
     */
    protected boolean distinct;

    /**
     * sunbob_task_unionpay
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2017-06-01
     */
    public SunbobTaskUnionpayExample() {
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
     * sunbob_task_unionpay 2017-06-01
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

        public Criteria andThird_noIsNull() {
            addCriterion("third_no is null");
            return (Criteria) this;
        }

        public Criteria andThird_noIsNotNull() {
            addCriterion("third_no is not null");
            return (Criteria) this;
        }

        public Criteria andThird_noEqualTo(String value) {
            addCriterion("third_no =", value, "third_no");
            return (Criteria) this;
        }

        public Criteria andThird_noNotEqualTo(String value) {
            addCriterion("third_no <>", value, "third_no");
            return (Criteria) this;
        }

        public Criteria andThird_noGreaterThan(String value) {
            addCriterion("third_no >", value, "third_no");
            return (Criteria) this;
        }

        public Criteria andThird_noGreaterThanOrEqualTo(String value) {
            addCriterion("third_no >=", value, "third_no");
            return (Criteria) this;
        }

        public Criteria andThird_noLessThan(String value) {
            addCriterion("third_no <", value, "third_no");
            return (Criteria) this;
        }

        public Criteria andThird_noLessThanOrEqualTo(String value) {
            addCriterion("third_no <=", value, "third_no");
            return (Criteria) this;
        }

        public Criteria andThird_noLike(String value) {
            addCriterion("third_no like", value, "third_no");
            return (Criteria) this;
        }

        public Criteria andThird_noNotLike(String value) {
            addCriterion("third_no not like", value, "third_no");
            return (Criteria) this;
        }

        public Criteria andThird_noIn(List<String> values) {
            addCriterion("third_no in", values, "third_no");
            return (Criteria) this;
        }

        public Criteria andThird_noNotIn(List<String> values) {
            addCriterion("third_no not in", values, "third_no");
            return (Criteria) this;
        }

        public Criteria andThird_noBetween(String value1, String value2) {
            addCriterion("third_no between", value1, value2, "third_no");
            return (Criteria) this;
        }

        public Criteria andThird_noNotBetween(String value1, String value2) {
            addCriterion("third_no not between", value1, value2, "third_no");
            return (Criteria) this;
        }

        public Criteria andThird_batch_noIsNull() {
            addCriterion("third_batch_no is null");
            return (Criteria) this;
        }

        public Criteria andThird_batch_noIsNotNull() {
            addCriterion("third_batch_no is not null");
            return (Criteria) this;
        }

        public Criteria andThird_batch_noEqualTo(String value) {
            addCriterion("third_batch_no =", value, "third_batch_no");
            return (Criteria) this;
        }

        public Criteria andThird_batch_noNotEqualTo(String value) {
            addCriterion("third_batch_no <>", value, "third_batch_no");
            return (Criteria) this;
        }

        public Criteria andThird_batch_noGreaterThan(String value) {
            addCriterion("third_batch_no >", value, "third_batch_no");
            return (Criteria) this;
        }

        public Criteria andThird_batch_noGreaterThanOrEqualTo(String value) {
            addCriterion("third_batch_no >=", value, "third_batch_no");
            return (Criteria) this;
        }

        public Criteria andThird_batch_noLessThan(String value) {
            addCriterion("third_batch_no <", value, "third_batch_no");
            return (Criteria) this;
        }

        public Criteria andThird_batch_noLessThanOrEqualTo(String value) {
            addCriterion("third_batch_no <=", value, "third_batch_no");
            return (Criteria) this;
        }

        public Criteria andThird_batch_noLike(String value) {
            addCriterion("third_batch_no like", value, "third_batch_no");
            return (Criteria) this;
        }

        public Criteria andThird_batch_noNotLike(String value) {
            addCriterion("third_batch_no not like", value, "third_batch_no");
            return (Criteria) this;
        }

        public Criteria andThird_batch_noIn(List<String> values) {
            addCriterion("third_batch_no in", values, "third_batch_no");
            return (Criteria) this;
        }

        public Criteria andThird_batch_noNotIn(List<String> values) {
            addCriterion("third_batch_no not in", values, "third_batch_no");
            return (Criteria) this;
        }

        public Criteria andThird_batch_noBetween(String value1, String value2) {
            addCriterion("third_batch_no between", value1, value2, "third_batch_no");
            return (Criteria) this;
        }

        public Criteria andThird_batch_noNotBetween(String value1, String value2) {
            addCriterion("third_batch_no not between", value1, value2, "third_batch_no");
            return (Criteria) this;
        }

        public Criteria andAmtIsNull() {
            addCriterion("amt is null");
            return (Criteria) this;
        }

        public Criteria andAmtIsNotNull() {
            addCriterion("amt is not null");
            return (Criteria) this;
        }

        public Criteria andAmtEqualTo(BigDecimal value) {
            addCriterion("amt =", value, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtNotEqualTo(BigDecimal value) {
            addCriterion("amt <>", value, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtGreaterThan(BigDecimal value) {
            addCriterion("amt >", value, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("amt >=", value, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtLessThan(BigDecimal value) {
            addCriterion("amt <", value, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("amt <=", value, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtIn(List<BigDecimal> values) {
            addCriterion("amt in", values, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtNotIn(List<BigDecimal> values) {
            addCriterion("amt not in", values, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amt between", value1, value2, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amt not between", value1, value2, "amt");
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

        public Criteria andTrans_typeIsNull() {
            addCriterion("trans_type is null");
            return (Criteria) this;
        }

        public Criteria andTrans_typeIsNotNull() {
            addCriterion("trans_type is not null");
            return (Criteria) this;
        }

        public Criteria andTrans_typeEqualTo(String value) {
            addCriterion("trans_type =", value, "trans_type");
            return (Criteria) this;
        }

        public Criteria andTrans_typeNotEqualTo(String value) {
            addCriterion("trans_type <>", value, "trans_type");
            return (Criteria) this;
        }

        public Criteria andTrans_typeGreaterThan(String value) {
            addCriterion("trans_type >", value, "trans_type");
            return (Criteria) this;
        }

        public Criteria andTrans_typeGreaterThanOrEqualTo(String value) {
            addCriterion("trans_type >=", value, "trans_type");
            return (Criteria) this;
        }

        public Criteria andTrans_typeLessThan(String value) {
            addCriterion("trans_type <", value, "trans_type");
            return (Criteria) this;
        }

        public Criteria andTrans_typeLessThanOrEqualTo(String value) {
            addCriterion("trans_type <=", value, "trans_type");
            return (Criteria) this;
        }

        public Criteria andTrans_typeLike(String value) {
            addCriterion("trans_type like", value, "trans_type");
            return (Criteria) this;
        }

        public Criteria andTrans_typeNotLike(String value) {
            addCriterion("trans_type not like", value, "trans_type");
            return (Criteria) this;
        }

        public Criteria andTrans_typeIn(List<String> values) {
            addCriterion("trans_type in", values, "trans_type");
            return (Criteria) this;
        }

        public Criteria andTrans_typeNotIn(List<String> values) {
            addCriterion("trans_type not in", values, "trans_type");
            return (Criteria) this;
        }

        public Criteria andTrans_typeBetween(String value1, String value2) {
            addCriterion("trans_type between", value1, value2, "trans_type");
            return (Criteria) this;
        }

        public Criteria andTrans_typeNotBetween(String value1, String value2) {
            addCriterion("trans_type not between", value1, value2, "trans_type");
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

        public Criteria andOppo_cardIsNull() {
            addCriterion("oppo_card is null");
            return (Criteria) this;
        }

        public Criteria andOppo_cardIsNotNull() {
            addCriterion("oppo_card is not null");
            return (Criteria) this;
        }

        public Criteria andOppo_cardEqualTo(String value) {
            addCriterion("oppo_card =", value, "oppo_card");
            return (Criteria) this;
        }

        public Criteria andOppo_cardNotEqualTo(String value) {
            addCriterion("oppo_card <>", value, "oppo_card");
            return (Criteria) this;
        }

        public Criteria andOppo_cardGreaterThan(String value) {
            addCriterion("oppo_card >", value, "oppo_card");
            return (Criteria) this;
        }

        public Criteria andOppo_cardGreaterThanOrEqualTo(String value) {
            addCriterion("oppo_card >=", value, "oppo_card");
            return (Criteria) this;
        }

        public Criteria andOppo_cardLessThan(String value) {
            addCriterion("oppo_card <", value, "oppo_card");
            return (Criteria) this;
        }

        public Criteria andOppo_cardLessThanOrEqualTo(String value) {
            addCriterion("oppo_card <=", value, "oppo_card");
            return (Criteria) this;
        }

        public Criteria andOppo_cardLike(String value) {
            addCriterion("oppo_card like", value, "oppo_card");
            return (Criteria) this;
        }

        public Criteria andOppo_cardNotLike(String value) {
            addCriterion("oppo_card not like", value, "oppo_card");
            return (Criteria) this;
        }

        public Criteria andOppo_cardIn(List<String> values) {
            addCriterion("oppo_card in", values, "oppo_card");
            return (Criteria) this;
        }

        public Criteria andOppo_cardNotIn(List<String> values) {
            addCriterion("oppo_card not in", values, "oppo_card");
            return (Criteria) this;
        }

        public Criteria andOppo_cardBetween(String value1, String value2) {
            addCriterion("oppo_card between", value1, value2, "oppo_card");
            return (Criteria) this;
        }

        public Criteria andOppo_cardNotBetween(String value1, String value2) {
            addCriterion("oppo_card not between", value1, value2, "oppo_card");
            return (Criteria) this;
        }

        public Criteria andOppo_nameIsNull() {
            addCriterion("oppo_name is null");
            return (Criteria) this;
        }

        public Criteria andOppo_nameIsNotNull() {
            addCriterion("oppo_name is not null");
            return (Criteria) this;
        }

        public Criteria andOppo_nameEqualTo(String value) {
            addCriterion("oppo_name =", value, "oppo_name");
            return (Criteria) this;
        }

        public Criteria andOppo_nameNotEqualTo(String value) {
            addCriterion("oppo_name <>", value, "oppo_name");
            return (Criteria) this;
        }

        public Criteria andOppo_nameGreaterThan(String value) {
            addCriterion("oppo_name >", value, "oppo_name");
            return (Criteria) this;
        }

        public Criteria andOppo_nameGreaterThanOrEqualTo(String value) {
            addCriterion("oppo_name >=", value, "oppo_name");
            return (Criteria) this;
        }

        public Criteria andOppo_nameLessThan(String value) {
            addCriterion("oppo_name <", value, "oppo_name");
            return (Criteria) this;
        }

        public Criteria andOppo_nameLessThanOrEqualTo(String value) {
            addCriterion("oppo_name <=", value, "oppo_name");
            return (Criteria) this;
        }

        public Criteria andOppo_nameLike(String value) {
            addCriterion("oppo_name like", value, "oppo_name");
            return (Criteria) this;
        }

        public Criteria andOppo_nameNotLike(String value) {
            addCriterion("oppo_name not like", value, "oppo_name");
            return (Criteria) this;
        }

        public Criteria andOppo_nameIn(List<String> values) {
            addCriterion("oppo_name in", values, "oppo_name");
            return (Criteria) this;
        }

        public Criteria andOppo_nameNotIn(List<String> values) {
            addCriterion("oppo_name not in", values, "oppo_name");
            return (Criteria) this;
        }

        public Criteria andOppo_nameBetween(String value1, String value2) {
            addCriterion("oppo_name between", value1, value2, "oppo_name");
            return (Criteria) this;
        }

        public Criteria andOppo_nameNotBetween(String value1, String value2) {
            addCriterion("oppo_name not between", value1, value2, "oppo_name");
            return (Criteria) this;
        }

        public Criteria andFinish_datetimeIsNull() {
            addCriterion("finish_datetime is null");
            return (Criteria) this;
        }

        public Criteria andFinish_datetimeIsNotNull() {
            addCriterion("finish_datetime is not null");
            return (Criteria) this;
        }

        public Criteria andFinish_datetimeEqualTo(Date value) {
            addCriterion("finish_datetime =", value, "finish_datetime");
            return (Criteria) this;
        }

        public Criteria andFinish_datetimeNotEqualTo(Date value) {
            addCriterion("finish_datetime <>", value, "finish_datetime");
            return (Criteria) this;
        }

        public Criteria andFinish_datetimeGreaterThan(Date value) {
            addCriterion("finish_datetime >", value, "finish_datetime");
            return (Criteria) this;
        }

        public Criteria andFinish_datetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("finish_datetime >=", value, "finish_datetime");
            return (Criteria) this;
        }

        public Criteria andFinish_datetimeLessThan(Date value) {
            addCriterion("finish_datetime <", value, "finish_datetime");
            return (Criteria) this;
        }

        public Criteria andFinish_datetimeLessThanOrEqualTo(Date value) {
            addCriterion("finish_datetime <=", value, "finish_datetime");
            return (Criteria) this;
        }

        public Criteria andFinish_datetimeIn(List<Date> values) {
            addCriterion("finish_datetime in", values, "finish_datetime");
            return (Criteria) this;
        }

        public Criteria andFinish_datetimeNotIn(List<Date> values) {
            addCriterion("finish_datetime not in", values, "finish_datetime");
            return (Criteria) this;
        }

        public Criteria andFinish_datetimeBetween(Date value1, Date value2) {
            addCriterion("finish_datetime between", value1, value2, "finish_datetime");
            return (Criteria) this;
        }

        public Criteria andFinish_datetimeNotBetween(Date value1, Date value2) {
            addCriterion("finish_datetime not between", value1, value2, "finish_datetime");
            return (Criteria) this;
        }

        public Criteria andResultIsNull() {
            addCriterion("result is null");
            return (Criteria) this;
        }

        public Criteria andResultIsNotNull() {
            addCriterion("result is not null");
            return (Criteria) this;
        }

        public Criteria andResultEqualTo(String value) {
            addCriterion("result =", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotEqualTo(String value) {
            addCriterion("result <>", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultGreaterThan(String value) {
            addCriterion("result >", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultGreaterThanOrEqualTo(String value) {
            addCriterion("result >=", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLessThan(String value) {
            addCriterion("result <", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLessThanOrEqualTo(String value) {
            addCriterion("result <=", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLike(String value) {
            addCriterion("result like", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotLike(String value) {
            addCriterion("result not like", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultIn(List<String> values) {
            addCriterion("result in", values, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotIn(List<String> values) {
            addCriterion("result not in", values, "result");
            return (Criteria) this;
        }

        public Criteria andResultBetween(String value1, String value2) {
            addCriterion("result between", value1, value2, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotBetween(String value1, String value2) {
            addCriterion("result not between", value1, value2, "result");
            return (Criteria) this;
        }

        public Criteria andSummary_wnIsNull() {
            addCriterion("summary_wn is null");
            return (Criteria) this;
        }

        public Criteria andSummary_wnIsNotNull() {
            addCriterion("summary_wn is not null");
            return (Criteria) this;
        }

        public Criteria andSummary_wnEqualTo(String value) {
            addCriterion("summary_wn =", value, "summary_wn");
            return (Criteria) this;
        }

        public Criteria andSummary_wnNotEqualTo(String value) {
            addCriterion("summary_wn <>", value, "summary_wn");
            return (Criteria) this;
        }

        public Criteria andSummary_wnGreaterThan(String value) {
            addCriterion("summary_wn >", value, "summary_wn");
            return (Criteria) this;
        }

        public Criteria andSummary_wnGreaterThanOrEqualTo(String value) {
            addCriterion("summary_wn >=", value, "summary_wn");
            return (Criteria) this;
        }

        public Criteria andSummary_wnLessThan(String value) {
            addCriterion("summary_wn <", value, "summary_wn");
            return (Criteria) this;
        }

        public Criteria andSummary_wnLessThanOrEqualTo(String value) {
            addCriterion("summary_wn <=", value, "summary_wn");
            return (Criteria) this;
        }

        public Criteria andSummary_wnLike(String value) {
            addCriterion("summary_wn like", value, "summary_wn");
            return (Criteria) this;
        }

        public Criteria andSummary_wnNotLike(String value) {
            addCriterion("summary_wn not like", value, "summary_wn");
            return (Criteria) this;
        }

        public Criteria andSummary_wnIn(List<String> values) {
            addCriterion("summary_wn in", values, "summary_wn");
            return (Criteria) this;
        }

        public Criteria andSummary_wnNotIn(List<String> values) {
            addCriterion("summary_wn not in", values, "summary_wn");
            return (Criteria) this;
        }

        public Criteria andSummary_wnBetween(String value1, String value2) {
            addCriterion("summary_wn between", value1, value2, "summary_wn");
            return (Criteria) this;
        }

        public Criteria andSummary_wnNotBetween(String value1, String value2) {
            addCriterion("summary_wn not between", value1, value2, "summary_wn");
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

        public Criteria andAttr1IsNull() {
            addCriterion("attr1 is null");
            return (Criteria) this;
        }

        public Criteria andAttr1IsNotNull() {
            addCriterion("attr1 is not null");
            return (Criteria) this;
        }

        public Criteria andAttr1EqualTo(BigDecimal value) {
            addCriterion("attr1 =", value, "attr1");
            return (Criteria) this;
        }

        public Criteria andAttr1NotEqualTo(BigDecimal value) {
            addCriterion("attr1 <>", value, "attr1");
            return (Criteria) this;
        }

        public Criteria andAttr1GreaterThan(BigDecimal value) {
            addCriterion("attr1 >", value, "attr1");
            return (Criteria) this;
        }

        public Criteria andAttr1GreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("attr1 >=", value, "attr1");
            return (Criteria) this;
        }

        public Criteria andAttr1LessThan(BigDecimal value) {
            addCriterion("attr1 <", value, "attr1");
            return (Criteria) this;
        }

        public Criteria andAttr1LessThanOrEqualTo(BigDecimal value) {
            addCriterion("attr1 <=", value, "attr1");
            return (Criteria) this;
        }

        public Criteria andAttr1In(List<BigDecimal> values) {
            addCriterion("attr1 in", values, "attr1");
            return (Criteria) this;
        }

        public Criteria andAttr1NotIn(List<BigDecimal> values) {
            addCriterion("attr1 not in", values, "attr1");
            return (Criteria) this;
        }

        public Criteria andAttr1Between(BigDecimal value1, BigDecimal value2) {
            addCriterion("attr1 between", value1, value2, "attr1");
            return (Criteria) this;
        }

        public Criteria andAttr1NotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("attr1 not between", value1, value2, "attr1");
            return (Criteria) this;
        }

        public Criteria andAttr2IsNull() {
            addCriterion("attr2 is null");
            return (Criteria) this;
        }

        public Criteria andAttr2IsNotNull() {
            addCriterion("attr2 is not null");
            return (Criteria) this;
        }

        public Criteria andAttr2EqualTo(String value) {
            addCriterion("attr2 =", value, "attr2");
            return (Criteria) this;
        }

        public Criteria andAttr2NotEqualTo(String value) {
            addCriterion("attr2 <>", value, "attr2");
            return (Criteria) this;
        }

        public Criteria andAttr2GreaterThan(String value) {
            addCriterion("attr2 >", value, "attr2");
            return (Criteria) this;
        }

        public Criteria andAttr2GreaterThanOrEqualTo(String value) {
            addCriterion("attr2 >=", value, "attr2");
            return (Criteria) this;
        }

        public Criteria andAttr2LessThan(String value) {
            addCriterion("attr2 <", value, "attr2");
            return (Criteria) this;
        }

        public Criteria andAttr2LessThanOrEqualTo(String value) {
            addCriterion("attr2 <=", value, "attr2");
            return (Criteria) this;
        }

        public Criteria andAttr2Like(String value) {
            addCriterion("attr2 like", value, "attr2");
            return (Criteria) this;
        }

        public Criteria andAttr2NotLike(String value) {
            addCriterion("attr2 not like", value, "attr2");
            return (Criteria) this;
        }

        public Criteria andAttr2In(List<String> values) {
            addCriterion("attr2 in", values, "attr2");
            return (Criteria) this;
        }

        public Criteria andAttr2NotIn(List<String> values) {
            addCriterion("attr2 not in", values, "attr2");
            return (Criteria) this;
        }

        public Criteria andAttr2Between(String value1, String value2) {
            addCriterion("attr2 between", value1, value2, "attr2");
            return (Criteria) this;
        }

        public Criteria andAttr2NotBetween(String value1, String value2) {
            addCriterion("attr2 not between", value1, value2, "attr2");
            return (Criteria) this;
        }

        public Criteria andAttr3IsNull() {
            addCriterion("attr3 is null");
            return (Criteria) this;
        }

        public Criteria andAttr3IsNotNull() {
            addCriterion("attr3 is not null");
            return (Criteria) this;
        }

        public Criteria andAttr3EqualTo(String value) {
            addCriterion("attr3 =", value, "attr3");
            return (Criteria) this;
        }

        public Criteria andAttr3NotEqualTo(String value) {
            addCriterion("attr3 <>", value, "attr3");
            return (Criteria) this;
        }

        public Criteria andAttr3GreaterThan(String value) {
            addCriterion("attr3 >", value, "attr3");
            return (Criteria) this;
        }

        public Criteria andAttr3GreaterThanOrEqualTo(String value) {
            addCriterion("attr3 >=", value, "attr3");
            return (Criteria) this;
        }

        public Criteria andAttr3LessThan(String value) {
            addCriterion("attr3 <", value, "attr3");
            return (Criteria) this;
        }

        public Criteria andAttr3LessThanOrEqualTo(String value) {
            addCriterion("attr3 <=", value, "attr3");
            return (Criteria) this;
        }

        public Criteria andAttr3Like(String value) {
            addCriterion("attr3 like", value, "attr3");
            return (Criteria) this;
        }

        public Criteria andAttr3NotLike(String value) {
            addCriterion("attr3 not like", value, "attr3");
            return (Criteria) this;
        }

        public Criteria andAttr3In(List<String> values) {
            addCriterion("attr3 in", values, "attr3");
            return (Criteria) this;
        }

        public Criteria andAttr3NotIn(List<String> values) {
            addCriterion("attr3 not in", values, "attr3");
            return (Criteria) this;
        }

        public Criteria andAttr3Between(String value1, String value2) {
            addCriterion("attr3 between", value1, value2, "attr3");
            return (Criteria) this;
        }

        public Criteria andAttr3NotBetween(String value1, String value2) {
            addCriterion("attr3 not between", value1, value2, "attr3");
            return (Criteria) this;
        }
    }

    /**
     * sunbob_task_unionpay
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * sunbob_task_unionpay 2017-06-01
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