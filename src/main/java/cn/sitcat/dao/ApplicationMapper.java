package cn.sitcat.dao;

import cn.sitcat.bean.Application;
import cn.sitcat.bean.ApplicationQueryVo;

import java.util.List;

public interface ApplicationMapper {
    public int save(ApplicationQueryVo applicationQueryVo);

    public List<ApplicationQueryVo> selectByUserOrstatus(ApplicationQueryVo applicationQueryVo);

    public Application selectById(Integer applicationId);

    public void updateStatusById(Application application);
}
