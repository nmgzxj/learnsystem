package com.cjih.learnsystem.baseflow;

import java.util.Date;
import java.util.List;

import com.cjih.learnsystem.common.util.Encrypt;
import com.jfinal.aop.Before;
import com.jfinal.club.common.controller.BaseController;
import com.jfinal.club.common.model.BaseFlow;
import com.jfinal.club.common.model.BaseFlowMsg;
import com.jfinal.club.common.model.ResourceInfo;
import com.jfinal.club.common.model.Unit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class BaseFlowController extends BaseController{
	
	BaseFlowService srv = BaseFlowService.me;
	
	public void index() {
		Page<BaseFlow> baseFlowPage = srv.paginate(getParaToInt("p", 1));
		setAttr("baseFlowPage", baseFlowPage);
		render("index.html");
	}
	
	/**
	 * 修改
	 */
	public void edit() {
		Integer flowId = getParaToInt("id");
		if(null==flowId) {
			flowId = getParaToInt("flowId");
		}
		setAttr("baseFlow", srv.edit(flowId));
		String sql = "select * from tbase_flow_msg where flow_id =" + getParaToInt("id") + " order by taxis";
		List<Record> flowMsgs = Db.find(sql);
		setAttr("flowMsgs", flowMsgs);

		render("edit.html");
	}

	/**
	 * 提交修改
	 */
	@Before(BaseFlowValidator.class)
	public void update() {
		BaseFlow baseFlow = getBean(BaseFlow.class);
		baseFlow.setModifyBy(Integer.toString(getLoginAccountId()));
		baseFlow.setModifyTime(new Date());
		Ret ret = srv.update(baseFlow);
		renderJson(ret);
	}

	public void save() {
		BaseFlow baseFlow = getBean(BaseFlow.class);
		baseFlow.setCreator(Integer.toString(getLoginAccountId()));
		baseFlow.setCreateTime(new Date());
		srv.save(baseFlow);
		setAttr("baseFlow", baseFlow);
		render("edit.html");
//		renderJson(ret);
	}

	public void delete() {
		Ret ret = srv.delete(getParaToInt("id"));
		renderJson(ret);
	}

	public void add() {
		render("add.html");
	}
	
    public void msg_add(){
      setAttr("flowId",getPara("flowId"));
      setAttr("msgTypeList", ResourceInfo.dao.find("select * from tresource_info where type_id='PUB007'"));
      setAttr("unitList", Unit.dao.find("select * from tunit where unit_status = '1'"));
      render("msg_add.html");
  }

    public void msg_edit(){
  	  	BaseFlowMsg mts =  BaseFlowMsg.dao.findById(getPara("flowMsgId"));
  	  	mts.setMsg(Encrypt.decode(mts.getMsg()));
  	  	setAttr("sub",mts);    
        setAttr("unitList", Unit.dao.find("select * from tunit where unit_status = '1'"));
        setAttr("msgTypeList", ResourceInfo.dao.find("select * from tresource_info where type_id='PUB007'"));
        render("msg_edit.html");
    }

	
  public void sort_up() {
		BaseFlowMsg mts = BaseFlowMsg.dao.findById(getPara("flowMsgId"));
		Integer curTaxis = mts.getTaxis();
		if(null==curTaxis) curTaxis=0;
		Integer newTaxis = curTaxis-1;
//		mts.setTaxis(newTaxis);
//		mts.update();
		String sql = "update tbase_flow_msg set taxis = "+ curTaxis+" where flow_id ="+mts.getFlowId() + 
				" and taxis = "+newTaxis;
		Db.update(sql);
		sql = "update tbase_flow_msg set taxis = "+ newTaxis+" where id ="+getPara("flowMsgId");
		Db.update(sql);
		edit();
	}
	
	public void sort_down() {
		BaseFlowMsg mts = BaseFlowMsg.dao.findById(getPara("flowMsgId"));
		Integer curTaxis = mts.getTaxis();
		if(null==curTaxis) curTaxis=0;
		Integer newTaxis = curTaxis+1;
	//	mts.setTaxis(newTaxis);
	//	mts.update();
		String sql = "update tbase_flow_msg set taxis = "+ curTaxis+" where flow_id ="+mts.getFlowId() + 
				" and taxis = "+newTaxis;
		Db.update(sql);
		sql = "update tbase_flow_msg set taxis = "+ newTaxis+" where id ="+getPara("flowMsgId");
		Db.update(sql);
		edit();
	}


	public void flow_msg_add_save(){
		BaseFlowMsg e = new BaseFlowMsg();
		e.setFlowId(getParaToInt("flowId"));
		e.setTitle(getPara("title"));
		e.setMsg(Encrypt.encode(getPara("msg")));
		e.setPort(getPara("port"));
		e.setDirection(getPara("direction"));
		e.setUnitId(getPara("unitId"));
		e.setMsgType(getPara("msgType"));
		e.setTaxis(getMaxTaxis(getParaToInt("flowId"))+1);
		e.setCreateTime(new Date());
		e.setCreator(Integer.toString(getLoginAccountId()));
		e.save();
		setAttr("baseFlow", BaseFlow.dao.findById(getParaToInt("flowId")));
		String sql = "select * from tbase_flow_msg where flow_id =" +  getParaToInt("flowId") + " order by taxis";
		List<Record> flowMsgs = Db.find(sql);
		setAttr("flowMsgs", flowMsgs);
		render("edit.html");
		//Ret.ok();
	}

	private int getMaxTaxis(int flowId) {
		Integer rtn=Db.queryInt("select max(taxis) from tbase_flow_msg where flow_id =" +  flowId );
		if(rtn==null)
			rtn = 0;
		return rtn;
	}
	
	public void flow_msg_edit_save(){
	BaseFlowMsg e = BaseFlowMsg.dao.findById(getPara("flowMsgId"));
		e.setTitle(getPara("title"));
		e.setPort(getPara("port"));
	e.setMsg(Encrypt.encode(getPara("msg")));
	e.setDirection(getPara("direction"));
	e.setUnitId(getPara("unitId"));
	e.setMsgType(getPara("msgType"));
	e.setModifyBy(Integer.toString(getLoginAccountId()));
	e.setModifyTime(new Date());
	e.update();
	setAttr("baseFlow", BaseFlow.dao.findById(getParaToInt("flowId")));
	String sql = "select * from tbase_flow_msg where flow_id =" +  getParaToInt("flowId") + " order by taxis";
	List<Record> flowMsgs = Db.find(sql);
	setAttr("flowMsgs", flowMsgs);
	render("edit.html");
	}
	
	public void flow_msg_edit_delete(){
	BaseFlowMsg e = BaseFlowMsg.dao.findById(getPara("flowMsgId"));
	if(null!=e)
			e.delete();
	setAttr("baseFlow", BaseFlow.dao.findById(getParaToInt("flowId")));
	String sql = "select * from tbase_flow_msg where flow_id =" +  getParaToInt("flowId") + " order by taxis";
	List<Record> flowMsgs = Db.find(sql);
	setAttr("flowMsgs", flowMsgs);
	render("edit.html");	
	}



	public void flow_list_readonly() {
		Page<BaseFlow> baseFlowPage = srv.paginate(getParaToInt("p", 1));
		setAttr("baseFlowPage", baseFlowPage);

		render("flow_list_readonly.html");
	}
	
	
	public void flow_edit_readonly() {
		Integer flowId = getParaToInt("id");
		if(null==flowId) {
			flowId = getParaToInt("flowId");
		}
		setAttr("baseFlow", srv.edit(flowId));
		String sql = "select * from tbase_flow_msg where flow_id =" + getParaToInt("id") + " order by taxis";
		List<Record> flowMsgs = Db.find(sql);
		setAttr("flowMsgs", flowMsgs);

		render("flow_edit_readonly.html");
	}
	
	public void flow_msg_edit_readonly(){
		  BaseFlowMsg mts =  BaseFlowMsg.dao.findById(getPara("flowMsgId"));
		  mts.setMsg(Encrypt.decode(mts.getMsg()));
	    setAttr("sub",mts);    
	    setAttr("unitList", Unit.dao.find("select * from tunit where unit_status = '1'"));
	    setAttr("msgTypeList", ResourceInfo.dao.find("select * from tresource_info where type_id='PUB007'"));
	    render("flow_msg_edit_readonly.html");
	}


}
//	
//	public void flow_list_choice() {
//		Integer page = getParaToInt("page");
//		if(page==null || page < 0) {
//			page = 1;
//		}
//		Integer pageSize = PropKit.getInt("pageSize");
//		String selectSql = "select *";
//		String fromSql = " from tbase_flow ";
//		Page<BaseFlow> flows = BaseFlow.dao.paginate(page,pageSize,selectSql, fromSql);
//		setAttr("flowList", flows.getList());
//        keepPara();
//
//		renderJsp("/basemsg/flow_list_choice.jsp");
//	}
//
//	
//
//	public void edit() {
//		String flowId = getPara("id");
//		if(StrKit.isBlank(flowId)) {
//			flowId = getPara("flowId");
//		}
//		BaseFlow bf = BaseFlow.dao.findById(flowId);
//		setAttr("baseFlow", bf);
//
//        String sql = "select * from tbase_flow_msg where flow_id ="
//        + flowId + " order by taxis";
//        List<Record> flowMsgs = Db.find(sql);
//
//        setAttr("flowMsgs", flowMsgs);
//		render("edit.html");
//	}

//
//	
//	public void add() {
//		render("edit.html");
//	}
//
//	public void save() {
//		BaseFlow bm = getBean(BaseFlow.class);
//		bm.setCreateTime(new Date());
//		bm.save();
//	}
//	
//	public void update() {
//		BaseFlow bm = getBean(BaseFlow.class);
//		bm.setModifyTime(new Date());
//		bm.update();
//	}
//
//	public void delete() {
//		BaseFlow bm = BaseFlow.dao.findById(getPara("id"));
//		bm.delete();
//	}
//	

//	

//      
//      public void flow_msg_add_save(){
//    	  	BaseFlowMsg e = new BaseFlowMsg();
//    	  	e.setFlowId(getParaToInt("flowId"));
//    	  	e.setTitle(getPara("title"));
//  	    e.setMsg(getPara("msg"));
//  	    e.setPort(getPara("port"));
//  	    e.setTaxis(1+Db.queryInt("select max(taxis) from tbase_flow_msg where flow_id="+e.getFlowId()));
//  	    e.setCreateTime(new Date());
////  	    e.setCreator(getSessionAttr("userName"));
//  	    e.save();
//  	    edit();
//      }
//
//      public void flow_msg_edit_save(){
//    	  BaseFlowMsg e = BaseFlowMsg.dao.findById(getPara("flowMsgId"));
//    	  	e.setTitle(getPara("title"));
//    	  	e.setPort(getPara("port"));
//  	    e.setMsg(getPara("msg"));
////  	    e.setModifyBy(getSessionAttr("userName"));
//  	    e.setModifyTime(new Date());
//  	    e.update();
//  	    edit();
//      }
//
//      public void flow_msg_edit_delete(){
//    	  BaseFlowMsg e = BaseFlowMsg.dao.findById(getPara("flowMsgId"));
//  	    if(null!=e)
//  	    		e.delete();
//  	    edit();
//      }
//
///    	  	
//      }
//
//}
