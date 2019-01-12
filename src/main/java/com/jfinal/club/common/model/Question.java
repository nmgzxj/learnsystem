package com.jfinal.club.common.model;

import com.cjih.learnsystem.controller.ResourceInfoController;
import com.jfinal.club.common.model.base.BaseQuestion;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Question extends BaseQuestion<Question> {
	public static final Question dao = new Question().dao();
	
	public String getQuestionTypeView(){
		return ResourceInfoController.getResourceName("PUB004", (String)get("questionType"));
	}
}
