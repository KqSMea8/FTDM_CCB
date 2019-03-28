package com.sunyard.sunfintech.dao.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BatchRepayDetailExample {
    /**
     * batch_repay_detail
     */
    protected String orderByClause;

    /**
     * batch_repay_detail
     */
    protected boolean distinct;

    /**
     * batch_repay_detail
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2018-09-20
     */
    public BatchRepayDetailExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *
     * @mbggenerated 2018-09-20
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *
     * @mbggenerated 2018-09-20
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *
     * @mbggenerated 2018-09-20
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *
     * @mbggenerated 2018-09-20
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *
     * @mbggenerated 2018-09-20
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *
     * @mbggenerated 2018-09-20
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *
     * @mbggenerated 2018-09-20
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *
     * @mbggenerated 2018-09-20
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
     * @mbggenerated 2018-09-20
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *
     * @mbggenerated 2018-09-20
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * batch_repay_detail 2018-09-20
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

        public Criteria andOccur_balanceIsNull() {
            addCriterion("occur_balance is null");
            return (Criteria) this;
        }

        public Criteria andOccur_balanceIsNotNull() {
            addCriterion("occur_balance is not null");
            return (Criteria) this;
        }

        public Criteria andOccur_balanceEqualTo(BigDecimal value) {
            addCriterion("occur_balance =", value, "occur_balance");
            return (Criteria) this;
        }

        public Criteria andOccur_balanceNotEqualTo(BigDecimal value) {
            addCriterion("occur_balance <>", value, "occur_balance");
            return (Criteria) this;
        }

        public Criteria andOccur_balanceGreaterThan(BigDecimal value) {
            addCriterion("occur_balance >", value, "occur_balance");
            return (Criteria) this;
        }

        public Criteria andOccur_balanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("occur_balance >=", value, "occur_balance");
            return (Criteria) this;
        }

        public Criteria andOccur_balanceLessThan(BigDecimal value) {
            addCriterion("occur_balance <", value, "occur_balance");
            return (Criteria) this;
        }

        public Criteria andOccur_balanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("occur_balance <=", value, "occur_balance");
            return (Criteria) this;
        }

        public Criteria andOccur_balanceIn(List<BigDecimal> values) {
            addCriterion("occur_balance in", values, "occur_balance");
            return (Criteria) this;
        }

        public Criteria andOccur_balanceNotIn(List<BigDecimal> values) {
            addCriterion("occur_balance not in", values, "occur_balance");
            return (Criteria) this;
        }

        public Criteria andOccur_balanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("occur_balance between", value1, value2, "occur_balance");
            return (Criteria) this;
        }

        public Criteria andOccur_balanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("occur_balance not between", value1, value2, "occur_balance");
            return (Criteria) this;
        }

        public Criteria andSummaryIsNull() {
            addCriterion("summary is null");
            return (Criteria) this;
        }

        public Criteria andSummaryIsNotNull() {
            addCriterion("summary is not null");
            return (Criteria) this;
        }

        public Criteria andSummaryEqualTo(String value) {
            addCriterion("summary =", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotEqualTo(String value) {
            addCriterion("summary <>", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryGreaterThan(String value) {
            addCriterion("summary >", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryGreaterThanOrEqualTo(String value) {
            addCriterion("summary >=", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryLessThan(String value) {
            addCriterion("summary <", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryLessThanOrEqualTo(String value) {
            addCriterion("summary <=", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryLike(String value) {
            addCriterion("summary like", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotLike(String value) {
            addCriterion("summary not like", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryIn(List<String> values) {
            addCriterion("summary in", values, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotIn(List<String> values) {
            addCriterion("summary not in", values, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryBetween(String value1, String value2) {
            addCriterion("summary between", value1, value2, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotBetween(String value1, String value2) {
            addCriterion("summary not between", value1, value2, "summary");
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
     * batch_repay_detail
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * batch_repay_detail 2018-09-20
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