package com.example.userservice.constoller;

import com.example.userservice.VO.ResponseTemplateVO;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/welcome")
    public String userServiceGateway(){
        return "Hello from user controller via zuul";
    }

    @PostMapping("/")
    public UserEntity saveUser(@RequestBody UserEntity userEntity){
      log.info("Save user inside UserController");
        return userService.saveUser(userEntity);
    }

    @GetMapping("/{id}")
    public ResponseTemplateVO getUserAndDepartment(@PathVariable("id") Long userId){
        return userService.getUserWithDepartment(userId);
    }
}
