package com.cjih.learnsystem.simulate;

import com.jfinal.club.common.model.Unit;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class UnitValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		setShortCircuit(true);
			validateRequiredString("operType", "msg", "操作类型不正确，请联系管理员。");
//			this.validateBoolean(index, "msg", "该网元X1接口未连接，请先建立连接。");
//			this.validateEqualField("operType", "a1", errorKey, errorMessage);
		if(!c.getPara("operType").equals("a1")&&"0".equals(Unit.dao.findById(c.getPara("unit")).getX1Status())) {
			addError("msg", "该网元X1接口未连接，请先建立连接。");
		}
	}

	@Override
	protected void handleError(Controller c) {
		c.keepPara("unit");
		c.keepPara("operType");
		c.render("simulation1.html");
	}

}

