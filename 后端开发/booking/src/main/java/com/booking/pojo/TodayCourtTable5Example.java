package com.booking.pojo;

import java.util.ArrayList;
import java.util.List;

public class TodayCourtTable5Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TodayCourtTable5Example() {
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

        public Criteria andTodayCourtIdIsNull() {
            addCriterion("today_court_id is null");
            return (Criteria) this;
        }

        public Criteria andTodayCourtIdIsNotNull() {
            addCriterion("today_court_id is not null");
            return (Criteria) this;
        }

        public Criteria andTodayCourtIdEqualTo(Integer value) {
            addCriterion("today_court_id =", value, "todayCourtId");
            return (Criteria) this;
        }

        public Criteria andTodayCourtIdNotEqualTo(Integer value) {
            addCriterion("today_court_id <>", value, "todayCourtId");
            return (Criteria) this;
        }

        public Criteria andTodayCourtIdGreaterThan(Integer value) {
            addCriterion("today_court_id >", value, "todayCourtId");
            return (Criteria) this;
        }

        public Criteria andTodayCourtIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("today_court_id >=", value, "todayCourtId");
            return (Criteria) this;
        }

        public Criteria andTodayCourtIdLessThan(Integer value) {
            addCriterion("today_court_id <", value, "todayCourtId");
            return (Criteria) this;
        }

        public Criteria andTodayCourtIdLessThanOrEqualTo(Integer value) {
            addCriterion("today_court_id <=", value, "todayCourtId");
            return (Criteria) this;
        }

        public Criteria andTodayCourtIdIn(List<Integer> values) {
            addCriterion("today_court_id in", values, "todayCourtId");
            return (Criteria) this;
        }

        public Criteria andTodayCourtIdNotIn(List<Integer> values) {
            addCriterion("today_court_id not in", values, "todayCourtId");
            return (Criteria) this;
        }

        public Criteria andTodayCourtIdBetween(Integer value1, Integer value2) {
            addCriterion("today_court_id between", value1, value2, "todayCourtId");
            return (Criteria) this;
        }

        public Criteria andTodayCourtIdNotBetween(Integer value1, Integer value2) {
            addCriterion("today_court_id not between", value1, value2, "todayCourtId");
            return (Criteria) this;
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

        public Criteria andTimeIdIsNull() {
            addCriterion("time_id is null");
            return (Criteria) this;
        }

        public Criteria andTimeIdIsNotNull() {
            addCriterion("time_id is not null");
            return (Criteria) this;
        }

        public Criteria andTimeIdEqualTo(Integer value) {
            addCriterion("time_id =", value, "timeId");
            return (Criteria) this;
        }

        public Criteria andTimeIdNotEqualTo(Integer value) {
            addCriterion("time_id <>", value, "timeId");
            return (Criteria) this;
        }

        public Criteria andTimeIdGreaterThan(Integer value) {
            addCriterion("time_id >", value, "timeId");
            return (Criteria) this;
        }

        public Criteria andTimeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("time_id >=", value, "timeId");
            return (Criteria) this;
        }

        public Criteria andTimeIdLessThan(Integer value) {
            addCriterion("time_id <", value, "timeId");
            return (Criteria) this;
        }

        public Criteria andTimeIdLessThanOrEqualTo(Integer value) {
            addCriterion("time_id <=", value, "timeId");
            return (Criteria) this;
        }

        public Criteria andTimeIdIn(List<Integer> values) {
            addCriterion("time_id in", values, "timeId");
            return (Criteria) this;
        }

        public Criteria andTimeIdNotIn(List<Integer> values) {
            addCriterion("time_id not in", values, "timeId");
            return (Criteria) this;
        }

        public Criteria andTimeIdBetween(Integer value1, Integer value2) {
            addCriterion("time_id between", value1, value2, "timeId");
            return (Criteria) this;
        }

        public Criteria andTimeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("time_id not between", value1, value2, "timeId");
            return (Criteria) this;
        }

        public Criteria andCourtStateIsNull() {
            addCriterion("court_state is null");
            return (Criteria) this;
        }

        public Criteria andCourtStateIsNotNull() {
            addCriterion("court_state is not null");
            return (Criteria) this;
        }

        public Criteria andCourtStateEqualTo(Integer value) {
            addCriterion("court_state =", value, "courtState");
            return (Criteria) this;
        }

        public Criteria andCourtStateNotEqualTo(Integer value) {
            addCriterion("court_state <>", value, "courtState");
            return (Criteria) this;
        }

        public Criteria andCourtStateGreaterThan(Integer value) {
            addCriterion("court_state >", value, "courtState");
            return (Criteria) this;
        }

        public Criteria andCourtStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("court_state >=", value, "courtState");
            return (Criteria) this;
        }

        public Criteria andCourtStateLessThan(Integer value) {
            addCriterion("court_state <", value, "courtState");
            return (Criteria) this;
        }

        public Criteria andCourtStateLessThanOrEqualTo(Integer value) {
            addCriterion("court_state <=", value, "courtState");
            return (Criteria) this;
        }

        public Criteria andCourtStateIn(List<Integer> values) {
            addCriterion("court_state in", values, "courtState");
            return (Criteria) this;
        }

        public Criteria andCourtStateNotIn(List<Integer> values) {
            addCriterion("court_state not in", values, "courtState");
            return (Criteria) this;
        }

        public Criteria andCourtStateBetween(Integer value1, Integer value2) {
            addCriterion("court_state between", value1, value2, "courtState");
            return (Criteria) this;
        }

        public Criteria andCourtStateNotBetween(Integer value1, Integer value2) {
            addCriterion("court_state not between", value1, value2, "courtState");
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