package com.krylova.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date startDate;
    private Date finishDate;
    private Integer price;

    @ManyToOne
    private Faculty faculty;
    @ManyToOne
    private CourseInfo courseInfo;

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    @ManyToMany(targetEntity=Employee.class, fetch = FetchType.LAZY)
    private Set<Employee> employees = new HashSet<>();
}
