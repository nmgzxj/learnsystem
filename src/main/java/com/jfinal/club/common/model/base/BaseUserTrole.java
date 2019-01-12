package com.jfinal.club.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseUserTrole<M extends BaseUserTrole<M>> extends Model<M> implements IBean {

	public void setId(String id) {
		set("id", id);
	}
	
	public String getId() {
		return getStr("id");
	}

	public void setRoleId(String roleId) {
		set("role_id", roleId);
	}
	
	public String getRoleId() {
		return getStr("role_id");
	}

	public void setUserId(String userId) {
		set("user_id", userId);
	}
	
	public String getUserId() {
		return getStr("user_id");
	}

}
