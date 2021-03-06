package sample.models;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import sample.utils.DateUtil;
import sample.utils.TimeUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Timetable implements ApiModel {
    private LongProperty id;
    private ObjectProperty<Course> course;
    private ObjectProperty<Place> place;
    private ObjectProperty<LocalDate> date;
    private ObjectProperty<LocalTime> time;

    public Timetable(Long id, Course course, Place place, LocalTime time, LocalDate date) {
        this.id = new SimpleLongProperty(id);
        this.course = new SimpleObjectProperty<>(course);
        this.place = new SimpleObjectProperty<>(place);
        this.time = new SimpleObjectProperty<>(time);
        this.date = new SimpleObjectProperty<>(date);
    }

    public Timetable(Course course, Place place, LocalTime time, LocalDate date) {
        this.id = null;
        this.course = new SimpleObjectProperty<>(course);
        this.place = new SimpleObjectProperty<>(place);
        this.time = new SimpleObjectProperty<>(time);
        this.date = new SimpleObjectProperty<>(date);
    }


    public Timetable() {
        this(null, null, null, null);
    }

    public long getId() {
        return id.get();
    }

    public LongProperty getIdProperty() {
        return id;
    }

    public Course getCourse() {
        return course.get();
    }

    public ObjectProperty<Course> getCourseProperty() {
        return course;
    }

    public void setCourse(Course course) {
        this.course.set(course);
    }

    public Place getPlace() {
        return place.get();
    }

    public ObjectProperty<Place> getPlaceProperty() {
        return place;
    }

    public void setPlace(Place place) {
        this.place.set(place);
    }

    public LocalDate getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDate> getDateProperty() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public LocalTime getTime() {
        return time.get();
    }

    public ObjectProperty<LocalTime> getTimeProperty() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time.set(time);
    }

    @Override
    public String toJSON() {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        if (id != null) {
            map.put("id", String.valueOf(getId()));
        }
        map.put("course", new Gson().fromJson(getCourse().toJSON(), JsonObject.class));
        map.put("place", new Gson().fromJson(getPlace().toJSON(), JsonObject.class));
        map.put("date", DateUtil.format(getDate()));
        map.put("time", TimeUtil.format(getTime()));
        return gson.toJson(map);
    }
}
