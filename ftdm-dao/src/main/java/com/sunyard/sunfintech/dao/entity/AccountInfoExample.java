package com.sunyard.sunfintech.dao.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class AccountInfoExample {
    /**
     * account_info
     */
    protected String orderByClause;

    /**
     * account_info
     */
    protected boolean distinct;

    /**
     * account_info
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2017-06-01
     */
    public AccountInfoExample() {
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
     * account_info 2017-06-01
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
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

        public Criteria andMall_platcustIsNull() {
            addCriterion("mall_platcust is null");
            return (Criteria) this;
        }

        public Criteria andMall_platcustIsNotNull() {
            addCriterion("mall_platcust is not null");
            return (Criteria) this;
        }

        public Criteria andMall_platcustEqualTo(String value) {
            addCriterion("mall_platcust =", value, "mall_platcust");
            return (Criteria) this;
        }

        public Criteria andMall_platcustNotEqualTo(String value) {
            addCriterion("mall_platcust <>", value, "mall_platcust");
            return (Criteria) this;
        }

        public Criteria andMall_platcustGreaterThan(String value) {
            addCriterion("mall_platcust >", value, "mall_platcust");
            return (Criteria) this;
        }

        public Criteria andMall_platcustGreaterThanOrEqualTo(String value) {
            addCriterion("mall_platcust >=", value, "mall_platcust");
            return (Criteria) this;
        }

        public Criteria andMall_platcustLessThan(String value) {
            addCriterion("mall_platcust <", value, "mall_platcust");
            return (Criteria) this;
        }

        public Criteria andMall_platcustLessThanOrEqualTo(String value) {
            addCriterion("mall_platcust <=", value, "mall_platcust");
            return (Criteria) this;
        }

        public Criteria andMall_platcustLike(String value) {
            addCriterion("mall_platcust like", value, "mall_platcust");
            return (Criteria) this;
        }

        public Criteria andMall_platcustNotLike(String value) {
            addCriterion("mall_platcust not like", value, "mall_platcust");
            return (Criteria) this;
        }

        public Criteria andMall_platcustIn(List<String> values) {
            addCriterion("mall_platcust in", values, "mall_platcust");
            return (Criteria) this;
        }

        public Criteria andMall_platcustNotIn(List<String> values) {
            addCriterion("mall_platcust not in", values, "mall_platcust");
            return (Criteria) this;
        }

        public Criteria andMall_platcustBetween(String value1, String value2) {
            addCriterion("mall_platcust between", value1, value2, "mall_platcust");
            return (Criteria) this;
        }

        public Criteria andMall_platcustNotBetween(String value1, String value2) {
            addCriterion("mall_platcust not between", value1, value2, "mall_platcust");
            return (Criteria) this;
        }

        public Criteria andAccount_typeIsNull() {
            addCriterion("account_type is null");
            return (Criteria) this;
        }

        public Criteria andAccount_typeIsNotNull() {
            addCriterion("account_type is not null");
            return (Criteria) this;
        }

        public Criteria andAccount_typeEqualTo(String value) {
            addCriterion("account_type =", value, "account_type");
            return (Criteria) this;
        }

        public Criteria andAccount_typeNotEqualTo(String value) {
            addCriterion("account_type <>", value, "account_type");
            return (Criteria) this;
        }

        public Criteria andAccount_typeGreaterThan(String value) {
            addCriterion("account_type >", value, "account_type");
            return (Criteria) this;
        }

        public Criteria andAccount_typeGreaterThanOrEqualTo(String value) {
            addCriterion("account_type >=", value, "account_type");
            return (Criteria) this;
        }

        public Criteria andAccount_typeLessThan(String value) {
            addCriterion("account_type <", value, "account_type");
            return (Criteria) this;
        }

        public Criteria andAccount_typeLessThanOrEqualTo(String value) {
            addCriterion("account_type <=", value, "account_type");
            return (Criteria) this;
        }

        public Criteria andAccount_typeLike(String value) {
            addCriterion("account_type like", value, "account_type");
            return (Criteria) this;
        }

        public Criteria andAccount_typeNotLike(String value) {
            addCriterion("account_type not like", value, "account_type");
            return (Criteria) this;
        }

        public Criteria andAccount_typeIn(List<String> values) {
            addCriterion("account_type in", values, "account_type");
            return (Criteria) this;
        }

        public Criteria andAccount_typeNotIn(List<String> values) {
            addCriterion("account_type not in", values, "account_type");
            return (Criteria) this;
        }

        public Criteria andAccount_typeBetween(String value1, String value2) {
            addCriterion("account_type between", value1, value2, "account_type");
            return (Criteria) this;
        }

        public Criteria andAccount_typeNotBetween(String value1, String value2) {
            addCriterion("account_type not between", value1, value2, "account_type");
            return (Criteria) this;
        }

        public Criteria andAccount_nameIsNull() {
            addCriterion("account_name is null");
            return (Criteria) this;
        }

        public Criteria andAccount_nameIsNotNull() {
            addCriterion("account_name is not null");
            return (Criteria) this;
        }

        public Criteria andAccount_nameEqualTo(String value) {
            addCriterion("account_name =", value, "account_name");
            return (Criteria) this;
        }

        public Criteria andAccount_nameNotEqualTo(String value) {
            addCriterion("account_name <>", value, "account_name");
            return (Criteria) this;
        }

        public Criteria andAccount_nameGreaterThan(String value) {
            addCriterion("account_name >", value, "account_name");
            return (Criteria) this;
        }

        public Criteria andAccount_nameGreaterThanOrEqualTo(String value) {
            addCriterion("account_name >=", value, "account_name");
            return (Criteria) this;
        }

        public Criteria andAccount_nameLessThan(String value) {
            addCriterion("account_name <", value, "account_name");
            return (Criteria) this;
        }

        public Criteria andAccount_nameLessThanOrEqualTo(String value) {
            addCriterion("account_name <=", value, "account_name");
            return (Criteria) this;
        }

        public Criteria andAccount_nameLike(String value) {
            addCriterion("account_name like", value, "account_name");
            return (Criteria) this;
        }

        public Criteria andAccount_nameNotLike(String value) {
            addCriterion("account_name not like", value, "account_name");
            return (Criteria) this;
        }

        public Criteria andAccount_nameIn(List<String> values) {
            addCriterion("account_name in", values, "account_name");
            return (Criteria) this;
        }

        public Criteria andAccount_nameNotIn(List<String> values) {
            addCriterion("account_name not in", values, "account_name");
            return (Criteria) this;
        }

        public Criteria andAccount_nameBetween(String value1, String value2) {
            addCriterion("account_name between", value1, value2, "account_name");
            return (Criteria) this;
        }

        public Criteria andAccount_nameNotBetween(String value1, String value2) {
            addCriterion("account_name not between", value1, value2, "account_name");
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

        public Criteria andT_balanceEqualTo(BigDecimal value) {
            addCriterion("t_balance =", value, "t_balance");
            return (Criteria) this;
        }

        public Criteria andT_balanceNotEqualTo(BigDecimal value) {
            addCriterion("t_balance <>", value, "t_balance");
            return (Criteria) this;
        }

        public Criteria andT_balanceGreaterThan(BigDecimal value) {
            addCriterion("t_balance >", value, "t_balance");
            return (Criteria) this;
        }

        public Criteria andT_balanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("t_balance >=", value, "t_balance");
            return (Criteria) this;
        }

        public Criteria andT_balanceLessThan(BigDecimal value) {
            addCriterion("t_balance <", value, "t_balance");
            return (Criteria) this;
        }

        public Criteria andT_balanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("t_balance <=", value, "t_balance");
            return (Criteria) this;
        }

        public Criteria andT_balanceIn(List<BigDecimal> values) {
            addCriterion("t_balance in", values, "t_balance");
            return (Criteria) this;
        }

        public Criteria andT_balanceNotIn(List<BigDecimal> values) {
            addCriterion("t_balance not in", values, "t_balance");
            return (Criteria) this;
        }

        public Criteria andT_balanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("t_balance between", value1, value2, "t_balance");
            return (Criteria) this;
        }

        public Criteria andT_balanceNotBetween(BigDecimal value1, BigDecimal value2) {
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

        public Criteria andN_balanceEqualTo(BigDecimal value) {
            addCriterion("n_balance =", value, "n_balance");
            return (Criteria) this;
        }

        public Criteria andN_balanceNotEqualTo(BigDecimal value) {
            addCriterion("n_balance <>", value, "n_balance");
            return (Criteria) this;
        }

        public Criteria andN_balanceGreaterThan(BigDecimal value) {
            addCriterion("n_balance >", value, "n_balance");
            return (Criteria) this;
        }

        public Criteria andN_balanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("n_balance >=", value, "n_balance");
            return (Criteria) this;
        }

        public Criteria andN_balanceLessThan(BigDecimal value) {
            addCriterion("n_balance <", value, "n_balance");
            return (Criteria) this;
        }

        public Criteria andN_balanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("n_balance <=", value, "n_balance");
            return (Criteria) this;
        }

        public Criteria andN_balanceIn(List<BigDecimal> values) {
            addCriterion("n_balance in", values, "n_balance");
            return (Criteria) this;
        }

        public Criteria andN_balanceNotIn(List<BigDecimal> values) {
            addCriterion("n_balance not in", values, "n_balance");
            return (Criteria) this;
        }

        public Criteria andN_balanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("n_balance between", value1, value2, "n_balance");
            return (Criteria) this;
        }

        public Criteria andN_balanceNotBetween(BigDecimal value1, BigDecimal value2) {
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

        public Criteria andF_balanceEqualTo(BigDecimal value) {
            addCriterion("f_balance =", value, "f_balance");
            return (Criteria) this;
        }

        public Criteria andF_balanceNotEqualTo(BigDecimal value) {
            addCriterion("f_balance <>", value, "f_balance");
            return (Criteria) this;
        }

        public Criteria andF_balanceGreaterThan(BigDecimal value) {
            addCriterion("f_balance >", value, "f_balance");
            return (Criteria) this;
        }

        public Criteria andF_balanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("f_balance >=", value, "f_balance");
            return (Criteria) this;
        }

        public Criteria andF_balanceLessThan(BigDecimal value) {
            addCriterion("f_balance <", value, "f_balance");
            return (Criteria) this;
        }

        public Criteria andF_balanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("f_balance <=", value, "f_balance");
            return (Criteria) this;
        }

        public Criteria andF_balanceIn(List<BigDecimal> values) {
            addCriterion("f_balance in", values, "f_balance");
            return (Criteria) this;
        }

        public Criteria andF_balanceNotIn(List<BigDecimal> values) {
            addCriterion("f_balance not in", values, "f_balance");
            return (Criteria) this;
        }

        public Criteria andF_balanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("f_balance between", value1, value2, "f_balance");
            return (Criteria) this;
        }

        public Criteria andF_balanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("f_balance not between", value1, value2, "f_balance");
            return (Criteria) this;
        }

        public Criteria andYester_balanceIsNull() {
            addCriterion("yester_balance is null");
            return (Criteria) this;
        }

        public Criteria andYester_balanceIsNotNull() {
            addCriterion("yester_balance is not null");
            return (Criteria) this;
        }

        public Criteria andYester_balanceEqualTo(BigDecimal value) {
            addCriterion("yester_balance =", value, "yester_balance");
            return (Criteria) this;
        }

        public Criteria andYester_balanceNotEqualTo(BigDecimal value) {
            addCriterion("yester_balance <>", value, "yester_balance");
            return (Criteria) this;
        }

        public Criteria andYester_balanceGreaterThan(BigDecimal value) {
            addCriterion("yester_balance >", value, "yester_balance");
            return (Criteria) this;
        }

        public Criteria andYester_balanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("yester_balance >=", value, "yester_balance");
            return (Criteria) this;
        }

        public Criteria andYester_balanceLessThan(BigDecimal value) {
            addCriterion("yester_balance <", value, "yester_balance");
            return (Criteria) this;
        }

        public Criteria andYester_balanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("yester_balance <=", value, "yester_balance");
            return (Criteria) this;
        }

        public Criteria andYester_balanceIn(List<BigDecimal> values) {
            addCriterion("yester_balance in", values, "yester_balance");
            return (Criteria) this;
        }

        public Criteria andYester_balanceNotIn(List<BigDecimal> values) {
            addCriterion("yester_balance not in", values, "yester_balance");
            return (Criteria) this;
        }

        public Criteria andYester_balanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("yester_balance between", value1, value2, "yester_balance");
            return (Criteria) this;
        }

        public Criteria andYester_balanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("yester_balance not between", value1, value2, "yester_balance");
            return (Criteria) this;
        }

        public Criteria andModify_timeIsNull() {
            addCriterion("modify_time is null");
            return (Criteria) this;
        }

        public Criteria andModify_timeIsNotNull() {
            addCriterion("modify_time is not null");
            return (Criteria) this;
        }

        public Criteria andModify_timeEqualTo(Date value) {
            addCriterion("modify_time =", value, "modify_time");
            return (Criteria) this;
        }

        public Criteria andModify_timeNotEqualTo(Date value) {
            addCriterion("modify_time <>", value, "modify_time");
            return (Criteria) this;
        }

        public Criteria andModify_timeGreaterThan(Date value) {
            addCriterion("modify_time >", value, "modify_time");
            return (Criteria) this;
        }

        public Criteria andModify_timeGreaterThanOrEqualTo(Date value) {
            addCriterion("modify_time >=", value, "modify_time");
            return (Criteria) this;
        }

        public Criteria andModify_timeLessThan(Date value) {
            addCriterion("modify_time <", value, "modify_time");
            return (Criteria) this;
        }

        public Criteria andModify_timeLessThanOrEqualTo(Date value) {
            addCriterion("modify_time <=", value, "modify_time");
            return (Criteria) this;
        }

        public Criteria andModify_timeIn(List<Date> values) {
            addCriterion("modify_time in", values, "modify_time");
            return (Criteria) this;
        }

        public Criteria andModify_timeNotIn(List<Date> values) {
            addCriterion("modify_time not in", values, "modify_time");
            return (Criteria) this;
        }

        public Criteria andModify_timeBetween(Date value1, Date value2) {
            addCriterion("modify_time between", value1, value2, "modify_time");
            return (Criteria) this;
        }

        public Criteria andModify_timeNotBetween(Date value1, Date value2) {
            addCriterion("modify_time not between", value1, value2, "modify_time");
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

        public Criteria andExt3IsNull() {
            addCriterion("ext3 is null");
            return (Criteria) this;
        }

        public Criteria andExt3IsNotNull() {
            addCriterion("ext3 is not null");
            return (Criteria) this;
        }

        public Criteria andExt3EqualTo(String value) {
            addCriterion("ext3 =", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3NotEqualTo(String value) {
            addCriterion("ext3 <>", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3GreaterThan(String value) {
            addCriterion("ext3 >", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3GreaterThanOrEqualTo(String value) {
            addCriterion("ext3 >=", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3LessThan(String value) {
            addCriterion("ext3 <", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3LessThanOrEqualTo(String value) {
            addCriterion("ext3 <=", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3Like(String value) {
            addCriterion("ext3 like", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3NotLike(String value) {
            addCriterion("ext3 not like", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3In(List<String> values) {
            addCriterion("ext3 in", values, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3NotIn(List<String> values) {
            addCriterion("ext3 not in", values, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3Between(String value1, String value2) {
            addCriterion("ext3 between", value1, value2, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3NotBetween(String value1, String value2) {
            addCriterion("ext3 not between", value1, value2, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt4IsNull() {
            addCriterion("ext4 is null");
            return (Criteria) this;
        }

        public Criteria andExt4IsNotNull() {
            addCriterion("ext4 is not null");
            return (Criteria) this;
        }

        public Criteria andExt4EqualTo(String value) {
            addCriterion("ext4 =", value, "ext4");
            return (Criteria) this;
        }

        public Criteria andExt4NotEqualTo(String value) {
            addCriterion("ext4 <>", value, "ext4");
            return (Criteria) this;
        }

        public Criteria andExt4GreaterThan(String value) {
            addCriterion("ext4 >", value, "ext4");
            return (Criteria) this;
        }

        public Criteria andExt4GreaterThanOrEqualTo(String value) {
            addCriterion("ext4 >=", value, "ext4");
            return (Criteria) this;
        }

        public Criteria andExt4LessThan(String value) {
            addCriterion("ext4 <", value, "ext4");
            return (Criteria) this;
        }

        public Criteria andExt4LessThanOrEqualTo(String value) {
            addCriterion("ext4 <=", value, "ext4");
            return (Criteria) this;
        }

        public Criteria andExt4Like(String value) {
            addCriterion("ext4 like", value, "ext4");
            return (Criteria) this;
        }

        public Criteria andExt4NotLike(String value) {
            addCriterion("ext4 not like", value, "ext4");
            return (Criteria) this;
        }

        public Criteria andExt4In(List<String> values) {
            addCriterion("ext4 in", values, "ext4");
            return (Criteria) this;
        }

        public Criteria andExt4NotIn(List<String> values) {
            addCriterion("ext4 not in", values, "ext4");
            return (Criteria) this;
        }

        public Criteria andExt4Between(String value1, String value2) {
            addCriterion("ext4 between", value1, value2, "ext4");
            return (Criteria) this;
        }

        public Criteria andExt4NotBetween(String value1, String value2) {
            addCriterion("ext4 not between", value1, value2, "ext4");
            return (Criteria) this;
        }

        public Criteria andExt5IsNull() {
            addCriterion("ext5 is null");
            return (Criteria) this;
        }

        public Criteria andExt5IsNotNull() {
            addCriterion("ext5 is not null");
            return (Criteria) this;
        }

        public Criteria andExt5EqualTo(String value) {
            addCriterion("ext5 =", value, "ext5");
            return (Criteria) this;
        }

        public Criteria andExt5NotEqualTo(String value) {
            addCriterion("ext5 <>", value, "ext5");
            return (Criteria) this;
        }

        public Criteria andExt5GreaterThan(String value) {
            addCriterion("ext5 >", value, "ext5");
            return (Criteria) this;
        }

        public Criteria andExt5GreaterThanOrEqualTo(String value) {
            addCriterion("ext5 >=", value, "ext5");
            return (Criteria) this;
        }

        public Criteria andExt5LessThan(String value) {
            addCriterion("ext5 <", value, "ext5");
            return (Criteria) this;
        }

        public Criteria andExt5LessThanOrEqualTo(String value) {
            addCriterion("ext5 <=", value, "ext5");
            return (Criteria) this;
        }

        public Criteria andExt5Like(String value) {
            addCriterion("ext5 like", value, "ext5");
            return (Criteria) this;
        }

        public Criteria andExt5NotLike(String value) {
            addCriterion("ext5 not like", value, "ext5");
            return (Criteria) this;
        }

        public Criteria andExt5In(List<String> values) {
            addCriterion("ext5 in", values, "ext5");
            return (Criteria) this;
        }

        public Criteria andExt5NotIn(List<String> values) {
            addCriterion("ext5 not in", values, "ext5");
            return (Criteria) this;
        }

        public Criteria andExt5Between(String value1, String value2) {
            addCriterion("ext5 between", value1, value2, "ext5");
            return (Criteria) this;
        }

        public Criteria andExt5NotBetween(String value1, String value2) {
            addCriterion("ext5 not between", value1, value2, "ext5");
            return (Criteria) this;
        }
    }

    /**
     * account_info
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * account_info 2017-06-01
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