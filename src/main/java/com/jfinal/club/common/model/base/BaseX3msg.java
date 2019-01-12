package com.jfinal.club.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseX3msg<M extends BaseX3msg<M>> extends Model<M> implements IBean {

	public void setId(Long id) {
		set("id", id);
	}

	public Long getId() {
		return get("id");
	}

	public void setUnit(String unit) {
		set("unit", unit);
	}

	public String getUnit() {
		return get("unit");
	}

	public void setLicX3Addr(String licX3Addr) {
		set("lic_x3_addr", licX3Addr);
	}

	public String getLicX3Addr() {
		return get("lic_x3_addr");
	}

	public void setMsgType(String msgType) {
		set("msg_type", msgType);
	}

	public String getMsgType() {
		return get("msg_type");
	}

	public void setPerposeId(String perposeId) {
		set("perpose_id", perposeId);
	}

	public String getPerposeId() {
		return get("perpose_id");
	}

	public void setRelation1(String relation1) {
		set("relation1", relation1);
	}

	public String getRelation1() {
		return get("relation1");
	}

	public void setRelation2(String relation2) {
		set("relation2", relation2);
	}

	public String getRelation2() {
		return get("relation2");
	}

	public void setMsgSn(String msgSn) {
		set("msg_sn", msgSn);
	}

	public String getMsgSn() {
		return get("msg_sn");
	}

	public void setOppositeAddr(String oppositeAddr) {
		set("opposite_addr", oppositeAddr);
	}

	public String getOppositeAddr() {
		return get("opposite_addr");
	}

	public void setOperationResult(String operationResult) {
		set("operation_result", operationResult);
	}

	public String getOperationResult() {
		return get("operation_result");
	}

	public void setFileName(String fileName) {
		set("file_name", fileName);
	}

	public String getFileName() {
		return get("file_name");
	}

	public void setEventTime(java.util.Date eventTime) {
		set("event_time", eventTime);
	}

	public java.util.Date getEventTime() {
		return get("event_time");
	}

	public void setCreator(String creator) {
		set("creator", creator);
	}

	public String getCreator() {
		return get("creator");
	}

	public void setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
	}

	public java.util.Date getCreateTime() {
		return get("create_time");
	}

	public void setModifyBy(String modifyBy) {
		set("modify_by", modifyBy);
	}

	public String getModifyBy() {
		return get("modify_by");
	}

	public void setModifyTime(java.util.Date modifyTime) {
		set("modify_time", modifyTime);
	}

	public java.util.Date getModifyTime() {
		return get("modify_time");
	}

	public void setPerposeType(String perposeType) {
		set("perpose_type", perposeType);
	}

	public String getPerposeType() {
		return get("perpose_type");
	}

	public void setMemo(String memo) {
		set("memo", memo);
	}

	public String getMemo() {
		return get("memo");
	}

}
