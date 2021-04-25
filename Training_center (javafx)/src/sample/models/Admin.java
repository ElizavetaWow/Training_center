package sample.models;

import java.time.LocalDate;

public class Admin extends Person{

    public Admin(Long id, String firstName, String lastName, String password, String email, LocalDate birthday){
        super(id, firstName, lastName, password, email, birthday);
        this.setRole(getRoles().indexOf("admin"));
    }
    
    public Admin(String firstName, String lastName, String password, String email, LocalDate birthday){
        super(firstName, lastName, password, email, birthday);
        this.setRole(getRoles().indexOf("admin"));
    }

    public Admin(){
        this(null, null, null, null, null);
    }

}
