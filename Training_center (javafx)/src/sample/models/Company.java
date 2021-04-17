package sample.models;

import com.google.gson.Gson;
import javafx.beans.property.*;
import sample.utils.DateUtil;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Company implements ApiModel{
    private LongProperty id;
    private StringProperty name;
    private StringProperty account;

    public Company(String name, String account){
        this.id = null;
        this.name = new SimpleStringProperty(name);
        this.account = new SimpleStringProperty(account);
    }
    public Company(Long id, String name, String account){
        this.id = new SimpleLongProperty(id);
        this.name = new SimpleStringProperty(name);
        this.account = new SimpleStringProperty(account);
    }

    public Company(){
        this(null, null);
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

    @Override
    public String toJSON() {
        Map<String, String> map = new HashMap<>();
        map.put("name", getName());
        map.put("account", getAccount());

        Gson gson = new Gson();
        return gson.toJson(map);
    }
}
