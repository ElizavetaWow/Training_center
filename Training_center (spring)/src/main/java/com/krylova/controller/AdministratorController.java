package com.krylova.controller;

import com.krylova.entity.Administrator;
import com.krylova.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AdministratorController {

    private final AdministratorService administratorService;

    @Autowired
    public AdministratorController(AdministratorService administratorService){
        this.administratorService = administratorService;
    }

    @PostMapping("/admins")
    public ResponseEntity<?> create(@RequestBody Administrator administrator){
        administratorService.create(administrator);
        return  new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/admins")
    public ResponseEntity<List<Administrator>> findAll(){
        final List<Administrator> administratorList = administratorService.findAll();
        return administratorList != null && !administratorList.isEmpty()
                ? new ResponseEntity<>(administratorList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/admins/{id}")
    public ResponseEntity<Optional<Administrator>> findById(@PathVariable(name = "id") Long id){
        final Optional<Administrator> administrator = administratorService.findById(id);
        return administrator != null
                ? new ResponseEntity<>(administrator, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/admins/email_{email}")
    public ResponseEntity<List<Administrator>> findAll(@PathVariable(name = "email") String email){
        final List<Administrator> administratorList = administratorService.findByEmail(email);
        return administratorList != null && !administratorList.isEmpty()
                ? new ResponseEntity<>(administratorList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/admins/{id}")
    public ResponseEntity<?> updateAdministrator(@PathVariable(name = "id") Long id, @RequestBody Administrator administratorUpdate) {
        return administratorService.findById(id).map(administrator -> {
            administrator.setFirstName(administratorUpdate.getFirstName());
            administrator.setLastName(administratorUpdate.getLastName());
            administrator.setEmail(administratorUpdate.getEmail());
            administrator.setPassword(administratorUpdate.getPassword());
            administrator.setBirthday(administratorUpdate.getBirthday());
            administratorService.update(administrator);
            return new ResponseEntity<>(administrator, HttpStatus.OK);
        }).orElseThrow(() -> new IllegalArgumentException());

    }

    @DeleteMapping("/admins/{id}")
    public ResponseEntity<?> deleteAdministrator(@PathVariable(name = "id") Long id) {
        return administratorService.findById(id).map(administrator -> {
            administratorService.delete(administrator);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new IllegalArgumentException());
    }


}
