package com.sunyard.sunfintech.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EaccClearSubjectInfoExample {
    /**
     * eacc_clear_subject_info
     */
    protected String orderByClause;

    /**
     * eacc_clear_subject_info
     */
    protected boolean distinct;

    /**
     * eacc_clear_subject_info
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2017-08-09
     */
    public EaccClearSubjectInfoExample() {
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
     * eacc_clear_subject_info 2017-08-09
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

        public Criteria andT_balanceIsNull() {
            addCriterion("t_balance is null");
            return (Criteria) this;
        }

        public Criteria andT_balanceIsNotNull() {
            addCriterion("t_balance is not null");
            return (Criteria) this;
        }

        public Criteria andT_balanceEqualTo(String value) {
            addCriterion("t_balance =", value, "t_balance");
            return (Criteria) this;
        }

        public Criteria andT_balanceNotEqualTo(String value) {
            addCriterion("t_balance <>", value, "t_balance");
            return (Criteria) this;
        }

        public Criteria andT_balanceGreaterThan(String value) {
            addCriterion("t_balance >", value, "t_balance");
            return (Criteria) this;
        }

        public Criteria andT_balanceGreaterThanOrEqualTo(String value) {
            addCriterion("t_balance >=", value, "t_balance");
            return (Criteria) this;
        }

        public Criteria andT_balanceLessThan(String value) {
            addCriterion("t_balance <", value, "t_balance");
            return (Criteria) this;
        }

        public Criteria andT_balanceLessThanOrEqualTo(String value) {
            addCriterion("t_balance <=", value, "t_balance");
            return (Criteria) this;
        }

        public Criteria andT_balanceLike(String value) {
            addCriterion("t_balance like", value, "t_balance");
            return (Criteria) this;
        }

        public Criteria andT_balanceNotLike(String value) {
            addCriterion("t_balance not like", value, "t_balance");
            return (Criteria) this;
        }

        public Criteria andT_balanceIn(List<String> values) {
            addCriterion("t_balance in", values, "t_balance");
            return (Criteria) this;
        }

        public Criteria andT_balanceNotIn(List<String> values) {
            addCriterion("t_balance not in", values, "t_balance");
            return (Criteria) this;
        }

        public Criteria andT_balanceBetween(String value1, String value2) {
            addCriterion("t_balance between", value1, value2, "t_balance");
            return (Criteria) this;
        }

        public Criteria andT_balanceNotBetween(String value1, String value2) {
            addCriterion("t_balance not between", value1, value2, "t_balance");
            return (Criteria) this;
        }

        public Criteria andN_balanceIsNull() {
            addCriterion("n_balance is null");
            return (Criteria) this;
        }

        public Criteria andN_balanceIsNotNull() {
            addCriterion("n_balance is not null");
            return (Criteria) this;
        }

        public Criteria andN_balanceEqualTo(String value) {
            addCriterion("n_balance =", value, "n_balance");
            return (Criteria) this;
        }

        public Criteria andN_balanceNotEqualTo(String value) {
            addCriterion("n_balance <>", value, "n_balance");
            return (Criteria) this;
        }

        public Criteria andN_balanceGreaterThan(String value) {
            addCriterion("n_balance >", value, "n_balance");
            return (Criteria) this;
        }

        public Criteria andN_balanceGreaterThanOrEqualTo(String value) {
            addCriterion("n_balance >=", value, "n_balance");
            return (Criteria) this;
        }

        public Criteria andN_balanceLessThan(String value) {
            addCriterion("n_balance <", value, "n_balance");
            return (Criteria) this;
        }

        public Criteria andN_balanceLessThanOrEqualTo(String value) {
            addCriterion("n_balance <=", value, "n_balance");
            return (Criteria) this;
        }

        public Criteria andN_balanceLike(String value) {
            addCriterion("n_balance like", value, "n_balance");
            return (Criteria) this;
        }

        public Criteria andN_balanceNotLike(String value) {
            addCriterion("n_balance not like", value, "n_balance");
            return (Criteria) this;
        }

        public Criteria andN_balanceIn(List<String> values) {
            addCriterion("n_balance in", values, "n_balance");
            return (Criteria) this;
        }

        public Criteria andN_balanceNotIn(List<String> values) {
            addCriterion("n_balance not in", values, "n_balance");
            return (Criteria) this;
        }

        public Criteria andN_balanceBetween(String value1, String value2) {
            addCriterion("n_balance between", value1, value2, "n_balance");
            return (Criteria) this;
        }

        public Criteria andN_balanceNotBetween(String value1, String value2) {
            addCriterion("n_balance not between", value1, value2, "n_balance");
            return (Criteria) this;
        }

        public Criteria andF_balanceIsNull() {
            addCriterion("f_balance is null");
            return (Criteria) this;
        }

        public Criteria andF_balanceIsNotNull() {
            addCriterion("f_balance is not null");
            return (Criteria) this;
        }

        public Criteria andF_balanceEqualTo(String value) {
            addCriterion("f_balance =", value, "f_balance");
            return (Criteria) this;
        }

        public Criteria andF_balanceNotEqualTo(String value) {
            addCriterion("f_balance <>", value, "f_balance");
            return (Criteria) this;
        }

        public Criteria andF_balanceGreaterThan(String value) {
            addCriterion("f_balance >", value, "f_balance");
            return (Criteria) this;
        }

        public Criteria andF_balanceGreaterThanOrEqualTo(String value) {
            addCriterion("f_balance >=", value, "f_balance");
            return (Criteria) this;
        }

        public Criteria andF_balanceLessThan(String value) {
            addCriterion("f_balance <", value, "f_balance");
            return (Criteria) this;
        }

        public Criteria andF_balanceLessThanOrEqualTo(String value) {
            addCriterion("f_balance <=", value, "f_balance");
            return (Criteria) this;
        }

        public Criteria andF_balanceLike(String value) {
            addCriterion("f_balance like", value, "f_balance");
            return (Criteria) this;
        }

        public Criteria andF_balanceNotLike(String value) {
            addCriterion("f_balance not like", value, "f_balance");
            return (Criteria) this;
        }

        public Criteria andF_balanceIn(List<String> values) {
            addCriterion("f_balance in", values, "f_balance");
            return (Criteria) this;
        }

        public Criteria andF_balanceNotIn(List<String> values) {
            addCriterion("f_balance not in", values, "f_balance");
            return (Criteria) this;
        }

        public Criteria andF_balanceBetween(String value1, String value2) {
            addCriterion("f_balance between", value1, value2, "f_balance");
            return (Criteria) this;
        }

        public Criteria andF_balanceNotBetween(String value1, String value2) {
            addCriterion("f_balance not between", value1, value2, "f_balance");
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
     * eacc_clear_subject_info
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * eacc_clear_subject_info 2017-08-09
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