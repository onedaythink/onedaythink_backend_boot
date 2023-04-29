package com.spring.onedaythink.user.mapper;

import com.spring.onedaythink.user.vo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    public User userIdCheck(User user);

    public User nicknameCheck(User user);

    public int insertUser(User user);

    public User selectLoginUser(User user);

    public User selectUser(User user);

    public List<User> selectListUsers(User user);

    public User updateUser(User user);

    public int deleteUser(User user);

}
