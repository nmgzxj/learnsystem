 package com.cjih.learnsystem.savedAnswer;


import java.util.List;
import java.util.UUID;

import com.cjih.learnsystem.common.util.Encrypt;
import com.jfinal.aop.Before;
import com.jfinal.club.common.controller.BaseController;
import com.jfinal.club.common.model.Answer;
import com.jfinal.club.common.model.Question;
import com.jfinal.club.common.model.QuestionFlow;
import com.jfinal.club.common.model.SavedAnswer;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;

public class SavedAnswerController extends BaseController{
	
SavedAnswerService srv = SavedAnswerService.me;
	
	public void index() {
		Page<SavedAnswer> savedAnswerPage = srv.paginate(getParaToInt("p", 1), getLoginAccountId());
		setAttr("savedAnswerPage", savedAnswerPage);
		render("index.html");
	}
	

	/**
	 * 提交修改
	 */
	@Before(SavedAnswerValidator.class)
	public void update() {
		SavedAnswer savedAnswer = getBean(SavedAnswer.class);
		Ret ret = srv.update(savedAnswer);
		renderJson(ret);
	}

	public void save() {
		SavedAnswer savedAnswer = getBean(SavedAnswer.class);
		Ret ret = srv.save(savedAnswer);
		renderJson(ret);
	}

	public void delete() {
		Ret ret = srv.delete(getParaToInt("id"));
		renderJson(ret);
	}

	public void add() {
		render("add.html");
	}
	
	public void edit() {
	String handle = getPara("handle");
	if(StrKit.notBlank(handle)) {
		answer();//保存答案
	}
	String answer_no = getPara("answer_no");
	// String answer_id = getPara("answer_id");
	Integer examId = getParaToInt("examId");
	String lastPage = getHeader("Referer");
	String userId = Integer.toString(getLoginAccountId());
	System.out.println("我来自：" + lastPage);
	String page = getPara("newpage");
	List<Question> questions = Question.dao
			.find("select id,questionId,questionTitle,questionItems,questionAnswer,questionType from tquestion\n"
					+ "    where id in (select question_id from texam_question where exam_id = '" + examId + "')");// .getQuestionByExam(examId);
	if (StrKit.isBlank(page)) {
		page = "0";
	}

	Long answerId=0l;
	String answer_str = "";
	/*
	 * 第一次从列表进入答题的时候，在答题表中初始化所有的数据。
	 */
	if (lastPage != null && lastPage.endsWith("/admin/answer")) {
		answer_no = UUID.randomUUID().toString();
		int i = 0;
		for (Question q : questions) {
			Answer answer = new Answer();
//			answer.setId(UUID.randomUUID().toString());
			answer.setUserId(userId);
			answer.setExamId(examId);
			answer.setQuestionId(q.getId().intValue());
			answer.setAnswerNo(answer_no);
			answer.setAnswer("");
			answer.save();// .insert(answer);
			if (i == 0)
				answerId = answer.getId();
			i++;
		}

	} else {

		String sql = "select id, user_id, exam_id, question_id, answer, official_id, user_score, answer_no from tanswer\n"
				+ "    where answer_no = '" + answer_no + "' and exam_id = '" + examId + "' and user_id= '" + userId
				+ "' and question_id = '" + questions.get(Integer.valueOf(page).intValue()).getId() + "'";
		Answer answer = Answer.dao.findFirst(sql);// .getAnswerByNo(,,,;
		try {
			answerId = answer.getId();
			answer_str = answer.getAnswer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	setAttr("question_size", questions.size());
	setAttr("answer_no", answer_no);
	setAttr("examId", examId);
	setAttr("answerId", answerId);
	setAttr("page", page);
//	 setAttr("answer", answer);
	Question q = questions.get(Integer.valueOf(page).intValue());
	q.setQuestionTitle(Encrypt.decode(q.getQuestionTitle()));
	q.setQuestionItems(Encrypt.decode(q.getQuestionItems()));
	q.setQuestionAnswer(Encrypt.decode(q.getQuestionAnswer()));

	setAttr("question", q);
	setAttr("flows",QuestionFlow.dao.find("select * from tquestion_flow where question_id="+questions.get(Integer.valueOf(page).intValue()).getId()));
	setAttr("answer_str", answer_str);
	render("edit.html");
}
	public void answer() {
	String answer_no = getPara("answer_no");
	String examId = getPara("examId");
	// String userId = getPara("userId");
	Long answerId = getParaToLong("answerId");
//	 Long questionId = getParaToLong("questionId");
	String answers[] = getParaValues("item");
	String answer_str = "";

	Answer answer = Answer.dao.findById(answerId);
//	answer.setId(answerId);
	// answer.setUserId(userId);
	// answer.setExamId(examId);
//	 answer.setQuestionId(questionId);
	if (null != answers) {
		for (String a : answers) {
			if(StrKit.notBlank(a)) {
			answer_str += a;
			if(!a.endsWith(";")) {
				answer_str += ';';
			}
			}
		}
	}
	answer.setAnswer(answer_str);
	answer.update();// .updateByPrimaryKeySelective(answer);

	List<Question> questions = Question.dao
			.find("select id,questionId,questionTitle,questionItems,questionAnswer,questionType from tquestion\n"
					+ "    where id in (select question_id from texam_question where exam_id = '" + examId + "')");
	String newpage = getPara("newpage");
	if (StrKit.isBlank(newpage)) {
		newpage = "0";
	}
	setAttr("question_size", questions.size());
	setAttr("examId", examId);
	setAttr("answer_no", answer_no);
	setAttr("answer_str", answer_str);
	Question q=questions.get(Integer.valueOf(newpage).intValue());
	q.setQuestionTitle(Encrypt.decode(q.getQuestionTitle()));
	q.setQuestionItems(Encrypt.decode(q.getQuestionItems()));
	q.setQuestionAnswer(Encrypt.decode(q.getQuestionAnswer()));

	setAttr("question", q);
	setAttr("flows",QuestionFlow.dao.find("select * from tquestion_flow where question_id="+q.getId()));
//	renderJsp("/learn/answerpage.jsp");
}

}


