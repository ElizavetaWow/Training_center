package sample.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Place {
    private LongProperty id;
    private StringProperty city;
    private StringProperty street;
    private StringProperty building;
    private IntegerProperty room;

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
