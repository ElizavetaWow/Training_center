package sample.models;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.beans.property.*;
import sample.utils.DateUtil;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Employee extends Person{
    private LongProperty id;
    private ObjectProperty<Company> company;

    public Employee(Long id, String firstName, String lastName, String password, String email, LocalDate birthday, Company company){
        super(id, firstName, lastName, password, email, birthday);
        this.id = new SimpleLongProperty(id);
        this.company = new SimpleObjectProperty<>(company);
        this.setRole(getRoles().indexOf("student"));
    }

    public Employee(Long id, String firstName, String lastName, String password, String email, LocalDate birthday){
        super(id, firstName, lastName, password, email, birthday);
        this.id = new SimpleLongProperty(id);
        this.company = new SimpleObjectProperty<>();
        this.setRole(getRoles().indexOf("student"));
    }

    public Employee(String firstName, String lastName, String password, String email, LocalDate birthday, Company company){
        super(firstName, lastName, password, email, birthday);
        this.company = new SimpleObjectProperty<>(company);
        this.setRole(getRoles().indexOf("student"));
    }
    public Employee(String firstName, String lastName, String password, String email, LocalDate birthday){
        super(firstName, lastName, password, email, birthday);
        this.company = null;
        this.setRole(getRoles().indexOf("student"));
    }

    public Employee(){
        this(null, null, null, null, null);
    }

    public Company getCompany() {
        return company.get();
    }

    public ObjectProperty<Company> companyProperty() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = new SimpleObjectProperty<>(company);
    }

    @Override
    public String toJSON() {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        if (id != null){
            map.put("id", String.valueOf(getId()));
        }
        map.put("firstName", getFirstName());
        map.put("lastName", getLastName());
        map.put("password", getPassword());
        map.put("email", getEmail());
        map.put("birthday", DateUtil.format(getBirthday()));
        map.put("company", new Gson().fromJson(getCompany().toJSON(), JsonObject.class));
        return gson.toJson(map);
    }
}
