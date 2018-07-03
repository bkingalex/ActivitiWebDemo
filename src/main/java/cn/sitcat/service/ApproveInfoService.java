package cn.sitcat.service;

import cn.sitcat.bean.ApproveInfo;

import java.util.List;

public interface ApproveInfoService {
    List<ApproveInfo> findListByApplicationId(Integer applicationId);
}
