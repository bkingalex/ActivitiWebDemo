package cn.sitcat.service.Impl;

import cn.sitcat.bean.*;
import cn.sitcat.dao.ApplicationMapper;
import cn.sitcat.dao.ApproveInfoMapper;
import cn.sitcat.service.FlowService;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.peer.PanelPeer;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hiseico
 * @create 2018-06-26 21:45
 * @desc 流程控制Service
 **/
@Service
@Transactional
public class FlowServiceImpl implements FlowService {
    @Autowired
    private ApplicationMapper applicationMapper;
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private ApproveInfoMapper approveInfoMapper;

    /**
     * 提交申请
     *
     * @param application
     */
    @Override
    public void submit(Application application) {

        //3.保存一个申请实体
        //将Application放到ApplicationQueryVo包装类中
        ApplicationQueryVo queryVo = new ApplicationQueryVo();
        queryVo.setTitle(application.getTitle());
        queryVo.setTemplate(application.getTemplate());
        queryVo.setApplicant(application.getApplicant());
        queryVo.setStatus(application.getStatus());
        queryVo.setApplyDate(application.getApplyDate());
        queryVo.setDocFilePath(application.getDocFilePath());
        queryVo.setUserId(application.getApplicant().getId());
        queryVo.setTemplateId(application.getTemplate().getId());
        int id = applicationMapper.save(queryVo);
        queryVo.setId(id);
        application.setId(id);
        //4.启动一个流程实例
        String key = application.getTemplate().getPdkey();
        Map<String, Object> variables = new HashMap<>();
        variables.put("application", application);
        variables.put("applicationId", queryVo.getId());
        ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey(key, variables);

        //5.办理第一个提交任务
        TaskQuery query = processEngine.getTaskService().createTaskQuery();
        query.taskAssignee(application.getApplicant().getLoginname());
        query.processInstanceId(processInstance.getId());
        Task task = query.singleResult();
        String taskId = task.getId();
        processEngine.getTaskService().complete(taskId);
    }

    /**
     * 通过部署id和图片名，获取流程图流
     *
     * @param deploymentId
     * @param imageName
     * @return
     */
    @Override
    public InputStream getPngStream(String deploymentId, String imageName) {
        return processEngine.getRepositoryService().getResourceAsStream(deploymentId, imageName);
    }

    /**
     * 根据申请id'（流程变量） 查询任务
     *
     * @param applicationId
     * @return
     */
    @Override
    public Task findTaskByApplicaionId(Integer applicationId) {
        TaskQuery taskQuery = processEngine.getTaskService().createTaskQuery();
        //根据设置的流程变量进行过客V
        taskQuery.processVariableValueEquals("applicationId", applicationId);
        return taskQuery.singleResult();
    }


    /**
     * 根据任务查询流程定义对象
     *
     * @param task
     * @return
     */
    @Override
    public ProcessDefinition findPDByTask(Task task) {
        //获得流程定义id
        String processDefinitionId = task.getProcessDefinitionId();
        //通过查询得出的流程定义对象 processdef表的基本内容
        ProcessDefinitionQuery query = processEngine.getRepositoryService().createProcessDefinitionQuery();
        query.processDefinitionId(processDefinitionId);
        return query.singleResult();
    }

    /**
     * 根据任务查询坐标
     *
     * @param task
     * @return
     */
    @Override
    public Map<String, Object> findCoordingByTask(Task task) {
        //获得流程定义id
        String processDefinitionId = task.getProcessDefinitionId();
        //获得流程实例id
        String processInstanceId = task.getProcessInstanceId();
        //获得流程定义对象 获得比processdef 更详细的流程定义对象 。返回的流程定义对象包含坐标信息
        ProcessDefinitionEntity pd = (ProcessDefinitionEntity) processEngine.getRepositoryService().getProcessDefinition(processDefinitionId);

        //根据流程实例id获取流程实例对象，通过流程实例获得活动节点  在执行表excution表中有一个ACT_ID_的字段就是活动节点
        ProcessInstanceQuery query = processEngine.getRuntimeService().createProcessInstanceQuery();
        //根据流程实例id过滤
        query.processInstanceId(processInstanceId);
        ProcessInstance processInstance = query.singleResult();
        //根据当前流程实例对象获得当前的活动节点
        String activityId = processInstance.getActivityId();

        ActivityImpl activity = pd.findActivity(activityId);
        Map<String, Object> map = new HashMap<>();
        map.put("x", activity.getX() * 0);
        map.put("y", activity.getY() - 100);
        map.put("width", activity.getWidth() * 2);
        map.put("height", activity.getHeight());
        return map;
    }

    /**
     * 根据当前登录人查询对应的任务列表，包装成List<TaskView>返回
     *
     * @param loginUser
     * @return
     */
    @Override
    public List<TaskView> findList(User loginUser) {
        TaskQuery taskQuery = processEngine.getTaskService().createTaskQuery();
        taskQuery.taskAssignee(loginUser.getLoginname());//添加过滤条件
        taskQuery.orderByTaskCreateTime().desc();//添加排序条件
        List<Task> taskList = taskQuery.list();
        List<TaskView> taskViewList = new ArrayList<>();
        for (Task task : taskList) {
            String taskId = task.getId();
            //根据任务id获取 获取申请信息
            Application application = (Application) processEngine.getTaskService().getVariable(taskId, "application");
            TaskView taskView = new TaskView(task, application);
            taskViewList.add(taskView);
        }
        return taskViewList;
    }

    /**
     * 审批处理
     *
     * @param approveInfo
     * @param taskId
     */
    @Override
    public void approve(ApproveInfo approveInfo, String taskId) {
        Task task = processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();
        //获得流程实例id
        String processInstanceId = task.getProcessInstanceId();

        //1.保存审批实体
        int approveInfoId = this.approveInfoMapper.save(approveInfo);

        //2.办理当前任务
        processEngine.getTaskService().complete(taskId);

        //查询当前流程实例是否存在。如果不存在说明当前办理人为最后一个节点
        ProcessInstance processInstance = processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

        if (approveInfo.getApproval()) { //3.如果审批结果为通过
            if (processInstance == null) {//流程实例为空，代表流程结束。当前办理人为最后一个节点，将申请的状态修改为已通过
                Application application = new Application();
                application.setId(approveInfo.getApplication_id());
                application.setStatus(Application.STATUS_APPROVED);
                this.applicationMapper.updateStatusById(application);
            }
        } else { //如果审批结果为不通过
            //将申请的状态修改为“不通过”，
            Application application = new Application();
            application.setId(approveInfo.getApplication_id());
            application.setStatus(Application.STATUS_UNAPPROVED);
            this.applicationMapper.updateStatusById(application);
            //如果当前办理人不是最后一个节点，手动结束流程
            if(processInstance!=null){
                processEngine.getRuntimeService().deleteProcessInstance(processInstanceId,Application.STATUS_UNAPPROVED);
            }
        }
    }
}
