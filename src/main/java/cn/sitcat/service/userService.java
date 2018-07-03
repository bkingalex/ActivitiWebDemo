package cn.sitcat.service;

import cn.sitcat.bean.User;

import java.util.List;

/**
 * @author Hiseico
 * @create 2018-06-10 16:08
 * @desc
 **/
public interface userService {
    List<User> login(User user);
}
