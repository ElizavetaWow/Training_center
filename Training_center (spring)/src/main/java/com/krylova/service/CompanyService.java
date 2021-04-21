package com.krylova.service;

import com.krylova.entity.Company;
import com.krylova.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public void create(Company company){
        companyRepository.save(company);
    }

    public void update(Company company){
        companyRepository.save(company);
    }

    public void delete(Company company){
        companyRepository.delete(company);
    }

    public List<Company> findAll(){
        return companyRepository.findAll();
    }

    public Optional<Company> findById(Long id){
        return companyRepository.findById(id);
    }

    public List<Company> findByName(String name){
        return companyRepository.findByName(name);
    }

}
