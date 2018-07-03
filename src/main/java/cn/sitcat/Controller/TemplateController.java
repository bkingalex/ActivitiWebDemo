package cn.sitcat.Controller;

import cn.sitcat.bean.Template;
import cn.sitcat.service.ProcessDefinitionService;
import cn.sitcat.service.TemplateService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;


/**
 * @author Hiseico
 * @create 2018-06-12 14:57
 * @desc 表单模板管理Controller
 **/
@Controller
@Scope("prototype")
public class TemplateController {
    @Autowired
    private TemplateService templateService;
    @Autowired
    private ProcessDefinitionService processDefinitionService;

    /**
     * 查询表单模板列表
     *
     * @return
     */
    @RequestMapping("/templateAction_list")
    public String list(Model model) {
        List<Template> list = templateService.findList();
        model.addAttribute("templateList", list);
        return "/template/list";
    }

    /**
     * 跳转到添加页面 并携带流程定义列表数据
     *
     * @param model
     * @return
     */
    @RequestMapping("/templateAction_addUI")
    public String addUI(Model model) {
        //准备定义流程列表数据，用于填充下拉框
        List<ProcessDefinition> pdList = processDefinitionService.findLastList();
        model.addAttribute("pdList", pdList);
        return "/template/addUI";
    }

    @RequestMapping("/templateAction_add")
    public String add(Model model, Template template, MultipartFile source) {
        templateService.save(template, source);
        return "redirect:/templateAction_list.do";
    }

    /**
     * 根据id删除表单模板，同时删除对应的模板文件
     *
     * @param id
     * @return
     */
    @RequestMapping("/templateAction_del")
    public String delById(Integer id) {
        templateService.deleteById(id);
        return "redirect:/templateAction_list.do";
    }

    /**
     * 跳转到修改页面
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/templateAction_edit")
    public String edit(Model model, Integer id) {
        //根据id查询要修改的数据，回填到修改页面
        Template template = templateService.findById(id);
        model.addAttribute("template", template);

        //准备定义流程列表数据，用于填充下拉框
        List<ProcessDefinition> pdList = processDefinitionService.findLastList();
        model.addAttribute("pdList", pdList);
        return "/template/editUI";
    }

    @RequestMapping("/templateAction_update")
    public String update(MultipartFile resource, Template template) {
        templateService.update(resource, template);
        return "redirect:/templateAction_list.do";
    }

    /**
     * 下载doc文件
     *
     * @param httpServletResponse
     * @param id
     */
    @RequestMapping("/templateAction_download")
    public void download(HttpServletResponse httpServletResponse, Integer id) {
        Template template = templateService.findById(id);
        String docfilepath = template.getDocfilepath();
        //截取路径中的文件名
        String fileName = docfilepath.substring(docfilepath.lastIndexOf("\\")+1);
        try {
            //读取服务器磁盘上文件的二进制数据
            InputStream docStream = new FileInputStream(new File(docfilepath));
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[10000]; //buff用于存放循环读取的临时数据
            int rc = 0;
            while ((rc = docStream.read(buff, 0, 10000)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            //将输入流转换为字符数组输出流
            byte[] docByte = swapStream.toByteArray();
            //设置响应头
            httpServletResponse.setContentType("application/octet-stream; charset=utf-8");
            httpServletResponse.setHeader("Content-Disposition", "attachment; filename="+fileName);
            OutputStream os = httpServletResponse.getOutputStream();
            os.write(docByte);
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
