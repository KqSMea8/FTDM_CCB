package com.sunyard.sunfintech.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ClearCheckinfoExample {
    /**
     * clear_checkinfo
     */
    protected String orderByClause;

    /**
     * clear_checkinfo
     */
    protected boolean distinct;

    /**
     * clear_checkinfo
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2017-07-04
     */
    public ClearCheckinfoExample() {
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
     * clear_checkinfo 2017-07-04
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

        public Criteria andLast_dateIsNull() {
            addCriterion("last_date is null");
            return (Criteria) this;
        }

        public Criteria andLast_dateIsNotNull() {
            addCriterion("last_date is not null");
            return (Criteria) this;
        }

        public Criteria andLast_dateEqualTo(Date value) {
            addCriterionForJDBCDate("last_date =", value, "last_date");
            return (Criteria) this;
        }

        public Criteria andLast_dateNotEqualTo(Date value) {
            addCriterionForJDBCDate("last_date <>", value, "last_date");
            return (Criteria) this;
        }

        public Criteria andLast_dateGreaterThan(Date value) {
            addCriterionForJDBCDate("last_date >", value, "last_date");
            return (Criteria) this;
        }

        public Criteria andLast_dateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("last_date >=", value, "last_date");
            return (Criteria) this;
        }

        public Criteria andLast_dateLessThan(Date value) {
            addCriterionForJDBCDate("last_date <", value, "last_date");
            return (Criteria) this;
        }

        public Criteria andLast_dateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("last_date <=", value, "last_date");
            return (Criteria) this;
        }

        public Criteria andLast_dateIn(List<Date> values) {
            addCriterionForJDBCDate("last_date in", values, "last_date");
            return (Criteria) this;
        }

        public Criteria andLast_dateNotIn(List<Date> values) {
            addCriterionForJDBCDate("last_date not in", values, "last_date");
            return (Criteria) this;
        }

        public Criteria andLast_dateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("last_date between", value1, value2, "last_date");
            return (Criteria) this;
        }

        public Criteria andLast_dateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("last_date not between", value1, value2, "last_date");
            return (Criteria) this;
        }

        public Criteria andStart_timeIsNull() {
            addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStart_timeIsNotNull() {
            addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStart_timeEqualTo(Date value) {
            addCriterion("start_time =", value, "start_time");
            return (Criteria) this;
        }

        public Criteria andStart_timeNotEqualTo(Date value) {
            addCriterion("start_time <>", value, "start_time");
            return (Criteria) this;
        }

        public Criteria andStart_timeGreaterThan(Date value) {
            addCriterion("start_time >", value, "start_time");
            return (Criteria) this;
        }

        public Criteria andStart_timeGreaterThanOrEqualTo(Date value) {
            addCriterion("start_time >=", value, "start_time");
            return (Criteria) this;
        }

        public Criteria andStart_timeLessThan(Date value) {
            addCriterion("start_time <", value, "start_time");
            return (Criteria) this;
        }

        public Criteria andStart_timeLessThanOrEqualTo(Date value) {
            addCriterion("start_time <=", value, "start_time");
            return (Criteria) this;
        }

        public Criteria andStart_timeIn(List<Date> values) {
            addCriterion("start_time in", values, "start_time");
            return (Criteria) this;
        }

        public Criteria andStart_timeNotIn(List<Date> values) {
            addCriterion("start_time not in", values, "start_time");
            return (Criteria) this;
        }

        public Criteria andStart_timeBetween(Date value1, Date value2) {
            addCriterion("start_time between", value1, value2, "start_time");
            return (Criteria) this;
        }

        public Criteria andStart_timeNotBetween(Date value1, Date value2) {
            addCriterion("start_time not between", value1, value2, "start_time");
            return (Criteria) this;
        }

        public Criteria andEnd_timeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEnd_timeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEnd_timeEqualTo(Date value) {
            addCriterion("end_time =", value, "end_time");
            return (Criteria) this;
        }

        public Criteria andEnd_timeNotEqualTo(Date value) {
            addCriterion("end_time <>", value, "end_time");
            return (Criteria) this;
        }

        public Criteria andEnd_timeGreaterThan(Date value) {
            addCriterion("end_time >", value, "end_time");
            return (Criteria) this;
        }

        public Criteria andEnd_timeGreaterThanOrEqualTo(Date value) {
            addCriterion("end_time >=", value, "end_time");
            return (Criteria) this;
        }

        public Criteria andEnd_timeLessThan(Date value) {
            addCriterion("end_time <", value, "end_time");
            return (Criteria) this;
        }

        public Criteria andEnd_timeLessThanOrEqualTo(Date value) {
            addCriterion("end_time <=", value, "end_time");
            return (Criteria) this;
        }

        public Criteria andEnd_timeIn(List<Date> values) {
            addCriterion("end_time in", values, "end_time");
            return (Criteria) this;
        }

        public Criteria andEnd_timeNotIn(List<Date> values) {
            addCriterion("end_time not in", values, "end_time");
            return (Criteria) this;
        }

        public Criteria andEnd_timeBetween(Date value1, Date value2) {
            addCriterion("end_time between", value1, value2, "end_time");
            return (Criteria) this;
        }

        public Criteria andEnd_timeNotBetween(Date value1, Date value2) {
            addCriterion("end_time not between", value1, value2, "end_time");
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
    }

    /**
     * clear_checkinfo
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * clear_checkinfo 2017-07-04
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