package cn.sitcat.bean;

import org.activiti.engine.task.Task;

import java.io.Serializable;

/**
 * @author Hiseico
 * @create 2018-07-02 10:37
 * @desc 包装任务信息和申请信息
 **/
public class TaskView implements Serializable{
    private Task task;
    private Application application;

    public TaskView() {
    }

    public TaskView(Task task, Application application) {
        this.task = task;
        this.application = application;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}