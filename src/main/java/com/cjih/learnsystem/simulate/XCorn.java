package com.cjih.learnsystem.simulate;

import java.util.Date;
import java.util.List;

import com.cjih.learnsystem.common.util.BCD;
import com.jfinal.club.common.model.Unit;
import com.jfinal.club.common.model.X1msg;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;

public class XCorn extends Controller implements Runnable{

	@Override
	public void run() {
		
		/*
		 * X1端口无消息自动断开
		 */
		String sql = "select * from tunit where x1_status = '1' and id not in (select DISTINCT unit from tx1msg where TIMESTAMPDIFF(SECOND,create_time,now())<" + PropKit.get("Tx1_nomsg")+")";
		//Db.update("update tunit set x1_status = '0' where id in ("+sql+")");
		List<Unit> units = Unit.dao.find(sql);
		X1msg x1;
		for(Unit u:units) {
			x1 = new X1msg();
			x1.setUnit(u.getId());
			x1.setOperationResult("[Success][Time out]");
			x1.setMsgArriveTime(new Date());
			x1.setMsgType("连接释放通知");
			x1.setCreator("unit system");
			x1.setCreateTime(new Date());
			x1.setMemo("协议版本号........[1]<00 00 00 01 >\\n" + 
					"消息类型..........[连接释放通知]\\n" + 
					"LIC标 识(M).......["+u.getLicId()+"]<"+BCD.str2HexStr(u.getLicId())+">\\n" + 
					"网元标识(M).......["+u.getUnitId()+"]<"+BCD.str2HexStr(u.getUnitId())+">\\n" + 
					"连接释放原因(M)...[Time out]<00 00 00 01 >\\n");
			x1.save();
			u.setX1Status("0");//当前网元X1端口设置为未连接。
			u.update();
		}
		
		/*
		 * X2接口心跳消息
		 */
//		List<Unit> units = Unit.dao.find("select * from tunit where unit_status = 1 ");
//		X2Msg x2;
//		for(Unit u:units) {
//			x2 = new X2Msg();
//			x2.setUnit(u.getId());
//			x2.save();
//		}
		
	}

}
