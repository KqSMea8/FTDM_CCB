package com.sunyard.sunfintech.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PlatPlatinfoExample {
    /**
     * plat_platinfo
     */
    protected String orderByClause;

    /**
     * plat_platinfo
     */
    protected boolean distinct;

    /**
     * plat_platinfo
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2017-06-01
     */
    public PlatPlatinfoExample() {
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
     * plat_platinfo 2017-06-01
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

        public Criteria andMall_noIsNull() {
            addCriterion("mall_no is null");
            return (Criteria) this;
        }

        public Criteria andMall_noIsNotNull() {
            addCriterion("mall_no is not null");
            return (Criteria) this;
        }

        public Criteria andMall_noEqualTo(String value) {
            addCriterion("mall_no =", value, "mall_no");
            return (Criteria) this;
        }

        public Criteria andMall_noNotEqualTo(String value) {
            addCriterion("mall_no <>", value, "mall_no");
            return (Criteria) this;
        }

        public Criteria andMall_noGreaterThan(String value) {
            addCriterion("mall_no >", value, "mall_no");
            return (Criteria) this;
        }

        public Criteria andMall_noGreaterThanOrEqualTo(String value) {
            addCriterion("mall_no >=", value, "mall_no");
            return (Criteria) this;
        }

        public Criteria andMall_noLessThan(String value) {
            addCriterion("mall_no <", value, "mall_no");
            return (Criteria) this;
        }

        public Criteria andMall_noLessThanOrEqualTo(String value) {
            addCriterion("mall_no <=", value, "mall_no");
            return (Criteria) this;
        }

        public Criteria andMall_noLike(String value) {
            addCriterion("mall_no like", value, "mall_no");
            return (Criteria) this;
        }

        public Criteria andMall_noNotLike(String value) {
            addCriterion("mall_no not like", value, "mall_no");
            return (Criteria) this;
        }

        public Criteria andMall_noIn(List<String> values) {
            addCriterion("mall_no in", values, "mall_no");
            return (Criteria) this;
        }

        public Criteria andMall_noNotIn(List<String> values) {
            addCriterion("mall_no not in", values, "mall_no");
            return (Criteria) this;
        }

        public Criteria andMall_noBetween(String value1, String value2) {
            addCriterion("mall_no between", value1, value2, "mall_no");
            return (Criteria) this;
        }

        public Criteria andMall_noNotBetween(String value1, String value2) {
            addCriterion("mall_no not between", value1, value2, "mall_no");
            return (Criteria) this;
        }

        public Criteria andMall_nameIsNull() {
            addCriterion("mall_name is null");
            return (Criteria) this;
        }

        public Criteria andMall_nameIsNotNull() {
            addCriterion("mall_name is not null");
            return (Criteria) this;
        }

        public Criteria andMall_nameEqualTo(String value) {
            addCriterion("mall_name =", value, "mall_name");
            return (Criteria) this;
        }

        public Criteria andMall_nameNotEqualTo(String value) {
            addCriterion("mall_name <>", value, "mall_name");
            return (Criteria) this;
        }

        public Criteria andMall_nameGreaterThan(String value) {
            addCriterion("mall_name >", value, "mall_name");
            return (Criteria) this;
        }

        public Criteria andMall_nameGreaterThanOrEqualTo(String value) {
            addCriterion("mall_name >=", value, "mall_name");
            return (Criteria) this;
        }

        public Criteria andMall_nameLessThan(String value) {
            addCriterion("mall_name <", value, "mall_name");
            return (Criteria) this;
        }

        public Criteria andMall_nameLessThanOrEqualTo(String value) {
            addCriterion("mall_name <=", value, "mall_name");
            return (Criteria) this;
        }

        public Criteria andMall_nameLike(String value) {
            addCriterion("mall_name like", value, "mall_name");
            return (Criteria) this;
        }

        public Criteria andMall_nameNotLike(String value) {
            addCriterion("mall_name not like", value, "mall_name");
            return (Criteria) this;
        }

        public Criteria andMall_nameIn(List<String> values) {
            addCriterion("mall_name in", values, "mall_name");
            return (Criteria) this;
        }

        public Criteria andMall_nameNotIn(List<String> values) {
            addCriterion("mall_name not in", values, "mall_name");
            return (Criteria) this;
        }

        public Criteria andMall_nameBetween(String value1, String value2) {
            addCriterion("mall_name between", value1, value2, "mall_name");
            return (Criteria) this;
        }

        public Criteria andMall_nameNotBetween(String value1, String value2) {
            addCriterion("mall_name not between", value1, value2, "mall_name");
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

        public Criteria andPlat_nameIsNull() {
            addCriterion("plat_name is null");
            return (Criteria) this;
        }

        public Criteria andPlat_nameIsNotNull() {
            addCriterion("plat_name is not null");
            return (Criteria) this;
        }

        public Criteria andPlat_nameEqualTo(String value) {
            addCriterion("plat_name =", value, "plat_name");
            return (Criteria) this;
        }

        public Criteria andPlat_nameNotEqualTo(String value) {
            addCriterion("plat_name <>", value, "plat_name");
            return (Criteria) this;
        }

        public Criteria andPlat_nameGreaterThan(String value) {
            addCriterion("plat_name >", value, "plat_name");
            return (Criteria) this;
        }

        public Criteria andPlat_nameGreaterThanOrEqualTo(String value) {
            addCriterion("plat_name >=", value, "plat_name");
            return (Criteria) this;
        }

        public Criteria andPlat_nameLessThan(String value) {
            addCriterion("plat_name <", value, "plat_name");
            return (Criteria) this;
        }

        public Criteria andPlat_nameLessThanOrEqualTo(String value) {
            addCriterion("plat_name <=", value, "plat_name");
            return (Criteria) this;
        }

        public Criteria andPlat_nameLike(String value) {
            addCriterion("plat_name like", value, "plat_name");
            return (Criteria) this;
        }

        public Criteria andPlat_nameNotLike(String value) {
            addCriterion("plat_name not like", value, "plat_name");
            return (Criteria) this;
        }

        public Criteria andPlat_nameIn(List<String> values) {
            addCriterion("plat_name in", values, "plat_name");
            return (Criteria) this;
        }

        public Criteria andPlat_nameNotIn(List<String> values) {
            addCriterion("plat_name not in", values, "plat_name");
            return (Criteria) this;
        }

        public Criteria andPlat_nameBetween(String value1, String value2) {
            addCriterion("plat_name between", value1, value2, "plat_name");
            return (Criteria) this;
        }

        public Criteria andPlat_nameNotBetween(String value1, String value2) {
            addCriterion("plat_name not between", value1, value2, "plat_name");
            return (Criteria) this;
        }

        public Criteria andPlat_typeIsNull() {
            addCriterion("plat_type is null");
            return (Criteria) this;
        }

        public Criteria andPlat_typeIsNotNull() {
            addCriterion("plat_type is not null");
            return (Criteria) this;
        }

        public Criteria andPlat_typeEqualTo(String value) {
            addCriterion("plat_type =", value, "plat_type");
            return (Criteria) this;
        }

        public Criteria andPlat_typeNotEqualTo(String value) {
            addCriterion("plat_type <>", value, "plat_type");
            return (Criteria) this;
        }

        public Criteria andPlat_typeGreaterThan(String value) {
            addCriterion("plat_type >", value, "plat_type");
            return (Criteria) this;
        }

        public Criteria andPlat_typeGreaterThanOrEqualTo(String value) {
            addCriterion("plat_type >=", value, "plat_type");
            return (Criteria) this;
        }

        public Criteria andPlat_typeLessThan(String value) {
            addCriterion("plat_type <", value, "plat_type");
            return (Criteria) this;
        }

        public Criteria andPlat_typeLessThanOrEqualTo(String value) {
            addCriterion("plat_type <=", value, "plat_type");
            return (Criteria) this;
        }

        public Criteria andPlat_typeLike(String value) {
            addCriterion("plat_type like", value, "plat_type");
            return (Criteria) this;
        }

        public Criteria andPlat_typeNotLike(String value) {
            addCriterion("plat_type not like", value, "plat_type");
            return (Criteria) this;
        }

        public Criteria andPlat_typeIn(List<String> values) {
            addCriterion("plat_type in", values, "plat_type");
            return (Criteria) this;
        }

        public Criteria andPlat_typeNotIn(List<String> values) {
            addCriterion("plat_type not in", values, "plat_type");
            return (Criteria) this;
        }

        public Criteria andPlat_typeBetween(String value1, String value2) {
            addCriterion("plat_type between", value1, value2, "plat_type");
            return (Criteria) this;
        }

        public Criteria andPlat_typeNotBetween(String value1, String value2) {
            addCriterion("plat_type not between", value1, value2, "plat_type");
            return (Criteria) this;
        }

        public Criteria andPlat_pwdIsNull() {
            addCriterion("plat_pwd is null");
            return (Criteria) this;
        }

        public Criteria andPlat_pwdIsNotNull() {
            addCriterion("plat_pwd is not null");
            return (Criteria) this;
        }

        public Criteria andPlat_pwdEqualTo(String value) {
            addCriterion("plat_pwd =", value, "plat_pwd");
            return (Criteria) this;
        }

        public Criteria andPlat_pwdNotEqualTo(String value) {
            addCriterion("plat_pwd <>", value, "plat_pwd");
            return (Criteria) this;
        }

        public Criteria andPlat_pwdGreaterThan(String value) {
            addCriterion("plat_pwd >", value, "plat_pwd");
            return (Criteria) this;
        }

        public Criteria andPlat_pwdGreaterThanOrEqualTo(String value) {
            addCriterion("plat_pwd >=", value, "plat_pwd");
            return (Criteria) this;
        }

        public Criteria andPlat_pwdLessThan(String value) {
            addCriterion("plat_pwd <", value, "plat_pwd");
            return (Criteria) this;
        }

        public Criteria andPlat_pwdLessThanOrEqualTo(String value) {
            addCriterion("plat_pwd <=", value, "plat_pwd");
            return (Criteria) this;
        }

        public Criteria andPlat_pwdLike(String value) {
            addCriterion("plat_pwd like", value, "plat_pwd");
            return (Criteria) this;
        }

        public Criteria andPlat_pwdNotLike(String value) {
            addCriterion("plat_pwd not like", value, "plat_pwd");
            return (Criteria) this;
        }

        public Criteria andPlat_pwdIn(List<String> values) {
            addCriterion("plat_pwd in", values, "plat_pwd");
            return (Criteria) this;
        }

        public Criteria andPlat_pwdNotIn(List<String> values) {
            addCriterion("plat_pwd not in", values, "plat_pwd");
            return (Criteria) this;
        }

        public Criteria andPlat_pwdBetween(String value1, String value2) {
            addCriterion("plat_pwd between", value1, value2, "plat_pwd");
            return (Criteria) this;
        }

        public Criteria andPlat_pwdNotBetween(String value1, String value2) {
            addCriterion("plat_pwd not between", value1, value2, "plat_pwd");
            return (Criteria) this;
        }

        public Criteria andCompany_attrIsNull() {
            addCriterion("company_attr is null");
            return (Criteria) this;
        }

        public Criteria andCompany_attrIsNotNull() {
            addCriterion("company_attr is not null");
            return (Criteria) this;
        }

        public Criteria andCompany_attrEqualTo(String value) {
            addCriterion("company_attr =", value, "company_attr");
            return (Criteria) this;
        }

        public Criteria andCompany_attrNotEqualTo(String value) {
            addCriterion("company_attr <>", value, "company_attr");
            return (Criteria) this;
        }

        public Criteria andCompany_attrGreaterThan(String value) {
            addCriterion("company_attr >", value, "company_attr");
            return (Criteria) this;
        }

        public Criteria andCompany_attrGreaterThanOrEqualTo(String value) {
            addCriterion("company_attr >=", value, "company_attr");
            return (Criteria) this;
        }

        public Criteria andCompany_attrLessThan(String value) {
            addCriterion("company_attr <", value, "company_attr");
            return (Criteria) this;
        }

        public Criteria andCompany_attrLessThanOrEqualTo(String value) {
            addCriterion("company_attr <=", value, "company_attr");
            return (Criteria) this;
        }

        public Criteria andCompany_attrLike(String value) {
            addCriterion("company_attr like", value, "company_attr");
            return (Criteria) this;
        }

        public Criteria andCompany_attrNotLike(String value) {
            addCriterion("company_attr not like", value, "company_attr");
            return (Criteria) this;
        }

        public Criteria andCompany_attrIn(List<String> values) {
            addCriterion("company_attr in", values, "company_attr");
            return (Criteria) this;
        }

        public Criteria andCompany_attrNotIn(List<String> values) {
            addCriterion("company_attr not in", values, "company_attr");
            return (Criteria) this;
        }

        public Criteria andCompany_attrBetween(String value1, String value2) {
            addCriterion("company_attr between", value1, value2, "company_attr");
            return (Criteria) this;
        }

        public Criteria andCompany_attrNotBetween(String value1, String value2) {
            addCriterion("company_attr not between", value1, value2, "company_attr");
            return (Criteria) this;
        }

        public Criteria andCompany_nameIsNull() {
            addCriterion("company_name is null");
            return (Criteria) this;
        }

        public Criteria andCompany_nameIsNotNull() {
            addCriterion("company_name is not null");
            return (Criteria) this;
        }

        public Criteria andCompany_nameEqualTo(String value) {
            addCriterion("company_name =", value, "company_name");
            return (Criteria) this;
        }

        public Criteria andCompany_nameNotEqualTo(String value) {
            addCriterion("company_name <>", value, "company_name");
            return (Criteria) this;
        }

        public Criteria andCompany_nameGreaterThan(String value) {
            addCriterion("company_name >", value, "company_name");
            return (Criteria) this;
        }

        public Criteria andCompany_nameGreaterThanOrEqualTo(String value) {
            addCriterion("company_name >=", value, "company_name");
            return (Criteria) this;
        }

        public Criteria andCompany_nameLessThan(String value) {
            addCriterion("company_name <", value, "company_name");
            return (Criteria) this;
        }

        public Criteria andCompany_nameLessThanOrEqualTo(String value) {
            addCriterion("company_name <=", value, "company_name");
            return (Criteria) this;
        }

        public Criteria andCompany_nameLike(String value) {
            addCriterion("company_name like", value, "company_name");
            return (Criteria) this;
        }

        public Criteria andCompany_nameNotLike(String value) {
            addCriterion("company_name not like", value, "company_name");
            return (Criteria) this;
        }

        public Criteria andCompany_nameIn(List<String> values) {
            addCriterion("company_name in", values, "company_name");
            return (Criteria) this;
        }

        public Criteria andCompany_nameNotIn(List<String> values) {
            addCriterion("company_name not in", values, "company_name");
            return (Criteria) this;
        }

        public Criteria andCompany_nameBetween(String value1, String value2) {
            addCriterion("company_name between", value1, value2, "company_name");
            return (Criteria) this;
        }

        public Criteria andCompany_nameNotBetween(String value1, String value2) {
            addCriterion("company_name not between", value1, value2, "company_name");
            return (Criteria) this;
        }

        public Criteria andCompany_capitalIsNull() {
            addCriterion("company_capital is null");
            return (Criteria) this;
        }

        public Criteria andCompany_capitalIsNotNull() {
            addCriterion("company_capital is not null");
            return (Criteria) this;
        }

        public Criteria andCompany_capitalEqualTo(String value) {
            addCriterion("company_capital =", value, "company_capital");
            return (Criteria) this;
        }

        public Criteria andCompany_capitalNotEqualTo(String value) {
            addCriterion("company_capital <>", value, "company_capital");
            return (Criteria) this;
        }

        public Criteria andCompany_capitalGreaterThan(String value) {
            addCriterion("company_capital >", value, "company_capital");
            return (Criteria) this;
        }

        public Criteria andCompany_capitalGreaterThanOrEqualTo(String value) {
            addCriterion("company_capital >=", value, "company_capital");
            return (Criteria) this;
        }

        public Criteria andCompany_capitalLessThan(String value) {
            addCriterion("company_capital <", value, "company_capital");
            return (Criteria) this;
        }

        public Criteria andCompany_capitalLessThanOrEqualTo(String value) {
            addCriterion("company_capital <=", value, "company_capital");
            return (Criteria) this;
        }

        public Criteria andCompany_capitalLike(String value) {
            addCriterion("company_capital like", value, "company_capital");
            return (Criteria) this;
        }

        public Criteria andCompany_capitalNotLike(String value) {
            addCriterion("company_capital not like", value, "company_capital");
            return (Criteria) this;
        }

        public Criteria andCompany_capitalIn(List<String> values) {
            addCriterion("company_capital in", values, "company_capital");
            return (Criteria) this;
        }

        public Criteria andCompany_capitalNotIn(List<String> values) {
            addCriterion("company_capital not in", values, "company_capital");
            return (Criteria) this;
        }

        public Criteria andCompany_capitalBetween(String value1, String value2) {
            addCriterion("company_capital between", value1, value2, "company_capital");
            return (Criteria) this;
        }

        public Criteria andCompany_capitalNotBetween(String value1, String value2) {
            addCriterion("company_capital not between", value1, value2, "company_capital");
            return (Criteria) this;
        }

        public Criteria andCompany_licenseIsNull() {
            addCriterion("company_license is null");
            return (Criteria) this;
        }

        public Criteria andCompany_licenseIsNotNull() {
            addCriterion("company_license is not null");
            return (Criteria) this;
        }

        public Criteria andCompany_licenseEqualTo(String value) {
            addCriterion("company_license =", value, "company_license");
            return (Criteria) this;
        }

        public Criteria andCompany_licenseNotEqualTo(String value) {
            addCriterion("company_license <>", value, "company_license");
            return (Criteria) this;
        }

        public Criteria andCompany_licenseGreaterThan(String value) {
            addCriterion("company_license >", value, "company_license");
            return (Criteria) this;
        }

        public Criteria andCompany_licenseGreaterThanOrEqualTo(String value) {
            addCriterion("company_license >=", value, "company_license");
            return (Criteria) this;
        }

        public Criteria andCompany_licenseLessThan(String value) {
            addCriterion("company_license <", value, "company_license");
            return (Criteria) this;
        }

        public Criteria andCompany_licenseLessThanOrEqualTo(String value) {
            addCriterion("company_license <=", value, "company_license");
            return (Criteria) this;
        }

        public Criteria andCompany_licenseLike(String value) {
            addCriterion("company_license like", value, "company_license");
            return (Criteria) this;
        }

        public Criteria andCompany_licenseNotLike(String value) {
            addCriterion("company_license not like", value, "company_license");
            return (Criteria) this;
        }

        public Criteria andCompany_licenseIn(List<String> values) {
            addCriterion("company_license in", values, "company_license");
            return (Criteria) this;
        }

        public Criteria andCompany_licenseNotIn(List<String> values) {
            addCriterion("company_license not in", values, "company_license");
            return (Criteria) this;
        }

        public Criteria andCompany_licenseBetween(String value1, String value2) {
            addCriterion("company_license between", value1, value2, "company_license");
            return (Criteria) this;
        }

        public Criteria andCompany_licenseNotBetween(String value1, String value2) {
            addCriterion("company_license not between", value1, value2, "company_license");
            return (Criteria) this;
        }

        public Criteria andLegal_nameIsNull() {
            addCriterion("legal_name is null");
            return (Criteria) this;
        }

        public Criteria andLegal_nameIsNotNull() {
            addCriterion("legal_name is not null");
            return (Criteria) this;
        }

        public Criteria andLegal_nameEqualTo(String value) {
            addCriterion("legal_name =", value, "legal_name");
            return (Criteria) this;
        }

        public Criteria andLegal_nameNotEqualTo(String value) {
            addCriterion("legal_name <>", value, "legal_name");
            return (Criteria) this;
        }

        public Criteria andLegal_nameGreaterThan(String value) {
            addCriterion("legal_name >", value, "legal_name");
            return (Criteria) this;
        }

        public Criteria andLegal_nameGreaterThanOrEqualTo(String value) {
            addCriterion("legal_name >=", value, "legal_name");
            return (Criteria) this;
        }

        public Criteria andLegal_nameLessThan(String value) {
            addCriterion("legal_name <", value, "legal_name");
            return (Criteria) this;
        }

        public Criteria andLegal_nameLessThanOrEqualTo(String value) {
            addCriterion("legal_name <=", value, "legal_name");
            return (Criteria) this;
        }

        public Criteria andLegal_nameLike(String value) {
            addCriterion("legal_name like", value, "legal_name");
            return (Criteria) this;
        }

        public Criteria andLegal_nameNotLike(String value) {
            addCriterion("legal_name not like", value, "legal_name");
            return (Criteria) this;
        }

        public Criteria andLegal_nameIn(List<String> values) {
            addCriterion("legal_name in", values, "legal_name");
            return (Criteria) this;
        }

        public Criteria andLegal_nameNotIn(List<String> values) {
            addCriterion("legal_name not in", values, "legal_name");
            return (Criteria) this;
        }

        public Criteria andLegal_nameBetween(String value1, String value2) {
            addCriterion("legal_name between", value1, value2, "legal_name");
            return (Criteria) this;
        }

        public Criteria andLegal_nameNotBetween(String value1, String value2) {
            addCriterion("legal_name not between", value1, value2, "legal_name");
            return (Criteria) this;
        }

        public Criteria andLegal_license_typeIsNull() {
            addCriterion("legal_license_type is null");
            return (Criteria) this;
        }

        public Criteria andLegal_license_typeIsNotNull() {
            addCriterion("legal_license_type is not null");
            return (Criteria) this;
        }

        public Criteria andLegal_license_typeEqualTo(String value) {
            addCriterion("legal_license_type =", value, "legal_license_type");
            return (Criteria) this;
        }

        public Criteria andLegal_license_typeNotEqualTo(String value) {
            addCriterion("legal_license_type <>", value, "legal_license_type");
            return (Criteria) this;
        }

        public Criteria andLegal_license_typeGreaterThan(String value) {
            addCriterion("legal_license_type >", value, "legal_license_type");
            return (Criteria) this;
        }

        public Criteria andLegal_license_typeGreaterThanOrEqualTo(String value) {
            addCriterion("legal_license_type >=", value, "legal_license_type");
            return (Criteria) this;
        }

        public Criteria andLegal_license_typeLessThan(String value) {
            addCriterion("legal_license_type <", value, "legal_license_type");
            return (Criteria) this;
        }

        public Criteria andLegal_license_typeLessThanOrEqualTo(String value) {
            addCriterion("legal_license_type <=", value, "legal_license_type");
            return (Criteria) this;
        }

        public Criteria andLegal_license_typeLike(String value) {
            addCriterion("legal_license_type like", value, "legal_license_type");
            return (Criteria) this;
        }

        public Criteria andLegal_license_typeNotLike(String value) {
            addCriterion("legal_license_type not like", value, "legal_license_type");
            return (Criteria) this;
        }

        public Criteria andLegal_license_typeIn(List<String> values) {
            addCriterion("legal_license_type in", values, "legal_license_type");
            return (Criteria) this;
        }

        public Criteria andLegal_license_typeNotIn(List<String> values) {
            addCriterion("legal_license_type not in", values, "legal_license_type");
            return (Criteria) this;
        }

        public Criteria andLegal_license_typeBetween(String value1, String value2) {
            addCriterion("legal_license_type between", value1, value2, "legal_license_type");
            return (Criteria) this;
        }

        public Criteria andLegal_license_typeNotBetween(String value1, String value2) {
            addCriterion("legal_license_type not between", value1, value2, "legal_license_type");
            return (Criteria) this;
        }

        public Criteria andLegal_licenseIsNull() {
            addCriterion("legal_license is null");
            return (Criteria) this;
        }

        public Criteria andLegal_licenseIsNotNull() {
            addCriterion("legal_license is not null");
            return (Criteria) this;
        }

        public Criteria andLegal_licenseEqualTo(String value) {
            addCriterion("legal_license =", value, "legal_license");
            return (Criteria) this;
        }

        public Criteria andLegal_licenseNotEqualTo(String value) {
            addCriterion("legal_license <>", value, "legal_license");
            return (Criteria) this;
        }

        public Criteria andLegal_licenseGreaterThan(String value) {
            addCriterion("legal_license >", value, "legal_license");
            return (Criteria) this;
        }

        public Criteria andLegal_licenseGreaterThanOrEqualTo(String value) {
            addCriterion("legal_license >=", value, "legal_license");
            return (Criteria) this;
        }

        public Criteria andLegal_licenseLessThan(String value) {
            addCriterion("legal_license <", value, "legal_license");
            return (Criteria) this;
        }

        public Criteria andLegal_licenseLessThanOrEqualTo(String value) {
            addCriterion("legal_license <=", value, "legal_license");
            return (Criteria) this;
        }

        public Criteria andLegal_licenseLike(String value) {
            addCriterion("legal_license like", value, "legal_license");
            return (Criteria) this;
        }

        public Criteria andLegal_licenseNotLike(String value) {
            addCriterion("legal_license not like", value, "legal_license");
            return (Criteria) this;
        }

        public Criteria andLegal_licenseIn(List<String> values) {
            addCriterion("legal_license in", values, "legal_license");
            return (Criteria) this;
        }

        public Criteria andLegal_licenseNotIn(List<String> values) {
            addCriterion("legal_license not in", values, "legal_license");
            return (Criteria) this;
        }

        public Criteria andLegal_licenseBetween(String value1, String value2) {
            addCriterion("legal_license between", value1, value2, "legal_license");
            return (Criteria) this;
        }

        public Criteria andLegal_licenseNotBetween(String value1, String value2) {
            addCriterion("legal_license not between", value1, value2, "legal_license");
            return (Criteria) this;
        }

        public Criteria andTax_licenseIsNull() {
            addCriterion("tax_license is null");
            return (Criteria) this;
        }

        public Criteria andTax_licenseIsNotNull() {
            addCriterion("tax_license is not null");
            return (Criteria) this;
        }

        public Criteria andTax_licenseEqualTo(String value) {
            addCriterion("tax_license =", value, "tax_license");
            return (Criteria) this;
        }

        public Criteria andTax_licenseNotEqualTo(String value) {
            addCriterion("tax_license <>", value, "tax_license");
            return (Criteria) this;
        }

        public Criteria andTax_licenseGreaterThan(String value) {
            addCriterion("tax_license >", value, "tax_license");
            return (Criteria) this;
        }

        public Criteria andTax_licenseGreaterThanOrEqualTo(String value) {
            addCriterion("tax_license >=", value, "tax_license");
            return (Criteria) this;
        }

        public Criteria andTax_licenseLessThan(String value) {
            addCriterion("tax_license <", value, "tax_license");
            return (Criteria) this;
        }

        public Criteria andTax_licenseLessThanOrEqualTo(String value) {
            addCriterion("tax_license <=", value, "tax_license");
            return (Criteria) this;
        }

        public Criteria andTax_licenseLike(String value) {
            addCriterion("tax_license like", value, "tax_license");
            return (Criteria) this;
        }

        public Criteria andTax_licenseNotLike(String value) {
            addCriterion("tax_license not like", value, "tax_license");
            return (Criteria) this;
        }

        public Criteria andTax_licenseIn(List<String> values) {
            addCriterion("tax_license in", values, "tax_license");
            return (Criteria) this;
        }

        public Criteria andTax_licenseNotIn(List<String> values) {
            addCriterion("tax_license not in", values, "tax_license");
            return (Criteria) this;
        }

        public Criteria andTax_licenseBetween(String value1, String value2) {
            addCriterion("tax_license between", value1, value2, "tax_license");
            return (Criteria) this;
        }

        public Criteria andTax_licenseNotBetween(String value1, String value2) {
            addCriterion("tax_license not between", value1, value2, "tax_license");
            return (Criteria) this;
        }

        public Criteria andTax_numberIsNull() {
            addCriterion("tax_number is null");
            return (Criteria) this;
        }

        public Criteria andTax_numberIsNotNull() {
            addCriterion("tax_number is not null");
            return (Criteria) this;
        }

        public Criteria andTax_numberEqualTo(String value) {
            addCriterion("tax_number =", value, "tax_number");
            return (Criteria) this;
        }

        public Criteria andTax_numberNotEqualTo(String value) {
            addCriterion("tax_number <>", value, "tax_number");
            return (Criteria) this;
        }

        public Criteria andTax_numberGreaterThan(String value) {
            addCriterion("tax_number >", value, "tax_number");
            return (Criteria) this;
        }

        public Criteria andTax_numberGreaterThanOrEqualTo(String value) {
            addCriterion("tax_number >=", value, "tax_number");
            return (Criteria) this;
        }

        public Criteria andTax_numberLessThan(String value) {
            addCriterion("tax_number <", value, "tax_number");
            return (Criteria) this;
        }

        public Criteria andTax_numberLessThanOrEqualTo(String value) {
            addCriterion("tax_number <=", value, "tax_number");
            return (Criteria) this;
        }

        public Criteria andTax_numberLike(String value) {
            addCriterion("tax_number like", value, "tax_number");
            return (Criteria) this;
        }

        public Criteria andTax_numberNotLike(String value) {
            addCriterion("tax_number not like", value, "tax_number");
            return (Criteria) this;
        }

        public Criteria andTax_numberIn(List<String> values) {
            addCriterion("tax_number in", values, "tax_number");
            return (Criteria) this;
        }

        public Criteria andTax_numberNotIn(List<String> values) {
            addCriterion("tax_number not in", values, "tax_number");
            return (Criteria) this;
        }

        public Criteria andTax_numberBetween(String value1, String value2) {
            addCriterion("tax_number between", value1, value2, "tax_number");
            return (Criteria) this;
        }

        public Criteria andTax_numberNotBetween(String value1, String value2) {
            addCriterion("tax_number not between", value1, value2, "tax_number");
            return (Criteria) this;
        }

        public Criteria andOrg_licenseIsNull() {
            addCriterion("org_license is null");
            return (Criteria) this;
        }

        public Criteria andOrg_licenseIsNotNull() {
            addCriterion("org_license is not null");
            return (Criteria) this;
        }

        public Criteria andOrg_licenseEqualTo(String value) {
            addCriterion("org_license =", value, "org_license");
            return (Criteria) this;
        }

        public Criteria andOrg_licenseNotEqualTo(String value) {
            addCriterion("org_license <>", value, "org_license");
            return (Criteria) this;
        }

        public Criteria andOrg_licenseGreaterThan(String value) {
            addCriterion("org_license >", value, "org_license");
            return (Criteria) this;
        }

        public Criteria andOrg_licenseGreaterThanOrEqualTo(String value) {
            addCriterion("org_license >=", value, "org_license");
            return (Criteria) this;
        }

        public Criteria andOrg_licenseLessThan(String value) {
            addCriterion("org_license <", value, "org_license");
            return (Criteria) this;
        }

        public Criteria andOrg_licenseLessThanOrEqualTo(String value) {
            addCriterion("org_license <=", value, "org_license");
            return (Criteria) this;
        }

        public Criteria andOrg_licenseLike(String value) {
            addCriterion("org_license like", value, "org_license");
            return (Criteria) this;
        }

        public Criteria andOrg_licenseNotLike(String value) {
            addCriterion("org_license not like", value, "org_license");
            return (Criteria) this;
        }

        public Criteria andOrg_licenseIn(List<String> values) {
            addCriterion("org_license in", values, "org_license");
            return (Criteria) this;
        }

        public Criteria andOrg_licenseNotIn(List<String> values) {
            addCriterion("org_license not in", values, "org_license");
            return (Criteria) this;
        }

        public Criteria andOrg_licenseBetween(String value1, String value2) {
            addCriterion("org_license between", value1, value2, "org_license");
            return (Criteria) this;
        }

        public Criteria andOrg_licenseNotBetween(String value1, String value2) {
            addCriterion("org_license not between", value1, value2, "org_license");
            return (Criteria) this;
        }

        public Criteria andPlat_stateIsNull() {
            addCriterion("plat_state is null");
            return (Criteria) this;
        }

        public Criteria andPlat_stateIsNotNull() {
            addCriterion("plat_state is not null");
            return (Criteria) this;
        }

        public Criteria andPlat_stateEqualTo(String value) {
            addCriterion("plat_state =", value, "plat_state");
            return (Criteria) this;
        }

        public Criteria andPlat_stateNotEqualTo(String value) {
            addCriterion("plat_state <>", value, "plat_state");
            return (Criteria) this;
        }

        public Criteria andPlat_stateGreaterThan(String value) {
            addCriterion("plat_state >", value, "plat_state");
            return (Criteria) this;
        }

        public Criteria andPlat_stateGreaterThanOrEqualTo(String value) {
            addCriterion("plat_state >=", value, "plat_state");
            return (Criteria) this;
        }

        public Criteria andPlat_stateLessThan(String value) {
            addCriterion("plat_state <", value, "plat_state");
            return (Criteria) this;
        }

        public Criteria andPlat_stateLessThanOrEqualTo(String value) {
            addCriterion("plat_state <=", value, "plat_state");
            return (Criteria) this;
        }

        public Criteria andPlat_stateLike(String value) {
            addCriterion("plat_state like", value, "plat_state");
            return (Criteria) this;
        }

        public Criteria andPlat_stateNotLike(String value) {
            addCriterion("plat_state not like", value, "plat_state");
            return (Criteria) this;
        }

        public Criteria andPlat_stateIn(List<String> values) {
            addCriterion("plat_state in", values, "plat_state");
            return (Criteria) this;
        }

        public Criteria andPlat_stateNotIn(List<String> values) {
            addCriterion("plat_state not in", values, "plat_state");
            return (Criteria) this;
        }

        public Criteria andPlat_stateBetween(String value1, String value2) {
            addCriterion("plat_state between", value1, value2, "plat_state");
            return (Criteria) this;
        }

        public Criteria andPlat_stateNotBetween(String value1, String value2) {
            addCriterion("plat_state not between", value1, value2, "plat_state");
            return (Criteria) this;
        }

        public Criteria andCompany_establish_dateIsNull() {
            addCriterion("company_establish_date is null");
            return (Criteria) this;
        }

        public Criteria andCompany_establish_dateIsNotNull() {
            addCriterion("company_establish_date is not null");
            return (Criteria) this;
        }

        public Criteria andCompany_establish_dateEqualTo(Date value) {
            addCriterionForJDBCDate("company_establish_date =", value, "company_establish_date");
            return (Criteria) this;
        }

        public Criteria andCompany_establish_dateNotEqualTo(Date value) {
            addCriterionForJDBCDate("company_establish_date <>", value, "company_establish_date");
            return (Criteria) this;
        }

        public Criteria andCompany_establish_dateGreaterThan(Date value) {
            addCriterionForJDBCDate("company_establish_date >", value, "company_establish_date");
            return (Criteria) this;
        }

        public Criteria andCompany_establish_dateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("company_establish_date >=", value, "company_establish_date");
            return (Criteria) this;
        }

        public Criteria andCompany_establish_dateLessThan(Date value) {
            addCriterionForJDBCDate("company_establish_date <", value, "company_establish_date");
            return (Criteria) this;
        }

        public Criteria andCompany_establish_dateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("company_establish_date <=", value, "company_establish_date");
            return (Criteria) this;
        }

        public Criteria andCompany_establish_dateIn(List<Date> values) {
            addCriterionForJDBCDate("company_establish_date in", values, "company_establish_date");
            return (Criteria) this;
        }

        public Criteria andCompany_establish_dateNotIn(List<Date> values) {
            addCriterionForJDBCDate("company_establish_date not in", values, "company_establish_date");
            return (Criteria) this;
        }

        public Criteria andCompany_establish_dateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("company_establish_date between", value1, value2, "company_establish_date");
            return (Criteria) this;
        }

        public Criteria andCompany_establish_dateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("company_establish_date not between", value1, value2, "company_establish_date");
            return (Criteria) this;
        }

        public Criteria andPlat_line_dateIsNull() {
            addCriterion("plat_line_date is null");
            return (Criteria) this;
        }

        public Criteria andPlat_line_dateIsNotNull() {
            addCriterion("plat_line_date is not null");
            return (Criteria) this;
        }

        public Criteria andPlat_line_dateEqualTo(Date value) {
            addCriterionForJDBCDate("plat_line_date =", value, "plat_line_date");
            return (Criteria) this;
        }

        public Criteria andPlat_line_dateNotEqualTo(Date value) {
            addCriterionForJDBCDate("plat_line_date <>", value, "plat_line_date");
            return (Criteria) this;
        }

        public Criteria andPlat_line_dateGreaterThan(Date value) {
            addCriterionForJDBCDate("plat_line_date >", value, "plat_line_date");
            return (Criteria) this;
        }

        public Criteria andPlat_line_dateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("plat_line_date >=", value, "plat_line_date");
            return (Criteria) this;
        }

        public Criteria andPlat_line_dateLessThan(Date value) {
            addCriterionForJDBCDate("plat_line_date <", value, "plat_line_date");
            return (Criteria) this;
        }

        public Criteria andPlat_line_dateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("plat_line_date <=", value, "plat_line_date");
            return (Criteria) this;
        }

        public Criteria andPlat_line_dateIn(List<Date> values) {
            addCriterionForJDBCDate("plat_line_date in", values, "plat_line_date");
            return (Criteria) this;
        }

        public Criteria andPlat_line_dateNotIn(List<Date> values) {
            addCriterionForJDBCDate("plat_line_date not in", values, "plat_line_date");
            return (Criteria) this;
        }

        public Criteria andPlat_line_dateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("plat_line_date between", value1, value2, "plat_line_date");
            return (Criteria) this;
        }

        public Criteria andPlat_line_dateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("plat_line_date not between", value1, value2, "plat_line_date");
            return (Criteria) this;
        }

        public Criteria andPlat_addressIsNull() {
            addCriterion("plat_address is null");
            return (Criteria) this;
        }

        public Criteria andPlat_addressIsNotNull() {
            addCriterion("plat_address is not null");
            return (Criteria) this;
        }

        public Criteria andPlat_addressEqualTo(String value) {
            addCriterion("plat_address =", value, "plat_address");
            return (Criteria) this;
        }

        public Criteria andPlat_addressNotEqualTo(String value) {
            addCriterion("plat_address <>", value, "plat_address");
            return (Criteria) this;
        }

        public Criteria andPlat_addressGreaterThan(String value) {
            addCriterion("plat_address >", value, "plat_address");
            return (Criteria) this;
        }

        public Criteria andPlat_addressGreaterThanOrEqualTo(String value) {
            addCriterion("plat_address >=", value, "plat_address");
            return (Criteria) this;
        }

        public Criteria andPlat_addressLessThan(String value) {
            addCriterion("plat_address <", value, "plat_address");
            return (Criteria) this;
        }

        public Criteria andPlat_addressLessThanOrEqualTo(String value) {
            addCriterion("plat_address <=", value, "plat_address");
            return (Criteria) this;
        }

        public Criteria andPlat_addressLike(String value) {
            addCriterion("plat_address like", value, "plat_address");
            return (Criteria) this;
        }

        public Criteria andPlat_addressNotLike(String value) {
            addCriterion("plat_address not like", value, "plat_address");
            return (Criteria) this;
        }

        public Criteria andPlat_addressIn(List<String> values) {
            addCriterion("plat_address in", values, "plat_address");
            return (Criteria) this;
        }

        public Criteria andPlat_addressNotIn(List<String> values) {
            addCriterion("plat_address not in", values, "plat_address");
            return (Criteria) this;
        }

        public Criteria andPlat_addressBetween(String value1, String value2) {
            addCriterion("plat_address between", value1, value2, "plat_address");
            return (Criteria) this;
        }

        public Criteria andPlat_addressNotBetween(String value1, String value2) {
            addCriterion("plat_address not between", value1, value2, "plat_address");
            return (Criteria) this;
        }

        public Criteria andPlat_busi_typeIsNull() {
            addCriterion("plat_busi_type is null");
            return (Criteria) this;
        }

        public Criteria andPlat_busi_typeIsNotNull() {
            addCriterion("plat_busi_type is not null");
            return (Criteria) this;
        }

        public Criteria andPlat_busi_typeEqualTo(String value) {
            addCriterion("plat_busi_type =", value, "plat_busi_type");
            return (Criteria) this;
        }

        public Criteria andPlat_busi_typeNotEqualTo(String value) {
            addCriterion("plat_busi_type <>", value, "plat_busi_type");
            return (Criteria) this;
        }

        public Criteria andPlat_busi_typeGreaterThan(String value) {
            addCriterion("plat_busi_type >", value, "plat_busi_type");
            return (Criteria) this;
        }

        public Criteria andPlat_busi_typeGreaterThanOrEqualTo(String value) {
            addCriterion("plat_busi_type >=", value, "plat_busi_type");
            return (Criteria) this;
        }

        public Criteria andPlat_busi_typeLessThan(String value) {
            addCriterion("plat_busi_type <", value, "plat_busi_type");
            return (Criteria) this;
        }

        public Criteria andPlat_busi_typeLessThanOrEqualTo(String value) {
            addCriterion("plat_busi_type <=", value, "plat_busi_type");
            return (Criteria) this;
        }

        public Criteria andPlat_busi_typeLike(String value) {
            addCriterion("plat_busi_type like", value, "plat_busi_type");
            return (Criteria) this;
        }

        public Criteria andPlat_busi_typeNotLike(String value) {
            addCriterion("plat_busi_type not like", value, "plat_busi_type");
            return (Criteria) this;
        }

        public Criteria andPlat_busi_typeIn(List<String> values) {
            addCriterion("plat_busi_type in", values, "plat_busi_type");
            return (Criteria) this;
        }

        public Criteria andPlat_busi_typeNotIn(List<String> values) {
            addCriterion("plat_busi_type not in", values, "plat_busi_type");
            return (Criteria) this;
        }

        public Criteria andPlat_busi_typeBetween(String value1, String value2) {
            addCriterion("plat_busi_type between", value1, value2, "plat_busi_type");
            return (Criteria) this;
        }

        public Criteria andPlat_busi_typeNotBetween(String value1, String value2) {
            addCriterion("plat_busi_type not between", value1, value2, "plat_busi_type");
            return (Criteria) this;
        }

        public Criteria andPlat_businessIsNull() {
            addCriterion("plat_business is null");
            return (Criteria) this;
        }

        public Criteria andPlat_businessIsNotNull() {
            addCriterion("plat_business is not null");
            return (Criteria) this;
        }

        public Criteria andPlat_businessEqualTo(String value) {
            addCriterion("plat_business =", value, "plat_business");
            return (Criteria) this;
        }

        public Criteria andPlat_businessNotEqualTo(String value) {
            addCriterion("plat_business <>", value, "plat_business");
            return (Criteria) this;
        }

        public Criteria andPlat_businessGreaterThan(String value) {
            addCriterion("plat_business >", value, "plat_business");
            return (Criteria) this;
        }

        public Criteria andPlat_businessGreaterThanOrEqualTo(String value) {
            addCriterion("plat_business >=", value, "plat_business");
            return (Criteria) this;
        }

        public Criteria andPlat_businessLessThan(String value) {
            addCriterion("plat_business <", value, "plat_business");
            return (Criteria) this;
        }

        public Criteria andPlat_businessLessThanOrEqualTo(String value) {
            addCriterion("plat_business <=", value, "plat_business");
            return (Criteria) this;
        }

        public Criteria andPlat_businessLike(String value) {
            addCriterion("plat_business like", value, "plat_business");
            return (Criteria) this;
        }

        public Criteria andPlat_businessNotLike(String value) {
            addCriterion("plat_business not like", value, "plat_business");
            return (Criteria) this;
        }

        public Criteria andPlat_businessIn(List<String> values) {
            addCriterion("plat_business in", values, "plat_business");
            return (Criteria) this;
        }

        public Criteria andPlat_businessNotIn(List<String> values) {
            addCriterion("plat_business not in", values, "plat_business");
            return (Criteria) this;
        }

        public Criteria andPlat_businessBetween(String value1, String value2) {
            addCriterion("plat_business between", value1, value2, "plat_business");
            return (Criteria) this;
        }

        public Criteria andPlat_businessNotBetween(String value1, String value2) {
            addCriterion("plat_business not between", value1, value2, "plat_business");
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

        public Criteria andActual_capitalIsNull() {
            addCriterion("actual_capital is null");
            return (Criteria) this;
        }

        public Criteria andActual_capitalIsNotNull() {
            addCriterion("actual_capital is not null");
            return (Criteria) this;
        }

        public Criteria andActual_capitalEqualTo(String value) {
            addCriterion("actual_capital =", value, "actual_capital");
            return (Criteria) this;
        }

        public Criteria andActual_capitalNotEqualTo(String value) {
            addCriterion("actual_capital <>", value, "actual_capital");
            return (Criteria) this;
        }

        public Criteria andActual_capitalGreaterThan(String value) {
            addCriterion("actual_capital >", value, "actual_capital");
            return (Criteria) this;
        }

        public Criteria andActual_capitalGreaterThanOrEqualTo(String value) {
            addCriterion("actual_capital >=", value, "actual_capital");
            return (Criteria) this;
        }

        public Criteria andActual_capitalLessThan(String value) {
            addCriterion("actual_capital <", value, "actual_capital");
            return (Criteria) this;
        }

        public Criteria andActual_capitalLessThanOrEqualTo(String value) {
            addCriterion("actual_capital <=", value, "actual_capital");
            return (Criteria) this;
        }

        public Criteria andActual_capitalLike(String value) {
            addCriterion("actual_capital like", value, "actual_capital");
            return (Criteria) this;
        }

        public Criteria andActual_capitalNotLike(String value) {
            addCriterion("actual_capital not like", value, "actual_capital");
            return (Criteria) this;
        }

        public Criteria andActual_capitalIn(List<String> values) {
            addCriterion("actual_capital in", values, "actual_capital");
            return (Criteria) this;
        }

        public Criteria andActual_capitalNotIn(List<String> values) {
            addCriterion("actual_capital not in", values, "actual_capital");
            return (Criteria) this;
        }

        public Criteria andActual_capitalBetween(String value1, String value2) {
            addCriterion("actual_capital between", value1, value2, "actual_capital");
            return (Criteria) this;
        }

        public Criteria andActual_capitalNotBetween(String value1, String value2) {
            addCriterion("actual_capital not between", value1, value2, "actual_capital");
            return (Criteria) this;
        }

        public Criteria andPlat_domainIsNull() {
            addCriterion("plat_domain is null");
            return (Criteria) this;
        }

        public Criteria andPlat_domainIsNotNull() {
            addCriterion("plat_domain is not null");
            return (Criteria) this;
        }

        public Criteria andPlat_domainEqualTo(String value) {
            addCriterion("plat_domain =", value, "plat_domain");
            return (Criteria) this;
        }

        public Criteria andPlat_domainNotEqualTo(String value) {
            addCriterion("plat_domain <>", value, "plat_domain");
            return (Criteria) this;
        }

        public Criteria andPlat_domainGreaterThan(String value) {
            addCriterion("plat_domain >", value, "plat_domain");
            return (Criteria) this;
        }

        public Criteria andPlat_domainGreaterThanOrEqualTo(String value) {
            addCriterion("plat_domain >=", value, "plat_domain");
            return (Criteria) this;
        }

        public Criteria andPlat_domainLessThan(String value) {
            addCriterion("plat_domain <", value, "plat_domain");
            return (Criteria) this;
        }

        public Criteria andPlat_domainLessThanOrEqualTo(String value) {
            addCriterion("plat_domain <=", value, "plat_domain");
            return (Criteria) this;
        }

        public Criteria andPlat_domainLike(String value) {
            addCriterion("plat_domain like", value, "plat_domain");
            return (Criteria) this;
        }

        public Criteria andPlat_domainNotLike(String value) {
            addCriterion("plat_domain not like", value, "plat_domain");
            return (Criteria) this;
        }

        public Criteria andPlat_domainIn(List<String> values) {
            addCriterion("plat_domain in", values, "plat_domain");
            return (Criteria) this;
        }

        public Criteria andPlat_domainNotIn(List<String> values) {
            addCriterion("plat_domain not in", values, "plat_domain");
            return (Criteria) this;
        }

        public Criteria andPlat_domainBetween(String value1, String value2) {
            addCriterion("plat_domain between", value1, value2, "plat_domain");
            return (Criteria) this;
        }

        public Criteria andPlat_domainNotBetween(String value1, String value2) {
            addCriterion("plat_domain not between", value1, value2, "plat_domain");
            return (Criteria) this;
        }

        public Criteria andInfo_securityIsNull() {
            addCriterion("info_security is null");
            return (Criteria) this;
        }

        public Criteria andInfo_securityIsNotNull() {
            addCriterion("info_security is not null");
            return (Criteria) this;
        }

        public Criteria andInfo_securityEqualTo(String value) {
            addCriterion("info_security =", value, "info_security");
            return (Criteria) this;
        }

        public Criteria andInfo_securityNotEqualTo(String value) {
            addCriterion("info_security <>", value, "info_security");
            return (Criteria) this;
        }

        public Criteria andInfo_securityGreaterThan(String value) {
            addCriterion("info_security >", value, "info_security");
            return (Criteria) this;
        }

        public Criteria andInfo_securityGreaterThanOrEqualTo(String value) {
            addCriterion("info_security >=", value, "info_security");
            return (Criteria) this;
        }

        public Criteria andInfo_securityLessThan(String value) {
            addCriterion("info_security <", value, "info_security");
            return (Criteria) this;
        }

        public Criteria andInfo_securityLessThanOrEqualTo(String value) {
            addCriterion("info_security <=", value, "info_security");
            return (Criteria) this;
        }

        public Criteria andInfo_securityLike(String value) {
            addCriterion("info_security like", value, "info_security");
            return (Criteria) this;
        }

        public Criteria andInfo_securityNotLike(String value) {
            addCriterion("info_security not like", value, "info_security");
            return (Criteria) this;
        }

        public Criteria andInfo_securityIn(List<String> values) {
            addCriterion("info_security in", values, "info_security");
            return (Criteria) this;
        }

        public Criteria andInfo_securityNotIn(List<String> values) {
            addCriterion("info_security not in", values, "info_security");
            return (Criteria) this;
        }

        public Criteria andInfo_securityBetween(String value1, String value2) {
            addCriterion("info_security between", value1, value2, "info_security");
            return (Criteria) this;
        }

        public Criteria andInfo_securityNotBetween(String value1, String value2) {
            addCriterion("info_security not between", value1, value2, "info_security");
            return (Criteria) this;
        }

        public Criteria andCheck_companyIsNull() {
            addCriterion("check_company is null");
            return (Criteria) this;
        }

        public Criteria andCheck_companyIsNotNull() {
            addCriterion("check_company is not null");
            return (Criteria) this;
        }

        public Criteria andCheck_companyEqualTo(String value) {
            addCriterion("check_company =", value, "check_company");
            return (Criteria) this;
        }

        public Criteria andCheck_companyNotEqualTo(String value) {
            addCriterion("check_company <>", value, "check_company");
            return (Criteria) this;
        }

        public Criteria andCheck_companyGreaterThan(String value) {
            addCriterion("check_company >", value, "check_company");
            return (Criteria) this;
        }

        public Criteria andCheck_companyGreaterThanOrEqualTo(String value) {
            addCriterion("check_company >=", value, "check_company");
            return (Criteria) this;
        }

        public Criteria andCheck_companyLessThan(String value) {
            addCriterion("check_company <", value, "check_company");
            return (Criteria) this;
        }

        public Criteria andCheck_companyLessThanOrEqualTo(String value) {
            addCriterion("check_company <=", value, "check_company");
            return (Criteria) this;
        }

        public Criteria andCheck_companyLike(String value) {
            addCriterion("check_company like", value, "check_company");
            return (Criteria) this;
        }

        public Criteria andCheck_companyNotLike(String value) {
            addCriterion("check_company not like", value, "check_company");
            return (Criteria) this;
        }

        public Criteria andCheck_companyIn(List<String> values) {
            addCriterion("check_company in", values, "check_company");
            return (Criteria) this;
        }

        public Criteria andCheck_companyNotIn(List<String> values) {
            addCriterion("check_company not in", values, "check_company");
            return (Criteria) this;
        }

        public Criteria andCheck_companyBetween(String value1, String value2) {
            addCriterion("check_company between", value1, value2, "check_company");
            return (Criteria) this;
        }

        public Criteria andCheck_companyNotBetween(String value1, String value2) {
            addCriterion("check_company not between", value1, value2, "check_company");
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

        public Criteria andAccount_verification_channelIsNull() {
            addCriterion("account_verification_channel is null");
            return (Criteria) this;
        }

        public Criteria andAccount_verification_channelIsNotNull() {
            addCriterion("account_verification_channel is not null");
            return (Criteria) this;
        }

        public Criteria andAccount_verification_channelEqualTo(String value) {
            addCriterion("account_verification_channel =", value, "account_verification_channel");
            return (Criteria) this;
        }

        public Criteria andAccount_verification_channelNotEqualTo(String value) {
            addCriterion("account_verification_channel <>", value, "account_verification_channel");
            return (Criteria) this;
        }

        public Criteria andAccount_verification_channelGreaterThan(String value) {
            addCriterion("account_verification_channel >", value, "account_verification_channel");
            return (Criteria) this;
        }

        public Criteria andAccount_verification_channelGreaterThanOrEqualTo(String value) {
            addCriterion("account_verification_channel >=", value, "account_verification_channel");
            return (Criteria) this;
        }

        public Criteria andAccount_verification_channelLessThan(String value) {
            addCriterion("account_verification_channel <", value, "account_verification_channel");
            return (Criteria) this;
        }

        public Criteria andAccount_verification_channelLessThanOrEqualTo(String value) {
            addCriterion("account_verification_channel <=", value, "account_verification_channel");
            return (Criteria) this;
        }

        public Criteria andAccount_verification_channelLike(String value) {
            addCriterion("account_verification_channel like", value, "account_verification_channel");
            return (Criteria) this;
        }

        public Criteria andAccount_verification_channelNotLike(String value) {
            addCriterion("account_verification_channel not like", value, "account_verification_channel");
            return (Criteria) this;
        }

        public Criteria andAccount_verification_channelIn(List<String> values) {
            addCriterion("account_verification_channel in", values, "account_verification_channel");
            return (Criteria) this;
        }

        public Criteria andAccount_verification_channelNotIn(List<String> values) {
            addCriterion("account_verification_channel not in", values, "account_verification_channel");
            return (Criteria) this;
        }

        public Criteria andAccount_verification_channelBetween(String value1, String value2) {
            addCriterion("account_verification_channel between", value1, value2, "account_verification_channel");
            return (Criteria) this;
        }

        public Criteria andAccount_verification_channelNotBetween(String value1, String value2) {
            addCriterion("account_verification_channel not between", value1, value2, "account_verification_channel");
            return (Criteria) this;
        }

        public Criteria andSocial_credit_codeIsNull() {
            addCriterion("social_credit_code is null");
            return (Criteria) this;
        }

        public Criteria andSocial_credit_codeIsNotNull() {
            addCriterion("social_credit_code is not null");
            return (Criteria) this;
        }

        public Criteria andSocial_credit_codeEqualTo(String value) {
            addCriterion("social_credit_code =", value, "social_credit_code");
            return (Criteria) this;
        }

        public Criteria andSocial_credit_codeNotEqualTo(String value) {
            addCriterion("social_credit_code <>", value, "social_credit_code");
            return (Criteria) this;
        }

        public Criteria andSocial_credit_codeGreaterThan(String value) {
            addCriterion("social_credit_code >", value, "social_credit_code");
            return (Criteria) this;
        }

        public Criteria andSocial_credit_codeGreaterThanOrEqualTo(String value) {
            addCriterion("social_credit_code >=", value, "social_credit_code");
            return (Criteria) this;
        }

        public Criteria andSocial_credit_codeLessThan(String value) {
            addCriterion("social_credit_code <", value, "social_credit_code");
            return (Criteria) this;
        }

        public Criteria andSocial_credit_codeLessThanOrEqualTo(String value) {
            addCriterion("social_credit_code <=", value, "social_credit_code");
            return (Criteria) this;
        }

        public Criteria andSocial_credit_codeLike(String value) {
            addCriterion("social_credit_code like", value, "social_credit_code");
            return (Criteria) this;
        }

        public Criteria andSocial_credit_codeNotLike(String value) {
            addCriterion("social_credit_code not like", value, "social_credit_code");
            return (Criteria) this;
        }

        public Criteria andSocial_credit_codeIn(List<String> values) {
            addCriterion("social_credit_code in", values, "social_credit_code");
            return (Criteria) this;
        }

        public Criteria andSocial_credit_codeNotIn(List<String> values) {
            addCriterion("social_credit_code not in", values, "social_credit_code");
            return (Criteria) this;
        }

        public Criteria andSocial_credit_codeBetween(String value1, String value2) {
            addCriterion("social_credit_code between", value1, value2, "social_credit_code");
            return (Criteria) this;
        }

        public Criteria andSocial_credit_codeNotBetween(String value1, String value2) {
            addCriterion("social_credit_code not between", value1, value2, "social_credit_code");
            return (Criteria) this;
        }

        public Criteria andAdd_notify_urlIsNull() {
            addCriterion("add_notify_url is null");
            return (Criteria) this;
        }

        public Criteria andAdd_notify_urlIsNotNull() {
            addCriterion("add_notify_url is not null");
            return (Criteria) this;
        }

        public Criteria andAdd_notify_urlEqualTo(String value) {
            addCriterion("add_notify_url =", value, "add_notify_url");
            return (Criteria) this;
        }

        public Criteria andAdd_notify_urlNotEqualTo(String value) {
            addCriterion("add_notify_url <>", value, "add_notify_url");
            return (Criteria) this;
        }

        public Criteria andAdd_notify_urlGreaterThan(String value) {
            addCriterion("add_notify_url >", value, "add_notify_url");
            return (Criteria) this;
        }

        public Criteria andAdd_notify_urlGreaterThanOrEqualTo(String value) {
            addCriterion("add_notify_url >=", value, "add_notify_url");
            return (Criteria) this;
        }

        public Criteria andAdd_notify_urlLessThan(String value) {
            addCriterion("add_notify_url <", value, "add_notify_url");
            return (Criteria) this;
        }

        public Criteria andAdd_notify_urlLessThanOrEqualTo(String value) {
            addCriterion("add_notify_url <=", value, "add_notify_url");
            return (Criteria) this;
        }

        public Criteria andAdd_notify_urlLike(String value) {
            addCriterion("add_notify_url like", value, "add_notify_url");
            return (Criteria) this;
        }

        public Criteria andAdd_notify_urlNotLike(String value) {
            addCriterion("add_notify_url not like", value, "add_notify_url");
            return (Criteria) this;
        }

        public Criteria andAdd_notify_urlIn(List<String> values) {
            addCriterion("add_notify_url in", values, "add_notify_url");
            return (Criteria) this;
        }

        public Criteria andAdd_notify_urlNotIn(List<String> values) {
            addCriterion("add_notify_url not in", values, "add_notify_url");
            return (Criteria) this;
        }

        public Criteria andAdd_notify_urlBetween(String value1, String value2) {
            addCriterion("add_notify_url between", value1, value2, "add_notify_url");
            return (Criteria) this;
        }

        public Criteria andAdd_notify_urlNotBetween(String value1, String value2) {
            addCriterion("add_notify_url not between", value1, value2, "add_notify_url");
            return (Criteria) this;
        }

        public Criteria andDaily_cut_urlIsNull() {
            addCriterion("daily_cut_url is null");
            return (Criteria) this;
        }

        public Criteria andDaily_cut_urlIsNotNull() {
            addCriterion("daily_cut_url is not null");
            return (Criteria) this;
        }

        public Criteria andDaily_cut_urlEqualTo(String value) {
            addCriterion("daily_cut_url =", value, "daily_cut_url");
            return (Criteria) this;
        }

        public Criteria andDaily_cut_urlNotEqualTo(String value) {
            addCriterion("daily_cut_url <>", value, "daily_cut_url");
            return (Criteria) this;
        }

        public Criteria andDaily_cut_urlGreaterThan(String value) {
            addCriterion("daily_cut_url >", value, "daily_cut_url");
            return (Criteria) this;
        }

        public Criteria andDaily_cut_urlGreaterThanOrEqualTo(String value) {
            addCriterion("daily_cut_url >=", value, "daily_cut_url");
            return (Criteria) this;
        }

        public Criteria andDaily_cut_urlLessThan(String value) {
            addCriterion("daily_cut_url <", value, "daily_cut_url");
            return (Criteria) this;
        }

        public Criteria andDaily_cut_urlLessThanOrEqualTo(String value) {
            addCriterion("daily_cut_url <=", value, "daily_cut_url");
            return (Criteria) this;
        }

        public Criteria andDaily_cut_urlLike(String value) {
            addCriterion("daily_cut_url like", value, "daily_cut_url");
            return (Criteria) this;
        }

        public Criteria andDaily_cut_urlNotLike(String value) {
            addCriterion("daily_cut_url not like", value, "daily_cut_url");
            return (Criteria) this;
        }

        public Criteria andDaily_cut_urlIn(List<String> values) {
            addCriterion("daily_cut_url in", values, "daily_cut_url");
            return (Criteria) this;
        }

        public Criteria andDaily_cut_urlNotIn(List<String> values) {
            addCriterion("daily_cut_url not in", values, "daily_cut_url");
            return (Criteria) this;
        }

        public Criteria andDaily_cut_urlBetween(String value1, String value2) {
            addCriterion("daily_cut_url between", value1, value2, "daily_cut_url");
            return (Criteria) this;
        }

        public Criteria andDaily_cut_urlNotBetween(String value1, String value2) {
            addCriterion("daily_cut_url not between", value1, value2, "daily_cut_url");
            return (Criteria) this;
        }

        public Criteria andPayment_accountIsNull() {
            addCriterion("payment_account is null");
            return (Criteria) this;
        }

        public Criteria andPayment_accountIsNotNull() {
            addCriterion("payment_account is not null");
            return (Criteria) this;
        }

        public Criteria andPayment_accountEqualTo(String value) {
            addCriterion("payment_account =", value, "payment_account");
            return (Criteria) this;
        }

        public Criteria andPayment_accountNotEqualTo(String value) {
            addCriterion("payment_account <>", value, "payment_account");
            return (Criteria) this;
        }

        public Criteria andPayment_accountGreaterThan(String value) {
            addCriterion("payment_account >", value, "payment_account");
            return (Criteria) this;
        }

        public Criteria andPayment_accountGreaterThanOrEqualTo(String value) {
            addCriterion("payment_account >=", value, "payment_account");
            return (Criteria) this;
        }

        public Criteria andPayment_accountLessThan(String value) {
            addCriterion("payment_account <", value, "payment_account");
            return (Criteria) this;
        }

        public Criteria andPayment_accountLessThanOrEqualTo(String value) {
            addCriterion("payment_account <=", value, "payment_account");
            return (Criteria) this;
        }

        public Criteria andPayment_accountLike(String value) {
            addCriterion("payment_account like", value, "payment_account");
            return (Criteria) this;
        }

        public Criteria andPayment_accountNotLike(String value) {
            addCriterion("payment_account not like", value, "payment_account");
            return (Criteria) this;
        }

        public Criteria andPayment_accountIn(List<String> values) {
            addCriterion("payment_account in", values, "payment_account");
            return (Criteria) this;
        }

        public Criteria andPayment_accountNotIn(List<String> values) {
            addCriterion("payment_account not in", values, "payment_account");
            return (Criteria) this;
        }

        public Criteria andPayment_accountBetween(String value1, String value2) {
            addCriterion("payment_account between", value1, value2, "payment_account");
            return (Criteria) this;
        }

        public Criteria andPayment_accountNotBetween(String value1, String value2) {
            addCriterion("payment_account not between", value1, value2, "payment_account");
            return (Criteria) this;
        }

        public Criteria andLiquidation_urlIsNull() {
            addCriterion("liquidation_url is null");
            return (Criteria) this;
        }

        public Criteria andLiquidation_urlIsNotNull() {
            addCriterion("liquidation_url is not null");
            return (Criteria) this;
        }

        public Criteria andLiquidation_urlEqualTo(String value) {
            addCriterion("liquidation_url =", value, "liquidation_url");
            return (Criteria) this;
        }

        public Criteria andLiquidation_urlNotEqualTo(String value) {
            addCriterion("liquidation_url <>", value, "liquidation_url");
            return (Criteria) this;
        }

        public Criteria andLiquidation_urlGreaterThan(String value) {
            addCriterion("liquidation_url >", value, "liquidation_url");
            return (Criteria) this;
        }

        public Criteria andLiquidation_urlGreaterThanOrEqualTo(String value) {
            addCriterion("liquidation_url >=", value, "liquidation_url");
            return (Criteria) this;
        }

        public Criteria andLiquidation_urlLessThan(String value) {
            addCriterion("liquidation_url <", value, "liquidation_url");
            return (Criteria) this;
        }

        public Criteria andLiquidation_urlLessThanOrEqualTo(String value) {
            addCriterion("liquidation_url <=", value, "liquidation_url");
            return (Criteria) this;
        }

        public Criteria andLiquidation_urlLike(String value) {
            addCriterion("liquidation_url like", value, "liquidation_url");
            return (Criteria) this;
        }

        public Criteria andLiquidation_urlNotLike(String value) {
            addCriterion("liquidation_url not like", value, "liquidation_url");
            return (Criteria) this;
        }

        public Criteria andLiquidation_urlIn(List<String> values) {
            addCriterion("liquidation_url in", values, "liquidation_url");
            return (Criteria) this;
        }

        public Criteria andLiquidation_urlNotIn(List<String> values) {
            addCriterion("liquidation_url not in", values, "liquidation_url");
            return (Criteria) this;
        }

        public Criteria andLiquidation_urlBetween(String value1, String value2) {
            addCriterion("liquidation_url between", value1, value2, "liquidation_url");
            return (Criteria) this;
        }

        public Criteria andLiquidation_urlNotBetween(String value1, String value2) {
            addCriterion("liquidation_url not between", value1, value2, "liquidation_url");
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
    }

    /**
     * plat_platinfo
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * plat_platinfo 2017-06-01
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