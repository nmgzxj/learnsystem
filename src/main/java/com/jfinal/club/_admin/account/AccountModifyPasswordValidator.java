package com.jfinal.club._admin.account;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class AccountModifyPasswordValidator  extends Validator {
	
	AccountAdminService srv = AccountAdminService.me;
	
	protected void validate(Controller c) {
		setShortCircuit(true);


		/**
		 * 验证 窗体参数
		 */
		validateRequired("old_pass", "msg", "原密码不能为空");
		if(!srv.verifyPassword(srv.findById(c.getParaToInt("id")),c.getPara("old_pass"))) {
			addError("msg","原密码错误");
		}		validateRequired("new_pass", "msg", "新密码不能为空");
		validateRequired("repeat_pass", "msg", "重复密码不能为空");
		if(!c.getPara("new_pass").equals(c.getPara("repeat_pass"))){
			addError("msg", "两次输入的密码不一致。");
		} 

	}

	protected void handleError(Controller c) {
		c.setAttr("state", "fail");
		c.renderJson();
	}

}
