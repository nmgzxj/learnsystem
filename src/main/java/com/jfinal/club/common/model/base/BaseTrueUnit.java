package com.jfinal.club.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseTrueUnit<M extends BaseTrueUnit<M>> extends Model<M> implements IBean {

	public void setId(java.lang.String id) {
		set("id", id);
	}
	
	public java.lang.String getId() {
		return getStr("id");
	}

	public void setUnitId(java.lang.String unitId) {
		set("unit_id", unitId);
	}
	
	public java.lang.String getUnitId() {
		return getStr("unit_id");
	}

	public void setX1Ip(java.lang.String x1Ip) {
		set("x1_ip", x1Ip);
	}
	
	public java.lang.String getX1Ip() {
		return getStr("x1_ip");
	}

	public void setX1Port(java.lang.String x1Port) {
		set("x1_port", x1Port);
	}
	
	public java.lang.String getX1Port() {
		return getStr("x1_port");
	}

	public void setUnitDesc(java.lang.String unitDesc) {
		set("unit_desc", unitDesc);
	}
	
	public java.lang.String getUnitDesc() {
		return getStr("unit_desc");
	}

	public void setLicId(java.lang.String licId) {
		set("lic_id", licId);
	}
	
	public java.lang.String getLicId() {
		return getStr("lic_id");
	}

	public void setLicIp(java.lang.String licIp) {
		set("lic_ip", licIp);
	}
	
	public java.lang.String getLicIp() {
		return getStr("lic_ip");
	}

	public void setX2Port(java.lang.String x2Port) {
		set("x2_port", x2Port);
	}
	
	public java.lang.String getX2Port() {
		return getStr("x2_port");
	}

	public void setX3Port(java.lang.String x3Port) {
		set("x3_port", x3Port);
	}
	
	public java.lang.String getX3Port() {
		return getStr("x3_port");
	}

	public void setExtX3Ip(java.lang.String extX3Ip) {
		set("ext_x3_ip", extX3Ip);
	}
	
	public java.lang.String getExtX3Ip() {
		return getStr("ext_x3_ip");
	}

	public void setExtX3Port(java.lang.String extX3Port) {
		set("ext_x3_port", extX3Port);
	}
	
	public java.lang.String getExtX3Port() {
		return getStr("ext_x3_port");
	}

	public void setX1Kt(java.lang.String x1Kt) {
		set("x1_kt", x1Kt);
	}
	
	public java.lang.String getX1Kt() {
		return getStr("x1_kt");
	}

	public void setX1Sqn(java.lang.String x1Sqn) {
		set("x1_sqn", x1Sqn);
	}
	
	public java.lang.String getX1Sqn() {
		return getStr("x1_sqn");
	}

	public void setX1Pwd(java.lang.String x1Pwd) {
		set("x1_pwd", x1Pwd);
	}
	
	public java.lang.String getX1Pwd() {
		return getStr("x1_pwd");
	}

	public void setX2Kt(java.lang.String x2Kt) {
		set("x2_kt", x2Kt);
	}
	
	public java.lang.String getX2Kt() {
		return getStr("x2_kt");
	}

	public void setX2Sqn(java.lang.String x2Sqn) {
		set("x2_sqn", x2Sqn);
	}
	
	public java.lang.String getX2Sqn() {
		return getStr("x2_sqn");
	}

	public void setX2Pwd(java.lang.String x2Pwd) {
		set("x2_pwd", x2Pwd);
	}
	
	public java.lang.String getX2Pwd() {
		return getStr("x2_pwd");
	}

	public void setUnitStatus(java.lang.String unitStatus) {
		set("unit_status", unitStatus);
	}
	
	public java.lang.String getUnitStatus() {
		return getStr("unit_status");
	}

	public void setX1Status(java.lang.String x1Status) {
		set("x1_status", x1Status);
	}
	
	public java.lang.String getX1Status() {
		return getStr("x1_status");
	}

	public void setX2Status(java.lang.String x2Status) {
		set("x2_status", x2Status);
	}
	
	public java.lang.String getX2Status() {
		return getStr("x2_status");
	}

	public void setX3Status(java.lang.String x3Status) {
		set("x3_status", x3Status);
	}
	
	public java.lang.String getX3Status() {
		return getStr("x3_status");
	}

	public void setUnitType(java.lang.String unitType) {
		set("unit_type", unitType);
	}
	
	public java.lang.String getUnitType() {
		return getStr("unit_type");
	}

	public void setCreator(java.lang.String creator) {
		set("creator", creator);
	}
	
	public java.lang.String getCreator() {
		return getStr("creator");
	}

	public void setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
	}
	
	public java.util.Date getCreateTime() {
		return get("create_time");
	}

	public void setModifyBy(java.lang.String modifyBy) {
		set("modify_by", modifyBy);
	}
	
	public java.lang.String getModifyBy() {
		return getStr("modify_by");
	}

	public void setModifyTime(java.util.Date modifyTime) {
		set("modify_time", modifyTime);
	}
	
	public java.util.Date getModifyTime() {
		return get("modify_time");
	}

}
