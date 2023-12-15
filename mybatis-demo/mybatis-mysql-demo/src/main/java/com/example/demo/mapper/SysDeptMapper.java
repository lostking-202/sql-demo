package com.example.demo.mapper;

import java.util.List;

public interface SysDeptMapper {
  String getIdByDeptName(String deptName);

  List<Integer> getId();
}
