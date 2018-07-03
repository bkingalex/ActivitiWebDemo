package cn.sitcat.bean;

import java.util.Date;
import java.util.Set;

/**
 * 申请实体
 * @author hiseico
 *
 */
public class Application implements java.io.Serializable {
	private Integer id;
	private String title;//申请的标题 格式：{模板名称}_{姓名}_{日期}
	private User applicant;//申请人
	private Date applyDate;//申请日期
	private String status;//申请状态
	private String docFilePath;//doc文件路径
	private Set<ApproveInfo> approveInfos;//当前申请对应的多个审批实体
	private Template template;//当前申请使用的模板对象
	
	public static final String STATUS_APPROVING = "审批中";
	public static final String STATUS_UNAPPROVED = "未通过";
	public static final String STATUS_APPROVED = "已通过";
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public User getApplicant() {
		return applicant;
	}
	public void setApplicant(User applicant) {
		this.applicant = applicant;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDocFilePath() {
		return docFilePath;
	}
	public void setDocFilePath(String docFilePath) {
		this.docFilePath = docFilePath;
	}
	public Template getTemplate() {
		return template;
	}
	public void setTemplate(Template template) {
		this.template = template;
	}
	
}
