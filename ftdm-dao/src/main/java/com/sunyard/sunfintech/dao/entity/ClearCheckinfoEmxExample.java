package com.sunyard.sunfintech.dao.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ClearCheckinfoEmxExample {
    /**
     * clear_checkinfo_emx
     */
    protected String orderByClause;

    /**
     * clear_checkinfo_emx
     */
    protected boolean distinct;

    /**
     * clear_checkinfo_emx
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2017-07-04
     */
    public ClearCheckinfoEmxExample() {
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
     * clear_checkinfo_emx 2017-07-04
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andClear_dateIsNull() {
            addCriterion("clear_date is null");
            return (Criteria) this;
        }

        public Criteria andClear_dateIsNotNull() {
            addCriterion("clear_date is not null");
            return (Criteria) this;
        }

        public Criteria andClear_dateEqualTo(Date value) {
            addCriterionForJDBCDate("clear_date =", value, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateNotEqualTo(Date value) {
            addCriterionForJDBCDate("clear_date <>", value, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateGreaterThan(Date value) {
            addCriterionForJDBCDate("clear_date >", value, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("clear_date >=", value, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateLessThan(Date value) {
            addCriterionForJDBCDate("clear_date <", value, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("clear_date <=", value, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateIn(List<Date> values) {
            addCriterionForJDBCDate("clear_date in", values, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateNotIn(List<Date> values) {
            addCriterionForJDBCDate("clear_date not in", values, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("clear_date between", value1, value2, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("clear_date not between", value1, value2, "clear_date");
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

        public Criteria andGet_timeIsNull() {
            addCriterion("get_time is null");
            return (Criteria) this;
        }

        public Criteria andGet_timeIsNotNull() {
            addCriterion("get_time is not null");
            return (Criteria) this;
        }

        public Criteria andGet_timeEqualTo(Date value) {
            addCriterion("get_time =", value, "get_time");
            return (Criteria) this;
        }

        public Criteria andGet_timeNotEqualTo(Date value) {
            addCriterion("get_time <>", value, "get_time");
            return (Criteria) this;
        }

        public Criteria andGet_timeGreaterThan(Date value) {
            addCriterion("get_time >", value, "get_time");
            return (Criteria) this;
        }

        public Criteria andGet_timeGreaterThanOrEqualTo(Date value) {
            addCriterion("get_time >=", value, "get_time");
            return (Criteria) this;
        }

        public Criteria andGet_timeLessThan(Date value) {
            addCriterion("get_time <", value, "get_time");
            return (Criteria) this;
        }

        public Criteria andGet_timeLessThanOrEqualTo(Date value) {
            addCriterion("get_time <=", value, "get_time");
            return (Criteria) this;
        }

        public Criteria andGet_timeIn(List<Date> values) {
            addCriterion("get_time in", values, "get_time");
            return (Criteria) this;
        }

        public Criteria andGet_timeNotIn(List<Date> values) {
            addCriterion("get_time not in", values, "get_time");
            return (Criteria) this;
        }

        public Criteria andGet_timeBetween(Date value1, Date value2) {
            addCriterion("get_time between", value1, value2, "get_time");
            return (Criteria) this;
        }

        public Criteria andGet_timeNotBetween(Date value1, Date value2) {
            addCriterion("get_time not between", value1, value2, "get_time");
            return (Criteria) this;
        }

        public Criteria andSend_timeIsNull() {
            addCriterion("send_time is null");
            return (Criteria) this;
        }

        public Criteria andSend_timeIsNotNull() {
            addCriterion("send_time is not null");
            return (Criteria) this;
        }

        public Criteria andSend_timeEqualTo(String value) {
            addCriterion("send_time =", value, "send_time");
            return (Criteria) this;
        }

        public Criteria andSend_timeNotEqualTo(String value) {
            addCriterion("send_time <>", value, "send_time");
            return (Criteria) this;
        }

        public Criteria andSend_timeGreaterThan(String value) {
            addCriterion("send_time >", value, "send_time");
            return (Criteria) this;
        }

        public Criteria andSend_timeGreaterThanOrEqualTo(String value) {
            addCriterion("send_time >=", value, "send_time");
            return (Criteria) this;
        }

        public Criteria andSend_timeLessThan(String value) {
            addCriterion("send_time <", value, "send_time");
            return (Criteria) this;
        }

        public Criteria andSend_timeLessThanOrEqualTo(String value) {
            addCriterion("send_time <=", value, "send_time");
            return (Criteria) this;
        }

        public Criteria andSend_timeLike(String value) {
            addCriterion("send_time like", value, "send_time");
            return (Criteria) this;
        }

        public Criteria andSend_timeNotLike(String value) {
            addCriterion("send_time not like", value, "send_time");
            return (Criteria) this;
        }

        public Criteria andSend_timeIn(List<String> values) {
            addCriterion("send_time in", values, "send_time");
            return (Criteria) this;
        }

        public Criteria andSend_timeNotIn(List<String> values) {
            addCriterion("send_time not in", values, "send_time");
            return (Criteria) this;
        }

        public Criteria andSend_timeBetween(String value1, String value2) {
            addCriterion("send_time between", value1, value2, "send_time");
            return (Criteria) this;
        }

        public Criteria andSend_timeNotBetween(String value1, String value2) {
            addCriterion("send_time not between", value1, value2, "send_time");
            return (Criteria) this;
        }

        public Criteria andCheck_timeIsNull() {
            addCriterion("check_time is null");
            return (Criteria) this;
        }

        public Criteria andCheck_timeIsNotNull() {
            addCriterion("check_time is not null");
            return (Criteria) this;
        }

        public Criteria andCheck_timeEqualTo(String value) {
            addCriterion("check_time =", value, "check_time");
            return (Criteria) this;
        }

        public Criteria andCheck_timeNotEqualTo(String value) {
            addCriterion("check_time <>", value, "check_time");
            return (Criteria) this;
        }

        public Criteria andCheck_timeGreaterThan(String value) {
            addCriterion("check_time >", value, "check_time");
            return (Criteria) this;
        }

        public Criteria andCheck_timeGreaterThanOrEqualTo(String value) {
            addCriterion("check_time >=", value, "check_time");
            return (Criteria) this;
        }

        public Criteria andCheck_timeLessThan(String value) {
            addCriterion("check_time <", value, "check_time");
            return (Criteria) this;
        }

        public Criteria andCheck_timeLessThanOrEqualTo(String value) {
            addCriterion("check_time <=", value, "check_time");
            return (Criteria) this;
        }

        public Criteria andCheck_timeLike(String value) {
            addCriterion("check_time like", value, "check_time");
            return (Criteria) this;
        }

        public Criteria andCheck_timeNotLike(String value) {
            addCriterion("check_time not like", value, "check_time");
            return (Criteria) this;
        }

        public Criteria andCheck_timeIn(List<String> values) {
            addCriterion("check_time in", values, "check_time");
            return (Criteria) this;
        }

        public Criteria andCheck_timeNotIn(List<String> values) {
            addCriterion("check_time not in", values, "check_time");
            return (Criteria) this;
        }

        public Criteria andCheck_timeBetween(String value1, String value2) {
            addCriterion("check_time between", value1, value2, "check_time");
            return (Criteria) this;
        }

        public Criteria andCheck_timeNotBetween(String value1, String value2) {
            addCriterion("check_time not between", value1, value2, "check_time");
            return (Criteria) this;
        }

        public Criteria andE_intIsNull() {
            addCriterion("e_int is null");
            return (Criteria) this;
        }

        public Criteria andE_intIsNotNull() {
            addCriterion("e_int is not null");
            return (Criteria) this;
        }

        public Criteria andE_intEqualTo(Integer value) {
            addCriterion("e_int =", value, "e_int");
            return (Criteria) this;
        }

        public Criteria andE_intNotEqualTo(Integer value) {
            addCriterion("e_int <>", value, "e_int");
            return (Criteria) this;
        }

        public Criteria andE_intGreaterThan(Integer value) {
            addCriterion("e_int >", value, "e_int");
            return (Criteria) this;
        }

        public Criteria andE_intGreaterThanOrEqualTo(Integer value) {
            addCriterion("e_int >=", value, "e_int");
            return (Criteria) this;
        }

        public Criteria andE_intLessThan(Integer value) {
            addCriterion("e_int <", value, "e_int");
            return (Criteria) this;
        }

        public Criteria andE_intLessThanOrEqualTo(Integer value) {
            addCriterion("e_int <=", value, "e_int");
            return (Criteria) this;
        }

        public Criteria andE_intIn(List<Integer> values) {
            addCriterion("e_int in", values, "e_int");
            return (Criteria) this;
        }

        public Criteria andE_intNotIn(List<Integer> values) {
            addCriterion("e_int not in", values, "e_int");
            return (Criteria) this;
        }

        public Criteria andE_intBetween(Integer value1, Integer value2) {
            addCriterion("e_int between", value1, value2, "e_int");
            return (Criteria) this;
        }

        public Criteria andE_intNotBetween(Integer value1, Integer value2) {
            addCriterion("e_int not between", value1, value2, "e_int");
            return (Criteria) this;
        }

        public Criteria andE_matIsNull() {
            addCriterion("e_mat is null");
            return (Criteria) this;
        }

        public Criteria andE_matIsNotNull() {
            addCriterion("e_mat is not null");
            return (Criteria) this;
        }

        public Criteria andE_matEqualTo(BigDecimal value) {
            addCriterion("e_mat =", value, "e_mat");
            return (Criteria) this;
        }

        public Criteria andE_matNotEqualTo(BigDecimal value) {
            addCriterion("e_mat <>", value, "e_mat");
            return (Criteria) this;
        }

        public Criteria andE_matGreaterThan(BigDecimal value) {
            addCriterion("e_mat >", value, "e_mat");
            return (Criteria) this;
        }

        public Criteria andE_matGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("e_mat >=", value, "e_mat");
            return (Criteria) this;
        }

        public Criteria andE_matLessThan(BigDecimal value) {
            addCriterion("e_mat <", value, "e_mat");
            return (Criteria) this;
        }

        public Criteria andE_matLessThanOrEqualTo(BigDecimal value) {
            addCriterion("e_mat <=", value, "e_mat");
            return (Criteria) this;
        }

        public Criteria andE_matIn(List<BigDecimal> values) {
            addCriterion("e_mat in", values, "e_mat");
            return (Criteria) this;
        }

        public Criteria andE_matNotIn(List<BigDecimal> values) {
            addCriterion("e_mat not in", values, "e_mat");
            return (Criteria) this;
        }

        public Criteria andE_matBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("e_mat between", value1, value2, "e_mat");
            return (Criteria) this;
        }

        public Criteria andE_matNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("e_mat not between", value1, value2, "e_mat");
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

        public Criteria andPayment_plat_noIsNull() {
            addCriterion("payment_plat_no is null");
            return (Criteria) this;
        }

        public Criteria andPayment_plat_noIsNotNull() {
            addCriterion("payment_plat_no is not null");
            return (Criteria) this;
        }

        public Criteria andPayment_plat_noEqualTo(String value) {
            addCriterion("payment_plat_no =", value, "payment_plat_no");
            return (Criteria) this;
        }

        public Criteria andPayment_plat_noNotEqualTo(String value) {
            addCriterion("payment_plat_no <>", value, "payment_plat_no");
            return (Criteria) this;
        }

        public Criteria andPayment_plat_noGreaterThan(String value) {
            addCriterion("payment_plat_no >", value, "payment_plat_no");
            return (Criteria) this;
        }

        public Criteria andPayment_plat_noGreaterThanOrEqualTo(String value) {
            addCriterion("payment_plat_no >=", value, "payment_plat_no");
            return (Criteria) this;
        }

        public Criteria andPayment_plat_noLessThan(String value) {
            addCriterion("payment_plat_no <", value, "payment_plat_no");
            return (Criteria) this;
        }

        public Criteria andPayment_plat_noLessThanOrEqualTo(String value) {
            addCriterion("payment_plat_no <=", value, "payment_plat_no");
            return (Criteria) this;
        }

        public Criteria andPayment_plat_noLike(String value) {
            addCriterion("payment_plat_no like", value, "payment_plat_no");
            return (Criteria) this;
        }

        public Criteria andPayment_plat_noNotLike(String value) {
            addCriterion("payment_plat_no not like", value, "payment_plat_no");
            return (Criteria) this;
        }

        public Criteria andPayment_plat_noIn(List<String> values) {
            addCriterion("payment_plat_no in", values, "payment_plat_no");
            return (Criteria) this;
        }

        public Criteria andPayment_plat_noNotIn(List<String> values) {
            addCriterion("payment_plat_no not in", values, "payment_plat_no");
            return (Criteria) this;
        }

        public Criteria andPayment_plat_noBetween(String value1, String value2) {
            addCriterion("payment_plat_no between", value1, value2, "payment_plat_no");
            return (Criteria) this;
        }

        public Criteria andPayment_plat_noNotBetween(String value1, String value2) {
            addCriterion("payment_plat_no not between", value1, value2, "payment_plat_no");
            return (Criteria) this;
        }
    }

    /**
     * clear_checkinfo_emx
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * clear_checkinfo_emx 2017-07-04
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