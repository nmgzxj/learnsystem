package com.cjih.learnsystem.simulate;

import java.util.Date;
import java.util.List;

import com.cjih.learnsystem.common.util.Encrypt;
import com.jfinal.club.common.controller.BaseController;
import com.jfinal.club.common.model.MsgTemplet;
import com.jfinal.club.common.model.MsgTempletSub;
import com.jfinal.club.common.model.ResourceInfo;
import com.jfinal.club.common.model.Unit;
import com.jfinal.club.common.model.X1msg;
import com.jfinal.club.common.model.X2msg;
import com.jfinal.club.common.model.X3msg;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

public class MsgTempletController extends BaseController {

	public void templet_list() {
		Integer page = getParaToInt("p");
		if(page==null || page < 0) {
			page = 1;
		}
		Integer pageSize = PropKit.getInt("pageSize");
		String selectSql = "select *";
		String fromSql = " from tmsg_templet ";
		Page<MsgTemplet> templets = MsgTemplet.dao.paginate(page,pageSize,selectSql, fromSql);
		setAttr("templetPage",templets);
		keepPara();
		render("templet_list.html");
	}

	public void templet_edit(){
		Integer templetId = getParaToInt("templetId");
		MsgTemplet e = MsgTemplet.dao.findById(templetId);
		setAttr("templet",e);

		List<MsgTempletSub> subTemplets = MsgTempletSub.dao.find("select * from tmsg_templet_sub where templet_id ="+templetId +" order by taxis");//questionService.getQuestionListByExam(e.getId());
		setAttr("subTemplets", subTemplets);
		render("templet_edit.html");

	}

	public void templet_delete(){
		Long id = getParaToLong("templetId");
		Db.update("delete from tmsg_templet_sub where templet_id = '"+id+"'");
		MsgTemplet.dao.deleteById(id);
		templet_list();
	}

	public void templet_add(){
		render("templet_add.html");
	}

	public void templet_sub_add(){
		setAttr("templetId",getPara("templetId"));
		setAttr("unitList", Unit.dao.find("select * from tunit where unit_status = '1'"));
		setAttr("msgTypeList", ResourceInfo.dao.find("select * from tresource_info where type_id='PUB007'"));
		render("templet_sub_edit.html");
	}

	public void templet_sub_edit(){
		Long subTempletId = getParaToLong("subTempletId");

		MsgTempletSub mts =  MsgTempletSub.dao.findById(subTempletId);
		mts.setMsg(Encrypt.decode(mts.getMsg()));
		setAttr("sub",mts);
		setAttr("unitList", Unit.dao.find("select * from tunit where unit_status = '1'"));
		setAttr("msgTypeList", ResourceInfo.dao.find("select * from tresource_info where type_id='PUB007'"));
		render("templet_sub_edit.html");
	}

	public void templet_sub_add_save(){
		MsgTempletSub e = new MsgTempletSub();
		setTempletSubParam(e);
		e.setTaxis(getMaxTaxis(e.getTempletId())+1);
		e.save();
		templet_edit();
	}

	private int getMaxTaxis(int templetId) {
		Integer rtn=Db.queryInt("select max(taxis) from tmsg_templet_sub where templet_id = "+  templetId );
		if(rtn==null)
			rtn = 0;
		return rtn;
	}

	public void templet_sub_edit_save(){
		MsgTempletSub e = MsgTempletSub.dao.findById(getPara("subTempletId"));
		setTempletSubParam(e);
//	    e.setTaxis(getParaToInt("taxis"));
		e.update();
		templet_edit();
	}

	private void setTempletSubParam(MsgTempletSub e) {
		e.setCallSn(getPara("callSn"));
		e.setCallSnChild(getPara("callSnChild"));
		e.setDirection(getPara("direction"));
		e.setEventSn(getPara("eventSn"));
		e.setEventTime(getParaToDate("eventTime"));
		e.setFileName(getPara("fileName"));
		e.setLicId(getPara("licId"));
		e.setLicX3Addr(getPara("licX3Addr"));
		e.setMemo(getPara("memo"));
		e.setMsg(Encrypt.encode(getPara("msg")));
		e.setMsgArriveTime(getParaToDate("msgArriveTime"));
		e.setMsgType(getPara("msgType"));
		e.setNeX2Addr(getPara("neX2Addr"));
		e.setOperationResult(getPara("operationResult"));
		e.setOppositeAddr(getPara("oppositeAddr"));
		e.setPerposeId(getPara("perposeId"));
		e.setPerposeType(getPara("perposeType"));
		e.setPort(getPara("port"));
		e.setRelation1(getPara("relation1"));
		e.setRelation2(getPara("relation2"));
		e.setCccId(getPara("cccId"));
		e.setIcid(getPara("icid"));
		e.setTempletId(getParaToInt("templetId"));
		e.setUnitId(getPara("unitId"));
	}

	public void templet_sub_edit_delete(){
		MsgTempletSub e = MsgTempletSub.dao.findById(getPara("subTempletId"));
		if(null!=e)
			e.delete();
		templet_edit();
	}


	public void templet_update(){
		Long templetId = getParaToLong("templetId");
		MsgTemplet e = new MsgTemplet();
		e.setId(templetId);
		e.setTempletName(getPara("templetName"));
		e.setDescription("description");
		e.update();//.updateByPrimaryKey(e);
		List<MsgTempletSub> subTempletList = MsgTempletSub.dao.find("select * from tmsg_templet_sub where templet_id ="+templetId);
		setAttr("templet", e);
		setAttr("subTempletList", subTempletList);
		render("templet_edit.html");
	}

	public void add_save(){
		MsgTemplet e = new MsgTemplet();
		setTempletParam(e);
		e.save();

		setAttr("templet",e);

		List<MsgTempletSub> subTemplets = MsgTempletSub.dao.find("select * from tmsg_templet_sub where templet_id ="+e.getId() +" order by taxis");//questionService.getQuestionListByExam(e.getId());
		setAttr("subTemplets", subTemplets);
		render("templet_edit.html");
	}


	public void edit_save(){
		MsgTemplet e = MsgTemplet.dao.findById(getParaToLong("templetId"));
		setTempletParam(e);

		e.update();
		templet_list();
	}

	private void setTempletParam(MsgTemplet e) {
		e.setTempletName(getPara("templetName"));
		e.setDescription(getPara("description"));
		e.setCallSn(getParaToInt("callSn"));
		e.setCallSnChild(getParaToInt("callSnChild"));
		e.setCreateTime(new Date());
		e.setCreator(""+getLoginAccountId());
		e.setEcgi(getPara("ecgi"));
		e.setEventSn(getPara("eventSn"));
		e.setGummeid(getPara("gummeid"));
		e.setGuti(getPara("guti"));
		e.setImei(getPara("imei"));
		e.setImsi(getPara("imsi"));
		e.setLai(getPara("lai"));
		e.setLastVisitedTai("lastVisitedTai");
		e.setLicId(getPara("licId"));
		e.setModifyBy(""+getLoginAccountId());
		e.setModifyTime(new Date());
		e.setMsisdn(getPara("msisdn"));
		e.setOldMmeSgsn(getPara("oldMmeSgsn"));
		e.setProposeType(getPara("proposeType"));
		e.setTaiList(getPara("taiList"));
	}


	public void templet_run() {
		List<MsgTempletSub> mtsList = MsgTempletSub.dao.find("select * from tmsg_templet_sub where templet_id = "+getParaToLong("templetId")+ " order by taxis");
		for(MsgTempletSub mts:mtsList) {
			if("x1".equals(mts.getPort())) {
				X1msg msg = new X1msg();
				msg.setUnit(mts.getUnitId());
				msg.setPerposeId(mts.getPerposeId());
				msg.setOperationResult(mts.getOperationResult());
				msg.setMsgArriveTime(mts.getMsgArriveTime());
				msg.setMsgType(mts.getMsgTypeView());
				msg.setCreator(Integer.toString(getLoginAccountId()));
				msg.setCreateTime(new Date());
				msg.setMemo(mts.getMsg());
				msg.save();
			}
			else if("x2".equals(mts.getPort())){
				X2msg msg = new X2msg();
				msg.setUnit(mts.getUnitId());
				msg.setNeX2Addr(mts.getNeX2Addr());
				msg.setMsgType(mts.getMsgTypeView());
				msg.setPerposeId(mts.getPerposeId());
				msg.setPerposeType(mts.getPerposeType());
				msg.setRelation1(mts.getRelation1());
				msg.setRelation2(mts.getRelation2());
				msg.setEventSn(mts.getEventSn());
				msg.setCallSn(mts.getCallSn());
				msg.setCallSnChild(mts.getCallSnChild());
				msg.setEventTime(mts.getEventTime());
				msg.setCreator(Integer.toString(getLoginAccountId()));
				msg.setCreateTime(new Date());
				msg.setMemo(mts.getMsg());
				msg.save();
			}
			else if("x3".equals(mts.getPort())) {
				X3msg msg = new X3msg();
				msg.setUnit(mts.getUnitId());
				msg.setPerposeId(mts.getPerposeId());
				msg.setPerposeType(mts.getPerposeType());
				msg.setLicX3Addr(mts.getLicX3Addr());
				msg.setRelation1(mts.getRelation1());
				msg.setRelation2(mts.getRelation2());
				msg.setMsgSn(mts.getMsgSn());
				msg.setOppositeAddr(mts.getOppositeAddr());
				msg.setOperationResult(mts.getOperationResult());
				msg.setFileName(mts.getFileName());
				msg.setEventTime(mts.getEventTime());
				msg.setMsgType(mts.getMsgTypeView());
				msg.setCreator(Integer.toString(getLoginAccountId()));
				msg.setCreateTime(new Date());
				msg.setMemo(mts.getMsg());

				msg.save();
			}
		}
		renderJson(Ret.ok());
	}

	public void sort_up() {
		MsgTempletSub mts = MsgTempletSub.dao.findById(getPara("subTempletId"));
		Integer curTaxis = mts.getTaxis();
		Integer newTaxis = curTaxis-1;
//    		mts.setTaxis(newTaxis);
//    		mts.update();
		String sql = "update tmsg_templet_sub set taxis = "+ curTaxis+" where templet_id ="+getPara("templetId") +
				" and taxis = "+newTaxis;
		Db.update(sql);
		sql = "update tmsg_templet_sub set taxis = "+ newTaxis+" where id ="+getPara("subTempletId");
		Db.update(sql);
		templet_edit();
	}

	public void sort_down() {
		MsgTempletSub mts = MsgTempletSub.dao.findById(getPara("subTempletId"));
		Integer curTaxis = mts.getTaxis();
		Integer newTaxis = curTaxis+1;
//		mts.setTaxis(newTaxis);
//		mts.update();
		String sql = "update tmsg_templet_sub set taxis = "+ curTaxis+" where templet_id ="+getPara("templetId") +
				" and taxis = "+newTaxis;
		Db.update(sql);
		sql = "update tmsg_templet_sub set taxis = "+ newTaxis+" where id ="+getPara("subTempletId");
		Db.update(sql);
		templet_edit();
	}

}
