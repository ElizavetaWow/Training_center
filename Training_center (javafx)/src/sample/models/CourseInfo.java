package sample.models;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CourseInfo {
    private LongProperty id;
    private StringProperty name;

    public CourseInfo(Long id, String name){
        this.id = new SimpleLongProperty(id);
        this.name = new SimpleStringProperty(name);
    }

    public CourseInfo(){
        this(null, null);
    }
}
