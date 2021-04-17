package sample.models;

import com.google.gson.Gson;
import javafx.beans.property.*;
import sample.utils.DateUtil;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Employee extends Person{

    private LongProperty companyId;

    public Employee(Long id, String firstName, String lastName, String password, String email, LocalDate birthday, Company company){
        super(id, firstName, lastName, password, email, birthday);
        this.companyId = new SimpleLongProperty(company.getId());
    }

    public Employee(Long id, String firstName, String lastName, String password, String email, LocalDate birthday, Long companyId){
        super(id, firstName, lastName, password, email, birthday);
        this.companyId = new SimpleLongProperty();
    }

    public Employee(String firstName, String lastName, String password, String email, LocalDate birthday, Company company){
        super(firstName, lastName, password, email, birthday);
        this.companyId = new SimpleLongProperty(company.getId());
    }
    public Employee(String firstName, String lastName, String password, String email, LocalDate birthday){
        super(firstName, lastName, password, email, birthday);
        this.companyId = null;
    }

    public Employee(){
        this(null, null, null, null, null);
    }

    public long getCompanyId() {
        return companyId.get();
    }

    public LongProperty companyIdProperty() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId.set(companyId);
    }

    @Override
    public String toJSON() {
        Map<String, String> map = new HashMap<>();
        map.put("firstName", getFirstName());
        map.put("lastName", getLastName());
        map.put("password", getPassword());
        map.put("email", getEmail());
        map.put("birthday", DateUtil.format(getBirthday()));
        map.put("companyId", String.valueOf(getCompanyId()));

        Gson gson = new Gson();
        return gson.toJson(map);
    }
}
