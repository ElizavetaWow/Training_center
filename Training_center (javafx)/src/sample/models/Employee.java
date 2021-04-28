package sample.models;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.beans.property.*;
import sample.utils.DateUtil;

import java.time.LocalDate;
import java.util.*;

public class Employee extends Person{
    private LongProperty id;
    private ObjectProperty<Company> company;
    private ObjectProperty<List<Course>> courses;

    public Employee(Long id, String firstName, String lastName, String password, String email, LocalDate birthday, Company company){
        super(id, firstName, lastName, password, email, birthday);
        this.id = new SimpleLongProperty(id);
        this.company = new SimpleObjectProperty<>(company);
        this.courses = new SimpleObjectProperty<>();
        this.setRole(getRoles().indexOf("student"));
    }

    public Employee(Long id, String firstName, String lastName, String password, String email, LocalDate birthday){
        super(id, firstName, lastName, password, email, birthday);
        this.id = new SimpleLongProperty(id);
        this.company = new SimpleObjectProperty<>();
        this.courses = new SimpleObjectProperty<>();
        this.setRole(getRoles().indexOf("student"));
    }

    public Employee(String firstName, String lastName, String password, String email, LocalDate birthday, Company company){
        super(firstName, lastName, password, email, birthday);
        this.company = new SimpleObjectProperty<>(company);
        this.courses = new SimpleObjectProperty<>();
        this.setRole(getRoles().indexOf("student"));
    }
    public Employee(String firstName, String lastName, String password, String email, LocalDate birthday){
        super(firstName, lastName, password, email, birthday);
        this.company = null;
        this.courses = new SimpleObjectProperty<>();
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

    public List<Course> getCourses() {
        return courses.get();
    }

    public ObjectProperty<List<Course>> getCoursesProperty() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses.setValue(courses);
    }
    public void setCourse(Course course) {
        this.courses.get().add(course);
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
        ArrayList coursesJA = new ArrayList();
        for (Course c: getCourses()){
            coursesJA.add(c.getId());
        }
        map.put("coursesList", coursesJA);
        return gson.toJson(map);
    }
}
