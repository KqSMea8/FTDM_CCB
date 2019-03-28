package com.sunyard.sunfintech.dao.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProdProductinfoExtExample {
    /**
     * prod_productinfo_ext
     */
    protected String orderByClause;

    /**
     * prod_productinfo_ext
     */
    protected boolean distinct;

    /**
     * prod_productinfo_ext
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2017-05-31
     */
    public ProdProductinfoExtExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *
     * @mbggenerated 2017-05-31
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *
     * @mbggenerated 2017-05-31
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *
     * @mbggenerated 2017-05-31
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *
     * @mbggenerated 2017-05-31
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *
     * @mbggenerated 2017-05-31
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *
     * @mbggenerated 2017-05-31
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *
     * @mbggenerated 2017-05-31
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *
     * @mbggenerated 2017-05-31
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
     * @mbggenerated 2017-05-31
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *
     * @mbggenerated 2017-05-31
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * prod_productinfo_ext 2017-05-31
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

        public Criteria andRisk_lvlIsNull() {
            addCriterion("risk_lvl is null");
            return (Criteria) this;
        }

        public Criteria andRisk_lvlIsNotNull() {
            addCriterion("risk_lvl is not null");
            return (Criteria) this;
        }

        public Criteria andRisk_lvlEqualTo(String value) {
            addCriterion("risk_lvl =", value, "risk_lvl");
            return (Criteria) this;
        }

        public Criteria andRisk_lvlNotEqualTo(String value) {
            addCriterion("risk_lvl <>", value, "risk_lvl");
            return (Criteria) this;
        }

        public Criteria andRisk_lvlGreaterThan(String value) {
            addCriterion("risk_lvl >", value, "risk_lvl");
            return (Criteria) this;
        }

        public Criteria andRisk_lvlGreaterThanOrEqualTo(String value) {
            addCriterion("risk_lvl >=", value, "risk_lvl");
            return (Criteria) this;
        }

        public Criteria andRisk_lvlLessThan(String value) {
            addCriterion("risk_lvl <", value, "risk_lvl");
            return (Criteria) this;
        }

        public Criteria andRisk_lvlLessThanOrEqualTo(String value) {
            addCriterion("risk_lvl <=", value, "risk_lvl");
            return (Criteria) this;
        }

        public Criteria andRisk_lvlLike(String value) {
            addCriterion("risk_lvl like", value, "risk_lvl");
            return (Criteria) this;
        }

        public Criteria andRisk_lvlNotLike(String value) {
            addCriterion("risk_lvl not like", value, "risk_lvl");
            return (Criteria) this;
        }

        public Criteria andRisk_lvlIn(List<String> values) {
            addCriterion("risk_lvl in", values, "risk_lvl");
            return (Criteria) this;
        }

        public Criteria andRisk_lvlNotIn(List<String> values) {
            addCriterion("risk_lvl not in", values, "risk_lvl");
            return (Criteria) this;
        }

        public Criteria andRisk_lvlBetween(String value1, String value2) {
            addCriterion("risk_lvl between", value1, value2, "risk_lvl");
            return (Criteria) this;
        }

        public Criteria andRisk_lvlNotBetween(String value1, String value2) {
            addCriterion("risk_lvl not between", value1, value2, "risk_lvl");
            return (Criteria) this;
        }

        public Criteria andProd_infoIsNull() {
            addCriterion("prod_info is null");
            return (Criteria) this;
        }

        public Criteria andProd_infoIsNotNull() {
            addCriterion("prod_info is not null");
            return (Criteria) this;
        }

        public Criteria andProd_infoEqualTo(String value) {
            addCriterion("prod_info =", value, "prod_info");
            return (Criteria) this;
        }

        public Criteria andProd_infoNotEqualTo(String value) {
            addCriterion("prod_info <>", value, "prod_info");
            return (Criteria) this;
        }

        public Criteria andProd_infoGreaterThan(String value) {
            addCriterion("prod_info >", value, "prod_info");
            return (Criteria) this;
        }

        public Criteria andProd_infoGreaterThanOrEqualTo(String value) {
            addCriterion("prod_info >=", value, "prod_info");
            return (Criteria) this;
        }

        public Criteria andProd_infoLessThan(String value) {
            addCriterion("prod_info <", value, "prod_info");
            return (Criteria) this;
        }

        public Criteria andProd_infoLessThanOrEqualTo(String value) {
            addCriterion("prod_info <=", value, "prod_info");
            return (Criteria) this;
        }

        public Criteria andProd_infoLike(String value) {
            addCriterion("prod_info like", value, "prod_info");
            return (Criteria) this;
        }

        public Criteria andProd_infoNotLike(String value) {
            addCriterion("prod_info not like", value, "prod_info");
            return (Criteria) this;
        }

        public Criteria andProd_infoIn(List<String> values) {
            addCriterion("prod_info in", values, "prod_info");
            return (Criteria) this;
        }

        public Criteria andProd_infoNotIn(List<String> values) {
            addCriterion("prod_info not in", values, "prod_info");
            return (Criteria) this;
        }

        public Criteria andProd_infoBetween(String value1, String value2) {
            addCriterion("prod_info between", value1, value2, "prod_info");
            return (Criteria) this;
        }

        public Criteria andProd_infoNotBetween(String value1, String value2) {
            addCriterion("prod_info not between", value1, value2, "prod_info");
            return (Criteria) this;
        }

        public Criteria andIs_advanceIsNull() {
            addCriterion("is_advance is null");
            return (Criteria) this;
        }

        public Criteria andIs_advanceIsNotNull() {
            addCriterion("is_advance is not null");
            return (Criteria) this;
        }

        public Criteria andIs_advanceEqualTo(String value) {
            addCriterion("is_advance =", value, "is_advance");
            return (Criteria) this;
        }

        public Criteria andIs_advanceNotEqualTo(String value) {
            addCriterion("is_advance <>", value, "is_advance");
            return (Criteria) this;
        }

        public Criteria andIs_advanceGreaterThan(String value) {
            addCriterion("is_advance >", value, "is_advance");
            return (Criteria) this;
        }

        public Criteria andIs_advanceGreaterThanOrEqualTo(String value) {
            addCriterion("is_advance >=", value, "is_advance");
            return (Criteria) this;
        }

        public Criteria andIs_advanceLessThan(String value) {
            addCriterion("is_advance <", value, "is_advance");
            return (Criteria) this;
        }

        public Criteria andIs_advanceLessThanOrEqualTo(String value) {
            addCriterion("is_advance <=", value, "is_advance");
            return (Criteria) this;
        }

        public Criteria andIs_advanceLike(String value) {
            addCriterion("is_advance like", value, "is_advance");
            return (Criteria) this;
        }

        public Criteria andIs_advanceNotLike(String value) {
            addCriterion("is_advance not like", value, "is_advance");
            return (Criteria) this;
        }

        public Criteria andIs_advanceIn(List<String> values) {
            addCriterion("is_advance in", values, "is_advance");
            return (Criteria) this;
        }

        public Criteria andIs_advanceNotIn(List<String> values) {
            addCriterion("is_advance not in", values, "is_advance");
            return (Criteria) this;
        }

        public Criteria andIs_advanceBetween(String value1, String value2) {
            addCriterion("is_advance between", value1, value2, "is_advance");
            return (Criteria) this;
        }

        public Criteria andIs_advanceNotBetween(String value1, String value2) {
            addCriterion("is_advance not between", value1, value2, "is_advance");
            return (Criteria) this;
        }

        public Criteria andPayout_allratioIsNull() {
            addCriterion("payout_allratio is null");
            return (Criteria) this;
        }

        public Criteria andPayout_allratioIsNotNull() {
            addCriterion("payout_allratio is not null");
            return (Criteria) this;
        }

        public Criteria andPayout_allratioEqualTo(BigDecimal value) {
            addCriterion("payout_allratio =", value, "payout_allratio");
            return (Criteria) this;
        }

        public Criteria andPayout_allratioNotEqualTo(BigDecimal value) {
            addCriterion("payout_allratio <>", value, "payout_allratio");
            return (Criteria) this;
        }

        public Criteria andPayout_allratioGreaterThan(BigDecimal value) {
            addCriterion("payout_allratio >", value, "payout_allratio");
            return (Criteria) this;
        }

        public Criteria andPayout_allratioGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("payout_allratio >=", value, "payout_allratio");
            return (Criteria) this;
        }

        public Criteria andPayout_allratioLessThan(BigDecimal value) {
            addCriterion("payout_allratio <", value, "payout_allratio");
            return (Criteria) this;
        }

        public Criteria andPayout_allratioLessThanOrEqualTo(BigDecimal value) {
            addCriterion("payout_allratio <=", value, "payout_allratio");
            return (Criteria) this;
        }

        public Criteria andPayout_allratioIn(List<BigDecimal> values) {
            addCriterion("payout_allratio in", values, "payout_allratio");
            return (Criteria) this;
        }

        public Criteria andPayout_allratioNotIn(List<BigDecimal> values) {
            addCriterion("payout_allratio not in", values, "payout_allratio");
            return (Criteria) this;
        }

        public Criteria andPayout_allratioBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("payout_allratio between", value1, value2, "payout_allratio");
            return (Criteria) this;
        }

        public Criteria andPayout_allratioNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("payout_allratio not between", value1, value2, "payout_allratio");
            return (Criteria) this;
        }

        public Criteria andBuyer_num_limitIsNull() {
            addCriterion("buyer_num_limit is null");
            return (Criteria) this;
        }

        public Criteria andBuyer_num_limitIsNotNull() {
            addCriterion("buyer_num_limit is not null");
            return (Criteria) this;
        }

        public Criteria andBuyer_num_limitEqualTo(Integer value) {
            addCriterion("buyer_num_limit =", value, "buyer_num_limit");
            return (Criteria) this;
        }

        public Criteria andBuyer_num_limitNotEqualTo(Integer value) {
            addCriterion("buyer_num_limit <>", value, "buyer_num_limit");
            return (Criteria) this;
        }

        public Criteria andBuyer_num_limitGreaterThan(Integer value) {
            addCriterion("buyer_num_limit >", value, "buyer_num_limit");
            return (Criteria) this;
        }

        public Criteria andBuyer_num_limitGreaterThanOrEqualTo(Integer value) {
            addCriterion("buyer_num_limit >=", value, "buyer_num_limit");
            return (Criteria) this;
        }

        public Criteria andBuyer_num_limitLessThan(Integer value) {
            addCriterion("buyer_num_limit <", value, "buyer_num_limit");
            return (Criteria) this;
        }

        public Criteria andBuyer_num_limitLessThanOrEqualTo(Integer value) {
            addCriterion("buyer_num_limit <=", value, "buyer_num_limit");
            return (Criteria) this;
        }

        public Criteria andBuyer_num_limitIn(List<Integer> values) {
            addCriterion("buyer_num_limit in", values, "buyer_num_limit");
            return (Criteria) this;
        }

        public Criteria andBuyer_num_limitNotIn(List<Integer> values) {
            addCriterion("buyer_num_limit not in", values, "buyer_num_limit");
            return (Criteria) this;
        }

        public Criteria andBuyer_num_limitBetween(Integer value1, Integer value2) {
            addCriterion("buyer_num_limit between", value1, value2, "buyer_num_limit");
            return (Criteria) this;
        }

        public Criteria andBuyer_num_limitNotBetween(Integer value1, Integer value2) {
            addCriterion("buyer_num_limit not between", value1, value2, "buyer_num_limit");
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
     * prod_productinfo_ext
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * prod_productinfo_ext 2017-05-31
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