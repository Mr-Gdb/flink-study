package com.gdb.learn.springmvc.controller;


import com.gdb.learn.springmvc.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/user")
@Api("用户界面")
public class UserController {

    @GetMapping("")
    @ApiOperation(value = "获取用户列表")
    public List<UserVo> list() {
        List<UserVo> result = new ArrayList<>();
        result.add(new UserVo().setId(1L).setUserName("yudaoyuanma1111"));
        result.add(new UserVo().setId(2L).setUserName("woshiyutou"));
        result.add(new UserVo().setId(3L).setUserName("chifanshuijiao"));
        // 返回列表
        return result;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取用户")
    public UserVo get(@PathVariable("id") Long id) {
        return new UserVo().setId(id).setUserName("username:" + id);
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
