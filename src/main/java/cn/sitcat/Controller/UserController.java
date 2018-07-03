package cn.sitcat.Controller;

import cn.sitcat.bean.User;
import cn.sitcat.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Hiseico
 * @create 2018-06-10 21:13
 * @desc 用户登录Controller
 **/
@Controller
@Scope("prototype")
public class UserController {
    @Autowired
    private userService userService;

    @RequestMapping("/userAction_login")
    public String login(HttpSession session, Model model,User user) {
        List<User> userList = userService.login(user);

//登录成功
        if (userList.size() >0) {
            User activeUser = userList.get(0);
            session.setAttribute("activeUser", activeUser);
            return "/home/index";
        } else {
            //登录失败
//设置错误提示信息
            model.addAttribute("msg", "用户名或密码错误！");
            return "index";
        }
    }

    /**
     * 用户退出系统
     * @return
     */
    @RequestMapping("/userAction_logout")
    public String loginout(HttpSession session){
        session.invalidate();
        return "redirect:/index.jsp";
    }
}
