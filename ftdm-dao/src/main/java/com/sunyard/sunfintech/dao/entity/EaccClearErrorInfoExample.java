package com.sunyard.sunfintech.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EaccClearErrorInfoExample {
    /**
     * eacc_clear_error_info
     */
    protected String orderByClause;

    /**
     * eacc_clear_error_info
     */
    protected boolean distinct;

    /**
     * eacc_clear_error_info
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2017-08-09
     */
    public EaccClearErrorInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *
     * @mbggenerated 2017-08-09
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *
     * @mbggenerated 2017-08-09
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *
     * @mbggenerated 2017-08-09
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *
     * @mbggenerated 2017-08-09
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *
     * @mbggenerated 2017-08-09
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *
     * @mbggenerated 2017-08-09
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *
     * @mbggenerated 2017-08-09
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *
     * @mbggenerated 2017-08-09
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
     * @mbggenerated 2017-08-09
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *
     * @mbggenerated 2017-08-09
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * eacc_clear_error_info 2017-08-09
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

        public Criteria andEaccountIsNull() {
            addCriterion("eaccount is null");
            return (Criteria) this;
        }

        public Criteria andEaccountIsNotNull() {
            addCriterion("eaccount is not null");
            return (Criteria) this;
        }

        public Criteria andEaccountEqualTo(String value) {
            addCriterion("eaccount =", value, "eaccount");
            return (Criteria) this;
        }

        public Criteria andEaccountNotEqualTo(String value) {
            addCriterion("eaccount <>", value, "eaccount");
            return (Criteria) this;
        }

        public Criteria andEaccountGreaterThan(String value) {
            addCriterion("eaccount >", value, "eaccount");
            return (Criteria) this;
        }

        public Criteria andEaccountGreaterThanOrEqualTo(String value) {
            addCriterion("eaccount >=", value, "eaccount");
            return (Criteria) this;
        }

        public Criteria andEaccountLessThan(String value) {
            addCriterion("eaccount <", value, "eaccount");
            return (Criteria) this;
        }

        public Criteria andEaccountLessThanOrEqualTo(String value) {
            addCriterion("eaccount <=", value, "eaccount");
            return (Criteria) this;
        }

        public Criteria andEaccountLike(String value) {
            addCriterion("eaccount like", value, "eaccount");
            return (Criteria) this;
        }

        public Criteria andEaccountNotLike(String value) {
            addCriterion("eaccount not like", value, "eaccount");
            return (Criteria) this;
        }

        public Criteria andEaccountIn(List<String> values) {
            addCriterion("eaccount in", values, "eaccount");
            return (Criteria) this;
        }

        public Criteria andEaccountNotIn(List<String> values) {
            addCriterion("eaccount not in", values, "eaccount");
            return (Criteria) this;
        }

        public Criteria andEaccountBetween(String value1, String value2) {
            addCriterion("eaccount between", value1, value2, "eaccount");
            return (Criteria) this;
        }

        public Criteria andEaccountNotBetween(String value1, String value2) {
            addCriterion("eaccount not between", value1, value2, "eaccount");
            return (Criteria) this;
        }

        public Criteria andOwn_AmtIsNull() {
            addCriterion("own_Amt is null");
            return (Criteria) this;
        }

        public Criteria andOwn_AmtIsNotNull() {
            addCriterion("own_Amt is not null");
            return (Criteria) this;
        }

        public Criteria andOwn_AmtEqualTo(String value) {
            addCriterion("own_Amt =", value, "own_Amt");
            return (Criteria) this;
        }

        public Criteria andOwn_AmtNotEqualTo(String value) {
            addCriterion("own_Amt <>", value, "own_Amt");
            return (Criteria) this;
        }

        public Criteria andOwn_AmtGreaterThan(String value) {
            addCriterion("own_Amt >", value, "own_Amt");
            return (Criteria) this;
        }

        public Criteria andOwn_AmtGreaterThanOrEqualTo(String value) {
            addCriterion("own_Amt >=", value, "own_Amt");
            return (Criteria) this;
        }

        public Criteria andOwn_AmtLessThan(String value) {
            addCriterion("own_Amt <", value, "own_Amt");
            return (Criteria) this;
        }

        public Criteria andOwn_AmtLessThanOrEqualTo(String value) {
            addCriterion("own_Amt <=", value, "own_Amt");
            return (Criteria) this;
        }

        public Criteria andOwn_AmtLike(String value) {
            addCriterion("own_Amt like", value, "own_Amt");
            return (Criteria) this;
        }

        public Criteria andOwn_AmtNotLike(String value) {
            addCriterion("own_Amt not like", value, "own_Amt");
            return (Criteria) this;
        }

        public Criteria andOwn_AmtIn(List<String> values) {
            addCriterion("own_Amt in", values, "own_Amt");
            return (Criteria) this;
        }

        public Criteria andOwn_AmtNotIn(List<String> values) {
            addCriterion("own_Amt not in", values, "own_Amt");
            return (Criteria) this;
        }

        public Criteria andOwn_AmtBetween(String value1, String value2) {
            addCriterion("own_Amt between", value1, value2, "own_Amt");
            return (Criteria) this;
        }

        public Criteria andOwn_AmtNotBetween(String value1, String value2) {
            addCriterion("own_Amt not between", value1, value2, "own_Amt");
            return (Criteria) this;
        }

        public Criteria andBank_AmtIsNull() {
            addCriterion("bank_Amt is null");
            return (Criteria) this;
        }

        public Criteria andBank_AmtIsNotNull() {
            addCriterion("bank_Amt is not null");
            return (Criteria) this;
        }

        public Criteria andBank_AmtEqualTo(String value) {
            addCriterion("bank_Amt =", value, "bank_Amt");
            return (Criteria) this;
        }

        public Criteria andBank_AmtNotEqualTo(String value) {
            addCriterion("bank_Amt <>", value, "bank_Amt");
            return (Criteria) this;
        }

        public Criteria andBank_AmtGreaterThan(String value) {
            addCriterion("bank_Amt >", value, "bank_Amt");
            return (Criteria) this;
        }

        public Criteria andBank_AmtGreaterThanOrEqualTo(String value) {
            addCriterion("bank_Amt >=", value, "bank_Amt");
            return (Criteria) this;
        }

        public Criteria andBank_AmtLessThan(String value) {
            addCriterion("bank_Amt <", value, "bank_Amt");
            return (Criteria) this;
        }

        public Criteria andBank_AmtLessThanOrEqualTo(String value) {
            addCriterion("bank_Amt <=", value, "bank_Amt");
            return (Criteria) this;
        }

        public Criteria andBank_AmtLike(String value) {
            addCriterion("bank_Amt like", value, "bank_Amt");
            return (Criteria) this;
        }

        public Criteria andBank_AmtNotLike(String value) {
            addCriterion("bank_Amt not like", value, "bank_Amt");
            return (Criteria) this;
        }

        public Criteria andBank_AmtIn(List<String> values) {
            addCriterion("bank_Amt in", values, "bank_Amt");
            return (Criteria) this;
        }

        public Criteria andBank_AmtNotIn(List<String> values) {
            addCriterion("bank_Amt not in", values, "bank_Amt");
            return (Criteria) this;
        }

        public Criteria andBank_AmtBetween(String value1, String value2) {
            addCriterion("bank_Amt between", value1, value2, "bank_Amt");
            return (Criteria) this;
        }

        public Criteria andBank_AmtNotBetween(String value1, String value2) {
            addCriterion("bank_Amt not between", value1, value2, "bank_Amt");
            return (Criteria) this;
        }

        public Criteria andError_msgIsNull() {
            addCriterion("error_msg is null");
            return (Criteria) this;
        }

        public Criteria andError_msgIsNotNull() {
            addCriterion("error_msg is not null");
            return (Criteria) this;
        }

        public Criteria andError_msgEqualTo(String value) {
            addCriterion("error_msg =", value, "error_msg");
            return (Criteria) this;
        }

        public Criteria andError_msgNotEqualTo(String value) {
            addCriterion("error_msg <>", value, "error_msg");
            return (Criteria) this;
        }

        public Criteria andError_msgGreaterThan(String value) {
            addCriterion("error_msg >", value, "error_msg");
            return (Criteria) this;
        }

        public Criteria andError_msgGreaterThanOrEqualTo(String value) {
            addCriterion("error_msg >=", value, "error_msg");
            return (Criteria) this;
        }

        public Criteria andError_msgLessThan(String value) {
            addCriterion("error_msg <", value, "error_msg");
            return (Criteria) this;
        }

        public Criteria andError_msgLessThanOrEqualTo(String value) {
            addCriterion("error_msg <=", value, "error_msg");
            return (Criteria) this;
        }

        public Criteria andError_msgLike(String value) {
            addCriterion("error_msg like", value, "error_msg");
            return (Criteria) this;
        }

        public Criteria andError_msgNotLike(String value) {
            addCriterion("error_msg not like", value, "error_msg");
            return (Criteria) this;
        }

        public Criteria andError_msgIn(List<String> values) {
            addCriterion("error_msg in", values, "error_msg");
            return (Criteria) this;
        }

        public Criteria andError_msgNotIn(List<String> values) {
            addCriterion("error_msg not in", values, "error_msg");
            return (Criteria) this;
        }

        public Criteria andError_msgBetween(String value1, String value2) {
            addCriterion("error_msg between", value1, value2, "error_msg");
            return (Criteria) this;
        }

        public Criteria andError_msgNotBetween(String value1, String value2) {
            addCriterion("error_msg not between", value1, value2, "error_msg");
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
     * eacc_clear_error_info
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * eacc_clear_error_info 2017-08-09
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