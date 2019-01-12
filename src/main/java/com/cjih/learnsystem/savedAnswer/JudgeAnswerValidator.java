package com.cjih.learnsystem.savedAnswer;

import java.util.List;

import com.jfinal.club.common.model.SavedAnswer;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.validate.Validator;

public class JudgeAnswerValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		setShortCircuit(true);
		List<SavedAnswer> saList = SavedAnswer.dao.find("select * from tsaved_answer where answer_no = '"+
		c.getPara("answer_no")+"' order by id");
		String questionId="";
		
		for(int i=0;i<saList.size();i++) {
			if(saList.get(i).getUserScore()==null) {
				questionId += (i+1)+ "、";
			}
		}
		if(StrKit.notBlank(questionId)) {
			
			addError("msg","本套考卷中还有未判的题, 编号为"+questionId);
		}
		//validateRequiredString("字段", "msg", "消息名称不能为空");
	}

	@Override
	protected void handleError(Controller c) {
		c.renderJson();
		
	}

}

