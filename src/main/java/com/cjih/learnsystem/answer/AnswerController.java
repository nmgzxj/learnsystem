package com.cjih.learnsystem.answer;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.cjih.learnsystem.common.util.Encrypt;
import com.jfinal.aop.Before;
import com.jfinal.club.common.controller.BaseController;
import com.jfinal.club.common.model.Answer;
import com.jfinal.club.common.model.AnswerLog;
import com.jfinal.club.common.model.Exam;
import com.jfinal.club.common.model.Question;
import com.jfinal.club.common.model.QuestionFlow;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;

public class AnswerController extends BaseController{
	
	AnswerService srv = AnswerService.me;
	private static final Logger logger = Logger.getLogger(AnswerController.class);

	
	public void index() {
		Page<Exam> answerPage = srv.paginate(getParaToInt("p", 1),getLoginAccountId());
		setAttr("answer_no", UUID.randomUUID().toString());
		setAttr("answerPage", answerPage);
		render("index.html");
	}


	/**
	 * 提交修改
	 */
	@Before(AnswerValidator.class)
	public void update() {
		Answer answer = getBean(Answer.class);
		Ret ret = srv.update(answer);
		renderJson(ret);
	}

	public void save() {
		Answer answer = getBean(Answer.class);
		Ret ret = srv.save(answer);
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
	Integer examId = getParaToInt("examId");
	String lastPage = getHeader("Referer");
	String userId = Integer.toString(getLoginAccountId());
	logger.debug("lastPage=" + lastPage);
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
			answer.setQuestionId(q.getId());
			answer.setAnswerNo(answer_no);
			answer.setAnswer("");
			answer.save();// .insert(answer);
			if (i == 0)
				answerId = answer.getId();
			i++;
		}
		
		//添加考试日志
		Exam exam = Exam.dao.findById(examId);
		
		AnswerLog al = new AnswerLog();
		al.setAccountId(getLoginAccountId());
		al.setAnswerNo(answer_no);
		al.setAnswerBegin(new Date());
		al.setExamName(exam.getExamName());
		al.setExamScore((double)exam.getScore());
		al.save();

	} else {

		logger.debug("debug==============");
		logger.debug("page = " + page);
		logger.debug("answer_no = " + answer_no);
		logger.debug("examId = " + examId);
		logger.debug("session = " + getSession());
		logger.debug("session = " + getSession(false));
		logger.debug("userId = " + userId);
		logger.debug("questions size = " + questions.size());
		String sql = "select id, user_id, exam_id, question_id, answer, official_id, user_score, answer_no from tanswer\n"
				+ "    where answer_no = '" + answer_no + "' and exam_id = '" + examId + "' and user_id= '" + userId
				+ "' and question_id = '" + questions.get(Integer.valueOf(page).intValue()).getId() + "'";
		logger.debug("sql = " + sql);
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
	logger.debug("debug=================");
	logger.debug("答案是：");
	if (null != answers) {
		for (String a : answers) {
			if(StrKit.notBlank(a)) {
			answer_str += a;
			if(!a.endsWith(";")) {
				answer_str += ';';
			}
			logger.debug(a);
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

//package com.cjih.learnsystem.answer;
//
//import com.jfinal.club.common.model.Answer;
//import com.jfinal.club.common.model.Exam;
//import com.jfinal.club.common.model.Question;
//import com.jfinal.club.common.model.QuestionFlow;
//import com.jfinal.club.common.model.SavedAnswer;
//import com.jfinal.club.common.controller.BaseController;
//import com.jfinal.kit.StrKit;
//import com.jfinal.plugin.activerecord.Db;
//
//import org.apache.log4j.Logger;
//
//import java.util.List;
//import java.util.UUID;
//
///**
// * Created by ZhangXianjin .
// */
//public class AnswerController extends BaseController {
//

//	
//	}
//	
//
//
//	public void index() {
//		setAttr("answer_no", UUID.randomUUID().toString());
//		setAttr("examList", Exam.dao.find("select * from texam where is_readonly = '1' "
//				+ "and id in (select exam_id from texam_user where user_id = (select id from tuser where name='"+getSessionAttr("userName")+"'))"));
//		render("/_view/answer/index.html");
//	}
//
//
//
//	public void answered_edit() {
//		String answer_no = getPara("answer_no");
//		String page = getPara("page");
//
//		if (answer_no == null || answer_no.equals("")) {
//			setSessionAttr("msg", "参数错误，请检查answer_no。");
//			renderJsp("/msg.jsp");
//		}
//		logger.debug("answer_no=" + answer_no);
//
//		if (page == null || page.equals("")) {
//			setSessionAttr("msg", "参数错误，请检查page。");
//			renderJsp("/msg.jsp");
//		}
//		logger.debug("page=" + page);
//
//		List<SavedAnswer> answers = SavedAnswer.dao.find(
//				"select id, user_id, exam_id, question_id, answer, official_id, user_score, answer_no from tsaved_answer\n"
//						+ "    where answer_no = '" + answer_no + "'");// .getAnsweredList(answer_no);
//		SavedAnswer answer = answers.get(Integer.parseInt(page));
//		setAttr("answer", answer);
//		Question question = Question.dao.findById(answer.getQuestionId());
//		setAttr("question", question);
//		setAttr("flows",QuestionFlow.dao.find("select * from tquestion_flow where question_id="+question.getId()));
//		setAttr("question_size", answers.size());
//		setAttr("answer_no", answer_no);
//		setAttr("examId", answer.getExamId());
//		setAttr("answerId", answer.getId());
//		logger.debug("debug===========");
//		for (SavedAnswer a : answers) {
//			logger.debug("examid=" + a.getExamId());
//			logger.debug("answer_no=" + a.getAnswerNo());
//			logger.debug("userid=" + a.getUserId());
//			logger.debug("officialid=" + a.getOfficialId());
//			logger.debug("userScore=" + a.getUserScore());
//			logger.debug("answer_str=" + a.getAnswer());
//			logger.debug("answerId=" + a.getId());
//		}
//		renderJsp("/learn/answered_edit.jsp");
//	}
//
//	/**
//	 * 已提交试卷列表
//	 */
//	public void answered_list() {
//		List<SavedAnswer> answers = SavedAnswer.dao
//				.find("select user_id, exam_id,  answer_no,sum(user_score) as user_score from tsaved_answer where user_id = '"+getSessionAttr("userName")+"'"
//						+ "    group by answer_no,user_id,exam_id");// ).getSavedAnswerList();
//		setAttr("savedAnswerList", answers);
//		// logger.debug("debug===========");
//		// for (SavedAnswer a:answers) {
//		// logger.debug("examid="+a.getExamId());
//		// logger.debug("answer_no="+a.getAnswerNo());
//		// logger.debug("userid="+a.getUserId());
//		// logger.debug("userScore="+a.getUserScore());
//		// }
//		renderJsp("/learn/answered_list.jsp");
//	}
//
//}
