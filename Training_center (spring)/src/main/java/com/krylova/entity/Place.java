package com.krylova.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private String street;
    private String building;
    private Integer room;

    @OneToMany(targetEntity=Timetable.class, fetch = FetchType.LAZY)
    private List timetables;
}
