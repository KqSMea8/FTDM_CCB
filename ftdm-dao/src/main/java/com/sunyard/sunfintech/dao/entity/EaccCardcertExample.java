package com.sunyard.sunfintech.dao.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class EaccCardcertExample {
    /**
     * eacc_cardcert
     */
    protected String orderByClause;

    /**
     * eacc_cardcert
     */
    protected boolean distinct;

    /**
     * eacc_cardcert
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2017-11-21
     */
    public EaccCardcertExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *
     * @mbggenerated 2017-11-21
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *
     * @mbggenerated 2017-11-21
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *
     * @mbggenerated 2017-11-21
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *
     * @mbggenerated 2017-11-21
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *
     * @mbggenerated 2017-11-21
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *
     * @mbggenerated 2017-11-21
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *
     * @mbggenerated 2017-11-21
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *
     * @mbggenerated 2017-11-21
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
     * @mbggenerated 2017-11-21
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *
     * @mbggenerated 2017-11-21
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * eacc_cardcert 2017-11-21
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

        public Criteria andAcct_nameIsNull() {
            addCriterion("acct_name is null");
            return (Criteria) this;
        }

        public Criteria andAcct_nameIsNotNull() {
            addCriterion("acct_name is not null");
            return (Criteria) this;
        }

        public Criteria andAcct_nameEqualTo(String value) {
            addCriterion("acct_name =", value, "acct_name");
            return (Criteria) this;
        }

        public Criteria andAcct_nameNotEqualTo(String value) {
            addCriterion("acct_name <>", value, "acct_name");
            return (Criteria) this;
        }

        public Criteria andAcct_nameGreaterThan(String value) {
            addCriterion("acct_name >", value, "acct_name");
            return (Criteria) this;
        }

        public Criteria andAcct_nameGreaterThanOrEqualTo(String value) {
            addCriterion("acct_name >=", value, "acct_name");
            return (Criteria) this;
        }

        public Criteria andAcct_nameLessThan(String value) {
            addCriterion("acct_name <", value, "acct_name");
            return (Criteria) this;
        }

        public Criteria andAcct_nameLessThanOrEqualTo(String value) {
            addCriterion("acct_name <=", value, "acct_name");
            return (Criteria) this;
        }

        public Criteria andAcct_nameLike(String value) {
            addCriterion("acct_name like", value, "acct_name");
            return (Criteria) this;
        }

        public Criteria andAcct_nameNotLike(String value) {
            addCriterion("acct_name not like", value, "acct_name");
            return (Criteria) this;
        }

        public Criteria andAcct_nameIn(List<String> values) {
            addCriterion("acct_name in", values, "acct_name");
            return (Criteria) this;
        }

        public Criteria andAcct_nameNotIn(List<String> values) {
            addCriterion("acct_name not in", values, "acct_name");
            return (Criteria) this;
        }

        public Criteria andAcct_nameBetween(String value1, String value2) {
            addCriterion("acct_name between", value1, value2, "acct_name");
            return (Criteria) this;
        }

        public Criteria andAcct_nameNotBetween(String value1, String value2) {
            addCriterion("acct_name not between", value1, value2, "acct_name");
            return (Criteria) this;
        }

        public Criteria andAcct_noIsNull() {
            addCriterion("acct_no is null");
            return (Criteria) this;
        }

        public Criteria andAcct_noIsNotNull() {
            addCriterion("acct_no is not null");
            return (Criteria) this;
        }

        public Criteria andAcct_noEqualTo(String value) {
            addCriterion("acct_no =", value, "acct_no");
            return (Criteria) this;
        }

        public Criteria andAcct_noNotEqualTo(String value) {
            addCriterion("acct_no <>", value, "acct_no");
            return (Criteria) this;
        }

        public Criteria andAcct_noGreaterThan(String value) {
            addCriterion("acct_no >", value, "acct_no");
            return (Criteria) this;
        }

        public Criteria andAcct_noGreaterThanOrEqualTo(String value) {
            addCriterion("acct_no >=", value, "acct_no");
            return (Criteria) this;
        }

        public Criteria andAcct_noLessThan(String value) {
            addCriterion("acct_no <", value, "acct_no");
            return (Criteria) this;
        }

        public Criteria andAcct_noLessThanOrEqualTo(String value) {
            addCriterion("acct_no <=", value, "acct_no");
            return (Criteria) this;
        }

        public Criteria andAcct_noLike(String value) {
            addCriterion("acct_no like", value, "acct_no");
            return (Criteria) this;
        }

        public Criteria andAcct_noNotLike(String value) {
            addCriterion("acct_no not like", value, "acct_no");
            return (Criteria) this;
        }

        public Criteria andAcct_noIn(List<String> values) {
            addCriterion("acct_no in", values, "acct_no");
            return (Criteria) this;
        }

        public Criteria andAcct_noNotIn(List<String> values) {
            addCriterion("acct_no not in", values, "acct_no");
            return (Criteria) this;
        }

        public Criteria andAcct_noBetween(String value1, String value2) {
            addCriterion("acct_no between", value1, value2, "acct_no");
            return (Criteria) this;
        }

        public Criteria andAcct_noNotBetween(String value1, String value2) {
            addCriterion("acct_no not between", value1, value2, "acct_no");
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

        public Criteria andOrg_noIsNull() {
            addCriterion("org_no is null");
            return (Criteria) this;
        }

        public Criteria andOrg_noIsNotNull() {
            addCriterion("org_no is not null");
            return (Criteria) this;
        }

        public Criteria andOrg_noEqualTo(String value) {
            addCriterion("org_no =", value, "org_no");
            return (Criteria) this;
        }

        public Criteria andOrg_noNotEqualTo(String value) {
            addCriterion("org_no <>", value, "org_no");
            return (Criteria) this;
        }

        public Criteria andOrg_noGreaterThan(String value) {
            addCriterion("org_no >", value, "org_no");
            return (Criteria) this;
        }

        public Criteria andOrg_noGreaterThanOrEqualTo(String value) {
            addCriterion("org_no >=", value, "org_no");
            return (Criteria) this;
        }

        public Criteria andOrg_noLessThan(String value) {
            addCriterion("org_no <", value, "org_no");
            return (Criteria) this;
        }

        public Criteria andOrg_noLessThanOrEqualTo(String value) {
            addCriterion("org_no <=", value, "org_no");
            return (Criteria) this;
        }

        public Criteria andOrg_noLike(String value) {
            addCriterion("org_no like", value, "org_no");
            return (Criteria) this;
        }

        public Criteria andOrg_noNotLike(String value) {
            addCriterion("org_no not like", value, "org_no");
            return (Criteria) this;
        }

        public Criteria andOrg_noIn(List<String> values) {
            addCriterion("org_no in", values, "org_no");
            return (Criteria) this;
        }

        public Criteria andOrg_noNotIn(List<String> values) {
            addCriterion("org_no not in", values, "org_no");
            return (Criteria) this;
        }

        public Criteria andOrg_noBetween(String value1, String value2) {
            addCriterion("org_no between", value1, value2, "org_no");
            return (Criteria) this;
        }

        public Criteria andOrg_noNotBetween(String value1, String value2) {
            addCriterion("org_no not between", value1, value2, "org_no");
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
     * eacc_cardcert
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * eacc_cardcert 2017-11-21
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