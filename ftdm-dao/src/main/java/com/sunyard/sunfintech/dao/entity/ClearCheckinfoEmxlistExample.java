package com.sunyard.sunfintech.dao.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClearCheckinfoEmxlistExample {
    /**
     * clear_checkinfo_emxlist
     */
    protected String orderByClause;

    /**
     * clear_checkinfo_emxlist
     */
    protected boolean distinct;

    /**
     * clear_checkinfo_emxlist
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2017-07-04
     */
    public ClearCheckinfoEmxlistExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *
     * @mbggenerated 2017-07-04
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *
     * @mbggenerated 2017-07-04
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *
     * @mbggenerated 2017-07-04
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *
     * @mbggenerated 2017-07-04
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *
     * @mbggenerated 2017-07-04
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *
     * @mbggenerated 2017-07-04
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *
     * @mbggenerated 2017-07-04
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *
     * @mbggenerated 2017-07-04
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
     * @mbggenerated 2017-07-04
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *
     * @mbggenerated 2017-07-04
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * clear_checkinfo_emxlist 2017-07-04
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

        public Criteria andTrans_dateIsNull() {
            addCriterion("trans_date is null");
            return (Criteria) this;
        }

        public Criteria andTrans_dateIsNotNull() {
            addCriterion("trans_date is not null");
            return (Criteria) this;
        }

        public Criteria andTrans_dateEqualTo(Date value) {
            addCriterion("trans_date =", value, "trans_date");
            return (Criteria) this;
        }

        public Criteria andTrans_dateNotEqualTo(Date value) {
            addCriterion("trans_date <>", value, "trans_date");
            return (Criteria) this;
        }

        public Criteria andTrans_dateGreaterThan(Date value) {
            addCriterion("trans_date >", value, "trans_date");
            return (Criteria) this;
        }

        public Criteria andTrans_dateGreaterThanOrEqualTo(Date value) {
            addCriterion("trans_date >=", value, "trans_date");
            return (Criteria) this;
        }

        public Criteria andTrans_dateLessThan(Date value) {
            addCriterion("trans_date <", value, "trans_date");
            return (Criteria) this;
        }

        public Criteria andTrans_dateLessThanOrEqualTo(Date value) {
            addCriterion("trans_date <=", value, "trans_date");
            return (Criteria) this;
        }

        public Criteria andTrans_dateIn(List<Date> values) {
            addCriterion("trans_date in", values, "trans_date");
            return (Criteria) this;
        }

        public Criteria andTrans_dateNotIn(List<Date> values) {
            addCriterion("trans_date not in", values, "trans_date");
            return (Criteria) this;
        }

        public Criteria andTrans_dateBetween(Date value1, Date value2) {
            addCriterion("trans_date between", value1, value2, "trans_date");
            return (Criteria) this;
        }

        public Criteria andTrans_dateNotBetween(Date value1, Date value2) {
            addCriterion("trans_date not between", value1, value2, "trans_date");
            return (Criteria) this;
        }

        public Criteria andSerial_noIsNull() {
            addCriterion("serial_no is null");
            return (Criteria) this;
        }

        public Criteria andSerial_noIsNotNull() {
            addCriterion("serial_no is not null");
            return (Criteria) this;
        }

        public Criteria andSerial_noEqualTo(String value) {
            addCriterion("serial_no =", value, "serial_no");
            return (Criteria) this;
        }

        public Criteria andSerial_noNotEqualTo(String value) {
            addCriterion("serial_no <>", value, "serial_no");
            return (Criteria) this;
        }

        public Criteria andSerial_noGreaterThan(String value) {
            addCriterion("serial_no >", value, "serial_no");
            return (Criteria) this;
        }

        public Criteria andSerial_noGreaterThanOrEqualTo(String value) {
            addCriterion("serial_no >=", value, "serial_no");
            return (Criteria) this;
        }

        public Criteria andSerial_noLessThan(String value) {
            addCriterion("serial_no <", value, "serial_no");
            return (Criteria) this;
        }

        public Criteria andSerial_noLessThanOrEqualTo(String value) {
            addCriterion("serial_no <=", value, "serial_no");
            return (Criteria) this;
        }

        public Criteria andSerial_noLike(String value) {
            addCriterion("serial_no like", value, "serial_no");
            return (Criteria) this;
        }

        public Criteria andSerial_noNotLike(String value) {
            addCriterion("serial_no not like", value, "serial_no");
            return (Criteria) this;
        }

        public Criteria andSerial_noIn(List<String> values) {
            addCriterion("serial_no in", values, "serial_no");
            return (Criteria) this;
        }

        public Criteria andSerial_noNotIn(List<String> values) {
            addCriterion("serial_no not in", values, "serial_no");
            return (Criteria) this;
        }

        public Criteria andSerial_noBetween(String value1, String value2) {
            addCriterion("serial_no between", value1, value2, "serial_no");
            return (Criteria) this;
        }

        public Criteria andSerial_noNotBetween(String value1, String value2) {
            addCriterion("serial_no not between", value1, value2, "serial_no");
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

        public Criteria andAmtIsNull() {
            addCriterion("amt is null");
            return (Criteria) this;
        }

        public Criteria andAmtIsNotNull() {
            addCriterion("amt is not null");
            return (Criteria) this;
        }

        public Criteria andAmtEqualTo(BigDecimal value) {
            addCriterion("amt =", value, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtNotEqualTo(BigDecimal value) {
            addCriterion("amt <>", value, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtGreaterThan(BigDecimal value) {
            addCriterion("amt >", value, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("amt >=", value, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtLessThan(BigDecimal value) {
            addCriterion("amt <", value, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("amt <=", value, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtIn(List<BigDecimal> values) {
            addCriterion("amt in", values, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtNotIn(List<BigDecimal> values) {
            addCriterion("amt not in", values, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amt between", value1, value2, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amt not between", value1, value2, "amt");
            return (Criteria) this;
        }

        public Criteria andChargeIsNull() {
            addCriterion("charge is null");
            return (Criteria) this;
        }

        public Criteria andChargeIsNotNull() {
            addCriterion("charge is not null");
            return (Criteria) this;
        }

        public Criteria andChargeEqualTo(BigDecimal value) {
            addCriterion("charge =", value, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeNotEqualTo(BigDecimal value) {
            addCriterion("charge <>", value, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeGreaterThan(BigDecimal value) {
            addCriterion("charge >", value, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("charge >=", value, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeLessThan(BigDecimal value) {
            addCriterion("charge <", value, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("charge <=", value, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeIn(List<BigDecimal> values) {
            addCriterion("charge in", values, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeNotIn(List<BigDecimal> values) {
            addCriterion("charge not in", values, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("charge between", value1, value2, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("charge not between", value1, value2, "charge");
            return (Criteria) this;
        }

        public Criteria andOrder_typeIsNull() {
            addCriterion("order_type is null");
            return (Criteria) this;
        }

        public Criteria andOrder_typeIsNotNull() {
            addCriterion("order_type is not null");
            return (Criteria) this;
        }

        public Criteria andOrder_typeEqualTo(String value) {
            addCriterion("order_type =", value, "order_type");
            return (Criteria) this;
        }

        public Criteria andOrder_typeNotEqualTo(String value) {
            addCriterion("order_type <>", value, "order_type");
            return (Criteria) this;
        }

        public Criteria andOrder_typeGreaterThan(String value) {
            addCriterion("order_type >", value, "order_type");
            return (Criteria) this;
        }

        public Criteria andOrder_typeGreaterThanOrEqualTo(String value) {
            addCriterion("order_type >=", value, "order_type");
            return (Criteria) this;
        }

        public Criteria andOrder_typeLessThan(String value) {
            addCriterion("order_type <", value, "order_type");
            return (Criteria) this;
        }

        public Criteria andOrder_typeLessThanOrEqualTo(String value) {
            addCriterion("order_type <=", value, "order_type");
            return (Criteria) this;
        }

        public Criteria andOrder_typeLike(String value) {
            addCriterion("order_type like", value, "order_type");
            return (Criteria) this;
        }

        public Criteria andOrder_typeNotLike(String value) {
            addCriterion("order_type not like", value, "order_type");
            return (Criteria) this;
        }

        public Criteria andOrder_typeIn(List<String> values) {
            addCriterion("order_type in", values, "order_type");
            return (Criteria) this;
        }

        public Criteria andOrder_typeNotIn(List<String> values) {
            addCriterion("order_type not in", values, "order_type");
            return (Criteria) this;
        }

        public Criteria andOrder_typeBetween(String value1, String value2) {
            addCriterion("order_type between", value1, value2, "order_type");
            return (Criteria) this;
        }

        public Criteria andOrder_typeNotBetween(String value1, String value2) {
            addCriterion("order_type not between", value1, value2, "order_type");
            return (Criteria) this;
        }

        public Criteria andPay_codeIsNull() {
            addCriterion("pay_code is null");
            return (Criteria) this;
        }

        public Criteria andPay_codeIsNotNull() {
            addCriterion("pay_code is not null");
            return (Criteria) this;
        }

        public Criteria andPay_codeEqualTo(String value) {
            addCriterion("pay_code =", value, "pay_code");
            return (Criteria) this;
        }

        public Criteria andPay_codeNotEqualTo(String value) {
            addCriterion("pay_code <>", value, "pay_code");
            return (Criteria) this;
        }

        public Criteria andPay_codeGreaterThan(String value) {
            addCriterion("pay_code >", value, "pay_code");
            return (Criteria) this;
        }

        public Criteria andPay_codeGreaterThanOrEqualTo(String value) {
            addCriterion("pay_code >=", value, "pay_code");
            return (Criteria) this;
        }

        public Criteria andPay_codeLessThan(String value) {
            addCriterion("pay_code <", value, "pay_code");
            return (Criteria) this;
        }

        public Criteria andPay_codeLessThanOrEqualTo(String value) {
            addCriterion("pay_code <=", value, "pay_code");
            return (Criteria) this;
        }

        public Criteria andPay_codeLike(String value) {
            addCriterion("pay_code like", value, "pay_code");
            return (Criteria) this;
        }

        public Criteria andPay_codeNotLike(String value) {
            addCriterion("pay_code not like", value, "pay_code");
            return (Criteria) this;
        }

        public Criteria andPay_codeIn(List<String> values) {
            addCriterion("pay_code in", values, "pay_code");
            return (Criteria) this;
        }

        public Criteria andPay_codeNotIn(List<String> values) {
            addCriterion("pay_code not in", values, "pay_code");
            return (Criteria) this;
        }

        public Criteria andPay_codeBetween(String value1, String value2) {
            addCriterion("pay_code between", value1, value2, "pay_code");
            return (Criteria) this;
        }

        public Criteria andPay_codeNotBetween(String value1, String value2) {
            addCriterion("pay_code not between", value1, value2, "pay_code");
            return (Criteria) this;
        }

        public Criteria andTripartite_numIsNull() {
            addCriterion("tripartite_num is null");
            return (Criteria) this;
        }

        public Criteria andTripartite_numIsNotNull() {
            addCriterion("tripartite_num is not null");
            return (Criteria) this;
        }

        public Criteria andTripartite_numEqualTo(String value) {
            addCriterion("tripartite_num =", value, "tripartite_num");
            return (Criteria) this;
        }

        public Criteria andTripartite_numNotEqualTo(String value) {
            addCriterion("tripartite_num <>", value, "tripartite_num");
            return (Criteria) this;
        }

        public Criteria andTripartite_numGreaterThan(String value) {
            addCriterion("tripartite_num >", value, "tripartite_num");
            return (Criteria) this;
        }

        public Criteria andTripartite_numGreaterThanOrEqualTo(String value) {
            addCriterion("tripartite_num >=", value, "tripartite_num");
            return (Criteria) this;
        }

        public Criteria andTripartite_numLessThan(String value) {
            addCriterion("tripartite_num <", value, "tripartite_num");
            return (Criteria) this;
        }

        public Criteria andTripartite_numLessThanOrEqualTo(String value) {
            addCriterion("tripartite_num <=", value, "tripartite_num");
            return (Criteria) this;
        }

        public Criteria andTripartite_numLike(String value) {
            addCriterion("tripartite_num like", value, "tripartite_num");
            return (Criteria) this;
        }

        public Criteria andTripartite_numNotLike(String value) {
            addCriterion("tripartite_num not like", value, "tripartite_num");
            return (Criteria) this;
        }

        public Criteria andTripartite_numIn(List<String> values) {
            addCriterion("tripartite_num in", values, "tripartite_num");
            return (Criteria) this;
        }

        public Criteria andTripartite_numNotIn(List<String> values) {
            addCriterion("tripartite_num not in", values, "tripartite_num");
            return (Criteria) this;
        }

        public Criteria andTripartite_numBetween(String value1, String value2) {
            addCriterion("tripartite_num between", value1, value2, "tripartite_num");
            return (Criteria) this;
        }

        public Criteria andTripartite_numNotBetween(String value1, String value2) {
            addCriterion("tripartite_num not between", value1, value2, "tripartite_num");
            return (Criteria) this;
        }

        public Criteria andPayment_plat_noIsNull() {
            addCriterion("payment_plat_no is null");
            return (Criteria) this;
        }

        public Criteria andPayment_plat_noIsNotNull() {
            addCriterion("payment_plat_no is not null");
            return (Criteria) this;
        }

        public Criteria andPayment_plat_noEqualTo(String value) {
            addCriterion("payment_plat_no =", value, "payment_plat_no");
            return (Criteria) this;
        }

        public Criteria andPayment_plat_noNotEqualTo(String value) {
            addCriterion("payment_plat_no <>", value, "payment_plat_no");
            return (Criteria) this;
        }

        public Criteria andPayment_plat_noGreaterThan(String value) {
            addCriterion("payment_plat_no >", value, "payment_plat_no");
            return (Criteria) this;
        }

        public Criteria andPayment_plat_noGreaterThanOrEqualTo(String value) {
            addCriterion("payment_plat_no >=", value, "payment_plat_no");
            return (Criteria) this;
        }

        public Criteria andPayment_plat_noLessThan(String value) {
            addCriterion("payment_plat_no <", value, "payment_plat_no");
            return (Criteria) this;
        }

        public Criteria andPayment_plat_noLessThanOrEqualTo(String value) {
            addCriterion("payment_plat_no <=", value, "payment_plat_no");
            return (Criteria) this;
        }

        public Criteria andPayment_plat_noLike(String value) {
            addCriterion("payment_plat_no like", value, "payment_plat_no");
            return (Criteria) this;
        }

        public Criteria andPayment_plat_noNotLike(String value) {
            addCriterion("payment_plat_no not like", value, "payment_plat_no");
            return (Criteria) this;
        }

        public Criteria andPayment_plat_noIn(List<String> values) {
            addCriterion("payment_plat_no in", values, "payment_plat_no");
            return (Criteria) this;
        }

        public Criteria andPayment_plat_noNotIn(List<String> values) {
            addCriterion("payment_plat_no not in", values, "payment_plat_no");
            return (Criteria) this;
        }

        public Criteria andPayment_plat_noBetween(String value1, String value2) {
            addCriterion("payment_plat_no between", value1, value2, "payment_plat_no");
            return (Criteria) this;
        }

        public Criteria andPayment_plat_noNotBetween(String value1, String value2) {
            addCriterion("payment_plat_no not between", value1, value2, "payment_plat_no");
            return (Criteria) this;
        }

        public Criteria andPayment_pay_codeIsNull() {
            addCriterion("payment_pay_code is null");
            return (Criteria) this;
        }

        public Criteria andPayment_pay_codeIsNotNull() {
            addCriterion("payment_pay_code is not null");
            return (Criteria) this;
        }

        public Criteria andPayment_pay_codeEqualTo(String value) {
            addCriterion("payment_pay_code =", value, "payment_pay_code");
            return (Criteria) this;
        }

        public Criteria andPayment_pay_codeNotEqualTo(String value) {
            addCriterion("payment_pay_code <>", value, "payment_pay_code");
            return (Criteria) this;
        }

        public Criteria andPayment_pay_codeGreaterThan(String value) {
            addCriterion("payment_pay_code >", value, "payment_pay_code");
            return (Criteria) this;
        }

        public Criteria andPayment_pay_codeGreaterThanOrEqualTo(String value) {
            addCriterion("payment_pay_code >=", value, "payment_pay_code");
            return (Criteria) this;
        }

        public Criteria andPayment_pay_codeLessThan(String value) {
            addCriterion("payment_pay_code <", value, "payment_pay_code");
            return (Criteria) this;
        }

        public Criteria andPayment_pay_codeLessThanOrEqualTo(String value) {
            addCriterion("payment_pay_code <=", value, "payment_pay_code");
            return (Criteria) this;
        }

        public Criteria andPayment_pay_codeLike(String value) {
            addCriterion("payment_pay_code like", value, "payment_pay_code");
            return (Criteria) this;
        }

        public Criteria andPayment_pay_codeNotLike(String value) {
            addCriterion("payment_pay_code not like", value, "payment_pay_code");
            return (Criteria) this;
        }

        public Criteria andPayment_pay_codeIn(List<String> values) {
            addCriterion("payment_pay_code in", values, "payment_pay_code");
            return (Criteria) this;
        }

        public Criteria andPayment_pay_codeNotIn(List<String> values) {
            addCriterion("payment_pay_code not in", values, "payment_pay_code");
            return (Criteria) this;
        }

        public Criteria andPayment_pay_codeBetween(String value1, String value2) {
            addCriterion("payment_pay_code between", value1, value2, "payment_pay_code");
            return (Criteria) this;
        }

        public Criteria andPayment_pay_codeNotBetween(String value1, String value2) {
            addCriterion("payment_pay_code not between", value1, value2, "payment_pay_code");
            return (Criteria) this;
        }
    }

    /**
     * clear_checkinfo_emxlist
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * clear_checkinfo_emxlist 2017-07-04
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