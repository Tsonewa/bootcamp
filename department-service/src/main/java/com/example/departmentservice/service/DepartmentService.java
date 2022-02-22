package com.example.departmentservice.service;

import com.example.departmentservice.entity.DepartmentEntity;
import com.example.departmentservice.repo.DepartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }


    public DepartmentEntity saveDepartment(DepartmentEntity deparmentEntity){
        log.info("Inside saveDepartment method of DepartmentController");
        return departmentRepository.save(deparmentEntity);
    }

    public DepartmentEntity findDepartmentById(Long departmnetId) {
      log.info("Inside findDepartmentById in DepartmentService");
      return departmentRepository.findDepartmentById(departmnetId);
    }
}
