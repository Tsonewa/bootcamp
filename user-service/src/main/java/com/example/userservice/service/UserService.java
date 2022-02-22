package com.example.userservice.service;

import com.example.userservice.VO.DepartmentEntity;
import com.example.userservice.VO.ResponseTemplateVO;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserService {

    public static String DEPARTMENT_URL = "http://DEPARTMENT-SERVICE/departments/";

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    public UserService(UserRepository userRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    public UserEntity saveUser(UserEntity userEntity) {
        log.info("SaveUser inside UserService");
        return userRepository.save(userEntity);
    }

    public ResponseTemplateVO getUserWithDepartment(Long userId) {

        ResponseTemplateVO vo = new ResponseTemplateVO();
        UserEntity userEntity = userRepository.findById(userId).orElseThrow();

        DepartmentEntity departmentEntity =
                restTemplate.getForObject(DEPARTMENT_URL + userEntity.getDepartmentId()
                        , DepartmentEntity.class);

        vo.setUserEntity(userEntity);
        vo.setDepartmentEntity(departmentEntity);

        return vo;
    }


}
