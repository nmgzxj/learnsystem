package com.jfinal.club.common.model;

import com.cjih.learnsystem.controller.ResourceInfoController;
import com.jfinal.club.common.model.base.BaseX2msg;
import com.jfinal.plugin.activerecord.Db;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class X2msg extends BaseX2msg<X2msg> {
	public static final X2msg dao = new X2msg().dao();
	
	public String getUnitIdView() {
//		return Db.queryStr("select unit_desc from tunit where id= '"+get("unit")+"'");
		return ResourceInfoController.getResourceName("PUB001", (String)get("unit"));
	}
}
