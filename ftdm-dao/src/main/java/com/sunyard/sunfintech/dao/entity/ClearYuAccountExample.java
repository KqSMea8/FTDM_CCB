package com.sunyard.sunfintech.dao.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClearYuAccountExample {
    /**
     * clear_yu_account
     */
    protected String orderByClause;

    /**
     * clear_yu_account
     */
    protected boolean distinct;

    /**
     * clear_yu_account
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2018-02-03
     */
    public ClearYuAccountExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *
     * @mbggenerated 2018-02-03
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *
     * @mbggenerated 2018-02-03
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *
     * @mbggenerated 2018-02-03
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *
     * @mbggenerated 2018-02-03
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *
     * @mbggenerated 2018-02-03
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *
     * @mbggenerated 2018-02-03
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *
     * @mbggenerated 2018-02-03
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *
     * @mbggenerated 2018-02-03
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
     * @mbggenerated 2018-02-03
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *
     * @mbggenerated 2018-02-03
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * clear_yu_account 2018-02-03
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

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
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

        public Criteria andSub_subjectIsNull() {
            addCriterion("sub_subject is null");
            return (Criteria) this;
        }

        public Criteria andSub_subjectIsNotNull() {
            addCriterion("sub_subject is not null");
            return (Criteria) this;
        }

        public Criteria andSub_subjectEqualTo(String value) {
            addCriterion("sub_subject =", value, "sub_subject");
            return (Criteria) this;
        }

        public Criteria andSub_subjectNotEqualTo(String value) {
            addCriterion("sub_subject <>", value, "sub_subject");
            return (Criteria) this;
        }

        public Criteria andSub_subjectGreaterThan(String value) {
            addCriterion("sub_subject >", value, "sub_subject");
            return (Criteria) this;
        }

        public Criteria andSub_subjectGreaterThanOrEqualTo(String value) {
            addCriterion("sub_subject >=", value, "sub_subject");
            return (Criteria) this;
        }

        public Criteria andSub_subjectLessThan(String value) {
            addCriterion("sub_subject <", value, "sub_subject");
            return (Criteria) this;
        }

        public Criteria andSub_subjectLessThanOrEqualTo(String value) {
            addCriterion("sub_subject <=", value, "sub_subject");
            return (Criteria) this;
        }

        public Criteria andSub_subjectLike(String value) {
            addCriterion("sub_subject like", value, "sub_subject");
            return (Criteria) this;
        }

        public Criteria andSub_subjectNotLike(String value) {
            addCriterion("sub_subject not like", value, "sub_subject");
            return (Criteria) this;
        }

        public Criteria andSub_subjectIn(List<String> values) {
            addCriterion("sub_subject in", values, "sub_subject");
            return (Criteria) this;
        }

        public Criteria andSub_subjectNotIn(List<String> values) {
            addCriterion("sub_subject not in", values, "sub_subject");
            return (Criteria) this;
        }

        public Criteria andSub_subjectBetween(String value1, String value2) {
            addCriterion("sub_subject between", value1, value2, "sub_subject");
            return (Criteria) this;
        }

        public Criteria andSub_subjectNotBetween(String value1, String value2) {
            addCriterion("sub_subject not between", value1, value2, "sub_subject");
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

        public Criteria andClear_dateEqualTo(String value) {
            addCriterion("clear_date =", value, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateNotEqualTo(String value) {
            addCriterion("clear_date <>", value, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateGreaterThan(String value) {
            addCriterion("clear_date >", value, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateGreaterThanOrEqualTo(String value) {
            addCriterion("clear_date >=", value, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateLessThan(String value) {
            addCriterion("clear_date <", value, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateLessThanOrEqualTo(String value) {
            addCriterion("clear_date <=", value, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateLike(String value) {
            addCriterion("clear_date like", value, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateNotLike(String value) {
            addCriterion("clear_date not like", value, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateIn(List<String> values) {
            addCriterion("clear_date in", values, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateNotIn(List<String> values) {
            addCriterion("clear_date not in", values, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateBetween(String value1, String value2) {
            addCriterion("clear_date between", value1, value2, "clear_date");
            return (Criteria) this;
        }

        public Criteria andClear_dateNotBetween(String value1, String value2) {
            addCriterion("clear_date not between", value1, value2, "clear_date");
            return (Criteria) this;
        }

        public Criteria andN_balanceIsNull() {
            addCriterion("n_balance is null");
            return (Criteria) this;
        }

        public Criteria andN_balanceIsNotNull() {
            addCriterion("n_balance is not null");
            return (Criteria) this;
        }

        public Criteria andN_balanceEqualTo(BigDecimal value) {
            addCriterion("n_balance =", value, "n_balance");
            return (Criteria) this;
        }

        public Criteria andN_balanceNotEqualTo(BigDecimal value) {
            addCriterion("n_balance <>", value, "n_balance");
            return (Criteria) this;
        }

        public Criteria andN_balanceGreaterThan(BigDecimal value) {
            addCriterion("n_balance >", value, "n_balance");
            return (Criteria) this;
        }

        public Criteria andN_balanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("n_balance >=", value, "n_balance");
            return (Criteria) this;
        }

        public Criteria andN_balanceLessThan(BigDecimal value) {
            addCriterion("n_balance <", value, "n_balance");
            return (Criteria) this;
        }

        public Criteria andN_balanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("n_balance <=", value, "n_balance");
            return (Criteria) this;
        }

        public Criteria andN_balanceIn(List<BigDecimal> values) {
            addCriterion("n_balance in", values, "n_balance");
            return (Criteria) this;
        }

        public Criteria andN_balanceNotIn(List<BigDecimal> values) {
            addCriterion("n_balance not in", values, "n_balance");
            return (Criteria) this;
        }

        public Criteria andN_balanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("n_balance between", value1, value2, "n_balance");
            return (Criteria) this;
        }

        public Criteria andN_balanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("n_balance not between", value1, value2, "n_balance");
            return (Criteria) this;
        }

        public Criteria andF_balanceIsNull() {
            addCriterion("f_balance is null");
            return (Criteria) this;
        }

        public Criteria andF_balanceIsNotNull() {
            addCriterion("f_balance is not null");
            return (Criteria) this;
        }

        public Criteria andF_balanceEqualTo(BigDecimal value) {
            addCriterion("f_balance =", value, "f_balance");
            return (Criteria) this;
        }

        public Criteria andF_balanceNotEqualTo(BigDecimal value) {
            addCriterion("f_balance <>", value, "f_balance");
            return (Criteria) this;
        }

        public Criteria andF_balanceGreaterThan(BigDecimal value) {
            addCriterion("f_balance >", value, "f_balance");
            return (Criteria) this;
        }

        public Criteria andF_balanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("f_balance >=", value, "f_balance");
            return (Criteria) this;
        }

        public Criteria andF_balanceLessThan(BigDecimal value) {
            addCriterion("f_balance <", value, "f_balance");
            return (Criteria) this;
        }

        public Criteria andF_balanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("f_balance <=", value, "f_balance");
            return (Criteria) this;
        }

        public Criteria andF_balanceIn(List<BigDecimal> values) {
            addCriterion("f_balance in", values, "f_balance");
            return (Criteria) this;
        }

        public Criteria andF_balanceNotIn(List<BigDecimal> values) {
            addCriterion("f_balance not in", values, "f_balance");
            return (Criteria) this;
        }

        public Criteria andF_balanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("f_balance between", value1, value2, "f_balance");
            return (Criteria) this;
        }

        public Criteria andF_balanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("f_balance not between", value1, value2, "f_balance");
            return (Criteria) this;
        }

        public Criteria andCur_n_balanceIsNull() {
            addCriterion("cur_n_balance is null");
            return (Criteria) this;
        }

        public Criteria andCur_n_balanceIsNotNull() {
            addCriterion("cur_n_balance is not null");
            return (Criteria) this;
        }

        public Criteria andCur_n_balanceEqualTo(BigDecimal value) {
            addCriterion("cur_n_balance =", value, "cur_n_balance");
            return (Criteria) this;
        }

        public Criteria andCur_n_balanceNotEqualTo(BigDecimal value) {
            addCriterion("cur_n_balance <>", value, "cur_n_balance");
            return (Criteria) this;
        }

        public Criteria andCur_n_balanceGreaterThan(BigDecimal value) {
            addCriterion("cur_n_balance >", value, "cur_n_balance");
            return (Criteria) this;
        }

        public Criteria andCur_n_balanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("cur_n_balance >=", value, "cur_n_balance");
            return (Criteria) this;
        }

        public Criteria andCur_n_balanceLessThan(BigDecimal value) {
            addCriterion("cur_n_balance <", value, "cur_n_balance");
            return (Criteria) this;
        }

        public Criteria andCur_n_balanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("cur_n_balance <=", value, "cur_n_balance");
            return (Criteria) this;
        }

        public Criteria andCur_n_balanceIn(List<BigDecimal> values) {
            addCriterion("cur_n_balance in", values, "cur_n_balance");
            return (Criteria) this;
        }

        public Criteria andCur_n_balanceNotIn(List<BigDecimal> values) {
            addCriterion("cur_n_balance not in", values, "cur_n_balance");
            return (Criteria) this;
        }

        public Criteria andCur_n_balanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cur_n_balance between", value1, value2, "cur_n_balance");
            return (Criteria) this;
        }

        public Criteria andCur_n_balanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cur_n_balance not between", value1, value2, "cur_n_balance");
            return (Criteria) this;
        }

        public Criteria andCur_f_balanceIsNull() {
            addCriterion("cur_f_balance is null");
            return (Criteria) this;
        }

        public Criteria andCur_f_balanceIsNotNull() {
            addCriterion("cur_f_balance is not null");
            return (Criteria) this;
        }

        public Criteria andCur_f_balanceEqualTo(BigDecimal value) {
            addCriterion("cur_f_balance =", value, "cur_f_balance");
            return (Criteria) this;
        }

        public Criteria andCur_f_balanceNotEqualTo(BigDecimal value) {
            addCriterion("cur_f_balance <>", value, "cur_f_balance");
            return (Criteria) this;
        }

        public Criteria andCur_f_balanceGreaterThan(BigDecimal value) {
            addCriterion("cur_f_balance >", value, "cur_f_balance");
            return (Criteria) this;
        }

        public Criteria andCur_f_balanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("cur_f_balance >=", value, "cur_f_balance");
            return (Criteria) this;
        }

        public Criteria andCur_f_balanceLessThan(BigDecimal value) {
            addCriterion("cur_f_balance <", value, "cur_f_balance");
            return (Criteria) this;
        }

        public Criteria andCur_f_balanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("cur_f_balance <=", value, "cur_f_balance");
            return (Criteria) this;
        }

        public Criteria andCur_f_balanceIn(List<BigDecimal> values) {
            addCriterion("cur_f_balance in", values, "cur_f_balance");
            return (Criteria) this;
        }

        public Criteria andCur_f_balanceNotIn(List<BigDecimal> values) {
            addCriterion("cur_f_balance not in", values, "cur_f_balance");
            return (Criteria) this;
        }

        public Criteria andCur_f_balanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cur_f_balance between", value1, value2, "cur_f_balance");
            return (Criteria) this;
        }

        public Criteria andCur_f_balanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cur_f_balance not between", value1, value2, "cur_f_balance");
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
     * clear_yu_account
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * clear_yu_account 2018-02-03
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