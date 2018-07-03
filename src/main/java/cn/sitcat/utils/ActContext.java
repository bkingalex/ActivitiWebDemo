package cn.sitcat.utils;

import cn.sitcat.bean.User;

import javax.mail.Session;
import javax.servlet.http.HttpSession;

/**
 * @author Hiseico
 * @create 2018-06-26 21:26
 * @desc 获取当前登录用户
 **/
public class ActContext {
    public static User getLoginUser(HttpSession session) {
        return (User) session.getAttribute("activeUser");
    }
}
