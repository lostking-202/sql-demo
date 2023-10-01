package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.dao.Person;
import com.example.demo.mapper.PersonMapper;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    PersonMapper personMapper;
    @GetMapping("/{id}")
    public Person getPersonById(@PathVariable Integer id){
        return personMapper.selectById(id);
    }

    @GetMapping("/name")
    public Person getPersonByName(@RequestParam(required = false) String name){
        return personMapper.selectOne(Wrappers.lambdaQuery(Person.class).like(!Strings.isNullOrEmpty(name),Person::getName,name));
    }
}
