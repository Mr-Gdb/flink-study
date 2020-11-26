package com.gdb.learn.springmvc.vo;

public class UserVo {
    Long id;
    String userName;

    public Long getId() {
        return this.id;
    }

    public UserVo setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return this.userName;
    }

    public UserVo setUserName(String userName) {
        this.userName = userName;
        return this;
    }
}
