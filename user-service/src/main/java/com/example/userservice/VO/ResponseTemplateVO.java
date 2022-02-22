package com.example.userservice.VO;

import com.example.userservice.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTemplateVO {

    private UserEntity userEntity;
    private DepartmentEntity departmentEntity;
}
