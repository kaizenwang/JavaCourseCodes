package com.kai.shardingSpheredemo;

import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author kaizen
 * @date 2021/09/22
 */
@Mapper
public interface UserMapper {

    @Insert("insert into user(name, avatar, mobile, gmt_create, gmt_update) values(#{name}, #{avatar}, #{mobile}, #{gmtCreate}, #{gmtUpdate})")
    int insert(User user);

    @Results({
            @Result(column = "name", property = "name"),
            @Result(column = "avatar", property = "avatar"),
            @Result(column = "mobile", property = "mobile"),
            @Result(column = "gmt_create", property = "gmtCreate"),
            @Result(column = "gmt_update", property = "gmtUpdate")
    })
    @Select("select * from user")
    List<User> listAll();
}
