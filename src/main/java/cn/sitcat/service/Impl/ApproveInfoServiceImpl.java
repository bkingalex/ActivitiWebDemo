package cn.sitcat.service.Impl;

import cn.sitcat.bean.ApproveInfo;
import cn.sitcat.dao.ApproveInfoMapper;
import cn.sitcat.service.ApproveInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Hiseico
 * @create 2018-06-29 20:05
 * @desc 审批对象
 **/
@Service
public class ApproveInfoServiceImpl implements ApproveInfoService {
    @Autowired
    private ApproveInfoMapper approveInfoMapper;

    @Override
    public List<ApproveInfo> findListByApplicationId(Integer applicationId) {
        return approveInfoMapper.selectListByApplicationId(applicationId);
    }
}
