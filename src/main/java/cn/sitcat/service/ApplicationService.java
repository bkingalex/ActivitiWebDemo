package cn.sitcat.service;

import cn.sitcat.bean.Application;
import cn.sitcat.bean.ApplicationQueryVo;
import cn.sitcat.bean.User;

import java.util.List;

public interface ApplicationService {
     List<ApplicationQueryVo> findApplicationList(String status, User applicant);

    Application findById(Integer applicationId);
}
