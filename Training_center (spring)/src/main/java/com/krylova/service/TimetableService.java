package com.krylova.service;

import com.krylova.entity.Timetable;
import com.krylova.repository.TimetableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TimetableService {
    @Autowired
    private TimetableRepository timetableRepository;

    public void create(Timetable timetable) {
        timetableRepository.save(timetable);
    }

    public void update(Timetable timetable) {
        timetableRepository.save(timetable);
    }

    public void delete(Timetable timetable) {
        timetableRepository.delete(timetable);
    }

    public List<Timetable> findAll() {
        return timetableRepository.findAll();
    }

    public Optional<Timetable> findById(Long id) {
        return timetableRepository.findById(id);
    }

    public List<Timetable> findByName(String name) {
        return timetableRepository.findByCourse_CourseInfo_Name(name);
    }

    public List<Timetable> findByDate(LocalDate date) {
        return timetableRepository.findByDate(date);
    }

    public List<Timetable> findByNameAndDate(String name, LocalDate date) {
        return timetableRepository.findByCourse_CourseInfo_NameAndDate(name, date);
    }
}
