package com.jfinal.club.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseMessage<M extends BaseMessage<M>> extends Model<M> implements IBean {

	public void setId(Integer id) {
		set("id", id);
	}
	
	public Integer getId() {
		return getInt("id");
	}

	public void setUser(Integer user) {
		set("user", user);
	}
	
	public Integer getUser() {
		return getInt("user");
	}

	public void setFriend(Integer friend) {
		set("friend", friend);
	}
	
	public Integer getFriend() {
		return getInt("friend");
	}

	public void setSender(Integer sender) {
		set("sender", sender);
	}
	
	public Integer getSender() {
		return getInt("sender");
	}

	public void setReceiver(Integer receiver) {
		set("receiver", receiver);
	}
	
	public Integer getReceiver() {
		return getInt("receiver");
	}

	public void setType(Integer type) {
		set("type", type);
	}
	
	public Integer getType() {
		return getInt("type");
	}

	public void setContent(String content) {
		set("content", content);
	}
	
	public String getContent() {
		return getStr("content");
	}

	public void setCreateAt(java.util.Date createAt) {
		set("createAt", createAt);
	}
	
	public java.util.Date getCreateAt() {
		return get("createAt");
	}

}
