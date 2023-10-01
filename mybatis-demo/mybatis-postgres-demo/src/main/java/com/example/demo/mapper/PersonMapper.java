package com.example.demo.mapper;

import com.example.demo.entity.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PersonMapper{
    @Select("select * from person where id = #{id}")
    Person getPersonById(@Param("id") int id);
}
