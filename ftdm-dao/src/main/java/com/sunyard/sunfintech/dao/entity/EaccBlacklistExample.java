package com.sunyard.sunfintech.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EaccBlacklistExample {
    /**
     * eacc_blacklist
     */
    protected String orderByClause;

    /**
     * eacc_blacklist
     */
    protected boolean distinct;

    /**
     * eacc_blacklist
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2017-06-01
     */
    public EaccBlacklistExample() {
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
     * eacc_blacklist 2017-06-01
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

        public Criteria andId_typeIsNull() {
            addCriterion("id_type is null");
            return (Criteria) this;
        }

        public Criteria andId_typeIsNotNull() {
            addCriterion("id_type is not null");
            return (Criteria) this;
        }

        public Criteria andId_typeEqualTo(String value) {
            addCriterion("id_type =", value, "id_type");
            return (Criteria) this;
        }

        public Criteria andId_typeNotEqualTo(String value) {
            addCriterion("id_type <>", value, "id_type");
            return (Criteria) this;
        }

        public Criteria andId_typeGreaterThan(String value) {
            addCriterion("id_type >", value, "id_type");
            return (Criteria) this;
        }

        public Criteria andId_typeGreaterThanOrEqualTo(String value) {
            addCriterion("id_type >=", value, "id_type");
            return (Criteria) this;
        }

        public Criteria andId_typeLessThan(String value) {
            addCriterion("id_type <", value, "id_type");
            return (Criteria) this;
        }

        public Criteria andId_typeLessThanOrEqualTo(String value) {
            addCriterion("id_type <=", value, "id_type");
            return (Criteria) this;
        }

        public Criteria andId_typeLike(String value) {
            addCriterion("id_type like", value, "id_type");
            return (Criteria) this;
        }

        public Criteria andId_typeNotLike(String value) {
            addCriterion("id_type not like", value, "id_type");
            return (Criteria) this;
        }

        public Criteria andId_typeIn(List<String> values) {
            addCriterion("id_type in", values, "id_type");
            return (Criteria) this;
        }

        public Criteria andId_typeNotIn(List<String> values) {
            addCriterion("id_type not in", values, "id_type");
            return (Criteria) this;
        }

        public Criteria andId_typeBetween(String value1, String value2) {
            addCriterion("id_type between", value1, value2, "id_type");
            return (Criteria) this;
        }

        public Criteria andId_typeNotBetween(String value1, String value2) {
            addCriterion("id_type not between", value1, value2, "id_type");
            return (Criteria) this;
        }

        public Criteria andId_codeIsNull() {
            addCriterion("id_code is null");
            return (Criteria) this;
        }

        public Criteria andId_codeIsNotNull() {
            addCriterion("id_code is not null");
            return (Criteria) this;
        }

        public Criteria andId_codeEqualTo(String value) {
            addCriterion("id_code =", value, "id_code");
            return (Criteria) this;
        }

        public Criteria andId_codeNotEqualTo(String value) {
            addCriterion("id_code <>", value, "id_code");
            return (Criteria) this;
        }

        public Criteria andId_codeGreaterThan(String value) {
            addCriterion("id_code >", value, "id_code");
            return (Criteria) this;
        }

        public Criteria andId_codeGreaterThanOrEqualTo(String value) {
            addCriterion("id_code >=", value, "id_code");
            return (Criteria) this;
        }

        public Criteria andId_codeLessThan(String value) {
            addCriterion("id_code <", value, "id_code");
            return (Criteria) this;
        }

        public Criteria andId_codeLessThanOrEqualTo(String value) {
            addCriterion("id_code <=", value, "id_code");
            return (Criteria) this;
        }

        public Criteria andId_codeLike(String value) {
            addCriterion("id_code like", value, "id_code");
            return (Criteria) this;
        }

        public Criteria andId_codeNotLike(String value) {
            addCriterion("id_code not like", value, "id_code");
            return (Criteria) this;
        }

        public Criteria andId_codeIn(List<String> values) {
            addCriterion("id_code in", values, "id_code");
            return (Criteria) this;
        }

        public Criteria andId_codeNotIn(List<String> values) {
            addCriterion("id_code not in", values, "id_code");
            return (Criteria) this;
        }

        public Criteria andId_codeBetween(String value1, String value2) {
            addCriterion("id_code between", value1, value2, "id_code");
            return (Criteria) this;
        }

        public Criteria andId_codeNotBetween(String value1, String value2) {
            addCriterion("id_code not between", value1, value2, "id_code");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andSexIsNull() {
            addCriterion("sex is null");
            return (Criteria) this;
        }

        public Criteria andSexIsNotNull() {
            addCriterion("sex is not null");
            return (Criteria) this;
        }

        public Criteria andSexEqualTo(String value) {
            addCriterion("sex =", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotEqualTo(String value) {
            addCriterion("sex <>", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThan(String value) {
            addCriterion("sex >", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThanOrEqualTo(String value) {
            addCriterion("sex >=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThan(String value) {
            addCriterion("sex <", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThanOrEqualTo(String value) {
            addCriterion("sex <=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLike(String value) {
            addCriterion("sex like", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotLike(String value) {
            addCriterion("sex not like", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexIn(List<String> values) {
            addCriterion("sex in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotIn(List<String> values) {
            addCriterion("sex not in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexBetween(String value1, String value2) {
            addCriterion("sex between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotBetween(String value1, String value2) {
            addCriterion("sex not between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andMobileIsNull() {
            addCriterion("mobile is null");
            return (Criteria) this;
        }

        public Criteria andMobileIsNotNull() {
            addCriterion("mobile is not null");
            return (Criteria) this;
        }

        public Criteria andMobileEqualTo(String value) {
            addCriterion("mobile =", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotEqualTo(String value) {
            addCriterion("mobile <>", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThan(String value) {
            addCriterion("mobile >", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThanOrEqualTo(String value) {
            addCriterion("mobile >=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThan(String value) {
            addCriterion("mobile <", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThanOrEqualTo(String value) {
            addCriterion("mobile <=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLike(String value) {
            addCriterion("mobile like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotLike(String value) {
            addCriterion("mobile not like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileIn(List<String> values) {
            addCriterion("mobile in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotIn(List<String> values) {
            addCriterion("mobile not in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileBetween(String value1, String value2) {
            addCriterion("mobile between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotBetween(String value1, String value2) {
            addCriterion("mobile not between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andReasonIsNull() {
            addCriterion("reason is null");
            return (Criteria) this;
        }

        public Criteria andReasonIsNotNull() {
            addCriterion("reason is not null");
            return (Criteria) this;
        }

        public Criteria andReasonEqualTo(String value) {
            addCriterion("reason =", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotEqualTo(String value) {
            addCriterion("reason <>", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThan(String value) {
            addCriterion("reason >", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThanOrEqualTo(String value) {
            addCriterion("reason >=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThan(String value) {
            addCriterion("reason <", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThanOrEqualTo(String value) {
            addCriterion("reason <=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLike(String value) {
            addCriterion("reason like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotLike(String value) {
            addCriterion("reason not like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonIn(List<String> values) {
            addCriterion("reason in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotIn(List<String> values) {
            addCriterion("reason not in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonBetween(String value1, String value2) {
            addCriterion("reason between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotBetween(String value1, String value2) {
            addCriterion("reason not between", value1, value2, "reason");
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

        public Criteria andOut_flgIsNull() {
            addCriterion("out_flg is null");
            return (Criteria) this;
        }

        public Criteria andOut_flgIsNotNull() {
            addCriterion("out_flg is not null");
            return (Criteria) this;
        }

        public Criteria andOut_flgEqualTo(String value) {
            addCriterion("out_flg =", value, "out_flg");
            return (Criteria) this;
        }

        public Criteria andOut_flgNotEqualTo(String value) {
            addCriterion("out_flg <>", value, "out_flg");
            return (Criteria) this;
        }

        public Criteria andOut_flgGreaterThan(String value) {
            addCriterion("out_flg >", value, "out_flg");
            return (Criteria) this;
        }

        public Criteria andOut_flgGreaterThanOrEqualTo(String value) {
            addCriterion("out_flg >=", value, "out_flg");
            return (Criteria) this;
        }

        public Criteria andOut_flgLessThan(String value) {
            addCriterion("out_flg <", value, "out_flg");
            return (Criteria) this;
        }

        public Criteria andOut_flgLessThanOrEqualTo(String value) {
            addCriterion("out_flg <=", value, "out_flg");
            return (Criteria) this;
        }

        public Criteria andOut_flgLike(String value) {
            addCriterion("out_flg like", value, "out_flg");
            return (Criteria) this;
        }

        public Criteria andOut_flgNotLike(String value) {
            addCriterion("out_flg not like", value, "out_flg");
            return (Criteria) this;
        }

        public Criteria andOut_flgIn(List<String> values) {
            addCriterion("out_flg in", values, "out_flg");
            return (Criteria) this;
        }

        public Criteria andOut_flgNotIn(List<String> values) {
            addCriterion("out_flg not in", values, "out_flg");
            return (Criteria) this;
        }

        public Criteria andOut_flgBetween(String value1, String value2) {
            addCriterion("out_flg between", value1, value2, "out_flg");
            return (Criteria) this;
        }

        public Criteria andOut_flgNotBetween(String value1, String value2) {
            addCriterion("out_flg not between", value1, value2, "out_flg");
            return (Criteria) this;
        }
    }

    /**
     * eacc_blacklist
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * eacc_blacklist 2017-06-01
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