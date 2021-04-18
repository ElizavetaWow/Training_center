package sample.models;

import com.google.gson.Gson;
import javafx.beans.property.*;
import sample.utils.DateUtil;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Employee extends Person{

    private ObjectProperty<Company> company;

    public Employee(Long id, String firstName, String lastName, String password, String email, LocalDate birthday, Company company){
        super(id, firstName, lastName, password, email, birthday);
        this.company = new SimpleObjectProperty<Company>(company);
    }

    public Employee(String firstName, String lastName, String password, String email, LocalDate birthday, Company company){
        super(firstName, lastName, password, email, birthday);
        this.company = new SimpleObjectProperty<Company>(company);
    }
    public Employee(String firstName, String lastName, String password, String email, LocalDate birthday){
        super(firstName, lastName, password, email, birthday);
        this.company = null;
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
        this.company = new SimpleObjectProperty<Company>(company);
    }

    @Override
    public String toJSON() {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<>();
        map.put("firstName", getFirstName());
        map.put("lastName", getLastName());
        map.put("password", getPassword());
        map.put("email", getEmail());
        map.put("birthday", DateUtil.format(getBirthday()));
        map.put("company", "");
        StringBuilder sb = new StringBuilder(getCompany().toJSON()) ;
        sb.insert(1, "\"id\":"+getCompany().getId()+",");
        StringBuilder jsb = new StringBuilder(gson.toJson(map)) ;
        int ind = jsb.indexOf("company\":")+9;
        jsb.replace(ind, ind+2, sb.toString());
        return jsb.toString();
    }
}
