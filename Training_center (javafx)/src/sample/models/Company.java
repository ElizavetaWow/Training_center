package sample.models;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Company {
    private LongProperty id;
    private StringProperty name;
    private StringProperty account;

    public Company(Long id, String name, String account){
        this.id = new SimpleLongProperty(id);
        this.name = new SimpleStringProperty(name);
        this.account = new SimpleStringProperty(account);
    }

    public Company(){
        this(Long.valueOf(0), "", "");
    }

    public String getName() {
        return name.get();
    }

    public StringProperty getNameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getAccount() {
        return account.get();
    }

    public StringProperty getAccountProperty() {
        return account;
    }

    public void setAccount(String account) {
        this.account.set(account);
    }

    public long getId() {
        return id.get();
    }

    public LongProperty getIdProperty() {
        return id;
    }
}
