package sample.models;

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
}
