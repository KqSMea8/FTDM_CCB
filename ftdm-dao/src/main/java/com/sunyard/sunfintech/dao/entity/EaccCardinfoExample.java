package com.sunyard.sunfintech.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EaccCardinfoExample {
    /**
     * eacc_cardinfo
     */
    protected String orderByClause;

    /**
     * eacc_cardinfo
     */
    protected boolean distinct;

    /**
     * eacc_cardinfo
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2017-06-01
     */
    public EaccCardinfoExample() {
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
     * eacc_cardinfo 2017-06-01
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

        public Criteria andPay_channelIsNull() {
            addCriterion("pay_channel is null");
            return (Criteria) this;
        }

        public Criteria andPay_channelIsNotNull() {
            addCriterion("pay_channel is not null");
            return (Criteria) this;
        }

        public Criteria andPay_channelEqualTo(String value) {
            addCriterion("pay_channel =", value, "pay_channel");
            return (Criteria) this;
        }

        public Criteria andPay_channelNotEqualTo(String value) {
            addCriterion("pay_channel <>", value, "pay_channel");
            return (Criteria) this;
        }

        public Criteria andPay_channelGreaterThan(String value) {
            addCriterion("pay_channel >", value, "pay_channel");
            return (Criteria) this;
        }

        public Criteria andPay_channelGreaterThanOrEqualTo(String value) {
            addCriterion("pay_channel >=", value, "pay_channel");
            return (Criteria) this;
        }

        public Criteria andPay_channelLessThan(String value) {
            addCriterion("pay_channel <", value, "pay_channel");
            return (Criteria) this;
        }

        public Criteria andPay_channelLessThanOrEqualTo(String value) {
            addCriterion("pay_channel <=", value, "pay_channel");
            return (Criteria) this;
        }

        public Criteria andPay_channelLike(String value) {
            addCriterion("pay_channel like", value, "pay_channel");
            return (Criteria) this;
        }

        public Criteria andPay_channelNotLike(String value) {
            addCriterion("pay_channel not like", value, "pay_channel");
            return (Criteria) this;
        }

        public Criteria andPay_channelIn(List<String> values) {
            addCriterion("pay_channel in", values, "pay_channel");
            return (Criteria) this;
        }

        public Criteria andPay_channelNotIn(List<String> values) {
            addCriterion("pay_channel not in", values, "pay_channel");
            return (Criteria) this;
        }

        public Criteria andPay_channelBetween(String value1, String value2) {
            addCriterion("pay_channel between", value1, value2, "pay_channel");
            return (Criteria) this;
        }

        public Criteria andPay_channelNotBetween(String value1, String value2) {
            addCriterion("pay_channel not between", value1, value2, "pay_channel");
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

        public Criteria andBank_noIsNull() {
            addCriterion("bank_no is null");
            return (Criteria) this;
        }

        public Criteria andBank_noIsNotNull() {
            addCriterion("bank_no is not null");
            return (Criteria) this;
        }

        public Criteria andBank_noEqualTo(String value) {
            addCriterion("bank_no =", value, "bank_no");
            return (Criteria) this;
        }

        public Criteria andBank_noNotEqualTo(String value) {
            addCriterion("bank_no <>", value, "bank_no");
            return (Criteria) this;
        }

        public Criteria andBank_noGreaterThan(String value) {
            addCriterion("bank_no >", value, "bank_no");
            return (Criteria) this;
        }

        public Criteria andBank_noGreaterThanOrEqualTo(String value) {
            addCriterion("bank_no >=", value, "bank_no");
            return (Criteria) this;
        }

        public Criteria andBank_noLessThan(String value) {
            addCriterion("bank_no <", value, "bank_no");
            return (Criteria) this;
        }

        public Criteria andBank_noLessThanOrEqualTo(String value) {
            addCriterion("bank_no <=", value, "bank_no");
            return (Criteria) this;
        }

        public Criteria andBank_noLike(String value) {
            addCriterion("bank_no like", value, "bank_no");
            return (Criteria) this;
        }

        public Criteria andBank_noNotLike(String value) {
            addCriterion("bank_no not like", value, "bank_no");
            return (Criteria) this;
        }

        public Criteria andBank_noIn(List<String> values) {
            addCriterion("bank_no in", values, "bank_no");
            return (Criteria) this;
        }

        public Criteria andBank_noNotIn(List<String> values) {
            addCriterion("bank_no not in", values, "bank_no");
            return (Criteria) this;
        }

        public Criteria andBank_noBetween(String value1, String value2) {
            addCriterion("bank_no between", value1, value2, "bank_no");
            return (Criteria) this;
        }

        public Criteria andBank_noNotBetween(String value1, String value2) {
            addCriterion("bank_no not between", value1, value2, "bank_no");
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

        public Criteria andCard_typeIsNull() {
            addCriterion("card_type is null");
            return (Criteria) this;
        }

        public Criteria andCard_typeIsNotNull() {
            addCriterion("card_type is not null");
            return (Criteria) this;
        }

        public Criteria andCard_typeEqualTo(String value) {
            addCriterion("card_type =", value, "card_type");
            return (Criteria) this;
        }

        public Criteria andCard_typeNotEqualTo(String value) {
            addCriterion("card_type <>", value, "card_type");
            return (Criteria) this;
        }

        public Criteria andCard_typeGreaterThan(String value) {
            addCriterion("card_type >", value, "card_type");
            return (Criteria) this;
        }

        public Criteria andCard_typeGreaterThanOrEqualTo(String value) {
            addCriterion("card_type >=", value, "card_type");
            return (Criteria) this;
        }

        public Criteria andCard_typeLessThan(String value) {
            addCriterion("card_type <", value, "card_type");
            return (Criteria) this;
        }

        public Criteria andCard_typeLessThanOrEqualTo(String value) {
            addCriterion("card_type <=", value, "card_type");
            return (Criteria) this;
        }

        public Criteria andCard_typeLike(String value) {
            addCriterion("card_type like", value, "card_type");
            return (Criteria) this;
        }

        public Criteria andCard_typeNotLike(String value) {
            addCriterion("card_type not like", value, "card_type");
            return (Criteria) this;
        }

        public Criteria andCard_typeIn(List<String> values) {
            addCriterion("card_type in", values, "card_type");
            return (Criteria) this;
        }

        public Criteria andCard_typeNotIn(List<String> values) {
            addCriterion("card_type not in", values, "card_type");
            return (Criteria) this;
        }

        public Criteria andCard_typeBetween(String value1, String value2) {
            addCriterion("card_type between", value1, value2, "card_type");
            return (Criteria) this;
        }

        public Criteria andCard_typeNotBetween(String value1, String value2) {
            addCriterion("card_type not between", value1, value2, "card_type");
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

        public Criteria andBindIdIsNull() {
            addCriterion("bindId is null");
            return (Criteria) this;
        }

        public Criteria andBindIdIsNotNull() {
            addCriterion("bindId is not null");
            return (Criteria) this;
        }

        public Criteria andBindIdEqualTo(String value) {
            addCriterion("bindId =", value, "bindId");
            return (Criteria) this;
        }

        public Criteria andBindIdNotEqualTo(String value) {
            addCriterion("bindId <>", value, "bindId");
            return (Criteria) this;
        }

        public Criteria andBindIdGreaterThan(String value) {
            addCriterion("bindId >", value, "bindId");
            return (Criteria) this;
        }

        public Criteria andBindIdGreaterThanOrEqualTo(String value) {
            addCriterion("bindId >=", value, "bindId");
            return (Criteria) this;
        }

        public Criteria andBindIdLessThan(String value) {
            addCriterion("bindId <", value, "bindId");
            return (Criteria) this;
        }

        public Criteria andBindIdLessThanOrEqualTo(String value) {
            addCriterion("bindId <=", value, "bindId");
            return (Criteria) this;
        }

        public Criteria andBindIdLike(String value) {
            addCriterion("bindId like", value, "bindId");
            return (Criteria) this;
        }

        public Criteria andBindIdNotLike(String value) {
            addCriterion("bindId not like", value, "bindId");
            return (Criteria) this;
        }

        public Criteria andBindIdIn(List<String> values) {
            addCriterion("bindId in", values, "bindId");
            return (Criteria) this;
        }

        public Criteria andBindIdNotIn(List<String> values) {
            addCriterion("bindId not in", values, "bindId");
            return (Criteria) this;
        }

        public Criteria andBindIdBetween(String value1, String value2) {
            addCriterion("bindId between", value1, value2, "bindId");
            return (Criteria) this;
        }

        public Criteria andBindIdNotBetween(String value1, String value2) {
            addCriterion("bindId not between", value1, value2, "bindId");
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
     * eacc_cardinfo
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * eacc_cardinfo 2017-06-01
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