package cn.sitcat.Controller;

import cn.sitcat.bean.*;
import cn.sitcat.service.ApplicationService;
import cn.sitcat.service.ApproveInfoService;
import cn.sitcat.service.FlowService;
import cn.sitcat.service.TemplateService;
import cn.sitcat.utils.ActContext;
import cn.sitcat.utils.UploadFileUtils;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Hiseico
 * @create 2018-06-25 23:05
 * @desc 流程控制Controller
 **/

@Controller
@Scope("prototype")
public class FlowController {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private FlowService flowService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ApproveInfoService approveInfoService;

    /**
     * 查询表单模板列表
     *
     * @return
     */
    @RequestMapping("/flowAction_templateList")
    public String list(Model model) {
        List<Template> list = templateService.findList();
        model.addAttribute("templateList", list);
        return "/flow/templateList";
    }

    /**
     * 提交申请
     */
    @RequestMapping("/flowAction_submit")
    public String submit(MultipartFile resource, Integer templateId, HttpSession session) {
        //1.处理上传文件
        String filepath = "J:\\CodeSpace\\activitiWeb20180608\\src\\main\\webapp\\upload\\";
        try {
            filepath = UploadFileUtils.uploadFile(resource, filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //2.包装一个申请实体
        Application application = new Application();
        application.setApplicant(ActContext.getLoginUser(session));//申请人，当前登录用户
        application.setApplyDate(new Date());//申请时间
        application.setDocFilePath(filepath);//doc文件存储路径
        application.setStatus(Application.STATUS_APPROVING);//设置申请状态 状态为审批中

        //设置申请使用的模板对象
        Template template = templateService.findById(templateId);//获取模板对象
        application.setTemplate(template);


        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(application.getApplyDate());//获取申请时间
        String title = template.getName() + "_" + ActContext.getLoginUser(session).getName() + "_" + dateStr;//设置标题
        application.setTitle(title);//申请的标题

        flowService.submit(application);
        return "/flow/templateList";
    }

    /**
     * 跳转到提交申请页面
     *
     * @param templateId
     * @param model
     * @return
     */
    @RequestMapping("/flowAction_submitUI")
    public String submitUI(Integer templateId, Model model) {
        model.addAttribute("templateId", templateId);
        return "/flow/submitUI";
    }

    /**
     * 查询当前登录人的申请记录
     * @param status
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/flowAction_myApplicationList")
    public String myApplicationList(String status, HttpSession session, Model model) {
        User applicant = ActContext.getLoginUser(session);
        //根据当前登录人或状态来查询申请数据
        List<ApplicationQueryVo> applicationList = applicationService.findApplicationList(status, applicant);
        model.addAttribute("applicationList", applicationList);
        model.addAttribute("status", status);
        return "/flow/myApplicationList";
    }

    /**
     * 获取流程图前置准备数据
     *
     * @param applicationId 申请实体id
     * @param model
     * @return
     */
    @RequestMapping("/flowAction_showPng")
    public String showPng(Integer applicationId, Model model) {
        //   根据流程变量查询当前任务
        Task task = flowService.findTaskByApplicaionId(applicationId);

        //根据当前的任务查询对应的流程定义对象
        ProcessDefinition pd = flowService.findPDByTask(task);
        String deploymentId = pd.getDeploymentId();
        String imageName = pd.getDiagramResourceName();
        System.out.println(1);
        //根据任务查询坐标
        Map<String, Object> map = flowService.findCoordingByTask(task);

        model.addAttribute("deploymentId", deploymentId);
        model.addAttribute("imageName", imageName);
        model.addAttribute("x", map.get("x"));
        model.addAttribute("y", map.get("y"));
        model.addAttribute("height", map.get("height"));
        model.addAttribute("width", map.get("width"));

        return "/flow/image";
    }

    /**
     * 查询png流
     */
    @RequestMapping("/flowAction_viewImage")
    public void viewImage(HttpServletResponse httpServletResponse, String imageName, String deploymentId) throws IOException {
        InputStream inputStream = flowService.getPngStream(deploymentId, imageName);
        //    将inpusStream转换为byte数组
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100]; //buff用于存放循环读取的临时数据
        int rc = 0;
        while ((rc = inputStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] img = swapStream.toByteArray(); //in_b为转换之后的结果

        httpServletResponse.setContentType("image/png");
        OutputStream os = httpServletResponse.getOutputStream();
        os.write(img);
        os.flush();
        os.close();
    }

    /**
     * 查询流转记录（根据申请id查询多个审批对象）
     *
     * @param applicationId
     * @param model
     * @return
     */
    @RequestMapping("/flowAction_approveInfoList")
    public String approveInfoList(Integer applicationId, Model model) {
        List<ApproveInfo> list = approveInfoService.findListByApplicationId(applicationId);
        model.addAttribute("list", list);
        return "/flow/approveInfoList";
    }

    /**
     * 查询待我审批任务（申请+任务）列表
     *
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/flowAction_myTaskList")
    public String myTaskList(Model model, HttpSession session) {
        List<TaskView> list = flowService.findList(ActContext.getLoginUser(session));
        model.addAttribute("list", list);
        return "/flow/myTaskList";
    }

    /**
     * 跳转到审批页面
     *
     * @param taskId
     * @param applicationId
     * @param model
     * @return
     */
    @RequestMapping("/flowAction_approveUI")
    public String approveUI(String taskId, Integer applicationId, Model model) {
        model.addAttribute("taskId", taskId);
        model.addAttribute("applicationId", applicationId);
        return "/flow/approveUI";
    }

    /**
     * 审批处理
     * @param taskId        任务id
     * @param applicationId 申请id
     * @param comment       审批意见
     * @param approval      是否通过
     * @param session
     * @return
     */
    @RequestMapping("/flowAction_approve")
    public String approve(String taskId, Integer applicationId, String comment, boolean approval,HttpSession session) {
        //1.包装一个审批实体并保存
        ApproveInfo approveInfo = new ApproveInfo();
        approveInfo.setApplication_id(applicationId);//审批对应的申请实体
        approveInfo.setApproval(approval);//是否通过
        approveInfo.setApproveDate(new Date());//审批时间
        approveInfo.setApproverId(ActContext.getLoginUser(session).getId());//设置审批人id
        approveInfo.setComment(comment);//审批意见

        flowService.approve(approveInfo,taskId);

        return "redirect:flowAction_myTaskList.do";
    }
}
