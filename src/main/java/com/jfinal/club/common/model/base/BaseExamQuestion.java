package com.jfinal.club.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseExamQuestion<M extends BaseExamQuestion<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Long id) {
		set("id", id);
	}
	
	public java.lang.Long getId() {
		return getLong("id");
	}

	public void setExamId(java.lang.Integer examId) {
		set("exam_id", examId);
	}
	
	public java.lang.Integer getExamId() {
		return getInt("exam_id");
	}

	public void setQuestionId(java.lang.Long questionId) {
		set("question_id", questionId);
	}
	
	public java.lang.Long getQuestionId() {
		return getLong("question_id");
	}

}
