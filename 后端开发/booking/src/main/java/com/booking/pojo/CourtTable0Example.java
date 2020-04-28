package com.booking.pojo;

import java.util.ArrayList;
import java.util.List;

public class CourtTable0Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CourtTable0Example() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

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

        public Criteria andCourtIdIsNull() {
            addCriterion("court_id is null");
            return (Criteria) this;
        }

        public Criteria andCourtIdIsNotNull() {
            addCriterion("court_id is not null");
            return (Criteria) this;
        }

        public Criteria andCourtIdEqualTo(Integer value) {
            addCriterion("court_id =", value, "courtId");
            return (Criteria) this;
        }

        public Criteria andCourtIdNotEqualTo(Integer value) {
            addCriterion("court_id <>", value, "courtId");
            return (Criteria) this;
        }

        public Criteria andCourtIdGreaterThan(Integer value) {
            addCriterion("court_id >", value, "courtId");
            return (Criteria) this;
        }

        public Criteria andCourtIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("court_id >=", value, "courtId");
            return (Criteria) this;
        }

        public Criteria andCourtIdLessThan(Integer value) {
            addCriterion("court_id <", value, "courtId");
            return (Criteria) this;
        }

        public Criteria andCourtIdLessThanOrEqualTo(Integer value) {
            addCriterion("court_id <=", value, "courtId");
            return (Criteria) this;
        }

        public Criteria andCourtIdIn(List<Integer> values) {
            addCriterion("court_id in", values, "courtId");
            return (Criteria) this;
        }

        public Criteria andCourtIdNotIn(List<Integer> values) {
            addCriterion("court_id not in", values, "courtId");
            return (Criteria) this;
        }

        public Criteria andCourtIdBetween(Integer value1, Integer value2) {
            addCriterion("court_id between", value1, value2, "courtId");
            return (Criteria) this;
        }

        public Criteria andCourtIdNotBetween(Integer value1, Integer value2) {
            addCriterion("court_id not between", value1, value2, "courtId");
            return (Criteria) this;
        }

        public Criteria andCourtMaterialIsNull() {
            addCriterion("court_material is null");
            return (Criteria) this;
        }

        public Criteria andCourtMaterialIsNotNull() {
            addCriterion("court_material is not null");
            return (Criteria) this;
        }

        public Criteria andCourtMaterialEqualTo(String value) {
            addCriterion("court_material =", value, "courtMaterial");
            return (Criteria) this;
        }

        public Criteria andCourtMaterialNotEqualTo(String value) {
            addCriterion("court_material <>", value, "courtMaterial");
            return (Criteria) this;
        }

        public Criteria andCourtMaterialGreaterThan(String value) {
            addCriterion("court_material >", value, "courtMaterial");
            return (Criteria) this;
        }

        public Criteria andCourtMaterialGreaterThanOrEqualTo(String value) {
            addCriterion("court_material >=", value, "courtMaterial");
            return (Criteria) this;
        }

        public Criteria andCourtMaterialLessThan(String value) {
            addCriterion("court_material <", value, "courtMaterial");
            return (Criteria) this;
        }

        public Criteria andCourtMaterialLessThanOrEqualTo(String value) {
            addCriterion("court_material <=", value, "courtMaterial");
            return (Criteria) this;
        }

        public Criteria andCourtMaterialLike(String value) {
            addCriterion("court_material like", value, "courtMaterial");
            return (Criteria) this;
        }

        public Criteria andCourtMaterialNotLike(String value) {
            addCriterion("court_material not like", value, "courtMaterial");
            return (Criteria) this;
        }

        public Criteria andCourtMaterialIn(List<String> values) {
            addCriterion("court_material in", values, "courtMaterial");
            return (Criteria) this;
        }

        public Criteria andCourtMaterialNotIn(List<String> values) {
            addCriterion("court_material not in", values, "courtMaterial");
            return (Criteria) this;
        }

        public Criteria andCourtMaterialBetween(String value1, String value2) {
            addCriterion("court_material between", value1, value2, "courtMaterial");
            return (Criteria) this;
        }

        public Criteria andCourtMaterialNotBetween(String value1, String value2) {
            addCriterion("court_material not between", value1, value2, "courtMaterial");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

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