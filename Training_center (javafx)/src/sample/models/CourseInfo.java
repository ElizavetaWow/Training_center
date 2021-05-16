package sample.models;

import com.google.gson.Gson;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.HashMap;
import java.util.Map;

public class CourseInfo implements ApiModel {
    private LongProperty id;
    private StringProperty name;

    public CourseInfo(Long id, String name) {
        this.id = new SimpleLongProperty(id);
        this.name = new SimpleStringProperty(name);
    }

    public CourseInfo(String name) {
        this.id = null;
        this.name = new SimpleStringProperty(name);
    }

    public CourseInfo() {
        this(null);
    }

    public long getId() {
        return id.get();
    }

    public LongProperty getIdProperty() {
        return id;
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

    @Override
    public String toJSON() {
        Map<String, String> map = new HashMap<>();
        if (id != null) {
            map.put("id", String.valueOf(getId()));
        }
        map.put("name", getName());
        Gson gson = new Gson();
        return gson.toJson(map);
    }
}
