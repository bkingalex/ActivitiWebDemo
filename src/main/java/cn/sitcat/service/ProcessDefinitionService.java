package cn.sitcat.service;

import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface ProcessDefinitionService {

    List<ProcessDefinition> findLastList();

    void deleyeByKey(String key);

    void deploy(MultipartFile resource);

    InputStream findImgStream(String pdId);
}
