package com.jfinal.club.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseRoleTresource<M extends BaseRoleTresource<M>> extends Model<M> implements IBean {

	public void setId(java.lang.String id) {
		set("id", id);
	}
	
	public java.lang.String getId() {
		return getStr("id");
	}

	public void setResourceId(java.lang.String resourceId) {
		set("resource_id", resourceId);
	}
	
	public java.lang.String getResourceId() {
		return getStr("resource_id");
	}

	public void setRoleId(java.lang.String roleId) {
		set("role_id", roleId);
	}
	
	public java.lang.String getRoleId() {
		return getStr("role_id");
	}

}
