package sample.models;

import com.google.gson.Gson;
import javafx.beans.property.*;

import java.util.HashMap;
import java.util.Map;

public class Company implements ApiModel {
    private LongProperty id;
    private IntegerProperty money;
    private StringProperty name;
    private StringProperty account;

    public Company(String name, String account) {
        this.id = null;
        this.money = new SimpleIntegerProperty(0);
        this.name = new SimpleStringProperty(name);
        this.account = new SimpleStringProperty(account);
    }

    public Company(Long id, String name, String account, Integer money) {
        this.id = new SimpleLongProperty(id);
        this.money = new SimpleIntegerProperty(money);
        this.name = new SimpleStringProperty(name);
        this.account = new SimpleStringProperty(account);
    }

    public Company() {
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

    public int getMoney() {
        return money.get();
    }

    public IntegerProperty geMoneyProperty() {
        return money;
    }

    public void changeMoney(int money) {
        this.money.set(this.money.get() + money);
    }

    public void setMoney(int money) {
        this.money.set(money);
    }

    @Override
    public String toJSON() {
        Map<String, Object> map = new HashMap<>();
        if (id != null) {
            map.put("id", String.valueOf(getId()));
        }
        map.put("name", getName());
        map.put("account", getAccount());
        map.put("money", getMoney());
        Gson gson = new Gson();
        return gson.toJson(map);
    }
}
