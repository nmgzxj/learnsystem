package com.jfinal.club.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseResource<M extends BaseResource<M>> extends Model<M> implements IBean {

	public void setID(java.lang.String ID) {
		set("ID", ID);
	}
	
	public java.lang.String getID() {
		return getStr("ID");
	}

	public void setSeq(java.math.BigDecimal seq) {
		set("seq", seq);
	}
	
	public java.math.BigDecimal getSeq() {
		return get("seq");
	}

	public void setText(java.lang.String text) {
		set("text", text);
	}
	
	public java.lang.String getText() {
		return getStr("text");
	}

	public void setUrl(java.lang.String url) {
		set("url", url);
	}
	
	public java.lang.String getUrl() {
		return getStr("url");
	}

	public void setPid(java.lang.String pid) {
		set("pid", pid);
	}
	
	public java.lang.String getPid() {
		return getStr("pid");
	}

}
