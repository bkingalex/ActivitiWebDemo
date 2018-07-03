package cn.sitcat.service;

import cn.sitcat.bean.Application;
import cn.sitcat.bean.ApproveInfo;
import cn.sitcat.bean.TaskView;
import cn.sitcat.bean.User;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface FlowService {
    public void submit(Application application);

    InputStream getPngStream(String deploymentId, String imageName);

    Task findTaskByApplicaionId(Integer applicationId);

    ProcessDefinition findPDByTask(Task task);

    Map<String,Object> findCoordingByTask(Task task);

    List<TaskView> findList(User loginUser);

    void approve(ApproveInfo approveInfo, String taskId);
}
