package com.sunyard.sunfintech.dao.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProdTransferExample {
    /**
     * prod_transfer
     */
    protected String orderByClause;

    /**
     * prod_transfer
     */
    protected boolean distinct;

    /**
     * prod_transfer
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2017-06-01
     */
    public ProdTransferExample() {
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
     * prod_transfer 2017-06-01
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

        public Criteria andTransfer_idIsNull() {
            addCriterion("transfer_id is null");
            return (Criteria) this;
        }

        public Criteria andTransfer_idIsNotNull() {
            addCriterion("transfer_id is not null");
            return (Criteria) this;
        }

        public Criteria andTransfer_idEqualTo(String value) {
            addCriterion("transfer_id =", value, "transfer_id");
            return (Criteria) this;
        }

        public Criteria andTransfer_idNotEqualTo(String value) {
            addCriterion("transfer_id <>", value, "transfer_id");
            return (Criteria) this;
        }

        public Criteria andTransfer_idGreaterThan(String value) {
            addCriterion("transfer_id >", value, "transfer_id");
            return (Criteria) this;
        }

        public Criteria andTransfer_idGreaterThanOrEqualTo(String value) {
            addCriterion("transfer_id >=", value, "transfer_id");
            return (Criteria) this;
        }

        public Criteria andTransfer_idLessThan(String value) {
            addCriterion("transfer_id <", value, "transfer_id");
            return (Criteria) this;
        }

        public Criteria andTransfer_idLessThanOrEqualTo(String value) {
            addCriterion("transfer_id <=", value, "transfer_id");
            return (Criteria) this;
        }

        public Criteria andTransfer_idLike(String value) {
            addCriterion("transfer_id like", value, "transfer_id");
            return (Criteria) this;
        }

        public Criteria andTransfer_idNotLike(String value) {
            addCriterion("transfer_id not like", value, "transfer_id");
            return (Criteria) this;
        }

        public Criteria andTransfer_idIn(List<String> values) {
            addCriterion("transfer_id in", values, "transfer_id");
            return (Criteria) this;
        }

        public Criteria andTransfer_idNotIn(List<String> values) {
            addCriterion("transfer_id not in", values, "transfer_id");
            return (Criteria) this;
        }

        public Criteria andTransfer_idBetween(String value1, String value2) {
            addCriterion("transfer_id between", value1, value2, "transfer_id");
            return (Criteria) this;
        }

        public Criteria andTransfer_idNotBetween(String value1, String value2) {
            addCriterion("transfer_id not between", value1, value2, "transfer_id");
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

        public Criteria andEacc_noIsNull() {
            addCriterion("eacc_no is null");
            return (Criteria) this;
        }

        public Criteria andEacc_noIsNotNull() {
            addCriterion("eacc_no is not null");
            return (Criteria) this;
        }

        public Criteria andEacc_noEqualTo(String value) {
            addCriterion("eacc_no =", value, "eacc_no");
            return (Criteria) this;
        }

        public Criteria andEacc_noNotEqualTo(String value) {
            addCriterion("eacc_no <>", value, "eacc_no");
            return (Criteria) this;
        }

        public Criteria andEacc_noGreaterThan(String value) {
            addCriterion("eacc_no >", value, "eacc_no");
            return (Criteria) this;
        }

        public Criteria andEacc_noGreaterThanOrEqualTo(String value) {
            addCriterion("eacc_no >=", value, "eacc_no");
            return (Criteria) this;
        }

        public Criteria andEacc_noLessThan(String value) {
            addCriterion("eacc_no <", value, "eacc_no");
            return (Criteria) this;
        }

        public Criteria andEacc_noLessThanOrEqualTo(String value) {
            addCriterion("eacc_no <=", value, "eacc_no");
            return (Criteria) this;
        }

        public Criteria andEacc_noLike(String value) {
            addCriterion("eacc_no like", value, "eacc_no");
            return (Criteria) this;
        }

        public Criteria andEacc_noNotLike(String value) {
            addCriterion("eacc_no not like", value, "eacc_no");
            return (Criteria) this;
        }

        public Criteria andEacc_noIn(List<String> values) {
            addCriterion("eacc_no in", values, "eacc_no");
            return (Criteria) this;
        }

        public Criteria andEacc_noNotIn(List<String> values) {
            addCriterion("eacc_no not in", values, "eacc_no");
            return (Criteria) this;
        }

        public Criteria andEacc_noBetween(String value1, String value2) {
            addCriterion("eacc_no between", value1, value2, "eacc_no");
            return (Criteria) this;
        }

        public Criteria andEacc_noNotBetween(String value1, String value2) {
            addCriterion("eacc_no not between", value1, value2, "eacc_no");
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

        public Criteria andTransfer_volIsNull() {
            addCriterion("transfer_vol is null");
            return (Criteria) this;
        }

        public Criteria andTransfer_volIsNotNull() {
            addCriterion("transfer_vol is not null");
            return (Criteria) this;
        }

        public Criteria andTransfer_volEqualTo(BigDecimal value) {
            addCriterion("transfer_vol =", value, "transfer_vol");
            return (Criteria) this;
        }

        public Criteria andTransfer_volNotEqualTo(BigDecimal value) {
            addCriterion("transfer_vol <>", value, "transfer_vol");
            return (Criteria) this;
        }

        public Criteria andTransfer_volGreaterThan(BigDecimal value) {
            addCriterion("transfer_vol >", value, "transfer_vol");
            return (Criteria) this;
        }

        public Criteria andTransfer_volGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("transfer_vol >=", value, "transfer_vol");
            return (Criteria) this;
        }

        public Criteria andTransfer_volLessThan(BigDecimal value) {
            addCriterion("transfer_vol <", value, "transfer_vol");
            return (Criteria) this;
        }

        public Criteria andTransfer_volLessThanOrEqualTo(BigDecimal value) {
            addCriterion("transfer_vol <=", value, "transfer_vol");
            return (Criteria) this;
        }

        public Criteria andTransfer_volIn(List<BigDecimal> values) {
            addCriterion("transfer_vol in", values, "transfer_vol");
            return (Criteria) this;
        }

        public Criteria andTransfer_volNotIn(List<BigDecimal> values) {
            addCriterion("transfer_vol not in", values, "transfer_vol");
            return (Criteria) this;
        }

        public Criteria andTransfer_volBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("transfer_vol between", value1, value2, "transfer_vol");
            return (Criteria) this;
        }

        public Criteria andTransfer_volNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("transfer_vol not between", value1, value2, "transfer_vol");
            return (Criteria) this;
        }

        public Criteria andTransfer_amtIsNull() {
            addCriterion("transfer_amt is null");
            return (Criteria) this;
        }

        public Criteria andTransfer_amtIsNotNull() {
            addCriterion("transfer_amt is not null");
            return (Criteria) this;
        }

        public Criteria andTransfer_amtEqualTo(BigDecimal value) {
            addCriterion("transfer_amt =", value, "transfer_amt");
            return (Criteria) this;
        }

        public Criteria andTransfer_amtNotEqualTo(BigDecimal value) {
            addCriterion("transfer_amt <>", value, "transfer_amt");
            return (Criteria) this;
        }

        public Criteria andTransfer_amtGreaterThan(BigDecimal value) {
            addCriterion("transfer_amt >", value, "transfer_amt");
            return (Criteria) this;
        }

        public Criteria andTransfer_amtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("transfer_amt >=", value, "transfer_amt");
            return (Criteria) this;
        }

        public Criteria andTransfer_amtLessThan(BigDecimal value) {
            addCriterion("transfer_amt <", value, "transfer_amt");
            return (Criteria) this;
        }

        public Criteria andTransfer_amtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("transfer_amt <=", value, "transfer_amt");
            return (Criteria) this;
        }

        public Criteria andTransfer_amtIn(List<BigDecimal> values) {
            addCriterion("transfer_amt in", values, "transfer_amt");
            return (Criteria) this;
        }

        public Criteria andTransfer_amtNotIn(List<BigDecimal> values) {
            addCriterion("transfer_amt not in", values, "transfer_amt");
            return (Criteria) this;
        }

        public Criteria andTransfer_amtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("transfer_amt between", value1, value2, "transfer_amt");
            return (Criteria) this;
        }

        public Criteria andTransfer_amtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("transfer_amt not between", value1, value2, "transfer_amt");
            return (Criteria) this;
        }

        public Criteria andTransfer_feeIsNull() {
            addCriterion("transfer_fee is null");
            return (Criteria) this;
        }

        public Criteria andTransfer_feeIsNotNull() {
            addCriterion("transfer_fee is not null");
            return (Criteria) this;
        }

        public Criteria andTransfer_feeEqualTo(BigDecimal value) {
            addCriterion("transfer_fee =", value, "transfer_fee");
            return (Criteria) this;
        }

        public Criteria andTransfer_feeNotEqualTo(BigDecimal value) {
            addCriterion("transfer_fee <>", value, "transfer_fee");
            return (Criteria) this;
        }

        public Criteria andTransfer_feeGreaterThan(BigDecimal value) {
            addCriterion("transfer_fee >", value, "transfer_fee");
            return (Criteria) this;
        }

        public Criteria andTransfer_feeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("transfer_fee >=", value, "transfer_fee");
            return (Criteria) this;
        }

        public Criteria andTransfer_feeLessThan(BigDecimal value) {
            addCriterion("transfer_fee <", value, "transfer_fee");
            return (Criteria) this;
        }

        public Criteria andTransfer_feeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("transfer_fee <=", value, "transfer_fee");
            return (Criteria) this;
        }

        public Criteria andTransfer_feeIn(List<BigDecimal> values) {
            addCriterion("transfer_fee in", values, "transfer_fee");
            return (Criteria) this;
        }

        public Criteria andTransfer_feeNotIn(List<BigDecimal> values) {
            addCriterion("transfer_fee not in", values, "transfer_fee");
            return (Criteria) this;
        }

        public Criteria andTransfer_feeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("transfer_fee between", value1, value2, "transfer_fee");
            return (Criteria) this;
        }

        public Criteria andTransfer_feeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("transfer_fee not between", value1, value2, "transfer_fee");
            return (Criteria) this;
        }

        public Criteria andDeal_eacc_noIsNull() {
            addCriterion("deal_eacc_no is null");
            return (Criteria) this;
        }

        public Criteria andDeal_eacc_noIsNotNull() {
            addCriterion("deal_eacc_no is not null");
            return (Criteria) this;
        }

        public Criteria andDeal_eacc_noEqualTo(String value) {
            addCriterion("deal_eacc_no =", value, "deal_eacc_no");
            return (Criteria) this;
        }

        public Criteria andDeal_eacc_noNotEqualTo(String value) {
            addCriterion("deal_eacc_no <>", value, "deal_eacc_no");
            return (Criteria) this;
        }

        public Criteria andDeal_eacc_noGreaterThan(String value) {
            addCriterion("deal_eacc_no >", value, "deal_eacc_no");
            return (Criteria) this;
        }

        public Criteria andDeal_eacc_noGreaterThanOrEqualTo(String value) {
            addCriterion("deal_eacc_no >=", value, "deal_eacc_no");
            return (Criteria) this;
        }

        public Criteria andDeal_eacc_noLessThan(String value) {
            addCriterion("deal_eacc_no <", value, "deal_eacc_no");
            return (Criteria) this;
        }

        public Criteria andDeal_eacc_noLessThanOrEqualTo(String value) {
            addCriterion("deal_eacc_no <=", value, "deal_eacc_no");
            return (Criteria) this;
        }

        public Criteria andDeal_eacc_noLike(String value) {
            addCriterion("deal_eacc_no like", value, "deal_eacc_no");
            return (Criteria) this;
        }

        public Criteria andDeal_eacc_noNotLike(String value) {
            addCriterion("deal_eacc_no not like", value, "deal_eacc_no");
            return (Criteria) this;
        }

        public Criteria andDeal_eacc_noIn(List<String> values) {
            addCriterion("deal_eacc_no in", values, "deal_eacc_no");
            return (Criteria) this;
        }

        public Criteria andDeal_eacc_noNotIn(List<String> values) {
            addCriterion("deal_eacc_no not in", values, "deal_eacc_no");
            return (Criteria) this;
        }

        public Criteria andDeal_eacc_noBetween(String value1, String value2) {
            addCriterion("deal_eacc_no between", value1, value2, "deal_eacc_no");
            return (Criteria) this;
        }

        public Criteria andDeal_eacc_noNotBetween(String value1, String value2) {
            addCriterion("deal_eacc_no not between", value1, value2, "deal_eacc_no");
            return (Criteria) this;
        }

        public Criteria andDeal_timeIsNull() {
            addCriterion("deal_time is null");
            return (Criteria) this;
        }

        public Criteria andDeal_timeIsNotNull() {
            addCriterion("deal_time is not null");
            return (Criteria) this;
        }

        public Criteria andDeal_timeEqualTo(Date value) {
            addCriterion("deal_time =", value, "deal_time");
            return (Criteria) this;
        }

        public Criteria andDeal_timeNotEqualTo(Date value) {
            addCriterion("deal_time <>", value, "deal_time");
            return (Criteria) this;
        }

        public Criteria andDeal_timeGreaterThan(Date value) {
            addCriterion("deal_time >", value, "deal_time");
            return (Criteria) this;
        }

        public Criteria andDeal_timeGreaterThanOrEqualTo(Date value) {
            addCriterion("deal_time >=", value, "deal_time");
            return (Criteria) this;
        }

        public Criteria andDeal_timeLessThan(Date value) {
            addCriterion("deal_time <", value, "deal_time");
            return (Criteria) this;
        }

        public Criteria andDeal_timeLessThanOrEqualTo(Date value) {
            addCriterion("deal_time <=", value, "deal_time");
            return (Criteria) this;
        }

        public Criteria andDeal_timeIn(List<Date> values) {
            addCriterion("deal_time in", values, "deal_time");
            return (Criteria) this;
        }

        public Criteria andDeal_timeNotIn(List<Date> values) {
            addCriterion("deal_time not in", values, "deal_time");
            return (Criteria) this;
        }

        public Criteria andDeal_timeBetween(Date value1, Date value2) {
            addCriterion("deal_time between", value1, value2, "deal_time");
            return (Criteria) this;
        }

        public Criteria andDeal_timeNotBetween(Date value1, Date value2) {
            addCriterion("deal_time not between", value1, value2, "deal_time");
            return (Criteria) this;
        }
    }

    /**
     * prod_transfer
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * prod_transfer 2017-06-01
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