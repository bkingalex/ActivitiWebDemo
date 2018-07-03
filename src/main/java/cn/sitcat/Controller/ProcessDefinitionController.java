package cn.sitcat.Controller;

import cn.sitcat.service.ProcessDefinitionService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @author Hiseico
 * @create 2018-06-11 9:17
 * @desc 流程定义管理Action
 **/
@Controller
@Scope("prototype")
public class ProcessDefinitionController {
    @Autowired
    private ProcessDefinitionService processDefinitionService;

    /**
     * 查询最新版本的流程定义列表
     *
     * @param model
     * @return
     */
    @RequestMapping("/processDefinitionAction_list")
    public String list(Model model) {
        List<ProcessDefinition> list = processDefinitionService.findLastList();
        model.addAttribute("pdList", list);
        return "/pd/list";
    }

    /**
     * 根据key删除流程定义
     *
     * @return
         */
    @RequestMapping("/processDefinitionAction_deleteByKey")
    public String deleteByKey(String key) {
        processDefinitionService.deleyeByKey(key);
        return "redirect:/processDefinitionAction_list.do";
    }

    /**
     * 部署流程定义功能（使用zip文件）
     */
    @RequestMapping("/processDefinitionAction_deploy")
    public String deply(MultipartFile resource){
        processDefinitionService.deploy(resource);
            return "redirect:/processDefinitionAction_list.do";
    }

    /**
     * 显示png图片 （文件下载）
     * @param pdId
     * @return
     */
    @RequestMapping("/processDefinitionAction_showImg")
    public void showImg(HttpServletResponse httpServletResponse, String pdId) throws IOException {
            //从数据库中获取流程图的二进制数据
            InputStream inputStream=processDefinitionService.findImgStream(pdId);
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100]; //buff用于存放循环读取的临时数据
        int rc = 0;
        //将输入流转换为字符数组输出流
        while ((rc = inputStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] img = swapStream.toByteArray(); //in_b为转换之后的结果

        //设置响应头
        //httpServletResponse.setHeader("content-type", "image/png;charset=UTF-8");
        httpServletResponse.setContentType("image/png");
        OutputStream os = httpServletResponse.getOutputStream();
        os.write(img);
        os.flush();
        os.close();
    }
}
