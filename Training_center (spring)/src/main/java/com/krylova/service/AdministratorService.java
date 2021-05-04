package com.krylova.service;

import com.krylova.entity.Administrator;
import com.krylova.repository.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministratorService {

    @Autowired
    private AdministratorRepository administratorRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;


    public void create(Administrator administrator){
        administrator.setPassword(bCryptPasswordEncoder.encode(administrator.getPassword()));
        administratorRepository.save(administrator);
    }

    public void update(Administrator administrator){
        administratorRepository.save(administrator);
    }

    public void delete(Administrator administrator){
        administratorRepository.delete(administrator);
    }

    public List<Administrator> findAll(){
        return administratorRepository.findAll();
    }

    public Optional<Administrator> findById(Long id){
        return administratorRepository.findById(id);
    }

    public List<Administrator> findByEmail(String email){
        return administratorRepository.findByEmail(email);
    }

}
