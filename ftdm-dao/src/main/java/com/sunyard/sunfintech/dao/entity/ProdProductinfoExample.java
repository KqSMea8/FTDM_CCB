package com.sunyard.sunfintech.dao.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProdProductinfoExample {
    /**
     * prod_productinfo
     */
    protected String orderByClause;

    /**
     * prod_productinfo
     */
    protected boolean distinct;

    /**
     * prod_productinfo
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2018-02-27
     */
    public ProdProductinfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *
     * @mbggenerated 2018-02-27
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *
     * @mbggenerated 2018-02-27
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *
     * @mbggenerated 2018-02-27
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *
     * @mbggenerated 2018-02-27
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *
     * @mbggenerated 2018-02-27
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *
     * @mbggenerated 2018-02-27
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *
     * @mbggenerated 2018-02-27
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *
     * @mbggenerated 2018-02-27
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
     * @mbggenerated 2018-02-27
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *
     * @mbggenerated 2018-02-27
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * prod_productinfo 2018-02-27
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

        public Criteria andProd_nameIsNull() {
            addCriterion("prod_name is null");
            return (Criteria) this;
        }

        public Criteria andProd_nameIsNotNull() {
            addCriterion("prod_name is not null");
            return (Criteria) this;
        }

        public Criteria andProd_nameEqualTo(String value) {
            addCriterion("prod_name =", value, "prod_name");
            return (Criteria) this;
        }

        public Criteria andProd_nameNotEqualTo(String value) {
            addCriterion("prod_name <>", value, "prod_name");
            return (Criteria) this;
        }

        public Criteria andProd_nameGreaterThan(String value) {
            addCriterion("prod_name >", value, "prod_name");
            return (Criteria) this;
        }

        public Criteria andProd_nameGreaterThanOrEqualTo(String value) {
            addCriterion("prod_name >=", value, "prod_name");
            return (Criteria) this;
        }

        public Criteria andProd_nameLessThan(String value) {
            addCriterion("prod_name <", value, "prod_name");
            return (Criteria) this;
        }

        public Criteria andProd_nameLessThanOrEqualTo(String value) {
            addCriterion("prod_name <=", value, "prod_name");
            return (Criteria) this;
        }

        public Criteria andProd_nameLike(String value) {
            addCriterion("prod_name like", value, "prod_name");
            return (Criteria) this;
        }

        public Criteria andProd_nameNotLike(String value) {
            addCriterion("prod_name not like", value, "prod_name");
            return (Criteria) this;
        }

        public Criteria andProd_nameIn(List<String> values) {
            addCriterion("prod_name in", values, "prod_name");
            return (Criteria) this;
        }

        public Criteria andProd_nameNotIn(List<String> values) {
            addCriterion("prod_name not in", values, "prod_name");
            return (Criteria) this;
        }

        public Criteria andProd_nameBetween(String value1, String value2) {
            addCriterion("prod_name between", value1, value2, "prod_name");
            return (Criteria) this;
        }

        public Criteria andProd_nameNotBetween(String value1, String value2) {
            addCriterion("prod_name not between", value1, value2, "prod_name");
            return (Criteria) this;
        }

        public Criteria andProd_typeIsNull() {
            addCriterion("prod_type is null");
            return (Criteria) this;
        }

        public Criteria andProd_typeIsNotNull() {
            addCriterion("prod_type is not null");
            return (Criteria) this;
        }

        public Criteria andProd_typeEqualTo(String value) {
            addCriterion("prod_type =", value, "prod_type");
            return (Criteria) this;
        }

        public Criteria andProd_typeNotEqualTo(String value) {
            addCriterion("prod_type <>", value, "prod_type");
            return (Criteria) this;
        }

        public Criteria andProd_typeGreaterThan(String value) {
            addCriterion("prod_type >", value, "prod_type");
            return (Criteria) this;
        }

        public Criteria andProd_typeGreaterThanOrEqualTo(String value) {
            addCriterion("prod_type >=", value, "prod_type");
            return (Criteria) this;
        }

        public Criteria andProd_typeLessThan(String value) {
            addCriterion("prod_type <", value, "prod_type");
            return (Criteria) this;
        }

        public Criteria andProd_typeLessThanOrEqualTo(String value) {
            addCriterion("prod_type <=", value, "prod_type");
            return (Criteria) this;
        }

        public Criteria andProd_typeLike(String value) {
            addCriterion("prod_type like", value, "prod_type");
            return (Criteria) this;
        }

        public Criteria andProd_typeNotLike(String value) {
            addCriterion("prod_type not like", value, "prod_type");
            return (Criteria) this;
        }

        public Criteria andProd_typeIn(List<String> values) {
            addCriterion("prod_type in", values, "prod_type");
            return (Criteria) this;
        }

        public Criteria andProd_typeNotIn(List<String> values) {
            addCriterion("prod_type not in", values, "prod_type");
            return (Criteria) this;
        }

        public Criteria andProd_typeBetween(String value1, String value2) {
            addCriterion("prod_type between", value1, value2, "prod_type");
            return (Criteria) this;
        }

        public Criteria andProd_typeNotBetween(String value1, String value2) {
            addCriterion("prod_type not between", value1, value2, "prod_type");
            return (Criteria) this;
        }

        public Criteria andTotal_limitIsNull() {
            addCriterion("total_limit is null");
            return (Criteria) this;
        }

        public Criteria andTotal_limitIsNotNull() {
            addCriterion("total_limit is not null");
            return (Criteria) this;
        }

        public Criteria andTotal_limitEqualTo(BigDecimal value) {
            addCriterion("total_limit =", value, "total_limit");
            return (Criteria) this;
        }

        public Criteria andTotal_limitNotEqualTo(BigDecimal value) {
            addCriterion("total_limit <>", value, "total_limit");
            return (Criteria) this;
        }

        public Criteria andTotal_limitGreaterThan(BigDecimal value) {
            addCriterion("total_limit >", value, "total_limit");
            return (Criteria) this;
        }

        public Criteria andTotal_limitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("total_limit >=", value, "total_limit");
            return (Criteria) this;
        }

        public Criteria andTotal_limitLessThan(BigDecimal value) {
            addCriterion("total_limit <", value, "total_limit");
            return (Criteria) this;
        }

        public Criteria andTotal_limitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("total_limit <=", value, "total_limit");
            return (Criteria) this;
        }

        public Criteria andTotal_limitIn(List<BigDecimal> values) {
            addCriterion("total_limit in", values, "total_limit");
            return (Criteria) this;
        }

        public Criteria andTotal_limitNotIn(List<BigDecimal> values) {
            addCriterion("total_limit not in", values, "total_limit");
            return (Criteria) this;
        }

        public Criteria andTotal_limitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_limit between", value1, value2, "total_limit");
            return (Criteria) this;
        }

        public Criteria andTotal_limitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_limit not between", value1, value2, "total_limit");
            return (Criteria) this;
        }

        public Criteria andRemain_limitIsNull() {
            addCriterion("remain_limit is null");
            return (Criteria) this;
        }

        public Criteria andRemain_limitIsNotNull() {
            addCriterion("remain_limit is not null");
            return (Criteria) this;
        }

        public Criteria andRemain_limitEqualTo(BigDecimal value) {
            addCriterion("remain_limit =", value, "remain_limit");
            return (Criteria) this;
        }

        public Criteria andRemain_limitNotEqualTo(BigDecimal value) {
            addCriterion("remain_limit <>", value, "remain_limit");
            return (Criteria) this;
        }

        public Criteria andRemain_limitGreaterThan(BigDecimal value) {
            addCriterion("remain_limit >", value, "remain_limit");
            return (Criteria) this;
        }

        public Criteria andRemain_limitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("remain_limit >=", value, "remain_limit");
            return (Criteria) this;
        }

        public Criteria andRemain_limitLessThan(BigDecimal value) {
            addCriterion("remain_limit <", value, "remain_limit");
            return (Criteria) this;
        }

        public Criteria andRemain_limitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("remain_limit <=", value, "remain_limit");
            return (Criteria) this;
        }

        public Criteria andRemain_limitIn(List<BigDecimal> values) {
            addCriterion("remain_limit in", values, "remain_limit");
            return (Criteria) this;
        }

        public Criteria andRemain_limitNotIn(List<BigDecimal> values) {
            addCriterion("remain_limit not in", values, "remain_limit");
            return (Criteria) this;
        }

        public Criteria andRemain_limitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("remain_limit between", value1, value2, "remain_limit");
            return (Criteria) this;
        }

        public Criteria andRemain_limitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("remain_limit not between", value1, value2, "remain_limit");
            return (Criteria) this;
        }

        public Criteria andSaled_limitIsNull() {
            addCriterion("saled_limit is null");
            return (Criteria) this;
        }

        public Criteria andSaled_limitIsNotNull() {
            addCriterion("saled_limit is not null");
            return (Criteria) this;
        }

        public Criteria andSaled_limitEqualTo(BigDecimal value) {
            addCriterion("saled_limit =", value, "saled_limit");
            return (Criteria) this;
        }

        public Criteria andSaled_limitNotEqualTo(BigDecimal value) {
            addCriterion("saled_limit <>", value, "saled_limit");
            return (Criteria) this;
        }

        public Criteria andSaled_limitGreaterThan(BigDecimal value) {
            addCriterion("saled_limit >", value, "saled_limit");
            return (Criteria) this;
        }

        public Criteria andSaled_limitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("saled_limit >=", value, "saled_limit");
            return (Criteria) this;
        }

        public Criteria andSaled_limitLessThan(BigDecimal value) {
            addCriterion("saled_limit <", value, "saled_limit");
            return (Criteria) this;
        }

        public Criteria andSaled_limitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("saled_limit <=", value, "saled_limit");
            return (Criteria) this;
        }

        public Criteria andSaled_limitIn(List<BigDecimal> values) {
            addCriterion("saled_limit in", values, "saled_limit");
            return (Criteria) this;
        }

        public Criteria andSaled_limitNotIn(List<BigDecimal> values) {
            addCriterion("saled_limit not in", values, "saled_limit");
            return (Criteria) this;
        }

        public Criteria andSaled_limitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("saled_limit between", value1, value2, "saled_limit");
            return (Criteria) this;
        }

        public Criteria andSaled_limitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("saled_limit not between", value1, value2, "saled_limit");
            return (Criteria) this;
        }

        public Criteria andValue_typeIsNull() {
            addCriterion("value_type is null");
            return (Criteria) this;
        }

        public Criteria andValue_typeIsNotNull() {
            addCriterion("value_type is not null");
            return (Criteria) this;
        }

        public Criteria andValue_typeEqualTo(String value) {
            addCriterion("value_type =", value, "value_type");
            return (Criteria) this;
        }

        public Criteria andValue_typeNotEqualTo(String value) {
            addCriterion("value_type <>", value, "value_type");
            return (Criteria) this;
        }

        public Criteria andValue_typeGreaterThan(String value) {
            addCriterion("value_type >", value, "value_type");
            return (Criteria) this;
        }

        public Criteria andValue_typeGreaterThanOrEqualTo(String value) {
            addCriterion("value_type >=", value, "value_type");
            return (Criteria) this;
        }

        public Criteria andValue_typeLessThan(String value) {
            addCriterion("value_type <", value, "value_type");
            return (Criteria) this;
        }

        public Criteria andValue_typeLessThanOrEqualTo(String value) {
            addCriterion("value_type <=", value, "value_type");
            return (Criteria) this;
        }

        public Criteria andValue_typeLike(String value) {
            addCriterion("value_type like", value, "value_type");
            return (Criteria) this;
        }

        public Criteria andValue_typeNotLike(String value) {
            addCriterion("value_type not like", value, "value_type");
            return (Criteria) this;
        }

        public Criteria andValue_typeIn(List<String> values) {
            addCriterion("value_type in", values, "value_type");
            return (Criteria) this;
        }

        public Criteria andValue_typeNotIn(List<String> values) {
            addCriterion("value_type not in", values, "value_type");
            return (Criteria) this;
        }

        public Criteria andValue_typeBetween(String value1, String value2) {
            addCriterion("value_type between", value1, value2, "value_type");
            return (Criteria) this;
        }

        public Criteria andValue_typeNotBetween(String value1, String value2) {
            addCriterion("value_type not between", value1, value2, "value_type");
            return (Criteria) this;
        }

        public Criteria andCreate_typeIsNull() {
            addCriterion("create_type is null");
            return (Criteria) this;
        }

        public Criteria andCreate_typeIsNotNull() {
            addCriterion("create_type is not null");
            return (Criteria) this;
        }

        public Criteria andCreate_typeEqualTo(String value) {
            addCriterion("create_type =", value, "create_type");
            return (Criteria) this;
        }

        public Criteria andCreate_typeNotEqualTo(String value) {
            addCriterion("create_type <>", value, "create_type");
            return (Criteria) this;
        }

        public Criteria andCreate_typeGreaterThan(String value) {
            addCriterion("create_type >", value, "create_type");
            return (Criteria) this;
        }

        public Criteria andCreate_typeGreaterThanOrEqualTo(String value) {
            addCriterion("create_type >=", value, "create_type");
            return (Criteria) this;
        }

        public Criteria andCreate_typeLessThan(String value) {
            addCriterion("create_type <", value, "create_type");
            return (Criteria) this;
        }

        public Criteria andCreate_typeLessThanOrEqualTo(String value) {
            addCriterion("create_type <=", value, "create_type");
            return (Criteria) this;
        }

        public Criteria andCreate_typeLike(String value) {
            addCriterion("create_type like", value, "create_type");
            return (Criteria) this;
        }

        public Criteria andCreate_typeNotLike(String value) {
            addCriterion("create_type not like", value, "create_type");
            return (Criteria) this;
        }

        public Criteria andCreate_typeIn(List<String> values) {
            addCriterion("create_type in", values, "create_type");
            return (Criteria) this;
        }

        public Criteria andCreate_typeNotIn(List<String> values) {
            addCriterion("create_type not in", values, "create_type");
            return (Criteria) this;
        }

        public Criteria andCreate_typeBetween(String value1, String value2) {
            addCriterion("create_type between", value1, value2, "create_type");
            return (Criteria) this;
        }

        public Criteria andCreate_typeNotBetween(String value1, String value2) {
            addCriterion("create_type not between", value1, value2, "create_type");
            return (Criteria) this;
        }

        public Criteria andPayout_typeIsNull() {
            addCriterion("payout_type is null");
            return (Criteria) this;
        }

        public Criteria andPayout_typeIsNotNull() {
            addCriterion("payout_type is not null");
            return (Criteria) this;
        }

        public Criteria andPayout_typeEqualTo(String value) {
            addCriterion("payout_type =", value, "payout_type");
            return (Criteria) this;
        }

        public Criteria andPayout_typeNotEqualTo(String value) {
            addCriterion("payout_type <>", value, "payout_type");
            return (Criteria) this;
        }

        public Criteria andPayout_typeGreaterThan(String value) {
            addCriterion("payout_type >", value, "payout_type");
            return (Criteria) this;
        }

        public Criteria andPayout_typeGreaterThanOrEqualTo(String value) {
            addCriterion("payout_type >=", value, "payout_type");
            return (Criteria) this;
        }

        public Criteria andPayout_typeLessThan(String value) {
            addCriterion("payout_type <", value, "payout_type");
            return (Criteria) this;
        }

        public Criteria andPayout_typeLessThanOrEqualTo(String value) {
            addCriterion("payout_type <=", value, "payout_type");
            return (Criteria) this;
        }

        public Criteria andPayout_typeLike(String value) {
            addCriterion("payout_type like", value, "payout_type");
            return (Criteria) this;
        }

        public Criteria andPayout_typeNotLike(String value) {
            addCriterion("payout_type not like", value, "payout_type");
            return (Criteria) this;
        }

        public Criteria andPayout_typeIn(List<String> values) {
            addCriterion("payout_type in", values, "payout_type");
            return (Criteria) this;
        }

        public Criteria andPayout_typeNotIn(List<String> values) {
            addCriterion("payout_type not in", values, "payout_type");
            return (Criteria) this;
        }

        public Criteria andPayout_typeBetween(String value1, String value2) {
            addCriterion("payout_type between", value1, value2, "payout_type");
            return (Criteria) this;
        }

        public Criteria andPayout_typeNotBetween(String value1, String value2) {
            addCriterion("payout_type not between", value1, value2, "payout_type");
            return (Criteria) this;
        }

        public Criteria andSell_dateIsNull() {
            addCriterion("sell_date is null");
            return (Criteria) this;
        }

        public Criteria andSell_dateIsNotNull() {
            addCriterion("sell_date is not null");
            return (Criteria) this;
        }

        public Criteria andSell_dateEqualTo(String value) {
            addCriterion("sell_date =", value, "sell_date");
            return (Criteria) this;
        }

        public Criteria andSell_dateNotEqualTo(String value) {
            addCriterion("sell_date <>", value, "sell_date");
            return (Criteria) this;
        }

        public Criteria andSell_dateGreaterThan(String value) {
            addCriterion("sell_date >", value, "sell_date");
            return (Criteria) this;
        }

        public Criteria andSell_dateGreaterThanOrEqualTo(String value) {
            addCriterion("sell_date >=", value, "sell_date");
            return (Criteria) this;
        }

        public Criteria andSell_dateLessThan(String value) {
            addCriterion("sell_date <", value, "sell_date");
            return (Criteria) this;
        }

        public Criteria andSell_dateLessThanOrEqualTo(String value) {
            addCriterion("sell_date <=", value, "sell_date");
            return (Criteria) this;
        }

        public Criteria andSell_dateLike(String value) {
            addCriterion("sell_date like", value, "sell_date");
            return (Criteria) this;
        }

        public Criteria andSell_dateNotLike(String value) {
            addCriterion("sell_date not like", value, "sell_date");
            return (Criteria) this;
        }

        public Criteria andSell_dateIn(List<String> values) {
            addCriterion("sell_date in", values, "sell_date");
            return (Criteria) this;
        }

        public Criteria andSell_dateNotIn(List<String> values) {
            addCriterion("sell_date not in", values, "sell_date");
            return (Criteria) this;
        }

        public Criteria andSell_dateBetween(String value1, String value2) {
            addCriterion("sell_date between", value1, value2, "sell_date");
            return (Criteria) this;
        }

        public Criteria andSell_dateNotBetween(String value1, String value2) {
            addCriterion("sell_date not between", value1, value2, "sell_date");
            return (Criteria) this;
        }

        public Criteria andValue_dateIsNull() {
            addCriterion("value_date is null");
            return (Criteria) this;
        }

        public Criteria andValue_dateIsNotNull() {
            addCriterion("value_date is not null");
            return (Criteria) this;
        }

        public Criteria andValue_dateEqualTo(String value) {
            addCriterion("value_date =", value, "value_date");
            return (Criteria) this;
        }

        public Criteria andValue_dateNotEqualTo(String value) {
            addCriterion("value_date <>", value, "value_date");
            return (Criteria) this;
        }

        public Criteria andValue_dateGreaterThan(String value) {
            addCriterion("value_date >", value, "value_date");
            return (Criteria) this;
        }

        public Criteria andValue_dateGreaterThanOrEqualTo(String value) {
            addCriterion("value_date >=", value, "value_date");
            return (Criteria) this;
        }

        public Criteria andValue_dateLessThan(String value) {
            addCriterion("value_date <", value, "value_date");
            return (Criteria) this;
        }

        public Criteria andValue_dateLessThanOrEqualTo(String value) {
            addCriterion("value_date <=", value, "value_date");
            return (Criteria) this;
        }

        public Criteria andValue_dateLike(String value) {
            addCriterion("value_date like", value, "value_date");
            return (Criteria) this;
        }

        public Criteria andValue_dateNotLike(String value) {
            addCriterion("value_date not like", value, "value_date");
            return (Criteria) this;
        }

        public Criteria andValue_dateIn(List<String> values) {
            addCriterion("value_date in", values, "value_date");
            return (Criteria) this;
        }

        public Criteria andValue_dateNotIn(List<String> values) {
            addCriterion("value_date not in", values, "value_date");
            return (Criteria) this;
        }

        public Criteria andValue_dateBetween(String value1, String value2) {
            addCriterion("value_date between", value1, value2, "value_date");
            return (Criteria) this;
        }

        public Criteria andValue_dateNotBetween(String value1, String value2) {
            addCriterion("value_date not between", value1, value2, "value_date");
            return (Criteria) this;
        }

        public Criteria andExpire_dateIsNull() {
            addCriterion("expire_date is null");
            return (Criteria) this;
        }

        public Criteria andExpire_dateIsNotNull() {
            addCriterion("expire_date is not null");
            return (Criteria) this;
        }

        public Criteria andExpire_dateEqualTo(String value) {
            addCriterion("expire_date =", value, "expire_date");
            return (Criteria) this;
        }

        public Criteria andExpire_dateNotEqualTo(String value) {
            addCriterion("expire_date <>", value, "expire_date");
            return (Criteria) this;
        }

        public Criteria andExpire_dateGreaterThan(String value) {
            addCriterion("expire_date >", value, "expire_date");
            return (Criteria) this;
        }

        public Criteria andExpire_dateGreaterThanOrEqualTo(String value) {
            addCriterion("expire_date >=", value, "expire_date");
            return (Criteria) this;
        }

        public Criteria andExpire_dateLessThan(String value) {
            addCriterion("expire_date <", value, "expire_date");
            return (Criteria) this;
        }

        public Criteria andExpire_dateLessThanOrEqualTo(String value) {
            addCriterion("expire_date <=", value, "expire_date");
            return (Criteria) this;
        }

        public Criteria andExpire_dateLike(String value) {
            addCriterion("expire_date like", value, "expire_date");
            return (Criteria) this;
        }

        public Criteria andExpire_dateNotLike(String value) {
            addCriterion("expire_date not like", value, "expire_date");
            return (Criteria) this;
        }

        public Criteria andExpire_dateIn(List<String> values) {
            addCriterion("expire_date in", values, "expire_date");
            return (Criteria) this;
        }

        public Criteria andExpire_dateNotIn(List<String> values) {
            addCriterion("expire_date not in", values, "expire_date");
            return (Criteria) this;
        }

        public Criteria andExpire_dateBetween(String value1, String value2) {
            addCriterion("expire_date between", value1, value2, "expire_date");
            return (Criteria) this;
        }

        public Criteria andExpire_dateNotBetween(String value1, String value2) {
            addCriterion("expire_date not between", value1, value2, "expire_date");
            return (Criteria) this;
        }

        public Criteria andCycleIsNull() {
            addCriterion("cycle is null");
            return (Criteria) this;
        }

        public Criteria andCycleIsNotNull() {
            addCriterion("cycle is not null");
            return (Criteria) this;
        }

        public Criteria andCycleEqualTo(Integer value) {
            addCriterion("cycle =", value, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleNotEqualTo(Integer value) {
            addCriterion("cycle <>", value, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleGreaterThan(Integer value) {
            addCriterion("cycle >", value, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleGreaterThanOrEqualTo(Integer value) {
            addCriterion("cycle >=", value, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleLessThan(Integer value) {
            addCriterion("cycle <", value, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleLessThanOrEqualTo(Integer value) {
            addCriterion("cycle <=", value, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleIn(List<Integer> values) {
            addCriterion("cycle in", values, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleNotIn(List<Integer> values) {
            addCriterion("cycle not in", values, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleBetween(Integer value1, Integer value2) {
            addCriterion("cycle between", value1, value2, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleNotBetween(Integer value1, Integer value2) {
            addCriterion("cycle not between", value1, value2, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycle_unitIsNull() {
            addCriterion("cycle_unit is null");
            return (Criteria) this;
        }

        public Criteria andCycle_unitIsNotNull() {
            addCriterion("cycle_unit is not null");
            return (Criteria) this;
        }

        public Criteria andCycle_unitEqualTo(String value) {
            addCriterion("cycle_unit =", value, "cycle_unit");
            return (Criteria) this;
        }

        public Criteria andCycle_unitNotEqualTo(String value) {
            addCriterion("cycle_unit <>", value, "cycle_unit");
            return (Criteria) this;
        }

        public Criteria andCycle_unitGreaterThan(String value) {
            addCriterion("cycle_unit >", value, "cycle_unit");
            return (Criteria) this;
        }

        public Criteria andCycle_unitGreaterThanOrEqualTo(String value) {
            addCriterion("cycle_unit >=", value, "cycle_unit");
            return (Criteria) this;
        }

        public Criteria andCycle_unitLessThan(String value) {
            addCriterion("cycle_unit <", value, "cycle_unit");
            return (Criteria) this;
        }

        public Criteria andCycle_unitLessThanOrEqualTo(String value) {
            addCriterion("cycle_unit <=", value, "cycle_unit");
            return (Criteria) this;
        }

        public Criteria andCycle_unitLike(String value) {
            addCriterion("cycle_unit like", value, "cycle_unit");
            return (Criteria) this;
        }

        public Criteria andCycle_unitNotLike(String value) {
            addCriterion("cycle_unit not like", value, "cycle_unit");
            return (Criteria) this;
        }

        public Criteria andCycle_unitIn(List<String> values) {
            addCriterion("cycle_unit in", values, "cycle_unit");
            return (Criteria) this;
        }

        public Criteria andCycle_unitNotIn(List<String> values) {
            addCriterion("cycle_unit not in", values, "cycle_unit");
            return (Criteria) this;
        }

        public Criteria andCycle_unitBetween(String value1, String value2) {
            addCriterion("cycle_unit between", value1, value2, "cycle_unit");
            return (Criteria) this;
        }

        public Criteria andCycle_unitNotBetween(String value1, String value2) {
            addCriterion("cycle_unit not between", value1, value2, "cycle_unit");
            return (Criteria) this;
        }

        public Criteria andParti_numIsNull() {
            addCriterion("parti_num is null");
            return (Criteria) this;
        }

        public Criteria andParti_numIsNotNull() {
            addCriterion("parti_num is not null");
            return (Criteria) this;
        }

        public Criteria andParti_numEqualTo(Integer value) {
            addCriterion("parti_num =", value, "parti_num");
            return (Criteria) this;
        }

        public Criteria andParti_numNotEqualTo(Integer value) {
            addCriterion("parti_num <>", value, "parti_num");
            return (Criteria) this;
        }

        public Criteria andParti_numGreaterThan(Integer value) {
            addCriterion("parti_num >", value, "parti_num");
            return (Criteria) this;
        }

        public Criteria andParti_numGreaterThanOrEqualTo(Integer value) {
            addCriterion("parti_num >=", value, "parti_num");
            return (Criteria) this;
        }

        public Criteria andParti_numLessThan(Integer value) {
            addCriterion("parti_num <", value, "parti_num");
            return (Criteria) this;
        }

        public Criteria andParti_numLessThanOrEqualTo(Integer value) {
            addCriterion("parti_num <=", value, "parti_num");
            return (Criteria) this;
        }

        public Criteria andParti_numIn(List<Integer> values) {
            addCriterion("parti_num in", values, "parti_num");
            return (Criteria) this;
        }

        public Criteria andParti_numNotIn(List<Integer> values) {
            addCriterion("parti_num not in", values, "parti_num");
            return (Criteria) this;
        }

        public Criteria andParti_numBetween(Integer value1, Integer value2) {
            addCriterion("parti_num between", value1, value2, "parti_num");
            return (Criteria) this;
        }

        public Criteria andParti_numNotBetween(Integer value1, Integer value2) {
            addCriterion("parti_num not between", value1, value2, "parti_num");
            return (Criteria) this;
        }

        public Criteria andIst_yearIsNull() {
            addCriterion("ist_year is null");
            return (Criteria) this;
        }

        public Criteria andIst_yearIsNotNull() {
            addCriterion("ist_year is not null");
            return (Criteria) this;
        }

        public Criteria andIst_yearEqualTo(BigDecimal value) {
            addCriterion("ist_year =", value, "ist_year");
            return (Criteria) this;
        }

        public Criteria andIst_yearNotEqualTo(BigDecimal value) {
            addCriterion("ist_year <>", value, "ist_year");
            return (Criteria) this;
        }

        public Criteria andIst_yearGreaterThan(BigDecimal value) {
            addCriterion("ist_year >", value, "ist_year");
            return (Criteria) this;
        }

        public Criteria andIst_yearGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ist_year >=", value, "ist_year");
            return (Criteria) this;
        }

        public Criteria andIst_yearLessThan(BigDecimal value) {
            addCriterion("ist_year <", value, "ist_year");
            return (Criteria) this;
        }

        public Criteria andIst_yearLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ist_year <=", value, "ist_year");
            return (Criteria) this;
        }

        public Criteria andIst_yearIn(List<BigDecimal> values) {
            addCriterion("ist_year in", values, "ist_year");
            return (Criteria) this;
        }

        public Criteria andIst_yearNotIn(List<BigDecimal> values) {
            addCriterion("ist_year not in", values, "ist_year");
            return (Criteria) this;
        }

        public Criteria andIst_yearBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ist_year between", value1, value2, "ist_year");
            return (Criteria) this;
        }

        public Criteria andIst_yearNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ist_year not between", value1, value2, "ist_year");
            return (Criteria) this;
        }

        public Criteria andProd_stateIsNull() {
            addCriterion("prod_state is null");
            return (Criteria) this;
        }

        public Criteria andProd_stateIsNotNull() {
            addCriterion("prod_state is not null");
            return (Criteria) this;
        }

        public Criteria andProd_stateEqualTo(String value) {
            addCriterion("prod_state =", value, "prod_state");
            return (Criteria) this;
        }

        public Criteria andProd_stateNotEqualTo(String value) {
            addCriterion("prod_state <>", value, "prod_state");
            return (Criteria) this;
        }

        public Criteria andProd_stateGreaterThan(String value) {
            addCriterion("prod_state >", value, "prod_state");
            return (Criteria) this;
        }

        public Criteria andProd_stateGreaterThanOrEqualTo(String value) {
            addCriterion("prod_state >=", value, "prod_state");
            return (Criteria) this;
        }

        public Criteria andProd_stateLessThan(String value) {
            addCriterion("prod_state <", value, "prod_state");
            return (Criteria) this;
        }

        public Criteria andProd_stateLessThanOrEqualTo(String value) {
            addCriterion("prod_state <=", value, "prod_state");
            return (Criteria) this;
        }

        public Criteria andProd_stateLike(String value) {
            addCriterion("prod_state like", value, "prod_state");
            return (Criteria) this;
        }

        public Criteria andProd_stateNotLike(String value) {
            addCriterion("prod_state not like", value, "prod_state");
            return (Criteria) this;
        }

        public Criteria andProd_stateIn(List<String> values) {
            addCriterion("prod_state in", values, "prod_state");
            return (Criteria) this;
        }

        public Criteria andProd_stateNotIn(List<String> values) {
            addCriterion("prod_state not in", values, "prod_state");
            return (Criteria) this;
        }

        public Criteria andProd_stateBetween(String value1, String value2) {
            addCriterion("prod_state between", value1, value2, "prod_state");
            return (Criteria) this;
        }

        public Criteria andProd_stateNotBetween(String value1, String value2) {
            addCriterion("prod_state not between", value1, value2, "prod_state");
            return (Criteria) this;
        }

        public Criteria andRepay_typeIsNull() {
            addCriterion("repay_type is null");
            return (Criteria) this;
        }

        public Criteria andRepay_typeIsNotNull() {
            addCriterion("repay_type is not null");
            return (Criteria) this;
        }

        public Criteria andRepay_typeEqualTo(String value) {
            addCriterion("repay_type =", value, "repay_type");
            return (Criteria) this;
        }

        public Criteria andRepay_typeNotEqualTo(String value) {
            addCriterion("repay_type <>", value, "repay_type");
            return (Criteria) this;
        }

        public Criteria andRepay_typeGreaterThan(String value) {
            addCriterion("repay_type >", value, "repay_type");
            return (Criteria) this;
        }

        public Criteria andRepay_typeGreaterThanOrEqualTo(String value) {
            addCriterion("repay_type >=", value, "repay_type");
            return (Criteria) this;
        }

        public Criteria andRepay_typeLessThan(String value) {
            addCriterion("repay_type <", value, "repay_type");
            return (Criteria) this;
        }

        public Criteria andRepay_typeLessThanOrEqualTo(String value) {
            addCriterion("repay_type <=", value, "repay_type");
            return (Criteria) this;
        }

        public Criteria andRepay_typeLike(String value) {
            addCriterion("repay_type like", value, "repay_type");
            return (Criteria) this;
        }

        public Criteria andRepay_typeNotLike(String value) {
            addCriterion("repay_type not like", value, "repay_type");
            return (Criteria) this;
        }

        public Criteria andRepay_typeIn(List<String> values) {
            addCriterion("repay_type in", values, "repay_type");
            return (Criteria) this;
        }

        public Criteria andRepay_typeNotIn(List<String> values) {
            addCriterion("repay_type not in", values, "repay_type");
            return (Criteria) this;
        }

        public Criteria andRepay_typeBetween(String value1, String value2) {
            addCriterion("repay_type between", value1, value2, "repay_type");
            return (Criteria) this;
        }

        public Criteria andRepay_typeNotBetween(String value1, String value2) {
            addCriterion("repay_type not between", value1, value2, "repay_type");
            return (Criteria) this;
        }

        public Criteria andProd_accountIsNull() {
            addCriterion("prod_account is null");
            return (Criteria) this;
        }

        public Criteria andProd_accountIsNotNull() {
            addCriterion("prod_account is not null");
            return (Criteria) this;
        }

        public Criteria andProd_accountEqualTo(String value) {
            addCriterion("prod_account =", value, "prod_account");
            return (Criteria) this;
        }

        public Criteria andProd_accountNotEqualTo(String value) {
            addCriterion("prod_account <>", value, "prod_account");
            return (Criteria) this;
        }

        public Criteria andProd_accountGreaterThan(String value) {
            addCriterion("prod_account >", value, "prod_account");
            return (Criteria) this;
        }

        public Criteria andProd_accountGreaterThanOrEqualTo(String value) {
            addCriterion("prod_account >=", value, "prod_account");
            return (Criteria) this;
        }

        public Criteria andProd_accountLessThan(String value) {
            addCriterion("prod_account <", value, "prod_account");
            return (Criteria) this;
        }

        public Criteria andProd_accountLessThanOrEqualTo(String value) {
            addCriterion("prod_account <=", value, "prod_account");
            return (Criteria) this;
        }

        public Criteria andProd_accountLike(String value) {
            addCriterion("prod_account like", value, "prod_account");
            return (Criteria) this;
        }

        public Criteria andProd_accountNotLike(String value) {
            addCriterion("prod_account not like", value, "prod_account");
            return (Criteria) this;
        }

        public Criteria andProd_accountIn(List<String> values) {
            addCriterion("prod_account in", values, "prod_account");
            return (Criteria) this;
        }

        public Criteria andProd_accountNotIn(List<String> values) {
            addCriterion("prod_account not in", values, "prod_account");
            return (Criteria) this;
        }

        public Criteria andProd_accountBetween(String value1, String value2) {
            addCriterion("prod_account between", value1, value2, "prod_account");
            return (Criteria) this;
        }

        public Criteria andProd_accountNotBetween(String value1, String value2) {
            addCriterion("prod_account not between", value1, value2, "prod_account");
            return (Criteria) this;
        }

        public Criteria andCharge_off_autoIsNull() {
            addCriterion("charge_off_auto is null");
            return (Criteria) this;
        }

        public Criteria andCharge_off_autoIsNotNull() {
            addCriterion("charge_off_auto is not null");
            return (Criteria) this;
        }

        public Criteria andCharge_off_autoEqualTo(String value) {
            addCriterion("charge_off_auto =", value, "charge_off_auto");
            return (Criteria) this;
        }

        public Criteria andCharge_off_autoNotEqualTo(String value) {
            addCriterion("charge_off_auto <>", value, "charge_off_auto");
            return (Criteria) this;
        }

        public Criteria andCharge_off_autoGreaterThan(String value) {
            addCriterion("charge_off_auto >", value, "charge_off_auto");
            return (Criteria) this;
        }

        public Criteria andCharge_off_autoGreaterThanOrEqualTo(String value) {
            addCriterion("charge_off_auto >=", value, "charge_off_auto");
            return (Criteria) this;
        }

        public Criteria andCharge_off_autoLessThan(String value) {
            addCriterion("charge_off_auto <", value, "charge_off_auto");
            return (Criteria) this;
        }

        public Criteria andCharge_off_autoLessThanOrEqualTo(String value) {
            addCriterion("charge_off_auto <=", value, "charge_off_auto");
            return (Criteria) this;
        }

        public Criteria andCharge_off_autoLike(String value) {
            addCriterion("charge_off_auto like", value, "charge_off_auto");
            return (Criteria) this;
        }

        public Criteria andCharge_off_autoNotLike(String value) {
            addCriterion("charge_off_auto not like", value, "charge_off_auto");
            return (Criteria) this;
        }

        public Criteria andCharge_off_autoIn(List<String> values) {
            addCriterion("charge_off_auto in", values, "charge_off_auto");
            return (Criteria) this;
        }

        public Criteria andCharge_off_autoNotIn(List<String> values) {
            addCriterion("charge_off_auto not in", values, "charge_off_auto");
            return (Criteria) this;
        }

        public Criteria andCharge_off_autoBetween(String value1, String value2) {
            addCriterion("charge_off_auto between", value1, value2, "charge_off_auto");
            return (Criteria) this;
        }

        public Criteria andCharge_off_autoNotBetween(String value1, String value2) {
            addCriterion("charge_off_auto not between", value1, value2, "charge_off_auto");
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

        public Criteria andExt1IsNull() {
            addCriterion("ext1 is null");
            return (Criteria) this;
        }

        public Criteria andExt1IsNotNull() {
            addCriterion("ext1 is not null");
            return (Criteria) this;
        }

        public Criteria andExt1EqualTo(String value) {
            addCriterion("ext1 =", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1NotEqualTo(String value) {
            addCriterion("ext1 <>", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1GreaterThan(String value) {
            addCriterion("ext1 >", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1GreaterThanOrEqualTo(String value) {
            addCriterion("ext1 >=", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1LessThan(String value) {
            addCriterion("ext1 <", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1LessThanOrEqualTo(String value) {
            addCriterion("ext1 <=", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1Like(String value) {
            addCriterion("ext1 like", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1NotLike(String value) {
            addCriterion("ext1 not like", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1In(List<String> values) {
            addCriterion("ext1 in", values, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1NotIn(List<String> values) {
            addCriterion("ext1 not in", values, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1Between(String value1, String value2) {
            addCriterion("ext1 between", value1, value2, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1NotBetween(String value1, String value2) {
            addCriterion("ext1 not between", value1, value2, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt2IsNull() {
            addCriterion("ext2 is null");
            return (Criteria) this;
        }

        public Criteria andExt2IsNotNull() {
            addCriterion("ext2 is not null");
            return (Criteria) this;
        }

        public Criteria andExt2EqualTo(String value) {
            addCriterion("ext2 =", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2NotEqualTo(String value) {
            addCriterion("ext2 <>", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2GreaterThan(String value) {
            addCriterion("ext2 >", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2GreaterThanOrEqualTo(String value) {
            addCriterion("ext2 >=", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2LessThan(String value) {
            addCriterion("ext2 <", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2LessThanOrEqualTo(String value) {
            addCriterion("ext2 <=", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2Like(String value) {
            addCriterion("ext2 like", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2NotLike(String value) {
            addCriterion("ext2 not like", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2In(List<String> values) {
            addCriterion("ext2 in", values, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2NotIn(List<String> values) {
            addCriterion("ext2 not in", values, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2Between(String value1, String value2) {
            addCriterion("ext2 between", value1, value2, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2NotBetween(String value1, String value2) {
            addCriterion("ext2 not between", value1, value2, "ext2");
            return (Criteria) this;
        }

        public Criteria andRisk_lvlIsNull() {
            addCriterion("risk_lvl is null");
            return (Criteria) this;
        }

        public Criteria andRisk_lvlIsNotNull() {
            addCriterion("risk_lvl is not null");
            return (Criteria) this;
        }

        public Criteria andRisk_lvlEqualTo(String value) {
            addCriterion("risk_lvl =", value, "risk_lvl");
            return (Criteria) this;
        }

        public Criteria andRisk_lvlNotEqualTo(String value) {
            addCriterion("risk_lvl <>", value, "risk_lvl");
            return (Criteria) this;
        }

        public Criteria andRisk_lvlGreaterThan(String value) {
            addCriterion("risk_lvl >", value, "risk_lvl");
            return (Criteria) this;
        }

        public Criteria andRisk_lvlGreaterThanOrEqualTo(String value) {
            addCriterion("risk_lvl >=", value, "risk_lvl");
            return (Criteria) this;
        }

        public Criteria andRisk_lvlLessThan(String value) {
            addCriterion("risk_lvl <", value, "risk_lvl");
            return (Criteria) this;
        }

        public Criteria andRisk_lvlLessThanOrEqualTo(String value) {
            addCriterion("risk_lvl <=", value, "risk_lvl");
            return (Criteria) this;
        }

        public Criteria andRisk_lvlLike(String value) {
            addCriterion("risk_lvl like", value, "risk_lvl");
            return (Criteria) this;
        }

        public Criteria andRisk_lvlNotLike(String value) {
            addCriterion("risk_lvl not like", value, "risk_lvl");
            return (Criteria) this;
        }

        public Criteria andRisk_lvlIn(List<String> values) {
            addCriterion("risk_lvl in", values, "risk_lvl");
            return (Criteria) this;
        }

        public Criteria andRisk_lvlNotIn(List<String> values) {
            addCriterion("risk_lvl not in", values, "risk_lvl");
            return (Criteria) this;
        }

        public Criteria andRisk_lvlBetween(String value1, String value2) {
            addCriterion("risk_lvl between", value1, value2, "risk_lvl");
            return (Criteria) this;
        }

        public Criteria andRisk_lvlNotBetween(String value1, String value2) {
            addCriterion("risk_lvl not between", value1, value2, "risk_lvl");
            return (Criteria) this;
        }

        public Criteria andProd_infoIsNull() {
            addCriterion("prod_info is null");
            return (Criteria) this;
        }

        public Criteria andProd_infoIsNotNull() {
            addCriterion("prod_info is not null");
            return (Criteria) this;
        }

        public Criteria andProd_infoEqualTo(String value) {
            addCriterion("prod_info =", value, "prod_info");
            return (Criteria) this;
        }

        public Criteria andProd_infoNotEqualTo(String value) {
            addCriterion("prod_info <>", value, "prod_info");
            return (Criteria) this;
        }

        public Criteria andProd_infoGreaterThan(String value) {
            addCriterion("prod_info >", value, "prod_info");
            return (Criteria) this;
        }

        public Criteria andProd_infoGreaterThanOrEqualTo(String value) {
            addCriterion("prod_info >=", value, "prod_info");
            return (Criteria) this;
        }

        public Criteria andProd_infoLessThan(String value) {
            addCriterion("prod_info <", value, "prod_info");
            return (Criteria) this;
        }

        public Criteria andProd_infoLessThanOrEqualTo(String value) {
            addCriterion("prod_info <=", value, "prod_info");
            return (Criteria) this;
        }

        public Criteria andProd_infoLike(String value) {
            addCriterion("prod_info like", value, "prod_info");
            return (Criteria) this;
        }

        public Criteria andProd_infoNotLike(String value) {
            addCriterion("prod_info not like", value, "prod_info");
            return (Criteria) this;
        }

        public Criteria andProd_infoIn(List<String> values) {
            addCriterion("prod_info in", values, "prod_info");
            return (Criteria) this;
        }

        public Criteria andProd_infoNotIn(List<String> values) {
            addCriterion("prod_info not in", values, "prod_info");
            return (Criteria) this;
        }

        public Criteria andProd_infoBetween(String value1, String value2) {
            addCriterion("prod_info between", value1, value2, "prod_info");
            return (Criteria) this;
        }

        public Criteria andProd_infoNotBetween(String value1, String value2) {
            addCriterion("prod_info not between", value1, value2, "prod_info");
            return (Criteria) this;
        }

        public Criteria andBuyer_num_limitIsNull() {
            addCriterion("buyer_num_limit is null");
            return (Criteria) this;
        }

        public Criteria andBuyer_num_limitIsNotNull() {
            addCriterion("buyer_num_limit is not null");
            return (Criteria) this;
        }

        public Criteria andBuyer_num_limitEqualTo(String value) {
            addCriterion("buyer_num_limit =", value, "buyer_num_limit");
            return (Criteria) this;
        }

        public Criteria andBuyer_num_limitNotEqualTo(String value) {
            addCriterion("buyer_num_limit <>", value, "buyer_num_limit");
            return (Criteria) this;
        }

        public Criteria andBuyer_num_limitGreaterThan(String value) {
            addCriterion("buyer_num_limit >", value, "buyer_num_limit");
            return (Criteria) this;
        }

        public Criteria andBuyer_num_limitGreaterThanOrEqualTo(String value) {
            addCriterion("buyer_num_limit >=", value, "buyer_num_limit");
            return (Criteria) this;
        }

        public Criteria andBuyer_num_limitLessThan(String value) {
            addCriterion("buyer_num_limit <", value, "buyer_num_limit");
            return (Criteria) this;
        }

        public Criteria andBuyer_num_limitLessThanOrEqualTo(String value) {
            addCriterion("buyer_num_limit <=", value, "buyer_num_limit");
            return (Criteria) this;
        }

        public Criteria andBuyer_num_limitLike(String value) {
            addCriterion("buyer_num_limit like", value, "buyer_num_limit");
            return (Criteria) this;
        }

        public Criteria andBuyer_num_limitNotLike(String value) {
            addCriterion("buyer_num_limit not like", value, "buyer_num_limit");
            return (Criteria) this;
        }

        public Criteria andBuyer_num_limitIn(List<String> values) {
            addCriterion("buyer_num_limit in", values, "buyer_num_limit");
            return (Criteria) this;
        }

        public Criteria andBuyer_num_limitNotIn(List<String> values) {
            addCriterion("buyer_num_limit not in", values, "buyer_num_limit");
            return (Criteria) this;
        }

        public Criteria andBuyer_num_limitBetween(String value1, String value2) {
            addCriterion("buyer_num_limit between", value1, value2, "buyer_num_limit");
            return (Criteria) this;
        }

        public Criteria andBuyer_num_limitNotBetween(String value1, String value2) {
            addCriterion("buyer_num_limit not between", value1, value2, "buyer_num_limit");
            return (Criteria) this;
        }

        public Criteria andOverLimitIsNull() {
            addCriterion("overLimit is null");
            return (Criteria) this;
        }

        public Criteria andOverLimitIsNotNull() {
            addCriterion("overLimit is not null");
            return (Criteria) this;
        }

        public Criteria andOverLimitEqualTo(String value) {
            addCriterion("overLimit =", value, "overLimit");
            return (Criteria) this;
        }

        public Criteria andOverLimitNotEqualTo(String value) {
            addCriterion("overLimit <>", value, "overLimit");
            return (Criteria) this;
        }

        public Criteria andOverLimitGreaterThan(String value) {
            addCriterion("overLimit >", value, "overLimit");
            return (Criteria) this;
        }

        public Criteria andOverLimitGreaterThanOrEqualTo(String value) {
            addCriterion("overLimit >=", value, "overLimit");
            return (Criteria) this;
        }

        public Criteria andOverLimitLessThan(String value) {
            addCriterion("overLimit <", value, "overLimit");
            return (Criteria) this;
        }

        public Criteria andOverLimitLessThanOrEqualTo(String value) {
            addCriterion("overLimit <=", value, "overLimit");
            return (Criteria) this;
        }

        public Criteria andOverLimitLike(String value) {
            addCriterion("overLimit like", value, "overLimit");
            return (Criteria) this;
        }

        public Criteria andOverLimitNotLike(String value) {
            addCriterion("overLimit not like", value, "overLimit");
            return (Criteria) this;
        }

        public Criteria andOverLimitIn(List<String> values) {
            addCriterion("overLimit in", values, "overLimit");
            return (Criteria) this;
        }

        public Criteria andOverLimitNotIn(List<String> values) {
            addCriterion("overLimit not in", values, "overLimit");
            return (Criteria) this;
        }

        public Criteria andOverLimitBetween(String value1, String value2) {
            addCriterion("overLimit between", value1, value2, "overLimit");
            return (Criteria) this;
        }

        public Criteria andOverLimitNotBetween(String value1, String value2) {
            addCriterion("overLimit not between", value1, value2, "overLimit");
            return (Criteria) this;
        }

        public Criteria andOver_total_limitIsNull() {
            addCriterion("over_total_limit is null");
            return (Criteria) this;
        }

        public Criteria andOver_total_limitIsNotNull() {
            addCriterion("over_total_limit is not null");
            return (Criteria) this;
        }

        public Criteria andOver_total_limitEqualTo(String value) {
            addCriterion("over_total_limit =", value, "over_total_limit");
            return (Criteria) this;
        }

        public Criteria andOver_total_limitNotEqualTo(String value) {
            addCriterion("over_total_limit <>", value, "over_total_limit");
            return (Criteria) this;
        }

        public Criteria andOver_total_limitGreaterThan(String value) {
            addCriterion("over_total_limit >", value, "over_total_limit");
            return (Criteria) this;
        }

        public Criteria andOver_total_limitGreaterThanOrEqualTo(String value) {
            addCriterion("over_total_limit >=", value, "over_total_limit");
            return (Criteria) this;
        }

        public Criteria andOver_total_limitLessThan(String value) {
            addCriterion("over_total_limit <", value, "over_total_limit");
            return (Criteria) this;
        }

        public Criteria andOver_total_limitLessThanOrEqualTo(String value) {
            addCriterion("over_total_limit <=", value, "over_total_limit");
            return (Criteria) this;
        }

        public Criteria andOver_total_limitLike(String value) {
            addCriterion("over_total_limit like", value, "over_total_limit");
            return (Criteria) this;
        }

        public Criteria andOver_total_limitNotLike(String value) {
            addCriterion("over_total_limit not like", value, "over_total_limit");
            return (Criteria) this;
        }

        public Criteria andOver_total_limitIn(List<String> values) {
            addCriterion("over_total_limit in", values, "over_total_limit");
            return (Criteria) this;
        }

        public Criteria andOver_total_limitNotIn(List<String> values) {
            addCriterion("over_total_limit not in", values, "over_total_limit");
            return (Criteria) this;
        }

        public Criteria andOver_total_limitBetween(String value1, String value2) {
            addCriterion("over_total_limit between", value1, value2, "over_total_limit");
            return (Criteria) this;
        }

        public Criteria andOver_total_limitNotBetween(String value1, String value2) {
            addCriterion("over_total_limit not between", value1, value2, "over_total_limit");
            return (Criteria) this;
        }

        public Criteria andAuto_flgIsNull() {
            addCriterion("auto_flg is null");
            return (Criteria) this;
        }

        public Criteria andAuto_flgIsNotNull() {
            addCriterion("auto_flg is not null");
            return (Criteria) this;
        }

        public Criteria andAuto_flgEqualTo(String value) {
            addCriterion("auto_flg =", value, "auto_flg");
            return (Criteria) this;
        }

        public Criteria andAuto_flgNotEqualTo(String value) {
            addCriterion("auto_flg <>", value, "auto_flg");
            return (Criteria) this;
        }

        public Criteria andAuto_flgGreaterThan(String value) {
            addCriterion("auto_flg >", value, "auto_flg");
            return (Criteria) this;
        }

        public Criteria andAuto_flgGreaterThanOrEqualTo(String value) {
            addCriterion("auto_flg >=", value, "auto_flg");
            return (Criteria) this;
        }

        public Criteria andAuto_flgLessThan(String value) {
            addCriterion("auto_flg <", value, "auto_flg");
            return (Criteria) this;
        }

        public Criteria andAuto_flgLessThanOrEqualTo(String value) {
            addCriterion("auto_flg <=", value, "auto_flg");
            return (Criteria) this;
        }

        public Criteria andAuto_flgLike(String value) {
            addCriterion("auto_flg like", value, "auto_flg");
            return (Criteria) this;
        }

        public Criteria andAuto_flgNotLike(String value) {
            addCriterion("auto_flg not like", value, "auto_flg");
            return (Criteria) this;
        }

        public Criteria andAuto_flgIn(List<String> values) {
            addCriterion("auto_flg in", values, "auto_flg");
            return (Criteria) this;
        }

        public Criteria andAuto_flgNotIn(List<String> values) {
            addCriterion("auto_flg not in", values, "auto_flg");
            return (Criteria) this;
        }

        public Criteria andAuto_flgBetween(String value1, String value2) {
            addCriterion("auto_flg between", value1, value2, "auto_flg");
            return (Criteria) this;
        }

        public Criteria andAuto_flgNotBetween(String value1, String value2) {
            addCriterion("auto_flg not between", value1, value2, "auto_flg");
            return (Criteria) this;
        }
    }

    /**
     * prod_productinfo
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * prod_productinfo 2018-02-27
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