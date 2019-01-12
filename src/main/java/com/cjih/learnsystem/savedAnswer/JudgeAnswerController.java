package com.cjih.learnsystem.savedAnswer;


import org.apache.log4j.Logger;

import com.cjih.learnsystem.common.util.Encrypt;
import com.jfinal.aop.Before;
import com.jfinal.club.common.account.AccountService;
import com.jfinal.club.common.controller.BaseController;
import com.jfinal.club.common.model.Question;
import com.jfinal.club.common.model.QuestionFlow;
import com.jfinal.club.common.model.SavedAnswer;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;

/**
 * Created by ZhangXianjin .
 */
public class JudgeAnswerController extends BaseController {

	private static final Logger logger = Logger.getLogger(JudgeAnswerController.class);
	JudgeAnswerService srv = JudgeAnswerService.me;


	public void edit() {
		form();
		render("edit.html");
	}
	
	public void read() {
		form();
		render("read.html");
	}

	private void form() {
		String answer_no = getPara("answer_no");
		String page = getPara("newpage");
		if (StrKit.isBlank(page)) {
			page = "0";
		}
		
		setAttr("page", page);
		setAttr("loginNickName", AccountService.me.getById(getLoginAccountId()).getNickName());

		if (answer_no == null || answer_no.equals("")) {
			setSessionAttr("msg", "参数错误，请检查answer_no。");
			render("/msg.html");
		}
		logger.debug("answer_no=" + answer_no);

		if (page == null || page.equals("")) {
			setSessionAttr("msg", "参数错误，请检查page。");
			render("/msg.html");
		}

		List<SavedAnswer> answers = SavedAnswer.dao.find(
				"select id, user_id, exam_id, question_id, answer, official_id,(select nickname from account "
				+ "where id= tsaved_answer.official_id) as official_name, user_score, answer_no from tsaved_answer\n"
						+ "    where answer_no = '" + answer_no + "'");// .getAnsweredList(answer_no);
		SavedAnswer answer = answers.get(Integer.parseInt(page));
		answer.setAnswer(answer.getAnswer());
		setAttr("answer", answer);
		Question q = Question.dao.findById(answer.getQuestionId());
		q.setQuestionTitle(Encrypt.decode(q.getQuestionTitle()));
		q.setQuestionItems(Encrypt.decode(q.getQuestionItems()));
		q.setQuestionAnswer(Encrypt.decode(q.getQuestionAnswer()));
		//给出建议分
		if(answer.getUserScore()==null) {
			answer.setUserScore(Double.valueOf(PropKit.get(q.getQuestionType()+"_score")));
		}

		setAttr("question", q);
		setAttr("flows",QuestionFlow.dao.find("select * from tquestion_flow where question_id="+q.getId()));
		setAttr("question_size", answers.size());
		setAttr("answer_no", answer_no);
		setAttr("examId", answer.getExamId());
		setAttr("answerId", answer.getId());
		logger.debug("debug===========");
		for (SavedAnswer a : answers) {
			logger.debug("examid=" + a.getExamId());
			logger.debug("answer_no=" + a.getAnswerNo());
			logger.debug("userid=" + a.getUserId());
			logger.debug("officialid=" + a.getOfficialId());
			logger.debug("userScore=" + a.getUserScore());
			logger.debug("answer_str=" + a.getAnswer());
			logger.debug("answerId=" + a.getId());
		}
	}

	/**
	 * 判卷试卷列表
	 */
	public void index() {
		
		Page<SavedAnswer> judgeAnswerPage = srv.paginate(getParaToInt("p",1));
		setAttr("judgeAnswerPage", judgeAnswerPage);

		render("index.html");
	}
	
	/**
	 * 保存当前问题的评分
	 */
	public void saveUserScore() {
		String answerId = getPara("answerId");
		Integer officialId = getLoginAccountId();
		String userScore = getPara("userScore");
		Db.update("update tsaved_answer   set official_id = '" + officialId + "', user_score = '"
				+ userScore + "'   where id = '" + answerId + "'");
		setAttr("page", getPara("page"));
		setAttr("answer_no", getPara("answer_no"));
		setAttr("examId", getPara("examId"));
		edit();
	}


	/**
	 * 归档，归档后，试卷不可再次判卷
	 */
	@Before(JudgeAnswerValidator.class)
	public void saveArchive() {
		String answer_no = getPara("answer_no");
		Integer officialId = getLoginAccountId();
		Db.update("update tanswer_log set official_id = "+officialId+
				", user_score = (select sum(user_score) from tsaved_answer where answer_no='"+answer_no+
				"'),judge_time=now()  where answer_no='"+answer_no+"'");
		setAttr("page", getPara("page"));
		setAttr("answer_no", answer_no);
		setAttr("examId", getPara("examId"));
		renderJson(Ret.ok());
	}
}
