package cn.sitcat.service.Impl;

import cn.sitcat.bean.Template;
import cn.sitcat.bean.TemplateExample;
import cn.sitcat.dao.TemplateMapper;
import cn.sitcat.service.TemplateService;
import cn.sitcat.utils.UploadFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Hiseico
 * @create 2018-06-12 15:03
 * @desc
 **/
@Service
@Transactional
public class TemplateServiceImpl implements TemplateService {
    @Autowired
    private TemplateMapper templateMapper;

    /**
     * @return
     */
    @Override
    public List<Template> findList() {
        TemplateExample templateExample = new TemplateExample();
        TemplateExample.Criteria criteria = templateExample.createCriteria();
        List<Template> templateList = templateMapper.selectByExample(templateExample);
        return templateList;
    }

    /**
     * 保存模板信息
     *
     * @param template
     */
    @Override
    public void save(Template template, MultipartFile source) {
//        将上传的文件保存到指定目录中
        String fileSavePath = "J:\\CodeSpace\\activitiWeb20180608\\src\\main\\webapp\\upload\\";
        try {
            String docFilePath = UploadFileUtils.uploadFile(source, fileSavePath);
            template.setDocfilepath(docFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.templateMapper.insert(template);
    }


    /**
     * 根据id删除记录，同时删除模板文件
     *
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
        //调用Service中的findById方法
        Template template = this.findById(id);
        //获取文件路径
        String docfilepath = template.getDocfilepath();
        File file = new File(docfilepath);
        //如果文件存在 再删除
        if (file.exists()) {
            file.delete();
        }
        this.templateMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据id查询模板信息
     */
    @Override
    public Template findById(Integer id) {
        //根据id查询记录
        TemplateExample example = new TemplateExample();
        TemplateExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<Template> templateList = this.templateMapper.selectByExample(example);
        Template template = templateList.get(0);
        return template;
    }

    @Override
    public void update(MultipartFile resource, Template template) {
        Template template1 = this.findById(template.getId());
        template1.setName(template.getName());
        template1.setPdkey(template.getPdkey());

        //如果resource不为null，用户上传了新文件
        if (resource != null) {
            //    删除原有文件
            String docFilePath = template1.getDocfilepath();
            File file = new File(docFilePath);
            if (file.exists()) {
                file.delete();
            }
            String fileSavePath = "J:\\CodeSpace\\activitiWeb20180608\\src\\main\\webapp\\upload\\";
            try {
                String uploadFilePath = UploadFileUtils.uploadFile(resource, fileSavePath);
                template1.setDocfilepath(uploadFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.templateMapper.updateByPrimaryKey(template1);
        }
    }
}
