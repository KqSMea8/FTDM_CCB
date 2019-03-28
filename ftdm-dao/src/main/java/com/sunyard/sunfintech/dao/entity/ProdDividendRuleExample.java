package com.sunyard.sunfintech.dao.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProdDividendRuleExample {
    /**
     * prod_dividend_rule
     */
    protected String orderByClause;

    /**
     * prod_dividend_rule
     */
    protected boolean distinct;

    /**
     * prod_dividend_rule
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2017-06-01
     */
    public ProdDividendRuleExample() {
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
     * prod_dividend_rule 2017-06-01
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

        public Criteria andPayout_plat_noIsNull() {
            addCriterion("payout_plat_no is null");
            return (Criteria) this;
        }

        public Criteria andPayout_plat_noIsNotNull() {
            addCriterion("payout_plat_no is not null");
            return (Criteria) this;
        }

        public Criteria andPayout_plat_noEqualTo(String value) {
            addCriterion("payout_plat_no =", value, "payout_plat_no");
            return (Criteria) this;
        }

        public Criteria andPayout_plat_noNotEqualTo(String value) {
            addCriterion("payout_plat_no <>", value, "payout_plat_no");
            return (Criteria) this;
        }

        public Criteria andPayout_plat_noGreaterThan(String value) {
            addCriterion("payout_plat_no >", value, "payout_plat_no");
            return (Criteria) this;
        }

        public Criteria andPayout_plat_noGreaterThanOrEqualTo(String value) {
            addCriterion("payout_plat_no >=", value, "payout_plat_no");
            return (Criteria) this;
        }

        public Criteria andPayout_plat_noLessThan(String value) {
            addCriterion("payout_plat_no <", value, "payout_plat_no");
            return (Criteria) this;
        }

        public Criteria andPayout_plat_noLessThanOrEqualTo(String value) {
            addCriterion("payout_plat_no <=", value, "payout_plat_no");
            return (Criteria) this;
        }

        public Criteria andPayout_plat_noLike(String value) {
            addCriterion("payout_plat_no like", value, "payout_plat_no");
            return (Criteria) this;
        }

        public Criteria andPayout_plat_noNotLike(String value) {
            addCriterion("payout_plat_no not like", value, "payout_plat_no");
            return (Criteria) this;
        }

        public Criteria andPayout_plat_noIn(List<String> values) {
            addCriterion("payout_plat_no in", values, "payout_plat_no");
            return (Criteria) this;
        }

        public Criteria andPayout_plat_noNotIn(List<String> values) {
            addCriterion("payout_plat_no not in", values, "payout_plat_no");
            return (Criteria) this;
        }

        public Criteria andPayout_plat_noBetween(String value1, String value2) {
            addCriterion("payout_plat_no between", value1, value2, "payout_plat_no");
            return (Criteria) this;
        }

        public Criteria andPayout_plat_noNotBetween(String value1, String value2) {
            addCriterion("payout_plat_no not between", value1, value2, "payout_plat_no");
            return (Criteria) this;
        }

        public Criteria andPayout_plat_accountIsNull() {
            addCriterion("payout_plat_account is null");
            return (Criteria) this;
        }

        public Criteria andPayout_plat_accountIsNotNull() {
            addCriterion("payout_plat_account is not null");
            return (Criteria) this;
        }

        public Criteria andPayout_plat_accountEqualTo(String value) {
            addCriterion("payout_plat_account =", value, "payout_plat_account");
            return (Criteria) this;
        }

        public Criteria andPayout_plat_accountNotEqualTo(String value) {
            addCriterion("payout_plat_account <>", value, "payout_plat_account");
            return (Criteria) this;
        }

        public Criteria andPayout_plat_accountGreaterThan(String value) {
            addCriterion("payout_plat_account >", value, "payout_plat_account");
            return (Criteria) this;
        }

        public Criteria andPayout_plat_accountGreaterThanOrEqualTo(String value) {
            addCriterion("payout_plat_account >=", value, "payout_plat_account");
            return (Criteria) this;
        }

        public Criteria andPayout_plat_accountLessThan(String value) {
            addCriterion("payout_plat_account <", value, "payout_plat_account");
            return (Criteria) this;
        }

        public Criteria andPayout_plat_accountLessThanOrEqualTo(String value) {
            addCriterion("payout_plat_account <=", value, "payout_plat_account");
            return (Criteria) this;
        }

        public Criteria andPayout_plat_accountLike(String value) {
            addCriterion("payout_plat_account like", value, "payout_plat_account");
            return (Criteria) this;
        }

        public Criteria andPayout_plat_accountNotLike(String value) {
            addCriterion("payout_plat_account not like", value, "payout_plat_account");
            return (Criteria) this;
        }

        public Criteria andPayout_plat_accountIn(List<String> values) {
            addCriterion("payout_plat_account in", values, "payout_plat_account");
            return (Criteria) this;
        }

        public Criteria andPayout_plat_accountNotIn(List<String> values) {
            addCriterion("payout_plat_account not in", values, "payout_plat_account");
            return (Criteria) this;
        }

        public Criteria andPayout_plat_accountBetween(String value1, String value2) {
            addCriterion("payout_plat_account between", value1, value2, "payout_plat_account");
            return (Criteria) this;
        }

        public Criteria andPayout_plat_accountNotBetween(String value1, String value2) {
            addCriterion("payout_plat_account not between", value1, value2, "payout_plat_account");
            return (Criteria) this;
        }

        public Criteria andPayout_ratioIsNull() {
            addCriterion("payout_ratio is null");
            return (Criteria) this;
        }

        public Criteria andPayout_ratioIsNotNull() {
            addCriterion("payout_ratio is not null");
            return (Criteria) this;
        }

        public Criteria andPayout_ratioEqualTo(BigDecimal value) {
            addCriterion("payout_ratio =", value, "payout_ratio");
            return (Criteria) this;
        }

        public Criteria andPayout_ratioNotEqualTo(BigDecimal value) {
            addCriterion("payout_ratio <>", value, "payout_ratio");
            return (Criteria) this;
        }

        public Criteria andPayout_ratioGreaterThan(BigDecimal value) {
            addCriterion("payout_ratio >", value, "payout_ratio");
            return (Criteria) this;
        }

        public Criteria andPayout_ratioGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("payout_ratio >=", value, "payout_ratio");
            return (Criteria) this;
        }

        public Criteria andPayout_ratioLessThan(BigDecimal value) {
            addCriterion("payout_ratio <", value, "payout_ratio");
            return (Criteria) this;
        }

        public Criteria andPayout_ratioLessThanOrEqualTo(BigDecimal value) {
            addCriterion("payout_ratio <=", value, "payout_ratio");
            return (Criteria) this;
        }

        public Criteria andPayout_ratioIn(List<BigDecimal> values) {
            addCriterion("payout_ratio in", values, "payout_ratio");
            return (Criteria) this;
        }

        public Criteria andPayout_ratioNotIn(List<BigDecimal> values) {
            addCriterion("payout_ratio not in", values, "payout_ratio");
            return (Criteria) this;
        }

        public Criteria andPayout_ratioBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("payout_ratio between", value1, value2, "payout_ratio");
            return (Criteria) this;
        }

        public Criteria andPayout_ratioNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("payout_ratio not between", value1, value2, "payout_ratio");
            return (Criteria) this;
        }

        public Criteria andPayout_amtIsNull() {
            addCriterion("payout_amt is null");
            return (Criteria) this;
        }

        public Criteria andPayout_amtIsNotNull() {
            addCriterion("payout_amt is not null");
            return (Criteria) this;
        }

        public Criteria andPayout_amtEqualTo(BigDecimal value) {
            addCriterion("payout_amt =", value, "payout_amt");
            return (Criteria) this;
        }

        public Criteria andPayout_amtNotEqualTo(BigDecimal value) {
            addCriterion("payout_amt <>", value, "payout_amt");
            return (Criteria) this;
        }

        public Criteria andPayout_amtGreaterThan(BigDecimal value) {
            addCriterion("payout_amt >", value, "payout_amt");
            return (Criteria) this;
        }

        public Criteria andPayout_amtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("payout_amt >=", value, "payout_amt");
            return (Criteria) this;
        }

        public Criteria andPayout_amtLessThan(BigDecimal value) {
            addCriterion("payout_amt <", value, "payout_amt");
            return (Criteria) this;
        }

        public Criteria andPayout_amtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("payout_amt <=", value, "payout_amt");
            return (Criteria) this;
        }

        public Criteria andPayout_amtIn(List<BigDecimal> values) {
            addCriterion("payout_amt in", values, "payout_amt");
            return (Criteria) this;
        }

        public Criteria andPayout_amtNotIn(List<BigDecimal> values) {
            addCriterion("payout_amt not in", values, "payout_amt");
            return (Criteria) this;
        }

        public Criteria andPayout_amtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("payout_amt between", value1, value2, "payout_amt");
            return (Criteria) this;
        }

        public Criteria andPayout_amtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("payout_amt not between", value1, value2, "payout_amt");
            return (Criteria) this;
        }
    }

    /**
     * prod_dividend_rule
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * prod_dividend_rule 2017-06-01
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