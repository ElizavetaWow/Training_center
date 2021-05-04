package sample.models;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.beans.property.*;
import sample.utils.DateUtil;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Course implements ApiModel{
    private LongProperty id;
    private IntegerProperty price;
    private ObjectProperty<LocalDate> startDate;
    private ObjectProperty<LocalDate> finishDate;
    private ObjectProperty<Faculty> faculty;
    private ObjectProperty<CourseInfo> courseInfo;
    private ObjectProperty<Set<Employee>> employees;

    public Course(Long id, LocalDate startDate, LocalDate finishDate, Integer price, CourseInfo courseInfo, Faculty faculty){
        this.id = new SimpleLongProperty(id);
        this.price = new SimpleIntegerProperty(price);
        this.startDate = new SimpleObjectProperty<>(startDate);
        this.finishDate = new SimpleObjectProperty<>(finishDate);
        this.courseInfo = new SimpleObjectProperty<>(courseInfo);
        this.faculty = new SimpleObjectProperty<>(faculty);
        this.employees = new SimpleObjectProperty<>();
    }

    public Course(LocalDate startDate, LocalDate finishDate, Integer price, CourseInfo courseInfo, Faculty faculty){
        this.id = null;
        this.price = new SimpleIntegerProperty(price);
        this.startDate = new SimpleObjectProperty<>(startDate);
        this.finishDate = new SimpleObjectProperty<>(finishDate);
        this.courseInfo = new SimpleObjectProperty<>(courseInfo);
        this.faculty = new SimpleObjectProperty<>(faculty);
        this.employees = new SimpleObjectProperty<>();
    }


    public Course(){
        this(null, null, null, null, null);
    }

    public long getId() {
        return id.get();
    }

    public LongProperty getIdProperty() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate.get();
    }

    public ObjectProperty<LocalDate> getStartDateProperty() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate.set(startDate);
    }

    public LocalDate getFinishDate() {
        return finishDate.get();
    }

    public ObjectProperty<LocalDate> getFinishDateProperty() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate.set(finishDate);
    }

    public Faculty getFaculty() {
        return faculty.get();
    }

    public ObjectProperty<Faculty> getFacultyProperty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty.set(faculty);
    }

    public CourseInfo getCourseInfo() {
        return courseInfo.get();
    }

    public ObjectProperty<CourseInfo> getCourseInfoProperty() {
        return courseInfo;
    }

    public void setCourseInfo(CourseInfo courseInfo) {
        this.courseInfo.set(courseInfo);
    }

    public Set<Employee> getEmployees() {
        return employees.get();
    }

    public ObjectProperty<Set<Employee>> employeesProperty() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees.set(employees);
    }

    public int getPrice() {
        return price.get();
    }

    public IntegerProperty getPriceProperty() {
        return price;
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public int getNumberOfEmployees(){
        if (getEmployees() != null)
            return getEmployees().size();
        return 0;
    }

    public void setEmployee(Employee employee) {
        this.employees.get().add(employee);
    }
    public void removeEmployee(Long id) {
        employees.get().removeIf(employee -> employee.getId() == id);
    }

    public String getNamesAndFaculty(){
        return getCourseInfo().getName()+" "+getFaculty().getNamesAndEmail();
    }

    @Override
    public String toJSON() {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        if (id != null){
            map.put("id", getId());
        }
        map.put("startDate", DateUtil.format(getStartDate()));
        map.put("finishDate", DateUtil.format(getFinishDate()));
        map.put("price", getPrice());
        map.put("courseInfo", new Gson().fromJson(getCourseInfo().toJSON(), JsonObject.class));
        map.put("faculty", new Gson().fromJson(getFaculty().toJSON(), JsonObject.class));
        Set employeeSet = new HashSet();
        if (getEmployees() != null && getEmployees().size() > 0) {
            for (Employee employee : getEmployees()) {
                employeeSet.add(new Gson().fromJson(employee.toJSON(), JsonObject.class));

            }
        }
        map.put("courses", employeeSet);
        return gson.toJson(map);
    }
}
