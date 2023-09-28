package com.example.demo.controller;

import com.example.demo.entity.Person;
import com.example.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    PersonRepository personRepository;
    @GetMapping("/{id}")
    public Person findById(@PathVariable Long id){
        return personRepository.findById(id).orElseThrow();
    }

    @PostMapping("/")
    public Person savePerson(@RequestBody Person person){
        return personRepository.save(person);
    }

}
