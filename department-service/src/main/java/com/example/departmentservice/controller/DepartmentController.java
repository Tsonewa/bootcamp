package com.example.departmentservice.controller;

import com.example.departmentservice.entity.DepartmentEntity;
import com.example.departmentservice.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/departments")
@Slf4j
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("/")
    public DepartmentEntity saveDepartment(@RequestBody DepartmentEntity departmentEntity){
      log.info("Inside saveDepartment method of DepartmentController");
        return departmentService.saveDepartment(departmentEntity);
    }

    @GetMapping("/{id}")
    public DepartmentEntity findDepartmentById(@PathVariable("id") Long departmnetId){

        return departmentService.findDepartmentById(departmnetId);
    }
}
