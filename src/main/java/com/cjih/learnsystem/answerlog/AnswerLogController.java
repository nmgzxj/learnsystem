package com.cjih.learnsystem.answerlog;

import com.jfinal.aop.Before;
import com.jfinal.club.common.controller.BaseController;
import com.jfinal.club.common.model.AnswerLog;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

public class AnswerLogController extends BaseController{
	
AnswerLogService srv = AnswerLogService.me;
	
	public void index() {
		Page<AnswerLog> answerLogPage = srv.paginate(getParaToInt("p", 1));
		setAttr("answerLogPage", answerLogPage);
		render("index.html");
	}
	
	/**
	 * 修改
	 */
	public void edit() {
		setAttr("answerLog", srv.edit(getParaToInt("id")));
		render("edit.html");
	}

	/**
	 * 提交修改
	 */
	@Before(AnswerLogValidator.class)
	public void update() {
		AnswerLog answerLog = getBean(AnswerLog.class);
		Ret ret = srv.update(answerLog);
		renderJson(ret);
	}

	public void save() {
		AnswerLog answerLog = getBean(AnswerLog.class);
		Ret ret = srv.save(answerLog);
		renderJson(ret);
	}

	public void delete() {
		Ret ret = srv.delete(getParaToInt("id"));
		renderJson(ret);
	}

	public void add() {
		render("add.html");
	}
}

