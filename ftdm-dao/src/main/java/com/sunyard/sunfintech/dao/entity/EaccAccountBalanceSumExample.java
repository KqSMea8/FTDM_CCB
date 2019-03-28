package com.sunyard.sunfintech.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EaccAccountBalanceSumExample {
    /**
     * eacc_account_balance_sum
     */
    protected String orderByClause;

    /**
     * eacc_account_balance_sum
     */
    protected boolean distinct;

    /**
     * eacc_account_balance_sum
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2017-08-15
     */
    public EaccAccountBalanceSumExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *
     * @mbggenerated 2017-08-15
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *
     * @mbggenerated 2017-08-15
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *
     * @mbggenerated 2017-08-15
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *
     * @mbggenerated 2017-08-15
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *
     * @mbggenerated 2017-08-15
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *
     * @mbggenerated 2017-08-15
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *
     * @mbggenerated 2017-08-15
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *
     * @mbggenerated 2017-08-15
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
     * @mbggenerated 2017-08-15
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *
     * @mbggenerated 2017-08-15
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * eacc_account_balance_sum 2017-08-15
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

        public Criteria andCount_dateIsNull() {
            addCriterion("count_date is null");
            return (Criteria) this;
        }

        public Criteria andCount_dateIsNotNull() {
            addCriterion("count_date is not null");
            return (Criteria) this;
        }

        public Criteria andCount_dateEqualTo(String value) {
            addCriterion("count_date =", value, "count_date");
            return (Criteria) this;
        }

        public Criteria andCount_dateNotEqualTo(String value) {
            addCriterion("count_date <>", value, "count_date");
            return (Criteria) this;
        }

        public Criteria andCount_dateGreaterThan(String value) {
            addCriterion("count_date >", value, "count_date");
            return (Criteria) this;
        }

        public Criteria andCount_dateGreaterThanOrEqualTo(String value) {
            addCriterion("count_date >=", value, "count_date");
            return (Criteria) this;
        }

        public Criteria andCount_dateLessThan(String value) {
            addCriterion("count_date <", value, "count_date");
            return (Criteria) this;
        }

        public Criteria andCount_dateLessThanOrEqualTo(String value) {
            addCriterion("count_date <=", value, "count_date");
            return (Criteria) this;
        }

        public Criteria andCount_dateLike(String value) {
            addCriterion("count_date like", value, "count_date");
            return (Criteria) this;
        }

        public Criteria andCount_dateNotLike(String value) {
            addCriterion("count_date not like", value, "count_date");
            return (Criteria) this;
        }

        public Criteria andCount_dateIn(List<String> values) {
            addCriterion("count_date in", values, "count_date");
            return (Criteria) this;
        }

        public Criteria andCount_dateNotIn(List<String> values) {
            addCriterion("count_date not in", values, "count_date");
            return (Criteria) this;
        }

        public Criteria andCount_dateBetween(String value1, String value2) {
            addCriterion("count_date between", value1, value2, "count_date");
            return (Criteria) this;
        }

        public Criteria andCount_dateNotBetween(String value1, String value2) {
            addCriterion("count_date not between", value1, value2, "count_date");
            return (Criteria) this;
        }

        public Criteria andOwn_balanceIsNull() {
            addCriterion("own_balance is null");
            return (Criteria) this;
        }

        public Criteria andOwn_balanceIsNotNull() {
            addCriterion("own_balance is not null");
            return (Criteria) this;
        }

        public Criteria andOwn_balanceEqualTo(String value) {
            addCriterion("own_balance =", value, "own_balance");
            return (Criteria) this;
        }

        public Criteria andOwn_balanceNotEqualTo(String value) {
            addCriterion("own_balance <>", value, "own_balance");
            return (Criteria) this;
        }

        public Criteria andOwn_balanceGreaterThan(String value) {
            addCriterion("own_balance >", value, "own_balance");
            return (Criteria) this;
        }

        public Criteria andOwn_balanceGreaterThanOrEqualTo(String value) {
            addCriterion("own_balance >=", value, "own_balance");
            return (Criteria) this;
        }

        public Criteria andOwn_balanceLessThan(String value) {
            addCriterion("own_balance <", value, "own_balance");
            return (Criteria) this;
        }

        public Criteria andOwn_balanceLessThanOrEqualTo(String value) {
            addCriterion("own_balance <=", value, "own_balance");
            return (Criteria) this;
        }

        public Criteria andOwn_balanceLike(String value) {
            addCriterion("own_balance like", value, "own_balance");
            return (Criteria) this;
        }

        public Criteria andOwn_balanceNotLike(String value) {
            addCriterion("own_balance not like", value, "own_balance");
            return (Criteria) this;
        }

        public Criteria andOwn_balanceIn(List<String> values) {
            addCriterion("own_balance in", values, "own_balance");
            return (Criteria) this;
        }

        public Criteria andOwn_balanceNotIn(List<String> values) {
            addCriterion("own_balance not in", values, "own_balance");
            return (Criteria) this;
        }

        public Criteria andOwn_balanceBetween(String value1, String value2) {
            addCriterion("own_balance between", value1, value2, "own_balance");
            return (Criteria) this;
        }

        public Criteria andOwn_balanceNotBetween(String value1, String value2) {
            addCriterion("own_balance not between", value1, value2, "own_balance");
            return (Criteria) this;
        }

        public Criteria andOther_balanceIsNull() {
            addCriterion("other_balance is null");
            return (Criteria) this;
        }

        public Criteria andOther_balanceIsNotNull() {
            addCriterion("other_balance is not null");
            return (Criteria) this;
        }

        public Criteria andOther_balanceEqualTo(String value) {
            addCriterion("other_balance =", value, "other_balance");
            return (Criteria) this;
        }

        public Criteria andOther_balanceNotEqualTo(String value) {
            addCriterion("other_balance <>", value, "other_balance");
            return (Criteria) this;
        }

        public Criteria andOther_balanceGreaterThan(String value) {
            addCriterion("other_balance >", value, "other_balance");
            return (Criteria) this;
        }

        public Criteria andOther_balanceGreaterThanOrEqualTo(String value) {
            addCriterion("other_balance >=", value, "other_balance");
            return (Criteria) this;
        }

        public Criteria andOther_balanceLessThan(String value) {
            addCriterion("other_balance <", value, "other_balance");
            return (Criteria) this;
        }

        public Criteria andOther_balanceLessThanOrEqualTo(String value) {
            addCriterion("other_balance <=", value, "other_balance");
            return (Criteria) this;
        }

        public Criteria andOther_balanceLike(String value) {
            addCriterion("other_balance like", value, "other_balance");
            return (Criteria) this;
        }

        public Criteria andOther_balanceNotLike(String value) {
            addCriterion("other_balance not like", value, "other_balance");
            return (Criteria) this;
        }

        public Criteria andOther_balanceIn(List<String> values) {
            addCriterion("other_balance in", values, "other_balance");
            return (Criteria) this;
        }

        public Criteria andOther_balanceNotIn(List<String> values) {
            addCriterion("other_balance not in", values, "other_balance");
            return (Criteria) this;
        }

        public Criteria andOther_balanceBetween(String value1, String value2) {
            addCriterion("other_balance between", value1, value2, "other_balance");
            return (Criteria) this;
        }

        public Criteria andOther_balanceNotBetween(String value1, String value2) {
            addCriterion("other_balance not between", value1, value2, "other_balance");
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
    }

    /**
     * eacc_account_balance_sum
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * eacc_account_balance_sum 2017-08-15
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