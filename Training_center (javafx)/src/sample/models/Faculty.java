package sample.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Faculty extends Person{

    public Faculty(Long id, String firstName, String lastName, String password, String email, LocalDate birthday){
        super(id, firstName, lastName, password, email, birthday);
    }
    
    public Faculty(String firstName, String lastName, String password, String email, LocalDate birthday){
        super(firstName, lastName, password, email, birthday);
    }

    public Faculty(){
        this(null, null, null, null, null);
    }

    public StringProperty getNamesAndEmailProperty(){
        return new SimpleStringProperty(getLastName()+" "+getFirstName()+" ["+getEmail()+"]");
    }

    public String getNamesAndEmail(){
        return getLastName()+" "+getFirstName()+" ["+getEmail()+"]";
    }

}
