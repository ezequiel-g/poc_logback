package eze.poc.logback.model;

import java.util.StringJoiner;

public class ModelConfidential {

    private long id;

    /**
     * confidential field
     */
    private String ssn;

    private String countryId;

    /**
     * confidential field
     */
    private String address;

    private String otherField;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(final String ssn) {
        this.ssn = ssn;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(final String countryId) {
        this.countryId = countryId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getOtherField() {
        return otherField;
    }

    public void setOtherField(final String otherField) {
        this.otherField = otherField;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ModelConfidential.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("ssn='" + ssn + "'")
                .add("countryId='" + countryId + "'")
                .add("address='" + address + "'")
                .add("otherField='" + otherField + "'")
                .toString();
    }

}
