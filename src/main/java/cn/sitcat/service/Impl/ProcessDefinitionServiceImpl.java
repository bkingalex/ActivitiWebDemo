package cn.sitcat.service.Impl;

import cn.sitcat.service.ProcessDefinitionService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.util.*;
import java.util.zip.ZipInputStream;

/**
 * @author Hiseico
 * @create 2018-06-11 9:20
 * @desc 流程定义管理的service
 **/
@Service
public class ProcessDefinitionServiceImpl implements ProcessDefinitionService {
@Autowired
    private ProcessEngine processEngine;

    /**
     * 查询最新版本的流程定义列表
     * @return
     */
    @Override
    public List<ProcessDefinition> findLastList() {
        ProcessDefinitionQuery query = processEngine.getRepositoryService().createProcessDefinitionQuery();
    //    按照版本升序排列
        List<ProcessDefinition> list = query.list();
        Map<String,ProcessDefinition> map = new HashMap<>();
        for(ProcessDefinition pd:list){
            map.put(pd.getKey(),pd);
        }
        return new ArrayList<ProcessDefinition>(map.values());
    }

    /**
     * 根据key删除流程定义
     * @param key
     */
    @Override
    public void deleyeByKey(String key) {
//        根据key查询流程定义列表
        ProcessDefinitionQuery query = processEngine.getRepositoryService().createProcessDefinitionQuery();
        query.processDefinitionKey(key);
        List<ProcessDefinition> list = query.list();
        for (ProcessDefinition pd:list) {
            //通过流程定义中的部署id，删除部署对象
            String deloymentId = pd.getDeploymentId();
            processEngine.getRepositoryService().deleteDeployment(deloymentId,true);//第二个参数代表级联删除
        }
    }

    /**
     * 部署流程定义 使用zip进行部署
     * @param resource
     */
    @Override
    public void deploy(MultipartFile resource) {
        //将MultipartFile对象转换为CommonsMultipartFile对象
        CommonsMultipartFile cf = (CommonsMultipartFile)resource;
        //获取上传文件的信息
        DiskFileItem fi = (DiskFileItem) cf.getFileItem();
        //获取MultipartFile对象的文件信息
        File file = fi.getStoreLocation();
        //获取MultipartFile对象的文件全路径，包括文件名
        String filePath = fi.getStoreLocation().getPath();
        //截取MultipartFile对象文件路径，不包括文件名
        int stringIndex=filePath.lastIndexOf("\\");
        filePath=filePath.substring(0,stringIndex);
        //创建一个新的临时文件，用于保存MultipartFile对象转换成File对象的内容
        File bpmnFile = new File(filePath+File.separator+UUID.randomUUID()+".zip");

        try {
            //将MultipartFile转换成File对象
            FileUtils.copyInputStreamToFile(resource.getInputStream(), bpmnFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //部署流程定义
        DeploymentBuilder deploymentBuilder = processEngine.getRepositoryService().createDeployment();
        ZipInputStream zipInputStream= null;
        try {
            zipInputStream = new ZipInputStream(new FileInputStream(bpmnFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        deploymentBuilder.addZipInputStream(zipInputStream);
        deploymentBuilder.deploy();
    }

    /**
     * 根据流程定义id 获取png图片的输入流
     * @param pdId
     * @return
     */
    @Override
    public InputStream findImgStream(String pdId) {
        //获取部署定义中的流程图的二进制流
        return  processEngine.getRepositoryService().getProcessDiagram(pdId);
    }
}
