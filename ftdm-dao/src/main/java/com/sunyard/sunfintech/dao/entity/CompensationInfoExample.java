package com.sunyard.sunfintech.dao.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CompensationInfoExample {
    /**
     * compensation_info
     */
    protected String orderByClause;

    /**
     * compensation_info
     */
    protected boolean distinct;

    /**
     * compensation_info
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2017-06-01
     */
    public CompensationInfoExample() {
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
     * compensation_info 2017-06-01
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

        public Criteria andCompensation_platcustIsNull() {
            addCriterion("compensation_platcust is null");
            return (Criteria) this;
        }

        public Criteria andCompensation_platcustIsNotNull() {
            addCriterion("compensation_platcust is not null");
            return (Criteria) this;
        }

        public Criteria andCompensation_platcustEqualTo(String value) {
            addCriterion("compensation_platcust =", value, "compensation_platcust");
            return (Criteria) this;
        }

        public Criteria andCompensation_platcustNotEqualTo(String value) {
            addCriterion("compensation_platcust <>", value, "compensation_platcust");
            return (Criteria) this;
        }

        public Criteria andCompensation_platcustGreaterThan(String value) {
            addCriterion("compensation_platcust >", value, "compensation_platcust");
            return (Criteria) this;
        }

        public Criteria andCompensation_platcustGreaterThanOrEqualTo(String value) {
            addCriterion("compensation_platcust >=", value, "compensation_platcust");
            return (Criteria) this;
        }

        public Criteria andCompensation_platcustLessThan(String value) {
            addCriterion("compensation_platcust <", value, "compensation_platcust");
            return (Criteria) this;
        }

        public Criteria andCompensation_platcustLessThanOrEqualTo(String value) {
            addCriterion("compensation_platcust <=", value, "compensation_platcust");
            return (Criteria) this;
        }

        public Criteria andCompensation_platcustLike(String value) {
            addCriterion("compensation_platcust like", value, "compensation_platcust");
            return (Criteria) this;
        }

        public Criteria andCompensation_platcustNotLike(String value) {
            addCriterion("compensation_platcust not like", value, "compensation_platcust");
            return (Criteria) this;
        }

        public Criteria andCompensation_platcustIn(List<String> values) {
            addCriterion("compensation_platcust in", values, "compensation_platcust");
            return (Criteria) this;
        }

        public Criteria andCompensation_platcustNotIn(List<String> values) {
            addCriterion("compensation_platcust not in", values, "compensation_platcust");
            return (Criteria) this;
        }

        public Criteria andCompensation_platcustBetween(String value1, String value2) {
            addCriterion("compensation_platcust between", value1, value2, "compensation_platcust");
            return (Criteria) this;
        }

        public Criteria andCompensation_platcustNotBetween(String value1, String value2) {
            addCriterion("compensation_platcust not between", value1, value2, "compensation_platcust");
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

        public Criteria andRepay_numEqualTo(Integer value) {
            addCriterion("repay_num =", value, "repay_num");
            return (Criteria) this;
        }

        public Criteria andRepay_numNotEqualTo(Integer value) {
            addCriterion("repay_num <>", value, "repay_num");
            return (Criteria) this;
        }

        public Criteria andRepay_numGreaterThan(Integer value) {
            addCriterion("repay_num >", value, "repay_num");
            return (Criteria) this;
        }

        public Criteria andRepay_numGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_num >=", value, "repay_num");
            return (Criteria) this;
        }

        public Criteria andRepay_numLessThan(Integer value) {
            addCriterion("repay_num <", value, "repay_num");
            return (Criteria) this;
        }

        public Criteria andRepay_numLessThanOrEqualTo(Integer value) {
            addCriterion("repay_num <=", value, "repay_num");
            return (Criteria) this;
        }

        public Criteria andRepay_numIn(List<Integer> values) {
            addCriterion("repay_num in", values, "repay_num");
            return (Criteria) this;
        }

        public Criteria andRepay_numNotIn(List<Integer> values) {
            addCriterion("repay_num not in", values, "repay_num");
            return (Criteria) this;
        }

        public Criteria andRepay_numBetween(Integer value1, Integer value2) {
            addCriterion("repay_num between", value1, value2, "repay_num");
            return (Criteria) this;
        }

        public Criteria andRepay_numNotBetween(Integer value1, Integer value2) {
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

        public Criteria andRepay_dateEqualTo(String value) {
            addCriterion("repay_date =", value, "repay_date");
            return (Criteria) this;
        }

        public Criteria andRepay_dateNotEqualTo(String value) {
            addCriterion("repay_date <>", value, "repay_date");
            return (Criteria) this;
        }

        public Criteria andRepay_dateGreaterThan(String value) {
            addCriterion("repay_date >", value, "repay_date");
            return (Criteria) this;
        }

        public Criteria andRepay_dateGreaterThanOrEqualTo(String value) {
            addCriterion("repay_date >=", value, "repay_date");
            return (Criteria) this;
        }

        public Criteria andRepay_dateLessThan(String value) {
            addCriterion("repay_date <", value, "repay_date");
            return (Criteria) this;
        }

        public Criteria andRepay_dateLessThanOrEqualTo(String value) {
            addCriterion("repay_date <=", value, "repay_date");
            return (Criteria) this;
        }

        public Criteria andRepay_dateLike(String value) {
            addCriterion("repay_date like", value, "repay_date");
            return (Criteria) this;
        }

        public Criteria andRepay_dateNotLike(String value) {
            addCriterion("repay_date not like", value, "repay_date");
            return (Criteria) this;
        }

        public Criteria andRepay_dateIn(List<String> values) {
            addCriterion("repay_date in", values, "repay_date");
            return (Criteria) this;
        }

        public Criteria andRepay_dateNotIn(List<String> values) {
            addCriterion("repay_date not in", values, "repay_date");
            return (Criteria) this;
        }

        public Criteria andRepay_dateBetween(String value1, String value2) {
            addCriterion("repay_date between", value1, value2, "repay_date");
            return (Criteria) this;
        }

        public Criteria andRepay_dateNotBetween(String value1, String value2) {
            addCriterion("repay_date not between", value1, value2, "repay_date");
            return (Criteria) this;
        }

        public Criteria andRepay_amtIsNull() {
            addCriterion("repay_amt is null");
            return (Criteria) this;
        }

        public Criteria andRepay_amtIsNotNull() {
            addCriterion("repay_amt is not null");
            return (Criteria) this;
        }

        public Criteria andRepay_amtEqualTo(BigDecimal value) {
            addCriterion("repay_amt =", value, "repay_amt");
            return (Criteria) this;
        }

        public Criteria andRepay_amtNotEqualTo(BigDecimal value) {
            addCriterion("repay_amt <>", value, "repay_amt");
            return (Criteria) this;
        }

        public Criteria andRepay_amtGreaterThan(BigDecimal value) {
            addCriterion("repay_amt >", value, "repay_amt");
            return (Criteria) this;
        }

        public Criteria andRepay_amtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_amt >=", value, "repay_amt");
            return (Criteria) this;
        }

        public Criteria andRepay_amtLessThan(BigDecimal value) {
            addCriterion("repay_amt <", value, "repay_amt");
            return (Criteria) this;
        }

        public Criteria andRepay_amtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_amt <=", value, "repay_amt");
            return (Criteria) this;
        }

        public Criteria andRepay_amtIn(List<BigDecimal> values) {
            addCriterion("repay_amt in", values, "repay_amt");
            return (Criteria) this;
        }

        public Criteria andRepay_amtNotIn(List<BigDecimal> values) {
            addCriterion("repay_amt not in", values, "repay_amt");
            return (Criteria) this;
        }

        public Criteria andRepay_amtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_amt between", value1, value2, "repay_amt");
            return (Criteria) this;
        }

        public Criteria andRepay_amtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_amt not between", value1, value2, "repay_amt");
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

        public Criteria andReal_repay_dateEqualTo(String value) {
            addCriterion("real_repay_date =", value, "real_repay_date");
            return (Criteria) this;
        }

        public Criteria andReal_repay_dateNotEqualTo(String value) {
            addCriterion("real_repay_date <>", value, "real_repay_date");
            return (Criteria) this;
        }

        public Criteria andReal_repay_dateGreaterThan(String value) {
            addCriterion("real_repay_date >", value, "real_repay_date");
            return (Criteria) this;
        }

        public Criteria andReal_repay_dateGreaterThanOrEqualTo(String value) {
            addCriterion("real_repay_date >=", value, "real_repay_date");
            return (Criteria) this;
        }

        public Criteria andReal_repay_dateLessThan(String value) {
            addCriterion("real_repay_date <", value, "real_repay_date");
            return (Criteria) this;
        }

        public Criteria andReal_repay_dateLessThanOrEqualTo(String value) {
            addCriterion("real_repay_date <=", value, "real_repay_date");
            return (Criteria) this;
        }

        public Criteria andReal_repay_dateLike(String value) {
            addCriterion("real_repay_date like", value, "real_repay_date");
            return (Criteria) this;
        }

        public Criteria andReal_repay_dateNotLike(String value) {
            addCriterion("real_repay_date not like", value, "real_repay_date");
            return (Criteria) this;
        }

        public Criteria andReal_repay_dateIn(List<String> values) {
            addCriterion("real_repay_date in", values, "real_repay_date");
            return (Criteria) this;
        }

        public Criteria andReal_repay_dateNotIn(List<String> values) {
            addCriterion("real_repay_date not in", values, "real_repay_date");
            return (Criteria) this;
        }

        public Criteria andReal_repay_dateBetween(String value1, String value2) {
            addCriterion("real_repay_date between", value1, value2, "real_repay_date");
            return (Criteria) this;
        }

        public Criteria andReal_repay_dateNotBetween(String value1, String value2) {
            addCriterion("real_repay_date not between", value1, value2, "real_repay_date");
            return (Criteria) this;
        }

        public Criteria andReal_repay_amtIsNull() {
            addCriterion("real_repay_amt is null");
            return (Criteria) this;
        }

        public Criteria andReal_repay_amtIsNotNull() {
            addCriterion("real_repay_amt is not null");
            return (Criteria) this;
        }

        public Criteria andReal_repay_amtEqualTo(BigDecimal value) {
            addCriterion("real_repay_amt =", value, "real_repay_amt");
            return (Criteria) this;
        }

        public Criteria andReal_repay_amtNotEqualTo(BigDecimal value) {
            addCriterion("real_repay_amt <>", value, "real_repay_amt");
            return (Criteria) this;
        }

        public Criteria andReal_repay_amtGreaterThan(BigDecimal value) {
            addCriterion("real_repay_amt >", value, "real_repay_amt");
            return (Criteria) this;
        }

        public Criteria andReal_repay_amtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("real_repay_amt >=", value, "real_repay_amt");
            return (Criteria) this;
        }

        public Criteria andReal_repay_amtLessThan(BigDecimal value) {
            addCriterion("real_repay_amt <", value, "real_repay_amt");
            return (Criteria) this;
        }

        public Criteria andReal_repay_amtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("real_repay_amt <=", value, "real_repay_amt");
            return (Criteria) this;
        }

        public Criteria andReal_repay_amtIn(List<BigDecimal> values) {
            addCriterion("real_repay_amt in", values, "real_repay_amt");
            return (Criteria) this;
        }

        public Criteria andReal_repay_amtNotIn(List<BigDecimal> values) {
            addCriterion("real_repay_amt not in", values, "real_repay_amt");
            return (Criteria) this;
        }

        public Criteria andReal_repay_amtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("real_repay_amt between", value1, value2, "real_repay_amt");
            return (Criteria) this;
        }

        public Criteria andReal_repay_amtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("real_repay_amt not between", value1, value2, "real_repay_amt");
            return (Criteria) this;
        }

        public Criteria andFee_amtIsNull() {
            addCriterion("fee_amt is null");
            return (Criteria) this;
        }

        public Criteria andFee_amtIsNotNull() {
            addCriterion("fee_amt is not null");
            return (Criteria) this;
        }

        public Criteria andFee_amtEqualTo(BigDecimal value) {
            addCriterion("fee_amt =", value, "fee_amt");
            return (Criteria) this;
        }

        public Criteria andFee_amtNotEqualTo(BigDecimal value) {
            addCriterion("fee_amt <>", value, "fee_amt");
            return (Criteria) this;
        }

        public Criteria andFee_amtGreaterThan(BigDecimal value) {
            addCriterion("fee_amt >", value, "fee_amt");
            return (Criteria) this;
        }

        public Criteria andFee_amtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("fee_amt >=", value, "fee_amt");
            return (Criteria) this;
        }

        public Criteria andFee_amtLessThan(BigDecimal value) {
            addCriterion("fee_amt <", value, "fee_amt");
            return (Criteria) this;
        }

        public Criteria andFee_amtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("fee_amt <=", value, "fee_amt");
            return (Criteria) this;
        }

        public Criteria andFee_amtIn(List<BigDecimal> values) {
            addCriterion("fee_amt in", values, "fee_amt");
            return (Criteria) this;
        }

        public Criteria andFee_amtNotIn(List<BigDecimal> values) {
            addCriterion("fee_amt not in", values, "fee_amt");
            return (Criteria) this;
        }

        public Criteria andFee_amtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("fee_amt between", value1, value2, "fee_amt");
            return (Criteria) this;
        }

        public Criteria andFee_amtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("fee_amt not between", value1, value2, "fee_amt");
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
     * compensation_info
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * compensation_info 2017-06-01
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