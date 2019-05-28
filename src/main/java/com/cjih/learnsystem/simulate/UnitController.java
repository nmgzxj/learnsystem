package com.cjih.learnsystem.simulate;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.jfinal.club.common.model.*;
import com.jfinal.club.common.model.Number;
import com.jfinal.ext.kit.DateKit;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import org.apache.log4j.Logger;

import com.cjih.learnsystem.common.util.BCD;
import com.cjih.learnsystem.common.util.Encrypt;
import com.jfinal.club.common.controller.BaseController;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;

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
		renderText(getUnitTypeName());
	}

	private String getUnitTypeName(){
        return Db.queryStr("select unit_type from tunit where id = '"+getPara("unitId")+"' ");
    }

	public void simulation2() {
		String unit = getPara("unit");
		String msgType = getPara("msgType");
		String x2sql = "select * from tx2msg where 1=1 ";
		x2sql = getSearchSql(x2sql);
		setAttr("x2MsgList", X2msg.dao.find(x2sql));
        setAttr("unitTypeList", ResourceInfo.dao.find("select * from tresource_info where type_id='PUB001'"));
		render("simulation2.html");
	}

	public void simulation3() {
		String unit = getPara("unit");
		String msgType = getPara("msgType");

		String x3sql = "select * from tx3msg where 1=1 ";
		x3sql = getSearchSql(x3sql);
		setAttr("x3MsgList", X3msg.dao.find(x3sql));
        setAttr("unitTypeList", ResourceInfo.dao.find("select * from tresource_info where type_id='PUB001'"));
		render("simulation3.html");
	}

	public void simulation1() {
		String select = "select *";
		String sqlExceptSelect = " from tX1msg where 1=1";
		sqlExceptSelect = getSearchSql(sqlExceptSelect);
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
				sqlExceptSelect += " and msg_type like '%" + msgType + "%' ";
			}
		}
		sqlExceptSelect += " order by id";
		return sqlExceptSelect;
	}

	/**
	 * X1接口发送消息
	 */
//	@Before(UnitValidator.class)
	public void X1MsgAdd() {
		String operType = getPara("operType");
		String numberFormat = getPara("numberFormat");
		String number = getPara("number");

		LogKit.debug("operType = " + operType);
		if (operType == null || operType.isEmpty()) {
			setAttr("msg", "操作类型不正确，请联系管理员。");
			render("/_view/msg.html");
		}
		if (!"createConnection".equals(operType) && "0".equals(Unit.dao.findById(getPara("unit")).getX1Status())) {

			setAttr("msg", "该网元X1接口未连接，请先建立连接。");
			render("/_view/msg.html");
		}
		else {
            LogKit.debug("x1status=" + Unit.dao.findById(getPara("unit")).getX1Status());
            LogKit.debug("operType=" + operType);
			if (operType.equals("createConnection")) {
				createConnection();
			} else if (operType.equals("a2")) {
				a2();
			} else if (operType.equals("a3")) {
				a3();
			} else if (operType.equals("a4")) {
				a4(numberFormat, number);
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
			else if (operType.equals("a14")) {
				a14();
			}
            else if (operType.equals("a15")) {
                a15();
            }
//			simulation1();
			redirect("/admin/unit/simulation1");
//			renderJson(Ret.ok());
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
		msg.setSn2(MsgTempletController.x1sn2++);
    }

    public String getNetType(String unitTypeStr){
	    Integer unitType = Integer.valueOf(unitTypeStr);
	    String netType = "4G";
        switch (unitType) {
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
                netType = "4G";
                break;
            case 4:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
                netType = "IMS";
                break;
            case 7:
            case 29:
            case 30:
            case 31:
            case 32:
                netType = "WWW";
                break;
            default:
                netType = "4G";
        }
        return netType;
    }
	/*
	 * 连接建立消息
	 *
	 */
	private void createConnection() {
		Unit u = Unit.dao.findById(getPara("unit"));
		X1msg msg = new X1msg();
		setMsg(msg);

		msg.setMsgType("连接建立请求");
		String rtn = "";
		if("4G".equalsIgnoreCase(getNetType(u.getUnitType()))||"IMS".equalsIgnoreCase(getNetType(u.getUnitType()))) {
			rtn = "协议版本号........[1]<00 00 00 01 >\\n"
					+ "消息类型..........[连接建立请求]\\n"
					+ "LIC标 识(M).......[" + u.getLicId() + "]<" + BCD.str2HexStr(u.getLicId()) + ">\\n"
					+ "网元标识(M).......[" + u.getUnitId() + "]<" + BCD.str2HexStr(u.getUnitId()) + " >\\n"
					+ "SQN组号(M)........[" + u.getX1Sqn() + "]<00 00 00 02 >\\n"
					+ "RAND(M)...........<ca f6 96 be dc b1 d2 >\\n"
					+ "认证字段(M).......<d5 64 58 30 6b 24 bb 24 1c 9c 34 e0 5a 62 86 35 >\\n";
		}
		else if("WWW".equalsIgnoreCase(getNetType(u.getUnitType()))){
			rtn = "["+ DateKit.toStr(new Date())+"] "+u.getUnitId()+"--- X1_CONNECT_REQ --- 2\\n" +
					"LIC标识:"+u.getLicId()+"\\n" +
					"INE标识:"+u.getUnitId()+"\\n" +
					"SQN组数:"+u.getX1Sqn()+"\\n" +
					"随机数:1012226\\n" +
					"认证字段:S!\u0014\u0001?箕V^+讐'蝂\\n";

		}
		msg.setMemo(Encrypt.encode(rtn));
		msg.save();

		msg = new X1msg();
        setMsg(msg);
		msg.setMsgType("连接建立响应");
		if("4G".equalsIgnoreCase(getNetType(u.getUnitType()))||"IMS".equalsIgnoreCase(getNetType(u.getUnitType()))) {
			rtn = "协议版本号........[1]<00 00 00 01 >\\n"
					+ "消息类型..........[连接建立响应]\\n"
					+ "网元标识(M).......[" + u.getUnitId() + "]<" + BCD.str2HexStr(u.getUnitId()) + ">\\n"
					+ "LIC标 识(M).......[" + u.getLicId() + "]<" + BCD.str2HexStr(u.getLicId()) + ">\\n";
			try {
				TrueUnit unitInfo = TrueUnit.dao.findFirst("select * from ttrue_unit where unit_id = '" + u.getUnitId()
						+ "' and lic_id = '" + u.getLicId() + "'");
				if (null == unitInfo) {// 如果网元id和licid没有对应关系，则判定为未登记。
					rtn += "执行结果(M).......[Failure]<00 00 00 01 >\\n"
							+ "失败原因(C).......[Ne Not Registered]<00 00 00 00 >\\n"
							+ "应答字段(C).......[Invalid][Ne Not Registered]\\n";
					msg.setOperationResult("[Failure]");
				}
				else if(!u.getUnitId().equals(unitInfo.getUnitId())) {//疑问：网元ID非法？
					rtn += "执行结果(M).......[Failure]<00 00 00 01 >\\n" +
							"失败原因(C).......[Ne Id Invalid]<00 00 00 02 >\\n" +
							"应答字段(C).......[Invalid]\\n";
				}
				else if (!u.getX1Sqn().equals(unitInfo.getX1Sqn()) | !u.getX1Kt().equals(unitInfo.getX1Kt())
						| !u.getX1Pwd().equals(unitInfo.getX1Pwd())) {
					rtn += "执行结果(M).......[Failure]<00 00 00 01 >\\n"
							+ "失败原因(C).......[Authentication Fail]<00 00 00 01 >\\n"
							+ "应答字段(C).......[Invalid]\\n";
					msg.setOperationResult("[Failure][Authentication Fail]");
				}
				else if ("1".equals(u.getX1Status())) {
					rtn += "执行结果(M).......[Failure]<00 00 00 01 >\\n"
							+ "失败原因(C).......[Connect is Exist]<00 00 00 03 >\\n"
							+ "应答字段(C).......[Invalid]\\n";
					msg.setOperationResult("[Failure][Connect is Exist]");
				} else {
					rtn += "执行结果(M).......[Success]<00 00 00 00 >\\n"
							+ "失败原因(C).......[Connect is Exist]<00 00 00 03 >\\n"
							+ "应答字段(C).......[Invalid]\\n";
					msg.setOperationResult("[Success]");
				}
			} catch (Exception e) {
				e.printStackTrace();
				rtn += "执行结果(M).......[Failure]<00 00 00 01 >\\n"
						+ "失败原因(C).......[Connect is Exist]<00 00 00 FF >\\n"
						+ "应答字段(C).......[Other]\\n";
				msg.setOperationResult("[Failure][Connect is Exist]");

			}
		}
		else if("WWW".equalsIgnoreCase(getNetType(u.getUnitType()))){
			rtn = "["+ DateKit.toStr(new Date())+"] "+u.getUnitId()+" --- X1_CONNECT_ACK --- 3\\n" +
					"INE标识:"+u.getUnitId()+"\\n" +
					"LIC标识:"+u.getLicId()+"\\n" +
					"成功标识:1\\n" +
					"失败原因:2\\n" +
					"应答字段:\\n" +
					"ps：\\n" +
					"成功标识：1-连接失败\\n" +
					"失败原因：2-被控目标已设定\\n";
		}
		msg.setMemo(Encrypt.encode(rtn));
		msg.save();
		u.setX1Status("1");// 设置当前网元X1端口状态为已连接。
		u.update();
//		simulation1();
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
		if("4G".equalsIgnoreCase(getNetType(u.getUnitType()))||"IMS".equalsIgnoreCase(getNetType(u.getUnitType()))) {
			msg.setMemo("协议版本号........[1]<00 00 00 01 >\\n"
					+ "消息类型..........[连接释放通知]\\n"
					+ "LIC标 识(M).......[" + u.getLicId() + "]<" + BCD.str2HexStr(u.getLicId()) + ">\\n"
					+ "网元标识(M).......[" + u.getUnitId() + "]<" + BCD.str2HexStr(u.getUnitId()) + " >\\n"
					+ "连接释放原因(M)...[Normal Release]<00 00 00 00 >\\n");
		}
		else if("WWW".equalsIgnoreCase(getNetType(u.getUnitType()))){
			msg.setMemo("["+DateKit.toStr(new Date())+"] "+u.getUnitId()+" --- X1_CONNECTRELEASE_REQ --- 10\\n" +
					"LIC标识:"+u.getLicId()+"\\n" +
					"INE标识:"+u.getUnitId()+"\\n" +
					"释放原因:1\\n");
		}
		msg.setMemo(Encrypt.encode(msg.getMemo()));
		msg.save();
//		simulation1();
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
		String rtn = "";
		if("4G".equalsIgnoreCase(getNetType(u.getUnitType()))||"IMS".equalsIgnoreCase(getNetType(u.getUnitType()))) {
			rtn = 	"协议版本号........[1]<01>\\n"
					+ "消息类型..........[BKMB设定请求]\\n"
					+ "MB类型(M).......[" + getPara("numberFormat") + "]<" + BCD.getTargetType(getPara("numberFormat")) + ">\\n"
					+ "MB标识(M).......[" + getPara("number") + "]<" + BCD.str2rBcdstr(getPara("number")) + ">\\n";
		}
		else if("WWW".equalsIgnoreCase(getNetType(u.getUnitType()))) {
			rtn = "["+DateKit.toStr(new Date())+"] "+u.getUnitId()+" --- X1_SETTARGET_REQ --- 2\\n" +
					"目标类型:"+getPara("numberFormat")+"\\n" +
					"目标标识:"+getPara("number")+"\\n" +
					"监控模式:"+getPara("monType")+"\\n" +
					"访问类型:"+getPara("addType")+"\\n" +
					"访问标识:"+getPara("add")+"\\n" +
					"传输层协议:"+getPara("tp")+"\\n" +
					"应用业务类型:"+getPara("appBizType")+"\\n" +
					"LNS服务器IP地址:"+getPara("lnsAdd")+"\\n";
		}
		msg.setMemo(Encrypt.encode(rtn));
		msg.save();
		msg = new X1msg();
		setMsg(msg);
		msg.setMsgType("BKMB设定响应");
		Number num = new Number();
		num.setNumberFormat(getPara("numberFormat"));
		num.setCreateTime(new Date());
		num.setUnit(u.getId());
		num.setMsisdn("1111111");
		num.setImei("11111111");
		num.setImsi("11111111");
		num.setIp("111.111.111.111");
		num.setIpPort("1111");
		num.setIpTime("1111");
		num.setIpPort("111.111.111.111:1111");
		num.setIpAddressField("111.111.111.111");
		num.setNai("1111");
		num.setPhoneNumber("1111111");
		num.setSip("111@111.com");
		num.setTel("111111");
		num.setX2ip(getPara("x2ip"));
		num.setX2port(getPara("x2port"));
		num.setX3ip(getPara("x3ip"));
		num.setX3port(getPara("x3port"));
		num.setMonType(getPara("monType"));
		num.setAddType(getPara("addType"));
		num.setAdd(getPara("add"));
		num.setTp(getPara("tp"));
		num.setAppBizType(getPara("appBizType"));
		num.setLnsAdd(getPara("lnsAdd"));

		if("4G".equalsIgnoreCase(getNetType(u.getUnitType()))||"IMS".equalsIgnoreCase(getNetType(u.getUnitType()))) {
			rtn = "协议版本号........[1]<01>\\n"
					+ "消息类型..........[BKMB设定响应]\\n"
					+ "MB类型(M).......[" + getPara("numberFormat") + "]<" + BCD.getTargetType(getPara("numberFormat")) + ">\\n"
					+ "MB标识(M).......[" + getPara("number") + "]<" + BCD.str2rBcdstr(getPara("number")) + ">\\n";
			/*
			 * 将号码存入被测网元 失败处理： 1.如果已BK，返回BKMB已存在 2.对于HSS，如果MB未开户，则HSS返回失败“用户不存在”
			 * 3.如果采用IMEI，号码长度不是15位，返回参数错误。 4.超过网元上限，返回“资源不足”
			 */
			List<Number> existNums = Number.dao.find(
					"select * from tnumber where unit='" + u.getId() + "' and number_format='" + getPara("numberFormat")
							+ "' and " + getPara("numberFormat") + "='" + getPara("number") + "'");
			if (existNums.size() > 0) {
				rtn += "操作结果(M).......[BKMB已存在]<02>\\n"
						+ "命令失败原因(C)...[MB Be Set]\\n";
				msg.setOperationResult("[BKMB已存在]");
			} else if (Number.dao.find("select * from tnumber where unit='" + u.getId() + "'").size() > PropKit
					.getInt("unit_number_size")) {
				rtn += "操作结果(M).......[资源限制]<02>\\n"
						+ "命令失败原因(C)...[resource limited]\\n";
				msg.setOperationResult("[资源不足]");
			} else {
				if ("msisdn".equals(num.getNumberFormat())) {
					num.setMsisdn(getPara("number"));
					rtn = getHssMemo(msg, rtn, num);
				} else if ("imei".equals(num.getNumberFormat())) {
					num.setImei(getPara("number"));
					/*
					 * IMEI号低于15的，报参数错误。
					 */
					if (num.getImei().length() < 15) {
						rtn += "操作结果(M).......[失败]<00>\\n"
								+ "命令失败原因(C)...[parameterError]<06>\\n";
					} else {
						rtn = getHssMemo(msg, rtn, num);
					}
				} else if ("imsi".equals(num.getNumberFormat())) {
					num.setImsi(getPara("number"));
					rtn = getHssMemo(msg, rtn, num);
				} else if ("ip".equals(num.getNumberFormat())) {
					num.setIp(getPara("number"));
					rtn = getHssMemo(msg, rtn, num);
				} else if ("ip_time".equals(num.getNumberFormat())) {
					num.setIpTime(getPara("number"));
					rtn = getHssMemo(msg, rtn, num);
				} else if ("ip_port".equals(num.getNumberFormat())) {
					num.setIpPort(getPara("number"));
					rtn = getHssMemo(msg, rtn, num);
				} else if ("ip_address_field".equals(num.getNumberFormat())) {
					num.setIpAddressField(getPara("number"));
					rtn = getHssMemo(msg, rtn, num);
				} else if ("nai".equals(num.getNumberFormat())) {
					num.setNai(getPara("number"));
					rtn = getHssMemo(msg, rtn, num);
				} else if ("phone_number".equals(num.getNumberFormat())) {
					num.setPhoneNumber(getPara("number"));
					rtn = getHssMemo(msg, rtn, num);
				} else if ("sip".equals(num.getNumberFormat())) {
					num.setSip(getPara("number"));
					rtn = getHssMemo(msg, rtn, num);
				} else if ("tel".equals(num.getNumberFormat())) {
					num.setTel(getPara("number"));
					rtn = getHssMemo(msg, rtn, num);
				}

			}
		}
		else if("WWW".equalsIgnoreCase(getNetType(u.getUnitType()))) {
			rtn = "["+DateKit.toStr(new Date())+"] "+u.getUnitId()+" --- X1_SETTARGET_ACK --- 7\\n" +
					"目标类型:"+getPara("numberFormat")+"\\n" +
					"目标标识:"+getPara("number")+"\\n" +
					"访问类型:"+getPara("addType")+"\\n" +
					"访问标识:"+getPara("add")+"\\n" +
					"传输层协议:"+getPara("tp")+"\\n" +
					"应用协议类型:"+getPara("appType")+"\\n" +
					"---接入获得的IP地址列表:\\n" +
					"结束标识:\\n";//
			/*
			 * 将号码存入被测网元 失败处理： 1.如果已BK，返回BKMB已存在 2.对于HSS，如果MB未开户，则HSS返回失败“用户不存在”
			 * 3.如果采用IMEI，号码长度不是15位，返回参数错误。 4.超过网元上限，返回“资源不足”
			 */
			List<Number> existNums = Number.dao.find(
					"select * from tnumber where unit='" + u.getId() + "' and number_format='" + getPara("numberFormat")
							+ "' and " + getPara("numberFormat") + "='" + getPara("number") + "'");
			if (existNums.size() > 0) {
				rtn += "操作结果:BKMB已存在\\n"
						+ "命令失败原因:MB Be Set\\n";
				msg.setOperationResult("[BKMB已存在]");
			} else if (Number.dao.find("select * from tnumber where unit='" + u.getId() + "'").size() > PropKit
					.getInt("unit_number_size")) {
				rtn += "操作结果:资源限制\\n"
						+ "命令失败原因:resource limited]\\n";
				msg.setOperationResult("[资源不足]");
			} else {

				if ("msisdn".equals(num.getNumberFormat())) {
					num.setMsisdn(getPara("number"));
					rtn = getHssMemo(msg, rtn, num);
				} else if ("imei".equals(num.getNumberFormat())) {
					num.setImei(getPara("number"));
					/*
					 * IMEI号低于15的，报参数错误。
					 */
					if (num.getImei().length() < 15) {
						rtn += "操作结果:失败\\n"
								+ "命令失败原因:parameterError\\n";
					} else {
						rtn = getHssMemo(msg, rtn, num);
					}
				} else if ("imsi".equals(num.getNumberFormat())) {
					num.setImsi(getPara("number"));
					rtn = getHssMemo(msg, rtn, num);
				} else if ("ip".equals(num.getNumberFormat())) {
					num.setIp(getPara("number"));
					rtn = getHssMemo(msg, rtn, num);
				} else if ("ip_time".equals(num.getNumberFormat())) {
					num.setIpTime(getPara("number"));
					rtn = getHssMemo(msg, rtn, num);
				} else if ("ip_port".equals(num.getNumberFormat())) {
					num.setIpPort(getPara("number"));
					rtn = getHssMemo(msg, rtn, num);
				} else if ("ip_address_field".equals(num.getNumberFormat())) {
					num.setIpAddressField(getPara("number"));
					rtn = getHssMemo(msg, rtn, num);
				} else if ("nai".equals(num.getNumberFormat())) {
					num.setNai(getPara("number"));
					rtn = getHssMemo(msg, rtn, num);
				} else if ("phone_number".equals(num.getNumberFormat())) {
					num.setPhoneNumber(getPara("number"));
					rtn = getHssMemo(msg, rtn, num);
				} else if ("sip".equals(num.getNumberFormat())) {
					num.setSip(getPara("number"));
					rtn = getHssMemo(msg, rtn, num);
				} else if ("tel".equals(num.getNumberFormat())) {
					num.setTel(getPara("number"));
					rtn = getHssMemo(msg, rtn, num);
				}

			}

		}
		msg.setMemo(rtn);
		msg.setMemo(Encrypt.encode(msg.getMemo()));
		msg.save();
//		simulation1();
	}


	private String getHssMemo(X1msg msg, String memo, Number num) {
		Unit u = setUnitStatus("1");
		System.out.println("getNetType(u.getUnitType())) = " + getNetType(u.getUnitType()));

		String sql = "select * from tnumber_user where "+getPara("numberFormat")+" = '"+getPara("number")+"'";
		List<NumberUser> numberUserList = NumberUser.dao.find(sql);

		//if (u.getUnitTypeView().equals("HSS") && !getPara("number").equals(PropKit.get("HSS_number"))) {
		if(numberUserList.size()==0 && (u.getUnitTypeView().equals("IMSHSS")||u.getUnitTypeView().equals("LTEHSS"))){
			memo += "操作结果(M).......[号码不在HSS库中]<00>\\n"
					+ "命令失败原因(C)...[Invalid]\\n";
			msg.setOperationResult("[号码不在HSS库中]");
		} else {
			if("4G".equalsIgnoreCase(getNetType(u.getUnitType()))||"IMS".equalsIgnoreCase(getNetType(u.getUnitType()))) {
				memo += "操作结果(M).......[成功]<00>\\n"
						+ "命令失败原因(C)...[Invalid]\\n";
			}
			else if("WWW".equalsIgnoreCase(getNetType(u.getUnitType()))) {
				memo += "成功标识:0" +
						"失败原因:\\n";
			}
			num.save();

		}
		return memo;
	}

    /*
	 * BKMB撤销 1.如果MB不是BK状态，返回失败原因“BKMB不存在” 2.对于HSS，这条在模拟器上与其他网元相同，不考虑特殊处理。
	 */
	private void a4(String numberFormat, String number) {
        Unit u = setUnitStatus("1");
        X1msg msg = new X1msg();
		setMsg(msg);
		msg.setPerposeId(number);
		msg.setMsgType("BKMB撤销请求");
		String rtn = "";
		if("4G".equalsIgnoreCase(getNetType(u.getUnitType()))||"IMS".equalsIgnoreCase(getNetType(u.getUnitType()))){
			rtn = "协议版本号........[1]<01>\\n" +
					"消息类型..........[BKMB撤消请求]\\n" +
					"MB类型(M).......[" + numberFormat + "]<" + BCD.getTargetType(numberFormat) + ">\\n"
					+ "MB标识(M).......[" + number + "]<" + BCD.str2rBcdstr(number) + ">\\n";
		}
		else if("WWW".equalsIgnoreCase(getNetType(u.getUnitType()))){
			rtn = "["+DateKit.toStr(new Date())+"] "+u.getUnitId()+" --- X1_DELTARGET_REQ --- 38\\n" +
					"目标类型:"+numberFormat+"\\n" +
					"目标标识:"+number+"\\n" +
					"访问类型:"+getPara("addType")+"\\n" +
					"访问标识:"+getPara("add")+"\\n";
		}
		msg.setMemo(Encrypt.encode(rtn));
		msg.save();

		msg = new X1msg();
		setMsg(msg);
		msg.setMsgType("BKMB撤销响应");
		if("4G".equalsIgnoreCase(getNetType(u.getUnitType()))||"IMS".equalsIgnoreCase(getNetType(u.getUnitType()))){
			rtn = "协议版本号........[1]<01>\\n" +
					"消息类型..........[BKMB撤消响应]\\n" +
					"MB类型(M).......[" + numberFormat + "]<" + BCD.getTargetType(numberFormat) + ">\\n"
					+ "MB标识(M).......[" + number+ "]<" + BCD.str2rBcdstr(number) + ">\\n";
			List<Number> existNums = Number.dao.find(
					"select * from tnumber where unit='" + u.getId() + "' and number_format='" + numberFormat
							+ "' and " + numberFormat + "='" + number + "'");
			if (existNums.size() == 0) {
				rtn += "操作结果(M).......[BKMB不存在]<02>\\n"
						+ "命令失败原因(C)...[MB not exist]\\n";
				msg.setOperationResult("[BKMB不存在]");
			} else {
				Db.update("delete from tnumber where " + numberFormat + " = '" + number + "'");
				rtn += "操作结果(M).......[成功]<00>\\n"
						+ "命令失败原因(C)...[Invalid]\\n";
				msg.setOperationResult("[成功]");
			}
		}
		else if("WWW".equalsIgnoreCase(getNetType(u.getUnitType()))){

			rtn = "["+DateKit.toStr(new Date())+"] "+u.getUnitId()+" --- X1_DELTARGET_ACK --- 19\n" +
					"目标类型:"+numberFormat+"\\n" +
					"目标标识:"+number+"\\n" +
					"访问类型:"+getPara("addType")+"\\n" +
					"访问标识:"+getPara("add")+"\\n";
			List<Number> existNums = Number.dao.find(
					"select * from tnumber where unit='" + u.getId() + "' and number_format='" + numberFormat
							+ "' and " + numberFormat + "='" + number + "'");
			if (existNums.size() == 0) {
				rtn += "操作结果:BKMB不存在\\n"
						+ "命令失败原因:MB not exist\\n";
				msg.setOperationResult("[BKMB不存在]");
			} else {
				Db.update("delete from tnumber where " + numberFormat + " = '" + number + "'");
				rtn +=	"成功标识:0\\n" +
						"失败原因:\\n";
				msg.setOperationResult("[成功]");
			}

		}
		msg.setPerposeId(number);
		msg.setMemo(Encrypt.encode(rtn));
		msg.save();
//		simulation1();
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
		if("4G".equalsIgnoreCase(getNetType(u.getUnitType()))||"IMS".equalsIgnoreCase(getNetType(u.getUnitType()))) {
			msg.setMemo("协议版本号........[1]<01>\\n"
					+ "消息类型..........[BKMB列示请求]\\n"
					+ "MB类型(M).......[" + getPara("numberFormat") + "]<" + BCD.getTargetType(getPara("numberFormat")) + ">\\n");
		}
		else if("WWW".equalsIgnoreCase(getNetType(u.getUnitType()))){
			msg.setMemo("["+DateKit.toStr(new Date())+"] "+u.getUnitId()+" --- X1_LISTTARGET_REQ --- 8\\n" +
					"目标类型:3\\n");
		}
		msg.setMemo(Encrypt.encode(msg.getMemo()));
		msg.save();
		msg = new X1msg();
		setMsg(msg);
		msg.setMsgType("BKMB列示响应");
		String memo = "";
		if("4G".equalsIgnoreCase(getNetType(u.getUnitType()))||"IMS".equalsIgnoreCase(getNetType(u.getUnitType()))) {
			memo = "协议版本号........[1]<01>\\n" +
					"消息类型..........[BKMB列示响应]\\n" +
					"操作结果(M).......[成功]<00>\\n"
					+ "命令失败原因(C)...[Invalid]\\n" +
					"BKMB列表(C)...\\n" + "[\\n";
			List<Number> numbers = Number.dao.find("select * from tnumber where unit='" + u.getId() + "'");
			if (numbers.size() == 0) {
				memo = "协议版本号........[1]<01>\\n" +
						"消息类型..........[BKMB列示响应]\\n" +
						"操作结果(M).......[没有BK目标]<00>\\n"
						+ "命令失败原因(C)...[no monitor]\\n";
				memo += "";
				msg.setOperationResult("没有BK目标");
			} else {
				String format = "";
				int i = 0;
				for (Number n : numbers) {
					format = n.getNumberFormat();
					if (("all".equals(getPara("numberFormat")) || "msisdn".equals(getPara("numberFormat"))) && "msisdn".equals(format)) {
						memo += "  Num=[";
						memo += n.getMsisdn();
						memo += "]<" + BCD.str2rBcdstr(n.getMsisdn()) + ">  Numtype=[msisdn]<00> \\n";
					} else if (("all".equals(getPara("numberFormat")) || "imsi".equals(getPara("numberFormat"))) && "imsi".equals(format)) {
						memo += "  Num=[";
						memo += n.getImsi();
						memo += "]<" + BCD.str2rBcdstr(n.getImsi()) + ">  Numtype=[imsi]<01> \\n";
					} else if (("all".equals(getPara("numberFormat")) || "imei".equals(getPara("numberFormat"))) && "imei".equals(format)) {
						memo += "  Num=[";
						memo += n.getImei();
						memo += "]<" + BCD.str2rBcdstr(n.getImei()) + ">  Numtype=[imei]<02> \\n";
					}

					i++;

					if (i % 100 == 0) {
						memo += "]本次列示MB数:100\\n结束标记(C).......[Continue]<00>\\\\n";
						msg.setMemo(memo);
						msg.setMemo(Encrypt.encode(msg.getMemo()));
						msg.save();
						msg = new X1msg();
						setMsg(msg);
						msg.setMsgType("BKMB列示响应");
						i = 0;
						memo = "协议版本号........[1]<01>\\n" +
								"消息类型..........[BKMB列示响应]\\n" +
								"操作结果(M).......[成功]<00>\\n"
								+ "命令失败原因(C)...[Invalid]\\n" +
								"BKMB列表(C)...\\n" + "[\\n";
					}

				}
				memo += "]本次列示MB数:";
				memo += i + "\\n" + "结束标记(C).......[End]<01>\\n";
			}
		}
		else if("WWW".equalsIgnoreCase(getNetType(u.getUnitType()))){
			memo = "["+DateKit.toStr(new Date())+"] "+u.getUnitId()+" --- X1_LISTTARGET_ACK --- 9\\n" +
					"成功标识:0\\n" +
					"失败原因:\\n" +
					"被控目标列表:\\n";

			List<Number> numbers = Number.dao.find("select * from tnumber where unit='" + u.getId() + "'");
			if (numbers.size() == 0) {
				memo = "["+DateKit.toStr(new Date())+"] "+u.getUnitId()+"--- X1_LISTTARGET_ACK --- 3\\n" +
						"成功标识:1\\n" +
						"失败原因:1\\n" +
						"被控目标列表:\\n" +
						"\\n" +
						"结束标识:\\n";
				msg.setOperationResult("没有BK目标");
			} else {
				String format = "";
				int i = 0;
				for (Number n : numbers) {
					format = n.getNumberFormat();
					if (("all".equals(getPara("numberFormat")) || "ip".equals(getPara("numberFormat"))) && "ip".equals(format)) {
						memo += i+"  目标类型:1 目标标识:";
						memo += n.getIp();
						i++;

					}
					else if (("all".equals(getPara("numberFormat")) || "ip_time".equals(getPara("numberFormat"))) && "ip_time".equals(format)) {
						memo += i+"  目标类型:2 目标标识:";
						memo += n.getIpTime();
						i++;

					}
					else if (("all".equals(getPara("numberFormat")) || "ip_port".equals(getPara("numberFormat"))) && "ip_port".equals(format)) {
						memo += i+"  目标类型:3 目标标识:";
						memo += n.getIpPort();
						i++;
					}
					else if (("all".equals(getPara("numberFormat")) || "ip_address_field".equals(getPara("numberFormat"))) && "ip_address_field".equals(format)) {
						memo += i+"  目标类型:4 目标标识:";
						memo += n.getIpAddressField();
						i++;
					}
					else if (("all".equals(getPara("numberFormat")) || "nai".equals(getPara("numberFormat"))) && "nai".equals(format)) {
						memo += i+"  目标类型:5 目标标识:";
						memo += n.getNai();
						i++;
					}
					else if (("all".equals(getPara("numberFormat")) || "phone_number".equals(getPara("numberFormat"))) && "phone_number".equals(format)) {
						memo += i+"  目标类型:6 目标标识:";
						memo += n.getPhoneNumber();
						i++;
					}
					memo += " 访问类型:"+ n.getAddType() + " 访问标识:"+n.getAdd() + " 传输层协议:"+n.getTp()+" \\n";


					if (i != 0 && i % 100 == 0) {
						memo += "本次列示MB数:100\\n结束标记:0\\n";
						msg.setMemo(memo);
						msg.setMemo(Encrypt.encode(msg.getMemo()));
						msg.save();
						msg = new X1msg();
						setMsg(msg);
						msg.setMsgType("BKMB列示响应");
						i = 0;
						memo = "["+DateKit.toStr(new Date())+"] "+u.getUnitId()+" --- X1_LISTTARGET_ACK --- 9\\n" +
								"成功标识:0\\n" +
								"失败原因:\\n" +
								"被控目标列表:\\n";
					}

				}
				memo += "]本次列示MB数:" + i + "\\n" +
						"结束标识:1\\n";
			}
//			memo += "0=目标类型:3 \\n" +
//					"目标标识:"+getPara("number")+" \\n" +
//					"访问类型:0 \\n" +
//					"访问标识: \\n" +
//					"传输层协议:\\n" +
//					"结束标识:1\\n";
		}
		msg.setMemo(memo);
		msg.setMemo(Encrypt.encode(msg.getMemo()));
		msg.save();
//		simulation1();
	}

	/**
	 * MB信息查询
	 */
	private void a6() {
		Unit u = setUnitStatus("1");
		X1msg msg = new X1msg();
		setMsg(msg);
		msg.setMsgType("MB信息查询请求");

		if("4G".equalsIgnoreCase(getNetType(u.getUnitType()))||"IMS".equalsIgnoreCase(getNetType(u.getUnitType()))) {
			msg.setMemo("协议版本号........[1]<01>\\n"
					+ "消息类型..........[查询请求]\\n"
					+ "MB类型(M).......[" + getPara("numberFormat") + "]<" + BCD.getTargetType(getPara("numberFormat")) + ">\\n"
					+ "MB标识(M).......[" + getPara("number") + "]<" + BCD.str2rBcdstr(getPara("number")) + ">\\n");
		}
		else if("WWW".equalsIgnoreCase(getNetType(u.getUnitType()))){
			msg.setMemo("["+DateKit.toStr(new Date())+"] "+u.getUnitId()+" --- X1_QUREYUSERINFO_REQ --- 6\n" +
					"目标类型:3\n" +
					"目标标识:"+getPara("number")+"\n" +
					"查询时间:(null)");
		}
		msg.setMemo(Encrypt.encode(msg.getMemo()));
		msg.save();
		msg = new X1msg();
		setMsg(msg);
		msg.setMsgType("用户信息查询响应");
		if("4G".equalsIgnoreCase(getNetType(u.getUnitType()))||"IMS".equalsIgnoreCase(getNetType(u.getUnitType()))) {
			Number n = Number.dao.findFirst("select * from tnumber_user where "+getPara("numberFormat")+" = '"+getPara("number")+"' ");
			if(n==null){
				msg.setMemo("协议版本号........[1]<01>\\n"
						+ "消息类型..........[用户信息查询响应]\\n"
						+ "MB类型(M).......[" + getPara("numberFormat") + "]<" + BCD.getTargetType(getPara("numberFormat")) + ">	\\n"
						+ "MB标识(M).......[" + getPara("number") + "]<" + BCD.str2rBcdstr(getPara("number")) + ">\\n"
						+ "操作结果(M).......[失败]<00>\\n"
						+ "命令失败原因(C)...[MB not exists]\\n");
				msg.setOperationResult("失败");
			}
			else {
				String unitView = u.getUnitTypeView();
				if ("AS".equals(unitView)) {
					msg.setMemo("协议版本号...........[2]<02>\\n" +
							"消息类型.............[用户信息查询响应]\\n" +
							"目标标识(M)..........[tel:"+ getPara("number") +"]<"+BCD.str2rBcdstr(getPara("number"))+">\\n" +
							"操作结果(M)..........[成功]<00>\\n" +
							"失败原因(C)..........[Invalid]\\n" +
							"AS上报信息(C)\\n" +
							"    补充业务类型列表(C)..\\n" +
							"[  Service Type = [呼叫等待(sCW)]<05 00 00 00>\\n" +
							"  Service Type = [呼叫保持(sCH)]<08 00 00 00>\\n" +
							"  Service Type = [呼叫恢复(sResum)]<09 00 00 00>\\n" +
							"]\n" +
							"    前转标识列表(C)......[Invalid]\\n" +
							"HSS上报信息(C)\\n" +
							"    用户状态(M)..........[Invalid]\\n" +
							"    拜访网络标识列表(M)..[Invalid]\\n" +
							"    S-CSCF名(C)..........[Invalid]\\n" +
							"    AS名列表(C)..........[Invalid]\\n" +
							"S-CSCF上报信息(C)\\n" +
							"    用户状态(M)..........[Invalid]\\n" +
							"    拜访网络标识(C)......[Invalid]\\n" +
							"    HSS/SLF域名(M).......[Invalid]\\n" +
							"    接入侧域名(M)........[Invalid]\\n" +
							"    Contact地址(M).......[Invalid]\\n" +
							"SBC地址(C)...............[Invalid]\\n" +
							"MSISDN(C)................[Invalid]\\n" +
							"IMSI(C)..................[Invalid]\\n" +
							"[ 编码码流 ]:　Length = 47\\n" +
							"30 2d 80 01 02 a1 28 ac 26 a0 14 80 12 74 65 6c 3a 2b 38 36 \\n" +
							"31 38 33 33 32 31 34 32 34 32 34 81 01 00 a3 0b a0 09 0a 01 \\n" +
							"05 0a 01 08 0a 01 09\\n");
				} else if ("IMSHSS".equals(unitView)) {
					msg.setMemo("协议版本号...........[2]<02>\\n" +
							"消息类型.............[用户信息查询响应]\\n" +
							"目标标识(M)..........[tel:"+ getPara("number") +"]<"+BCD.str2rBcdstr(getPara("number"))+">\\n" +
							"操作结果(M)..........[成功]<00>\\n" +
							"失败原因(C)..........[Invalid]\\n" +
							"AS上报信息(C)\\n" +
							"    补充业务类型列表(C)..[Invalid]\\n" +
							"    前转标识列表(C)......[Invalid]\\n" +
							"    主附卡关联列表(C)(适用一号多终端AS)......[Invalid]\\n" +
							"HSS上报信息(C)\\n" +
							"    用户状态(M)..........[在线 (online)]<01>\\n" +
							"    拜访网络标识列表(M)..\\n" +
							"[\\n" +
							"  VisitNetworkID =[pcscf01.zj.ims.chinaunicom.cn]<70 63 73 63 66 30 31 2e 7a 6a 2e 69 6d 73 2e 63 68 69 6e 61 75 6e 69 63 6f 6d 2e 63 6e>\\n" +
							"  VisitNetworkID =[pcscf02.zj.ims.chinaunicom.cn]<70 63 73 63 66 30 32 2e 7a 6a 2e 69 6d 73 2e 63 68 69 6e 61 75 6e 69 63 6f 6d 2e 63 6e>\\n" +
							"  VisitNetworkID =[agcf01.zj.ims.chinaunicom.cn]<61 67 63 66 30 31 2e 7a 6a 2e 69 6d 73 2e 63 68 69 6e 61 75 6e 69 63 6f 6d 2e 63 6e>\\n" +
							"  VisitNetworkID =[agcf02.zj.ims.chinaunicom.cn]<61 67 63 66 30 32 2e 7a 6a 2e 69 6d 73 2e 63 68 69 6e 61 75 6e 69 63 6f 6d 2e 63 6e>\\n" +
							"]\\n" +
							"    S-CSCF名(C)..........[sip:scscf02.zj.ims.chinaunicom.cn]<73 69 70 3a 73 63 73 63 66 30 32 2e 7a 6a 2e 69 6d 73 2e 63 68 69 6e 61 75 6e 69 63 6f 6d 2e 63 6e>\\n" +
							"    AS名列表(C)..........\\n" +
							"[\\n" +
							"  As Name=[sip:mmtel90.zj.ims.chinaunicom.cn]<73 69 70 3a 6d 6d 74 65 6c 39 30 2e 7a 6a 2e 69 6d 73 2e 63 68 69 6e 61 75 6e 69 63 6f 6d 2e 63 6e>\\n" +
							"]\\n" +
							"S-CSCF上报信息(C)\\n" +
							"    用户状态(M)..........[Invalid]\\n" +
							"    拜访网络标识(C)......[Invalid]\\n" +
							"    HSS/SLF域名(M).......[Invalid]\\n" +
							"    接入侧域名(M)........[Invalid]\\n" +
							"    Contact地址(M).......[Invalid]\\n" +
							"SBC地址(C)...............[Invalid]\\n" +
							"MSISDN(C)................[Invalid]\\n" +
							"IMSI(C)..................[Invalid]\\n" +
							"接入侧互通AGCF上报信息(C)(适用互通节点接入侧互通AGCF)\\n" +
							"    MSISDN(C)..........[Invalid]\\n" +
							"    IMSI(C)......[Invalid]\\n" +
							"    IMEI(C).......[Invalid]\\n" +
							"    ISDN(C)........[Invalid]\\n" +
							"    用户状态(C).......[Invalid]\\n" +
							"    位置更新时间(C).......[Invalid]\\n" +
							"    最新位置(C).......[Invalid]\\n");
				} else if ("S-CSCF".equals(unitView)) {
					msg.setMemo(
							"协议版本号...........[2]<02>\\n" +
							"消息类型.............[用户信息查询响应]\\n" +
							"目标标识(M)..........[tel:"+ getPara("number") +"]<"+BCD.str2rBcdstr(getPara("number"))+">\\n" +
							"操作结果(M)..........[成功]<00>\\n" +
							"失败原因(C)..........[Invalid]\\n" +
							"AS上报信息(C)\\n" +
							"    补充业务类型列表(C)..[Invalid]\\n" +
							"    前转标识列表(C)......[Invalid]\\n" +
							"    主附卡关联列表(C)(适用一号多终端AS)......[Invalid]\\n" +
							"HSS上报信息(C)\\n" +
							"    用户状态(M)..........[Invalid]\\n" +
							"    拜访网络标识列表(M)..[Invalid]\\n" +
							"    S-CSCF名(C)..........[Invalid]\\n" +
							"    AS名列表(C)..........[Invalid]\\n" +
							"S-CSCF上报信息(C).........\\n" +
							"[\\n" +
							"    用户状态(M)..........[在线 (online)]<01>\\n" +
							"    拜访网络标识(C)......[\\n" +
							"  VisitNetworkID =[pcscf01.zj.ims.chinaunicom.cn]<70 63 73 63 66 30 31 2e 7a 6a 2e 69 6d 73 2e 63 68 69 6e 61 75 6e 69 63 6f 6d 2e 63 6e>\\n" +
							"  VisitNetworkID =[pcscf02.zj.ims.chinaunicom.cn]<70 63 73 63 66 30 32 2e 7a 6a 2e 69 6d 73 2e 63 68 69 6e 61 75 6e 69 63 6f 6d 2e 63 6e>\\n" +
							"  VisitNetworkID =[agcf01.zj.ims.chinaunicom.cn]<61 67 63 66 30 31 2e 7a 6a 2e 69 6d 73 2e 63 68 69 6e 61 75 6e 69 63 6f 6d 2e 63 6e>\\n" +
							"  VisitNetworkID =[agcf02.zj.ims.chinaunicom.cn]<61 67 63 66 30 32 2e 7a 6a 2e 69 6d 73 2e 63 68 69 6e 61 75 6e 69 63 6f 6d 2e 63 6e>\\n" +
							"]\\n" +
							"    HSS/SLF域名(M).......[hss.domain21.huawei.com]\\n" +
							"    接入侧域名(M)........[pcscf.domain21.huawei.com]\\n" +
							"    Contact地址(M).......[sip:+8617830939495@191.139.2.12:11169]\\n" +
							"]\\n" +
							"SBC地址(C)...............[Invalid]\\n" +
							"MSISDN(C)................[Invalid]\\n" +
							"IMSI(C)..................[Invalid]\\n" +
							"接入侧互通AGCF上报信息(C)(适用互通节点接入侧互通AGCF)\\n" +
							"    MSISDN(C)..........[Invalid]\\n" +
							"    IMSI(C)......[Invalid]\\n" +
							"    IMEI(C).......[Invalid]\\n" +
							"    ISDN(C)........[Invalid]\\n" +
							"    用户状态(C).......[Invalid]\\n" +
							"    位置更新时间(C).......[Invalid]\\n" +
							"    最新位置(C).......[Invalid]");
				} else if ("P-CSCF".equals(unitView) || "AGCF".equals(unitView)) {
					msg.setMemo("协议版本号...........[2]<02>\\n" +
							"消息类型.............[用户信息查询响应]\\n" +
							"目标标识(M)..........[tel:"+ getPara("number") +"]<"+BCD.str2rBcdstr(getPara("number"))+">\\n" +
							"操作结果(M)..........[成功]<00>\\n" +
							"失败原因(C)..........[Invalid]\\n" +
							"AS上报信息(C)\\n" +
							"    补充业务类型列表(C)..[Invalid]\\n" +
							"    前转标识列表(C)......[Invalid]\\n" +
							"    主附卡关联列表(C)(适用一号多终端AS)......[Invalid]\\n" +
							"HSS上报信息(C)\\n" +
							"    用户状态(M)..........[Invalid]\\n" +
							"    拜访网络标识列表(M)..[Invalid]\\n" +
							"    S-CSCF名(C)..........[Invalid]\\n" +
							"    AS名列表(C)..........[Invalid]\\n" +
							"S-CSCF上报信息(C)\\n" +
							"    用户状态(M)..........[Invalid]\\n" +
							"    拜访网络标识(C)......[Invalid]\\n" +
							"    HSS/SLF域名(M).......[Invalid]\\n" +
							"    接入侧域名(M)........[Invalid]\\n" +
							"    Contact地址(M).......[Invalid]\\n" +
							"SBC地址(C)...............[10.188.101.97:5060]<31 30 2e 31 38 38 2e 31 30 31 2e 39 37 3a 35 30 36 30>\\n" +
							"\\n" +
							"MSISDN(C)................[Invalid]\\n" +
							"IMSI(C)..................[Invalid]\\n" +
							"接入侧互通AGCF上报信息(C)(适用互通节点接入侧互通AGCF)\\n" +
							"    MSISDN(C)..........[Invalid]\\n" +
							"    IMSI(C)......[Invalid]\\n" +
							"    IMEI(C).......[Invalid]\\n" +
							"    ISDN(C)........[Invalid]\\n" +
							"    用户状态(C).......[Invalid]\\n" +
							"    位置更新时间(C).......[Invalid]\\n" +
							"    最新位置(C).......[Invalid]");
				} else {
					msg.setMemo("协议版本号........[1]<01>\\n"
							+ "消息类型..........[用户信息查询响应]\\n"
							+ "MB类型(M).......[" + getPara("numberFormat") + "]<" + BCD.getTargetType(getPara("numberFormat")) + ">	\\n"
							+ "MB标识(M).......[" + getPara("number") + "]<" + BCD.str2rBcdstr(getPara("number")) + ">\\n"
							+ "操作结果(M).......[成功]<00>\\n"
							+ "命令失败原因(C)...[Invalid]\\n"
							+ "MSISDN(C).........[" + n.getMsisdn() + "]<" + BCD.str2rBcdstr(n.getMsisdn()) + ">\\n"
							+ "IMSI(C)...........[" + n.getImsi() + "]<" + BCD.str2rBcdstr(n.getImsi()) + ">\\n"
							+ "IMEI(C)...........[" + n.getImei() + "]<" + BCD.str2rBcdstr(n.getImei()) + ">\\n"
							+ "用户状态(C).......[(ecm_Connected)]<00>\\n"
							+ "位置更新时间(C)...[" + BCD.getCurrentTime() + "]<" + BCD.getTimerBcd(BCD.getCurrentTime()) + ">\\n"
							+ "附着时间(C).......[Invalid]\\n"
							+ "最新位置(C): \\n"
							+ " gummeid[C]......[Invalid]\\n"
							+ "  lai[C]..........[Invalid]\\n"
							+ "  lastVisitedTAI[C]........[MCC460 MNC30 TAC20007]<64 f0 03 4e 27 >\\n"
							+ "  taiList[C]......[Invalid]\\n"
							+ "  ecgi[C].........[460 30 4294902530]<64 f0 03 ff ff 03 02 >\\n");
				}
			}
		}
		else if("WWW".equalsIgnoreCase(getNetType(u.getUnitType()))){
			Number n = Number.dao.findFirst("select * from tnumber_user where "+getPara("numberFormat")+" = '"+getPara("number")+"' ");
			if(n == null){
				msg.setMemo("[" + DateKit.toStr(new Date()) + "] " + u.getUnitId() + " --- X1_QUREYUSERINFO_ACK --- 7\\n" +
						"目标类型:" + getPara("numberFormat") + "\\n" +
						"目标标识:" + getPara("number") + "\\n" +
						"成功标识:1\\n" +
						"失败原因:MB not exists\\n" +
						"结束标识:1\\n");
				msg.setOperationResult("失败");
			}
			else {
				msg.setMemo("[" + DateKit.toStr(new Date()) + "] " + u.getUnitId() + " --- X1_QUREYUSERINFO_ACK --- 7\\n" +
						"目标类型:" + getPara("numberFormat") + "\\n" +
						"目标标识:" + getPara("number") + "\\n" +
						"成功标识:0\\n" +
						"失败原因:\\n" +
						"用户信息列表:\\n" +
						"0=用户状态:1\\n" +
						"接入获得的IP地址列表:" + n.getIp() + "\\n" +
						"\\n" +
						"结束标识:\\n" +
						"新NAI标识:" + n.getNai() + "\\n" +
						"电话号码:" + n.getPhoneNumber() + "\\n" +
						"结束标识:1\\n");
			}
		}
		msg.setMemo(Encrypt.encode(msg.getMemo()));
		msg.save();
//		simulation1();
	}

	/**
	 * 网元时间查询
	 */
	private void a7() {
		Unit u = setUnitStatus("1");
		X1msg msg = new X1msg();
		setMsg(msg);
		msg.setMsgType("网元时间查询请求");
		if("4G".equalsIgnoreCase(getNetType(u.getUnitType()))||"IMS".equalsIgnoreCase(getNetType(u.getUnitType()))){
			msg.setMemo("协议版本号........[1]<01>\\n"
					+ "消息类型..........[网元时间查询请求]\\n"
					+ "EPC网元标识(M)....[" + u.getUnitId()
					+ "]<" + BCD.str2HexStr(u.getUnitId()) + ">\\n");
		}
		else if("WWW".equalsIgnoreCase(getNetType(u.getUnitType()))){
			msg.setMemo("["+DateKit.toStr(new Date())+"] "+u.getUnitId()+" --- X1_QUERYTIME_REQ --- 4\\n" +
					"LIC标识:LIC0\\n" +
					"INE标识:"+u.getUnitId()+"\\n");
		}
		msg.setMemo(Encrypt.encode(msg.getMemo()));
		msg.save();
		msg = new X1msg();
		setMsg(msg);
		msg.setMsgType("网元时间查询响应");
		if("4G".equalsIgnoreCase(getNetType(u.getUnitType()))||"IMS".equalsIgnoreCase(getNetType(u.getUnitType()))){
			msg.setMemo("协议版本号........[1]<01>\\n" +
					"消息类型..........[网元时间查询响应]\\n" +
					"EPC网元标识(M)....[" + u.getUnitId() + "]<" + BCD.str2HexStr(u.getUnitId()) + ">\\n" +
					"网元时间:..........["+DateKit.toStr(new Date(), "yyyy-MM-dd HH:mm:ss")+"]\\n" +
					"操作结果(M).......[成功]<00>\\n"
					+ "命令失败原因(C)...[Invalid]\\n");
		}
		else if("WWW".equalsIgnoreCase(getNetType(u.getUnitType()))){
			msg.setMemo("["+DateKit.toStr(new Date())+"] "+u.getUnitId()+" --- X1_QUERYTIME_ACK --- 5\\n" +
					"INE标识:"+u.getUnitId()+"\\n" +
					"成功标识:0\\n" +
					"失败原因:\\n" +
					"网元时间:"+DateKit.toStr(new Date(), "yyyyMMddHHmmss")+"\\n");
		}
		msg.setMemo(Encrypt.encode(msg.getMemo()));
		msg.save();
//		simulation1();
	}

	/**
	 * BKMB参数修改
	 */
	private void a8() {
		Unit unit = setUnitStatus("1");
		X1msg msg = new X1msg();
		setMsg(msg);
		msg.setMsgType("BKMB参数修改请求");
		if("4G".equalsIgnoreCase(getNetType(unit.getUnitType()))||"IMS".equalsIgnoreCase(getNetType(unit.getUnitType()))){
		msg.setMemo("协议版本号........[2]<02>\\n" +
				"消息类型..........[BKMB参数修改请求]\\n" +
				"目标标识(M).......["+getPara("number")+"]<"+BCD.str2rBcdstr(getPara("number"))+">\\n" +
				"X2地址(C).........\\n" +
				"  Port:["+getPara("x2Port")+"]<13 88>\\n" +
				"  IP:["+getPara("x2Ip")+"]<0a b8 4d 49>\\n");
		}
		else if("WWW".equalsIgnoreCase(getNetType(unit.getUnitType()))){
			msg.setMemo("目标类型：2\\n" +//1，ip 2 ip_add段 3 nai 4 phone_number 5 mac_add 6 ip_time 7 ip_port 8 ip_vrf 9 ip_port_vrf 255 all
                    "目标标识："+getPara("number")+"\\n" +
                    "访问地址类型："+getPara("addType")+"\\n" +
                    "访问地址标识："+getPara("add")+"\\n" +
                    "传输层协议："+getPara("tp")+"\\n" +
                    "应用业务类型："+getPara("appType")+"");
		}
		msg.setMemo(Encrypt.encode(msg.getMemo()));
		msg.save();
		msg = new X1msg();
		setMsg(msg);
		msg.setMsgType("BKMB参数修改响应");
		if("4G".equalsIgnoreCase(getNetType(unit.getUnitType()))||"IMS".equalsIgnoreCase(getNetType(unit.getUnitType()))){
			List<Number> existNums = Number.dao.find(
					"select * from tnumber where unit='" + unit.getId() + "' and number_format='" + getPara("numberFormat")
							+ "' and " + getPara("numberFormat") + "='" + getPara("number") + "'");
			if(existNums.size()==0) {
				msg.setMemo("协议版本号........[2]<02>\\n" +
						"消息类型..........[BKMB参数修改响应]\\n" +
						"目标标识(M).......[" + getPara("number") + "]<" + BCD.str2rBcdstr(getPara("number")) + ">\\n" +
						"操作结果(M).......[失败]<01>\\n" +
						"失败原因(C).......[被控目标不存在 (monitorNumberNotExist)]<01>\\n");
				msg.setOperationResult("失败");
			}
			else{
				for(Number n:existNums){
					n.setX2ip(getPara("x2ip"));
					n.setX2port(getPara("x2port"));
					n.setX3ip(getPara("x3ip"));
					n.setX3port(getPara("x3port"));
					n.setMonType(getPara("monType"));
					n.setAddType(getPara("addType"));
					n.setAdd(getPara("add"));
					n.setTp(getPara("tp"));
					n.setAppBizType("appBizType");
					n.setLnsAdd(getPara("lnsAdd"));
					n.setSn1(getParaToInt("sn1"));
					n.setSn2(getParaToInt("sn2"));
					n.update();
				}
				msg.setMemo("协议版本号........[2]<02>\n" +
						"消息类型..........[BKMB参数修改响应]\n" +
						"目标标识(M).......[" + getPara("number") + "]<" + BCD.str2rBcdstr(getPara("number")) + ">\n" +
						"操作结果(M).......[成功]<01>\n" +
						"失败原因(C).......[invalid]<01>\n" +
						"\n");
			}
		}
		else if("WWW".equalsIgnoreCase(getNetType(unit.getUnitType()))){
			List<Number> existNums = Number.dao.find(
					"select * from tnumber where unit='" + unit.getId() + "' and number_format='" + getPara("numberFormat")
							+ "' and " + getPara("numberFormat") + "='" + getPara("number") + "'");
			if(existNums.size()==0) {
				msg.setMemo("协议版本号[2]<02>\\n" +
						"消息类型:[BKMB参数修改响应]\\n" +
						"目标标识:" + getPara("number") + "\\n" +
						"操作结果:[失败]<01>\\n" +
						"失败原因:被控目标不存在 (monitorNumberNotExist)\\n");
				msg.setOperationResult("失败");
			}
			else{
				for(Number n:existNums){
					n.setX2ip(getPara("x2ip"));
					n.setX2port(getPara("x2port"));
					n.setX3ip(getPara("x3ip"));
					n.setX3port(getPara("x3port"));
					n.setMonType(getPara("monType"));
					n.setAddType(getPara("addType"));
					n.setAdd(getPara("add"));
					n.setTp(getPara("tp"));
					n.setAppBizType("appBizType");
					n.setLnsAdd(getPara("lnsAdd"));
					n.setSn1(getParaToInt("sn1"));
					n.setSn2(getParaToInt("sn2"));
					n.update();
				}
				msg.setMemo("目标类型：2\\n" +//1，ip 2 ip_add段 3 nai 4 phone_number 5 mac_add 6 ip_time 7 ip_port 8 ip_vrf 9 ip_port_vrf 255 all
                        "目标标识："+getPara("number")+"\\n" +
                                "访问地址类型："+getPara("addType")+"\\n" +
                                "访问地址标识："+getPara("add")+"\\n" +
                                "传输层协议："+getPara("tp")+"\\n" +
                                "应用业务类型："+getPara("appType")+"\\n" +
                        "操作结果：\\n" + //0 成功 1 失败
                        "失败原因：\\n"); //原因找原来文档
			}
		}
		msg.setMemo(Encrypt.encode(msg.getMemo()));
		msg.save();
//		simulation1();
	}

	/**
	 * BK目标参数查询
	 */
	private void a9() {
        Unit unit =setUnitStatus("1");
        X1msg msg = new X1msg();
        setMsg(msg);
        msg.setMsgType("BK目标参数查询请求");
		if("4G".equalsIgnoreCase(getNetType(unit.getUnitType()))||"IMS".equalsIgnoreCase(getNetType(unit.getUnitType()))) {
			msg.setMemo("[ 12:02:20 ] [ 消息内容 (IMS_PLX_X1 ==> NE) ][ NEID = " + unit.getUnitTypeView() + " ]:\\n" +
					"协议版本号........[2]<02>\\n" +
					"消息类型..........[被控目标参数查询请求]\\n" +
					"目标标识(M).......[" + getPara("number") + "]<" + BCD.str2rBcdstr(getPara("number")) + ">\\n" +
					"\\n");
		}
        else if("WWW".equalsIgnoreCase(getNetType(unit.getUnitType()))){
			msg.setMemo("目标类型：" +getPara("numberFormat")+"\\n"+
                    "目标标识："+getPara("number")+"\\n");
        }
        msg.setMemo(Encrypt.encode(msg.getMemo()));
        msg.save();
        msg = new X1msg();
        setMsg(msg);
        msg.setMsgType("BK目标参数查询响应");
		Number num = Number.dao.findFirst("select * from tnumber where unit = '"+getPara("unit")+"' and "+getPara("numberFormat")+" = '"+getPara("number")+"'");
		if("4G".equalsIgnoreCase(getNetType(unit.getUnitType()))||"IMS".equalsIgnoreCase(getNetType(unit.getUnitType()))) {
		    if(num==null){
                msg.setMemo("协议版本号........[2]<02>\\n" +
                        "消息类型..........[BKMB参数查询响应]\\n" +
                        "目标标识(M).......[" + getPara("number") + "]<" + BCD.str2rBcdstr(getPara("number")) + ">\\n" +
                        "操作结果(M).......[失败]<00>\\n" +
                        "失败原因(C).......[MB not exits]\\n");
				msg.setOperationResult("失败");
            }
		    else {
                msg.setMemo("协议版本号........[2]<02>\\n" +
                        "消息类型..........[BKMB参数查询响应]\\n" +
                        "目标标识(M).......[" + getPara("number") + "]<" + BCD.str2rBcdstr(getPara("number")) + ">\\n" +
                        "操作结果(M).......[成功]<00>\\n" +
                        "失败原因(C).......[Invalid]\\n" +
                        "X2地址(C).........\\n" +
                        "  Port:[" + num.getX2port() + "]<" + BCD.str2rBcdstr(num.getX2port()) + ">\\n" +
                        "  IP:[" + num.getX2ip() + "]<" + BCD.str2rBcdstr(num.getX2ip()) + ">\\n");
            }
		}
		else if("WWW".equalsIgnoreCase(getNetType(unit.getUnitType()))){
			if(num==null){
				msg.setMemo("目标类型："+getPara("numberFormat")+"\\n" +
						"目标标识:"+getPara("number")+"\\n" +
						"操作结果：1\\n" +
						"失败原因：MB not exists\\n");
				msg.setOperationResult("失败");
			}
			else {
				msg.setMemo("目标类型：" + getPara("numberFormat") + "\\n" +
						"目标标识:" + getPara("number") + "\\n" +
						"操作结果：0\\n" +
						"失败原因：\\n" +
						"bk目标参数列表：" +
						"访问地址类型：" + num.getAddType() + "\\n"+
						"访问地址标识：" + num.getAdd() + "\\n"+
						"传输层协议：" +  num.getTp() + "\\n"+
						"应用业务类型：" + num.getAppBizType() + "\\n"+
						"结束标志：1");
				if(unit.getUnitTypeView().equals("AAA")){
						msg.setMemo(msg.getMemo()+
								"监听方式类型：" +num.getMonType()+"\\n"+//AAA查询会有
						"LNS地址："+num.getLnsAdd());//AAA查询会有
				}
			}
		}
        msg.setMemo(Encrypt.encode(msg.getMemo()));
        msg.save();
//		simulation1();
    }

	/**
	 * BK目标位置定位查询
	 */
	private void a10() {
        Unit unit = setUnitStatus("1");
        X1msg msg = new X1msg();
        setMsg(msg);
        msg.setMsgType("BK目标位置定位查询请求");
		if("4G".equalsIgnoreCase(getNetType(unit.getUnitType()))||"IMS".equalsIgnoreCase(getNetType(unit.getUnitType()))) {
			msg.setMemo(""+unit.getUnitId()+" --- X1_QUERYLOCATION_REQ --- 2 \\n" +
					"目标类型："+getPara("numberFormat")+"\\n" +
					"目标标识："+getPara("number")+"\\n");
		}
		else if("WWW".equalsIgnoreCase(getNetType(unit.getUnitType()))){
			msg.setMemo("["+DateKit.toStr(new Date())+"] "+unit.getUnitId()+" --- X1_QUERYLOCATION_REQ --- 8\\n" +
					"目标类型:"+getPara("numberFormat")+"\\n" +
					"目标标识:"+getPara("number")+"\\n");
		}
        msg.setMemo(Encrypt.encode(msg.getMemo()));
        msg.save();
        msg = new X1msg();
        setMsg(msg);
        msg.setMsgType("BK目标位置定位查询响应");
		if("4G".equalsIgnoreCase(getNetType(unit.getUnitType()))||"IMS".equalsIgnoreCase(getNetType(unit.getUnitType()))) {
			msg.setMemo(""+unit.getUnitId()+" --- X1_QUERYLOCATION_ACK --- 3 \\n" +
					"INE标识:"+unit.getUnitId()+" \\n" +
					"成功标识: 0 \\n" +
					"失败原因:\\n" +
					"AP标识：17826644\\n");
		}
		else if("WWW".equalsIgnoreCase(getNetType(unit.getUnitType()))){
			msg.setMemo("["+DateKit.toStr(new Date())+"] "+unit.getUnitId()+" --- X1_QUERYLOCATION_ACK --- 9\n" +
					"INE标识:"+unit.getUnitId()+"\\n" +
					"成功标识:1\n" +
					"失败原因:255\n" +
					"AP标识:");
		}
        msg.setMemo(Encrypt.encode(msg.getMemo()));
        msg.save();
//        simulation1();
    }

	/**
	 * 目标内/外网IP地址关联查询 仅互联网设备
	 */
	private void a11() {
        Unit unit = setUnitStatus("1");
        X1msg msg = new X1msg();
        setMsg(msg);
        msg.setMsgType("目标内/外网IP地址关联查询请求");
		if("WWW".equalsIgnoreCase(getNetType(unit.getUnitType()))){
			msg.setMemo("目标标识类型：7\\n" +
                    "目标标识：\\n" +
                    "查询标识：1\\n");
		}
        msg.setMemo(Encrypt.encode(msg.getMemo()));
        msg.save();
        msg = new X1msg();
        setMsg(msg);
        msg.setMsgType("目标内/外网IP地址关联查询响应");
		if("WWW".equalsIgnoreCase(getNetType(unit.getUnitType()))){
			msg.setMemo("INE标识："+unit.getUnitId()+"\\n" +
                    "成功标识：0\\n" +
                    "失败原因：\\n" +
                    "查询标识：\\n" +
                    "目标类型：" + getPara("numberFormat")+"\\n"+
                    "目标标识："+ getPara("number")+"\\n");
		}
        msg.setMemo(Encrypt.encode(msg.getMemo()));
        msg.save();
//		simulation1();
    }

	/**
	 * IMPU关联标识查询
	 */
	private void a13() {
        Unit unit = setUnitStatus("1");
        X1msg msg = new X1msg();
        setMsg(msg);
        msg.setMsgType("IMPU关联标识查询请求");
		if("IMS".equalsIgnoreCase(getNetType(unit.getUnitType()))) {
			msg.setMemo("[ 18:06:42 ] [ 消息内容 ("+unit.getLicId()+" ==> NE) ][ NEID = "+unit.getUnitId()+" ]:\\n" +
					"协议版本号........[2]<02>\\n" +
					"消息类型..........[IMPU关联标识查询请求]\\n" +
					"目标标识(M).......["+ getPara("number") +"]<"+BCD.str2rBcdstr(getPara("number"))+">\\n" +
					"\n");
		}

        msg.setMemo(Encrypt.encode(msg.getMemo()));
        msg.save();
        msg = new X1msg();
        setMsg(msg);
        msg.setMsgType("IMPU关联标识查询响应");
        String sql = "select * from tnumber_user where 1=1 ";
        String tel = getPara("tel"),sip = getPara("sip");
        if(StrKit.notBlank(tel)){
        	sql += "  and id in (select number_user_id from tnumber_user_sub where impu_type='tel' and impu = '"+tel+"')";
		}
        if(StrKit.notBlank(sip)){
			sql += "  and id in (select number_user_id from tnumber_user_sub where impu_type='sip' and impu = '"+sip+"')";
		}
		Number num = Number.dao.findFirst(sql);
		if("IMS".equalsIgnoreCase(getNetType(unit.getUnitType()))) {
        String rtn = "协议版本号........[2]<02>\\n" +
                "消息类型..........[IMPU关联标识查询响应]\\n" +
				"目标标识(M).......["+ getPara("number") +"]<"+BCD.str2rBcdstr(getPara("number"))+">\\n" ;
				if(num!=null) {
					rtn += "操作结果(M).......[成功]<00>\\n" +
							"失败原因(C).......[Invalid]\\n" +
							"IMPI标识(C).......[" + num.getImpi() + "]<" + BCD.str2rBcdstr(num.getImpi()) + ">\\n";
				}
				else{
					rtn = "操作结果：失败\\n" +
                            "命令失败原因：标识错误\\n" +
                            "用户不存在或标识错误、输入tel，类型选sip等，IMPI标识空\\n";
					msg.setOperationResult("失败");
				}
				msg.setMemo(rtn);
		}

        msg.setMemo(Encrypt.encode(msg.getMemo()));
        msg.save();
//		simulation1();
    }

	/**
	 * IMPI关联标识查询
	 */
	private void a12() {
        Unit unit = setUnitStatus("1");
        X1msg msg = new X1msg();
        setMsg(msg);
		msg.setMsgType("IMPI关联标识查询请求");
		if("4G".equalsIgnoreCase(getNetType(unit.getUnitType()))||"IMS".equalsIgnoreCase(getNetType(unit.getUnitType()))) {
			msg.setMemo("[ 18:12:30 ] [ 消息内容 (IMS_PLX_X1 ==> NE) ][ NEID = HSS1 ]:\\n" +
					"协议版本号........[2]<02>\\n" +
					"消息类型..........[IMPI关联标识查询请求]\\n" +
					"目标标识(M).......[" + getPara("number") + "]<" + BCD.str2rBcdstr(getPara("number")) + ">\\n" +
					"\n");
		}

        msg.setMemo(Encrypt.encode(msg.getMemo()));
        msg.save();
        msg = new X1msg();
        setMsg(msg);
        msg.setMsgType("IMPI关联标识查询响应");
        Number num = Number.dao.findFirst("select * from tnumber_user where impi = '"+getPara("impi")+"'");
		if("4G".equalsIgnoreCase(getNetType(unit.getUnitType()))||"IMS".equalsIgnoreCase(getNetType(unit.getUnitType()))) {
			if(num!=null) {
				String rtn = "";
				rtn = ("协议版本号........[2]<02>\\n" +
						"消息类型..........[IMPI关联标识查询响应]\\n" +
						"目标标识(M).......[" + getPara("number") + "]<" + BCD.str2rBcdstr(getPara("number")) + ">\\n" +
						"操作结果(M).......[成功]<00>\\n" +
						"失败原因(C).......[Invalid]\\n" +
						"IMPU标识列表(C)...\\n" +
						"[\\n");
				List<NumberUserSub> nusList = NumberUserSub.dao.find("select * from tnumber_user_sub where number_user_id="+num.getId());
				for(NumberUserSub n:nusList) {
					if("sip".equals(n.getImpuType())) {
						rtn += "  Num=[sip:" + n.getImpu() + "]<" + BCD.str2rBcdstr(n.getImpu()) + ">\\n";
					}
					else if("tel".equals(n.getImpuType())){
						rtn += "num=[tel:" + n.getImpu() + "]<" + BCD.str2rBcdstr(n.getImpu()) + ">\\n";
					}
				}
				msg.setMemo(rtn+"]本次列示目标数:2\\n");
			}
			else{
			    msg.setMemo("操作结果：失败\\n"+
                        "命令失败原因：标识错误\\n" +
                        "用户不存在或标识错误、输入tel，类型选sip等，IMPI标识空\\n");
				msg.setOperationResult("失败");
            }
		}

        msg.setMemo(Encrypt.encode(msg.getMemo()));
        msg.save();
//		simulation1();
    }

	/**
	 * 批量撤销
	 */
	private void a14() {
		String unit = getPara("unit");
		Unit u = Unit.dao.findById(unit);
		int numberCount = getParaToInt("numberCount");
		String numberStr = getPara("number");
		int bkNumber = 0;
		String bkNumberStr = "";
		String numberFormat = getPara("numberFormat");


		for(int i=0;i<numberCount;i++) {
			if("msisdn".equals(numberFormat) || "imei".equals(numberFormat) || "imsi".equals(numberFormat) || "tel".equals(numberFormat)) {
				if (StrKit.notBlank(numberStr)) {
					bkNumber = Integer.valueOf(numberStr.substring(numberStr.length() - 4, numberStr.length()));
					bkNumberStr = numberStr.substring(0, numberStr.length() - 4);
				}
				if (bkNumber + i > 9999) {
					break;
				}
				bkNumberStr += genNumber(bkNumber+i,4);
			}
			else if("ip".equals(numberFormat)){
				if(StrKit.notBlank(numberStr)){
					bkNumber = Integer.valueOf(numberStr.substring(numberStr.lastIndexOf('.')+1,numberStr.length()));
					bkNumberStr = numberStr.substring(0,numberStr.lastIndexOf('.')+1);
				}
				if(bkNumber+i > 255){
					break;
				}
				bkNumberStr += bkNumber+i;
			}

			a4(numberFormat, bkNumberStr);
		}
	}

    /**
     * 批量BKMB设定消息
     */
    private void a15() {
        String unit = getPara("unit");
        Unit u = Unit.dao.findById(unit);
        int numberCount = getParaToInt("numberCount");
        String numberStr = getPara("number");
        int bkNumber = 0;
        String bkNumberStr = "";


        for(int i=0;i<numberCount;i++) {

            X1msg msg = new X1msg();
            setMsg(msg);
            msg.setMsgType("BKMB设定请求");
            String rtn = "";
            if ("4G".equalsIgnoreCase(getNetType(u.getUnitType())) || "IMS".equalsIgnoreCase(getNetType(u.getUnitType()))) {
				if(StrKit.notBlank(numberStr)){
					bkNumber = Integer.valueOf(numberStr.substring(numberStr.length()-4,numberStr.length()));
					bkNumberStr = numberStr.substring(0,numberStr.length()-4);
				}
				if(bkNumber+i > 9999){
					break;
				}
				bkNumberStr += genNumber(bkNumber+i,4);
                rtn = "协议版本号........[1]<01>\\n"
                        + "消息类型..........[BKMB设定请求]\\n"
                        + "MB类型(M).......[" + getPara("numberFormat") + "]<" + BCD.getTargetType(getPara("numberFormat")) + ">\\n"
                        + "MB标识(M).......[" + bkNumberStr + "]<" + BCD.str2rBcdstr(bkNumberStr) + ">\\n";
            }
			else if ("WWW".equalsIgnoreCase(getNetType(u.getUnitType())) ) {
				if(StrKit.notBlank(numberStr)){
					bkNumber = Integer.valueOf(numberStr.substring(numberStr.lastIndexOf('.')+1,numberStr.length()));
					bkNumberStr = numberStr.substring(0,numberStr.lastIndexOf('.')+1);
				}
				if(bkNumber+i > 255){
					break;
				}
				bkNumberStr += bkNumber+i;
				rtn = "["+DateKit.toStr(new Date())+"] "+u.getUnitId()+" --- X1_SETTARGET_REQ --- 2\\n" +
						"目标类型:"+getPara("numberFormat")+"\\n" +
						"目标标识:"+getPara("number")+"\\n" +
						"监控模式:"+getPara("monType")+"\\n" +
						"访问类型:"+getPara("addType")+"\\n" +
						"访问标识:"+getPara("add")+"\\n" +
						"传输层协议:"+getPara("tp")+"\\n" +
						"应用业务类型:"+getPara("appBizType")+"\\n" +
						"LNS服务器IP地址:"+getPara("lnsAdd")+"\\n";

			}
            msg.setMemo(Encrypt.encode(rtn));
			msg.setPerposeId(bkNumberStr);
            msg.save();
            msg = new X1msg();
            setMsg(msg);
            msg.setPerposeId(bkNumberStr);
            msg.setMsgType("BKMB设定响应");

			Number num = new Number();
			num.setNumberFormat(getPara("numberFormat"));
			num.setCreateTime(new Date());
			num.setMsisdn("1111111");
			num.setImei("11111111");
			num.setImsi("11111111");
			num.setIp("111.111.111.111");
			num.setIpPort("1111");
			num.setIpTime("1111");
			num.setIpPort("111.111.111.111:1111");
			num.setIpAddressField("111.111.111.111");
			num.setNai("1111");
			num.setPhoneNumber("1111111");
			num.setSip("111@111.com");
			num.setTel("111111");
			num.setX2ip(getPara("x2ip"));
			num.setX2port(getPara("x2port"));
			num.setX3ip(getPara("x3ip"));
			num.setX3port(getPara("x3port"));
			num.setMonType(getPara("monType"));
			num.setAddType(getPara("addType"));
			num.setAdd(getPara("add"));
			num.setTp(getPara("tp"));
			num.setAppBizType(getPara("appBizType"));
			num.setLnsAdd(getPara("lnsAdd"));
			num.setUnit(u.getId());
            if ("4G".equalsIgnoreCase(getNetType(u.getUnitType())) || "IMS".equalsIgnoreCase(getNetType(u.getUnitType()))) {
                rtn = "协议版本号........[1]<01>\\n"
                        + "消息类型..........[BKMB设定响应]\\n"
                        + "MB类型(M).......[" + getPara("numberFormat") + "]<" + BCD.getTargetType(getPara("numberFormat")) + ">\\n"
                        + "MB标识(M).......[" + bkNumberStr + "]<" + BCD.str2rBcdstr(bkNumberStr) + ">\\n";
                /*
                 * 将号码存入被测网元 失败处理：
                 * 1.如果已BK，返回BKMB已存在
                 * 2.对于HSS，如果MB未开户，则HSS返回失败“用户不存在”
                 * 3.如果采用IMEI，号码长度不是15位，返回参数错误。
                 * 4.超过网元上限，返回“资源不足”
                 */
                List<Number> existNums = Number.dao.find(
                        "select * from tnumber where unit='" + u.getId() + "' and number_format='" + getPara("numberFormat")
                                + "' and " + getPara("numberFormat") + "='" + bkNumberStr + "'");
                if (existNums.size() > 0) {
                    rtn += "操作结果(M).......[BKMB已存在]<02>\\n"
                            + "命令失败原因(C)...[MB Be Set]\\n";
                    msg.setOperationResult("[BKMB已存在]");
                } else if (Number.dao.find("select * from tnumber where unit='" + u.getId() + "'").size() > PropKit
                        .getInt("unit_number_size")) {
                    rtn += "操作结果(M).......[资源限制]<02>\\n"
                            + "命令失败原因(C)...[resource limited]\\n";
                    msg.setOperationResult("[资源不足]");
                } else {
                    if ("msisdn".equals(num.getNumberFormat())) {
                        num.setMsisdn(bkNumberStr);
                        rtn = getHssMemo(msg, rtn, num);
                    } else if ("Phone_number".equals(num.getNumberFormat())) {
                        num.setPhoneNumber(bkNumberStr);
                    } else if ("TEL".equals(num.getNumberFormat())) {
                        num.setTel(bkNumberStr);
                    }
                    rtn += "操作结果(M).......[成功]<00>\\n"
                            + "命令失败原因(C)...[Invalid]\\n";
                }
            }
            else if ("WWW".equalsIgnoreCase(getNetType(u.getUnitType()))) {
				rtn = "["+DateKit.toStr(new Date())+"] "+u.getUnitId()+" --- X1_SETTARGET_ACK --- 7\\n" +
						"目标类型:"+getPara("numberFormat")+"\\n" +
						"目标标识:"+getPara("number")+"\\n" +
						"访问类型:"+getPara("addType")+"\\n" +
						"访问标识:"+getPara("add")+"\\n" +
						"传输层协议:"+getPara("tp")+"\\n" +
						"应用协议类型:"+getPara("appType")+"\\n" +
						"---接入获得的IP地址列表:\\n" +
						"结束标识:\\n";//
				/*
				 * 将号码存入被测网元 失败处理： 1.如果已BK，返回BKMB已存在 2.对于HSS，如果MB未开户，则HSS返回失败“用户不存在”
				 * 3.如果采用IMEI，号码长度不是15位，返回参数错误。 4.超过网元上限，返回“资源不足”
				 */
				List<Number> existNums = Number.dao.find(
						"select * from tnumber where unit='" + u.getId() + "' and number_format='" + getPara("numberFormat")
								+ "' and " + getPara("numberFormat") + "='" + bkNumberStr + "'");
				if (existNums.size() > 0) {
					rtn += "操作结果:BKMB已存在\\n"
							+ "命令失败原因:MB Be Set\\n";
					msg.setOperationResult("[BKMB已存在]");
				} else if (Number.dao.find("select * from tnumber where unit='" + u.getId() + "'").size() > PropKit
						.getInt("unit_number_size")) {
					rtn += "操作结果:资源限制\\n"
							+ "命令失败原因:resource limited]\\n";
					msg.setOperationResult("[资源不足]");
				} else {

					if ("msisdn".equals(num.getNumberFormat())) {
						num.setMsisdn(getPara("number"));
						rtn = getHssMemo(msg, rtn, num);
					} else if ("imei".equals(num.getNumberFormat())) {
						num.setImei(getPara("number"));
						/*
						 * IMEI号低于15的，报参数错误。
						 */
						if (num.getImei().length() < 15) {
							rtn += "操作结果:失败\\n"
									+ "命令失败原因:parameterError\\n";
						} else {
							rtn = getHssMemo(msg, rtn, num);
						}
					} else if ("imsi".equals(num.getNumberFormat())) {
						num.setImsi(getPara("number"));
						rtn = getHssMemo(msg, rtn, num);
					} else if ("ip".equals(num.getNumberFormat())) {
						num.setIp(getPara("number"));
						rtn = getHssMemo(msg, rtn, num);
					} else if ("ip_time".equals(num.getNumberFormat())) {
						num.setIpTime(getPara("number"));
						rtn = getHssMemo(msg, rtn, num);
					} else if ("ip_port".equals(num.getNumberFormat())) {
						num.setIpPort(getPara("number"));
						rtn = getHssMemo(msg, rtn, num);
					} else if ("ip_address_field".equals(num.getNumberFormat())) {
						num.setIpAddressField(getPara("number"));
						rtn = getHssMemo(msg, rtn, num);
					} else if ("nai".equals(num.getNumberFormat())) {
						num.setNai(getPara("number"));
						rtn = getHssMemo(msg, rtn, num);
					} else if ("phone_number".equals(num.getNumberFormat())) {
						num.setPhoneNumber(getPara("number"));
						rtn = getHssMemo(msg, rtn, num);
					} else if ("sip".equals(num.getNumberFormat())) {
						num.setSip(getPara("number"));
						rtn = getHssMemo(msg, rtn, num);
					} else if ("tel".equals(num.getNumberFormat())) {
						num.setTel(getPara("number"));
						rtn = getHssMemo(msg, rtn, num);
					}

				}
			}

            msg.setMemo(rtn);
            msg.setMemo(Encrypt.encode(msg.getMemo()));
            msg.save();
        }
//        simulation1();
    }

	private static String genNumber(Integer id,int length) {
		String rtn = "";
		String sid= ""+id;
		for(int i=0;i<length-sid.length();i++) {
			rtn += "0";
		}
		rtn += sid;
		return rtn;
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

	public void x1_delete() {
		Db.update("delete from tx1msg");
		simulation1();
	}

	public void x2_delete() {
		Db.update("delete from tx2msg");
		simulation2();
	}

	public void x3_delete() {
		Db.update("delete from tx3msg");
		simulation3();
	}
}
