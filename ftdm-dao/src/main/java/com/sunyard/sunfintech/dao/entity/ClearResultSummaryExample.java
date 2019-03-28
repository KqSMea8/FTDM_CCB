package com.sunyard.sunfintech.dao.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ClearResultSummaryExample {
    /**
     * clear_result_summary
     */
    protected String orderByClause;

    /**
     * clear_result_summary
     */
    protected boolean distinct;

    /**
     * clear_result_summary
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2017-06-19
     */
    public ClearResultSummaryExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *
     * @mbggenerated 2017-06-19
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *
     * @mbggenerated 2017-06-19
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *
     * @mbggenerated 2017-06-19
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *
     * @mbggenerated 2017-06-19
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *
     * @mbggenerated 2017-06-19
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *
     * @mbggenerated 2017-06-19
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *
     * @mbggenerated 2017-06-19
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *
     * @mbggenerated 2017-06-19
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
     * @mbggenerated 2017-06-19
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *
     * @mbggenerated 2017-06-19
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * clear_result_summary 2017-06-19
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

        public Criteria andPidIsNull() {
            addCriterion("pid is null");
            return (Criteria) this;
        }

        public Criteria andPidIsNotNull() {
            addCriterion("pid is not null");
            return (Criteria) this;
        }

        public Criteria andPidEqualTo(Integer value) {
            addCriterion("pid =", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotEqualTo(Integer value) {
            addCriterion("pid <>", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThan(Integer value) {
            addCriterion("pid >", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThanOrEqualTo(Integer value) {
            addCriterion("pid >=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThan(Integer value) {
            addCriterion("pid <", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThanOrEqualTo(Integer value) {
            addCriterion("pid <=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidIn(List<Integer> values) {
            addCriterion("pid in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotIn(List<Integer> values) {
            addCriterion("pid not in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidBetween(Integer value1, Integer value2) {
            addCriterion("pid between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotBetween(Integer value1, Integer value2) {
            addCriterion("pid not between", value1, value2, "pid");
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

        public Criteria andClear_dateIsNull() {
            addCriterion("clear_date is null");
            return (Criteria) this;
        }

        public Criteria andClear_dateIsNotNull() {
            addCriterion("clear_date is not null");
            return (Criteria) this;
        }

        public Criteria andClear_dateEqualTo(Date value) {
            addCriterionForJDBCDate("clear_date =", value, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateNotEqualTo(Date value) {
            addCriterionForJDBCDate("clear_date <>", value, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateGreaterThan(Date value) {
            addCriterionForJDBCDate("clear_date >", value, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("clear_date >=", value, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateLessThan(Date value) {
            addCriterionForJDBCDate("clear_date <", value, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("clear_date <=", value, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateIn(List<Date> values) {
            addCriterionForJDBCDate("clear_date in", values, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateNotIn(List<Date> values) {
            addCriterionForJDBCDate("clear_date not in", values, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("clear_date between", value1, value2, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("clear_date not between", value1, value2, "clear_date");
            return (Criteria) this;
        }

        public Criteria andRecharge_sum_insideIsNull() {
            addCriterion("recharge_sum_inside is null");
            return (Criteria) this;
        }

        public Criteria andRecharge_sum_insideIsNotNull() {
            addCriterion("recharge_sum_inside is not null");
            return (Criteria) this;
        }

        public Criteria andRecharge_sum_insideEqualTo(BigDecimal value) {
            addCriterion("recharge_sum_inside =", value, "recharge_sum_inside");
            return (Criteria) this;
        }

        public Criteria andRecharge_sum_insideNotEqualTo(BigDecimal value) {
            addCriterion("recharge_sum_inside <>", value, "recharge_sum_inside");
            return (Criteria) this;
        }

        public Criteria andRecharge_sum_insideGreaterThan(BigDecimal value) {
            addCriterion("recharge_sum_inside >", value, "recharge_sum_inside");
            return (Criteria) this;
        }

        public Criteria andRecharge_sum_insideGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recharge_sum_inside >=", value, "recharge_sum_inside");
            return (Criteria) this;
        }

        public Criteria andRecharge_sum_insideLessThan(BigDecimal value) {
            addCriterion("recharge_sum_inside <", value, "recharge_sum_inside");
            return (Criteria) this;
        }

        public Criteria andRecharge_sum_insideLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recharge_sum_inside <=", value, "recharge_sum_inside");
            return (Criteria) this;
        }

        public Criteria andRecharge_sum_insideIn(List<BigDecimal> values) {
            addCriterion("recharge_sum_inside in", values, "recharge_sum_inside");
            return (Criteria) this;
        }

        public Criteria andRecharge_sum_insideNotIn(List<BigDecimal> values) {
            addCriterion("recharge_sum_inside not in", values, "recharge_sum_inside");
            return (Criteria) this;
        }

        public Criteria andRecharge_sum_insideBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recharge_sum_inside between", value1, value2, "recharge_sum_inside");
            return (Criteria) this;
        }

        public Criteria andRecharge_sum_insideNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recharge_sum_inside not between", value1, value2, "recharge_sum_inside");
            return (Criteria) this;
        }

        public Criteria andRecharge_sum_outsideIsNull() {
            addCriterion("recharge_sum_outside is null");
            return (Criteria) this;
        }

        public Criteria andRecharge_sum_outsideIsNotNull() {
            addCriterion("recharge_sum_outside is not null");
            return (Criteria) this;
        }

        public Criteria andRecharge_sum_outsideEqualTo(BigDecimal value) {
            addCriterion("recharge_sum_outside =", value, "recharge_sum_outside");
            return (Criteria) this;
        }

        public Criteria andRecharge_sum_outsideNotEqualTo(BigDecimal value) {
            addCriterion("recharge_sum_outside <>", value, "recharge_sum_outside");
            return (Criteria) this;
        }

        public Criteria andRecharge_sum_outsideGreaterThan(BigDecimal value) {
            addCriterion("recharge_sum_outside >", value, "recharge_sum_outside");
            return (Criteria) this;
        }

        public Criteria andRecharge_sum_outsideGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recharge_sum_outside >=", value, "recharge_sum_outside");
            return (Criteria) this;
        }

        public Criteria andRecharge_sum_outsideLessThan(BigDecimal value) {
            addCriterion("recharge_sum_outside <", value, "recharge_sum_outside");
            return (Criteria) this;
        }

        public Criteria andRecharge_sum_outsideLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recharge_sum_outside <=", value, "recharge_sum_outside");
            return (Criteria) this;
        }

        public Criteria andRecharge_sum_outsideIn(List<BigDecimal> values) {
            addCriterion("recharge_sum_outside in", values, "recharge_sum_outside");
            return (Criteria) this;
        }

        public Criteria andRecharge_sum_outsideNotIn(List<BigDecimal> values) {
            addCriterion("recharge_sum_outside not in", values, "recharge_sum_outside");
            return (Criteria) this;
        }

        public Criteria andRecharge_sum_outsideBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recharge_sum_outside between", value1, value2, "recharge_sum_outside");
            return (Criteria) this;
        }

        public Criteria andRecharge_sum_outsideNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recharge_sum_outside not between", value1, value2, "recharge_sum_outside");
            return (Criteria) this;
        }

        public Criteria andRecharge_count_insideIsNull() {
            addCriterion("recharge_count_inside is null");
            return (Criteria) this;
        }

        public Criteria andRecharge_count_insideIsNotNull() {
            addCriterion("recharge_count_inside is not null");
            return (Criteria) this;
        }

        public Criteria andRecharge_count_insideEqualTo(Integer value) {
            addCriterion("recharge_count_inside =", value, "recharge_count_inside");
            return (Criteria) this;
        }

        public Criteria andRecharge_count_insideNotEqualTo(Integer value) {
            addCriterion("recharge_count_inside <>", value, "recharge_count_inside");
            return (Criteria) this;
        }

        public Criteria andRecharge_count_insideGreaterThan(Integer value) {
            addCriterion("recharge_count_inside >", value, "recharge_count_inside");
            return (Criteria) this;
        }

        public Criteria andRecharge_count_insideGreaterThanOrEqualTo(Integer value) {
            addCriterion("recharge_count_inside >=", value, "recharge_count_inside");
            return (Criteria) this;
        }

        public Criteria andRecharge_count_insideLessThan(Integer value) {
            addCriterion("recharge_count_inside <", value, "recharge_count_inside");
            return (Criteria) this;
        }

        public Criteria andRecharge_count_insideLessThanOrEqualTo(Integer value) {
            addCriterion("recharge_count_inside <=", value, "recharge_count_inside");
            return (Criteria) this;
        }

        public Criteria andRecharge_count_insideIn(List<Integer> values) {
            addCriterion("recharge_count_inside in", values, "recharge_count_inside");
            return (Criteria) this;
        }

        public Criteria andRecharge_count_insideNotIn(List<Integer> values) {
            addCriterion("recharge_count_inside not in", values, "recharge_count_inside");
            return (Criteria) this;
        }

        public Criteria andRecharge_count_insideBetween(Integer value1, Integer value2) {
            addCriterion("recharge_count_inside between", value1, value2, "recharge_count_inside");
            return (Criteria) this;
        }

        public Criteria andRecharge_count_insideNotBetween(Integer value1, Integer value2) {
            addCriterion("recharge_count_inside not between", value1, value2, "recharge_count_inside");
            return (Criteria) this;
        }

        public Criteria andRecharge_count_outsideIsNull() {
            addCriterion("recharge_count_outside is null");
            return (Criteria) this;
        }

        public Criteria andRecharge_count_outsideIsNotNull() {
            addCriterion("recharge_count_outside is not null");
            return (Criteria) this;
        }

        public Criteria andRecharge_count_outsideEqualTo(Integer value) {
            addCriterion("recharge_count_outside =", value, "recharge_count_outside");
            return (Criteria) this;
        }

        public Criteria andRecharge_count_outsideNotEqualTo(Integer value) {
            addCriterion("recharge_count_outside <>", value, "recharge_count_outside");
            return (Criteria) this;
        }

        public Criteria andRecharge_count_outsideGreaterThan(Integer value) {
            addCriterion("recharge_count_outside >", value, "recharge_count_outside");
            return (Criteria) this;
        }

        public Criteria andRecharge_count_outsideGreaterThanOrEqualTo(Integer value) {
            addCriterion("recharge_count_outside >=", value, "recharge_count_outside");
            return (Criteria) this;
        }

        public Criteria andRecharge_count_outsideLessThan(Integer value) {
            addCriterion("recharge_count_outside <", value, "recharge_count_outside");
            return (Criteria) this;
        }

        public Criteria andRecharge_count_outsideLessThanOrEqualTo(Integer value) {
            addCriterion("recharge_count_outside <=", value, "recharge_count_outside");
            return (Criteria) this;
        }

        public Criteria andRecharge_count_outsideIn(List<Integer> values) {
            addCriterion("recharge_count_outside in", values, "recharge_count_outside");
            return (Criteria) this;
        }

        public Criteria andRecharge_count_outsideNotIn(List<Integer> values) {
            addCriterion("recharge_count_outside not in", values, "recharge_count_outside");
            return (Criteria) this;
        }

        public Criteria andRecharge_count_outsideBetween(Integer value1, Integer value2) {
            addCriterion("recharge_count_outside between", value1, value2, "recharge_count_outside");
            return (Criteria) this;
        }

        public Criteria andRecharge_count_outsideNotBetween(Integer value1, Integer value2) {
            addCriterion("recharge_count_outside not between", value1, value2, "recharge_count_outside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_sum_insideIsNull() {
            addCriterion("withdrawals_sum_inside is null");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_sum_insideIsNotNull() {
            addCriterion("withdrawals_sum_inside is not null");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_sum_insideEqualTo(BigDecimal value) {
            addCriterion("withdrawals_sum_inside =", value, "withdrawals_sum_inside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_sum_insideNotEqualTo(BigDecimal value) {
            addCriterion("withdrawals_sum_inside <>", value, "withdrawals_sum_inside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_sum_insideGreaterThan(BigDecimal value) {
            addCriterion("withdrawals_sum_inside >", value, "withdrawals_sum_inside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_sum_insideGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("withdrawals_sum_inside >=", value, "withdrawals_sum_inside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_sum_insideLessThan(BigDecimal value) {
            addCriterion("withdrawals_sum_inside <", value, "withdrawals_sum_inside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_sum_insideLessThanOrEqualTo(BigDecimal value) {
            addCriterion("withdrawals_sum_inside <=", value, "withdrawals_sum_inside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_sum_insideIn(List<BigDecimal> values) {
            addCriterion("withdrawals_sum_inside in", values, "withdrawals_sum_inside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_sum_insideNotIn(List<BigDecimal> values) {
            addCriterion("withdrawals_sum_inside not in", values, "withdrawals_sum_inside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_sum_insideBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("withdrawals_sum_inside between", value1, value2, "withdrawals_sum_inside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_sum_insideNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("withdrawals_sum_inside not between", value1, value2, "withdrawals_sum_inside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_sum_outsideIsNull() {
            addCriterion("withdrawals_sum_outside is null");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_sum_outsideIsNotNull() {
            addCriterion("withdrawals_sum_outside is not null");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_sum_outsideEqualTo(BigDecimal value) {
            addCriterion("withdrawals_sum_outside =", value, "withdrawals_sum_outside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_sum_outsideNotEqualTo(BigDecimal value) {
            addCriterion("withdrawals_sum_outside <>", value, "withdrawals_sum_outside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_sum_outsideGreaterThan(BigDecimal value) {
            addCriterion("withdrawals_sum_outside >", value, "withdrawals_sum_outside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_sum_outsideGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("withdrawals_sum_outside >=", value, "withdrawals_sum_outside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_sum_outsideLessThan(BigDecimal value) {
            addCriterion("withdrawals_sum_outside <", value, "withdrawals_sum_outside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_sum_outsideLessThanOrEqualTo(BigDecimal value) {
            addCriterion("withdrawals_sum_outside <=", value, "withdrawals_sum_outside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_sum_outsideIn(List<BigDecimal> values) {
            addCriterion("withdrawals_sum_outside in", values, "withdrawals_sum_outside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_sum_outsideNotIn(List<BigDecimal> values) {
            addCriterion("withdrawals_sum_outside not in", values, "withdrawals_sum_outside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_sum_outsideBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("withdrawals_sum_outside between", value1, value2, "withdrawals_sum_outside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_sum_outsideNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("withdrawals_sum_outside not between", value1, value2, "withdrawals_sum_outside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_count_insideIsNull() {
            addCriterion("withdrawals_count_inside is null");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_count_insideIsNotNull() {
            addCriterion("withdrawals_count_inside is not null");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_count_insideEqualTo(Integer value) {
            addCriterion("withdrawals_count_inside =", value, "withdrawals_count_inside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_count_insideNotEqualTo(Integer value) {
            addCriterion("withdrawals_count_inside <>", value, "withdrawals_count_inside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_count_insideGreaterThan(Integer value) {
            addCriterion("withdrawals_count_inside >", value, "withdrawals_count_inside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_count_insideGreaterThanOrEqualTo(Integer value) {
            addCriterion("withdrawals_count_inside >=", value, "withdrawals_count_inside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_count_insideLessThan(Integer value) {
            addCriterion("withdrawals_count_inside <", value, "withdrawals_count_inside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_count_insideLessThanOrEqualTo(Integer value) {
            addCriterion("withdrawals_count_inside <=", value, "withdrawals_count_inside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_count_insideIn(List<Integer> values) {
            addCriterion("withdrawals_count_inside in", values, "withdrawals_count_inside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_count_insideNotIn(List<Integer> values) {
            addCriterion("withdrawals_count_inside not in", values, "withdrawals_count_inside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_count_insideBetween(Integer value1, Integer value2) {
            addCriterion("withdrawals_count_inside between", value1, value2, "withdrawals_count_inside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_count_insideNotBetween(Integer value1, Integer value2) {
            addCriterion("withdrawals_count_inside not between", value1, value2, "withdrawals_count_inside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_count_outsideIsNull() {
            addCriterion("withdrawals_count_outside is null");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_count_outsideIsNotNull() {
            addCriterion("withdrawals_count_outside is not null");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_count_outsideEqualTo(Integer value) {
            addCriterion("withdrawals_count_outside =", value, "withdrawals_count_outside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_count_outsideNotEqualTo(Integer value) {
            addCriterion("withdrawals_count_outside <>", value, "withdrawals_count_outside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_count_outsideGreaterThan(Integer value) {
            addCriterion("withdrawals_count_outside >", value, "withdrawals_count_outside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_count_outsideGreaterThanOrEqualTo(Integer value) {
            addCriterion("withdrawals_count_outside >=", value, "withdrawals_count_outside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_count_outsideLessThan(Integer value) {
            addCriterion("withdrawals_count_outside <", value, "withdrawals_count_outside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_count_outsideLessThanOrEqualTo(Integer value) {
            addCriterion("withdrawals_count_outside <=", value, "withdrawals_count_outside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_count_outsideIn(List<Integer> values) {
            addCriterion("withdrawals_count_outside in", values, "withdrawals_count_outside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_count_outsideNotIn(List<Integer> values) {
            addCriterion("withdrawals_count_outside not in", values, "withdrawals_count_outside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_count_outsideBetween(Integer value1, Integer value2) {
            addCriterion("withdrawals_count_outside between", value1, value2, "withdrawals_count_outside");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_count_outsideNotBetween(Integer value1, Integer value2) {
            addCriterion("withdrawals_count_outside not between", value1, value2, "withdrawals_count_outside");
            return (Criteria) this;
        }

        public Criteria andRecharge_count_platIsNull() {
            addCriterion("recharge_count_plat is null");
            return (Criteria) this;
        }

        public Criteria andRecharge_count_platIsNotNull() {
            addCriterion("recharge_count_plat is not null");
            return (Criteria) this;
        }

        public Criteria andRecharge_count_platEqualTo(Integer value) {
            addCriterion("recharge_count_plat =", value, "recharge_count_plat");
            return (Criteria) this;
        }

        public Criteria andRecharge_count_platNotEqualTo(Integer value) {
            addCriterion("recharge_count_plat <>", value, "recharge_count_plat");
            return (Criteria) this;
        }

        public Criteria andRecharge_count_platGreaterThan(Integer value) {
            addCriterion("recharge_count_plat >", value, "recharge_count_plat");
            return (Criteria) this;
        }

        public Criteria andRecharge_count_platGreaterThanOrEqualTo(Integer value) {
            addCriterion("recharge_count_plat >=", value, "recharge_count_plat");
            return (Criteria) this;
        }

        public Criteria andRecharge_count_platLessThan(Integer value) {
            addCriterion("recharge_count_plat <", value, "recharge_count_plat");
            return (Criteria) this;
        }

        public Criteria andRecharge_count_platLessThanOrEqualTo(Integer value) {
            addCriterion("recharge_count_plat <=", value, "recharge_count_plat");
            return (Criteria) this;
        }

        public Criteria andRecharge_count_platIn(List<Integer> values) {
            addCriterion("recharge_count_plat in", values, "recharge_count_plat");
            return (Criteria) this;
        }

        public Criteria andRecharge_count_platNotIn(List<Integer> values) {
            addCriterion("recharge_count_plat not in", values, "recharge_count_plat");
            return (Criteria) this;
        }

        public Criteria andRecharge_count_platBetween(Integer value1, Integer value2) {
            addCriterion("recharge_count_plat between", value1, value2, "recharge_count_plat");
            return (Criteria) this;
        }

        public Criteria andRecharge_count_platNotBetween(Integer value1, Integer value2) {
            addCriterion("recharge_count_plat not between", value1, value2, "recharge_count_plat");
            return (Criteria) this;
        }

        public Criteria andRecharge_sum_platIsNull() {
            addCriterion("recharge_sum_plat is null");
            return (Criteria) this;
        }

        public Criteria andRecharge_sum_platIsNotNull() {
            addCriterion("recharge_sum_plat is not null");
            return (Criteria) this;
        }

        public Criteria andRecharge_sum_platEqualTo(BigDecimal value) {
            addCriterion("recharge_sum_plat =", value, "recharge_sum_plat");
            return (Criteria) this;
        }

        public Criteria andRecharge_sum_platNotEqualTo(BigDecimal value) {
            addCriterion("recharge_sum_plat <>", value, "recharge_sum_plat");
            return (Criteria) this;
        }

        public Criteria andRecharge_sum_platGreaterThan(BigDecimal value) {
            addCriterion("recharge_sum_plat >", value, "recharge_sum_plat");
            return (Criteria) this;
        }

        public Criteria andRecharge_sum_platGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recharge_sum_plat >=", value, "recharge_sum_plat");
            return (Criteria) this;
        }

        public Criteria andRecharge_sum_platLessThan(BigDecimal value) {
            addCriterion("recharge_sum_plat <", value, "recharge_sum_plat");
            return (Criteria) this;
        }

        public Criteria andRecharge_sum_platLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recharge_sum_plat <=", value, "recharge_sum_plat");
            return (Criteria) this;
        }

        public Criteria andRecharge_sum_platIn(List<BigDecimal> values) {
            addCriterion("recharge_sum_plat in", values, "recharge_sum_plat");
            return (Criteria) this;
        }

        public Criteria andRecharge_sum_platNotIn(List<BigDecimal> values) {
            addCriterion("recharge_sum_plat not in", values, "recharge_sum_plat");
            return (Criteria) this;
        }

        public Criteria andRecharge_sum_platBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recharge_sum_plat between", value1, value2, "recharge_sum_plat");
            return (Criteria) this;
        }

        public Criteria andRecharge_sum_platNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recharge_sum_plat not between", value1, value2, "recharge_sum_plat");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_count_platIsNull() {
            addCriterion("withdrawals_count_plat is null");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_count_platIsNotNull() {
            addCriterion("withdrawals_count_plat is not null");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_count_platEqualTo(Integer value) {
            addCriterion("withdrawals_count_plat =", value, "withdrawals_count_plat");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_count_platNotEqualTo(Integer value) {
            addCriterion("withdrawals_count_plat <>", value, "withdrawals_count_plat");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_count_platGreaterThan(Integer value) {
            addCriterion("withdrawals_count_plat >", value, "withdrawals_count_plat");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_count_platGreaterThanOrEqualTo(Integer value) {
            addCriterion("withdrawals_count_plat >=", value, "withdrawals_count_plat");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_count_platLessThan(Integer value) {
            addCriterion("withdrawals_count_plat <", value, "withdrawals_count_plat");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_count_platLessThanOrEqualTo(Integer value) {
            addCriterion("withdrawals_count_plat <=", value, "withdrawals_count_plat");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_count_platIn(List<Integer> values) {
            addCriterion("withdrawals_count_plat in", values, "withdrawals_count_plat");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_count_platNotIn(List<Integer> values) {
            addCriterion("withdrawals_count_plat not in", values, "withdrawals_count_plat");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_count_platBetween(Integer value1, Integer value2) {
            addCriterion("withdrawals_count_plat between", value1, value2, "withdrawals_count_plat");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_count_platNotBetween(Integer value1, Integer value2) {
            addCriterion("withdrawals_count_plat not between", value1, value2, "withdrawals_count_plat");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_sum_platIsNull() {
            addCriterion("withdrawals_sum_plat is null");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_sum_platIsNotNull() {
            addCriterion("withdrawals_sum_plat is not null");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_sum_platEqualTo(BigDecimal value) {
            addCriterion("withdrawals_sum_plat =", value, "withdrawals_sum_plat");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_sum_platNotEqualTo(BigDecimal value) {
            addCriterion("withdrawals_sum_plat <>", value, "withdrawals_sum_plat");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_sum_platGreaterThan(BigDecimal value) {
            addCriterion("withdrawals_sum_plat >", value, "withdrawals_sum_plat");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_sum_platGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("withdrawals_sum_plat >=", value, "withdrawals_sum_plat");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_sum_platLessThan(BigDecimal value) {
            addCriterion("withdrawals_sum_plat <", value, "withdrawals_sum_plat");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_sum_platLessThanOrEqualTo(BigDecimal value) {
            addCriterion("withdrawals_sum_plat <=", value, "withdrawals_sum_plat");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_sum_platIn(List<BigDecimal> values) {
            addCriterion("withdrawals_sum_plat in", values, "withdrawals_sum_plat");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_sum_platNotIn(List<BigDecimal> values) {
            addCriterion("withdrawals_sum_plat not in", values, "withdrawals_sum_plat");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_sum_platBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("withdrawals_sum_plat between", value1, value2, "withdrawals_sum_plat");
            return (Criteria) this;
        }

        public Criteria andWithdrawals_sum_platNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("withdrawals_sum_plat not between", value1, value2, "withdrawals_sum_plat");
            return (Criteria) this;
        }

        public Criteria andLiquidationIsNull() {
            addCriterion("liquidation is null");
            return (Criteria) this;
        }

        public Criteria andLiquidationIsNotNull() {
            addCriterion("liquidation is not null");
            return (Criteria) this;
        }

        public Criteria andLiquidationEqualTo(String value) {
            addCriterion("liquidation =", value, "liquidation");
            return (Criteria) this;
        }

        public Criteria andLiquidationNotEqualTo(String value) {
            addCriterion("liquidation <>", value, "liquidation");
            return (Criteria) this;
        }

        public Criteria andLiquidationGreaterThan(String value) {
            addCriterion("liquidation >", value, "liquidation");
            return (Criteria) this;
        }

        public Criteria andLiquidationGreaterThanOrEqualTo(String value) {
            addCriterion("liquidation >=", value, "liquidation");
            return (Criteria) this;
        }

        public Criteria andLiquidationLessThan(String value) {
            addCriterion("liquidation <", value, "liquidation");
            return (Criteria) this;
        }

        public Criteria andLiquidationLessThanOrEqualTo(String value) {
            addCriterion("liquidation <=", value, "liquidation");
            return (Criteria) this;
        }

        public Criteria andLiquidationLike(String value) {
            addCriterion("liquidation like", value, "liquidation");
            return (Criteria) this;
        }

        public Criteria andLiquidationNotLike(String value) {
            addCriterion("liquidation not like", value, "liquidation");
            return (Criteria) this;
        }

        public Criteria andLiquidationIn(List<String> values) {
            addCriterion("liquidation in", values, "liquidation");
            return (Criteria) this;
        }

        public Criteria andLiquidationNotIn(List<String> values) {
            addCriterion("liquidation not in", values, "liquidation");
            return (Criteria) this;
        }

        public Criteria andLiquidationBetween(String value1, String value2) {
            addCriterion("liquidation between", value1, value2, "liquidation");
            return (Criteria) this;
        }

        public Criteria andLiquidationNotBetween(String value1, String value2) {
            addCriterion("liquidation not between", value1, value2, "liquidation");
            return (Criteria) this;
        }

        public Criteria andLiquidation_recordtimeIsNull() {
            addCriterion("liquidation_recordtime is null");
            return (Criteria) this;
        }

        public Criteria andLiquidation_recordtimeIsNotNull() {
            addCriterion("liquidation_recordtime is not null");
            return (Criteria) this;
        }

        public Criteria andLiquidation_recordtimeEqualTo(String value) {
            addCriterion("liquidation_recordtime =", value, "liquidation_recordtime");
            return (Criteria) this;
        }

        public Criteria andLiquidation_recordtimeNotEqualTo(String value) {
            addCriterion("liquidation_recordtime <>", value, "liquidation_recordtime");
            return (Criteria) this;
        }

        public Criteria andLiquidation_recordtimeGreaterThan(String value) {
            addCriterion("liquidation_recordtime >", value, "liquidation_recordtime");
            return (Criteria) this;
        }

        public Criteria andLiquidation_recordtimeGreaterThanOrEqualTo(String value) {
            addCriterion("liquidation_recordtime >=", value, "liquidation_recordtime");
            return (Criteria) this;
        }

        public Criteria andLiquidation_recordtimeLessThan(String value) {
            addCriterion("liquidation_recordtime <", value, "liquidation_recordtime");
            return (Criteria) this;
        }

        public Criteria andLiquidation_recordtimeLessThanOrEqualTo(String value) {
            addCriterion("liquidation_recordtime <=", value, "liquidation_recordtime");
            return (Criteria) this;
        }

        public Criteria andLiquidation_recordtimeLike(String value) {
            addCriterion("liquidation_recordtime like", value, "liquidation_recordtime");
            return (Criteria) this;
        }

        public Criteria andLiquidation_recordtimeNotLike(String value) {
            addCriterion("liquidation_recordtime not like", value, "liquidation_recordtime");
            return (Criteria) this;
        }

        public Criteria andLiquidation_recordtimeIn(List<String> values) {
            addCriterion("liquidation_recordtime in", values, "liquidation_recordtime");
            return (Criteria) this;
        }

        public Criteria andLiquidation_recordtimeNotIn(List<String> values) {
            addCriterion("liquidation_recordtime not in", values, "liquidation_recordtime");
            return (Criteria) this;
        }

        public Criteria andLiquidation_recordtimeBetween(String value1, String value2) {
            addCriterion("liquidation_recordtime between", value1, value2, "liquidation_recordtime");
            return (Criteria) this;
        }

        public Criteria andLiquidation_recordtimeNotBetween(String value1, String value2) {
            addCriterion("liquidation_recordtime not between", value1, value2, "liquidation_recordtime");
            return (Criteria) this;
        }

        public Criteria andBalance_sumIsNull() {
            addCriterion("balance_sum is null");
            return (Criteria) this;
        }

        public Criteria andBalance_sumIsNotNull() {
            addCriterion("balance_sum is not null");
            return (Criteria) this;
        }

        public Criteria andBalance_sumEqualTo(BigDecimal value) {
            addCriterion("balance_sum =", value, "balance_sum");
            return (Criteria) this;
        }

        public Criteria andBalance_sumNotEqualTo(BigDecimal value) {
            addCriterion("balance_sum <>", value, "balance_sum");
            return (Criteria) this;
        }

        public Criteria andBalance_sumGreaterThan(BigDecimal value) {
            addCriterion("balance_sum >", value, "balance_sum");
            return (Criteria) this;
        }

        public Criteria andBalance_sumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("balance_sum >=", value, "balance_sum");
            return (Criteria) this;
        }

        public Criteria andBalance_sumLessThan(BigDecimal value) {
            addCriterion("balance_sum <", value, "balance_sum");
            return (Criteria) this;
        }

        public Criteria andBalance_sumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("balance_sum <=", value, "balance_sum");
            return (Criteria) this;
        }

        public Criteria andBalance_sumIn(List<BigDecimal> values) {
            addCriterion("balance_sum in", values, "balance_sum");
            return (Criteria) this;
        }

        public Criteria andBalance_sumNotIn(List<BigDecimal> values) {
            addCriterion("balance_sum not in", values, "balance_sum");
            return (Criteria) this;
        }

        public Criteria andBalance_sumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("balance_sum between", value1, value2, "balance_sum");
            return (Criteria) this;
        }

        public Criteria andBalance_sumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("balance_sum not between", value1, value2, "balance_sum");
            return (Criteria) this;
        }

        public Criteria andBalance_bankIsNull() {
            addCriterion("balance_bank is null");
            return (Criteria) this;
        }

        public Criteria andBalance_bankIsNotNull() {
            addCriterion("balance_bank is not null");
            return (Criteria) this;
        }

        public Criteria andBalance_bankEqualTo(BigDecimal value) {
            addCriterion("balance_bank =", value, "balance_bank");
            return (Criteria) this;
        }

        public Criteria andBalance_bankNotEqualTo(BigDecimal value) {
            addCriterion("balance_bank <>", value, "balance_bank");
            return (Criteria) this;
        }

        public Criteria andBalance_bankGreaterThan(BigDecimal value) {
            addCriterion("balance_bank >", value, "balance_bank");
            return (Criteria) this;
        }

        public Criteria andBalance_bankGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("balance_bank >=", value, "balance_bank");
            return (Criteria) this;
        }

        public Criteria andBalance_bankLessThan(BigDecimal value) {
            addCriterion("balance_bank <", value, "balance_bank");
            return (Criteria) this;
        }

        public Criteria andBalance_bankLessThanOrEqualTo(BigDecimal value) {
            addCriterion("balance_bank <=", value, "balance_bank");
            return (Criteria) this;
        }

        public Criteria andBalance_bankIn(List<BigDecimal> values) {
            addCriterion("balance_bank in", values, "balance_bank");
            return (Criteria) this;
        }

        public Criteria andBalance_bankNotIn(List<BigDecimal> values) {
            addCriterion("balance_bank not in", values, "balance_bank");
            return (Criteria) this;
        }

        public Criteria andBalance_bankBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("balance_bank between", value1, value2, "balance_bank");
            return (Criteria) this;
        }

        public Criteria andBalance_bankNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("balance_bank not between", value1, value2, "balance_bank");
            return (Criteria) this;
        }

        public Criteria andBook_balanceIsNull() {
            addCriterion("book_balance is null");
            return (Criteria) this;
        }

        public Criteria andBook_balanceIsNotNull() {
            addCriterion("book_balance is not null");
            return (Criteria) this;
        }

        public Criteria andBook_balanceEqualTo(BigDecimal value) {
            addCriterion("book_balance =", value, "book_balance");
            return (Criteria) this;
        }

        public Criteria andBook_balanceNotEqualTo(BigDecimal value) {
            addCriterion("book_balance <>", value, "book_balance");
            return (Criteria) this;
        }

        public Criteria andBook_balanceGreaterThan(BigDecimal value) {
            addCriterion("book_balance >", value, "book_balance");
            return (Criteria) this;
        }

        public Criteria andBook_balanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("book_balance >=", value, "book_balance");
            return (Criteria) this;
        }

        public Criteria andBook_balanceLessThan(BigDecimal value) {
            addCriterion("book_balance <", value, "book_balance");
            return (Criteria) this;
        }

        public Criteria andBook_balanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("book_balance <=", value, "book_balance");
            return (Criteria) this;
        }

        public Criteria andBook_balanceIn(List<BigDecimal> values) {
            addCriterion("book_balance in", values, "book_balance");
            return (Criteria) this;
        }

        public Criteria andBook_balanceNotIn(List<BigDecimal> values) {
            addCriterion("book_balance not in", values, "book_balance");
            return (Criteria) this;
        }

        public Criteria andBook_balanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("book_balance between", value1, value2, "book_balance");
            return (Criteria) this;
        }

        public Criteria andBook_balanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("book_balance not between", value1, value2, "book_balance");
            return (Criteria) this;
        }
    }

    /**
     * clear_result_summary
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * clear_result_summary 2017-06-19
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