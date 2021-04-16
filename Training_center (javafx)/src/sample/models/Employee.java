package sample.models;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Employee extends Person {

    private StringProperty companyName;

    public Employee(Long id, String firstName, String lastName, String password, String email, LocalDate birthday, Company company){
        super(id, firstName, lastName, password, email, birthday);
        this.companyName = new SimpleStringProperty(company.getName());
    }

    public Employee(){
        this(null, null, null, null, null, null, null);
    }

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
