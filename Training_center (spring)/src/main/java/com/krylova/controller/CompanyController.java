package com.krylova.controller;

import com.krylova.entity.Company;
import com.krylova.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CompanyController {
    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService){
        this.companyService = companyService;
    }

    @PostMapping("/companies")
    public ResponseEntity<?> create(@RequestBody Company company){
        companyService.create(company);
        return  new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/companies")
    public ResponseEntity<List<Company>> findAll(){
        final List<Company> companyList = companyService.findAll();
        return companyList != null && !companyList.isEmpty()
                ? new ResponseEntity<>(companyList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/companies/{id}")
    public ResponseEntity<Optional<Company>> find(@PathVariable(name = "id") Long id){
        final Optional<Company> company = companyService.find(id);
        return company != null
                ? new ResponseEntity<>(company, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/companies/{id}")
    public ResponseEntity<?> updateCompany(@PathVariable(name = "id") Long id, @RequestBody Company companyUpdate) {
        return companyService.find(id).map(company -> {
            company.setName(companyUpdate.getName());
            company.setAccount(companyUpdate.getAccount());
            company.setEmployees(companyUpdate.getEmployees());
            companyService.update(company);
            return new ResponseEntity<>(company, HttpStatus.OK);
        }).orElseThrow(() -> new IllegalArgumentException());

    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable(name = "id") Long id) {
        return companyService.find(id).map(company -> {
            companyService.delete(company);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new IllegalArgumentException());
    }

}
