package com.cjih.learnsystem.simulate;

import java.util.Date;
import java.util.List;

import cn.jbolt.common.util.ArrayUtil;
import com.cjih.learnsystem.common.util.Encrypt;
import com.jfinal.club.common.controller.BaseController;
import com.jfinal.club.common.model.*;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

public class MsgTempletController extends BaseController {

	public static int sn1 = 1;
	public static int x1sn2 = 1;
	public static int event1 = 1;

	protected MsgTempletService srv = MsgTempletService.me;

	public void templet_list() {

		setAttr("templetPage",srv.paginate(getParaToInt("p",1), getPara("title")));
		keepPara();
//		setAttr("title", getPara("title")==null?"":getPara("title"));
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
		setAttr("unitList", Unit.dao.find("select * from tresource_info where type_id='PUB001'"));
		//"select * from tunit where unit_status = '1'")
		setAttr("msgTypeList", BaseMsg.dao.find("select * from tbase_msg "));
		render("templet_sub_edit.html");
	}

	public void templet_sub_edit(){
		Long subTempletId = getParaToLong("subTempletId");

		MsgTempletSub mts =  MsgTempletSub.dao.findById(subTempletId);
		mts.setMsg(Encrypt.decode(mts.getMsg()));
		setAttr("sub",mts);
		setAttr("unitList", Unit.dao.find("select * from tresource_info where type_id='PUB001'"));
		//"select * from tunit where unit_status = '1'")
		setAttr("msgTypeList", BaseMsg.dao.find("select * from tbase_msg "));
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
		e.setTaxis(getParaToInt("taxis"));
		e.setUnitRole(getPara("unitRole"));
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
		List<MsgTempletSub> mtsList = MsgTempletSub.dao.find("select * from tmsg_templet_sub where templet_id = "+getParaToLong("templetId")+ " and unit_id in (select unit_type from tunit where unit_status = '1') order by taxis");
		int x2sn2=1,x3sn2=1;
		sn1++;
		for(MsgTempletSub mts:mtsList) {
			if("x1".equals(mts.getPort())) {
				X1msg msg = new X1msg();
				msg.setUnit(mts.getUnitId());
//				msg.setUnit(mts.getUnitTypeView());
				msg.setPerposeId(mts.getPerposeId());
				msg.setOperationResult(mts.getOperationResult());
				msg.setMsgArriveTime(mts.getMsgArriveTime());
				msg.setMsgType(mts.getMsgTypeView());
				msg.setCreator(Integer.toString(getLoginAccountId()));
				msg.setCreateTime(new Date());
				msg.setMemo(mts.getMsg());
				msg.setSn1(sn1);
				msg.setSn2(x1sn2++);
				msg.save();
			}
			else if("x2".equals(mts.getPort())){
				X2msg msg = new X2msg();
				msg.setUnit(mts.getUnitId());
				msg.setUnitRole(mts.getUnitRole());
//				msg.setUnit(mts.getUnitType());
				msg.setNeX2Addr(mts.getNeX2Addr());
//
				msg.setPerposeId(mts.getPerposeId());
				msg.setPerposeType(mts.getPerposeType());
				msg.setRelation1(mts.getRelation1());
				msg.setRelation2(mts.getRelation2());
				msg.setEventSn(""+event1++);//mts.getEventSn());
				msg.setCallSn(mts.getCallSn());
				msg.setCallSnChild(mts.getCallSnChild());
				msg.setEventTime(mts.getEventTime());
				msg.setCreator(Integer.toString(getLoginAccountId()));
				msg.setCreateTime(new Date());
				msg.setMemo(mts.getMsg());
				msg.setMsgType(mts.getMsgTypeView());
//				msg.setMsgType(getMsgType(Encrypt.decode(msg.getMemo())));
				msg.setSn1(sn1);
				msg.setSn2(x2sn2++);
				msg.save();
			}
			else if("x3".equals(mts.getPort())) {
				X3msg msg = new X3msg();
				msg.setUnit(mts.getUnitId());
				msg.setUnitRole(mts.getUnitRole());
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
//

				msg.setCreator(Integer.toString(getLoginAccountId()));
				msg.setCreateTime(new Date());
				msg.setMemo(mts.getMsg());
				msg.setMsgType(mts.getMsgTypeView());
				//msg.setMsgType(getMsgType(Encrypt.decode(msg.getMemo())));
				msg.setSn1(sn1);
				msg.setSn2(x3sn2);

				msg.save();
			}
		}
		renderJson(Ret.ok());
	}

	public void sort_up() {
		sort(getPara("templetId"), getPara("subTempletId"), true);
	}

	public void sort_down() {
		sort(getPara("templetId"), getPara("subTempletId"), false);
	}

	private void sort(String templetId, String subTempletId,boolean isUp) {
		MsgTempletSub mts = MsgTempletSub.dao.findById(subTempletId);
		Integer curTaxis = mts.getTaxis();
		int newTaxis = curTaxis-(isUp?1:-1);
		String sql = "update tmsg_templet_sub set taxis = "+ curTaxis+" where templet_id ="+templetId +
				" and taxis = "+newTaxis;
		Db.update(sql);
		sql = "update tmsg_templet_sub set taxis = "+ newTaxis+" where id ="+subTempletId;
		Db.update(sql);
		templet_edit();
	}

//	private void batch_sort(String templetId, String subTempletId,boolean isUp) {
//		MsgTempletSub mts = MsgTempletSub.dao.findById(subTempletId);
//		Integer curTaxis = mts.getTaxis();
//		int newTaxis = curTaxis-(isUp?1:-1);
//		String sql = "update tmsg_templet_sub set taxis = "+ curTaxis+" where templet_id ="+templetId +
//				" and taxis = "+newTaxis;
//		Db.update(sql);
//		sql = "update tmsg_templet_sub set taxis = "+ newTaxis+" where id ="+subTempletId;
//		Db.update(sql);
//		templet_edit();
//	}

//	public void sort_down() {
//		MsgTempletSub mts = MsgTempletSub.dao.findById(getPara("subTempletId"));
//		Integer curTaxis = mts.getTaxis();
//		int newTaxis = curTaxis+1;
//		String sql = "update tmsg_templet_sub set taxis = "+ curTaxis+" where templet_id ="+getPara("templetId") +
//				" and taxis = "+newTaxis;
//		Db.update(sql);
//		sql = "update tmsg_templet_sub set taxis = "+ newTaxis+" where id ="+getPara("subTempletId");
//		Db.update(sql);
//		templet_edit();
//	}



	public void getMessage(){
//		List<BaseMsg> msgs = BaseMsg.dao.find("select * from tbase_msg");
//		StringBuffer rtn = new StringBuffer();
//		for(BaseMsg msg:msgs){
//			rtn.append(msg.getMsg());
//		}
		renderText(Encrypt.decode(BaseMsg.dao.findFirst("select * from tbase_msg where id="+getPara("id")).getMsg()));
	}

//用于从消息中获取活动事件名称，应张远晶要求产生，后被其废弃。
//	public static String getMsgType(String memo){
//		String rtn = "";
//		if(StrKit.isBlank(memo)){
//			return rtn;
//		}
//		String lines[] = memo.split("\\n");
//		if(lines.length==0){
//			return rtn;
//		}
//		for(String line:lines){
//			if(line.startsWith("消息类型")){
//				rtn = line.substring(line.lastIndexOf('[')+1,line.lastIndexOf(']'));
//			}
//			if("活动事件上报".equals(rtn) && line.startsWith("事件内容")){
//				rtn = line.substring(line.lastIndexOf('(')+1,line.lastIndexOf(')'));
//			}
//		}
//		return rtn;
//	}

	public void deleteIds() {
		Db.delete("delete from tmsg_templet_sub where templet_id in (0"+ ArrayUtil.join(getParaValues("id"),",")+"0)");
		Db.delete("delete from tmsg_templet where id in (0"+ ArrayUtil.join(getParaValues("id"),",")+"0)");
		redirect("/admin/templet/templet_list");
	}

	public void deleteMsgIds() {
		Db.delete("delete from tmsg_templet_sub where id in (0"+ ArrayUtil.join(getParaValues("id"),",")+"0)");
		redirect("/admin/templet/templet_edit?templetId="+getPara("templetId"));
	}

	public void batch_up() {
		batch_move(true);
		redirect("/admin/templet/templet_edit?templetId="+getPara("templetId"));
	}

	private void batch_move(boolean isUp) {
		String templetId = getPara("templetId");
		String[] ids = getParaValues("id");
		int curTaxis = 0;
		int newTaxis = 0;

		if(ids!=null) {
			for (int i=0; i<ids.length; i++) {
				if(i==0){
					curTaxis = MsgTempletSub.dao.findById(ids[0]).getTaxis();
					newTaxis = curTaxis;
					if(isUp) {
						Db.update("update tmsg_templet_sub set taxis = "+ (curTaxis+ids.length-1)+" where templet_id =" + templetId +
								" and taxis = " + (curTaxis-1));
					}
					else{
						Db.update("update tmsg_templet_sub set taxis = " + curTaxis + " where templet_id =" + templetId +
								" and taxis = " + (curTaxis+ids.length));
					}
				}
				if(isUp) {
					Db.update("update tmsg_templet_sub set taxis = (taxis-1) where id =" + ids[i]);
				}
				else {
					Db.update("update tmsg_templet_sub set taxis = (taxis+1) where id =" + ids[i]);
				}
			}


		}
	}

	public void batch_down() {
		batch_move(false);
		redirect("/admin/templet/templet_edit?templetId="+getPara("templetId"));
	}


}
