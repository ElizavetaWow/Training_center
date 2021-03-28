package sample.models;

import javafx.beans.property.StringProperty;

public class Employee extends Person {

    private StringProperty companyName;

    public String getCompanyName() {
        return companyName.get();
    }

    public StringProperty companyNameProperty() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }
}
