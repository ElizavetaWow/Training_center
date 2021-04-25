package sample.models;

import com.google.gson.Gson;
import javafx.beans.property.*;

import java.util.HashMap;
import java.util.Map;

public class Place implements ApiModel{
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

    public Place(String city, String street, String building) {
        this.id = null;
        this.city = new SimpleStringProperty(city);
        this.street = new SimpleStringProperty(street);
        this.building = new SimpleStringProperty(building);
        this.room = new SimpleIntegerProperty(0);
    }

    public Place(){
        this(null, null, null);
    }

    public long getId() {
        return id.get();
    }

    public String getCity() {
        return city.get();
    }

    public StringProperty getCityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public String getStreet() {
        return street.get();
    }

    public StringProperty getStreetProperty() {
        return street;
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public String getBuilding() {
        return building.get();
    }

    public StringProperty getBuildingProperty() {
        return building;
    }

    public void setBuilding(String building) {
        this.building.set(building);
    }

    public int getRoom() {
        return room.get();
    }

    public IntegerProperty getRoomProperty() {
        return room;
    }

    public void setRoom(int room) {
        this.room.set(room);
    }

    public StringProperty getFullPlaceProperty(){
        return new SimpleStringProperty(getCity()+" "+getStreet()+" "+getBuilding()+" "+getRoom());
    }

    public String getFullPlace(){
        return getCity()+" "+getStreet()+" "+getBuilding()+" "+getRoom();
    }


    @Override
    public String toJSON() {
        Map<String, String> map = new HashMap<>();
        if (id != null){
            map.put("id", String.valueOf(getId()));
        }
        map.put("city", getCity());
        map.put("street", getStreet());
        map.put("building", getBuilding());
        map.put("room", String.valueOf(getRoom()));
        Gson gson = new Gson();
        return gson.toJson(map);
    }
}
