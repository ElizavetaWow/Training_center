package sample.models;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class Timetable {
    private LongProperty id;
    private StringProperty course;
    private StringProperty place;
    private ObjectProperty<LocalDate> date;
    private ObjectProperty<LocalTime> time;

    public Timetable(Long id, String course, String place, LocalTime time, LocalDate date){
        this.id = new SimpleLongProperty(id);
        this.course = new SimpleStringProperty(course);
        this.place = new SimpleStringProperty(place);
        this.time = new SimpleObjectProperty<LocalTime>(time);
        this.date = new SimpleObjectProperty<LocalDate>(date);

    }

    public Timetable(){
        this(null, null, null, null, null);
    }
}
