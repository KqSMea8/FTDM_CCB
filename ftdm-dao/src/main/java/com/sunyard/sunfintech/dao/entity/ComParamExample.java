package com.sunyard.sunfintech.dao.entity;

import java.util.ArrayList;
import java.util.List;

public class ComParamExample {
    /**
     * com_param
     */
    protected String orderByClause;

    /**
     * com_param
     */
    protected boolean distinct;

    /**
     * com_param
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2017-10-27
     */
    public ComParamExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *
     * @mbggenerated 2017-10-27
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *
     * @mbggenerated 2017-10-27
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *
     * @mbggenerated 2017-10-27
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *
     * @mbggenerated 2017-10-27
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *
     * @mbggenerated 2017-10-27
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *
     * @mbggenerated 2017-10-27
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *
     * @mbggenerated 2017-10-27
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *
     * @mbggenerated 2017-10-27
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
     * @mbggenerated 2017-10-27
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *
     * @mbggenerated 2017-10-27
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * com_param 2017-10-27
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

        public Criteria andParam_typeIsNull() {
            addCriterion("param_type is null");
            return (Criteria) this;
        }

        public Criteria andParam_typeIsNotNull() {
            addCriterion("param_type is not null");
            return (Criteria) this;
        }

        public Criteria andParam_typeEqualTo(String value) {
            addCriterion("param_type =", value, "param_type");
            return (Criteria) this;
        }

        public Criteria andParam_typeNotEqualTo(String value) {
            addCriterion("param_type <>", value, "param_type");
            return (Criteria) this;
        }

        public Criteria andParam_typeGreaterThan(String value) {
            addCriterion("param_type >", value, "param_type");
            return (Criteria) this;
        }

        public Criteria andParam_typeGreaterThanOrEqualTo(String value) {
            addCriterion("param_type >=", value, "param_type");
            return (Criteria) this;
        }

        public Criteria andParam_typeLessThan(String value) {
            addCriterion("param_type <", value, "param_type");
            return (Criteria) this;
        }

        public Criteria andParam_typeLessThanOrEqualTo(String value) {
            addCriterion("param_type <=", value, "param_type");
            return (Criteria) this;
        }

        public Criteria andParam_typeLike(String value) {
            addCriterion("param_type like", value, "param_type");
            return (Criteria) this;
        }

        public Criteria andParam_typeNotLike(String value) {
            addCriterion("param_type not like", value, "param_type");
            return (Criteria) this;
        }

        public Criteria andParam_typeIn(List<String> values) {
            addCriterion("param_type in", values, "param_type");
            return (Criteria) this;
        }

        public Criteria andParam_typeNotIn(List<String> values) {
            addCriterion("param_type not in", values, "param_type");
            return (Criteria) this;
        }

        public Criteria andParam_typeBetween(String value1, String value2) {
            addCriterion("param_type between", value1, value2, "param_type");
            return (Criteria) this;
        }

        public Criteria andParam_typeNotBetween(String value1, String value2) {
            addCriterion("param_type not between", value1, value2, "param_type");
            return (Criteria) this;
        }

        public Criteria andProd_typeIsNull() {
            addCriterion("prod_type is null");
            return (Criteria) this;
        }

        public Criteria andProd_typeIsNotNull() {
            addCriterion("prod_type is not null");
            return (Criteria) this;
        }

        public Criteria andProd_typeEqualTo(String value) {
            addCriterion("prod_type =", value, "prod_type");
            return (Criteria) this;
        }

        public Criteria andProd_typeNotEqualTo(String value) {
            addCriterion("prod_type <>", value, "prod_type");
            return (Criteria) this;
        }

        public Criteria andProd_typeGreaterThan(String value) {
            addCriterion("prod_type >", value, "prod_type");
            return (Criteria) this;
        }

        public Criteria andProd_typeGreaterThanOrEqualTo(String value) {
            addCriterion("prod_type >=", value, "prod_type");
            return (Criteria) this;
        }

        public Criteria andProd_typeLessThan(String value) {
            addCriterion("prod_type <", value, "prod_type");
            return (Criteria) this;
        }

        public Criteria andProd_typeLessThanOrEqualTo(String value) {
            addCriterion("prod_type <=", value, "prod_type");
            return (Criteria) this;
        }

        public Criteria andProd_typeLike(String value) {
            addCriterion("prod_type like", value, "prod_type");
            return (Criteria) this;
        }

        public Criteria andProd_typeNotLike(String value) {
            addCriterion("prod_type not like", value, "prod_type");
            return (Criteria) this;
        }

        public Criteria andProd_typeIn(List<String> values) {
            addCriterion("prod_type in", values, "prod_type");
            return (Criteria) this;
        }

        public Criteria andProd_typeNotIn(List<String> values) {
            addCriterion("prod_type not in", values, "prod_type");
            return (Criteria) this;
        }

        public Criteria andProd_typeBetween(String value1, String value2) {
            addCriterion("prod_type between", value1, value2, "prod_type");
            return (Criteria) this;
        }

        public Criteria andProd_typeNotBetween(String value1, String value2) {
            addCriterion("prod_type not between", value1, value2, "prod_type");
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

        public Criteria andParam_codeIsNull() {
            addCriterion("param_code is null");
            return (Criteria) this;
        }

        public Criteria andParam_codeIsNotNull() {
            addCriterion("param_code is not null");
            return (Criteria) this;
        }

        public Criteria andParam_codeEqualTo(String value) {
            addCriterion("param_code =", value, "param_code");
            return (Criteria) this;
        }

        public Criteria andParam_codeNotEqualTo(String value) {
            addCriterion("param_code <>", value, "param_code");
            return (Criteria) this;
        }

        public Criteria andParam_codeGreaterThan(String value) {
            addCriterion("param_code >", value, "param_code");
            return (Criteria) this;
        }

        public Criteria andParam_codeGreaterThanOrEqualTo(String value) {
            addCriterion("param_code >=", value, "param_code");
            return (Criteria) this;
        }

        public Criteria andParam_codeLessThan(String value) {
            addCriterion("param_code <", value, "param_code");
            return (Criteria) this;
        }

        public Criteria andParam_codeLessThanOrEqualTo(String value) {
            addCriterion("param_code <=", value, "param_code");
            return (Criteria) this;
        }

        public Criteria andParam_codeLike(String value) {
            addCriterion("param_code like", value, "param_code");
            return (Criteria) this;
        }

        public Criteria andParam_codeNotLike(String value) {
            addCriterion("param_code not like", value, "param_code");
            return (Criteria) this;
        }

        public Criteria andParam_codeIn(List<String> values) {
            addCriterion("param_code in", values, "param_code");
            return (Criteria) this;
        }

        public Criteria andParam_codeNotIn(List<String> values) {
            addCriterion("param_code not in", values, "param_code");
            return (Criteria) this;
        }

        public Criteria andParam_codeBetween(String value1, String value2) {
            addCriterion("param_code between", value1, value2, "param_code");
            return (Criteria) this;
        }

        public Criteria andParam_codeNotBetween(String value1, String value2) {
            addCriterion("param_code not between", value1, value2, "param_code");
            return (Criteria) this;
        }

        public Criteria andParam_valueIsNull() {
            addCriterion("param_value is null");
            return (Criteria) this;
        }

        public Criteria andParam_valueIsNotNull() {
            addCriterion("param_value is not null");
            return (Criteria) this;
        }

        public Criteria andParam_valueEqualTo(String value) {
            addCriterion("param_value =", value, "param_value");
            return (Criteria) this;
        }

        public Criteria andParam_valueNotEqualTo(String value) {
            addCriterion("param_value <>", value, "param_value");
            return (Criteria) this;
        }

        public Criteria andParam_valueGreaterThan(String value) {
            addCriterion("param_value >", value, "param_value");
            return (Criteria) this;
        }

        public Criteria andParam_valueGreaterThanOrEqualTo(String value) {
            addCriterion("param_value >=", value, "param_value");
            return (Criteria) this;
        }

        public Criteria andParam_valueLessThan(String value) {
            addCriterion("param_value <", value, "param_value");
            return (Criteria) this;
        }

        public Criteria andParam_valueLessThanOrEqualTo(String value) {
            addCriterion("param_value <=", value, "param_value");
            return (Criteria) this;
        }

        public Criteria andParam_valueLike(String value) {
            addCriterion("param_value like", value, "param_value");
            return (Criteria) this;
        }

        public Criteria andParam_valueNotLike(String value) {
            addCriterion("param_value not like", value, "param_value");
            return (Criteria) this;
        }

        public Criteria andParam_valueIn(List<String> values) {
            addCriterion("param_value in", values, "param_value");
            return (Criteria) this;
        }

        public Criteria andParam_valueNotIn(List<String> values) {
            addCriterion("param_value not in", values, "param_value");
            return (Criteria) this;
        }

        public Criteria andParam_valueBetween(String value1, String value2) {
            addCriterion("param_value between", value1, value2, "param_value");
            return (Criteria) this;
        }

        public Criteria andParam_valueNotBetween(String value1, String value2) {
            addCriterion("param_value not between", value1, value2, "param_value");
            return (Criteria) this;
        }

        public Criteria andParam_nameIsNull() {
            addCriterion("param_name is null");
            return (Criteria) this;
        }

        public Criteria andParam_nameIsNotNull() {
            addCriterion("param_name is not null");
            return (Criteria) this;
        }

        public Criteria andParam_nameEqualTo(String value) {
            addCriterion("param_name =", value, "param_name");
            return (Criteria) this;
        }

        public Criteria andParam_nameNotEqualTo(String value) {
            addCriterion("param_name <>", value, "param_name");
            return (Criteria) this;
        }

        public Criteria andParam_nameGreaterThan(String value) {
            addCriterion("param_name >", value, "param_name");
            return (Criteria) this;
        }

        public Criteria andParam_nameGreaterThanOrEqualTo(String value) {
            addCriterion("param_name >=", value, "param_name");
            return (Criteria) this;
        }

        public Criteria andParam_nameLessThan(String value) {
            addCriterion("param_name <", value, "param_name");
            return (Criteria) this;
        }

        public Criteria andParam_nameLessThanOrEqualTo(String value) {
            addCriterion("param_name <=", value, "param_name");
            return (Criteria) this;
        }

        public Criteria andParam_nameLike(String value) {
            addCriterion("param_name like", value, "param_name");
            return (Criteria) this;
        }

        public Criteria andParam_nameNotLike(String value) {
            addCriterion("param_name not like", value, "param_name");
            return (Criteria) this;
        }

        public Criteria andParam_nameIn(List<String> values) {
            addCriterion("param_name in", values, "param_name");
            return (Criteria) this;
        }

        public Criteria andParam_nameNotIn(List<String> values) {
            addCriterion("param_name not in", values, "param_name");
            return (Criteria) this;
        }

        public Criteria andParam_nameBetween(String value1, String value2) {
            addCriterion("param_name between", value1, value2, "param_name");
            return (Criteria) this;
        }

        public Criteria andParam_nameNotBetween(String value1, String value2) {
            addCriterion("param_name not between", value1, value2, "param_name");
            return (Criteria) this;
        }

        public Criteria andParam_descIsNull() {
            addCriterion("param_desc is null");
            return (Criteria) this;
        }

        public Criteria andParam_descIsNotNull() {
            addCriterion("param_desc is not null");
            return (Criteria) this;
        }

        public Criteria andParam_descEqualTo(String value) {
            addCriterion("param_desc =", value, "param_desc");
            return (Criteria) this;
        }

        public Criteria andParam_descNotEqualTo(String value) {
            addCriterion("param_desc <>", value, "param_desc");
            return (Criteria) this;
        }

        public Criteria andParam_descGreaterThan(String value) {
            addCriterion("param_desc >", value, "param_desc");
            return (Criteria) this;
        }

        public Criteria andParam_descGreaterThanOrEqualTo(String value) {
            addCriterion("param_desc >=", value, "param_desc");
            return (Criteria) this;
        }

        public Criteria andParam_descLessThan(String value) {
            addCriterion("param_desc <", value, "param_desc");
            return (Criteria) this;
        }

        public Criteria andParam_descLessThanOrEqualTo(String value) {
            addCriterion("param_desc <=", value, "param_desc");
            return (Criteria) this;
        }

        public Criteria andParam_descLike(String value) {
            addCriterion("param_desc like", value, "param_desc");
            return (Criteria) this;
        }

        public Criteria andParam_descNotLike(String value) {
            addCriterion("param_desc not like", value, "param_desc");
            return (Criteria) this;
        }

        public Criteria andParam_descIn(List<String> values) {
            addCriterion("param_desc in", values, "param_desc");
            return (Criteria) this;
        }

        public Criteria andParam_descNotIn(List<String> values) {
            addCriterion("param_desc not in", values, "param_desc");
            return (Criteria) this;
        }

        public Criteria andParam_descBetween(String value1, String value2) {
            addCriterion("param_desc between", value1, value2, "param_desc");
            return (Criteria) this;
        }

        public Criteria andParam_descNotBetween(String value1, String value2) {
            addCriterion("param_desc not between", value1, value2, "param_desc");
            return (Criteria) this;
        }
    }

    /**
     * com_param
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * com_param 2017-10-27
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