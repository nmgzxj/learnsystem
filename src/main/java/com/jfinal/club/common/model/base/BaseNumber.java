package com.jfinal.club.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseNumber<M extends BaseNumber<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Long id) {
		set("id", id);
	}
	
	public java.lang.Long getId() {
		return getLong("id");
	}

	public void setMsisdn(java.lang.String msisdn) {
		set("msisdn", msisdn);
	}
	
	public java.lang.String getMsisdn() {
		return getStr("msisdn");
	}

	public void setImsi(java.lang.String imsi) {
		set("imsi", imsi);
	}
	
	public java.lang.String getImsi() {
		return getStr("imsi");
	}

	public void setImei(java.lang.String imei) {
		set("imei", imei);
	}
	
	public java.lang.String getImei() {
		return getStr("imei");
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

	public void setUnit(java.lang.String unit) {
		set("unit", unit);
	}
	
	public java.lang.String getUnit() {
		return getStr("unit");
	}

	public void setNumberFormat(java.lang.String numberFormat) {
		set("number_format", numberFormat);
	}
	
	public java.lang.String getNumberFormat() {
		return getStr("number_format");
	}

	public void setLicId(java.lang.String licId) {
		set("lic_id", licId);
	}
	
	public java.lang.String getLicId() {
		return getStr("lic_id");
	}

	public void setIp(java.lang.String ip) {
		set("ip", ip);
	}
	
	public java.lang.String getIp() {
		return getStr("ip");
	}

	public void setIpTime(java.lang.String ipTime) {
		set("ip_time", ipTime);
	}
	
	public java.lang.String getIpTime() {
		return getStr("ip_time");
	}

	public void setIpPort(java.lang.String ipPort) {
		set("ip_port", ipPort);
	}
	
	public java.lang.String getIpPort() {
		return getStr("ip_port");
	}

	public void setIpAddressField(java.lang.String ipAddressField) {
		set("ip_address_field", ipAddressField);
	}
	
	public java.lang.String getIpAddressField() {
		return getStr("ip_address_field");
	}

	public void setNai(java.lang.String nai) {
		set("nai", nai);
	}
	
	public java.lang.String getNai() {
		return getStr("nai");
	}

	public void setPhoneNumber(java.lang.String phoneNumber) {
		set("phone_number", phoneNumber);
	}
	
	public java.lang.String getPhoneNumber() {
		return getStr("phone_number");
	}

	public void setSip(java.lang.String sip) {
		set("sip", sip);
	}
	
	public java.lang.String getSip() {
		return getStr("sip");
	}

	public void setTel(java.lang.String tel) {
		set("tel", tel);
	}
	
	public java.lang.String getTel() {
		return getStr("tel");
	}

}
