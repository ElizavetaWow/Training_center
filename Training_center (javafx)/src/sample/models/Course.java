package sample.models;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.beans.property.*;
import sample.utils.DateUtil;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Course implements ApiModel{
    private LongProperty id;
    private ObjectProperty<LocalDate> startDate;
    private ObjectProperty<LocalDate> finishDate;
    private ObjectProperty<Faculty> faculty;
    private ObjectProperty<CourseInfo> courseInfo;

    public Course(Long id, LocalDate startDate, LocalDate finishDate, CourseInfo courseInfo, Faculty faculty){
        this.id = new SimpleLongProperty(id);
        this.startDate = new SimpleObjectProperty<LocalDate>(startDate);
        this.finishDate = new SimpleObjectProperty<LocalDate>(finishDate);
        this.courseInfo = new SimpleObjectProperty<CourseInfo>(courseInfo);
        this.faculty = new SimpleObjectProperty<Faculty>(faculty);
    }

    public Course(LocalDate startDate, LocalDate finishDate, CourseInfo courseInfo, Faculty faculty){
        this.id = null;
        this.startDate = new SimpleObjectProperty<LocalDate>(startDate);
        this.finishDate = new SimpleObjectProperty<LocalDate>(finishDate);
        this.courseInfo = new SimpleObjectProperty<CourseInfo>(courseInfo);
        this.faculty = new SimpleObjectProperty<Faculty>(faculty);
    }


    public Course(){
        this(null, null, null, null);
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


    @Override
    public String toJSON() {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        map.put("startDate", DateUtil.format(getStartDate()));
        map.put("finishDate", DateUtil.format(getFinishDate()));
        map.put("courseInfo", new Gson().fromJson(getCourseInfo().toJSON(), JsonObject.class));
        map.put("faculty", new Gson().fromJson(getFaculty().toJSON(), JsonObject.class));
        return gson.toJson(map);
    }
}
