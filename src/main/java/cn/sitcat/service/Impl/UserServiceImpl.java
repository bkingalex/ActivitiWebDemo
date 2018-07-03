package cn.sitcat.service.Impl;

import cn.sitcat.bean.User;
import cn.sitcat.bean.UserExample;
import cn.sitcat.dao.UserMapper;
import cn.sitcat.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Hiseico
 * @create 2018-06-10 16:08
 * @desc
 **/
@Service
@Transactional
public class UserServiceImpl implements userService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> login(User user) {
            UserExample userExample = new UserExample();
            UserExample.Criteria criteria = userExample.createCriteria();
            criteria.andLoginnameEqualTo(user.getLoginname());
            criteria.andPasswordEqualTo(user.getPassword());
            List<User> userList = userMapper.selectByExample(userExample);
            return userList;
    }
}
