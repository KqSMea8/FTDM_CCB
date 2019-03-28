package com.sunyard.sunfintech.dao.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EaccTransTransreqExample {
    /**
     * eacc_trans_transreq
     */
    protected String orderByClause;

    /**
     * eacc_trans_transreq
     */
    protected boolean distinct;

    /**
     * eacc_trans_transreq
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2018-02-04
     */
    public EaccTransTransreqExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *
     * @mbggenerated 2018-02-04
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *
     * @mbggenerated 2018-02-04
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *
     * @mbggenerated 2018-02-04
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *
     * @mbggenerated 2018-02-04
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *
     * @mbggenerated 2018-02-04
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *
     * @mbggenerated 2018-02-04
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *
     * @mbggenerated 2018-02-04
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *
     * @mbggenerated 2018-02-04
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
     * @mbggenerated 2018-02-04
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *
     * @mbggenerated 2018-02-04
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * eacc_trans_transreq 2018-02-04
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

        public Criteria andParent_trans_serialIsNull() {
            addCriterion("parent_trans_serial is null");
            return (Criteria) this;
        }

        public Criteria andParent_trans_serialIsNotNull() {
            addCriterion("parent_trans_serial is not null");
            return (Criteria) this;
        }

        public Criteria andParent_trans_serialEqualTo(String value) {
            addCriterion("parent_trans_serial =", value, "parent_trans_serial");
            return (Criteria) this;
        }

        public Criteria andParent_trans_serialNotEqualTo(String value) {
            addCriterion("parent_trans_serial <>", value, "parent_trans_serial");
            return (Criteria) this;
        }

        public Criteria andParent_trans_serialGreaterThan(String value) {
            addCriterion("parent_trans_serial >", value, "parent_trans_serial");
            return (Criteria) this;
        }

        public Criteria andParent_trans_serialGreaterThanOrEqualTo(String value) {
            addCriterion("parent_trans_serial >=", value, "parent_trans_serial");
            return (Criteria) this;
        }

        public Criteria andParent_trans_serialLessThan(String value) {
            addCriterion("parent_trans_serial <", value, "parent_trans_serial");
            return (Criteria) this;
        }

        public Criteria andParent_trans_serialLessThanOrEqualTo(String value) {
            addCriterion("parent_trans_serial <=", value, "parent_trans_serial");
            return (Criteria) this;
        }

        public Criteria andParent_trans_serialLike(String value) {
            addCriterion("parent_trans_serial like", value, "parent_trans_serial");
            return (Criteria) this;
        }

        public Criteria andParent_trans_serialNotLike(String value) {
            addCriterion("parent_trans_serial not like", value, "parent_trans_serial");
            return (Criteria) this;
        }

        public Criteria andParent_trans_serialIn(List<String> values) {
            addCriterion("parent_trans_serial in", values, "parent_trans_serial");
            return (Criteria) this;
        }

        public Criteria andParent_trans_serialNotIn(List<String> values) {
            addCriterion("parent_trans_serial not in", values, "parent_trans_serial");
            return (Criteria) this;
        }

        public Criteria andParent_trans_serialBetween(String value1, String value2) {
            addCriterion("parent_trans_serial between", value1, value2, "parent_trans_serial");
            return (Criteria) this;
        }

        public Criteria andParent_trans_serialNotBetween(String value1, String value2) {
            addCriterion("parent_trans_serial not between", value1, value2, "parent_trans_serial");
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

        public Criteria andEaccountIsNull() {
            addCriterion("eaccount is null");
            return (Criteria) this;
        }

        public Criteria andEaccountIsNotNull() {
            addCriterion("eaccount is not null");
            return (Criteria) this;
        }

        public Criteria andEaccountEqualTo(String value) {
            addCriterion("eaccount =", value, "eaccount");
            return (Criteria) this;
        }

        public Criteria andEaccountNotEqualTo(String value) {
            addCriterion("eaccount <>", value, "eaccount");
            return (Criteria) this;
        }

        public Criteria andEaccountGreaterThan(String value) {
            addCriterion("eaccount >", value, "eaccount");
            return (Criteria) this;
        }

        public Criteria andEaccountGreaterThanOrEqualTo(String value) {
            addCriterion("eaccount >=", value, "eaccount");
            return (Criteria) this;
        }

        public Criteria andEaccountLessThan(String value) {
            addCriterion("eaccount <", value, "eaccount");
            return (Criteria) this;
        }

        public Criteria andEaccountLessThanOrEqualTo(String value) {
            addCriterion("eaccount <=", value, "eaccount");
            return (Criteria) this;
        }

        public Criteria andEaccountLike(String value) {
            addCriterion("eaccount like", value, "eaccount");
            return (Criteria) this;
        }

        public Criteria andEaccountNotLike(String value) {
            addCriterion("eaccount not like", value, "eaccount");
            return (Criteria) this;
        }

        public Criteria andEaccountIn(List<String> values) {
            addCriterion("eaccount in", values, "eaccount");
            return (Criteria) this;
        }

        public Criteria andEaccountNotIn(List<String> values) {
            addCriterion("eaccount not in", values, "eaccount");
            return (Criteria) this;
        }

        public Criteria andEaccountBetween(String value1, String value2) {
            addCriterion("eaccount between", value1, value2, "eaccount");
            return (Criteria) this;
        }

        public Criteria andEaccountNotBetween(String value1, String value2) {
            addCriterion("eaccount not between", value1, value2, "eaccount");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andPropertyIsNull() {
            addCriterion("property is null");
            return (Criteria) this;
        }

        public Criteria andPropertyIsNotNull() {
            addCriterion("property is not null");
            return (Criteria) this;
        }

        public Criteria andPropertyEqualTo(Integer value) {
            addCriterion("property =", value, "property");
            return (Criteria) this;
        }

        public Criteria andPropertyNotEqualTo(Integer value) {
            addCriterion("property <>", value, "property");
            return (Criteria) this;
        }

        public Criteria andPropertyGreaterThan(Integer value) {
            addCriterion("property >", value, "property");
            return (Criteria) this;
        }

        public Criteria andPropertyGreaterThanOrEqualTo(Integer value) {
            addCriterion("property >=", value, "property");
            return (Criteria) this;
        }

        public Criteria andPropertyLessThan(Integer value) {
            addCriterion("property <", value, "property");
            return (Criteria) this;
        }

        public Criteria andPropertyLessThanOrEqualTo(Integer value) {
            addCriterion("property <=", value, "property");
            return (Criteria) this;
        }

        public Criteria andPropertyIn(List<Integer> values) {
            addCriterion("property in", values, "property");
            return (Criteria) this;
        }

        public Criteria andPropertyNotIn(List<Integer> values) {
            addCriterion("property not in", values, "property");
            return (Criteria) this;
        }

        public Criteria andPropertyBetween(Integer value1, Integer value2) {
            addCriterion("property between", value1, value2, "property");
            return (Criteria) this;
        }

        public Criteria andPropertyNotBetween(Integer value1, Integer value2) {
            addCriterion("property not between", value1, value2, "property");
            return (Criteria) this;
        }

        public Criteria andOppo_eaccountIsNull() {
            addCriterion("oppo_eaccount is null");
            return (Criteria) this;
        }

        public Criteria andOppo_eaccountIsNotNull() {
            addCriterion("oppo_eaccount is not null");
            return (Criteria) this;
        }

        public Criteria andOppo_eaccountEqualTo(String value) {
            addCriterion("oppo_eaccount =", value, "oppo_eaccount");
            return (Criteria) this;
        }

        public Criteria andOppo_eaccountNotEqualTo(String value) {
            addCriterion("oppo_eaccount <>", value, "oppo_eaccount");
            return (Criteria) this;
        }

        public Criteria andOppo_eaccountGreaterThan(String value) {
            addCriterion("oppo_eaccount >", value, "oppo_eaccount");
            return (Criteria) this;
        }

        public Criteria andOppo_eaccountGreaterThanOrEqualTo(String value) {
            addCriterion("oppo_eaccount >=", value, "oppo_eaccount");
            return (Criteria) this;
        }

        public Criteria andOppo_eaccountLessThan(String value) {
            addCriterion("oppo_eaccount <", value, "oppo_eaccount");
            return (Criteria) this;
        }

        public Criteria andOppo_eaccountLessThanOrEqualTo(String value) {
            addCriterion("oppo_eaccount <=", value, "oppo_eaccount");
            return (Criteria) this;
        }

        public Criteria andOppo_eaccountLike(String value) {
            addCriterion("oppo_eaccount like", value, "oppo_eaccount");
            return (Criteria) this;
        }

        public Criteria andOppo_eaccountNotLike(String value) {
            addCriterion("oppo_eaccount not like", value, "oppo_eaccount");
            return (Criteria) this;
        }

        public Criteria andOppo_eaccountIn(List<String> values) {
            addCriterion("oppo_eaccount in", values, "oppo_eaccount");
            return (Criteria) this;
        }

        public Criteria andOppo_eaccountNotIn(List<String> values) {
            addCriterion("oppo_eaccount not in", values, "oppo_eaccount");
            return (Criteria) this;
        }

        public Criteria andOppo_eaccountBetween(String value1, String value2) {
            addCriterion("oppo_eaccount between", value1, value2, "oppo_eaccount");
            return (Criteria) this;
        }

        public Criteria andOppo_eaccountNotBetween(String value1, String value2) {
            addCriterion("oppo_eaccount not between", value1, value2, "oppo_eaccount");
            return (Criteria) this;
        }

        public Criteria andOppo_nameIsNull() {
            addCriterion("oppo_name is null");
            return (Criteria) this;
        }

        public Criteria andOppo_nameIsNotNull() {
            addCriterion("oppo_name is not null");
            return (Criteria) this;
        }

        public Criteria andOppo_nameEqualTo(String value) {
            addCriterion("oppo_name =", value, "oppo_name");
            return (Criteria) this;
        }

        public Criteria andOppo_nameNotEqualTo(String value) {
            addCriterion("oppo_name <>", value, "oppo_name");
            return (Criteria) this;
        }

        public Criteria andOppo_nameGreaterThan(String value) {
            addCriterion("oppo_name >", value, "oppo_name");
            return (Criteria) this;
        }

        public Criteria andOppo_nameGreaterThanOrEqualTo(String value) {
            addCriterion("oppo_name >=", value, "oppo_name");
            return (Criteria) this;
        }

        public Criteria andOppo_nameLessThan(String value) {
            addCriterion("oppo_name <", value, "oppo_name");
            return (Criteria) this;
        }

        public Criteria andOppo_nameLessThanOrEqualTo(String value) {
            addCriterion("oppo_name <=", value, "oppo_name");
            return (Criteria) this;
        }

        public Criteria andOppo_nameLike(String value) {
            addCriterion("oppo_name like", value, "oppo_name");
            return (Criteria) this;
        }

        public Criteria andOppo_nameNotLike(String value) {
            addCriterion("oppo_name not like", value, "oppo_name");
            return (Criteria) this;
        }

        public Criteria andOppo_nameIn(List<String> values) {
            addCriterion("oppo_name in", values, "oppo_name");
            return (Criteria) this;
        }

        public Criteria andOppo_nameNotIn(List<String> values) {
            addCriterion("oppo_name not in", values, "oppo_name");
            return (Criteria) this;
        }

        public Criteria andOppo_nameBetween(String value1, String value2) {
            addCriterion("oppo_name between", value1, value2, "oppo_name");
            return (Criteria) this;
        }

        public Criteria andOppo_nameNotBetween(String value1, String value2) {
            addCriterion("oppo_name not between", value1, value2, "oppo_name");
            return (Criteria) this;
        }

        public Criteria andOppo_propertyIsNull() {
            addCriterion("oppo_property is null");
            return (Criteria) this;
        }

        public Criteria andOppo_propertyIsNotNull() {
            addCriterion("oppo_property is not null");
            return (Criteria) this;
        }

        public Criteria andOppo_propertyEqualTo(Integer value) {
            addCriterion("oppo_property =", value, "oppo_property");
            return (Criteria) this;
        }

        public Criteria andOppo_propertyNotEqualTo(Integer value) {
            addCriterion("oppo_property <>", value, "oppo_property");
            return (Criteria) this;
        }

        public Criteria andOppo_propertyGreaterThan(Integer value) {
            addCriterion("oppo_property >", value, "oppo_property");
            return (Criteria) this;
        }

        public Criteria andOppo_propertyGreaterThanOrEqualTo(Integer value) {
            addCriterion("oppo_property >=", value, "oppo_property");
            return (Criteria) this;
        }

        public Criteria andOppo_propertyLessThan(Integer value) {
            addCriterion("oppo_property <", value, "oppo_property");
            return (Criteria) this;
        }

        public Criteria andOppo_propertyLessThanOrEqualTo(Integer value) {
            addCriterion("oppo_property <=", value, "oppo_property");
            return (Criteria) this;
        }

        public Criteria andOppo_propertyIn(List<Integer> values) {
            addCriterion("oppo_property in", values, "oppo_property");
            return (Criteria) this;
        }

        public Criteria andOppo_propertyNotIn(List<Integer> values) {
            addCriterion("oppo_property not in", values, "oppo_property");
            return (Criteria) this;
        }

        public Criteria andOppo_propertyBetween(Integer value1, Integer value2) {
            addCriterion("oppo_property between", value1, value2, "oppo_property");
            return (Criteria) this;
        }

        public Criteria andOppo_propertyNotBetween(Integer value1, Integer value2) {
            addCriterion("oppo_property not between", value1, value2, "oppo_property");
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

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
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

        public Criteria andEnabledEqualTo(Integer value) {
            addCriterion("enabled =", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledNotEqualTo(Integer value) {
            addCriterion("enabled <>", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledGreaterThan(Integer value) {
            addCriterion("enabled >", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledGreaterThanOrEqualTo(Integer value) {
            addCriterion("enabled >=", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledLessThan(Integer value) {
            addCriterion("enabled <", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledLessThanOrEqualTo(Integer value) {
            addCriterion("enabled <=", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledIn(List<Integer> values) {
            addCriterion("enabled in", values, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledNotIn(List<Integer> values) {
            addCriterion("enabled not in", values, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledBetween(Integer value1, Integer value2) {
            addCriterion("enabled between", value1, value2, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledNotBetween(Integer value1, Integer value2) {
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

        public Criteria andExt3IsNull() {
            addCriterion("ext3 is null");
            return (Criteria) this;
        }

        public Criteria andExt3IsNotNull() {
            addCriterion("ext3 is not null");
            return (Criteria) this;
        }

        public Criteria andExt3EqualTo(String value) {
            addCriterion("ext3 =", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3NotEqualTo(String value) {
            addCriterion("ext3 <>", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3GreaterThan(String value) {
            addCriterion("ext3 >", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3GreaterThanOrEqualTo(String value) {
            addCriterion("ext3 >=", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3LessThan(String value) {
            addCriterion("ext3 <", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3LessThanOrEqualTo(String value) {
            addCriterion("ext3 <=", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3Like(String value) {
            addCriterion("ext3 like", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3NotLike(String value) {
            addCriterion("ext3 not like", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3In(List<String> values) {
            addCriterion("ext3 in", values, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3NotIn(List<String> values) {
            addCriterion("ext3 not in", values, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3Between(String value1, String value2) {
            addCriterion("ext3 between", value1, value2, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3NotBetween(String value1, String value2) {
            addCriterion("ext3 not between", value1, value2, "ext3");
            return (Criteria) this;
        }
    }

    /**
     * eacc_trans_transreq
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * eacc_trans_transreq 2018-02-04
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