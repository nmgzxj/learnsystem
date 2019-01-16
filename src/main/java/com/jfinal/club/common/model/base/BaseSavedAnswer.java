package com.jfinal.club.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseSavedAnswer<M extends BaseSavedAnswer<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Long id) {
		set("id", id);
	}
	
	public java.lang.Long getId() {
		return getLong("id");
	}

	public void setUserId(java.lang.String userId) {
		set("user_id", userId);
	}
	
	public java.lang.String getUserId() {
		return getStr("user_id");
	}

	public void setExamId(java.lang.Integer examId) {
		set("exam_id", examId);
	}
	
	public java.lang.Integer getExamId() {
		return getInt("exam_id");
	}

	public void setQuestionId(java.lang.Integer questionId) {
		set("question_id", questionId);
	}
	
	public java.lang.Integer getQuestionId() {
		return getInt("question_id");
	}

	public void setAnswer(java.lang.String answer) {
		set("answer", answer);
	}
	
	public java.lang.String getAnswer() {
		return getStr("answer");
	}

	public void setOfficialId(java.lang.String officialId) {
		set("official_id", officialId);
	}
	
	public java.lang.String getOfficialId() {
		return getStr("official_id");
	}

	public void setUserScore(java.lang.Double userScore) {
		set("user_score", userScore);
	}
	
	public java.lang.Double getUserScore() {
		return getDouble("user_score");
	}

	public void setAnswerNo(java.lang.String answerNo) {
		set("answer_no", answerNo);
	}
	
	public java.lang.String getAnswerNo() {
		return getStr("answer_no");
	}

}
