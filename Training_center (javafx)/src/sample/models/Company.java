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
        this(null, null, null);
    }
}
