package com.jfinal.club.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseAnswer<M extends BaseAnswer<M>> extends Model<M> implements IBean {

	public void setId(Long id) {
		set("id", id);
	}

	public Long getId() {
		return get("id");
	}

	public void setUserId(String userId) {
		set("user_id", userId);
	}

	public String getUserId() {
		return get("user_id");
	}

	public void setExamId(Integer examId) {
		set("exam_id", examId);
	}

	public Integer getExamId() {
		return get("exam_id");
	}

	public void setQuestionId(Long questionId) {
		set("question_id", questionId);
	}

	public Long getQuestionId() {
		return get("question_id");
	}

	public void setAnswer(String answer) {
		set("answer", answer);
	}

	public String getAnswer() {
		return get("answer");
	}

	public void setOfficialId(String officialId) {
		set("official_id", officialId);
	}

	public String getOfficialId() {
		return get("official_id");
	}

	public void setUserScore(Float userScore) {
		set("user_score", userScore);
	}

	public Float getUserScore() {
		return get("user_score");
	}

	public void setAnswerNo(String answerNo) {
		set("answer_no", answerNo);
	}

	public String getAnswerNo() {
		return get("answer_no");
	}

}
