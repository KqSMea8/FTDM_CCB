package com.sunyard.sunfintech.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlatPaycodeExample {
    /**
     * plat_paycode
     */
    protected String orderByClause;

    /**
     * plat_paycode
     */
    protected boolean distinct;

    /**
     * plat_paycode
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2018-07-02
     */
    public PlatPaycodeExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *
     * @mbggenerated 2018-07-02
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *
     * @mbggenerated 2018-07-02
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *
     * @mbggenerated 2018-07-02
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *
     * @mbggenerated 2018-07-02
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *
     * @mbggenerated 2018-07-02
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *
     * @mbggenerated 2018-07-02
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *
     * @mbggenerated 2018-07-02
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *
     * @mbggenerated 2018-07-02
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
     * @mbggenerated 2018-07-02
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *
     * @mbggenerated 2018-07-02
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * plat_paycode 2018-07-02
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

        public Criteria andPay_code_nameIsNull() {
            addCriterion("pay_code_name is null");
            return (Criteria) this;
        }

        public Criteria andPay_code_nameIsNotNull() {
            addCriterion("pay_code_name is not null");
            return (Criteria) this;
        }

        public Criteria andPay_code_nameEqualTo(String value) {
            addCriterion("pay_code_name =", value, "pay_code_name");
            return (Criteria) this;
        }

        public Criteria andPay_code_nameNotEqualTo(String value) {
            addCriterion("pay_code_name <>", value, "pay_code_name");
            return (Criteria) this;
        }

        public Criteria andPay_code_nameGreaterThan(String value) {
            addCriterion("pay_code_name >", value, "pay_code_name");
            return (Criteria) this;
        }

        public Criteria andPay_code_nameGreaterThanOrEqualTo(String value) {
            addCriterion("pay_code_name >=", value, "pay_code_name");
            return (Criteria) this;
        }

        public Criteria andPay_code_nameLessThan(String value) {
            addCriterion("pay_code_name <", value, "pay_code_name");
            return (Criteria) this;
        }

        public Criteria andPay_code_nameLessThanOrEqualTo(String value) {
            addCriterion("pay_code_name <=", value, "pay_code_name");
            return (Criteria) this;
        }

        public Criteria andPay_code_nameLike(String value) {
            addCriterion("pay_code_name like", value, "pay_code_name");
            return (Criteria) this;
        }

        public Criteria andPay_code_nameNotLike(String value) {
            addCriterion("pay_code_name not like", value, "pay_code_name");
            return (Criteria) this;
        }

        public Criteria andPay_code_nameIn(List<String> values) {
            addCriterion("pay_code_name in", values, "pay_code_name");
            return (Criteria) this;
        }

        public Criteria andPay_code_nameNotIn(List<String> values) {
            addCriterion("pay_code_name not in", values, "pay_code_name");
            return (Criteria) this;
        }

        public Criteria andPay_code_nameBetween(String value1, String value2) {
            addCriterion("pay_code_name between", value1, value2, "pay_code_name");
            return (Criteria) this;
        }

        public Criteria andPay_code_nameNotBetween(String value1, String value2) {
            addCriterion("pay_code_name not between", value1, value2, "pay_code_name");
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

        public Criteria andChannel_idIsNull() {
            addCriterion("channel_id is null");
            return (Criteria) this;
        }

        public Criteria andChannel_idIsNotNull() {
            addCriterion("channel_id is not null");
            return (Criteria) this;
        }

        public Criteria andChannel_idEqualTo(String value) {
            addCriterion("channel_id =", value, "channel_id");
            return (Criteria) this;
        }

        public Criteria andChannel_idNotEqualTo(String value) {
            addCriterion("channel_id <>", value, "channel_id");
            return (Criteria) this;
        }

        public Criteria andChannel_idGreaterThan(String value) {
            addCriterion("channel_id >", value, "channel_id");
            return (Criteria) this;
        }

        public Criteria andChannel_idGreaterThanOrEqualTo(String value) {
            addCriterion("channel_id >=", value, "channel_id");
            return (Criteria) this;
        }

        public Criteria andChannel_idLessThan(String value) {
            addCriterion("channel_id <", value, "channel_id");
            return (Criteria) this;
        }

        public Criteria andChannel_idLessThanOrEqualTo(String value) {
            addCriterion("channel_id <=", value, "channel_id");
            return (Criteria) this;
        }

        public Criteria andChannel_idLike(String value) {
            addCriterion("channel_id like", value, "channel_id");
            return (Criteria) this;
        }

        public Criteria andChannel_idNotLike(String value) {
            addCriterion("channel_id not like", value, "channel_id");
            return (Criteria) this;
        }

        public Criteria andChannel_idIn(List<String> values) {
            addCriterion("channel_id in", values, "channel_id");
            return (Criteria) this;
        }

        public Criteria andChannel_idNotIn(List<String> values) {
            addCriterion("channel_id not in", values, "channel_id");
            return (Criteria) this;
        }

        public Criteria andChannel_idBetween(String value1, String value2) {
            addCriterion("channel_id between", value1, value2, "channel_id");
            return (Criteria) this;
        }

        public Criteria andChannel_idNotBetween(String value1, String value2) {
            addCriterion("channel_id not between", value1, value2, "channel_id");
            return (Criteria) this;
        }

        public Criteria andIs_transactionIsNull() {
            addCriterion("is_transaction is null");
            return (Criteria) this;
        }

        public Criteria andIs_transactionIsNotNull() {
            addCriterion("is_transaction is not null");
            return (Criteria) this;
        }

        public Criteria andIs_transactionEqualTo(String value) {
            addCriterion("is_transaction =", value, "is_transaction");
            return (Criteria) this;
        }

        public Criteria andIs_transactionNotEqualTo(String value) {
            addCriterion("is_transaction <>", value, "is_transaction");
            return (Criteria) this;
        }

        public Criteria andIs_transactionGreaterThan(String value) {
            addCriterion("is_transaction >", value, "is_transaction");
            return (Criteria) this;
        }

        public Criteria andIs_transactionGreaterThanOrEqualTo(String value) {
            addCriterion("is_transaction >=", value, "is_transaction");
            return (Criteria) this;
        }

        public Criteria andIs_transactionLessThan(String value) {
            addCriterion("is_transaction <", value, "is_transaction");
            return (Criteria) this;
        }

        public Criteria andIs_transactionLessThanOrEqualTo(String value) {
            addCriterion("is_transaction <=", value, "is_transaction");
            return (Criteria) this;
        }

        public Criteria andIs_transactionLike(String value) {
            addCriterion("is_transaction like", value, "is_transaction");
            return (Criteria) this;
        }

        public Criteria andIs_transactionNotLike(String value) {
            addCriterion("is_transaction not like", value, "is_transaction");
            return (Criteria) this;
        }

        public Criteria andIs_transactionIn(List<String> values) {
            addCriterion("is_transaction in", values, "is_transaction");
            return (Criteria) this;
        }

        public Criteria andIs_transactionNotIn(List<String> values) {
            addCriterion("is_transaction not in", values, "is_transaction");
            return (Criteria) this;
        }

        public Criteria andIs_transactionBetween(String value1, String value2) {
            addCriterion("is_transaction between", value1, value2, "is_transaction");
            return (Criteria) this;
        }

        public Criteria andIs_transactionNotBetween(String value1, String value2) {
            addCriterion("is_transaction not between", value1, value2, "is_transaction");
            return (Criteria) this;
        }

        public Criteria andExt_2IsNull() {
            addCriterion("ext_2 is null");
            return (Criteria) this;
        }

        public Criteria andExt_2IsNotNull() {
            addCriterion("ext_2 is not null");
            return (Criteria) this;
        }

        public Criteria andExt_2EqualTo(String value) {
            addCriterion("ext_2 =", value, "ext_2");
            return (Criteria) this;
        }

        public Criteria andExt_2NotEqualTo(String value) {
            addCriterion("ext_2 <>", value, "ext_2");
            return (Criteria) this;
        }

        public Criteria andExt_2GreaterThan(String value) {
            addCriterion("ext_2 >", value, "ext_2");
            return (Criteria) this;
        }

        public Criteria andExt_2GreaterThanOrEqualTo(String value) {
            addCriterion("ext_2 >=", value, "ext_2");
            return (Criteria) this;
        }

        public Criteria andExt_2LessThan(String value) {
            addCriterion("ext_2 <", value, "ext_2");
            return (Criteria) this;
        }

        public Criteria andExt_2LessThanOrEqualTo(String value) {
            addCriterion("ext_2 <=", value, "ext_2");
            return (Criteria) this;
        }

        public Criteria andExt_2Like(String value) {
            addCriterion("ext_2 like", value, "ext_2");
            return (Criteria) this;
        }

        public Criteria andExt_2NotLike(String value) {
            addCriterion("ext_2 not like", value, "ext_2");
            return (Criteria) this;
        }

        public Criteria andExt_2In(List<String> values) {
            addCriterion("ext_2 in", values, "ext_2");
            return (Criteria) this;
        }

        public Criteria andExt_2NotIn(List<String> values) {
            addCriterion("ext_2 not in", values, "ext_2");
            return (Criteria) this;
        }

        public Criteria andExt_2Between(String value1, String value2) {
            addCriterion("ext_2 between", value1, value2, "ext_2");
            return (Criteria) this;
        }

        public Criteria andExt_2NotBetween(String value1, String value2) {
            addCriterion("ext_2 not between", value1, value2, "ext_2");
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

        public Criteria andIs_bankcheckIsNull() {
            addCriterion("is_bankcheck is null");
            return (Criteria) this;
        }

        public Criteria andIs_bankcheckIsNotNull() {
            addCriterion("is_bankcheck is not null");
            return (Criteria) this;
        }

        public Criteria andIs_bankcheckEqualTo(String value) {
            addCriterion("is_bankcheck =", value, "is_bankcheck");
            return (Criteria) this;
        }

        public Criteria andIs_bankcheckNotEqualTo(String value) {
            addCriterion("is_bankcheck <>", value, "is_bankcheck");
            return (Criteria) this;
        }

        public Criteria andIs_bankcheckGreaterThan(String value) {
            addCriterion("is_bankcheck >", value, "is_bankcheck");
            return (Criteria) this;
        }

        public Criteria andIs_bankcheckGreaterThanOrEqualTo(String value) {
            addCriterion("is_bankcheck >=", value, "is_bankcheck");
            return (Criteria) this;
        }

        public Criteria andIs_bankcheckLessThan(String value) {
            addCriterion("is_bankcheck <", value, "is_bankcheck");
            return (Criteria) this;
        }

        public Criteria andIs_bankcheckLessThanOrEqualTo(String value) {
            addCriterion("is_bankcheck <=", value, "is_bankcheck");
            return (Criteria) this;
        }

        public Criteria andIs_bankcheckLike(String value) {
            addCriterion("is_bankcheck like", value, "is_bankcheck");
            return (Criteria) this;
        }

        public Criteria andIs_bankcheckNotLike(String value) {
            addCriterion("is_bankcheck not like", value, "is_bankcheck");
            return (Criteria) this;
        }

        public Criteria andIs_bankcheckIn(List<String> values) {
            addCriterion("is_bankcheck in", values, "is_bankcheck");
            return (Criteria) this;
        }

        public Criteria andIs_bankcheckNotIn(List<String> values) {
            addCriterion("is_bankcheck not in", values, "is_bankcheck");
            return (Criteria) this;
        }

        public Criteria andIs_bankcheckBetween(String value1, String value2) {
            addCriterion("is_bankcheck between", value1, value2, "is_bankcheck");
            return (Criteria) this;
        }

        public Criteria andIs_bankcheckNotBetween(String value1, String value2) {
            addCriterion("is_bankcheck not between", value1, value2, "is_bankcheck");
            return (Criteria) this;
        }

        public Criteria andIs_msgcheckIsNull() {
            addCriterion("is_msgcheck is null");
            return (Criteria) this;
        }

        public Criteria andIs_msgcheckIsNotNull() {
            addCriterion("is_msgcheck is not null");
            return (Criteria) this;
        }

        public Criteria andIs_msgcheckEqualTo(String value) {
            addCriterion("is_msgcheck =", value, "is_msgcheck");
            return (Criteria) this;
        }

        public Criteria andIs_msgcheckNotEqualTo(String value) {
            addCriterion("is_msgcheck <>", value, "is_msgcheck");
            return (Criteria) this;
        }

        public Criteria andIs_msgcheckGreaterThan(String value) {
            addCriterion("is_msgcheck >", value, "is_msgcheck");
            return (Criteria) this;
        }

        public Criteria andIs_msgcheckGreaterThanOrEqualTo(String value) {
            addCriterion("is_msgcheck >=", value, "is_msgcheck");
            return (Criteria) this;
        }

        public Criteria andIs_msgcheckLessThan(String value) {
            addCriterion("is_msgcheck <", value, "is_msgcheck");
            return (Criteria) this;
        }

        public Criteria andIs_msgcheckLessThanOrEqualTo(String value) {
            addCriterion("is_msgcheck <=", value, "is_msgcheck");
            return (Criteria) this;
        }

        public Criteria andIs_msgcheckLike(String value) {
            addCriterion("is_msgcheck like", value, "is_msgcheck");
            return (Criteria) this;
        }

        public Criteria andIs_msgcheckNotLike(String value) {
            addCriterion("is_msgcheck not like", value, "is_msgcheck");
            return (Criteria) this;
        }

        public Criteria andIs_msgcheckIn(List<String> values) {
            addCriterion("is_msgcheck in", values, "is_msgcheck");
            return (Criteria) this;
        }

        public Criteria andIs_msgcheckNotIn(List<String> values) {
            addCriterion("is_msgcheck not in", values, "is_msgcheck");
            return (Criteria) this;
        }

        public Criteria andIs_msgcheckBetween(String value1, String value2) {
            addCriterion("is_msgcheck between", value1, value2, "is_msgcheck");
            return (Criteria) this;
        }

        public Criteria andIs_msgcheckNotBetween(String value1, String value2) {
            addCriterion("is_msgcheck not between", value1, value2, "is_msgcheck");
            return (Criteria) this;
        }

        public Criteria andAuto_clearIsNull() {
            addCriterion("auto_clear is null");
            return (Criteria) this;
        }

        public Criteria andAuto_clearIsNotNull() {
            addCriterion("auto_clear is not null");
            return (Criteria) this;
        }

        public Criteria andAuto_clearEqualTo(String value) {
            addCriterion("auto_clear =", value, "auto_clear");
            return (Criteria) this;
        }

        public Criteria andAuto_clearNotEqualTo(String value) {
            addCriterion("auto_clear <>", value, "auto_clear");
            return (Criteria) this;
        }

        public Criteria andAuto_clearGreaterThan(String value) {
            addCriterion("auto_clear >", value, "auto_clear");
            return (Criteria) this;
        }

        public Criteria andAuto_clearGreaterThanOrEqualTo(String value) {
            addCriterion("auto_clear >=", value, "auto_clear");
            return (Criteria) this;
        }

        public Criteria andAuto_clearLessThan(String value) {
            addCriterion("auto_clear <", value, "auto_clear");
            return (Criteria) this;
        }

        public Criteria andAuto_clearLessThanOrEqualTo(String value) {
            addCriterion("auto_clear <=", value, "auto_clear");
            return (Criteria) this;
        }

        public Criteria andAuto_clearLike(String value) {
            addCriterion("auto_clear like", value, "auto_clear");
            return (Criteria) this;
        }

        public Criteria andAuto_clearNotLike(String value) {
            addCriterion("auto_clear not like", value, "auto_clear");
            return (Criteria) this;
        }

        public Criteria andAuto_clearIn(List<String> values) {
            addCriterion("auto_clear in", values, "auto_clear");
            return (Criteria) this;
        }

        public Criteria andAuto_clearNotIn(List<String> values) {
            addCriterion("auto_clear not in", values, "auto_clear");
            return (Criteria) this;
        }

        public Criteria andAuto_clearBetween(String value1, String value2) {
            addCriterion("auto_clear between", value1, value2, "auto_clear");
            return (Criteria) this;
        }

        public Criteria andAuto_clearNotBetween(String value1, String value2) {
            addCriterion("auto_clear not between", value1, value2, "auto_clear");
            return (Criteria) this;
        }

        public Criteria andClear_card_noIsNull() {
            addCriterion("clear_card_no is null");
            return (Criteria) this;
        }

        public Criteria andClear_card_noIsNotNull() {
            addCriterion("clear_card_no is not null");
            return (Criteria) this;
        }

        public Criteria andClear_card_noEqualTo(String value) {
            addCriterion("clear_card_no =", value, "clear_card_no");
            return (Criteria) this;
        }

        public Criteria andClear_card_noNotEqualTo(String value) {
            addCriterion("clear_card_no <>", value, "clear_card_no");
            return (Criteria) this;
        }

        public Criteria andClear_card_noGreaterThan(String value) {
            addCriterion("clear_card_no >", value, "clear_card_no");
            return (Criteria) this;
        }

        public Criteria andClear_card_noGreaterThanOrEqualTo(String value) {
            addCriterion("clear_card_no >=", value, "clear_card_no");
            return (Criteria) this;
        }

        public Criteria andClear_card_noLessThan(String value) {
            addCriterion("clear_card_no <", value, "clear_card_no");
            return (Criteria) this;
        }

        public Criteria andClear_card_noLessThanOrEqualTo(String value) {
            addCriterion("clear_card_no <=", value, "clear_card_no");
            return (Criteria) this;
        }

        public Criteria andClear_card_noLike(String value) {
            addCriterion("clear_card_no like", value, "clear_card_no");
            return (Criteria) this;
        }

        public Criteria andClear_card_noNotLike(String value) {
            addCriterion("clear_card_no not like", value, "clear_card_no");
            return (Criteria) this;
        }

        public Criteria andClear_card_noIn(List<String> values) {
            addCriterion("clear_card_no in", values, "clear_card_no");
            return (Criteria) this;
        }

        public Criteria andClear_card_noNotIn(List<String> values) {
            addCriterion("clear_card_no not in", values, "clear_card_no");
            return (Criteria) this;
        }

        public Criteria andClear_card_noBetween(String value1, String value2) {
            addCriterion("clear_card_no between", value1, value2, "clear_card_no");
            return (Criteria) this;
        }

        public Criteria andClear_card_noNotBetween(String value1, String value2) {
            addCriterion("clear_card_no not between", value1, value2, "clear_card_no");
            return (Criteria) this;
        }
    }

    /**
     * plat_paycode
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * plat_paycode 2018-07-02
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