package com.gdb.learn.springmvc.mapper;

import com.gdb.learn.springmvc.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    List<User> queryUserList();

    User queryUserById(int id);
}
