package com.krylova.service;

import com.krylova.entity.Administrator;
import com.krylova.repository.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministratorService {

    @Autowired
    private AdministratorRepository administratorRepository;

    public Administrator create(Administrator administrator) {
        return administratorRepository.save(administrator);
    }

    public Administrator update(Administrator administrator) {
        return administratorRepository.save(administrator);
    }

    public boolean delete(Administrator administrator) {
        administratorRepository.delete(administrator);
        return true;
    }

    public List<Administrator> findAll() {
        return administratorRepository.findAll();
    }

    public Optional<Administrator> findById(Long id) {
        return administratorRepository.findById(id);
    }

    public List<Administrator> findByEmail(String email) {
        return administratorRepository.findByEmail(email);
    }


}
