package com.krylova.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @ManyToOne
    private Faculty faculty;
    @ManyToOne
    private CourseInfo courseInfo;
    @ManyToMany(targetEntity=Employee.class)
    private Set employees;
}
