package com.krylova.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    private Date birthday;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Company company;
    @ManyToMany(targetEntity=Course.class, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set courses;

}
