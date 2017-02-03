package o.horbenko.repository.extended.containers;

import javax.validation.constraints.NotNull;

public class FilterCriteriaHolder<T> {

    private String field;
    private String operator;
    private T value;

    public FilterCriteriaHolder field(@NotNull String field) {
        this.field = field;
        return this;
    }

    public FilterCriteriaHolder operator(@NotNull String operator) {
        this.operator = operator;
        return this;
    }

    public FilterCriteriaHolder value(@NotNull T value) {
        this.value = value;
        return this;
    }

    public String getField() {
        return field;
    }

    public void setField(@NotNull String field) {
        this.field = field;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(@NotNull String operator) {
        this.operator = operator;
    }

    public T getValue() {
        return value;
    }

    public void setValue(@NotNull T value) {
        this.value = value;
    }
}
