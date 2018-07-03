package cn.sitcat.dao;

import cn.sitcat.bean.ApproveInfo;

import java.util.List;

public interface ApproveInfoMapper {
    public List<ApproveInfo> selectListByApplicationId(Integer ApplicationId);

    public int save(ApproveInfo approveInfo);
}
