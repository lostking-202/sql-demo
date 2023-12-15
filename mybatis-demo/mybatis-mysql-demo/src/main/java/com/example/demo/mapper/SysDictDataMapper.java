package com.example.demo.mapper;

import org.apache.ibatis.annotations.Param;

public interface SysDictDataMapper {
  Integer getIdByTypeAndValue(@Param("dictType") String type,@Param("dictValue") String value);
}
