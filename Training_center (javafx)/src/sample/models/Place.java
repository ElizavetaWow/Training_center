package sample.models;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Place {
    private LongProperty id;
    private StringProperty city;
    private StringProperty street;
    private StringProperty building;
    private IntegerProperty room;

    public Place(Long id, String city, String street, String building, Integer room) {
        this.id = new SimpleLongProperty(id);
        this.city = new SimpleStringProperty(city);
        this.street = new SimpleStringProperty(street);
        this.building = new SimpleStringProperty(building);
        this.room = new SimpleIntegerProperty(room);
    }

    public Place(){
        this(null, null, null, null, null);
    }

    public long getId() {
        return id.get();
    }

    public String getCity() {
        return city.get();
    }

    public StringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public String getStreet() {
        return street.get();
    }

    public StringProperty streetProperty() {
        return street;
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public String getBuilding() {
        return building.get();
    }

    public StringProperty buildingProperty() {
        return building;
    }

    public void setBuilding(String building) {
        this.building.set(building);
    }

    public int getRoom() {
        return room.get();
    }

    public IntegerProperty roomProperty() {
        return room;
    }

    public void setRoom(int room) {
        this.room.set(room);
    }
}
