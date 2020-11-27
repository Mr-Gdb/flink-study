package com.gdb.learn.springmvc.controller;


import com.gdb.learn.springmvc.entity.User;
import com.gdb.learn.springmvc.mapper.UserMapper;
import com.gdb.learn.springmvc.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/user")
@Api("用户界面")
public class UserController {

    @Autowired
    UserMapper mapper;

    @GetMapping("")
    @ApiOperation(value = "获取用户列表")
    public List<User> list() {
        List<User> users = mapper.queryUserList();
        // 返回列表
        return users;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取用户")
    public User get(@PathVariable("id") Long id) {
        return mapper.queryUserById(id.intValue());
    }

    @PostMapping("")
    public void add(UserVo vo) {
        System.out.println(String.format("id: %l,name:%s",vo.getId(), vo.getUserName()));
    }


    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable("id") Long id) {
        System.out.println("delete user id = " + id);
        Boolean res = true;
        return res;
    }

    @PutMapping("/{id}")
    public Boolean update(@PathVariable("id") Long id, UserVo vo) {
        return true;
    }
}
