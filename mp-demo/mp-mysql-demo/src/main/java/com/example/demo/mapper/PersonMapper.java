package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.dao.Person;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PersonMapper extends BaseMapper<Person> {
}
