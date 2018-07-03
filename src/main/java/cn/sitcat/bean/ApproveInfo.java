package cn.sitcat.bean;

import java.util.Date;

/**
 * 审批实体
 * @author hiseico
 *
 */
public class ApproveInfo implements java.io.Serializable{
	private Integer id;
	private User approver;//审批人
	private Integer approverId;//审批人id
	private Date approveDate;//审批时间
	private Boolean approval;//是否通过
	private String comment;//审批意见
	private Application application;//当前审批对应的申请实体
	private Integer application_id;//当前审批对应的申请实体id
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public User getApprover() {
		return approver;
	}
	public void setApprover(User approver) {
		this.approver = approver;
	}
	public Date getApproveDate() {
		return approveDate;
	}
	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}
	public Boolean getApproval() {
		return approval;
	}
	public void setApproval(Boolean approval) {
		this.approval = approval;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Application getApplication() {
		return application;
	}
	public void setApplication(Application application) {
		this.application = application;
	}

	public Integer getApproverId() {
		return approverId;
	}

	public void setApproverId(Integer approverId) {
		this.approverId = approverId;
	}

	public Integer getApplication_id() {
		return application_id;
	}

	public void setApplication_id(Integer application_id) {
		this.application_id = application_id;
	}
}
