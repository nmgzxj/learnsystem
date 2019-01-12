package com.jfinal.club.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseOnline<M extends BaseOnline<M>> extends Model<M> implements IBean {

	public void setId(String id) {
		set("id", id);
	}
	
	public String getId() {
		return getStr("id");
	}

	public void setIp(String ip) {
		set("ip", ip);
	}
	
	public String getIp() {
		return getStr("ip");
	}

	public void setLogindatetime(java.util.Date logindatetime) {
		set("logindatetime", logindatetime);
	}
	
	public java.util.Date getLogindatetime() {
		return get("logindatetime");
	}

	public void setLoginname(java.util.Date loginname) {
		set("loginname", loginname);
	}
	
	public java.util.Date getLoginname() {
		return get("loginname");
	}

}
