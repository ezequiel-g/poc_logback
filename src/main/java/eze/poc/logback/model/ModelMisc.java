package eze.poc.logback.model;

import java.util.StringJoiner;

public class ModelMisc {

    private int key;

    private String value;

    public int getKey() {
        return key;
    }

    public void setKey(final int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ModelMisc.class.getSimpleName() + "[", "]")
                .add("key=" + key)
                .add("value='" + value + "'")
                .toString();
    }

}
