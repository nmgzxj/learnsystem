package com.cjih.learnsystem.baseflow;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class BaseFlowValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		setShortCircuit(true);

		//validateRequiredString("字段", "msg", "消息名称不能为空");
	}

	@Override
	protected void handleError(Controller c) {

	}

}

