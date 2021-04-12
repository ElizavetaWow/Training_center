package sample.models;

import javafx.beans.property.*;

import java.time.LocalDate;

public abstract class Person {
    private LongProperty id;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty email;
    private StringProperty password;
    private ObjectProperty<LocalDate> birthday;

    public Person(Long id, String firstName, String lastName, String password, String email, LocalDate birthday){
        this.id = new SimpleLongProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.email = new SimpleStringProperty(email);
        this.password = new SimpleStringProperty(password);
        this.birthday = new SimpleObjectProperty<LocalDate>(birthday);

    }

    public Person(){
        this(null, null, null, null, null, null);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty getFirstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty getLastNameProperty() {
        return lastName;
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty getEmailProperty() {
        return email;
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty getPasswordProperty() {
        return password;
    }

    public LocalDate getBirthday() {
        return birthday.get();
    }

    public ObjectProperty<LocalDate> getBirthdayProperty() {
        return birthday;
    }

    public long getId() {
        return id.get();
    }

    public void setFirstName(String firstName) {
        this.firstName = new SimpleStringProperty(firstName);
    }

    public void setLastName(String lastName) {
        this.lastName = new SimpleStringProperty(lastName);
    }

    public void setEmail(String email) {
        this.email = new SimpleStringProperty(email);
    }

    public void setPassword(String password) {
        this.password = new SimpleStringProperty(password);
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = new SimpleObjectProperty<>(birthday);
    }
}
