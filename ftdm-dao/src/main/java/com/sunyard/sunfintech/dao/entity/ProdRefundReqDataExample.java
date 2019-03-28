package com.sunyard.sunfintech.dao.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProdRefundReqDataExample {
    /**
     * prod_refund_req_data
     */
    protected String orderByClause;

    /**
     * prod_refund_req_data
     */
    protected boolean distinct;

    /**
     * prod_refund_req_data
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2018-07-23
     */
    public ProdRefundReqDataExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *
     * @mbggenerated 2018-07-23
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *
     * @mbggenerated 2018-07-23
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *
     * @mbggenerated 2018-07-23
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *
     * @mbggenerated 2018-07-23
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *
     * @mbggenerated 2018-07-23
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *
     * @mbggenerated 2018-07-23
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *
     * @mbggenerated 2018-07-23
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *
     * @mbggenerated 2018-07-23
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
     * @mbggenerated 2018-07-23
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *
     * @mbggenerated 2018-07-23
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * prod_refund_req_data 2018-07-23
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

        public Criteria andReal_repay_amountIsNull() {
            addCriterion("real_repay_amount is null");
            return (Criteria) this;
        }

        public Criteria andReal_repay_amountIsNotNull() {
            addCriterion("real_repay_amount is not null");
            return (Criteria) this;
        }

        public Criteria andReal_repay_amountEqualTo(BigDecimal value) {
            addCriterion("real_repay_amount =", value, "real_repay_amount");
            return (Criteria) this;
        }

        public Criteria andReal_repay_amountNotEqualTo(BigDecimal value) {
            addCriterion("real_repay_amount <>", value, "real_repay_amount");
            return (Criteria) this;
        }

        public Criteria andReal_repay_amountGreaterThan(BigDecimal value) {
            addCriterion("real_repay_amount >", value, "real_repay_amount");
            return (Criteria) this;
        }

        public Criteria andReal_repay_amountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("real_repay_amount >=", value, "real_repay_amount");
            return (Criteria) this;
        }

        public Criteria andReal_repay_amountLessThan(BigDecimal value) {
            addCriterion("real_repay_amount <", value, "real_repay_amount");
            return (Criteria) this;
        }

        public Criteria andReal_repay_amountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("real_repay_amount <=", value, "real_repay_amount");
            return (Criteria) this;
        }

        public Criteria andReal_repay_amountIn(List<BigDecimal> values) {
            addCriterion("real_repay_amount in", values, "real_repay_amount");
            return (Criteria) this;
        }

        public Criteria andReal_repay_amountNotIn(List<BigDecimal> values) {
            addCriterion("real_repay_amount not in", values, "real_repay_amount");
            return (Criteria) this;
        }

        public Criteria andReal_repay_amountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("real_repay_amount between", value1, value2, "real_repay_amount");
            return (Criteria) this;
        }

        public Criteria andReal_repay_amountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("real_repay_amount not between", value1, value2, "real_repay_amount");
            return (Criteria) this;
        }

        public Criteria andExperience_amtIsNull() {
            addCriterion("experience_amt is null");
            return (Criteria) this;
        }

        public Criteria andExperience_amtIsNotNull() {
            addCriterion("experience_amt is not null");
            return (Criteria) this;
        }

        public Criteria andExperience_amtEqualTo(BigDecimal value) {
            addCriterion("experience_amt =", value, "experience_amt");
            return (Criteria) this;
        }

        public Criteria andExperience_amtNotEqualTo(BigDecimal value) {
            addCriterion("experience_amt <>", value, "experience_amt");
            return (Criteria) this;
        }

        public Criteria andExperience_amtGreaterThan(BigDecimal value) {
            addCriterion("experience_amt >", value, "experience_amt");
            return (Criteria) this;
        }

        public Criteria andExperience_amtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("experience_amt >=", value, "experience_amt");
            return (Criteria) this;
        }

        public Criteria andExperience_amtLessThan(BigDecimal value) {
            addCriterion("experience_amt <", value, "experience_amt");
            return (Criteria) this;
        }

        public Criteria andExperience_amtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("experience_amt <=", value, "experience_amt");
            return (Criteria) this;
        }

        public Criteria andExperience_amtIn(List<BigDecimal> values) {
            addCriterion("experience_amt in", values, "experience_amt");
            return (Criteria) this;
        }

        public Criteria andExperience_amtNotIn(List<BigDecimal> values) {
            addCriterion("experience_amt not in", values, "experience_amt");
            return (Criteria) this;
        }

        public Criteria andExperience_amtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("experience_amt between", value1, value2, "experience_amt");
            return (Criteria) this;
        }

        public Criteria andExperience_amtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("experience_amt not between", value1, value2, "experience_amt");
            return (Criteria) this;
        }

        public Criteria andRates_amtIsNull() {
            addCriterion("rates_amt is null");
            return (Criteria) this;
        }

        public Criteria andRates_amtIsNotNull() {
            addCriterion("rates_amt is not null");
            return (Criteria) this;
        }

        public Criteria andRates_amtEqualTo(BigDecimal value) {
            addCriterion("rates_amt =", value, "rates_amt");
            return (Criteria) this;
        }

        public Criteria andRates_amtNotEqualTo(BigDecimal value) {
            addCriterion("rates_amt <>", value, "rates_amt");
            return (Criteria) this;
        }

        public Criteria andRates_amtGreaterThan(BigDecimal value) {
            addCriterion("rates_amt >", value, "rates_amt");
            return (Criteria) this;
        }

        public Criteria andRates_amtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("rates_amt >=", value, "rates_amt");
            return (Criteria) this;
        }

        public Criteria andRates_amtLessThan(BigDecimal value) {
            addCriterion("rates_amt <", value, "rates_amt");
            return (Criteria) this;
        }

        public Criteria andRates_amtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("rates_amt <=", value, "rates_amt");
            return (Criteria) this;
        }

        public Criteria andRates_amtIn(List<BigDecimal> values) {
            addCriterion("rates_amt in", values, "rates_amt");
            return (Criteria) this;
        }

        public Criteria andRates_amtNotIn(List<BigDecimal> values) {
            addCriterion("rates_amt not in", values, "rates_amt");
            return (Criteria) this;
        }

        public Criteria andRates_amtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("rates_amt between", value1, value2, "rates_amt");
            return (Criteria) this;
        }

        public Criteria andRates_amtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("rates_amt not between", value1, value2, "rates_amt");
            return (Criteria) this;
        }

        public Criteria andRepay_feeIsNull() {
            addCriterion("repay_fee is null");
            return (Criteria) this;
        }

        public Criteria andRepay_feeIsNotNull() {
            addCriterion("repay_fee is not null");
            return (Criteria) this;
        }

        public Criteria andRepay_feeEqualTo(BigDecimal value) {
            addCriterion("repay_fee =", value, "repay_fee");
            return (Criteria) this;
        }

        public Criteria andRepay_feeNotEqualTo(BigDecimal value) {
            addCriterion("repay_fee <>", value, "repay_fee");
            return (Criteria) this;
        }

        public Criteria andRepay_feeGreaterThan(BigDecimal value) {
            addCriterion("repay_fee >", value, "repay_fee");
            return (Criteria) this;
        }

        public Criteria andRepay_feeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_fee >=", value, "repay_fee");
            return (Criteria) this;
        }

        public Criteria andRepay_feeLessThan(BigDecimal value) {
            addCriterion("repay_fee <", value, "repay_fee");
            return (Criteria) this;
        }

        public Criteria andRepay_feeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_fee <=", value, "repay_fee");
            return (Criteria) this;
        }

        public Criteria andRepay_feeIn(List<BigDecimal> values) {
            addCriterion("repay_fee in", values, "repay_fee");
            return (Criteria) this;
        }

        public Criteria andRepay_feeNotIn(List<BigDecimal> values) {
            addCriterion("repay_fee not in", values, "repay_fee");
            return (Criteria) this;
        }

        public Criteria andRepay_feeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_fee between", value1, value2, "repay_fee");
            return (Criteria) this;
        }

        public Criteria andRepay_feeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_fee not between", value1, value2, "repay_fee");
            return (Criteria) this;
        }

        public Criteria andReal_repay_valIsNull() {
            addCriterion("real_repay_val is null");
            return (Criteria) this;
        }

        public Criteria andReal_repay_valIsNotNull() {
            addCriterion("real_repay_val is not null");
            return (Criteria) this;
        }

        public Criteria andReal_repay_valEqualTo(BigDecimal value) {
            addCriterion("real_repay_val =", value, "real_repay_val");
            return (Criteria) this;
        }

        public Criteria andReal_repay_valNotEqualTo(BigDecimal value) {
            addCriterion("real_repay_val <>", value, "real_repay_val");
            return (Criteria) this;
        }

        public Criteria andReal_repay_valGreaterThan(BigDecimal value) {
            addCriterion("real_repay_val >", value, "real_repay_val");
            return (Criteria) this;
        }

        public Criteria andReal_repay_valGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("real_repay_val >=", value, "real_repay_val");
            return (Criteria) this;
        }

        public Criteria andReal_repay_valLessThan(BigDecimal value) {
            addCriterion("real_repay_val <", value, "real_repay_val");
            return (Criteria) this;
        }

        public Criteria andReal_repay_valLessThanOrEqualTo(BigDecimal value) {
            addCriterion("real_repay_val <=", value, "real_repay_val");
            return (Criteria) this;
        }

        public Criteria andReal_repay_valIn(List<BigDecimal> values) {
            addCriterion("real_repay_val in", values, "real_repay_val");
            return (Criteria) this;
        }

        public Criteria andReal_repay_valNotIn(List<BigDecimal> values) {
            addCriterion("real_repay_val not in", values, "real_repay_val");
            return (Criteria) this;
        }

        public Criteria andReal_repay_valBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("real_repay_val between", value1, value2, "real_repay_val");
            return (Criteria) this;
        }

        public Criteria andReal_repay_valNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("real_repay_val not between", value1, value2, "real_repay_val");
            return (Criteria) this;
        }

        public Criteria andCust_noIsNull() {
            addCriterion("cust_no is null");
            return (Criteria) this;
        }

        public Criteria andCust_noIsNotNull() {
            addCriterion("cust_no is not null");
            return (Criteria) this;
        }

        public Criteria andCust_noEqualTo(String value) {
            addCriterion("cust_no =", value, "cust_no");
            return (Criteria) this;
        }

        public Criteria andCust_noNotEqualTo(String value) {
            addCriterion("cust_no <>", value, "cust_no");
            return (Criteria) this;
        }

        public Criteria andCust_noGreaterThan(String value) {
            addCriterion("cust_no >", value, "cust_no");
            return (Criteria) this;
        }

        public Criteria andCust_noGreaterThanOrEqualTo(String value) {
            addCriterion("cust_no >=", value, "cust_no");
            return (Criteria) this;
        }

        public Criteria andCust_noLessThan(String value) {
            addCriterion("cust_no <", value, "cust_no");
            return (Criteria) this;
        }

        public Criteria andCust_noLessThanOrEqualTo(String value) {
            addCriterion("cust_no <=", value, "cust_no");
            return (Criteria) this;
        }

        public Criteria andCust_noLike(String value) {
            addCriterion("cust_no like", value, "cust_no");
            return (Criteria) this;
        }

        public Criteria andCust_noNotLike(String value) {
            addCriterion("cust_no not like", value, "cust_no");
            return (Criteria) this;
        }

        public Criteria andCust_noIn(List<String> values) {
            addCriterion("cust_no in", values, "cust_no");
            return (Criteria) this;
        }

        public Criteria andCust_noNotIn(List<String> values) {
            addCriterion("cust_no not in", values, "cust_no");
            return (Criteria) this;
        }

        public Criteria andCust_noBetween(String value1, String value2) {
            addCriterion("cust_no between", value1, value2, "cust_no");
            return (Criteria) this;
        }

        public Criteria andCust_noNotBetween(String value1, String value2) {
            addCriterion("cust_no not between", value1, value2, "cust_no");
            return (Criteria) this;
        }

        public Criteria andRepay_numIsNull() {
            addCriterion("repay_num is null");
            return (Criteria) this;
        }

        public Criteria andRepay_numIsNotNull() {
            addCriterion("repay_num is not null");
            return (Criteria) this;
        }

        public Criteria andRepay_numEqualTo(String value) {
            addCriterion("repay_num =", value, "repay_num");
            return (Criteria) this;
        }

        public Criteria andRepay_numNotEqualTo(String value) {
            addCriterion("repay_num <>", value, "repay_num");
            return (Criteria) this;
        }

        public Criteria andRepay_numGreaterThan(String value) {
            addCriterion("repay_num >", value, "repay_num");
            return (Criteria) this;
        }

        public Criteria andRepay_numGreaterThanOrEqualTo(String value) {
            addCriterion("repay_num >=", value, "repay_num");
            return (Criteria) this;
        }

        public Criteria andRepay_numLessThan(String value) {
            addCriterion("repay_num <", value, "repay_num");
            return (Criteria) this;
        }

        public Criteria andRepay_numLessThanOrEqualTo(String value) {
            addCriterion("repay_num <=", value, "repay_num");
            return (Criteria) this;
        }

        public Criteria andRepay_numLike(String value) {
            addCriterion("repay_num like", value, "repay_num");
            return (Criteria) this;
        }

        public Criteria andRepay_numNotLike(String value) {
            addCriterion("repay_num not like", value, "repay_num");
            return (Criteria) this;
        }

        public Criteria andRepay_numIn(List<String> values) {
            addCriterion("repay_num in", values, "repay_num");
            return (Criteria) this;
        }

        public Criteria andRepay_numNotIn(List<String> values) {
            addCriterion("repay_num not in", values, "repay_num");
            return (Criteria) this;
        }

        public Criteria andRepay_numBetween(String value1, String value2) {
            addCriterion("repay_num between", value1, value2, "repay_num");
            return (Criteria) this;
        }

        public Criteria andRepay_numNotBetween(String value1, String value2) {
            addCriterion("repay_num not between", value1, value2, "repay_num");
            return (Criteria) this;
        }

        public Criteria andRepay_dateIsNull() {
            addCriterion("repay_date is null");
            return (Criteria) this;
        }

        public Criteria andRepay_dateIsNotNull() {
            addCriterion("repay_date is not null");
            return (Criteria) this;
        }

        public Criteria andRepay_dateEqualTo(Date value) {
            addCriterion("repay_date =", value, "repay_date");
            return (Criteria) this;
        }

        public Criteria andRepay_dateNotEqualTo(Date value) {
            addCriterion("repay_date <>", value, "repay_date");
            return (Criteria) this;
        }

        public Criteria andRepay_dateGreaterThan(Date value) {
            addCriterion("repay_date >", value, "repay_date");
            return (Criteria) this;
        }

        public Criteria andRepay_dateGreaterThanOrEqualTo(Date value) {
            addCriterion("repay_date >=", value, "repay_date");
            return (Criteria) this;
        }

        public Criteria andRepay_dateLessThan(Date value) {
            addCriterion("repay_date <", value, "repay_date");
            return (Criteria) this;
        }

        public Criteria andRepay_dateLessThanOrEqualTo(Date value) {
            addCriterion("repay_date <=", value, "repay_date");
            return (Criteria) this;
        }

        public Criteria andRepay_dateIn(List<Date> values) {
            addCriterion("repay_date in", values, "repay_date");
            return (Criteria) this;
        }

        public Criteria andRepay_dateNotIn(List<Date> values) {
            addCriterion("repay_date not in", values, "repay_date");
            return (Criteria) this;
        }

        public Criteria andRepay_dateBetween(Date value1, Date value2) {
            addCriterion("repay_date between", value1, value2, "repay_date");
            return (Criteria) this;
        }

        public Criteria andRepay_dateNotBetween(Date value1, Date value2) {
            addCriterion("repay_date not between", value1, value2, "repay_date");
            return (Criteria) this;
        }

        public Criteria andReal_repay_dateIsNull() {
            addCriterion("real_repay_date is null");
            return (Criteria) this;
        }

        public Criteria andReal_repay_dateIsNotNull() {
            addCriterion("real_repay_date is not null");
            return (Criteria) this;
        }

        public Criteria andReal_repay_dateEqualTo(Date value) {
            addCriterion("real_repay_date =", value, "real_repay_date");
            return (Criteria) this;
        }

        public Criteria andReal_repay_dateNotEqualTo(Date value) {
            addCriterion("real_repay_date <>", value, "real_repay_date");
            return (Criteria) this;
        }

        public Criteria andReal_repay_dateGreaterThan(Date value) {
            addCriterion("real_repay_date >", value, "real_repay_date");
            return (Criteria) this;
        }

        public Criteria andReal_repay_dateGreaterThanOrEqualTo(Date value) {
            addCriterion("real_repay_date >=", value, "real_repay_date");
            return (Criteria) this;
        }

        public Criteria andReal_repay_dateLessThan(Date value) {
            addCriterion("real_repay_date <", value, "real_repay_date");
            return (Criteria) this;
        }

        public Criteria andReal_repay_dateLessThanOrEqualTo(Date value) {
            addCriterion("real_repay_date <=", value, "real_repay_date");
            return (Criteria) this;
        }

        public Criteria andReal_repay_dateIn(List<Date> values) {
            addCriterion("real_repay_date in", values, "real_repay_date");
            return (Criteria) this;
        }

        public Criteria andReal_repay_dateNotIn(List<Date> values) {
            addCriterion("real_repay_date not in", values, "real_repay_date");
            return (Criteria) this;
        }

        public Criteria andReal_repay_dateBetween(Date value1, Date value2) {
            addCriterion("real_repay_date between", value1, value2, "real_repay_date");
            return (Criteria) this;
        }

        public Criteria andReal_repay_dateNotBetween(Date value1, Date value2) {
            addCriterion("real_repay_date not between", value1, value2, "real_repay_date");
            return (Criteria) this;
        }

        public Criteria andIs_payoffIsNull() {
            addCriterion("is_payoff is null");
            return (Criteria) this;
        }

        public Criteria andIs_payoffIsNotNull() {
            addCriterion("is_payoff is not null");
            return (Criteria) this;
        }

        public Criteria andIs_payoffEqualTo(String value) {
            addCriterion("is_payoff =", value, "is_payoff");
            return (Criteria) this;
        }

        public Criteria andIs_payoffNotEqualTo(String value) {
            addCriterion("is_payoff <>", value, "is_payoff");
            return (Criteria) this;
        }

        public Criteria andIs_payoffGreaterThan(String value) {
            addCriterion("is_payoff >", value, "is_payoff");
            return (Criteria) this;
        }

        public Criteria andIs_payoffGreaterThanOrEqualTo(String value) {
            addCriterion("is_payoff >=", value, "is_payoff");
            return (Criteria) this;
        }

        public Criteria andIs_payoffLessThan(String value) {
            addCriterion("is_payoff <", value, "is_payoff");
            return (Criteria) this;
        }

        public Criteria andIs_payoffLessThanOrEqualTo(String value) {
            addCriterion("is_payoff <=", value, "is_payoff");
            return (Criteria) this;
        }

        public Criteria andIs_payoffLike(String value) {
            addCriterion("is_payoff like", value, "is_payoff");
            return (Criteria) this;
        }

        public Criteria andIs_payoffNotLike(String value) {
            addCriterion("is_payoff not like", value, "is_payoff");
            return (Criteria) this;
        }

        public Criteria andIs_payoffIn(List<String> values) {
            addCriterion("is_payoff in", values, "is_payoff");
            return (Criteria) this;
        }

        public Criteria andIs_payoffNotIn(List<String> values) {
            addCriterion("is_payoff not in", values, "is_payoff");
            return (Criteria) this;
        }

        public Criteria andIs_payoffBetween(String value1, String value2) {
            addCriterion("is_payoff between", value1, value2, "is_payoff");
            return (Criteria) this;
        }

        public Criteria andIs_payoffNotBetween(String value1, String value2) {
            addCriterion("is_payoff not between", value1, value2, "is_payoff");
            return (Criteria) this;
        }

        public Criteria andRepay_flagIsNull() {
            addCriterion("repay_flag is null");
            return (Criteria) this;
        }

        public Criteria andRepay_flagIsNotNull() {
            addCriterion("repay_flag is not null");
            return (Criteria) this;
        }

        public Criteria andRepay_flagEqualTo(String value) {
            addCriterion("repay_flag =", value, "repay_flag");
            return (Criteria) this;
        }

        public Criteria andRepay_flagNotEqualTo(String value) {
            addCriterion("repay_flag <>", value, "repay_flag");
            return (Criteria) this;
        }

        public Criteria andRepay_flagGreaterThan(String value) {
            addCriterion("repay_flag >", value, "repay_flag");
            return (Criteria) this;
        }

        public Criteria andRepay_flagGreaterThanOrEqualTo(String value) {
            addCriterion("repay_flag >=", value, "repay_flag");
            return (Criteria) this;
        }

        public Criteria andRepay_flagLessThan(String value) {
            addCriterion("repay_flag <", value, "repay_flag");
            return (Criteria) this;
        }

        public Criteria andRepay_flagLessThanOrEqualTo(String value) {
            addCriterion("repay_flag <=", value, "repay_flag");
            return (Criteria) this;
        }

        public Criteria andRepay_flagLike(String value) {
            addCriterion("repay_flag like", value, "repay_flag");
            return (Criteria) this;
        }

        public Criteria andRepay_flagNotLike(String value) {
            addCriterion("repay_flag not like", value, "repay_flag");
            return (Criteria) this;
        }

        public Criteria andRepay_flagIn(List<String> values) {
            addCriterion("repay_flag in", values, "repay_flag");
            return (Criteria) this;
        }

        public Criteria andRepay_flagNotIn(List<String> values) {
            addCriterion("repay_flag not in", values, "repay_flag");
            return (Criteria) this;
        }

        public Criteria andRepay_flagBetween(String value1, String value2) {
            addCriterion("repay_flag between", value1, value2, "repay_flag");
            return (Criteria) this;
        }

        public Criteria andRepay_flagNotBetween(String value1, String value2) {
            addCriterion("repay_flag not between", value1, value2, "repay_flag");
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
    }

    /**
     * prod_refund_req_data
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * prod_refund_req_data 2018-07-23
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