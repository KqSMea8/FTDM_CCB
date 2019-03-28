package com.sunyard.sunfintech.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UnRegisterUserInfoExample {
    /**
     * unregister_userinfo
     */
    protected String orderByClause;

    /**
     * unregister_userinfo
     */
    protected boolean distinct;

    /**
     * unregister_userinfo
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2018-05-17
     */
    public UnRegisterUserInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *
     * @mbggenerated 2018-05-17
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *
     * @mbggenerated 2018-05-17
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *
     * @mbggenerated 2018-05-17
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *
     * @mbggenerated 2018-05-17
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *
     * @mbggenerated 2018-05-17
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *
     * @mbggenerated 2018-05-17
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *
     * @mbggenerated 2018-05-17
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *
     * @mbggenerated 2018-05-17
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
     * @mbggenerated 2018-05-17
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *
     * @mbggenerated 2018-05-17
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * unregister_userinfo 2018-05-17
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

        public Criteria andMallcustIsNull() {
            addCriterion("mallcust is null");
            return (Criteria) this;
        }

        public Criteria andMallcustIsNotNull() {
            addCriterion("mallcust is not null");
            return (Criteria) this;
        }

        public Criteria andMallcustEqualTo(String value) {
            addCriterion("mallcust =", value, "mallcust");
            return (Criteria) this;
        }

        public Criteria andMallcustNotEqualTo(String value) {
            addCriterion("mallcust <>", value, "mallcust");
            return (Criteria) this;
        }

        public Criteria andMallcustGreaterThan(String value) {
            addCriterion("mallcust >", value, "mallcust");
            return (Criteria) this;
        }

        public Criteria andMallcustGreaterThanOrEqualTo(String value) {
            addCriterion("mallcust >=", value, "mallcust");
            return (Criteria) this;
        }

        public Criteria andMallcustLessThan(String value) {
            addCriterion("mallcust <", value, "mallcust");
            return (Criteria) this;
        }

        public Criteria andMallcustLessThanOrEqualTo(String value) {
            addCriterion("mallcust <=", value, "mallcust");
            return (Criteria) this;
        }

        public Criteria andMallcustLike(String value) {
            addCriterion("mallcust like", value, "mallcust");
            return (Criteria) this;
        }

        public Criteria andMallcustNotLike(String value) {
            addCriterion("mallcust not like", value, "mallcust");
            return (Criteria) this;
        }

        public Criteria andMallcustIn(List<String> values) {
            addCriterion("mallcust in", values, "mallcust");
            return (Criteria) this;
        }

        public Criteria andMallcustNotIn(List<String> values) {
            addCriterion("mallcust not in", values, "mallcust");
            return (Criteria) this;
        }

        public Criteria andMallcustBetween(String value1, String value2) {
            addCriterion("mallcust between", value1, value2, "mallcust");
            return (Criteria) this;
        }

        public Criteria andMallcustNotBetween(String value1, String value2) {
            addCriterion("mallcust not between", value1, value2, "mallcust");
            return (Criteria) this;
        }

        public Criteria andEaccount_flgIsNull() {
            addCriterion("eaccount_flg is null");
            return (Criteria) this;
        }

        public Criteria andEaccount_flgIsNotNull() {
            addCriterion("eaccount_flg is not null");
            return (Criteria) this;
        }

        public Criteria andEaccount_flgEqualTo(String value) {
            addCriterion("eaccount_flg =", value, "eaccount_flg");
            return (Criteria) this;
        }

        public Criteria andEaccount_flgNotEqualTo(String value) {
            addCriterion("eaccount_flg <>", value, "eaccount_flg");
            return (Criteria) this;
        }

        public Criteria andEaccount_flgGreaterThan(String value) {
            addCriterion("eaccount_flg >", value, "eaccount_flg");
            return (Criteria) this;
        }

        public Criteria andEaccount_flgGreaterThanOrEqualTo(String value) {
            addCriterion("eaccount_flg >=", value, "eaccount_flg");
            return (Criteria) this;
        }

        public Criteria andEaccount_flgLessThan(String value) {
            addCriterion("eaccount_flg <", value, "eaccount_flg");
            return (Criteria) this;
        }

        public Criteria andEaccount_flgLessThanOrEqualTo(String value) {
            addCriterion("eaccount_flg <=", value, "eaccount_flg");
            return (Criteria) this;
        }

        public Criteria andEaccount_flgLike(String value) {
            addCriterion("eaccount_flg like", value, "eaccount_flg");
            return (Criteria) this;
        }

        public Criteria andEaccount_flgNotLike(String value) {
            addCriterion("eaccount_flg not like", value, "eaccount_flg");
            return (Criteria) this;
        }

        public Criteria andEaccount_flgIn(List<String> values) {
            addCriterion("eaccount_flg in", values, "eaccount_flg");
            return (Criteria) this;
        }

        public Criteria andEaccount_flgNotIn(List<String> values) {
            addCriterion("eaccount_flg not in", values, "eaccount_flg");
            return (Criteria) this;
        }

        public Criteria andEaccount_flgBetween(String value1, String value2) {
            addCriterion("eaccount_flg between", value1, value2, "eaccount_flg");
            return (Criteria) this;
        }

        public Criteria andEaccount_flgNotBetween(String value1, String value2) {
            addCriterion("eaccount_flg not between", value1, value2, "eaccount_flg");
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

        public Criteria andCus_typeIsNull() {
            addCriterion("cus_type is null");
            return (Criteria) this;
        }

        public Criteria andCus_typeIsNotNull() {
            addCriterion("cus_type is not null");
            return (Criteria) this;
        }

        public Criteria andCus_typeEqualTo(String value) {
            addCriterion("cus_type =", value, "cus_type");
            return (Criteria) this;
        }

        public Criteria andCus_typeNotEqualTo(String value) {
            addCriterion("cus_type <>", value, "cus_type");
            return (Criteria) this;
        }

        public Criteria andCus_typeGreaterThan(String value) {
            addCriterion("cus_type >", value, "cus_type");
            return (Criteria) this;
        }

        public Criteria andCus_typeGreaterThanOrEqualTo(String value) {
            addCriterion("cus_type >=", value, "cus_type");
            return (Criteria) this;
        }

        public Criteria andCus_typeLessThan(String value) {
            addCriterion("cus_type <", value, "cus_type");
            return (Criteria) this;
        }

        public Criteria andCus_typeLessThanOrEqualTo(String value) {
            addCriterion("cus_type <=", value, "cus_type");
            return (Criteria) this;
        }

        public Criteria andCus_typeLike(String value) {
            addCriterion("cus_type like", value, "cus_type");
            return (Criteria) this;
        }

        public Criteria andCus_typeNotLike(String value) {
            addCriterion("cus_type not like", value, "cus_type");
            return (Criteria) this;
        }

        public Criteria andCus_typeIn(List<String> values) {
            addCriterion("cus_type in", values, "cus_type");
            return (Criteria) this;
        }

        public Criteria andCus_typeNotIn(List<String> values) {
            addCriterion("cus_type not in", values, "cus_type");
            return (Criteria) this;
        }

        public Criteria andCus_typeBetween(String value1, String value2) {
            addCriterion("cus_type between", value1, value2, "cus_type");
            return (Criteria) this;
        }

        public Criteria andCus_typeNotBetween(String value1, String value2) {
            addCriterion("cus_type not between", value1, value2, "cus_type");
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

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
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

        public Criteria andTrans_pwdIsNull() {
            addCriterion("trans_pwd is null");
            return (Criteria) this;
        }

        public Criteria andTrans_pwdIsNotNull() {
            addCriterion("trans_pwd is not null");
            return (Criteria) this;
        }

        public Criteria andTrans_pwdEqualTo(String value) {
            addCriterion("trans_pwd =", value, "trans_pwd");
            return (Criteria) this;
        }

        public Criteria andTrans_pwdNotEqualTo(String value) {
            addCriterion("trans_pwd <>", value, "trans_pwd");
            return (Criteria) this;
        }

        public Criteria andTrans_pwdGreaterThan(String value) {
            addCriterion("trans_pwd >", value, "trans_pwd");
            return (Criteria) this;
        }

        public Criteria andTrans_pwdGreaterThanOrEqualTo(String value) {
            addCriterion("trans_pwd >=", value, "trans_pwd");
            return (Criteria) this;
        }

        public Criteria andTrans_pwdLessThan(String value) {
            addCriterion("trans_pwd <", value, "trans_pwd");
            return (Criteria) this;
        }

        public Criteria andTrans_pwdLessThanOrEqualTo(String value) {
            addCriterion("trans_pwd <=", value, "trans_pwd");
            return (Criteria) this;
        }

        public Criteria andTrans_pwdLike(String value) {
            addCriterion("trans_pwd like", value, "trans_pwd");
            return (Criteria) this;
        }

        public Criteria andTrans_pwdNotLike(String value) {
            addCriterion("trans_pwd not like", value, "trans_pwd");
            return (Criteria) this;
        }

        public Criteria andTrans_pwdIn(List<String> values) {
            addCriterion("trans_pwd in", values, "trans_pwd");
            return (Criteria) this;
        }

        public Criteria andTrans_pwdNotIn(List<String> values) {
            addCriterion("trans_pwd not in", values, "trans_pwd");
            return (Criteria) this;
        }

        public Criteria andTrans_pwdBetween(String value1, String value2) {
            addCriterion("trans_pwd between", value1, value2, "trans_pwd");
            return (Criteria) this;
        }

        public Criteria andTrans_pwdNotBetween(String value1, String value2) {
            addCriterion("trans_pwd not between", value1, value2, "trans_pwd");
            return (Criteria) this;
        }

        public Criteria andReg_timeIsNull() {
            addCriterion("reg_time is null");
            return (Criteria) this;
        }

        public Criteria andReg_timeIsNotNull() {
            addCriterion("reg_time is not null");
            return (Criteria) this;
        }

        public Criteria andReg_timeEqualTo(Date value) {
            addCriterion("reg_time =", value, "reg_time");
            return (Criteria) this;
        }

        public Criteria andReg_timeNotEqualTo(Date value) {
            addCriterion("reg_time <>", value, "reg_time");
            return (Criteria) this;
        }

        public Criteria andReg_timeGreaterThan(Date value) {
            addCriterion("reg_time >", value, "reg_time");
            return (Criteria) this;
        }

        public Criteria andReg_timeGreaterThanOrEqualTo(Date value) {
            addCriterion("reg_time >=", value, "reg_time");
            return (Criteria) this;
        }

        public Criteria andReg_timeLessThan(Date value) {
            addCriterion("reg_time <", value, "reg_time");
            return (Criteria) this;
        }

        public Criteria andReg_timeLessThanOrEqualTo(Date value) {
            addCriterion("reg_time <=", value, "reg_time");
            return (Criteria) this;
        }

        public Criteria andReg_timeIn(List<Date> values) {
            addCriterion("reg_time in", values, "reg_time");
            return (Criteria) this;
        }

        public Criteria andReg_timeNotIn(List<Date> values) {
            addCriterion("reg_time not in", values, "reg_time");
            return (Criteria) this;
        }

        public Criteria andReg_timeBetween(Date value1, Date value2) {
            addCriterion("reg_time between", value1, value2, "reg_time");
            return (Criteria) this;
        }

        public Criteria andReg_timeNotBetween(Date value1, Date value2) {
            addCriterion("reg_time not between", value1, value2, "reg_time");
            return (Criteria) this;
        }

        public Criteria andIs_card_bindIsNull() {
            addCriterion("is_card_bind is null");
            return (Criteria) this;
        }

        public Criteria andIs_card_bindIsNotNull() {
            addCriterion("is_card_bind is not null");
            return (Criteria) this;
        }

        public Criteria andIs_card_bindEqualTo(String value) {
            addCriterion("is_card_bind =", value, "is_card_bind");
            return (Criteria) this;
        }

        public Criteria andIs_card_bindNotEqualTo(String value) {
            addCriterion("is_card_bind <>", value, "is_card_bind");
            return (Criteria) this;
        }

        public Criteria andIs_card_bindGreaterThan(String value) {
            addCriterion("is_card_bind >", value, "is_card_bind");
            return (Criteria) this;
        }

        public Criteria andIs_card_bindGreaterThanOrEqualTo(String value) {
            addCriterion("is_card_bind >=", value, "is_card_bind");
            return (Criteria) this;
        }

        public Criteria andIs_card_bindLessThan(String value) {
            addCriterion("is_card_bind <", value, "is_card_bind");
            return (Criteria) this;
        }

        public Criteria andIs_card_bindLessThanOrEqualTo(String value) {
            addCriterion("is_card_bind <=", value, "is_card_bind");
            return (Criteria) this;
        }

        public Criteria andIs_card_bindLike(String value) {
            addCriterion("is_card_bind like", value, "is_card_bind");
            return (Criteria) this;
        }

        public Criteria andIs_card_bindNotLike(String value) {
            addCriterion("is_card_bind not like", value, "is_card_bind");
            return (Criteria) this;
        }

        public Criteria andIs_card_bindIn(List<String> values) {
            addCriterion("is_card_bind in", values, "is_card_bind");
            return (Criteria) this;
        }

        public Criteria andIs_card_bindNotIn(List<String> values) {
            addCriterion("is_card_bind not in", values, "is_card_bind");
            return (Criteria) this;
        }

        public Criteria andIs_card_bindBetween(String value1, String value2) {
            addCriterion("is_card_bind between", value1, value2, "is_card_bind");
            return (Criteria) this;
        }

        public Criteria andIs_card_bindNotBetween(String value1, String value2) {
            addCriterion("is_card_bind not between", value1, value2, "is_card_bind");
            return (Criteria) this;
        }

        public Criteria andOrg_nameIsNull() {
            addCriterion("org_name is null");
            return (Criteria) this;
        }

        public Criteria andOrg_nameIsNotNull() {
            addCriterion("org_name is not null");
            return (Criteria) this;
        }

        public Criteria andOrg_nameEqualTo(String value) {
            addCriterion("org_name =", value, "org_name");
            return (Criteria) this;
        }

        public Criteria andOrg_nameNotEqualTo(String value) {
            addCriterion("org_name <>", value, "org_name");
            return (Criteria) this;
        }

        public Criteria andOrg_nameGreaterThan(String value) {
            addCriterion("org_name >", value, "org_name");
            return (Criteria) this;
        }

        public Criteria andOrg_nameGreaterThanOrEqualTo(String value) {
            addCriterion("org_name >=", value, "org_name");
            return (Criteria) this;
        }

        public Criteria andOrg_nameLessThan(String value) {
            addCriterion("org_name <", value, "org_name");
            return (Criteria) this;
        }

        public Criteria andOrg_nameLessThanOrEqualTo(String value) {
            addCriterion("org_name <=", value, "org_name");
            return (Criteria) this;
        }

        public Criteria andOrg_nameLike(String value) {
            addCriterion("org_name like", value, "org_name");
            return (Criteria) this;
        }

        public Criteria andOrg_nameNotLike(String value) {
            addCriterion("org_name not like", value, "org_name");
            return (Criteria) this;
        }

        public Criteria andOrg_nameIn(List<String> values) {
            addCriterion("org_name in", values, "org_name");
            return (Criteria) this;
        }

        public Criteria andOrg_nameNotIn(List<String> values) {
            addCriterion("org_name not in", values, "org_name");
            return (Criteria) this;
        }

        public Criteria andOrg_nameBetween(String value1, String value2) {
            addCriterion("org_name between", value1, value2, "org_name");
            return (Criteria) this;
        }

        public Criteria andOrg_nameNotBetween(String value1, String value2) {
            addCriterion("org_name not between", value1, value2, "org_name");
            return (Criteria) this;
        }

        public Criteria andBank_licenseIsNull() {
            addCriterion("bank_license is null");
            return (Criteria) this;
        }

        public Criteria andBank_licenseIsNotNull() {
            addCriterion("bank_license is not null");
            return (Criteria) this;
        }

        public Criteria andBank_licenseEqualTo(String value) {
            addCriterion("bank_license =", value, "bank_license");
            return (Criteria) this;
        }

        public Criteria andBank_licenseNotEqualTo(String value) {
            addCriterion("bank_license <>", value, "bank_license");
            return (Criteria) this;
        }

        public Criteria andBank_licenseGreaterThan(String value) {
            addCriterion("bank_license >", value, "bank_license");
            return (Criteria) this;
        }

        public Criteria andBank_licenseGreaterThanOrEqualTo(String value) {
            addCriterion("bank_license >=", value, "bank_license");
            return (Criteria) this;
        }

        public Criteria andBank_licenseLessThan(String value) {
            addCriterion("bank_license <", value, "bank_license");
            return (Criteria) this;
        }

        public Criteria andBank_licenseLessThanOrEqualTo(String value) {
            addCriterion("bank_license <=", value, "bank_license");
            return (Criteria) this;
        }

        public Criteria andBank_licenseLike(String value) {
            addCriterion("bank_license like", value, "bank_license");
            return (Criteria) this;
        }

        public Criteria andBank_licenseNotLike(String value) {
            addCriterion("bank_license not like", value, "bank_license");
            return (Criteria) this;
        }

        public Criteria andBank_licenseIn(List<String> values) {
            addCriterion("bank_license in", values, "bank_license");
            return (Criteria) this;
        }

        public Criteria andBank_licenseNotIn(List<String> values) {
            addCriterion("bank_license not in", values, "bank_license");
            return (Criteria) this;
        }

        public Criteria andBank_licenseBetween(String value1, String value2) {
            addCriterion("bank_license between", value1, value2, "bank_license");
            return (Criteria) this;
        }

        public Criteria andBank_licenseNotBetween(String value1, String value2) {
            addCriterion("bank_license not between", value1, value2, "bank_license");
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

        public Criteria andBusiness_licenseIsNull() {
            addCriterion("business_license is null");
            return (Criteria) this;
        }

        public Criteria andBusiness_licenseIsNotNull() {
            addCriterion("business_license is not null");
            return (Criteria) this;
        }

        public Criteria andBusiness_licenseEqualTo(String value) {
            addCriterion("business_license =", value, "business_license");
            return (Criteria) this;
        }

        public Criteria andBusiness_licenseNotEqualTo(String value) {
            addCriterion("business_license <>", value, "business_license");
            return (Criteria) this;
        }

        public Criteria andBusiness_licenseGreaterThan(String value) {
            addCriterion("business_license >", value, "business_license");
            return (Criteria) this;
        }

        public Criteria andBusiness_licenseGreaterThanOrEqualTo(String value) {
            addCriterion("business_license >=", value, "business_license");
            return (Criteria) this;
        }

        public Criteria andBusiness_licenseLessThan(String value) {
            addCriterion("business_license <", value, "business_license");
            return (Criteria) this;
        }

        public Criteria andBusiness_licenseLessThanOrEqualTo(String value) {
            addCriterion("business_license <=", value, "business_license");
            return (Criteria) this;
        }

        public Criteria andBusiness_licenseLike(String value) {
            addCriterion("business_license like", value, "business_license");
            return (Criteria) this;
        }

        public Criteria andBusiness_licenseNotLike(String value) {
            addCriterion("business_license not like", value, "business_license");
            return (Criteria) this;
        }

        public Criteria andBusiness_licenseIn(List<String> values) {
            addCriterion("business_license in", values, "business_license");
            return (Criteria) this;
        }

        public Criteria andBusiness_licenseNotIn(List<String> values) {
            addCriterion("business_license not in", values, "business_license");
            return (Criteria) this;
        }

        public Criteria andBusiness_licenseBetween(String value1, String value2) {
            addCriterion("business_license between", value1, value2, "business_license");
            return (Criteria) this;
        }

        public Criteria andBusiness_licenseNotBetween(String value1, String value2) {
            addCriterion("business_license not between", value1, value2, "business_license");
            return (Criteria) this;
        }

        public Criteria andTax_noIsNull() {
            addCriterion("tax_no is null");
            return (Criteria) this;
        }

        public Criteria andTax_noIsNotNull() {
            addCriterion("tax_no is not null");
            return (Criteria) this;
        }

        public Criteria andTax_noEqualTo(String value) {
            addCriterion("tax_no =", value, "tax_no");
            return (Criteria) this;
        }

        public Criteria andTax_noNotEqualTo(String value) {
            addCriterion("tax_no <>", value, "tax_no");
            return (Criteria) this;
        }

        public Criteria andTax_noGreaterThan(String value) {
            addCriterion("tax_no >", value, "tax_no");
            return (Criteria) this;
        }

        public Criteria andTax_noGreaterThanOrEqualTo(String value) {
            addCriterion("tax_no >=", value, "tax_no");
            return (Criteria) this;
        }

        public Criteria andTax_noLessThan(String value) {
            addCriterion("tax_no <", value, "tax_no");
            return (Criteria) this;
        }

        public Criteria andTax_noLessThanOrEqualTo(String value) {
            addCriterion("tax_no <=", value, "tax_no");
            return (Criteria) this;
        }

        public Criteria andTax_noLike(String value) {
            addCriterion("tax_no like", value, "tax_no");
            return (Criteria) this;
        }

        public Criteria andTax_noNotLike(String value) {
            addCriterion("tax_no not like", value, "tax_no");
            return (Criteria) this;
        }

        public Criteria andTax_noIn(List<String> values) {
            addCriterion("tax_no in", values, "tax_no");
            return (Criteria) this;
        }

        public Criteria andTax_noNotIn(List<String> values) {
            addCriterion("tax_no not in", values, "tax_no");
            return (Criteria) this;
        }

        public Criteria andTax_noBetween(String value1, String value2) {
            addCriterion("tax_no between", value1, value2, "tax_no");
            return (Criteria) this;
        }

        public Criteria andTax_noNotBetween(String value1, String value2) {
            addCriterion("tax_no not between", value1, value2, "tax_no");
            return (Criteria) this;
        }

        public Criteria andDefault_card_noIsNull() {
            addCriterion("default_card_no is null");
            return (Criteria) this;
        }

        public Criteria andDefault_card_noIsNotNull() {
            addCriterion("default_card_no is not null");
            return (Criteria) this;
        }

        public Criteria andDefault_card_noEqualTo(String value) {
            addCriterion("default_card_no =", value, "default_card_no");
            return (Criteria) this;
        }

        public Criteria andDefault_card_noNotEqualTo(String value) {
            addCriterion("default_card_no <>", value, "default_card_no");
            return (Criteria) this;
        }

        public Criteria andDefault_card_noGreaterThan(String value) {
            addCriterion("default_card_no >", value, "default_card_no");
            return (Criteria) this;
        }

        public Criteria andDefault_card_noGreaterThanOrEqualTo(String value) {
            addCriterion("default_card_no >=", value, "default_card_no");
            return (Criteria) this;
        }

        public Criteria andDefault_card_noLessThan(String value) {
            addCriterion("default_card_no <", value, "default_card_no");
            return (Criteria) this;
        }

        public Criteria andDefault_card_noLessThanOrEqualTo(String value) {
            addCriterion("default_card_no <=", value, "default_card_no");
            return (Criteria) this;
        }

        public Criteria andDefault_card_noLike(String value) {
            addCriterion("default_card_no like", value, "default_card_no");
            return (Criteria) this;
        }

        public Criteria andDefault_card_noNotLike(String value) {
            addCriterion("default_card_no not like", value, "default_card_no");
            return (Criteria) this;
        }

        public Criteria andDefault_card_noIn(List<String> values) {
            addCriterion("default_card_no in", values, "default_card_no");
            return (Criteria) this;
        }

        public Criteria andDefault_card_noNotIn(List<String> values) {
            addCriterion("default_card_no not in", values, "default_card_no");
            return (Criteria) this;
        }

        public Criteria andDefault_card_noBetween(String value1, String value2) {
            addCriterion("default_card_no between", value1, value2, "default_card_no");
            return (Criteria) this;
        }

        public Criteria andDefault_card_noNotBetween(String value1, String value2) {
            addCriterion("default_card_no not between", value1, value2, "default_card_no");
            return (Criteria) this;
        }

        public Criteria andDefault_mobileIsNull() {
            addCriterion("default_mobile is null");
            return (Criteria) this;
        }

        public Criteria andDefault_mobileIsNotNull() {
            addCriterion("default_mobile is not null");
            return (Criteria) this;
        }

        public Criteria andDefault_mobileEqualTo(String value) {
            addCriterion("default_mobile =", value, "default_mobile");
            return (Criteria) this;
        }

        public Criteria andDefault_mobileNotEqualTo(String value) {
            addCriterion("default_mobile <>", value, "default_mobile");
            return (Criteria) this;
        }

        public Criteria andDefault_mobileGreaterThan(String value) {
            addCriterion("default_mobile >", value, "default_mobile");
            return (Criteria) this;
        }

        public Criteria andDefault_mobileGreaterThanOrEqualTo(String value) {
            addCriterion("default_mobile >=", value, "default_mobile");
            return (Criteria) this;
        }

        public Criteria andDefault_mobileLessThan(String value) {
            addCriterion("default_mobile <", value, "default_mobile");
            return (Criteria) this;
        }

        public Criteria andDefault_mobileLessThanOrEqualTo(String value) {
            addCriterion("default_mobile <=", value, "default_mobile");
            return (Criteria) this;
        }

        public Criteria andDefault_mobileLike(String value) {
            addCriterion("default_mobile like", value, "default_mobile");
            return (Criteria) this;
        }

        public Criteria andDefault_mobileNotLike(String value) {
            addCriterion("default_mobile not like", value, "default_mobile");
            return (Criteria) this;
        }

        public Criteria andDefault_mobileIn(List<String> values) {
            addCriterion("default_mobile in", values, "default_mobile");
            return (Criteria) this;
        }

        public Criteria andDefault_mobileNotIn(List<String> values) {
            addCriterion("default_mobile not in", values, "default_mobile");
            return (Criteria) this;
        }

        public Criteria andDefault_mobileBetween(String value1, String value2) {
            addCriterion("default_mobile between", value1, value2, "default_mobile");
            return (Criteria) this;
        }

        public Criteria andDefault_mobileNotBetween(String value1, String value2) {
            addCriterion("default_mobile not between", value1, value2, "default_mobile");
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

        public Criteria andInvesterIsNull() {
            addCriterion("invester is null");
            return (Criteria) this;
        }

        public Criteria andInvesterIsNotNull() {
            addCriterion("invester is not null");
            return (Criteria) this;
        }

        public Criteria andInvesterEqualTo(Byte value) {
            addCriterion("invester =", value, "invester");
            return (Criteria) this;
        }

        public Criteria andInvesterNotEqualTo(Byte value) {
            addCriterion("invester <>", value, "invester");
            return (Criteria) this;
        }

        public Criteria andInvesterGreaterThan(Byte value) {
            addCriterion("invester >", value, "invester");
            return (Criteria) this;
        }

        public Criteria andInvesterGreaterThanOrEqualTo(Byte value) {
            addCriterion("invester >=", value, "invester");
            return (Criteria) this;
        }

        public Criteria andInvesterLessThan(Byte value) {
            addCriterion("invester <", value, "invester");
            return (Criteria) this;
        }

        public Criteria andInvesterLessThanOrEqualTo(Byte value) {
            addCriterion("invester <=", value, "invester");
            return (Criteria) this;
        }

        public Criteria andInvesterIn(List<Byte> values) {
            addCriterion("invester in", values, "invester");
            return (Criteria) this;
        }

        public Criteria andInvesterNotIn(List<Byte> values) {
            addCriterion("invester not in", values, "invester");
            return (Criteria) this;
        }

        public Criteria andInvesterBetween(Byte value1, Byte value2) {
            addCriterion("invester between", value1, value2, "invester");
            return (Criteria) this;
        }

        public Criteria andInvesterNotBetween(Byte value1, Byte value2) {
            addCriterion("invester not between", value1, value2, "invester");
            return (Criteria) this;
        }

        public Criteria andFinancierIsNull() {
            addCriterion("financier is null");
            return (Criteria) this;
        }

        public Criteria andFinancierIsNotNull() {
            addCriterion("financier is not null");
            return (Criteria) this;
        }

        public Criteria andFinancierEqualTo(Byte value) {
            addCriterion("financier =", value, "financier");
            return (Criteria) this;
        }

        public Criteria andFinancierNotEqualTo(Byte value) {
            addCriterion("financier <>", value, "financier");
            return (Criteria) this;
        }

        public Criteria andFinancierGreaterThan(Byte value) {
            addCriterion("financier >", value, "financier");
            return (Criteria) this;
        }

        public Criteria andFinancierGreaterThanOrEqualTo(Byte value) {
            addCriterion("financier >=", value, "financier");
            return (Criteria) this;
        }

        public Criteria andFinancierLessThan(Byte value) {
            addCriterion("financier <", value, "financier");
            return (Criteria) this;
        }

        public Criteria andFinancierLessThanOrEqualTo(Byte value) {
            addCriterion("financier <=", value, "financier");
            return (Criteria) this;
        }

        public Criteria andFinancierIn(List<Byte> values) {
            addCriterion("financier in", values, "financier");
            return (Criteria) this;
        }

        public Criteria andFinancierNotIn(List<Byte> values) {
            addCriterion("financier not in", values, "financier");
            return (Criteria) this;
        }

        public Criteria andFinancierBetween(Byte value1, Byte value2) {
            addCriterion("financier between", value1, value2, "financier");
            return (Criteria) this;
        }

        public Criteria andFinancierNotBetween(Byte value1, Byte value2) {
            addCriterion("financier not between", value1, value2, "financier");
            return (Criteria) this;
        }

        public Criteria andAdvancerIsNull() {
            addCriterion("advancer is null");
            return (Criteria) this;
        }

        public Criteria andAdvancerIsNotNull() {
            addCriterion("advancer is not null");
            return (Criteria) this;
        }

        public Criteria andAdvancerEqualTo(Byte value) {
            addCriterion("advancer =", value, "advancer");
            return (Criteria) this;
        }

        public Criteria andAdvancerNotEqualTo(Byte value) {
            addCriterion("advancer <>", value, "advancer");
            return (Criteria) this;
        }

        public Criteria andAdvancerGreaterThan(Byte value) {
            addCriterion("advancer >", value, "advancer");
            return (Criteria) this;
        }

        public Criteria andAdvancerGreaterThanOrEqualTo(Byte value) {
            addCriterion("advancer >=", value, "advancer");
            return (Criteria) this;
        }

        public Criteria andAdvancerLessThan(Byte value) {
            addCriterion("advancer <", value, "advancer");
            return (Criteria) this;
        }

        public Criteria andAdvancerLessThanOrEqualTo(Byte value) {
            addCriterion("advancer <=", value, "advancer");
            return (Criteria) this;
        }

        public Criteria andAdvancerIn(List<Byte> values) {
            addCriterion("advancer in", values, "advancer");
            return (Criteria) this;
        }

        public Criteria andAdvancerNotIn(List<Byte> values) {
            addCriterion("advancer not in", values, "advancer");
            return (Criteria) this;
        }

        public Criteria andAdvancerBetween(Byte value1, Byte value2) {
            addCriterion("advancer between", value1, value2, "advancer");
            return (Criteria) this;
        }

        public Criteria andAdvancerNotBetween(Byte value1, Byte value2) {
            addCriterion("advancer not between", value1, value2, "advancer");
            return (Criteria) this;
        }

        public Criteria andGuarantorIsNull() {
            addCriterion("guarantor is null");
            return (Criteria) this;
        }

        public Criteria andGuarantorIsNotNull() {
            addCriterion("guarantor is not null");
            return (Criteria) this;
        }

        public Criteria andGuarantorEqualTo(Byte value) {
            addCriterion("guarantor =", value, "guarantor");
            return (Criteria) this;
        }

        public Criteria andGuarantorNotEqualTo(Byte value) {
            addCriterion("guarantor <>", value, "guarantor");
            return (Criteria) this;
        }

        public Criteria andGuarantorGreaterThan(Byte value) {
            addCriterion("guarantor >", value, "guarantor");
            return (Criteria) this;
        }

        public Criteria andGuarantorGreaterThanOrEqualTo(Byte value) {
            addCriterion("guarantor >=", value, "guarantor");
            return (Criteria) this;
        }

        public Criteria andGuarantorLessThan(Byte value) {
            addCriterion("guarantor <", value, "guarantor");
            return (Criteria) this;
        }

        public Criteria andGuarantorLessThanOrEqualTo(Byte value) {
            addCriterion("guarantor <=", value, "guarantor");
            return (Criteria) this;
        }

        public Criteria andGuarantorIn(List<Byte> values) {
            addCriterion("guarantor in", values, "guarantor");
            return (Criteria) this;
        }

        public Criteria andGuarantorNotIn(List<Byte> values) {
            addCriterion("guarantor not in", values, "guarantor");
            return (Criteria) this;
        }

        public Criteria andGuarantorBetween(Byte value1, Byte value2) {
            addCriterion("guarantor between", value1, value2, "guarantor");
            return (Criteria) this;
        }

        public Criteria andGuarantorNotBetween(Byte value1, Byte value2) {
            addCriterion("guarantor not between", value1, value2, "guarantor");
            return (Criteria) this;
        }

        public Criteria andExt_role1IsNull() {
            addCriterion("ext_role1 is null");
            return (Criteria) this;
        }

        public Criteria andExt_role1IsNotNull() {
            addCriterion("ext_role1 is not null");
            return (Criteria) this;
        }

        public Criteria andExt_role1EqualTo(Byte value) {
            addCriterion("ext_role1 =", value, "ext_role1");
            return (Criteria) this;
        }

        public Criteria andExt_role1NotEqualTo(Byte value) {
            addCriterion("ext_role1 <>", value, "ext_role1");
            return (Criteria) this;
        }

        public Criteria andExt_role1GreaterThan(Byte value) {
            addCriterion("ext_role1 >", value, "ext_role1");
            return (Criteria) this;
        }

        public Criteria andExt_role1GreaterThanOrEqualTo(Byte value) {
            addCriterion("ext_role1 >=", value, "ext_role1");
            return (Criteria) this;
        }

        public Criteria andExt_role1LessThan(Byte value) {
            addCriterion("ext_role1 <", value, "ext_role1");
            return (Criteria) this;
        }

        public Criteria andExt_role1LessThanOrEqualTo(Byte value) {
            addCriterion("ext_role1 <=", value, "ext_role1");
            return (Criteria) this;
        }

        public Criteria andExt_role1In(List<Byte> values) {
            addCriterion("ext_role1 in", values, "ext_role1");
            return (Criteria) this;
        }

        public Criteria andExt_role1NotIn(List<Byte> values) {
            addCriterion("ext_role1 not in", values, "ext_role1");
            return (Criteria) this;
        }

        public Criteria andExt_role1Between(Byte value1, Byte value2) {
            addCriterion("ext_role1 between", value1, value2, "ext_role1");
            return (Criteria) this;
        }

        public Criteria andExt_role1NotBetween(Byte value1, Byte value2) {
            addCriterion("ext_role1 not between", value1, value2, "ext_role1");
            return (Criteria) this;
        }

        public Criteria andExt_role2IsNull() {
            addCriterion("ext_role2 is null");
            return (Criteria) this;
        }

        public Criteria andExt_role2IsNotNull() {
            addCriterion("ext_role2 is not null");
            return (Criteria) this;
        }

        public Criteria andExt_role2EqualTo(Byte value) {
            addCriterion("ext_role2 =", value, "ext_role2");
            return (Criteria) this;
        }

        public Criteria andExt_role2NotEqualTo(Byte value) {
            addCriterion("ext_role2 <>", value, "ext_role2");
            return (Criteria) this;
        }

        public Criteria andExt_role2GreaterThan(Byte value) {
            addCriterion("ext_role2 >", value, "ext_role2");
            return (Criteria) this;
        }

        public Criteria andExt_role2GreaterThanOrEqualTo(Byte value) {
            addCriterion("ext_role2 >=", value, "ext_role2");
            return (Criteria) this;
        }

        public Criteria andExt_role2LessThan(Byte value) {
            addCriterion("ext_role2 <", value, "ext_role2");
            return (Criteria) this;
        }

        public Criteria andExt_role2LessThanOrEqualTo(Byte value) {
            addCriterion("ext_role2 <=", value, "ext_role2");
            return (Criteria) this;
        }

        public Criteria andExt_role2In(List<Byte> values) {
            addCriterion("ext_role2 in", values, "ext_role2");
            return (Criteria) this;
        }

        public Criteria andExt_role2NotIn(List<Byte> values) {
            addCriterion("ext_role2 not in", values, "ext_role2");
            return (Criteria) this;
        }

        public Criteria andExt_role2Between(Byte value1, Byte value2) {
            addCriterion("ext_role2 between", value1, value2, "ext_role2");
            return (Criteria) this;
        }

        public Criteria andExt_role2NotBetween(Byte value1, Byte value2) {
            addCriterion("ext_role2 not between", value1, value2, "ext_role2");
            return (Criteria) this;
        }

        public Criteria andPwd_flgIsNull() {
            addCriterion("pwd_flg is null");
            return (Criteria) this;
        }

        public Criteria andPwd_flgIsNotNull() {
            addCriterion("pwd_flg is not null");
            return (Criteria) this;
        }

        public Criteria andPwd_flgEqualTo(String value) {
            addCriterion("pwd_flg =", value, "pwd_flg");
            return (Criteria) this;
        }

        public Criteria andPwd_flgNotEqualTo(String value) {
            addCriterion("pwd_flg <>", value, "pwd_flg");
            return (Criteria) this;
        }

        public Criteria andPwd_flgGreaterThan(String value) {
            addCriterion("pwd_flg >", value, "pwd_flg");
            return (Criteria) this;
        }

        public Criteria andPwd_flgGreaterThanOrEqualTo(String value) {
            addCriterion("pwd_flg >=", value, "pwd_flg");
            return (Criteria) this;
        }

        public Criteria andPwd_flgLessThan(String value) {
            addCriterion("pwd_flg <", value, "pwd_flg");
            return (Criteria) this;
        }

        public Criteria andPwd_flgLessThanOrEqualTo(String value) {
            addCriterion("pwd_flg <=", value, "pwd_flg");
            return (Criteria) this;
        }

        public Criteria andPwd_flgLike(String value) {
            addCriterion("pwd_flg like", value, "pwd_flg");
            return (Criteria) this;
        }

        public Criteria andPwd_flgNotLike(String value) {
            addCriterion("pwd_flg not like", value, "pwd_flg");
            return (Criteria) this;
        }

        public Criteria andPwd_flgIn(List<String> values) {
            addCriterion("pwd_flg in", values, "pwd_flg");
            return (Criteria) this;
        }

        public Criteria andPwd_flgNotIn(List<String> values) {
            addCriterion("pwd_flg not in", values, "pwd_flg");
            return (Criteria) this;
        }

        public Criteria andPwd_flgBetween(String value1, String value2) {
            addCriterion("pwd_flg between", value1, value2, "pwd_flg");
            return (Criteria) this;
        }

        public Criteria andPwd_flgNotBetween(String value1, String value2) {
            addCriterion("pwd_flg not between", value1, value2, "pwd_flg");
            return (Criteria) this;
        }
    }

    /**
     * unregister_userinfo
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * unregister_userinfo 2018-05-17
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