package cn.sitcat.bean;

import java.io.Serializable;

public class Template implements Serializable {
    private Integer id;

    private String name;

    private String pdkey;

    private String docfilepath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPdkey() {
        return pdkey;
    }

    public void setPdkey(String pdkey) {
        this.pdkey = pdkey == null ? null : pdkey.trim();
    }

    public String getDocfilepath() {
        return docfilepath;
    }

    public void setDocfilepath(String docfilepath) {
        this.docfilepath = docfilepath == null ? null : docfilepath.trim();
    }
}