package com.cjih.learnsystem.simulate;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.cjih.learnsystem.common.util.BCD;
import com.cjih.learnsystem.common.util.Encrypt;
import com.jfinal.club.common.model.ResourceInfo;
import com.jfinal.club.common.model.TrueUnit;
import com.jfinal.club.common.model.Unit;
import com.jfinal.club.common.model.X1msg;
import com.jfinal.club.common.model.X2msg;
import com.jfinal.club.common.model.X3msg;
import com.jfinal.aop.Before;
import com.jfinal.club.common.controller.BaseController;
import com.jfinal.club.common.model.Number;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

/**
 * Created by ZhangXianjin on 2017/11/29.
 */
public class UnitController extends BaseController {

	private static final Logger logger = Logger.getLogger(UnitController.class);

	public void simulationunit() {
		setAttr("unitList", Unit.dao.find("select * from tunit"));
		setAttr("units", ResourceInfo.dao.find("select * from tresource_info where type_id='PUB001'"));
		render("simulationunit.html");
	}

	public void unitinfo() {
		setAttr("trueUnitList", TrueUnit.dao.find("select * from ttrue_unit"));
		setAttr("units", ResourceInfo.dao.find("select * from tresource_info where type_id='PUB001'"));
		render("unitinfo.html");
	}

	public void getUnitType(){
		renderText(""+Db.queryStr("select unit_type from tunit where id = '"+getPara("unitId")+"' "));
	}

	public void simulationmsg() {
		String unit = getPara("unit");
		String msgType = getPara("msgType");
		String x2sql = "select * from tx2msg where 1=1 ";
		x2sql = getSearchSql(x2sql);
		setAttr("x2MsgList", X2msg.dao.find(x2sql));

		String x3sql = "select * from tx3msg where 1=1 ";
		x3sql = getSearchSql(x3sql);
		setAttr("x3MsgList", X3msg.dao.find(x3sql));
		setAttr("unitList", Unit.dao.find("select * from tunit where unit_status = '1'"));
		render("simulationmsg.html");
	}

	public void simulation1() {
		String select = "select *";
		String sqlExceptSelect = " from tX1msg where 1=1";
		sqlExceptSelect = getSearchSql(sqlExceptSelect);
		Page<X1msg> page = X1msg.dao.paginate(1, PropKit.getInt("pageSize"), select, sqlExceptSelect);
		// setAttr("X1msgList", page.getList());//todo
		setAttr("X1msgList", X1msg.dao.find(select + sqlExceptSelect));
		setAttr("unitList", Unit.dao.find("select * from tunit where unit_status = '1'"));
		render("simulation1.html");
	}

	private String getSearchSql(String sqlExceptSelect) {
		String unit = getPara("unit");
		String msgType = getPara("operType");
		if ("search".equals(getPara("handle"))) {
			if (unit != null && !unit.equals("")) {
				sqlExceptSelect += " and unit = '" + unit + "'";
			}
			if (msgType != null && !msgType.equals("")) {
				sqlExceptSelect += " and msg_type = (select resource_name from tresource_info where resource_id = '"
						+ msgType + "')";
			}
		}
		sqlExceptSelect += " order by id";
		return sqlExceptSelect;
	}

	/**
	 * X1接口发送消息
	 */
	@Before(UnitValidator.class)
	public void X1MsgAdd() {
		String operType = getPara("operType");
		if (operType == null || operType.isEmpty()) {
			setAttr("msg", "操作类型不正确，请联系管理员。");
			render("/msg.html");
		}
		if (!operType.equals("a1") && "0".equals(Unit.dao.findById(getPara("unit")).getX1Status())) {
			setAttr("msg", "该网元X1接口未连接，请先建立连接。");
			render("/msg.html");
		} else {
			System.out.println("x1status=" + Unit.dao.findById(getPara("unit")).getX1Status());
			System.out.println("operType=" + operType);
			if (operType.equals("a1")) {
				a1();
			} else if (operType.equals("a2")) {
				a2();
			} else if (operType.equals("a3")) {
				a3();
			} else if (operType.equals("a4")) {
				a4();
			} else if (operType.equals("a5")) {
				a5();
			} else if (operType.equals("a6")) {
				a6();
			}
			else if (operType.equals("a7")) {
				a7();
			}
			else if (operType.equals("a8")) {
				a8();
			}
			else if (operType.equals("a9")) {
				a9();
			}
			else if (operType.equals("a10")) {
				a10();
			}
			else if (operType.equals("a11")) {
				a11();
			}
			else if (operType.equals("a12")) {
				a12();
			}
			else if (operType.equals("a13")) {
				a13();
			}
			simulation1();
		}
	}

	private void setMsg(X1msg msg){
        msg.setUnit(getPara("unit"));
        msg.setPerposeId(getPara("number"));
        msg.setOperationResult("成功");
        msg.setMsgArriveTime(new Date());
        msg.setMsgType("未知消息");
        msg.setCreator(""+getLoginAccountId());
        msg.setCreateTime(new Date());
        msg.setMemo("未知消息");
        msg.setMemo(Encrypt.encode(msg.getMemo()));
        msg.setX2ip(getPara("x2Ip"));
        msg.setX2port(getPara("x2Port"));
        msg.setX3ip(getPara("x3Ip"));
        msg.setX3port(getPara("x3Port"));
        msg.setMonType(getPara("monType"));
        msg.setAddType(getPara("addType"));
        msg.setAdd(getPara("add"));
        msg.setTp(getPara("tp"));
        msg.setAppBizType(getPara("appBizType"));
        msg.setLnsAdd(getPara("lnsAdd"));
    }

	/*
	 * 连接建立消息
	 *
	 */
	private void a1() {
		Unit u = Unit.dao.findById(getPara("unit"));
		X1msg msg = new X1msg();
		setMsg(msg);

		msg.setMsgType("连接建立请求");
		msg.setMemo("协议版本号........[1]<00 00 00 01 >\\n"
				+ "消息类型..........[连接建立请求]\\n" + "LIC标 识(M).......[" + u.getLicId() + "]<" + BCD.str2HexStr(u.getLicId()) + ">\\n"
				+ "网元标识(M).......[" + u.getUnitId() + "]<" + BCD.str2HexStr(u.getUnitId()) + " >\\n"
				+ "SQN组号(M)........[" + u.getX1Sqn() + "]<00 00 00 02 >\\n"
				+ "RAND(M)...........<ca f6 96 be dc b1 d2 >\\n"
				+ "认证字段(M).......<d5 64 58 30 6b 24 bb 24 1c 9c 34 e0 5a 62 86 35 >\\n");
		msg.setMemo(Encrypt.encode(msg.getMemo()));
		msg.save();
		msg = new X1msg();
        setMsg(msg);
		msg.setMsgType("连接建立响应");
		String memo = "协议版本号........[1]<00 00 00 01 >\\n"
				+ "消息类型..........[连接建立响应]\\n"
				+ "网元标识(M).......[" + u.getUnitId() + "]<" + BCD.str2HexStr(u.getUnitId()) + ">\\n"
				+ "LIC标 识(M).......[" + u.getLicId() + "]<" + BCD.str2HexStr(u.getLicId()) + ">\\n";
		try {
			TrueUnit unitInfo = TrueUnit.dao.findFirst("select * from ttrue_unit where unit_id = '" + u.getUnitId()
					+ "' and lic_id = '" + u.getLicId() + "'");
			if (null == unitInfo) {// 如果网元id和licid没有对应关系，则判定为未登记。
				memo += "执行结果(M).......[Failure]<00 00 00 01 >\\n"
						+ "失败原因(C).......[Ne Not Registered]<00 00 00 00 >\\n"
						+ "应答字段(C).......[Invalid][Ne Not Registered]\\n";
				msg.setOperationResult("[Failure]");
			}
			else if(!u.getUnitId().equals(unitInfo.getUnitId())) {//疑问：网元ID非法？
				memo += "执行结果(M).......[Failure]<00 00 00 01 >\\n" +
						"失败原因(C).......[Ne Id Invalid]<00 00 00 02 >\\n" +
						"应答字段(C).......[Invalid]\\n";
			}
			else if (!u.getX1Sqn().equals(unitInfo.getX1Sqn()) | !u.getX1Kt().equals(unitInfo.getX1Kt())
					| !u.getX1Pwd().equals(unitInfo.getX1Pwd())) {
				memo += "执行结果(M).......[Failure]<00 00 00 01 >\\n"
						+ "失败原因(C).......[Authentication Fail]<00 00 00 01 >\\n"
						+ "应答字段(C).......[Invalid]\\n";
				msg.setOperationResult("[Failure][Authentication Fail]");
			}
			else if ("1".equals(u.getX1Status())) {
				memo += "执行结果(M).......[Failure]<00 00 00 01 >\\n"
						+ "失败原因(C).......[Connect is Exist]<00 00 00 03 >\\n"
						+ "应答字段(C).......[Invalid]\\n";
				msg.setOperationResult("[Failure][Connect is Exist]");
			} else {
				memo += "执行结果(M).......[Success]<00 00 00 00 >\\n"
						+ "失败原因(C).......[Connect is Exist]<00 00 00 03 >\\n"
						+ "应答字段(C).......[Invalid]\\n";
				msg.setOperationResult("[Success]");
			}
		} catch (Exception e) {
			e.printStackTrace();
			memo += "执行结果(M).......[Failure]<00 00 00 01 >\\n"
					+ "失败原因(C).......[Connect is Exist]<00 00 00 FF >\\n"
					+ "应答字段(C).......[Other]\\n";
			msg.setOperationResult("[Failure][Connect is Exist]");

		}
		msg.setMemo(memo);
		msg.setMemo(Encrypt.encode(msg.getMemo()));
		msg.save();
		u.setX1Status("1");// 设置当前网元X1端口状态为已连接。
		u.update();
	}

	/**
	 * 断开连接通知
	 */
	private void a2() {
		String unit = getPara("unit");
		Unit u = Unit.dao.findById(unit);
		u.setX1Status("0");
		u.update();
		X1msg msg = new X1msg();
		setMsg(msg);
		msg.setOperationResult("[Success][Normal Release]");
		msg.setMsgType("连接释放通知");
		msg.setMemo("协议版本号........[1]<00 00 00 01 >\\n" + "消息类型..........[连接释放通知]\\n" + "LIC标 识(M).......["
				+ u.getLicId() + "]<" + BCD.str2HexStr(u.getLicId()) + ">\\n" + "网元标识(M).......[" + u.getUnitId() + "]<"
				+ BCD.str2HexStr(u.getUnitId()) + " >\\n" + "连接释放原因(M)...[Normal Release]<00 00 00 00 >\\n");
		msg.setMemo(Encrypt.encode(msg.getMemo()));
		msg.save();
	}

	/**
	 * BKMB设定消息
	 */
	private void a3() {
		String unit = getPara("unit");
		Unit u = Unit.dao.findById(unit);
		X1msg msg = new X1msg();
		setMsg(msg);
		msg.setMsgType("BKMB设定请求");
		msg.setMemo("协议版本号........[1]<01>\\n" + "消息类型..........[BKMB设定请求]\\n" + "MB类型(M).......["
				+ getPara("numberFormat") + "]<" + BCD.getTargetType(getPara("numberFormat")) + ">\\n"
				+ "MB标识(M).......[" + getPara("number") + "]<" + BCD.str2rBcdstr(getPara("number")) + ">\\n");
		msg.setMemo(Encrypt.encode(msg.getMemo()));
		msg.save();
		msg = new X1msg();
		setMsg(msg);
		msg.setMsgType("BKMB设定响应");
		String memo = "协议版本号........[1]<01>\\n" + "消息类型..........[BKMB设定响应]\\n" + "MB类型(M).......["
				+ getPara("numberFormat") + "]<" + BCD.getTargetType(getPara("numberFormat")) + ">\\n"
				+ "MB标识(M).......[" + getPara("number") + "]<" + BCD.str2rBcdstr(getPara("number")) + ">\\n";
		/*
		 * 将号码存入被测网元 失败处理： 1.如果已BK，返回BKMB已存在 2.对于HSS，如果MB未开户，则HSS返回失败“用户不存在”
		 * 3.如果采用IMEI，号码长度不是15位，返回参数错误。 4.超过网元上限，返回“资源不足”
		 */
		List<Number> existNums = Number.dao.find(
				"select * from tnumber where unit='" + u.getId() + "' and number_format='" + getPara("numberFormat")
						+ "' and " + getPara("numberFormat") + "='" + getPara("number") + "'");
		if (existNums.size() > 0) {
			memo += "操作结果(M).......[BKMB已存在]<02>\\n" + "命令失败原因(C)...[MB Be Set]\\n";
			msg.setOperationResult("[BKMB已存在]");
		} else if (Number.dao.find("select * from tnumber where unit='" + u.getId() + "'").size() > PropKit
				.getInt("unit_number_size")) {
			memo += "操作结果(M).......[资源限制]<02>\\n" + "命令失败原因(C)...[resource limited]\\n";
			msg.setOperationResult("[资源不足]");
		} else {
			Number num = new Number();
			num.setNumberFormat(getPara("numberFormat"));
			if ("msisdn".equals(num.getNumberFormat())) {
				num.setMsisdn(getPara("number"));
				num.setImei("11111111");
				num.setImsi("11111111");
                memo = getHssMemo(u, msg, memo);
            } else if ("imei".equals(num.getNumberFormat())) {
				num.setImei(getPara("number"));
				num.setMsisdn("11111111");
				num.setImsi("11111111");
				/*
				 * IMEI号低于15的，报参数错误。
				 */
				if (num.getImei().length() < 15) {
					memo += "操作结果(M).......[失败]<00>\\n" + "命令失败原因(C)...[parameterError]<06>\\n";
				} else {
                    memo = getHssMemo(u, msg, memo);
                }
			} else if ("imsi".equals(num.getNumberFormat())) {
				num.setImsi(getPara("number"));
				num.setMsisdn("11111111");
				num.setImei("11111111");
				memo += "操作结果(M).......[成功]<00>\\n" + "命令失败原因(C)...[Invalid]\\n";
			}

			num.setCreator(""+getLoginAccountId());
			num.setCreateTime(new Date());
			num.setUnit(unit);
			num.save();
		}
		msg.setMemo(memo);
		msg.setMemo(Encrypt.encode(msg.getMemo()));
		msg.save();
	}

    private String getHssMemo(Unit u, X1msg msg, String memo) {
        if (u.getUnitTypeView().equals("HSS") && !getPara("number").equals(PropKit.get("HSS_number"))) {
            memo += "操作结果(M).......[号码不在HSS库中]<00>\\n"
                    + "命令失败原因(C)...[Invalid]\\n";
            msg.setOperationResult("[号码不在HSS库中]");
        } else {
            memo += "操作结果(M).......[成功]<00>\\n"
                    + "命令失败原因(C)...[Invalid]\\n";
        }
        return memo;
    }

    /*
	 * BKMB撤销 1.如果MB不是BK状态，返回失败原因“BKMB不存在” 2.对于HSS，这条在模拟器上与其他网元相同，不考虑特殊处理。
	 */
	private void a4() {
        Unit u = setUnitStatus("1");
        X1msg msg = new X1msg();
		setMsg(msg);
		msg.setMsgType("BKMB撤销请求");
		msg.setMemo("协议版本号........[1]<01>\\n" + "消息类型..........[BKMB撤消请求]\\n" + "MB类型(M).......["
				+ getPara("numberFormat") + "]<" + BCD.getTargetType(getPara("numberFormat")) + ">\\n"
				+ "MB标识(M).......[" + getPara("number") + "]<" + BCD.str2rBcdstr(getPara("number")) + ">\\n");
		msg.setMemo(Encrypt.encode(msg.getMemo()));
		msg.save();

		msg = new X1msg();
		setMsg(msg);
		msg.setMsgType("BKMB撤销响应");
		String memo = "协议版本号........[1]<01>\\n" + "消息类型..........[BKMB撤消响应]\\n" + "MB类型(M).......["
				+ getPara("numberFormat") + "]<" + BCD.getTargetType(getPara("numberFormat")) + ">\\n"
				+ "MB标识(M).......[" + getPara("number") + "]<" + BCD.str2rBcdstr(getPara("number")) + ">\\n";
		List<Number> existNums = Number.dao.find(
				"select * from tnumber where unit='" + u.getId() + "' and number_format='" + getPara("numberFormat")
						+ "' and " + getPara("numberFormat") + "='" + getPara("number") + "'");
		if (existNums.size() == 0) {
			memo += "操作结果(M).......[BKMB不存在]<02>\\n" + "命令失败原因(C)...[MB not exist]\\n";
			msg.setOperationResult("[BKMB不存在]");
		} else {
			Db.update("delete from tnumber where " + getPara("numberFormat") + " = '" + getPara("number") + "'");
			memo += "操作结果(M).......[成功]<00>\\n" + "命令失败原因(C)...[Invalid]\\n";
			msg.setOperationResult("[成功]");
		}
		msg.setMemo(memo);
		msg.setMemo(Encrypt.encode(msg.getMemo()));
		msg.save();
	}

    /**
     * 设置网元X1接口连接状态，1：连接。0：断开。
     * @param status
     * @return
     */
    private Unit setUnitStatus(String status) {
        Unit u = Unit.dao.findById(getPara("unit"));
        u.setX1Status(status);
        u.update();
        return u;
    }

    /**
	 * BKMB 列示 1.如果网元中没有BKMB，则返回“没有目标标识”
	 */
	private void a5() {
		Unit u = setUnitStatus("1");
		X1msg msg = new X1msg();
		setMsg(msg);
		msg.setMsgType("BKMB列示");
		msg.setMemo("协议版本号........[1]<01>\\n" + "消息类型..........[BKMB列示请求]\\n" + "MB类型(M).......["
				+ getPara("numberFormat") + "]<" + BCD.getTargetType(getPara("numberFormat")) + ">\\n");
		msg.setMemo(Encrypt.encode(msg.getMemo()));
		msg.save();
		msg = new X1msg();
		setMsg(msg);
		msg.setMsgType("BKMB列示响应");
		String memo = "";
		memo = "协议版本号........[1]<01>\\n" + "消息类型..........[BKMB列示响应]\\n" + "操作结果(M).......[成功]<00>\\n"
				+ "命令失败原因(C)...[Invalid]\\n" + "BKMB列表(C)...\\n" + "[\\n";
		List<Number> numbers = Number.dao.find("select * from tnumber where unit='" + u.getId() + "'");
		if (numbers.size() == 0) {
			memo = "协议版本号........[1]<01>\\n" + "消息类型..........[BKMB列示响应]\\n" + "操作结果(M).......[没有BK目标]<00>\\n"
					+ "命令失败原因(C)...[no monitor]\\n";
			memo += "";
			msg.setOperationResult("没有BK目标");
		} else {
			String format = "";
			int i = 1;
			for (Number n : numbers) {
				format = n.getNumberFormat();
				if ("msisdn".equals(format)) {
					memo += "  Num=[";
					memo += n.getMsisdn();
					memo += "]<" + BCD.str2rBcdstr(n.getMsisdn()) + ">  Numtype=[msisdn]<00> \\n";
				} else if ("imsi".equals(format)) {
					memo += "  Num=[";
					memo += n.getImsi();
					memo += "]<" + BCD.str2rBcdstr(n.getImsi()) + ">  Numtype=[imsi]<01> \\n";
				} else if ("imei".equals(format)) {
					memo += "  Num=[";
					memo += n.getImei();
					memo += "]<" + BCD.str2rBcdstr(n.getImei()) + ">  Numtype=[imei]<02> \\n";
				}
				if (i % 100 == 0) {
					memo += "]本次列示MB数:100\\n结束标记(C).......[Continue]<00>\\\\n";
					msg.setMemo(memo);
					msg.setMemo(Encrypt.encode(msg.getMemo()));
					msg.save();
					msg = new X1msg();
					setMsg(msg);
					msg.setMsgType("BKMB列示响应");
					i = 0;
					memo = "协议版本号........[1]<01>\\n" + "消息类型..........[BKMB列示响应]\\n" + "操作结果(M).......[成功]<00>\\n"
							+ "命令失败原因(C)...[Invalid]\\n" + "BKMB列表(C)...\\n" + "[\\n";
				}
				i++;
			}
			memo += "]本次列示MB数:";
			memo += i + "\\n" + "结束标记(C).......[End]<01>\\n";
		}
		msg.setMemo(memo);
		msg.setMemo(Encrypt.encode(msg.getMemo()));
		msg.save();
	}

	/**
	 * MB信息查询
	 */
	private void a6() {
		Unit u = setUnitStatus("1");
		X1msg msg = new X1msg();
		setMsg(msg);
		msg.setMsgType("MB信息查询请求");
		msg.setMemo("协议版本号........[1]<01>\\n" + "消息类型..........[YHXX查询请求]\\n" + "MB类型(M).......["
				+ getPara("numberFormat") + "]<" + BCD.getTargetType(getPara("numberFormat")) + ">\\n"
				+ "MB标识(M).......[" + getPara("number") + "]<" + BCD.str2rBcdstr(getPara("number")) + ">\\n");
		msg.setMemo(Encrypt.encode(msg.getMemo()));
		msg.save();
		msg = new X1msg();
		setMsg(msg);
		msg.setMsgType("MB信息查询响应");
		if ("P-GW".equals(getPara("unitType")) || "S-GW".equals(getPara("unitType"))) {
			msg.setMemo("协议版本号........[1]<01>\\n"
					+ "消息类型..........[YHXX查询响应]\\n"
					+ "MB类型(M).......["
					+ getPara("numberFormat") + "]<" + BCD.getTargetType(getPara("numberFormat")) + ">	\\n"
					+ "MB标识(M).......[" + getPara("number") + "]<" + BCD.str2rBcdstr(getPara("number")) + ">\\n"
					+ "操作结果(M).......[成功]<00>\\n" + "命令失败原因(C)...[Invalid]\\n"
					+ "MSISDN(C).........[8619911111140]<68 91 19 11 11 41 f0>\\n"
					+ "IMSI(C)...........[460301111111140]<64 30 10 11 11 11 41 f0>\\n"
					+ "IMEI(C)...........[865163023472080]<68 15 36 20 43 27 80 f0>\\n"
					+ "用户状态(C).......[(ecm_Connected)]<00>\\n" + "位置更新时间(C)...[" + BCD.getCurrentTime() + "]<"
					+ BCD.getTimerBcd(BCD.getCurrentTime()) + ">\\n" + "附着时间(C).......[Invalid]\\n" + "最新位置(C): \\n"
					+ " gummeid[C]......[Invalid]\\n" + "  lai[C]..........[Invalid]\\n"
					+ "  lastVisitedTAI[C]........[MCC460 MNC30 TAC20007]<64 f0 03 4e 27 >\\n"
					+ "  taiList[C]......[Invalid]\\n"
					+ "  ecgi[C].........[460 30 4294902530]<64 f0 03 ff ff 03 02 >\\n");
		} else if ("MME".equals(getPara("unitType"))) {
			msg.setMemo("协议版本号........[1]<01>\\n" + "消息类型..........[YHXX查询响应]\\n" + "MB类型(M).......["
					+ getPara("numberFormat") + "]<" + BCD.getTargetType(getPara("numberFormat")) + ">\\n"
					+ "MB标识(M).......[" + getPara("number") + "]<" + BCD.str2rBcdstr(getPara("number")) + ">\\n"
					+ "操作结果(M).......[成功]<00>\\n" + "命令失败原因(C)...[Invalid]\\n"
					+ "MSISDN(C).........[8615331548297]<68 51 33 51 84 92 f7>\\n"
					+ "IMSI(C)...........[460110560968538]<64 10 01 65 90 86 35 f8>\\n"
					+ "IMEI(C)...........[862969020031400]<68 92 96 20 00 13 04 f0>\\n"
					+ "用户状态(C).......[ (ecm_Idle)]<01>\\n" + "位置更新时间(C)...[Invalid]\\n" + "附着时间(C).......["
					+ BCD.getCurrentTime() + "]<" + BCD.getTimerBcd(BCD.getCurrentTime()) + ">\\n" + "最新位置(C): \\n"
					+ "  gummeid(C)......[460 11 21248 2]<64 f0 11 53 00 02>\\n" + "  lai[C]..........[Invalid]\\n"
					+ "  lastVisitedTAI[C]........[MCC460 MNC11 TAC44053]<64 f0 11 ac 15 >\\n"
					+ "  taiList[C]........[个数9 类型0 1046 0 4524 5548]<08 01 64 f0 11 ac 15 ac fb>\\n"
					+ "  ecgi[C].........[460 11 181265970]<64 f0 11 0a cd e6 32 >\\n");
		} else if ("HSS".equals(getPara("unitType"))) {
			msg.setMemo("协议版本号........[1]<01>\\n" + "消息类型..........[YHXX查询响应]\\n" + "MB类型(M).......["
					+ getPara("numberFormat") + "]<" + BCD.getTargetType(getPara("numberFormat")) + ">\\n"
					+ "MB标识(M).......[" + getPara("number") + "]<" + BCD.str2rBcdstr(getPara("number")) + ">\\n"
					+ "操作结果(M).......[成功]<00>\\n" + "命令失败原因(C)...[Invalid]\\n"
					+ "MSISDN(C).........[8613900000215]<68 31 09 00 00 12 f5>\\n"
					+ "IMSI(C)...........[454060000000215]<54 04 06 00 00 00 12 f5>\\n"
					+ "IMEI(C)...........[354427068745260]<53 44 72 60 78 54 62 f0>\\n" + "用户状态(C).......[Invalid]\\n"
					+ "位置更新时间(C)...[Invalid]\\n" + "附着时间(C).......[Invalid]\\n" + "最新位置(C): \\n"
					+ "  gummeid(C)......[3776 37 28269 109]<73 67 73 6e 6d 6d>\\n" + "  lai[C]..........[Invalid]\\n"
					+ "  lastVisitedTAI[C].........[Invalid]\\n" + "  taiList[C]......[Invalid]\\n"
					+ "  ecgi[C]........[Invalid]\\n");
		}
		msg.setMemo(Encrypt.encode(msg.getMemo()));
		msg.save();
	}

	/**
	 * 网元时间查询
	 */
	private void a7() {
		Unit u = setUnitStatus("1");
		X1msg msg = new X1msg();
		setMsg(msg);
		msg.setMsgType("网元时间查询请求");
		msg.setMemo("协议版本号........[1]<01>\\n"
				+ "消息类型..........[网元时间查询请求]\\n"
				+ "EPC网元标识(M)....[" + u.getUnitId()
				+ "]<" + BCD.str2HexStr(u.getUnitId()) + ">\\n");
		msg.setMemo(Encrypt.encode(msg.getMemo()));
		msg.save();
		msg = new X1msg();
		setMsg(msg);
		msg.setMsgType("网元时间查询响应");
		msg.setMemo("协议版本号........[1]<01>\\n" + "消息类型..........[网元时间查询响应]\\n" + "EPC网元标识(M)....[" + u.getUnitId()
				+ "]<" + BCD.str2HexStr(u.getUnitId()) + ">\\n" + "操作结果(M).......[成功]<00>\\n"
				+ "命令失败原因(C)...[Invalid]\\n");
		msg.setMemo(Encrypt.encode(msg.getMemo()));
		msg.save();
	}

	/**
	 * BKMB参数修改
	 */
	private void a8() {
		setUnitStatus("1");
		X1msg msg = new X1msg();
		setMsg(msg);
		msg.setMsgType("BKMB参数修改请求");
		msg.setMemo("协议版本号........[2]<02>\n" +
				"消息类型..........[BKMB参数修改请求]\n" +
				"目标标识(M).......["+getPara("number")+"]<"+BCD.str2rBcdstr(getPara("number"))+">\n" +
				"X2地址(C).........\n" +
				"  Port:["+getPara("x2Port")+"]<13 88>\n" +
				"  IP:["+getPara("x2Ip")+"]<0a b8 4d 49>\n" +
				"\n");
		msg.setMemo(Encrypt.encode(msg.getMemo()));
		msg.save();
		msg = new X1msg();
		setMsg(msg);
		msg.setMsgType("BKMB参数修改响应");
		msg.setMemo("协议版本号........[2]<02>\n" +
				"消息类型..........[BKMB参数修改响应]\n" +
				"目标标识(M).......["+getPara("number")+"]<"+BCD.str2rBcdstr(getPara("number"))+">\n" +
				"操作结果(M).......[失败]<01>\n" +
				"失败原因(C).......[被控目标不存在 (monitorNumberNotExist)]<01>\n" +
				"\n");
		msg.setMemo(Encrypt.encode(msg.getMemo()));
		msg.save();
	}

	/**
	 * BK目标参数查询
	 */
	private void a9() {
        setUnitStatus("1");
        X1msg msg = new X1msg();
        setMsg(msg);
        msg.setMsgType("BK目标参数查询请求");
        msg.setMemo("[ 12:02:20 ] [ 消息内容 (IMS_PLX_X1 ==> NE) ][ NEID = CCAS11 ]:\n" +
                "协议版本号........[2]<02>\n" +
                "消息类型..........[被控目标参数查询请求]\n" +
                "目标标识(M).......[sip:+8618787484294@bjcmnas1aeb.cmn.chinamobile.com]<73 69 70 3a 2b 38 36 31 38 37 38 37 34 38 34 32 39 34 40 62 6a 63 6d 6e 61 73 31 61 65 62 2e 63 6d 6e 2e 63 68 69 6e 61 6d 6f 62 69 6c 65 2e 63 6f 6d>\n" +
                "\n");
        msg.setMemo(Encrypt.encode(msg.getMemo()));
        msg.save();
        msg = new X1msg();
        setMsg(msg);
        msg.setMsgType("BK目标参数查询响应");
        msg.setMemo("协议版本号........[2]<02>\n" +
                "消息类型..........[BKMB参数查询响应]\n" +
                "目标标识(M).......[sip:+8618787484294@bjcmnas1aeb.cmn.chinamobile.com]<73 69 70 3a 2b 38 36 31 38 37 38 37 34 38 34 32 39 34 40 62 6a 63 6d 6e 61 73 31 61 65 62 2e 63 6d 6e 2e 63 68 69 6e 61 6d 6f 62 69 6c 65 2e 63 6f 6d>\n" +
                "操作结果(M).......[成功]<00>\n" +
                "失败原因(C).......[Invalid]\n" +
                "X2地址(C).........\n" +
                "  Port:[8112]<1f b0>\n" +
                "  IP:[10.189.185.25]<0a bd b9 19>\n");
        msg.setMemo(Encrypt.encode(msg.getMemo()));
        msg.save();
    }

	/**
	 * BK目标位置定位查询 没找到
	 */
	private void a10() {
        setUnitStatus("1");
        X1msg msg = new X1msg();
        setMsg(msg);
        msg.setMsgType("BK目标位置定位查询请求");
        msg.setMemo("协议版本号........[2]<02>\n" +
                "消息类型..........[BKMB参数修改请求]\n" +
                "目标标识(M).......["+getPara("number")+"]<"+BCD.str2rBcdstr(getPara("number"))+">\n" +
                "X2地址(C).........\n" +
                "  Port:["+getPara("x2Port")+"]<13 88>\n" +
                "  IP:["+getPara("x2Ip")+"]<0a b8 4d 49>\n");
        msg.setMemo(Encrypt.encode(msg.getMemo()));
        msg.save();
        msg = new X1msg();
        setMsg(msg);
        msg.setMsgType("BK目标位置定位查询响应");
        msg.setMemo("协议版本号........[2]<02>\n" +
                "消息类型..........[BKMB参数修改响应]\n" +
                "目标标识(M).......["+getPara("number")+"]<"+BCD.str2rBcdstr(getPara("number"))+">\n" +
                "操作结果(M).......[失败]<01>\n" +
                "失败原因(C).......[被控目标不存在 (monitorNumberNotExist)]<01>\n");
        msg.setMemo(Encrypt.encode(msg.getMemo()));
        msg.save();
    }

	/**
	 * 目标内/外网IP地址关联查询 没找到
	 */
	private void a11() {
        setUnitStatus("1");
        X1msg msg = new X1msg();
        setMsg(msg);
        msg.setMsgType("目标内/外网IP地址关联查询请求");
        msg.setMemo("协议版本号........[2]<02>\n" +
                "消息类型..........[目标内/外网IP地址关联查询请求]\n" +
                "目标标识(M).......["+getPara("number")+"]<"+BCD.str2rBcdstr(getPara("number"))+">\n" +
                "X2地址(C).........\n" +
                "  Port:["+getPara("x2Port")+"]<13 88>\n" +
                "  IP:["+getPara("x2Ip")+"]<0a b8 4d 49>\n");
        msg.setMemo(Encrypt.encode(msg.getMemo()));
        msg.save();
        msg = new X1msg();
        setMsg(msg);
        msg.setMsgType("目标内/外网IP地址关联查询响应");
        msg.setMemo("协议版本号........[2]<02>\n" +
                "消息类型..........[目标内/外网IP地址关联查询响应]\n" +
                "目标标识(M).......["+getPara("number")+"]<"+BCD.str2rBcdstr(getPara("number"))+">\n" +
                "操作结果(M).......[失败]<01>\n" +
                "失败原因(C).......[被控目标不存在 (monitorNumberNotExist)]<01>\n");
        msg.setMemo(Encrypt.encode(msg.getMemo()));
        msg.save();
    }

	/**
	 * IMPU关联标识查询
	 */
	private void a13() {
        setUnitStatus("1");
        X1msg msg = new X1msg();
        setMsg(msg);
        msg.setMsgType("IMPU关联标识查询请求");
        msg.setMemo("[ 18:06:42 ] [ 消息内容 (IMS_PLX_X1 ==> NE) ][ NEID = HSS1 ]:\n" +
                "协议版本号........[2]<02>\n" +
                "消息类型..........[IMPU关联标识查询请求]\n" +
                "目标标识(M).......[sip:+8645185913004@hl.ims.chinaunicom.cn]<73 69 70 3a 2b 38 36 34 35 31 38 35 39 31 33 30 30 34 40 68 6c 2e 69 6d 73 2e 63 68 69 6e 61 75 6e 69 63 6f 6d 2e 63 6e>\n" +
                "\n");
        msg.setMemo(Encrypt.encode(msg.getMemo()));
        msg.save();
        msg = new X1msg();
        setMsg(msg);
        msg.setMsgType("IMPU关联标识查询响应");
        msg.setMemo("协议版本号........[2]<02>\n" +
                "消息类型..........[IMPU关联标识查询响应]\n" +
                "目标标识(M).......[sip:+8645185913004@hl.ims.chinaunicom.cn]<73 69 70 3a 2b 38 36 34 35 31 38 35 39 31 33 30 30 34 40 68 6c 2e 69 6d 73 2e 63 68 69 6e 61 75 6e 69 63 6f 6d 2e 63 6e>\n" +
                "操作结果(M).......[成功]<00>\n" +
                "失败原因(C).......[Invalid]\n" +
                "IMPI标识(C).......[+8645185913004@hl.ims.chinaunicom.cn]<2b 38 36 34 35 31 38 35 39 31 33 30 30 34 40 68 6c 2e 69 6d 73 2e 63 68 69 6e 61 75 6e 69 63 6f 6d 2e 63 6e>\n" +
                "\n");
        msg.setMemo(Encrypt.encode(msg.getMemo()));
        msg.save();
    }

	/**
	 * IMPI关联标识查询
	 */
	private void a12() {
        setUnitStatus("1");
        X1msg msg = new X1msg();
        setMsg(msg);
		msg.setMsgType("IMPI关联标识查询请求");
        msg.setMemo("[ 18:12:30 ] [ 消息内容 (IMS_PLX_X1 ==> NE) ][ NEID = HSS1 ]:\n" +
                "协议版本号........[2]<02>\n" +
                "消息类型..........[IMPI关联标识查询请求]\n" +
                "目标标识(M).......[+8645185913005@hl.ims.chinaunicom.cn]<2b 38 36 34 35 31 38 35 39 31 33 30 30 35 40 68 6c 2e 69 6d 73 2e 63 68 69 6e 61 75 6e 69 63 6f 6d 2e 63 6e>\n" +
                "\n");
        msg.setMemo(Encrypt.encode(msg.getMemo()));
        msg.save();
        msg = new X1msg();
        setMsg(msg);
        msg.setMsgType("IMPI关联标识查询响应");
        msg.setMemo("协议版本号........[2]<02>\n" +
                "消息类型..........[IMPI关联标识查询响应]\n" +
                "目标标识(M).......[+8645185913005@hl.ims.chinaunicom.cn]<2b 38 36 34 35 31 38 35 39 31 33 30 30 35 40 68 6c 2e 69 6d 73 2e 63 68 69 6e 61 75 6e 69 63 6f 6d 2e 63 6e>\n" +
                "操作结果(M).......[成功]<00>\n" +
                "失败原因(C).......[Invalid]\n" +
                "IMPU标识列表(C)...\n" +
                "[\n" +
                "  Num=[sip:+8645185913005@hl.ims.chinaunicom.cn]<73 69 70 3a 2b 38 36 34 35 31 38 35 39 31 33 30 30 35 40 68 6c 2e 69 6d 73 2e 63 68 69 6e 61 75 6e 69 63 6f 6d 2e 63 6e>\n" +
                "  Num=[tel:+8645185913005]<74 65 6c 3a 2b 38 36 34 35 31 38 35 39 31 33 30 30 35>\n" +
                "]本次列示目标数:2\n");
        msg.setMemo(Encrypt.encode(msg.getMemo()));
        msg.save();
    }


	/**
	 * 新增网元
	 */
	public void add_save() {
		Unit unit = new Unit();
		setUnitParam(unit, UUID.randomUUID().toString());
		// unit.setCreator(getSessionAttr("userName"));
		unit.setCreateTime(new Date());
		unit.save();
		simulationunit();
	}

	/**
	 * 修改网元信息。
	 */
	public void update_save() {
		Unit unit = new Unit();
		setUnitParam(unit, getPara("id"));
		unit.setModifyBy(""+getLoginAccountId());
		unit.setModifyTime(new Date());
		unit.update();
		simulationunit();
	}

	private void setUnitParam(Unit unit, String id) {
		unit.setId(id);
		unit.setUnitType(getPara("unitType"));
		unit.setUnitId(getPara("unitId"));
		unit.setX1Ip(getPara("x1Ip"));
		unit.setX1Port(getPara("x1Port"));
		unit.setUnitDesc(getPara("unitDesc"));
		unit.setLicId(getPara("licId"));
		unit.setLicIp(getPara("licIp"));
		unit.setX2Port(getPara("x2Port"));
		unit.setX3Port(getPara("x3Port"));
		unit.setExtX3Ip(getPara("extX3Ip"));
		unit.setExtX3Port(getPara("extX3Port"));
		unit.setX1Kt(getPara("x1Kt"));
		unit.setX1Pwd(getPara("x1Pwd"));
		unit.setX1Sqn(getPara("x1Sqn"));
		unit.setX2Kt(getPara("x2Kt"));
		unit.setX2Pwd(getPara("x2Pwd"));
		unit.setX2Sqn(getPara("x2Sqn"));
		unit.setUnitStatus("0");
		unit.setX1Status("0");
		unit.setX2Status("0");
		unit.setX3Status("0");
	}

	/**
	 * 改变网元状态，分别为“启用”和“停用”。 废弃，改为iPhone开关样式了。
	 */
	// public void changeUnitStatus(){
	// Unit u = Unit.dao.findById(getPara("id"));
	// u.setUnitStatus(getPara("status"));
	// /*
	// * 如果停用网元，同时将X1、X2、X3接口设置为停用。
	// */
	// if("0".equals(u.getUnitStatus())){
	// u.setX1Status("0");
	// u.setX2Status("0");
	// u.setX3Status("0");
	// }
	// u.update();
	// renderJson(Ret.ok());
	// }

	/**
	 * “停用”网元。
	 */
	public void shutdownUnit() {
		Unit u = Unit.dao.findById(getPara("id"));
		u.setUnitStatus("0");
		u.setX1Status("0");
		u.setX2Status("0");
		u.setX3Status("0");
		u.update();
		renderJson(Ret.ok());
	}

	/**
	 * “启用”网元。
	 */
	public void startupUnit() {
		Unit u = Unit.dao.findById(getPara("id"));
		u.setUnitStatus("1");
		u.update();
		renderJson(Ret.ok());
	}

	/**
	 * 根据ID删除网元。
	 */
	public void unit_delete() {
		Db.update("delete from tunit where id='" + getPara("id") + "'");
		simulationunit();
	}

	/**
	 * true unit 新增网元
	 */
	public void true_add_save() {
		TrueUnit unit = new TrueUnit();
		unit.setId(UUID.randomUUID().toString());
		setTrueUnitParam(unit);
		unit.setCreator(""+getLoginAccountId());
		unit.setCreateTime(new Date());
		unit.save();
		unitinfo();
	}

	private void setTrueUnitParam(TrueUnit unit) {
		unit.setUnitType(getPara("unitType"));
		unit.setUnitId(getPara("unitId"));
		unit.setX1Ip(getPara("x1Ip"));
		unit.setX1Port(getPara("x1Port"));
		unit.setUnitDesc(getPara("unitDesc"));
		unit.setLicId(getPara("licId"));
		unit.setLicIp(getPara("licIp"));
		unit.setX2Port(getPara("x2Port"));
		unit.setX3Port(getPara("x3Port"));
		unit.setExtX3Ip(getPara("extX3Ip"));
		unit.setExtX3Port(getPara("extX3Port"));
		unit.setX1Kt(getPara("x1Kt"));
		unit.setX1Pwd(getPara("x1Pwd"));
		unit.setX1Sqn(getPara("x1Sqn"));
		unit.setX2Kt(getPara("x2Kt"));
		unit.setX2Pwd(getPara("x2Pwd"));
		unit.setX2Sqn(getPara("x2Sqn"));
		unit.setUnitStatus("0");
		unit.setX1Status("0");
		unit.setX2Status("0");
		unit.setX3Status("0");
	}

	/**
	 * true unit 修改网元信息。
	 */
	public void true_update_save() {
		TrueUnit unit = new TrueUnit();
		unit.setId(getPara("id"));
		setTrueUnitParam(unit);
		// unit.setModifyBy(getSessionAttr("userName"));
		unit.setModifyTime(new Date());
		unit.update();
		unitinfo();
	}

	/**
	 * true unit 根据ID删除网元。
	 */
	public void true_unit_delete() {
		Db.update("delete from ttrue_unit where id='" + getPara("id") + "'");
		unitinfo();
	}

	public void unitmsg_list() {
		renderJsp("/simulation/unitmsg_list.jsp");
	}
}
