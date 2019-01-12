package com.jfinal.club.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseResourceType<M extends BaseResourceType<M>> extends Model<M> implements IBean {

	public void setTypeId(String typeId) {
		set("type_id", typeId);
	}

	public String getTypeId() {
		return get("type_id");
	}

	public void setTypeName(String typeName) {
		set("type_name", typeName);
	}

	public String getTypeName() {
		return get("type_name");
	}

	public void setTypeDesc(Integer typeDesc) {
		set("type_desc", typeDesc);
	}

	public Integer getTypeDesc() {
		return get("type_desc");
	}

	public void setIsShow(String isShow) {
		set("is_show", isShow);
	}

	public String getIsShow() {
		return get("is_show");
	}

	public void setPTypeId(String pTypeId) {
		set("p_type_id", pTypeId);
	}

	public String getPTypeId() {
		return get("p_type_id");
	}

}
