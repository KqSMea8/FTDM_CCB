package com.sunyard.sunfintech.dao.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EaccFinancinfoExample {
    /**
     * eacc_financinfo
     */
    protected String orderByClause;

    /**
     * eacc_financinfo
     */
    protected boolean distinct;

    /**
     * eacc_financinfo
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2017-09-15
     */
    public EaccFinancinfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *
     * @mbggenerated 2017-09-15
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *
     * @mbggenerated 2017-09-15
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *
     * @mbggenerated 2017-09-15
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *
     * @mbggenerated 2017-09-15
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *
     * @mbggenerated 2017-09-15
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *
     * @mbggenerated 2017-09-15
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *
     * @mbggenerated 2017-09-15
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *
     * @mbggenerated 2017-09-15
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
     * @mbggenerated 2017-09-15
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *
     * @mbggenerated 2017-09-15
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * eacc_financinfo 2017-09-15
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

        public Criteria andReg_dateIsNull() {
            addCriterion("reg_date is null");
            return (Criteria) this;
        }

        public Criteria andReg_dateIsNotNull() {
            addCriterion("reg_date is not null");
            return (Criteria) this;
        }

        public Criteria andReg_dateEqualTo(String value) {
            addCriterion("reg_date =", value, "reg_date");
            return (Criteria) this;
        }

        public Criteria andReg_dateNotEqualTo(String value) {
            addCriterion("reg_date <>", value, "reg_date");
            return (Criteria) this;
        }

        public Criteria andReg_dateGreaterThan(String value) {
            addCriterion("reg_date >", value, "reg_date");
            return (Criteria) this;
        }

        public Criteria andReg_dateGreaterThanOrEqualTo(String value) {
            addCriterion("reg_date >=", value, "reg_date");
            return (Criteria) this;
        }

        public Criteria andReg_dateLessThan(String value) {
            addCriterion("reg_date <", value, "reg_date");
            return (Criteria) this;
        }

        public Criteria andReg_dateLessThanOrEqualTo(String value) {
            addCriterion("reg_date <=", value, "reg_date");
            return (Criteria) this;
        }

        public Criteria andReg_dateLike(String value) {
            addCriterion("reg_date like", value, "reg_date");
            return (Criteria) this;
        }

        public Criteria andReg_dateNotLike(String value) {
            addCriterion("reg_date not like", value, "reg_date");
            return (Criteria) this;
        }

        public Criteria andReg_dateIn(List<String> values) {
            addCriterion("reg_date in", values, "reg_date");
            return (Criteria) this;
        }

        public Criteria andReg_dateNotIn(List<String> values) {
            addCriterion("reg_date not in", values, "reg_date");
            return (Criteria) this;
        }

        public Criteria andReg_dateBetween(String value1, String value2) {
            addCriterion("reg_date between", value1, value2, "reg_date");
            return (Criteria) this;
        }

        public Criteria andReg_dateNotBetween(String value1, String value2) {
            addCriterion("reg_date not between", value1, value2, "reg_date");
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

        public Criteria andReg_timeEqualTo(String value) {
            addCriterion("reg_time =", value, "reg_time");
            return (Criteria) this;
        }

        public Criteria andReg_timeNotEqualTo(String value) {
            addCriterion("reg_time <>", value, "reg_time");
            return (Criteria) this;
        }

        public Criteria andReg_timeGreaterThan(String value) {
            addCriterion("reg_time >", value, "reg_time");
            return (Criteria) this;
        }

        public Criteria andReg_timeGreaterThanOrEqualTo(String value) {
            addCriterion("reg_time >=", value, "reg_time");
            return (Criteria) this;
        }

        public Criteria andReg_timeLessThan(String value) {
            addCriterion("reg_time <", value, "reg_time");
            return (Criteria) this;
        }

        public Criteria andReg_timeLessThanOrEqualTo(String value) {
            addCriterion("reg_time <=", value, "reg_time");
            return (Criteria) this;
        }

        public Criteria andReg_timeLike(String value) {
            addCriterion("reg_time like", value, "reg_time");
            return (Criteria) this;
        }

        public Criteria andReg_timeNotLike(String value) {
            addCriterion("reg_time not like", value, "reg_time");
            return (Criteria) this;
        }

        public Criteria andReg_timeIn(List<String> values) {
            addCriterion("reg_time in", values, "reg_time");
            return (Criteria) this;
        }

        public Criteria andReg_timeNotIn(List<String> values) {
            addCriterion("reg_time not in", values, "reg_time");
            return (Criteria) this;
        }

        public Criteria andReg_timeBetween(String value1, String value2) {
            addCriterion("reg_time between", value1, value2, "reg_time");
            return (Criteria) this;
        }

        public Criteria andReg_timeNotBetween(String value1, String value2) {
            addCriterion("reg_time not between", value1, value2, "reg_time");
            return (Criteria) this;
        }

        public Criteria andFinanc_amtIsNull() {
            addCriterion("financ_amt is null");
            return (Criteria) this;
        }

        public Criteria andFinanc_amtIsNotNull() {
            addCriterion("financ_amt is not null");
            return (Criteria) this;
        }

        public Criteria andFinanc_amtEqualTo(BigDecimal value) {
            addCriterion("financ_amt =", value, "financ_amt");
            return (Criteria) this;
        }

        public Criteria andFinanc_amtNotEqualTo(BigDecimal value) {
            addCriterion("financ_amt <>", value, "financ_amt");
            return (Criteria) this;
        }

        public Criteria andFinanc_amtGreaterThan(BigDecimal value) {
            addCriterion("financ_amt >", value, "financ_amt");
            return (Criteria) this;
        }

        public Criteria andFinanc_amtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("financ_amt >=", value, "financ_amt");
            return (Criteria) this;
        }

        public Criteria andFinanc_amtLessThan(BigDecimal value) {
            addCriterion("financ_amt <", value, "financ_amt");
            return (Criteria) this;
        }

        public Criteria andFinanc_amtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("financ_amt <=", value, "financ_amt");
            return (Criteria) this;
        }

        public Criteria andFinanc_amtIn(List<BigDecimal> values) {
            addCriterion("financ_amt in", values, "financ_amt");
            return (Criteria) this;
        }

        public Criteria andFinanc_amtNotIn(List<BigDecimal> values) {
            addCriterion("financ_amt not in", values, "financ_amt");
            return (Criteria) this;
        }

        public Criteria andFinanc_amtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("financ_amt between", value1, value2, "financ_amt");
            return (Criteria) this;
        }

        public Criteria andFinanc_amtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("financ_amt not between", value1, value2, "financ_amt");
            return (Criteria) this;
        }

        public Criteria andCycle_unitIsNull() {
            addCriterion("cycle_unit is null");
            return (Criteria) this;
        }

        public Criteria andCycle_unitIsNotNull() {
            addCriterion("cycle_unit is not null");
            return (Criteria) this;
        }

        public Criteria andCycle_unitEqualTo(String value) {
            addCriterion("cycle_unit =", value, "cycle_unit");
            return (Criteria) this;
        }

        public Criteria andCycle_unitNotEqualTo(String value) {
            addCriterion("cycle_unit <>", value, "cycle_unit");
            return (Criteria) this;
        }

        public Criteria andCycle_unitGreaterThan(String value) {
            addCriterion("cycle_unit >", value, "cycle_unit");
            return (Criteria) this;
        }

        public Criteria andCycle_unitGreaterThanOrEqualTo(String value) {
            addCriterion("cycle_unit >=", value, "cycle_unit");
            return (Criteria) this;
        }

        public Criteria andCycle_unitLessThan(String value) {
            addCriterion("cycle_unit <", value, "cycle_unit");
            return (Criteria) this;
        }

        public Criteria andCycle_unitLessThanOrEqualTo(String value) {
            addCriterion("cycle_unit <=", value, "cycle_unit");
            return (Criteria) this;
        }

        public Criteria andCycle_unitLike(String value) {
            addCriterion("cycle_unit like", value, "cycle_unit");
            return (Criteria) this;
        }

        public Criteria andCycle_unitNotLike(String value) {
            addCriterion("cycle_unit not like", value, "cycle_unit");
            return (Criteria) this;
        }

        public Criteria andCycle_unitIn(List<String> values) {
            addCriterion("cycle_unit in", values, "cycle_unit");
            return (Criteria) this;
        }

        public Criteria andCycle_unitNotIn(List<String> values) {
            addCriterion("cycle_unit not in", values, "cycle_unit");
            return (Criteria) this;
        }

        public Criteria andCycle_unitBetween(String value1, String value2) {
            addCriterion("cycle_unit between", value1, value2, "cycle_unit");
            return (Criteria) this;
        }

        public Criteria andCycle_unitNotBetween(String value1, String value2) {
            addCriterion("cycle_unit not between", value1, value2, "cycle_unit");
            return (Criteria) this;
        }

        public Criteria andCycleIsNull() {
            addCriterion("cycle is null");
            return (Criteria) this;
        }

        public Criteria andCycleIsNotNull() {
            addCriterion("cycle is not null");
            return (Criteria) this;
        }

        public Criteria andCycleEqualTo(Integer value) {
            addCriterion("cycle =", value, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleNotEqualTo(Integer value) {
            addCriterion("cycle <>", value, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleGreaterThan(Integer value) {
            addCriterion("cycle >", value, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleGreaterThanOrEqualTo(Integer value) {
            addCriterion("cycle >=", value, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleLessThan(Integer value) {
            addCriterion("cycle <", value, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleLessThanOrEqualTo(Integer value) {
            addCriterion("cycle <=", value, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleIn(List<Integer> values) {
            addCriterion("cycle in", values, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleNotIn(List<Integer> values) {
            addCriterion("cycle not in", values, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleBetween(Integer value1, Integer value2) {
            addCriterion("cycle between", value1, value2, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleNotBetween(Integer value1, Integer value2) {
            addCriterion("cycle not between", value1, value2, "cycle");
            return (Criteria) this;
        }

        public Criteria andFinanc_intIsNull() {
            addCriterion("financ_int is null");
            return (Criteria) this;
        }

        public Criteria andFinanc_intIsNotNull() {
            addCriterion("financ_int is not null");
            return (Criteria) this;
        }

        public Criteria andFinanc_intEqualTo(BigDecimal value) {
            addCriterion("financ_int =", value, "financ_int");
            return (Criteria) this;
        }

        public Criteria andFinanc_intNotEqualTo(BigDecimal value) {
            addCriterion("financ_int <>", value, "financ_int");
            return (Criteria) this;
        }

        public Criteria andFinanc_intGreaterThan(BigDecimal value) {
            addCriterion("financ_int >", value, "financ_int");
            return (Criteria) this;
        }

        public Criteria andFinanc_intGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("financ_int >=", value, "financ_int");
            return (Criteria) this;
        }

        public Criteria andFinanc_intLessThan(BigDecimal value) {
            addCriterion("financ_int <", value, "financ_int");
            return (Criteria) this;
        }

        public Criteria andFinanc_intLessThanOrEqualTo(BigDecimal value) {
            addCriterion("financ_int <=", value, "financ_int");
            return (Criteria) this;
        }

        public Criteria andFinanc_intIn(List<BigDecimal> values) {
            addCriterion("financ_int in", values, "financ_int");
            return (Criteria) this;
        }

        public Criteria andFinanc_intNotIn(List<BigDecimal> values) {
            addCriterion("financ_int not in", values, "financ_int");
            return (Criteria) this;
        }

        public Criteria andFinanc_intBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("financ_int between", value1, value2, "financ_int");
            return (Criteria) this;
        }

        public Criteria andFinanc_intNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("financ_int not between", value1, value2, "financ_int");
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

        public Criteria andUse_dateIsNull() {
            addCriterion("use_date is null");
            return (Criteria) this;
        }

        public Criteria andUse_dateIsNotNull() {
            addCriterion("use_date is not null");
            return (Criteria) this;
        }

        public Criteria andUse_dateEqualTo(String value) {
            addCriterion("use_date =", value, "use_date");
            return (Criteria) this;
        }

        public Criteria andUse_dateNotEqualTo(String value) {
            addCriterion("use_date <>", value, "use_date");
            return (Criteria) this;
        }

        public Criteria andUse_dateGreaterThan(String value) {
            addCriterion("use_date >", value, "use_date");
            return (Criteria) this;
        }

        public Criteria andUse_dateGreaterThanOrEqualTo(String value) {
            addCriterion("use_date >=", value, "use_date");
            return (Criteria) this;
        }

        public Criteria andUse_dateLessThan(String value) {
            addCriterion("use_date <", value, "use_date");
            return (Criteria) this;
        }

        public Criteria andUse_dateLessThanOrEqualTo(String value) {
            addCriterion("use_date <=", value, "use_date");
            return (Criteria) this;
        }

        public Criteria andUse_dateLike(String value) {
            addCriterion("use_date like", value, "use_date");
            return (Criteria) this;
        }

        public Criteria andUse_dateNotLike(String value) {
            addCriterion("use_date not like", value, "use_date");
            return (Criteria) this;
        }

        public Criteria andUse_dateIn(List<String> values) {
            addCriterion("use_date in", values, "use_date");
            return (Criteria) this;
        }

        public Criteria andUse_dateNotIn(List<String> values) {
            addCriterion("use_date not in", values, "use_date");
            return (Criteria) this;
        }

        public Criteria andUse_dateBetween(String value1, String value2) {
            addCriterion("use_date between", value1, value2, "use_date");
            return (Criteria) this;
        }

        public Criteria andUse_dateNotBetween(String value1, String value2) {
            addCriterion("use_date not between", value1, value2, "use_date");
            return (Criteria) this;
        }

        public Criteria andFinanc_purposeIsNull() {
            addCriterion("financ_purpose is null");
            return (Criteria) this;
        }

        public Criteria andFinanc_purposeIsNotNull() {
            addCriterion("financ_purpose is not null");
            return (Criteria) this;
        }

        public Criteria andFinanc_purposeEqualTo(String value) {
            addCriterion("financ_purpose =", value, "financ_purpose");
            return (Criteria) this;
        }

        public Criteria andFinanc_purposeNotEqualTo(String value) {
            addCriterion("financ_purpose <>", value, "financ_purpose");
            return (Criteria) this;
        }

        public Criteria andFinanc_purposeGreaterThan(String value) {
            addCriterion("financ_purpose >", value, "financ_purpose");
            return (Criteria) this;
        }

        public Criteria andFinanc_purposeGreaterThanOrEqualTo(String value) {
            addCriterion("financ_purpose >=", value, "financ_purpose");
            return (Criteria) this;
        }

        public Criteria andFinanc_purposeLessThan(String value) {
            addCriterion("financ_purpose <", value, "financ_purpose");
            return (Criteria) this;
        }

        public Criteria andFinanc_purposeLessThanOrEqualTo(String value) {
            addCriterion("financ_purpose <=", value, "financ_purpose");
            return (Criteria) this;
        }

        public Criteria andFinanc_purposeLike(String value) {
            addCriterion("financ_purpose like", value, "financ_purpose");
            return (Criteria) this;
        }

        public Criteria andFinanc_purposeNotLike(String value) {
            addCriterion("financ_purpose not like", value, "financ_purpose");
            return (Criteria) this;
        }

        public Criteria andFinanc_purposeIn(List<String> values) {
            addCriterion("financ_purpose in", values, "financ_purpose");
            return (Criteria) this;
        }

        public Criteria andFinanc_purposeNotIn(List<String> values) {
            addCriterion("financ_purpose not in", values, "financ_purpose");
            return (Criteria) this;
        }

        public Criteria andFinanc_purposeBetween(String value1, String value2) {
            addCriterion("financ_purpose between", value1, value2, "financ_purpose");
            return (Criteria) this;
        }

        public Criteria andFinanc_purposeNotBetween(String value1, String value2) {
            addCriterion("financ_purpose not between", value1, value2, "financ_purpose");
            return (Criteria) this;
        }

        public Criteria andBank_codeIsNull() {
            addCriterion("bank_code is null");
            return (Criteria) this;
        }

        public Criteria andBank_codeIsNotNull() {
            addCriterion("bank_code is not null");
            return (Criteria) this;
        }

        public Criteria andBank_codeEqualTo(String value) {
            addCriterion("bank_code =", value, "bank_code");
            return (Criteria) this;
        }

        public Criteria andBank_codeNotEqualTo(String value) {
            addCriterion("bank_code <>", value, "bank_code");
            return (Criteria) this;
        }

        public Criteria andBank_codeGreaterThan(String value) {
            addCriterion("bank_code >", value, "bank_code");
            return (Criteria) this;
        }

        public Criteria andBank_codeGreaterThanOrEqualTo(String value) {
            addCriterion("bank_code >=", value, "bank_code");
            return (Criteria) this;
        }

        public Criteria andBank_codeLessThan(String value) {
            addCriterion("bank_code <", value, "bank_code");
            return (Criteria) this;
        }

        public Criteria andBank_codeLessThanOrEqualTo(String value) {
            addCriterion("bank_code <=", value, "bank_code");
            return (Criteria) this;
        }

        public Criteria andBank_codeLike(String value) {
            addCriterion("bank_code like", value, "bank_code");
            return (Criteria) this;
        }

        public Criteria andBank_codeNotLike(String value) {
            addCriterion("bank_code not like", value, "bank_code");
            return (Criteria) this;
        }

        public Criteria andBank_codeIn(List<String> values) {
            addCriterion("bank_code in", values, "bank_code");
            return (Criteria) this;
        }

        public Criteria andBank_codeNotIn(List<String> values) {
            addCriterion("bank_code not in", values, "bank_code");
            return (Criteria) this;
        }

        public Criteria andBank_codeBetween(String value1, String value2) {
            addCriterion("bank_code between", value1, value2, "bank_code");
            return (Criteria) this;
        }

        public Criteria andBank_codeNotBetween(String value1, String value2) {
            addCriterion("bank_code not between", value1, value2, "bank_code");
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

        public Criteria andWithdraw_accountIsNull() {
            addCriterion("withdraw_account is null");
            return (Criteria) this;
        }

        public Criteria andWithdraw_accountIsNotNull() {
            addCriterion("withdraw_account is not null");
            return (Criteria) this;
        }

        public Criteria andWithdraw_accountEqualTo(String value) {
            addCriterion("withdraw_account =", value, "withdraw_account");
            return (Criteria) this;
        }

        public Criteria andWithdraw_accountNotEqualTo(String value) {
            addCriterion("withdraw_account <>", value, "withdraw_account");
            return (Criteria) this;
        }

        public Criteria andWithdraw_accountGreaterThan(String value) {
            addCriterion("withdraw_account >", value, "withdraw_account");
            return (Criteria) this;
        }

        public Criteria andWithdraw_accountGreaterThanOrEqualTo(String value) {
            addCriterion("withdraw_account >=", value, "withdraw_account");
            return (Criteria) this;
        }

        public Criteria andWithdraw_accountLessThan(String value) {
            addCriterion("withdraw_account <", value, "withdraw_account");
            return (Criteria) this;
        }

        public Criteria andWithdraw_accountLessThanOrEqualTo(String value) {
            addCriterion("withdraw_account <=", value, "withdraw_account");
            return (Criteria) this;
        }

        public Criteria andWithdraw_accountLike(String value) {
            addCriterion("withdraw_account like", value, "withdraw_account");
            return (Criteria) this;
        }

        public Criteria andWithdraw_accountNotLike(String value) {
            addCriterion("withdraw_account not like", value, "withdraw_account");
            return (Criteria) this;
        }

        public Criteria andWithdraw_accountIn(List<String> values) {
            addCriterion("withdraw_account in", values, "withdraw_account");
            return (Criteria) this;
        }

        public Criteria andWithdraw_accountNotIn(List<String> values) {
            addCriterion("withdraw_account not in", values, "withdraw_account");
            return (Criteria) this;
        }

        public Criteria andWithdraw_accountBetween(String value1, String value2) {
            addCriterion("withdraw_account between", value1, value2, "withdraw_account");
            return (Criteria) this;
        }

        public Criteria andWithdraw_accountNotBetween(String value1, String value2) {
            addCriterion("withdraw_account not between", value1, value2, "withdraw_account");
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

        public Criteria andPayee_nameIsNull() {
            addCriterion("payee_name is null");
            return (Criteria) this;
        }

        public Criteria andPayee_nameIsNotNull() {
            addCriterion("payee_name is not null");
            return (Criteria) this;
        }

        public Criteria andPayee_nameEqualTo(String value) {
            addCriterion("payee_name =", value, "payee_name");
            return (Criteria) this;
        }

        public Criteria andPayee_nameNotEqualTo(String value) {
            addCriterion("payee_name <>", value, "payee_name");
            return (Criteria) this;
        }

        public Criteria andPayee_nameGreaterThan(String value) {
            addCriterion("payee_name >", value, "payee_name");
            return (Criteria) this;
        }

        public Criteria andPayee_nameGreaterThanOrEqualTo(String value) {
            addCriterion("payee_name >=", value, "payee_name");
            return (Criteria) this;
        }

        public Criteria andPayee_nameLessThan(String value) {
            addCriterion("payee_name <", value, "payee_name");
            return (Criteria) this;
        }

        public Criteria andPayee_nameLessThanOrEqualTo(String value) {
            addCriterion("payee_name <=", value, "payee_name");
            return (Criteria) this;
        }

        public Criteria andPayee_nameLike(String value) {
            addCriterion("payee_name like", value, "payee_name");
            return (Criteria) this;
        }

        public Criteria andPayee_nameNotLike(String value) {
            addCriterion("payee_name not like", value, "payee_name");
            return (Criteria) this;
        }

        public Criteria andPayee_nameIn(List<String> values) {
            addCriterion("payee_name in", values, "payee_name");
            return (Criteria) this;
        }

        public Criteria andPayee_nameNotIn(List<String> values) {
            addCriterion("payee_name not in", values, "payee_name");
            return (Criteria) this;
        }

        public Criteria andPayee_nameBetween(String value1, String value2) {
            addCriterion("payee_name between", value1, value2, "payee_name");
            return (Criteria) this;
        }

        public Criteria andPayee_nameNotBetween(String value1, String value2) {
            addCriterion("payee_name not between", value1, value2, "payee_name");
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

        public Criteria andFee_intIsNull() {
            addCriterion("fee_int is null");
            return (Criteria) this;
        }

        public Criteria andFee_intIsNotNull() {
            addCriterion("fee_int is not null");
            return (Criteria) this;
        }

        public Criteria andFee_intEqualTo(BigDecimal value) {
            addCriterion("fee_int =", value, "fee_int");
            return (Criteria) this;
        }

        public Criteria andFee_intNotEqualTo(BigDecimal value) {
            addCriterion("fee_int <>", value, "fee_int");
            return (Criteria) this;
        }

        public Criteria andFee_intGreaterThan(BigDecimal value) {
            addCriterion("fee_int >", value, "fee_int");
            return (Criteria) this;
        }

        public Criteria andFee_intGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("fee_int >=", value, "fee_int");
            return (Criteria) this;
        }

        public Criteria andFee_intLessThan(BigDecimal value) {
            addCriterion("fee_int <", value, "fee_int");
            return (Criteria) this;
        }

        public Criteria andFee_intLessThanOrEqualTo(BigDecimal value) {
            addCriterion("fee_int <=", value, "fee_int");
            return (Criteria) this;
        }

        public Criteria andFee_intIn(List<BigDecimal> values) {
            addCriterion("fee_int in", values, "fee_int");
            return (Criteria) this;
        }

        public Criteria andFee_intNotIn(List<BigDecimal> values) {
            addCriterion("fee_int not in", values, "fee_int");
            return (Criteria) this;
        }

        public Criteria andFee_intBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("fee_int between", value1, value2, "fee_int");
            return (Criteria) this;
        }

        public Criteria andFee_intNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("fee_int not between", value1, value2, "fee_int");
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

        public Criteria andTrustee_platcustIsNull() {
            addCriterion("trustee_platcust is null");
            return (Criteria) this;
        }

        public Criteria andTrustee_platcustIsNotNull() {
            addCriterion("trustee_platcust is not null");
            return (Criteria) this;
        }

        public Criteria andTrustee_platcustEqualTo(String value) {
            addCriterion("trustee_platcust =", value, "trustee_platcust");
            return (Criteria) this;
        }

        public Criteria andTrustee_platcustNotEqualTo(String value) {
            addCriterion("trustee_platcust <>", value, "trustee_platcust");
            return (Criteria) this;
        }

        public Criteria andTrustee_platcustGreaterThan(String value) {
            addCriterion("trustee_platcust >", value, "trustee_platcust");
            return (Criteria) this;
        }

        public Criteria andTrustee_platcustGreaterThanOrEqualTo(String value) {
            addCriterion("trustee_platcust >=", value, "trustee_platcust");
            return (Criteria) this;
        }

        public Criteria andTrustee_platcustLessThan(String value) {
            addCriterion("trustee_platcust <", value, "trustee_platcust");
            return (Criteria) this;
        }

        public Criteria andTrustee_platcustLessThanOrEqualTo(String value) {
            addCriterion("trustee_platcust <=", value, "trustee_platcust");
            return (Criteria) this;
        }

        public Criteria andTrustee_platcustLike(String value) {
            addCriterion("trustee_platcust like", value, "trustee_platcust");
            return (Criteria) this;
        }

        public Criteria andTrustee_platcustNotLike(String value) {
            addCriterion("trustee_platcust not like", value, "trustee_platcust");
            return (Criteria) this;
        }

        public Criteria andTrustee_platcustIn(List<String> values) {
            addCriterion("trustee_platcust in", values, "trustee_platcust");
            return (Criteria) this;
        }

        public Criteria andTrustee_platcustNotIn(List<String> values) {
            addCriterion("trustee_platcust not in", values, "trustee_platcust");
            return (Criteria) this;
        }

        public Criteria andTrustee_platcustBetween(String value1, String value2) {
            addCriterion("trustee_platcust between", value1, value2, "trustee_platcust");
            return (Criteria) this;
        }

        public Criteria andTrustee_platcustNotBetween(String value1, String value2) {
            addCriterion("trustee_platcust not between", value1, value2, "trustee_platcust");
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
     * eacc_financinfo
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * eacc_financinfo 2017-09-15
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