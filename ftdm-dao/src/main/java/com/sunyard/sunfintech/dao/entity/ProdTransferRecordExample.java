package com.sunyard.sunfintech.dao.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProdTransferRecordExample {
    /**
     * prod_transfer_record
     */
    protected String orderByClause;

    /**
     * prod_transfer_record
     */
    protected boolean distinct;

    /**
     * prod_transfer_record
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2017-06-01
     */
    public ProdTransferRecordExample() {
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
     * prod_transfer_record 2017-06-01
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

        public Criteria andPtrans_dateIsNull() {
            addCriterion("ptrans_date is null");
            return (Criteria) this;
        }

        public Criteria andPtrans_dateIsNotNull() {
            addCriterion("ptrans_date is not null");
            return (Criteria) this;
        }

        public Criteria andPtrans_dateEqualTo(String value) {
            addCriterion("ptrans_date =", value, "ptrans_date");
            return (Criteria) this;
        }

        public Criteria andPtrans_dateNotEqualTo(String value) {
            addCriterion("ptrans_date <>", value, "ptrans_date");
            return (Criteria) this;
        }

        public Criteria andPtrans_dateGreaterThan(String value) {
            addCriterion("ptrans_date >", value, "ptrans_date");
            return (Criteria) this;
        }

        public Criteria andPtrans_dateGreaterThanOrEqualTo(String value) {
            addCriterion("ptrans_date >=", value, "ptrans_date");
            return (Criteria) this;
        }

        public Criteria andPtrans_dateLessThan(String value) {
            addCriterion("ptrans_date <", value, "ptrans_date");
            return (Criteria) this;
        }

        public Criteria andPtrans_dateLessThanOrEqualTo(String value) {
            addCriterion("ptrans_date <=", value, "ptrans_date");
            return (Criteria) this;
        }

        public Criteria andPtrans_dateLike(String value) {
            addCriterion("ptrans_date like", value, "ptrans_date");
            return (Criteria) this;
        }

        public Criteria andPtrans_dateNotLike(String value) {
            addCriterion("ptrans_date not like", value, "ptrans_date");
            return (Criteria) this;
        }

        public Criteria andPtrans_dateIn(List<String> values) {
            addCriterion("ptrans_date in", values, "ptrans_date");
            return (Criteria) this;
        }

        public Criteria andPtrans_dateNotIn(List<String> values) {
            addCriterion("ptrans_date not in", values, "ptrans_date");
            return (Criteria) this;
        }

        public Criteria andPtrans_dateBetween(String value1, String value2) {
            addCriterion("ptrans_date between", value1, value2, "ptrans_date");
            return (Criteria) this;
        }

        public Criteria andPtrans_dateNotBetween(String value1, String value2) {
            addCriterion("ptrans_date not between", value1, value2, "ptrans_date");
            return (Criteria) this;
        }

        public Criteria andPartner_trans_timeIsNull() {
            addCriterion("partner_trans_time is null");
            return (Criteria) this;
        }

        public Criteria andPartner_trans_timeIsNotNull() {
            addCriterion("partner_trans_time is not null");
            return (Criteria) this;
        }

        public Criteria andPartner_trans_timeEqualTo(String value) {
            addCriterion("partner_trans_time =", value, "partner_trans_time");
            return (Criteria) this;
        }

        public Criteria andPartner_trans_timeNotEqualTo(String value) {
            addCriterion("partner_trans_time <>", value, "partner_trans_time");
            return (Criteria) this;
        }

        public Criteria andPartner_trans_timeGreaterThan(String value) {
            addCriterion("partner_trans_time >", value, "partner_trans_time");
            return (Criteria) this;
        }

        public Criteria andPartner_trans_timeGreaterThanOrEqualTo(String value) {
            addCriterion("partner_trans_time >=", value, "partner_trans_time");
            return (Criteria) this;
        }

        public Criteria andPartner_trans_timeLessThan(String value) {
            addCriterion("partner_trans_time <", value, "partner_trans_time");
            return (Criteria) this;
        }

        public Criteria andPartner_trans_timeLessThanOrEqualTo(String value) {
            addCriterion("partner_trans_time <=", value, "partner_trans_time");
            return (Criteria) this;
        }

        public Criteria andPartner_trans_timeLike(String value) {
            addCriterion("partner_trans_time like", value, "partner_trans_time");
            return (Criteria) this;
        }

        public Criteria andPartner_trans_timeNotLike(String value) {
            addCriterion("partner_trans_time not like", value, "partner_trans_time");
            return (Criteria) this;
        }

        public Criteria andPartner_trans_timeIn(List<String> values) {
            addCriterion("partner_trans_time in", values, "partner_trans_time");
            return (Criteria) this;
        }

        public Criteria andPartner_trans_timeNotIn(List<String> values) {
            addCriterion("partner_trans_time not in", values, "partner_trans_time");
            return (Criteria) this;
        }

        public Criteria andPartner_trans_timeBetween(String value1, String value2) {
            addCriterion("partner_trans_time between", value1, value2, "partner_trans_time");
            return (Criteria) this;
        }

        public Criteria andPartner_trans_timeNotBetween(String value1, String value2) {
            addCriterion("partner_trans_time not between", value1, value2, "partner_trans_time");
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

        public Criteria andTrans_shareIsNull() {
            addCriterion("trans_share is null");
            return (Criteria) this;
        }

        public Criteria andTrans_shareIsNotNull() {
            addCriterion("trans_share is not null");
            return (Criteria) this;
        }

        public Criteria andTrans_shareEqualTo(BigDecimal value) {
            addCriterion("trans_share =", value, "trans_share");
            return (Criteria) this;
        }

        public Criteria andTrans_shareNotEqualTo(BigDecimal value) {
            addCriterion("trans_share <>", value, "trans_share");
            return (Criteria) this;
        }

        public Criteria andTrans_shareGreaterThan(BigDecimal value) {
            addCriterion("trans_share >", value, "trans_share");
            return (Criteria) this;
        }

        public Criteria andTrans_shareGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("trans_share >=", value, "trans_share");
            return (Criteria) this;
        }

        public Criteria andTrans_shareLessThan(BigDecimal value) {
            addCriterion("trans_share <", value, "trans_share");
            return (Criteria) this;
        }

        public Criteria andTrans_shareLessThanOrEqualTo(BigDecimal value) {
            addCriterion("trans_share <=", value, "trans_share");
            return (Criteria) this;
        }

        public Criteria andTrans_shareIn(List<BigDecimal> values) {
            addCriterion("trans_share in", values, "trans_share");
            return (Criteria) this;
        }

        public Criteria andTrans_shareNotIn(List<BigDecimal> values) {
            addCriterion("trans_share not in", values, "trans_share");
            return (Criteria) this;
        }

        public Criteria andTrans_shareBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("trans_share between", value1, value2, "trans_share");
            return (Criteria) this;
        }

        public Criteria andTrans_shareNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("trans_share not between", value1, value2, "trans_share");
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

        public Criteria andTrans_amtIsNull() {
            addCriterion("trans_amt is null");
            return (Criteria) this;
        }

        public Criteria andTrans_amtIsNotNull() {
            addCriterion("trans_amt is not null");
            return (Criteria) this;
        }

        public Criteria andTrans_amtEqualTo(BigDecimal value) {
            addCriterion("trans_amt =", value, "trans_amt");
            return (Criteria) this;
        }

        public Criteria andTrans_amtNotEqualTo(BigDecimal value) {
            addCriterion("trans_amt <>", value, "trans_amt");
            return (Criteria) this;
        }

        public Criteria andTrans_amtGreaterThan(BigDecimal value) {
            addCriterion("trans_amt >", value, "trans_amt");
            return (Criteria) this;
        }

        public Criteria andTrans_amtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("trans_amt >=", value, "trans_amt");
            return (Criteria) this;
        }

        public Criteria andTrans_amtLessThan(BigDecimal value) {
            addCriterion("trans_amt <", value, "trans_amt");
            return (Criteria) this;
        }

        public Criteria andTrans_amtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("trans_amt <=", value, "trans_amt");
            return (Criteria) this;
        }

        public Criteria andTrans_amtIn(List<BigDecimal> values) {
            addCriterion("trans_amt in", values, "trans_amt");
            return (Criteria) this;
        }

        public Criteria andTrans_amtNotIn(List<BigDecimal> values) {
            addCriterion("trans_amt not in", values, "trans_amt");
            return (Criteria) this;
        }

        public Criteria andTrans_amtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("trans_amt between", value1, value2, "trans_amt");
            return (Criteria) this;
        }

        public Criteria andTrans_amtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("trans_amt not between", value1, value2, "trans_amt");
            return (Criteria) this;
        }

        public Criteria andDeal_amoutIsNull() {
            addCriterion("deal_amout is null");
            return (Criteria) this;
        }

        public Criteria andDeal_amoutIsNotNull() {
            addCriterion("deal_amout is not null");
            return (Criteria) this;
        }

        public Criteria andDeal_amoutEqualTo(BigDecimal value) {
            addCriterion("deal_amout =", value, "deal_amout");
            return (Criteria) this;
        }

        public Criteria andDeal_amoutNotEqualTo(BigDecimal value) {
            addCriterion("deal_amout <>", value, "deal_amout");
            return (Criteria) this;
        }

        public Criteria andDeal_amoutGreaterThan(BigDecimal value) {
            addCriterion("deal_amout >", value, "deal_amout");
            return (Criteria) this;
        }

        public Criteria andDeal_amoutGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("deal_amout >=", value, "deal_amout");
            return (Criteria) this;
        }

        public Criteria andDeal_amoutLessThan(BigDecimal value) {
            addCriterion("deal_amout <", value, "deal_amout");
            return (Criteria) this;
        }

        public Criteria andDeal_amoutLessThanOrEqualTo(BigDecimal value) {
            addCriterion("deal_amout <=", value, "deal_amout");
            return (Criteria) this;
        }

        public Criteria andDeal_amoutIn(List<BigDecimal> values) {
            addCriterion("deal_amout in", values, "deal_amout");
            return (Criteria) this;
        }

        public Criteria andDeal_amoutNotIn(List<BigDecimal> values) {
            addCriterion("deal_amout not in", values, "deal_amout");
            return (Criteria) this;
        }

        public Criteria andDeal_amoutBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("deal_amout between", value1, value2, "deal_amout");
            return (Criteria) this;
        }

        public Criteria andDeal_amoutNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("deal_amout not between", value1, value2, "deal_amout");
            return (Criteria) this;
        }

        public Criteria andInvest_amtIsNull() {
            addCriterion("invest_amt is null");
            return (Criteria) this;
        }

        public Criteria andInvest_amtIsNotNull() {
            addCriterion("invest_amt is not null");
            return (Criteria) this;
        }

        public Criteria andInvest_amtEqualTo(BigDecimal value) {
            addCriterion("invest_amt =", value, "invest_amt");
            return (Criteria) this;
        }

        public Criteria andInvest_amtNotEqualTo(BigDecimal value) {
            addCriterion("invest_amt <>", value, "invest_amt");
            return (Criteria) this;
        }

        public Criteria andInvest_amtGreaterThan(BigDecimal value) {
            addCriterion("invest_amt >", value, "invest_amt");
            return (Criteria) this;
        }

        public Criteria andInvest_amtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("invest_amt >=", value, "invest_amt");
            return (Criteria) this;
        }

        public Criteria andInvest_amtLessThan(BigDecimal value) {
            addCriterion("invest_amt <", value, "invest_amt");
            return (Criteria) this;
        }

        public Criteria andInvest_amtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("invest_amt <=", value, "invest_amt");
            return (Criteria) this;
        }

        public Criteria andInvest_amtIn(List<BigDecimal> values) {
            addCriterion("invest_amt in", values, "invest_amt");
            return (Criteria) this;
        }

        public Criteria andInvest_amtNotIn(List<BigDecimal> values) {
            addCriterion("invest_amt not in", values, "invest_amt");
            return (Criteria) this;
        }

        public Criteria andInvest_amtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invest_amt between", value1, value2, "invest_amt");
            return (Criteria) this;
        }

        public Criteria andInvest_amtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invest_amt not between", value1, value2, "invest_amt");
            return (Criteria) this;
        }

        public Criteria andExtract_amtIsNull() {
            addCriterion("extract_amt is null");
            return (Criteria) this;
        }

        public Criteria andExtract_amtIsNotNull() {
            addCriterion("extract_amt is not null");
            return (Criteria) this;
        }

        public Criteria andExtract_amtEqualTo(BigDecimal value) {
            addCriterion("extract_amt =", value, "extract_amt");
            return (Criteria) this;
        }

        public Criteria andExtract_amtNotEqualTo(BigDecimal value) {
            addCriterion("extract_amt <>", value, "extract_amt");
            return (Criteria) this;
        }

        public Criteria andExtract_amtGreaterThan(BigDecimal value) {
            addCriterion("extract_amt >", value, "extract_amt");
            return (Criteria) this;
        }

        public Criteria andExtract_amtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("extract_amt >=", value, "extract_amt");
            return (Criteria) this;
        }

        public Criteria andExtract_amtLessThan(BigDecimal value) {
            addCriterion("extract_amt <", value, "extract_amt");
            return (Criteria) this;
        }

        public Criteria andExtract_amtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("extract_amt <=", value, "extract_amt");
            return (Criteria) this;
        }

        public Criteria andExtract_amtIn(List<BigDecimal> values) {
            addCriterion("extract_amt in", values, "extract_amt");
            return (Criteria) this;
        }

        public Criteria andExtract_amtNotIn(List<BigDecimal> values) {
            addCriterion("extract_amt not in", values, "extract_amt");
            return (Criteria) this;
        }

        public Criteria andExtract_amtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("extract_amt between", value1, value2, "extract_amt");
            return (Criteria) this;
        }

        public Criteria andExtract_amtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("extract_amt not between", value1, value2, "extract_amt");
            return (Criteria) this;
        }

        public Criteria andDeal_platcustIsNull() {
            addCriterion("deal_platcust is null");
            return (Criteria) this;
        }

        public Criteria andDeal_platcustIsNotNull() {
            addCriterion("deal_platcust is not null");
            return (Criteria) this;
        }

        public Criteria andDeal_platcustEqualTo(String value) {
            addCriterion("deal_platcust =", value, "deal_platcust");
            return (Criteria) this;
        }

        public Criteria andDeal_platcustNotEqualTo(String value) {
            addCriterion("deal_platcust <>", value, "deal_platcust");
            return (Criteria) this;
        }

        public Criteria andDeal_platcustGreaterThan(String value) {
            addCriterion("deal_platcust >", value, "deal_platcust");
            return (Criteria) this;
        }

        public Criteria andDeal_platcustGreaterThanOrEqualTo(String value) {
            addCriterion("deal_platcust >=", value, "deal_platcust");
            return (Criteria) this;
        }

        public Criteria andDeal_platcustLessThan(String value) {
            addCriterion("deal_platcust <", value, "deal_platcust");
            return (Criteria) this;
        }

        public Criteria andDeal_platcustLessThanOrEqualTo(String value) {
            addCriterion("deal_platcust <=", value, "deal_platcust");
            return (Criteria) this;
        }

        public Criteria andDeal_platcustLike(String value) {
            addCriterion("deal_platcust like", value, "deal_platcust");
            return (Criteria) this;
        }

        public Criteria andDeal_platcustNotLike(String value) {
            addCriterion("deal_platcust not like", value, "deal_platcust");
            return (Criteria) this;
        }

        public Criteria andDeal_platcustIn(List<String> values) {
            addCriterion("deal_platcust in", values, "deal_platcust");
            return (Criteria) this;
        }

        public Criteria andDeal_platcustNotIn(List<String> values) {
            addCriterion("deal_platcust not in", values, "deal_platcust");
            return (Criteria) this;
        }

        public Criteria andDeal_platcustBetween(String value1, String value2) {
            addCriterion("deal_platcust between", value1, value2, "deal_platcust");
            return (Criteria) this;
        }

        public Criteria andDeal_platcustNotBetween(String value1, String value2) {
            addCriterion("deal_platcust not between", value1, value2, "deal_platcust");
            return (Criteria) this;
        }

        public Criteria andCommissionIsNull() {
            addCriterion("commission is null");
            return (Criteria) this;
        }

        public Criteria andCommissionIsNotNull() {
            addCriterion("commission is not null");
            return (Criteria) this;
        }

        public Criteria andCommissionEqualTo(String value) {
            addCriterion("commission =", value, "commission");
            return (Criteria) this;
        }

        public Criteria andCommissionNotEqualTo(String value) {
            addCriterion("commission <>", value, "commission");
            return (Criteria) this;
        }

        public Criteria andCommissionGreaterThan(String value) {
            addCriterion("commission >", value, "commission");
            return (Criteria) this;
        }

        public Criteria andCommissionGreaterThanOrEqualTo(String value) {
            addCriterion("commission >=", value, "commission");
            return (Criteria) this;
        }

        public Criteria andCommissionLessThan(String value) {
            addCriterion("commission <", value, "commission");
            return (Criteria) this;
        }

        public Criteria andCommissionLessThanOrEqualTo(String value) {
            addCriterion("commission <=", value, "commission");
            return (Criteria) this;
        }

        public Criteria andCommissionLike(String value) {
            addCriterion("commission like", value, "commission");
            return (Criteria) this;
        }

        public Criteria andCommissionNotLike(String value) {
            addCriterion("commission not like", value, "commission");
            return (Criteria) this;
        }

        public Criteria andCommissionIn(List<String> values) {
            addCriterion("commission in", values, "commission");
            return (Criteria) this;
        }

        public Criteria andCommissionNotIn(List<String> values) {
            addCriterion("commission not in", values, "commission");
            return (Criteria) this;
        }

        public Criteria andCommissionBetween(String value1, String value2) {
            addCriterion("commission between", value1, value2, "commission");
            return (Criteria) this;
        }

        public Criteria andCommissionNotBetween(String value1, String value2) {
            addCriterion("commission not between", value1, value2, "commission");
            return (Criteria) this;
        }

        public Criteria andCommission_extIsNull() {
            addCriterion("commission_ext is null");
            return (Criteria) this;
        }

        public Criteria andCommission_extIsNotNull() {
            addCriterion("commission_ext is not null");
            return (Criteria) this;
        }

        public Criteria andCommission_extEqualTo(String value) {
            addCriterion("commission_ext =", value, "commission_ext");
            return (Criteria) this;
        }

        public Criteria andCommission_extNotEqualTo(String value) {
            addCriterion("commission_ext <>", value, "commission_ext");
            return (Criteria) this;
        }

        public Criteria andCommission_extGreaterThan(String value) {
            addCriterion("commission_ext >", value, "commission_ext");
            return (Criteria) this;
        }

        public Criteria andCommission_extGreaterThanOrEqualTo(String value) {
            addCriterion("commission_ext >=", value, "commission_ext");
            return (Criteria) this;
        }

        public Criteria andCommission_extLessThan(String value) {
            addCriterion("commission_ext <", value, "commission_ext");
            return (Criteria) this;
        }

        public Criteria andCommission_extLessThanOrEqualTo(String value) {
            addCriterion("commission_ext <=", value, "commission_ext");
            return (Criteria) this;
        }

        public Criteria andCommission_extLike(String value) {
            addCriterion("commission_ext like", value, "commission_ext");
            return (Criteria) this;
        }

        public Criteria andCommission_extNotLike(String value) {
            addCriterion("commission_ext not like", value, "commission_ext");
            return (Criteria) this;
        }

        public Criteria andCommission_extIn(List<String> values) {
            addCriterion("commission_ext in", values, "commission_ext");
            return (Criteria) this;
        }

        public Criteria andCommission_extNotIn(List<String> values) {
            addCriterion("commission_ext not in", values, "commission_ext");
            return (Criteria) this;
        }

        public Criteria andCommission_extBetween(String value1, String value2) {
            addCriterion("commission_ext between", value1, value2, "commission_ext");
            return (Criteria) this;
        }

        public Criteria andCommission_extNotBetween(String value1, String value2) {
            addCriterion("commission_ext not between", value1, value2, "commission_ext");
            return (Criteria) this;
        }

        public Criteria andPublish_dateIsNull() {
            addCriterion("publish_date is null");
            return (Criteria) this;
        }

        public Criteria andPublish_dateIsNotNull() {
            addCriterion("publish_date is not null");
            return (Criteria) this;
        }

        public Criteria andPublish_dateEqualTo(Date value) {
            addCriterion("publish_date =", value, "publish_date");
            return (Criteria) this;
        }

        public Criteria andPublish_dateNotEqualTo(Date value) {
            addCriterion("publish_date <>", value, "publish_date");
            return (Criteria) this;
        }

        public Criteria andPublish_dateGreaterThan(Date value) {
            addCriterion("publish_date >", value, "publish_date");
            return (Criteria) this;
        }

        public Criteria andPublish_dateGreaterThanOrEqualTo(Date value) {
            addCriterion("publish_date >=", value, "publish_date");
            return (Criteria) this;
        }

        public Criteria andPublish_dateLessThan(Date value) {
            addCriterion("publish_date <", value, "publish_date");
            return (Criteria) this;
        }

        public Criteria andPublish_dateLessThanOrEqualTo(Date value) {
            addCriterion("publish_date <=", value, "publish_date");
            return (Criteria) this;
        }

        public Criteria andPublish_dateIn(List<Date> values) {
            addCriterion("publish_date in", values, "publish_date");
            return (Criteria) this;
        }

        public Criteria andPublish_dateNotIn(List<Date> values) {
            addCriterion("publish_date not in", values, "publish_date");
            return (Criteria) this;
        }

        public Criteria andPublish_dateBetween(Date value1, Date value2) {
            addCriterion("publish_date between", value1, value2, "publish_date");
            return (Criteria) this;
        }

        public Criteria andPublish_dateNotBetween(Date value1, Date value2) {
            addCriterion("publish_date not between", value1, value2, "publish_date");
            return (Criteria) this;
        }

        public Criteria andDeal_dateIsNull() {
            addCriterion("deal_date is null");
            return (Criteria) this;
        }

        public Criteria andDeal_dateIsNotNull() {
            addCriterion("deal_date is not null");
            return (Criteria) this;
        }

        public Criteria andDeal_dateEqualTo(Date value) {
            addCriterion("deal_date =", value, "deal_date");
            return (Criteria) this;
        }

        public Criteria andDeal_dateNotEqualTo(Date value) {
            addCriterion("deal_date <>", value, "deal_date");
            return (Criteria) this;
        }

        public Criteria andDeal_dateGreaterThan(Date value) {
            addCriterion("deal_date >", value, "deal_date");
            return (Criteria) this;
        }

        public Criteria andDeal_dateGreaterThanOrEqualTo(Date value) {
            addCriterion("deal_date >=", value, "deal_date");
            return (Criteria) this;
        }

        public Criteria andDeal_dateLessThan(Date value) {
            addCriterion("deal_date <", value, "deal_date");
            return (Criteria) this;
        }

        public Criteria andDeal_dateLessThanOrEqualTo(Date value) {
            addCriterion("deal_date <=", value, "deal_date");
            return (Criteria) this;
        }

        public Criteria andDeal_dateIn(List<Date> values) {
            addCriterion("deal_date in", values, "deal_date");
            return (Criteria) this;
        }

        public Criteria andDeal_dateNotIn(List<Date> values) {
            addCriterion("deal_date not in", values, "deal_date");
            return (Criteria) this;
        }

        public Criteria andDeal_dateBetween(Date value1, Date value2) {
            addCriterion("deal_date between", value1, value2, "deal_date");
            return (Criteria) this;
        }

        public Criteria andDeal_dateNotBetween(Date value1, Date value2) {
            addCriterion("deal_date not between", value1, value2, "deal_date");
            return (Criteria) this;
        }

        public Criteria andTransfer_incomeIsNull() {
            addCriterion("transfer_income is null");
            return (Criteria) this;
        }

        public Criteria andTransfer_incomeIsNotNull() {
            addCriterion("transfer_income is not null");
            return (Criteria) this;
        }

        public Criteria andTransfer_incomeEqualTo(BigDecimal value) {
            addCriterion("transfer_income =", value, "transfer_income");
            return (Criteria) this;
        }

        public Criteria andTransfer_incomeNotEqualTo(BigDecimal value) {
            addCriterion("transfer_income <>", value, "transfer_income");
            return (Criteria) this;
        }

        public Criteria andTransfer_incomeGreaterThan(BigDecimal value) {
            addCriterion("transfer_income >", value, "transfer_income");
            return (Criteria) this;
        }

        public Criteria andTransfer_incomeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("transfer_income >=", value, "transfer_income");
            return (Criteria) this;
        }

        public Criteria andTransfer_incomeLessThan(BigDecimal value) {
            addCriterion("transfer_income <", value, "transfer_income");
            return (Criteria) this;
        }

        public Criteria andTransfer_incomeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("transfer_income <=", value, "transfer_income");
            return (Criteria) this;
        }

        public Criteria andTransfer_incomeIn(List<BigDecimal> values) {
            addCriterion("transfer_income in", values, "transfer_income");
            return (Criteria) this;
        }

        public Criteria andTransfer_incomeNotIn(List<BigDecimal> values) {
            addCriterion("transfer_income not in", values, "transfer_income");
            return (Criteria) this;
        }

        public Criteria andTransfer_incomeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("transfer_income between", value1, value2, "transfer_income");
            return (Criteria) this;
        }

        public Criteria andTransfer_incomeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("transfer_income not between", value1, value2, "transfer_income");
            return (Criteria) this;
        }

        public Criteria andIncome_acctIsNull() {
            addCriterion("income_acct is null");
            return (Criteria) this;
        }

        public Criteria andIncome_acctIsNotNull() {
            addCriterion("income_acct is not null");
            return (Criteria) this;
        }

        public Criteria andIncome_acctEqualTo(String value) {
            addCriterion("income_acct =", value, "income_acct");
            return (Criteria) this;
        }

        public Criteria andIncome_acctNotEqualTo(String value) {
            addCriterion("income_acct <>", value, "income_acct");
            return (Criteria) this;
        }

        public Criteria andIncome_acctGreaterThan(String value) {
            addCriterion("income_acct >", value, "income_acct");
            return (Criteria) this;
        }

        public Criteria andIncome_acctGreaterThanOrEqualTo(String value) {
            addCriterion("income_acct >=", value, "income_acct");
            return (Criteria) this;
        }

        public Criteria andIncome_acctLessThan(String value) {
            addCriterion("income_acct <", value, "income_acct");
            return (Criteria) this;
        }

        public Criteria andIncome_acctLessThanOrEqualTo(String value) {
            addCriterion("income_acct <=", value, "income_acct");
            return (Criteria) this;
        }

        public Criteria andIncome_acctLike(String value) {
            addCriterion("income_acct like", value, "income_acct");
            return (Criteria) this;
        }

        public Criteria andIncome_acctNotLike(String value) {
            addCriterion("income_acct not like", value, "income_acct");
            return (Criteria) this;
        }

        public Criteria andIncome_acctIn(List<String> values) {
            addCriterion("income_acct in", values, "income_acct");
            return (Criteria) this;
        }

        public Criteria andIncome_acctNotIn(List<String> values) {
            addCriterion("income_acct not in", values, "income_acct");
            return (Criteria) this;
        }

        public Criteria andIncome_acctBetween(String value1, String value2) {
            addCriterion("income_acct between", value1, value2, "income_acct");
            return (Criteria) this;
        }

        public Criteria andIncome_acctNotBetween(String value1, String value2) {
            addCriterion("income_acct not between", value1, value2, "income_acct");
            return (Criteria) this;
        }

        public Criteria andRelated_prod_idsIsNull() {
            addCriterion("related_prod_ids is null");
            return (Criteria) this;
        }

        public Criteria andRelated_prod_idsIsNotNull() {
            addCriterion("related_prod_ids is not null");
            return (Criteria) this;
        }

        public Criteria andRelated_prod_idsEqualTo(String value) {
            addCriterion("related_prod_ids =", value, "related_prod_ids");
            return (Criteria) this;
        }

        public Criteria andRelated_prod_idsNotEqualTo(String value) {
            addCriterion("related_prod_ids <>", value, "related_prod_ids");
            return (Criteria) this;
        }

        public Criteria andRelated_prod_idsGreaterThan(String value) {
            addCriterion("related_prod_ids >", value, "related_prod_ids");
            return (Criteria) this;
        }

        public Criteria andRelated_prod_idsGreaterThanOrEqualTo(String value) {
            addCriterion("related_prod_ids >=", value, "related_prod_ids");
            return (Criteria) this;
        }

        public Criteria andRelated_prod_idsLessThan(String value) {
            addCriterion("related_prod_ids <", value, "related_prod_ids");
            return (Criteria) this;
        }

        public Criteria andRelated_prod_idsLessThanOrEqualTo(String value) {
            addCriterion("related_prod_ids <=", value, "related_prod_ids");
            return (Criteria) this;
        }

        public Criteria andRelated_prod_idsLike(String value) {
            addCriterion("related_prod_ids like", value, "related_prod_ids");
            return (Criteria) this;
        }

        public Criteria andRelated_prod_idsNotLike(String value) {
            addCriterion("related_prod_ids not like", value, "related_prod_ids");
            return (Criteria) this;
        }

        public Criteria andRelated_prod_idsIn(List<String> values) {
            addCriterion("related_prod_ids in", values, "related_prod_ids");
            return (Criteria) this;
        }

        public Criteria andRelated_prod_idsNotIn(List<String> values) {
            addCriterion("related_prod_ids not in", values, "related_prod_ids");
            return (Criteria) this;
        }

        public Criteria andRelated_prod_idsBetween(String value1, String value2) {
            addCriterion("related_prod_ids between", value1, value2, "related_prod_ids");
            return (Criteria) this;
        }

        public Criteria andRelated_prod_idsNotBetween(String value1, String value2) {
            addCriterion("related_prod_ids not between", value1, value2, "related_prod_ids");
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
    }

    /**
     * prod_transfer_record
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * prod_transfer_record 2017-06-01
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