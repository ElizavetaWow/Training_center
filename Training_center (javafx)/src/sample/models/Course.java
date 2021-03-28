package sample.models;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Course {
    private LongProperty id;
    private ObjectProperty<LocalDate> startDate;
    private ObjectProperty<LocalDate> finishDate;

    public Course(Long id, LocalDate startDate, LocalDate finishDate){
        this.id = new SimpleLongProperty(id);
        this.startDate = new SimpleObjectProperty<LocalDate>(startDate);
        this.finishDate = new SimpleObjectProperty<LocalDate>(finishDate);
    }

    public Course(){
        this(null, null, null);
    }
}
