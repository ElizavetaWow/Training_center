package com.krylova.service;

import com.krylova.entity.Faculty;
import com.krylova.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacultyService {

    @Autowired
    private FacultyRepository facultyRepository;

    public void create(Faculty faculty){
        facultyRepository.save(faculty);
    }

    public void update(Faculty faculty){
        facultyRepository.save(faculty);
    }

    public void delete(Faculty faculty){
        facultyRepository.delete(faculty);
    }

    public List<Faculty> findAll(){
        return facultyRepository.findAll();
    }

    public Optional<Faculty> findById(Long id){
        return facultyRepository.findById(id);
    }

    public Faculty findByEmail(String email){
        return facultyRepository.findByEmail(email);
    }

}
