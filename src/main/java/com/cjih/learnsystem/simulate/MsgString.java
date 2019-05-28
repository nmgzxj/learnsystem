//package com.cjih.learnsystem.simulate;
//
//import com.cjih.learnsystem.common.util.BCD;
//import com.cjih.learnsystem.common.util.Encrypt;
//import com.jfinal.club.common.model.*;
//import com.jfinal.club.common.model.Number;
//import com.jfinal.ext.kit.DateKit;
//import com.jfinal.kit.PropKit;
//import com.jfinal.plugin.activerecord.Db;
//
//import java.util.Date;
//import java.util.List;
//
//public class MsgString {
//
//    public MsgString(Unit unit){
//        this.unit = unit;
//    }
//
//    /**
//     * 消息类型，如建立连接请求，建立连接响应等。
//     */
//    public String msgType = "";
//
//    /**
//     * 网络类型：4G、互联网、IMS
//     */
//    public String netType = "";
//
//    /**
//     * X1消息，为超过1页的目标列式准备。
//     */
//    X1msg x1msg = null;
//
//    /**
//     * 网元ID
//     */
//    public String unitId = "";
//
//    /**
//     * LICID
//     */
//    public String licId = "";
//
//    /**
//     * 消息创建时间
//     */
//    public String msgTime = "";
//
//    /**
//     * 成功标识
//     */
//    public String successStr = "";
//
//    /**
//     *失败原因
//     */
//    public String failCouse = "";
//
//    /**
//     * 目标标识类型
//     */
//    public String numberFormat = "";
//
//    /**
//     * 目标标识
//     */
//    public String number = "";
//
//    /**
//     * 网元
//     */
//    private Unit unit;
//
//    private String operationResult = "";
//
//    public String getOperationResult(){
//        return operationResult;
//    }
//
//    public String getMsg(){
//        String rtn = "";
//
//        if("createConnectionReq".equalsIgnoreCase(msgType)){
//            rtn = getCreateConnectionReq();
//        }
//        else if("createConnectionAck".equalsIgnoreCase(msgType)){
//            rtn = getCreateConnectionAck();
//        }
//        else if("connectionRelease".equalsIgnoreCase(msgType)){
//            rtn = getConnectionRelease();
//        }
//        else if("setupTargetReq".equalsIgnoreCase(msgType)){
//            rtn = getSetupTargetReq();
//        }
//        else if("setupTargetAck".equalsIgnoreCase(msgType)){
//            rtn = getSetupTargetAck();
//        }
//        else if("deleteTargetReq".equalsIgnoreCase(msgType)){
//            rtn = getDeleteTargetReq();
//        }
//        else if("deleteTargetAck".equalsIgnoreCase(msgType)){
//            rtn = getDeleteTargetAck();
//        }
//        else if("listTargetReq".equalsIgnoreCase(msgType)){
//            rtn = getListTargetReq();
//        }
//        else if("listTargetAck".equalsIgnoreCase(msgType)){
//            rtn = getListTargetAck();
//        }
//        else if("targetInfoReq".equalsIgnoreCase(msgType)){
//            rtn = getTargetInfoReq();
//        }
//        else if("targetInfoAck".equalsIgnoreCase(msgType)){
//            rtn = getTargetInfoAck();
//        }
//        else if("queryTimeReq".equalsIgnoreCase(msgType)){
//            rtn = getQueryTimeReq();
//        }
//        else if("queryTimeAck".equalsIgnoreCase(msgType)){
//            rtn = getQueryTimeAck();
//        }
//        else if("targetParameterModifyReq".equalsIgnoreCase(msgType)){
//            rtn = getTargetParameterModify();
//        }
//        else if("targetParameterModifyAck".equalsIgnoreCase(msgType)){
//            rtn = getTargetParameterModifyAck();
//        }
//        else if("targetParameterQueryReq".equalsIgnoreCase(msgType)){
//            rtn = getTargetParameterQuery();
//        }
//        else if("targetParameterQueryAck".equalsIgnoreCase(msgType)){
//            rtn = getTargetParameterQueryAck();
//        }
//        else if("locationQueryReq".equalsIgnoreCase(msgType)){
//            rtn = getLocationQueryReq();
//        }
//        else if("locationQueryAck".equalsIgnoreCase(msgType)){
//            rtn = getLocationQueryAck();
//        }
//        else if("ipConnectionQueryReq".equalsIgnoreCase(msgType)){
//            rtn = getIpConnectionQuery();
//        }
//        else if("ipConnectionQueryAck".equalsIgnoreCase(msgType)){
//            rtn = getIpConnectionQueryAck();
//        }
//        else if("IMPUQueryReq".equalsIgnoreCase(msgType)){
//            rtn = getIMPUQuery();
//        }
//        else if("IMPUQueryAck".equalsIgnoreCase(msgType)){
//            rtn = getIMPUQueryAck();
//        }
//        else if("IMPIQueryReq".equalsIgnoreCase(msgType)){
//            rtn = getIMPIQueryReq();
//        }
//        else if("IMPIQueryAck".equalsIgnoreCase(msgType)){
//            rtn = getIMPIQueryAck();
//        }
//        else{
//            rtn = "未知消息类型。";
//        }
//        return rtn;
//    }
//
//    /**
//     * 建立连接请求。
//     * @return
//     */
//    private String getCreateConnectionReq(){
//        String rtn = "";
//        if("4G".equals(netType)){
//            rtn = "协议版本号........[1]<00 00 00 01 >\\n"
//                + "消息类型..........[连接建立请求]\\n"
//                + "LIC标 识(M).......[" + unit.getLicId() + "]<" + BCD.str2HexStr(unit.getLicId()) + ">\\n"
//                + "网元标识(M).......[" + unit.getUnitId() + "]<" + BCD.str2HexStr(unit.getUnitId()) + " >\\n"
//                + "SQN组号(M)........[" + unit.getX1Sqn() + "]<00 00 00 02 >\\n"
//                + "RAND(M)...........<ca f6 96 be dc b1 d2 >\\n"
//                + "认证字段(M).......<d5 64 58 30 6b 24 bb 24 1c 9c 34 e0 5a 62 86 35 >\\n";
//        }
//        else if("WWW".equals(netType)){
//            rtn = "["+ DateKit.toStr(new Date())+"] "+unit.getUnitId()+"--- X1_CONNECT_REQ --- 2\\n" +
//                    "LIC标识:"+unit.getLicId()+"\\n" +
//                    "INE标识:"+unit.getUnitId()+"\\n" +
//                    "SQN组数:"+unit.getX1Sqn()+"\\n" +
//                    "随机数:1012226\\n" +
//                    "认证字段:S!\u0014\u0001?箕V^+讐'蝂\\n";
//        }
//        else if("IMS".equals(netType)){
//            rtn = "[ 17:43:55 ] [ 消息内容 (IMS_PLX_X1 ==> NE) ][ NEID = "+unit.getUnitId()+" ]:\\n" +
//                    "协议版本号........[2]<02>\\n" +
//                    "消息类型..........[X1连接建立请求]\\n" +
//                    "LIC标 识(M).......["+unit.getLicId()+"]<54 54 54 45 53 54>\\n" +
//                    "网元标识(M).......["+unit.getUnitId()+"]<31 32 33 34 35 36>\\n" +
//                    "SQN组号(M)........["+unit.getX1Sqn()+"]<23 00 00>\\n" +
//                    "RAND(M)...........<c8 cc 57 a0 fb 7a bb>\\n" +
//                    "认证字段(M).......<4a b8 7e 9b 53 8e 36 23 9c c8 b0 d7 b3 99 09 6d>\\n";
//        }
//        else{
//            rtn = "未知网络类型。";
//        }
//        return rtn;
//    }
//
//    /**
//     * 建立连接响应。
//     * @return
//     */
//    private String getCreateConnectionAck(){
//        String rtn = "";
//        if("4G".equals(netType) || "IMS".equalsIgnoreCase(netType)){
//             rtn = "协议版本号........[1]<00 00 00 01 >\\n"
//                    + "消息类型..........[连接建立响应]\\n"
//                    + "网元标识(M).......[" + unit.getUnitId() + "]<" + BCD.str2HexStr(unit.getUnitId()) + ">\\n"
//                    + "LIC标 识(M).......[" + unit.getLicId() + "]<" + BCD.str2HexStr(unit.getLicId()) + ">\\n";
//            try {
//                TrueUnit unitInfo = TrueUnit.dao.findFirst("select * from ttrue_unit where unit_id = '" + unit.getUnitId()
//                        + "' and lic_id = '" + unit.getLicId() + "'");
//                if (null == unitInfo) {// 如果网元id和licid没有对应关系，则判定为未登记。
//                    rtn += "执行结果(M).......[Failure]<00 00 00 01 >\\n"
//                            + "失败原因(C).......[Ne Not Registered]<00 00 00 00 >\\n"
//                            + "应答字段(C).......[Invalid][Ne Not Registered]\\n";
//                    operationResult = "[Failure]";
//                }
//                else if(!unit.getUnitId().equals(unitInfo.getUnitId())) {//疑问：网元ID非法？
//                    rtn += "执行结果(M).......[Failure]<00 00 00 01 >\\n" +
//                            "失败原因(C).......[Ne Id Invalid]<00 00 00 02 >\\n" +
//                            "应答字段(C).......[Invalid]\\n";
//                }
//                else if (!unit.getX1Sqn().equals(unitInfo.getX1Sqn()) | !unit.getX1Kt().equals(unitInfo.getX1Kt())
//                        | !unit.getX1Pwd().equals(unitInfo.getX1Pwd())) {
//                    rtn += "执行结果(M).......[Failure]<00 00 00 01 >\\n"
//                            + "失败原因(C).......[Authentication Fail]<00 00 00 01 >\\n"
//                            + "应答字段(C).......[Invalid]\\n";
//                    operationResult = "[Failure][Authentication Fail]";
//                }
//                else if ("1".equals(unit.getX1Status())) {
//                    rtn += "执行结果(M).......[Failure]<00 00 00 01 >\\n"
//                            + "失败原因(C).......[Connect is Exist]<00 00 00 03 >\\n"
//                            + "应答字段(C).......[Invalid]\\n";
//                    operationResult = "[Failure][Connect is Exist]";
//                } else {
//                    rtn += "执行结果(M).......[Success]<00 00 00 00 >\\n"
//                            + "失败原因(C).......[Connect is Exist]<00 00 00 03 >\\n"
//                            + "应答字段(C).......[Invalid]\\n";
//                    operationResult = "[Success]";
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                rtn += "执行结果(M).......[Failure]<00 00 00 01 >\\n"
//                        + "失败原因(C).......[Connect is Exist]<00 00 00 FF >\\n"
//                        + "应答字段(C).......[Other]\\n";
//                operationResult = "[Failure][Connect is Exist]";
//
//            }
//
//        }
//        else if("WWW".equals(netType)){
//            rtn = "["+ DateKit.toStr(new Date())+"] "+unit.getUnitId()+" --- X1_CONNECT_ACK --- 3\\n" +
//                    "INE标识:"+unit.getUnitId()+"\\n" +
//                    "LIC标识:"+unit.getLicId()+"\\n" +
//                    "成功标识:1\\n" +
//                    "失败原因:2\\n" +
//                    "应答字段:\\n" +
//                    "ps：\\n" +
//                    "成功标识：1-连接失败\\n" +
//                    "失败原因：2-被控目标已设定\\n";
//        }
//        else{
//            rtn = "未知网络类型。";
//        }
//        return rtn;
//    }
//
//    private String getConnectionRelease(){
//        String rtn = "";
//        if("4G".equals(netType)){
//            rtn = "协议版本号........[1]<00 00 00 01 >\\n"
//                    + "消息类型..........[连接释放通知]\\n"
//                    + "LIC标 识(M).......[" + unit.getLicId() + "]<" + BCD.str2HexStr(unit.getLicId()) + ">\\n"
//                    + "网元标识(M).......[" + unit.getUnitId() + "]<" + BCD.str2HexStr(unit.getUnitId()) + " >\\n"
//                    + "连接释放原因(M)...[Normal Release]<00 00 00 00 >\\n";
//        }
//        else if("WWW".equals(netType)){
//            rtn = "["+DateKit.toStr(new Date())+"] AAA0 --- X1_CONNECTRELEASE_REQ --- 10\\n" +
//                    "LIC标识:"+unit.getLicId()+"\\n" +
//                    "INE标识:"+unit.getUnitId()+"\\n" +
//                    "释放原因:1\\n";
//        }
//        else if("IMS".equals(netType)){
//
//        }
//        else{
//            rtn = "未知网络类型。";
//        }
//        return rtn;
//    }
//
//    private String getSetupTargetReq(){
//        String rtn = "";
//        if("4G".equals(netType)){
//            rtn = "协议版本号........[1]<01>\\n"
//                    + "消息类型..........[BKMB设定请求]\\n"
//                    + "MB类型(M).......[" + numberFormat + "]<" + BCD.getTargetType(numberFormat) + ">\\n"
//                    + "MB标识(M).......[" + number + "]<" + BCD.str2rBcdstr(number) + ">\\n";
//        }
//        else if("WWW".equals(netType)){
//            rtn = "[2018-07-30 14:59:03] AAA0 --- X1_SETTARGET_REQ --- 2\\n" +
//                    "目标类型:3\\n" +
//                    "目标标识:FSSBWH\\n" +
//                    "监控模式:\\n" +
//                    "访问类型:0\\n" +
//                    "访问标识:\\n" +
//                    "传输层协议:\\n" +
//                    "应用业务类型:\\n" +
//                    "LNS服务器IP地址:\\n";
//        }
//        else if("IMS".equals(netType)){
//
//        }
//        else{
//            rtn = "未知网络类型。";
//        }
//        return rtn;
//    }
//
//    private String getSetupTargetAck(){
//        String rtn = "";
//        if("4G".equals(netType) || "IMS".equalsIgnoreCase(netType)){
//            rtn = "协议版本号........[1]<01>\\n"
//                    + "消息类型..........[BKMB设定响应]\\n"
//                    + "MB类型(M).......[" + numberFormat + "]<" + BCD.getTargetType(numberFormat) + ">\\n"
//                    + "MB标识(M).......[" + number + "]<" + BCD.str2rBcdstr(number) + ">\\n";
//            /*
//             * 将号码存入被测网元 失败处理： 1.如果已BK，返回BKMB已存在 2.对于HSS，如果MB未开户，则HSS返回失败“用户不存在”
//             * 3.如果采用IMEI，号码长度不是15位，返回参数错误。 4.超过网元上限，返回“资源不足”
//             */
//            List<Number> existNums = Number.dao.find(
//                    "select * from tnumber where unit='" + unit.getId() + "' and number_format='" + numberFormat
//                            + "' and " + numberFormat + "='" + number + "'");
//            if (existNums.size() > 0) {
//                rtn += "操作结果(M).......[BKMB已存在]<02>\\n"
//                        + "命令失败原因(C)...[MB Be Set]\\n";
//                operationResult = "[BKMB已存在]";
//            } else if (Number.dao.find("select * from tnumber where unit='" + unit.getId() + "'").size() > PropKit
//                    .getInt("unit_number_size")) {
//                rtn += "操作结果(M).......[资源限制]<02>\\n"
//                        + "命令失败原因(C)...[resource limited]\\n";
//                operationResult = "[资源不足]";
//            } else {
//                Number num = new Number();
//                num.setMsisdn("1111111");
//                num.setImei("11111111");
//                num.setImsi("11111111");
//                num.setIp("111.111.111.111");
//                num.setIpPort("1111");
//                num.setIpTime("1111");
//                num.setIpPort("111.111.111.111:1111");
//                num.setIpAddressField("111.111.111.111");
//                num.setNai("1111");
//                num.setPhoneNumber("1111111");
//                num.setSip("111@111.com");
//                num.setTel("111111");
//                num.setNumberFormat(numberFormat);
//                if ("msisdn".equals(num.getNumberFormat())) {
//                    num.setMsisdn(number);
//                    rtn = getHssMemo(rtn);
//                }
//                else if ("imei".equals(num.getNumberFormat())) {
//                    num.setImei(number);
//                    /*
//                     * IMEI号低于15的，报参数错误。
//                     */
//                    if (num.getImei().length() < 15) {
//                        rtn += "操作结果(M).......[失败]<00>\\n"
//                                + "命令失败原因(C)...[parameterError]<06>\\n";
//                    } else {
//                        rtn = getHssMemo(rtn);
//                    }
//                }
//                else if ("imsi".equals(num.getNumberFormat())) {
//                    num.setImsi(number);
//                }
//                else if ("IP".equals(num.getNumberFormat())) {
//                    num.setIp(number);
//                }
//                else if ("IP_time".equals(num.getNumberFormat())) {
//                    num.setIpTime(number);
//                }
//                else if ("IP_PORT".equals(num.getNumberFormat())) {
//                    num.setIpPort(number);
//                }
//                else if ("Ip_address_field".equals(num.getNumberFormat())) {
//                    num.setIpAddressField(number);
//                }
//                else if ("NAI".equals(num.getNumberFormat())) {
//                    num.setNai(number);
//                }
//                else if ("Phone_number".equals(num.getNumberFormat())) {
//                    num.setPhoneNumber(number);
//                }
//                else if ("SIP".equals(num.getNumberFormat())) {
//                    num.setSip(number);
//                }
//                else if ("TEL".equals(num.getNumberFormat())) {
//                    num.setTel(number);
//                }
//                rtn += "操作结果(M).......[成功]<00>\\n"
//                        + "命令失败原因(C)...[Invalid]\\n";
//                //		IP、IP_time、IP-PORT、Ip_address_field、NAI、Phone_number、SIP、TEL
//                num.setCreateTime(new Date());
//                num.setUnit(unit.getId());
//                num.save();
//            }
//        }
//        else if("WWW".equals(netType)){
//            rtn = "[2018-05-25 09:29:12] AAA0 --- X1_SETTARGET_ACK --- 7\\n" +
//                    "目标类型:3\\n" +
//                    "目标标识:13803508714@wlan\\n" +
//                    "访问类型:0\\n" +
//                    "访问标识:\\n" +
//                    "传输层协议:\\n" +
//                    "应用协议类型:\\n" +
//                    "---接入获得的IP地址列表:\\n" +
//                    "结束标识:\\n" +
//                    "成功标识:0\\n" +
//                    "失败原因:\\n";
//        }
//        else{
//            rtn = "未知网络类型。";
//        }
//        return rtn;
//    }
//
//    private String getHssMemo(String memo) {
//        String sql = "select * from tnumber_user where "+numberFormat+" = '"+number+"'";
//        List<NumberUser> numberUserList = NumberUser.dao.find(sql);
//
//        //if (u.getUnitTypeView().equals("HSS") && !getPara("number").equals(PropKit.get("HSS_number"))) {
//        if(numberUserList.size()==0){
//            memo += "操作结果(M).......[号码不在HSS库中]<00>\\n"
//                    + "命令失败原因(C)...[Invalid]\\n";
//            operationResult = "[号码不在HSS库中]";
//        } else {
//            memo += "操作结果(M).......[成功]<00>\\n"
//                    + "命令失败原因(C)...[Invalid]\\n";
//        }
//        return memo;
//    }
//
//    private String getDeleteTargetReq(){
//        String rtn = "";
//        if("4G".equals(netType)){
//            rtn = "协议版本号........[1]<01>\\n" +
//                    "消息类型..........[BKMB撤消请求]\\n" +
//                    "MB类型(M).......[" + numberFormat + "]<" + BCD.getTargetType(numberFormat) + ">\\n"
//                    + "MB标识(M).......[" + number + "]<" + BCD.str2rBcdstr(number) + ">\\n";
//        }
//        else if("WWW".equals(netType)){
//            rtn = "[2018-05-25 10:06:48] AAA0 --- X1_DELTARGET_REQ --- 38\\n" +
//                    "目标类型:3\\n" +
//                    "目标标识:13803508714@wlan\\n" +
//                    "访问类型:0\\n" +
//                    "访问标识:\\n";
//        }
//        else if("IMS".equals(netType)){
//
//        }
//        else{
//            rtn = "未知网络类型。";
//        }
//        return rtn;
//    }
//
//
//    private String getDeleteTargetAck(){
//        String rtn = "";
//        if("4G".equals(netType)){
//            rtn = "协议版本号........[1]<01>\\n" +
//                    "消息类型..........[BKMB撤消响应]\\n" +
//                    "MB类型(M).......[" + numberFormat + "]<" + BCD.getTargetType(numberFormat) + ">\\n"
//                    + "MB标识(M).......[" + number + "]<" + BCD.str2rBcdstr(number) + ">\\n";
//            List<Number> existNums = Number.dao.find(
//                    "select * from tnumber where unit='" + unit.getId() + "' and number_format='" + numberFormat
//                            + "' and " + numberFormat + "='" + number + "'");
//            if (existNums.size() == 0) {
//                rtn += "操作结果(M).......[BKMB不存在]<02>\\n" + "命令失败原因(C)...[MB not exist]\\n";
//                operationResult = "[BKMB不存在]";
//            } else {
//                Db.update("delete from tnumber where " + numberFormat + " = '" + number + "'");
//                rtn += "操作结果(M).......[成功]<00>\\n" + "命令失败原因(C)...[Invalid]\\n";
//                operationResult = "[成功]";
//            }
//        }
//        else if("WWW".equals(netType)){
//            rtn = "[2018-07-24 16:28:08] bras10 --- X1_DELTARGET_ACK --- 19\n" +
//                    "目标类型:2\n" +
//                    "目标标识:10.0.129.15:30\n" +
//                    "访问类型:0\n" +
//                    "访问标识:\n" +
//                    "成功标识:0\n" +
//                    "失败原因:";
//        }
//        else if("IMS".equals(netType)){
//
//        }
//        else{
//            rtn = "未知网络类型。";
//        }
//        return rtn;
//    }
//
//    private String getListTargetReq(){
//        String rtn = "";
//        if("4G".equals(netType)){
//            rtn = "协议版本号........[1]<01>\\n"
//                    + "消息类型..........[BKMB列示请求]\\n"
//                    + "MB类型(M).......[" + numberFormat + "]<" + BCD.getTargetType(numberFormat) + ">\\n";
//        }
//        else if("WWW".equals(netType)){
//            rtn = "[2018-05-25 09:29:20] AAA0 --- X1_LISTTARGET_REQ --- 8\\n" +
//                    "目标类型:3\\n";
//        }
//        else if("IMS".equals(netType)){
//
//        }
//        else{
//            rtn = "未知网络类型。";
//        }
//        return rtn;
//    }
//
//    private String getListTargetAck(){
//        String rtn = "";
//        if("4G".equals(netType)){
//
//        }
//        else if("WWW".equals(netType)){
//            rtn = "[2018-05-25 09:29:20] AAA0 --- X1_LISTTARGET_ACK --- 9\\n" +
//                    "成功标识:0\\n" +
//                    "失败原因:\\n" +
//                    "被控目标列表:\\n" +
//                    "0=目标类型:3 \\n" +
//                    "目标标识:13803508714@wlan \\n" +
//                    "访问类型:0 \\n" +
//                    "访问标识: 传输层协议:\\n" +
//                    "结束标识:1\\n";
//        }
//        else if("IMS".equals(netType)){
//
//        }
//        else{
//            rtn = "未知网络类型。";
//        }
//        return rtn;
//    }
//
//
//    private String getTargetInfoReq(){
//        String rtn = "";
//        if("4G".equals(netType)){
//
//        }
//        else if("WWW".equals(netType)){
//
//        }
//        else if("IMS".equals(netType)){
//
//        }
//        else{
//            rtn = "未知网络类型。";
//        }
//        return rtn;
//    }
//
//
//    private String getTargetInfoAck(){
//        String rtn = "";
//        if("4G".equals(netType)){
//
//        }
//        else if("WWW".equals(netType)){
//
//        }
//        else if("IMS".equals(netType)){
//
//        }
//        else{
//            rtn = "未知网络类型。";
//        }
//        return rtn;
//    }
//
//    private String getQueryTimeReq(){
//        String rtn = "";
//        if("4G".equals(netType)){
//            rtn = "协议版本号........[1]<01>\\n"
//                    + "消息类型..........[网元时间查询请求]\\n"
//                    + "EPC网元标识(M)....[" + unit.getUnitId()
//                    + "]<" + BCD.str2HexStr(unit.getUnitId()) + ">\\n";
//        }
//        else if("WWW".equals(netType)){
//            rtn = "[2018-05-25 09:28:36] AAA0 --- X1_QUERYTIME_REQ --- 4\\n" +
//                    "LIC标识:LIC0\\n" +
//                    "INE标识:AAA0\\n";
//        }
//        else if("IMS".equals(netType)){
//
//        }
//        else{
//            rtn = "未知网络类型。";
//        }
//        return rtn;
//    }
//
//    private String getQueryTimeAck(){
//        String rtn = "";
//        if("4G".equals(netType)){
//            rtn = "协议版本号........[1]<01>\\n" +
//                    "消息类型..........[网元时间查询响应]\\n" +
//                    "EPC网元标识(M)....[" + unit.getUnitId() + "]<" + BCD.str2HexStr(unit.getUnitId()) + ">\\n" +
//                    "操作结果(M).......[成功]<00>\\n"
//                    + "命令失败原因(C)...[Invalid]\\n";
//        }
//        else if("WWW".equals(netType)){
//            rtn = "[2018-05-25 09:28:36] AAA0 --- X1_QUERYTIME_ACK --- 5\\n" +
//                    "INE标识:AAA0\\n" +
//                    "成功标识:0\\n" +
//                    "失败原因:\\n" +
//                    "网元时间:20180525092457\\n";
//        }
//        else if("IMS".equals(netType)){
//
//        }
//        else{
//            rtn = "未知网络类型。";
//        }
//        return rtn;
//    }
//
//    private String getTargetParameterModify(){
//        String rtn = "";
//        if("4G".equals(netType)){
//            rtn = "协议版本号........[2]<02>\n" +
//                    "消息类型..........[BKMB参数修改请求]\n" +
//                    "目标标识(M).......["+number+"]<"+BCD.str2rBcdstr(number)+">\n" +
//                    "X2地址(C).........\n" +
////                    "  Port:["+getPara("x2Port")+"]<13 88>\n" +
////                    "  IP:["+getPara("x2Ip")+"]<0a b8 4d 49>\n" +
//                    "\n";
//        }
//        else if("WWW".equals(netType)){
//
//        }
//        else if("IMS".equals(netType)){
//
//        }
//        else{
//            rtn = "未知网络类型。";
//        }
//        return rtn;
//    }
//
//    private String getTargetParameterModifyAck(){
//        String rtn = "";
//        if("4G".equals(netType)){
//
//        }
//        else if("WWW".equals(netType)){
//
//        }
//        else if("IMS".equals(netType)){
//
//        }
//        else{
//            rtn = "未知网络类型。";
//        }
//        return rtn;
//    }
//
//    private String getTargetParameterQuery(){
//        String rtn = "";
//        if("4G".equals(netType)){
//
//        }
//        else if("WWW".equals(netType)){
//            rtn = "[2018-05-25 09:39:00] AAA0 --- X1_QUERYTARGETPARA_REQ --- 6\\n" +
//                    "目标类型:255\\n" +
//                    "目标标识:\\n";
//        }
//        else if("IMS".equals(netType)){
//
//        }
//        else{
//            rtn = "未知网络类型。";
//        }
//        return rtn;
//    }
//
//    private String getTargetParameterQueryAck(){
//        String rtn = "";
//        if("4G".equals(netType)){
//
//        }
//        else if("WWW".equals(netType)){
//            rtn = "[2018-07-31 10:47:33] AAA0 --- X1_QUERYTARGETPARA_ACK --- 35\n" +
//                    "目标类型:4\n" +
//                    "目标标识:13900139001\n" +
//                    "成功标识:0\n" +
//                    "失败原因:\n" +
//                    "被控目标参数列表:\n" +
//                    "结束标识:1\n" +
//                    "监控模式类型:0\n" +
//                    "LNS服务器IP地址:\n";
//        }
//        else if("IMS".equals(netType)){
//
//        }
//        else{
//            rtn = "未知网络类型。";
//        }
//        return rtn;
//    }
//
//    private String getLocationQueryReq(){
//        String rtn = "";
//        if("4G".equals(netType)){
//
//        }
//        else if("WWW".equals(netType)){
//            rtn = "[2018-05-25 10:14:21] AAA0 --- X1_QUERYLOCATION_REQ --- 8\\n" +
//                    "目标类型:3\\n" +
//                    "目标标识:13803508714@wlan\\n";
//        }
//        else if("IMS".equals(netType)){
//
//        }
//        else{
//            rtn = "未知网络类型。";
//        }
//        return rtn;
//    }
//
//    private String getLocationQueryAck(){
//        String rtn = "";
//        if("4G".equals(netType)){
//
//        }
//        else if("WWW".equals(netType)){
//            rtn = "[2018-05-25 10:14:21] AAA0 --- X1_QUERYLOCATION_ACK --- 9\\n" +
//                    "INE标识:AAA0\\n" +
//                    "成功标识:1\\n" +
//                    "失败原因:255\\n" +
//                    "AP标识:\\n";
//        }
//        else if("IMS".equals(netType)){
//
//        }
//        else{
//            rtn = "未知网络类型。";
//        }
//        return rtn;
//    }
//
//    private String getIpConnectionQuery(){
//        String rtn = "";
//        if("4G".equals(netType)){
//
//        }
//        else if("WWW".equals(netType)){
//
//        }
//        else if("IMS".equals(netType)){
//
//        }
//        else{
//            rtn = "未知网络类型。";
//        }
//        return rtn;
//    }
//
//    private String getIpConnectionQueryAck(){
//        String rtn = "";
//        if("4G".equals(netType)){
//
//        }
//        else if("WWW".equals(netType)){
//
//        }
//        else if("IMS".equals(netType)){
//
//        }
//        else{
//            rtn = "未知网络类型。";
//        }
//        return rtn;
//    }
//
//
//    private String getIMPUQuery(){
//        String rtn = "";
//        if("4G".equals(netType)){
//
//        }
//        else if("WWW".equals(netType)){
//
//        }
//        else if("IMS".equals(netType)){
//            rtn = "[ 18:06:42 ] [ 消息内容 (IMS_PLX_X1 ==> NE) ][ NEID = HSS1 ]:\\n" +
//                    "协议版本号........[2]<02>\\n" +
//                    "消息类型..........[IMPU关联标识查询请求]\\n" +
//                    "目标标识(M).......[sip:+8645185913004@hl.ims.chinaunicom.cn]<73 69 70 3a 2b 38 36 34 35 31 38 35 39 31 33 30 30 34 40 68 6c 2e 69 6d 73 2e 63 68 69 6e 61 75 6e 69 63 6f 6d 2e 63 6e>\\n";
//        }
//        else{
//            rtn = "未知网络类型。";
//        }
//        return rtn;
//    }
//
//
//    private String getIMPUQueryAck(){
//        String rtn = "";
//        if("4G".equals(netType)){
//
//        }
//        else if("WWW".equals(netType)){
//
//        }
//        else if("IMS".equals(netType)){
//            rtn = "[ 18:06:43 ] [ 消息内容 (NE ==> IMS_PLX_X1) ][ NEID = HSS1 ]:\\n" +
//                    "协议版本号........[2]<02>\\n" +
//                    "消息类型..........[IMPU关联标识查询响应]\\n" +
//                    "目标标识(M).......[sip:+8645185913004@hl.ims.chinaunicom.cn]<73 69 70 3a 2b 38 36 34 35 31 38 35 39 31 33 30 30 34 40 68 6c 2e 69 6d 73 2e 63 68 69 6e 61 75 6e 69 63 6f 6d 2e 63 6e>\\n" +
//                    "操作结果(M).......[成功]<00>\\n" +
//                    "失败原因(C).......[Invalid]\\n" +
//                    "IMPI标识(C).......[+8645185913004@hl.ims.chinaunicom.cn]<2b 38 36 34 35 31 38 35 39 31 33 30 30 34 40 68 6c 2e 69 6d 73 2e 63 68 69 6e 61 75 6e 69 63 6f 6d 2e 63 6e>\\n";
//        }
//        else{
//            rtn = "未知网络类型。";
//        }
//        return rtn;
//    }
//
//
//    private String getIMPIQueryReq(){
//        String rtn = "";
//        if("4G".equals(netType)){
//
//        }
//        else if("WWW".equals(netType)){
//
//        }
//        else if("IMS".equals(netType)){
//            rtn = "[ 18:12:30 ] [ 消息内容 (IMS_PLX_X1 ==> NE) ][ NEID = HSS1 ]:\\n" +
//                    "协议版本号........[2]<02>\\n" +
//                    "消息类型..........[IMPI关联标识查询请求]\\n" +
//                    "目标标识(M).......[+8645185913005@hl.ims.chinaunicom.cn]<2b 38 36 34 35 31 38 35 39 31 33 30 30 35 40 68 6c 2e 69 6d 73 2e 63 68 69 6e 61 75 6e 69 63 6f 6d 2e 63 6e>\\n";
//        }
//        else{
//            rtn = "未知网络类型。";
//        }
//        return rtn;
//    }
//
//
//    private String getIMPIQueryAck(){
//        String rtn = "";
//        if("4G".equals(netType)){
//
//        }
//        else if("WWW".equals(netType)){
//
//        }
//        else if("IMS".equals(netType)){
//            rtn = "[ 18:12:31 ] [ 消息内容 (NE ==> IMS_PLX_X1) ][ NEID = HSS1 ]:\\n" +
//                    "协议版本号........[2]<02>\\n" +
//                    "消息类型..........[IMPI关联标识查询响应]\\n" +
//                    "目标标识(M).......[+8645185913005@hl.ims.chinaunicom.cn]<2b 38 36 34 35 31 38 35 39 31 33 30 30 35 40 68 6c 2e 69 6d 73 2e 63 68 69 6e 61 75 6e 69 63 6f 6d 2e 63 6e>\\n" +
//                    "操作结果(M).......[成功]<00>\\n" +
//                    "失败原因(C).......[Invalid]\\n" +
//                    "IMPU标识列表(C)...\\n" +
//                    "[\\n" +
//                    "  Num=[sip:+8645185913005@hl.ims.chinaunicom.cn]<73 69 70 3a 2b 38 36 34 35 31 38 35 39 31 33 30 30 35 40 68 6c 2e 69 6d 73 2e 63 68 69 6e 61 75 6e 69 63 6f 6d 2e 63 6e>\\n" +
//                    "  Num=[tel:+8645185913005]<74 65 6c 3a 2b 38 36 34 35 31 38 35 39 31 33 30 30 35>\\n" +
//                    "]本次列示目标数:2\\n";
//        }
//        else{
//            rtn = "未知网络类型。";
//        }
//        return rtn;
//    }
//
//
//    private String getUserInfoQueryReq(){
//        String rtn = "";
//        if("4G".equals(netType)){
//
//        }
//        else if("WWW".equals(netType)){
//            rtn = "[2018-05-25 10:12:19] AAA0 --- X1_QUREYUSERINFO_REQ --- 6\\n" +
//                    "目标类型:3\\n" +
//                    "目标标识:13803508714@wlan\\n" +
//                    "查询时间:(null)\\n";
//        }
//        else if("IMS".equals(netType)){
//
//        }
//        else{
//            rtn = "未知网络类型。";
//        }
//        return rtn;
//    }
//
//
//    private String getUserInfoQueryAck(){
//        String rtn = "";
//        if("4G".equals(netType)){
//
//        }
//        else if("WWW".equals(netType)){
//            rtn = "[2018-05-25 10:12:19] AAA0 --- X1_QUREYUSERINFO_ACK --- 7\\n" +
//                    "目标类型:3\\n" +
//                    "目标标识:13803508714@wlan\\n" +
//                    "成功标识:0\\n" +
//                    "失败原因:\\n" +
//                    "用户信息列表:\\n" +
//                    "0=用户状态:1\\n" +
//                    "接入获得的IP地址列表:\\n" +
//                    "\\n" +
//                    "结束标识:\\n" +
//                    "新NAI标识:13803508714@wlan\\n" +
//                    "电话号码:\\n" +
//                    "结束标识:\\n";
//        }
//        else if("IMS".equals(netType)){
//
//        }
//        else{
//            rtn = "未知网络类型。";
//        }
//        return rtn;
//    }
//
//
//
//}
