package com.cjih.learnsystem.controller;

import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;

public class SystemController extends Controller {
	
	
	public void data_clear() {
//		renderJsp("/system/data_list.jsp");
		render("/_view/system/data_clear.html");
	}
	
	public void data_delete() {
		String table = getPara("table");
		if("tx1msg".equals(table)|"tx2msg".equals(table)|"tx3msg".equals(table)
				|"tsaved_answer".equals(table)|"tanswer".equals(table)) {
			Db.update("delete from "+table);
		}
		else {
			renderText("此数据不可删除！");
		}
		data_clear();
	}
	
	public void datax1_delete() {
		String table = getPara("table");
		if("tx1msg".equals(table)|"tx2msg".equals(table)|"tx3msg".equals(table)
				|"tsaved_answer".equals(table)|"tanswer".equals(table)) {
			Db.update("delete from tx1msg");
		}
		else {
			renderText("此数据不可删除！");
		}
		renderJson(Ret.ok());
	}

	public void datax2x3_delete() {
		String table = getPara("table");
		if("tx1msg".equals(table)|"tx2msg".equals(table)|"tx3msg".equals(table)
				|"tsaved_answer".equals(table)|"tanswer".equals(table)) {
			Db.update("delete from tx2msg");
			Db.update("delete from tx3msg");
		}
		else {
			renderText("此数据不可删除！");
		}
		renderJson(Ret.ok());
	}



}
