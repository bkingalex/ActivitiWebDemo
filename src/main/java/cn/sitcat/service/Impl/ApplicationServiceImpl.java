package cn.sitcat.service.Impl;

import cn.sitcat.bean.Application;
import cn.sitcat.bean.ApplicationQueryVo;
import cn.sitcat.bean.User;
import cn.sitcat.dao.ApplicationMapper;
import cn.sitcat.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Hiseico
 * @create 2018-06-27 15:41
 * @desc
 **/
@Service
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    private ApplicationMapper applicationMapper;

    @Override
    public List<ApplicationQueryVo> findApplicationList(String status, User applicant) {
        ApplicationQueryVo queryVo = new ApplicationQueryVo();
        queryVo.setUserId(applicant.getId());
        if (status != null && status != "") {
            queryVo.setStatus(status);
        }
        return applicationMapper.selectByUserOrstatus(queryVo);
    }

    @Override
    public Application findById(Integer applicationId) {
       return this.applicationMapper.selectById(applicationId);
    }
}
