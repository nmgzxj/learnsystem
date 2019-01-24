package com.jfinal.club.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseMsgTempletSub<M extends BaseMsgTempletSub<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Long id) {
		set("id", id);
	}
	
	public java.lang.Long getId() {
		return getLong("id");
	}

	public void setTempletId(java.lang.Integer templetId) {
		set("templet_id", templetId);
	}
	
	public java.lang.Integer getTempletId() {
		return getInt("templet_id");
	}

	public void setTaxis(java.lang.Integer taxis) {
		set("taxis", taxis);
	}
	
	public java.lang.Integer getTaxis() {
		return getInt("taxis");
	}

	public void setUnitId(java.lang.String unitId) {
		set("unit_id", unitId);
	}
	
	public java.lang.String getUnitId() {
		return getStr("unit_id");
	}

	public void setMsg(java.lang.String msg) {
		set("msg", msg);
	}
	
	public java.lang.String getMsg() {
		return getStr("msg");
	}

	public void setLicId(java.lang.String licId) {
		set("lic_id", licId);
	}
	
	public java.lang.String getLicId() {
		return getStr("lic_id");
	}

	public void setPort(java.lang.String port) {
		set("port", port);
	}
	
	public java.lang.String getPort() {
		return getStr("port");
	}

	public void setDirection(java.lang.String direction) {
		set("direction", direction);
	}
	
	public java.lang.String getDirection() {
		return getStr("direction");
	}

	public void setNeX2Addr(java.lang.String neX2Addr) {
		set("ne_x2_addr", neX2Addr);
	}
	
	public java.lang.String getNeX2Addr() {
		return getStr("ne_x2_addr");
	}

	public void setMsgType(java.lang.String msgType) {
		set("msg_type", msgType);
	}
	
	public java.lang.String getMsgType() {
		return getStr("msg_type");
	}

	public void setPerposeId(java.lang.String perposeId) {
		set("perpose_id", perposeId);
	}
	
	public java.lang.String getPerposeId() {
		return getStr("perpose_id");
	}

	public void setRelation1(java.lang.String relation1) {
		set("relation1", relation1);
	}
	
	public java.lang.String getRelation1() {
		return getStr("relation1");
	}

	public void setRelation2(java.lang.String relation2) {
		set("relation2", relation2);
	}
	
	public java.lang.String getRelation2() {
		return getStr("relation2");
	}

	public void setEventSn(java.lang.String eventSn) {
		set("event_sn", eventSn);
	}
	
	public java.lang.String getEventSn() {
		return getStr("event_sn");
	}

	public void setCallSn(java.lang.String callSn) {
		set("call_sn", callSn);
	}
	
	public java.lang.String getCallSn() {
		return getStr("call_sn");
	}

	public void setCallSnChild(java.lang.String callSnChild) {
		set("call_sn_child", callSnChild);
	}
	
	public java.lang.String getCallSnChild() {
		return getStr("call_sn_child");
	}

	public void setEventTime(java.util.Date eventTime) {
		set("event_time", eventTime);
	}
	
	public java.util.Date getEventTime() {
		return get("event_time");
	}

	public void setPerposeType(java.lang.String perposeType) {
		set("perpose_type", perposeType);
	}
	
	public java.lang.String getPerposeType() {
		return getStr("perpose_type");
	}

	public void setLicX3Addr(java.lang.String licX3Addr) {
		set("lic_x3_addr", licX3Addr);
	}
	
	public java.lang.String getLicX3Addr() {
		return getStr("lic_x3_addr");
	}

	public void setMsgSn(java.lang.String msgSn) {
		set("msg_sn", msgSn);
	}
	
	public java.lang.String getMsgSn() {
		return getStr("msg_sn");
	}

	public void setOppositeAddr(java.lang.String oppositeAddr) {
		set("opposite_addr", oppositeAddr);
	}
	
	public java.lang.String getOppositeAddr() {
		return getStr("opposite_addr");
	}

	public void setOperationResult(java.lang.String operationResult) {
		set("operation_result", operationResult);
	}
	
	public java.lang.String getOperationResult() {
		return getStr("operation_result");
	}

	public void setFileName(java.lang.String fileName) {
		set("file_name", fileName);
	}
	
	public java.lang.String getFileName() {
		return getStr("file_name");
	}

	public void setMemo(java.lang.String memo) {
		set("memo", memo);
	}
	
	public java.lang.String getMemo() {
		return getStr("memo");
	}

	public void setMsgArriveTime(java.util.Date msgArriveTime) {
		set("msg_arrive_time", msgArriveTime);
	}
	
	public java.util.Date getMsgArriveTime() {
		return get("msg_arrive_time");
	}

	public void setCccId(java.lang.String cccId) {
		set("cccId", cccId);
	}
	
	public java.lang.String getCccId() {
		return getStr("cccId");
	}

	public void setIcid(java.lang.String icid) {
		set("icid", icid);
	}
	
	public java.lang.String getIcid() {
		return getStr("icid");
	}

}