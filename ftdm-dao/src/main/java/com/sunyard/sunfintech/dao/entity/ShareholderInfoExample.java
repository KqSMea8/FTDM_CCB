package com.sunyard.sunfintech.dao.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShareholderInfoExample {
    /**
     * shareholder_info
     */
    protected String orderByClause;

    /**
     * shareholder_info
     */
    protected boolean distinct;

    /**
     * shareholder_info
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2017-06-01
     */
    public ShareholderInfoExample() {
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
     * shareholder_info 2017-06-01
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

        public Criteria andShareholder_nameIsNull() {
            addCriterion("shareholder_name is null");
            return (Criteria) this;
        }

        public Criteria andShareholder_nameIsNotNull() {
            addCriterion("shareholder_name is not null");
            return (Criteria) this;
        }

        public Criteria andShareholder_nameEqualTo(String value) {
            addCriterion("shareholder_name =", value, "shareholder_name");
            return (Criteria) this;
        }

        public Criteria andShareholder_nameNotEqualTo(String value) {
            addCriterion("shareholder_name <>", value, "shareholder_name");
            return (Criteria) this;
        }

        public Criteria andShareholder_nameGreaterThan(String value) {
            addCriterion("shareholder_name >", value, "shareholder_name");
            return (Criteria) this;
        }

        public Criteria andShareholder_nameGreaterThanOrEqualTo(String value) {
            addCriterion("shareholder_name >=", value, "shareholder_name");
            return (Criteria) this;
        }

        public Criteria andShareholder_nameLessThan(String value) {
            addCriterion("shareholder_name <", value, "shareholder_name");
            return (Criteria) this;
        }

        public Criteria andShareholder_nameLessThanOrEqualTo(String value) {
            addCriterion("shareholder_name <=", value, "shareholder_name");
            return (Criteria) this;
        }

        public Criteria andShareholder_nameLike(String value) {
            addCriterion("shareholder_name like", value, "shareholder_name");
            return (Criteria) this;
        }

        public Criteria andShareholder_nameNotLike(String value) {
            addCriterion("shareholder_name not like", value, "shareholder_name");
            return (Criteria) this;
        }

        public Criteria andShareholder_nameIn(List<String> values) {
            addCriterion("shareholder_name in", values, "shareholder_name");
            return (Criteria) this;
        }

        public Criteria andShareholder_nameNotIn(List<String> values) {
            addCriterion("shareholder_name not in", values, "shareholder_name");
            return (Criteria) this;
        }

        public Criteria andShareholder_nameBetween(String value1, String value2) {
            addCriterion("shareholder_name between", value1, value2, "shareholder_name");
            return (Criteria) this;
        }

        public Criteria andShareholder_nameNotBetween(String value1, String value2) {
            addCriterion("shareholder_name not between", value1, value2, "shareholder_name");
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

        public Criteria andJobIsNull() {
            addCriterion("job is null");
            return (Criteria) this;
        }

        public Criteria andJobIsNotNull() {
            addCriterion("job is not null");
            return (Criteria) this;
        }

        public Criteria andJobEqualTo(String value) {
            addCriterion("job =", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobNotEqualTo(String value) {
            addCriterion("job <>", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobGreaterThan(String value) {
            addCriterion("job >", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobGreaterThanOrEqualTo(String value) {
            addCriterion("job >=", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobLessThan(String value) {
            addCriterion("job <", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobLessThanOrEqualTo(String value) {
            addCriterion("job <=", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobLike(String value) {
            addCriterion("job like", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobNotLike(String value) {
            addCriterion("job not like", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobIn(List<String> values) {
            addCriterion("job in", values, "job");
            return (Criteria) this;
        }

        public Criteria andJobNotIn(List<String> values) {
            addCriterion("job not in", values, "job");
            return (Criteria) this;
        }

        public Criteria andJobBetween(String value1, String value2) {
            addCriterion("job between", value1, value2, "job");
            return (Criteria) this;
        }

        public Criteria andJobNotBetween(String value1, String value2) {
            addCriterion("job not between", value1, value2, "job");
            return (Criteria) this;
        }

        public Criteria andProportionIsNull() {
            addCriterion("proportion is null");
            return (Criteria) this;
        }

        public Criteria andProportionIsNotNull() {
            addCriterion("proportion is not null");
            return (Criteria) this;
        }

        public Criteria andProportionEqualTo(BigDecimal value) {
            addCriterion("proportion =", value, "proportion");
            return (Criteria) this;
        }

        public Criteria andProportionNotEqualTo(BigDecimal value) {
            addCriterion("proportion <>", value, "proportion");
            return (Criteria) this;
        }

        public Criteria andProportionGreaterThan(BigDecimal value) {
            addCriterion("proportion >", value, "proportion");
            return (Criteria) this;
        }

        public Criteria andProportionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("proportion >=", value, "proportion");
            return (Criteria) this;
        }

        public Criteria andProportionLessThan(BigDecimal value) {
            addCriterion("proportion <", value, "proportion");
            return (Criteria) this;
        }

        public Criteria andProportionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("proportion <=", value, "proportion");
            return (Criteria) this;
        }

        public Criteria andProportionIn(List<BigDecimal> values) {
            addCriterion("proportion in", values, "proportion");
            return (Criteria) this;
        }

        public Criteria andProportionNotIn(List<BigDecimal> values) {
            addCriterion("proportion not in", values, "proportion");
            return (Criteria) this;
        }

        public Criteria andProportionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("proportion between", value1, value2, "proportion");
            return (Criteria) this;
        }

        public Criteria andProportionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("proportion not between", value1, value2, "proportion");
            return (Criteria) this;
        }

        public Criteria andId_typeIsNull() {
            addCriterion("id_type is null");
            return (Criteria) this;
        }

        public Criteria andId_typeIsNotNull() {
            addCriterion("id_type is not null");
            return (Criteria) this;
        }

        public Criteria andId_typeEqualTo(String value) {
            addCriterion("id_type =", value, "id_type");
            return (Criteria) this;
        }

        public Criteria andId_typeNotEqualTo(String value) {
            addCriterion("id_type <>", value, "id_type");
            return (Criteria) this;
        }

        public Criteria andId_typeGreaterThan(String value) {
            addCriterion("id_type >", value, "id_type");
            return (Criteria) this;
        }

        public Criteria andId_typeGreaterThanOrEqualTo(String value) {
            addCriterion("id_type >=", value, "id_type");
            return (Criteria) this;
        }

        public Criteria andId_typeLessThan(String value) {
            addCriterion("id_type <", value, "id_type");
            return (Criteria) this;
        }

        public Criteria andId_typeLessThanOrEqualTo(String value) {
            addCriterion("id_type <=", value, "id_type");
            return (Criteria) this;
        }

        public Criteria andId_typeLike(String value) {
            addCriterion("id_type like", value, "id_type");
            return (Criteria) this;
        }

        public Criteria andId_typeNotLike(String value) {
            addCriterion("id_type not like", value, "id_type");
            return (Criteria) this;
        }

        public Criteria andId_typeIn(List<String> values) {
            addCriterion("id_type in", values, "id_type");
            return (Criteria) this;
        }

        public Criteria andId_typeNotIn(List<String> values) {
            addCriterion("id_type not in", values, "id_type");
            return (Criteria) this;
        }

        public Criteria andId_typeBetween(String value1, String value2) {
            addCriterion("id_type between", value1, value2, "id_type");
            return (Criteria) this;
        }

        public Criteria andId_typeNotBetween(String value1, String value2) {
            addCriterion("id_type not between", value1, value2, "id_type");
            return (Criteria) this;
        }

        public Criteria andId_codeIsNull() {
            addCriterion("id_code is null");
            return (Criteria) this;
        }

        public Criteria andId_codeIsNotNull() {
            addCriterion("id_code is not null");
            return (Criteria) this;
        }

        public Criteria andId_codeEqualTo(String value) {
            addCriterion("id_code =", value, "id_code");
            return (Criteria) this;
        }

        public Criteria andId_codeNotEqualTo(String value) {
            addCriterion("id_code <>", value, "id_code");
            return (Criteria) this;
        }

        public Criteria andId_codeGreaterThan(String value) {
            addCriterion("id_code >", value, "id_code");
            return (Criteria) this;
        }

        public Criteria andId_codeGreaterThanOrEqualTo(String value) {
            addCriterion("id_code >=", value, "id_code");
            return (Criteria) this;
        }

        public Criteria andId_codeLessThan(String value) {
            addCriterion("id_code <", value, "id_code");
            return (Criteria) this;
        }

        public Criteria andId_codeLessThanOrEqualTo(String value) {
            addCriterion("id_code <=", value, "id_code");
            return (Criteria) this;
        }

        public Criteria andId_codeLike(String value) {
            addCriterion("id_code like", value, "id_code");
            return (Criteria) this;
        }

        public Criteria andId_codeNotLike(String value) {
            addCriterion("id_code not like", value, "id_code");
            return (Criteria) this;
        }

        public Criteria andId_codeIn(List<String> values) {
            addCriterion("id_code in", values, "id_code");
            return (Criteria) this;
        }

        public Criteria andId_codeNotIn(List<String> values) {
            addCriterion("id_code not in", values, "id_code");
            return (Criteria) this;
        }

        public Criteria andId_codeBetween(String value1, String value2) {
            addCriterion("id_code between", value1, value2, "id_code");
            return (Criteria) this;
        }

        public Criteria andId_codeNotBetween(String value1, String value2) {
            addCriterion("id_code not between", value1, value2, "id_code");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
            return (Criteria) this;
        }
    }

    /**
     * shareholder_info
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * shareholder_info 2017-06-01
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