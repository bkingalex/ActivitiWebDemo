package cn.sitcat.bean;

import java.io.Serializable;

/**
 * @author Hiseico
 * @create 2018-06-26 21:59
 * @desc 申请实体扩展类
 **/
public class ApplicationQueryVo extends Application implements Serializable{
    private Integer userId;
    private Integer templateId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }
}
