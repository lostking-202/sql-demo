package com.example.demo.controller;

import com.example.demo.entity.Person;
import com.example.demo.mapper.PersonMapper;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    PersonMapper personMapper;
    @Autowired
    PersonService personService;
    @GetMapping("/{id}")
    public Person getPersonById(@PathVariable int id){
        return personMapper.getPersonById(id);
    }

    @GetMapping("batchTest")
    public void batchTest(){
        personService.batchDo();
    }
}
