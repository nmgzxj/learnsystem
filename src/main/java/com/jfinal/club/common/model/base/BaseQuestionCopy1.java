package com.jfinal.club.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseQuestionCopy1<M extends BaseQuestionCopy1<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Long id) {
		set("id", id);
	}
	
	public java.lang.Long getId() {
		return getLong("id");
	}

	public void setQuestionId(java.lang.String questionId) {
		set("questionId", questionId);
	}
	
	public java.lang.String getQuestionId() {
		return getStr("questionId");
	}

	public void setQuestionTitle(java.lang.String questionTitle) {
		set("questionTitle", questionTitle);
	}
	
	public java.lang.String getQuestionTitle() {
		return getStr("questionTitle");
	}

	public void setQuestionItems(java.lang.String questionItems) {
		set("questionItems", questionItems);
	}
	
	public java.lang.String getQuestionItems() {
		return getStr("questionItems");
	}

	public void setQuestionAnswer(java.lang.String questionAnswer) {
		set("questionAnswer", questionAnswer);
	}
	
	public java.lang.String getQuestionAnswer() {
		return getStr("questionAnswer");
	}

	public void setQuestionType(java.lang.String questionType) {
		set("questionType", questionType);
	}
	
	public java.lang.String getQuestionType() {
		return getStr("questionType");
	}

	public void setKeywords(java.lang.String keywords) {
		set("keywords", keywords);
	}
	
	public java.lang.String getKeywords() {
		return getStr("keywords");
	}

	public void setIsPassed(java.lang.String isPassed) {
		set("isPassed", isPassed);
	}
	
	public java.lang.String getIsPassed() {
		return getStr("isPassed");
	}

	public void setCreateBy(java.lang.String createBy) {
		set("createBy", createBy);
	}
	
	public java.lang.String getCreateBy() {
		return getStr("createBy");
	}

	public void setCreateTime(java.util.Date createTime) {
		set("createTime", createTime);
	}
	
	public java.util.Date getCreateTime() {
		return get("createTime");
	}

	public void setModifyBy(java.lang.String modifyBy) {
		set("modifyBy", modifyBy);
	}
	
	public java.lang.String getModifyBy() {
		return getStr("modifyBy");
	}

	public void setModifyTime(java.util.Date modifyTime) {
		set("modifyTime", modifyTime);
	}
	
	public java.util.Date getModifyTime() {
		return get("modifyTime");
	}

	public void setAuditBy(java.lang.String auditBy) {
		set("auditBy", auditBy);
	}
	
	public java.lang.String getAuditBy() {
		return getStr("auditBy");
	}

	public void setAuditTime(java.util.Date auditTime) {
		set("auditTime", auditTime);
	}
	
	public java.util.Date getAuditTime() {
		return get("auditTime");
	}

}
