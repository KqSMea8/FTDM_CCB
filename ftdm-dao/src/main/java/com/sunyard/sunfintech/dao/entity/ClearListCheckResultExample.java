package com.sunyard.sunfintech.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClearListCheckResultExample {
    /**
     * clear_list_check_result
     */
    protected String orderByClause;

    /**
     * clear_list_check_result
     */
    protected boolean distinct;

    /**
     * clear_list_check_result
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2018-06-21
     */
    public ClearListCheckResultExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *
     * @mbggenerated 2018-06-21
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *
     * @mbggenerated 2018-06-21
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *
     * @mbggenerated 2018-06-21
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *
     * @mbggenerated 2018-06-21
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *
     * @mbggenerated 2018-06-21
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *
     * @mbggenerated 2018-06-21
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *
     * @mbggenerated 2018-06-21
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *
     * @mbggenerated 2018-06-21
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
     * @mbggenerated 2018-06-21
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *
     * @mbggenerated 2018-06-21
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * clear_list_check_result 2018-06-21
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

        public Criteria andSer_noIsNull() {
            addCriterion("ser_no is null");
            return (Criteria) this;
        }

        public Criteria andSer_noIsNotNull() {
            addCriterion("ser_no is not null");
            return (Criteria) this;
        }

        public Criteria andSer_noEqualTo(String value) {
            addCriterion("ser_no =", value, "ser_no");
            return (Criteria) this;
        }

        public Criteria andSer_noNotEqualTo(String value) {
            addCriterion("ser_no <>", value, "ser_no");
            return (Criteria) this;
        }

        public Criteria andSer_noGreaterThan(String value) {
            addCriterion("ser_no >", value, "ser_no");
            return (Criteria) this;
        }

        public Criteria andSer_noGreaterThanOrEqualTo(String value) {
            addCriterion("ser_no >=", value, "ser_no");
            return (Criteria) this;
        }

        public Criteria andSer_noLessThan(String value) {
            addCriterion("ser_no <", value, "ser_no");
            return (Criteria) this;
        }

        public Criteria andSer_noLessThanOrEqualTo(String value) {
            addCriterion("ser_no <=", value, "ser_no");
            return (Criteria) this;
        }

        public Criteria andSer_noLike(String value) {
            addCriterion("ser_no like", value, "ser_no");
            return (Criteria) this;
        }

        public Criteria andSer_noNotLike(String value) {
            addCriterion("ser_no not like", value, "ser_no");
            return (Criteria) this;
        }

        public Criteria andSer_noIn(List<String> values) {
            addCriterion("ser_no in", values, "ser_no");
            return (Criteria) this;
        }

        public Criteria andSer_noNotIn(List<String> values) {
            addCriterion("ser_no not in", values, "ser_no");
            return (Criteria) this;
        }

        public Criteria andSer_noBetween(String value1, String value2) {
            addCriterion("ser_no between", value1, value2, "ser_no");
            return (Criteria) this;
        }

        public Criteria andSer_noNotBetween(String value1, String value2) {
            addCriterion("ser_no not between", value1, value2, "ser_no");
            return (Criteria) this;
        }

        public Criteria andTrans_codeIsNull() {
            addCriterion("trans_code is null");
            return (Criteria) this;
        }

        public Criteria andTrans_codeIsNotNull() {
            addCriterion("trans_code is not null");
            return (Criteria) this;
        }

        public Criteria andTrans_codeEqualTo(String value) {
            addCriterion("trans_code =", value, "trans_code");
            return (Criteria) this;
        }

        public Criteria andTrans_codeNotEqualTo(String value) {
            addCriterion("trans_code <>", value, "trans_code");
            return (Criteria) this;
        }

        public Criteria andTrans_codeGreaterThan(String value) {
            addCriterion("trans_code >", value, "trans_code");
            return (Criteria) this;
        }

        public Criteria andTrans_codeGreaterThanOrEqualTo(String value) {
            addCriterion("trans_code >=", value, "trans_code");
            return (Criteria) this;
        }

        public Criteria andTrans_codeLessThan(String value) {
            addCriterion("trans_code <", value, "trans_code");
            return (Criteria) this;
        }

        public Criteria andTrans_codeLessThanOrEqualTo(String value) {
            addCriterion("trans_code <=", value, "trans_code");
            return (Criteria) this;
        }

        public Criteria andTrans_codeLike(String value) {
            addCriterion("trans_code like", value, "trans_code");
            return (Criteria) this;
        }

        public Criteria andTrans_codeNotLike(String value) {
            addCriterion("trans_code not like", value, "trans_code");
            return (Criteria) this;
        }

        public Criteria andTrans_codeIn(List<String> values) {
            addCriterion("trans_code in", values, "trans_code");
            return (Criteria) this;
        }

        public Criteria andTrans_codeNotIn(List<String> values) {
            addCriterion("trans_code not in", values, "trans_code");
            return (Criteria) this;
        }

        public Criteria andTrans_codeBetween(String value1, String value2) {
            addCriterion("trans_code between", value1, value2, "trans_code");
            return (Criteria) this;
        }

        public Criteria andTrans_codeNotBetween(String value1, String value2) {
            addCriterion("trans_code not between", value1, value2, "trans_code");
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

        public Criteria andCheck_timeEqualTo(Date value) {
            addCriterion("check_time =", value, "check_time");
            return (Criteria) this;
        }

        public Criteria andCheck_timeNotEqualTo(Date value) {
            addCriterion("check_time <>", value, "check_time");
            return (Criteria) this;
        }

        public Criteria andCheck_timeGreaterThan(Date value) {
            addCriterion("check_time >", value, "check_time");
            return (Criteria) this;
        }

        public Criteria andCheck_timeGreaterThanOrEqualTo(Date value) {
            addCriterion("check_time >=", value, "check_time");
            return (Criteria) this;
        }

        public Criteria andCheck_timeLessThan(Date value) {
            addCriterion("check_time <", value, "check_time");
            return (Criteria) this;
        }

        public Criteria andCheck_timeLessThanOrEqualTo(Date value) {
            addCriterion("check_time <=", value, "check_time");
            return (Criteria) this;
        }

        public Criteria andCheck_timeIn(List<Date> values) {
            addCriterion("check_time in", values, "check_time");
            return (Criteria) this;
        }

        public Criteria andCheck_timeNotIn(List<Date> values) {
            addCriterion("check_time not in", values, "check_time");
            return (Criteria) this;
        }

        public Criteria andCheck_timeBetween(Date value1, Date value2) {
            addCriterion("check_time between", value1, value2, "check_time");
            return (Criteria) this;
        }

        public Criteria andCheck_timeNotBetween(Date value1, Date value2) {
            addCriterion("check_time not between", value1, value2, "check_time");
            return (Criteria) this;
        }

        public Criteria andCheck_content_amtIsNull() {
            addCriterion("check_content_amt is null");
            return (Criteria) this;
        }

        public Criteria andCheck_content_amtIsNotNull() {
            addCriterion("check_content_amt is not null");
            return (Criteria) this;
        }

        public Criteria andCheck_content_amtEqualTo(String value) {
            addCriterion("check_content_amt =", value, "check_content_amt");
            return (Criteria) this;
        }

        public Criteria andCheck_content_amtNotEqualTo(String value) {
            addCriterion("check_content_amt <>", value, "check_content_amt");
            return (Criteria) this;
        }

        public Criteria andCheck_content_amtGreaterThan(String value) {
            addCriterion("check_content_amt >", value, "check_content_amt");
            return (Criteria) this;
        }

        public Criteria andCheck_content_amtGreaterThanOrEqualTo(String value) {
            addCriterion("check_content_amt >=", value, "check_content_amt");
            return (Criteria) this;
        }

        public Criteria andCheck_content_amtLessThan(String value) {
            addCriterion("check_content_amt <", value, "check_content_amt");
            return (Criteria) this;
        }

        public Criteria andCheck_content_amtLessThanOrEqualTo(String value) {
            addCriterion("check_content_amt <=", value, "check_content_amt");
            return (Criteria) this;
        }

        public Criteria andCheck_content_amtLike(String value) {
            addCriterion("check_content_amt like", value, "check_content_amt");
            return (Criteria) this;
        }

        public Criteria andCheck_content_amtNotLike(String value) {
            addCriterion("check_content_amt not like", value, "check_content_amt");
            return (Criteria) this;
        }

        public Criteria andCheck_content_amtIn(List<String> values) {
            addCriterion("check_content_amt in", values, "check_content_amt");
            return (Criteria) this;
        }

        public Criteria andCheck_content_amtNotIn(List<String> values) {
            addCriterion("check_content_amt not in", values, "check_content_amt");
            return (Criteria) this;
        }

        public Criteria andCheck_content_amtBetween(String value1, String value2) {
            addCriterion("check_content_amt between", value1, value2, "check_content_amt");
            return (Criteria) this;
        }

        public Criteria andCheck_content_amtNotBetween(String value1, String value2) {
            addCriterion("check_content_amt not between", value1, value2, "check_content_amt");
            return (Criteria) this;
        }

        public Criteria andCheck_content_reqIsNull() {
            addCriterion("check_content_req is null");
            return (Criteria) this;
        }

        public Criteria andCheck_content_reqIsNotNull() {
            addCriterion("check_content_req is not null");
            return (Criteria) this;
        }

        public Criteria andCheck_content_reqEqualTo(String value) {
            addCriterion("check_content_req =", value, "check_content_req");
            return (Criteria) this;
        }

        public Criteria andCheck_content_reqNotEqualTo(String value) {
            addCriterion("check_content_req <>", value, "check_content_req");
            return (Criteria) this;
        }

        public Criteria andCheck_content_reqGreaterThan(String value) {
            addCriterion("check_content_req >", value, "check_content_req");
            return (Criteria) this;
        }

        public Criteria andCheck_content_reqGreaterThanOrEqualTo(String value) {
            addCriterion("check_content_req >=", value, "check_content_req");
            return (Criteria) this;
        }

        public Criteria andCheck_content_reqLessThan(String value) {
            addCriterion("check_content_req <", value, "check_content_req");
            return (Criteria) this;
        }

        public Criteria andCheck_content_reqLessThanOrEqualTo(String value) {
            addCriterion("check_content_req <=", value, "check_content_req");
            return (Criteria) this;
        }

        public Criteria andCheck_content_reqLike(String value) {
            addCriterion("check_content_req like", value, "check_content_req");
            return (Criteria) this;
        }

        public Criteria andCheck_content_reqNotLike(String value) {
            addCriterion("check_content_req not like", value, "check_content_req");
            return (Criteria) this;
        }

        public Criteria andCheck_content_reqIn(List<String> values) {
            addCriterion("check_content_req in", values, "check_content_req");
            return (Criteria) this;
        }

        public Criteria andCheck_content_reqNotIn(List<String> values) {
            addCriterion("check_content_req not in", values, "check_content_req");
            return (Criteria) this;
        }

        public Criteria andCheck_content_reqBetween(String value1, String value2) {
            addCriterion("check_content_req between", value1, value2, "check_content_req");
            return (Criteria) this;
        }

        public Criteria andCheck_content_reqNotBetween(String value1, String value2) {
            addCriterion("check_content_req not between", value1, value2, "check_content_req");
            return (Criteria) this;
        }

        public Criteria andChenk_resultIsNull() {
            addCriterion("chenk_result is null");
            return (Criteria) this;
        }

        public Criteria andChenk_resultIsNotNull() {
            addCriterion("chenk_result is not null");
            return (Criteria) this;
        }

        public Criteria andChenk_resultEqualTo(String value) {
            addCriterion("chenk_result =", value, "chenk_result");
            return (Criteria) this;
        }

        public Criteria andChenk_resultNotEqualTo(String value) {
            addCriterion("chenk_result <>", value, "chenk_result");
            return (Criteria) this;
        }

        public Criteria andChenk_resultGreaterThan(String value) {
            addCriterion("chenk_result >", value, "chenk_result");
            return (Criteria) this;
        }

        public Criteria andChenk_resultGreaterThanOrEqualTo(String value) {
            addCriterion("chenk_result >=", value, "chenk_result");
            return (Criteria) this;
        }

        public Criteria andChenk_resultLessThan(String value) {
            addCriterion("chenk_result <", value, "chenk_result");
            return (Criteria) this;
        }

        public Criteria andChenk_resultLessThanOrEqualTo(String value) {
            addCriterion("chenk_result <=", value, "chenk_result");
            return (Criteria) this;
        }

        public Criteria andChenk_resultLike(String value) {
            addCriterion("chenk_result like", value, "chenk_result");
            return (Criteria) this;
        }

        public Criteria andChenk_resultNotLike(String value) {
            addCriterion("chenk_result not like", value, "chenk_result");
            return (Criteria) this;
        }

        public Criteria andChenk_resultIn(List<String> values) {
            addCriterion("chenk_result in", values, "chenk_result");
            return (Criteria) this;
        }

        public Criteria andChenk_resultNotIn(List<String> values) {
            addCriterion("chenk_result not in", values, "chenk_result");
            return (Criteria) this;
        }

        public Criteria andChenk_resultBetween(String value1, String value2) {
            addCriterion("chenk_result between", value1, value2, "chenk_result");
            return (Criteria) this;
        }

        public Criteria andChenk_resultNotBetween(String value1, String value2) {
            addCriterion("chenk_result not between", value1, value2, "chenk_result");
            return (Criteria) this;
        }

        public Criteria andSuggestionIsNull() {
            addCriterion("suggestion is null");
            return (Criteria) this;
        }

        public Criteria andSuggestionIsNotNull() {
            addCriterion("suggestion is not null");
            return (Criteria) this;
        }

        public Criteria andSuggestionEqualTo(String value) {
            addCriterion("suggestion =", value, "suggestion");
            return (Criteria) this;
        }

        public Criteria andSuggestionNotEqualTo(String value) {
            addCriterion("suggestion <>", value, "suggestion");
            return (Criteria) this;
        }

        public Criteria andSuggestionGreaterThan(String value) {
            addCriterion("suggestion >", value, "suggestion");
            return (Criteria) this;
        }

        public Criteria andSuggestionGreaterThanOrEqualTo(String value) {
            addCriterion("suggestion >=", value, "suggestion");
            return (Criteria) this;
        }

        public Criteria andSuggestionLessThan(String value) {
            addCriterion("suggestion <", value, "suggestion");
            return (Criteria) this;
        }

        public Criteria andSuggestionLessThanOrEqualTo(String value) {
            addCriterion("suggestion <=", value, "suggestion");
            return (Criteria) this;
        }

        public Criteria andSuggestionLike(String value) {
            addCriterion("suggestion like", value, "suggestion");
            return (Criteria) this;
        }

        public Criteria andSuggestionNotLike(String value) {
            addCriterion("suggestion not like", value, "suggestion");
            return (Criteria) this;
        }

        public Criteria andSuggestionIn(List<String> values) {
            addCriterion("suggestion in", values, "suggestion");
            return (Criteria) this;
        }

        public Criteria andSuggestionNotIn(List<String> values) {
            addCriterion("suggestion not in", values, "suggestion");
            return (Criteria) this;
        }

        public Criteria andSuggestionBetween(String value1, String value2) {
            addCriterion("suggestion between", value1, value2, "suggestion");
            return (Criteria) this;
        }

        public Criteria andSuggestionNotBetween(String value1, String value2) {
            addCriterion("suggestion not between", value1, value2, "suggestion");
            return (Criteria) this;
        }

        public Criteria andRecheckIsNull() {
            addCriterion("recheck is null");
            return (Criteria) this;
        }

        public Criteria andRecheckIsNotNull() {
            addCriterion("recheck is not null");
            return (Criteria) this;
        }

        public Criteria andRecheckEqualTo(String value) {
            addCriterion("recheck =", value, "recheck");
            return (Criteria) this;
        }

        public Criteria andRecheckNotEqualTo(String value) {
            addCriterion("recheck <>", value, "recheck");
            return (Criteria) this;
        }

        public Criteria andRecheckGreaterThan(String value) {
            addCriterion("recheck >", value, "recheck");
            return (Criteria) this;
        }

        public Criteria andRecheckGreaterThanOrEqualTo(String value) {
            addCriterion("recheck >=", value, "recheck");
            return (Criteria) this;
        }

        public Criteria andRecheckLessThan(String value) {
            addCriterion("recheck <", value, "recheck");
            return (Criteria) this;
        }

        public Criteria andRecheckLessThanOrEqualTo(String value) {
            addCriterion("recheck <=", value, "recheck");
            return (Criteria) this;
        }

        public Criteria andRecheckLike(String value) {
            addCriterion("recheck like", value, "recheck");
            return (Criteria) this;
        }

        public Criteria andRecheckNotLike(String value) {
            addCriterion("recheck not like", value, "recheck");
            return (Criteria) this;
        }

        public Criteria andRecheckIn(List<String> values) {
            addCriterion("recheck in", values, "recheck");
            return (Criteria) this;
        }

        public Criteria andRecheckNotIn(List<String> values) {
            addCriterion("recheck not in", values, "recheck");
            return (Criteria) this;
        }

        public Criteria andRecheckBetween(String value1, String value2) {
            addCriterion("recheck between", value1, value2, "recheck");
            return (Criteria) this;
        }

        public Criteria andRecheckNotBetween(String value1, String value2) {
            addCriterion("recheck not between", value1, value2, "recheck");
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

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }
    }

    /**
     * clear_list_check_result
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * clear_list_check_result 2018-06-21
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