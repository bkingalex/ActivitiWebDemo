package cn.sitcat.service;

import cn.sitcat.bean.Template;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TemplateService {
    List<Template> findList();

    void save(Template template, MultipartFile source);

    void deleteById(Integer id);

    Template findById(Integer id);

    void update(MultipartFile resource, Template template);
}
