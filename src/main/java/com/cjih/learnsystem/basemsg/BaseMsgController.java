package com.cjih.learnsystem.basemsg;

import java.util.Date;

import com.cjih.learnsystem.common.util.Encrypt;
import com.jfinal.aop.Before;
import com.jfinal.club.common.controller.BaseController;
import com.jfinal.club.common.model.BaseMsg;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;


public class BaseMsgController extends BaseController{
	
	BaseMsgService srv = BaseMsgService.me;
	
	public void index() {
		list();
		setAttr("title", getPara("title")==null?"":getPara("title"));
		setAttr("msg", getPara("msg")==null?"":getPara("msg"));
		setAttr("port", getPara("port")==null?"":getPara("port"));
		render("index.html");
	}

	private void list() {
		String query = "";
		if(StrKit.notBlank(getPara("title"))) {
			query += " and title like '%"+getPara("title")+"%'";
		}
		if(StrKit.notBlank(getPara("msg"))) {
			query += " and msg like '%"+getPara("msg")+"%'";
		}
		if(StrKit.notBlank(getPara("port"))) {
			query += " and port = '"+getPara("port")+"'";
		}
		Page<BaseMsg> basemsgPage = srv.paginate(getParaToInt("p", 1), query);
		setAttr("basemsgPage", basemsgPage);
		keepPara();
	}
	
	/**
	 * 修改
	 */
	public void edit() {
		BaseMsg baseMsg = srv.edit(getParaToInt("id"));
		baseMsg.setMsg(Encrypt.decode(baseMsg.getMsg()));
		setAttr("baseMsg", baseMsg);
		setAttr("p", getPara("p"));
		render("edit.html");
	}

	/**
	 * 提交修改
	 */
	@Before(BaseMsgValidator.class)
	public void update() {
		BaseMsg baseMsg = getBean(BaseMsg.class);
		baseMsg.setMsg(Encrypt.encode(baseMsg.getMsg()));
		baseMsg.setModifyBy(Integer.toString(getLoginAccountId()));
		baseMsg.setModifyTime(new Date());
		Ret ret = srv.update(baseMsg);
		renderJson(ret);
	}

	@Before(BaseMsgValidator.class)
	public void save() {
		BaseMsg bm = getBean(BaseMsg.class);
		bm.setMsg(Encrypt.encode(bm.getMsg()));
		bm.setCreator(Integer.toString(getLoginAccountId()));
		bm.setCreateTime(new Date());
		Ret ret = srv.save(bm);
		renderJson(ret);
	}

	public void delete() {
		Ret ret = srv.delete(getParaToInt("id"));
		renderJson(ret);
	}

	public void deleteIds() {
		srv.deleteIds(getParaValuesToInt("id"));
		redirect("/admin/basemsg");
	}

	public void add() {
		render("add.html");
	}
	
	public void msg_list_readonly() {
		list();
		render("msg_list_readonly.html");
	}

	
	public void msg_edit_readonly() {
		BaseMsg bm = srv.edit(getParaToInt("id"));
		bm.setMsg(Encrypt.decode(bm.getMsg()));
		setAttr("baseMsg", bm);
		render("msg_edit_readonly.html");
	}

}
