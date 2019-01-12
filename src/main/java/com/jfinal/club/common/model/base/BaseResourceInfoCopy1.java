package com.jfinal.club.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseResourceInfoCopy1<M extends BaseResourceInfoCopy1<M>> extends Model<M> implements IBean {

	public void setResourceId(String resourceId) {
		set("resource_id", resourceId);
	}

	public String getResourceId() {
		return get("resource_id");
	}

	public void setTypeId(String typeId) {
		set("type_id", typeId);
	}

	public String getTypeId() {
		return get("type_id");
	}

	public void setResourceName(String resourceName) {
		set("resource_name", resourceName);
	}

	public String getResourceName() {
		return get("resource_name");
	}

	public void setTaxis(Integer taxis) {
		set("taxis", taxis);
	}

	public Integer getTaxis() {
		return get("taxis");
	}

	public void setPTypeId(String pTypeId) {
		set("p_type_id", pTypeId);
	}

	public String getPTypeId() {
		return get("p_type_id");
	}

	public void setDelFlag(String delFlag) {
		set("del_flag", delFlag);
	}

	public String getDelFlag() {
		return get("del_flag");
	}

}
