package com.cjih.learnsystem.simulate;

import com.jfinal.club.common.controller.BaseController;
import com.jfinal.club.common.model.NumberUser;
import com.jfinal.club.common.model.NumberUserSub;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import org.apache.log4j.Logger;

import java.util.List;

public class NumberUserController extends BaseController {

    NumberUserService srv = NumberUserService.me;

    private static final Logger logger = Logger.getLogger(UnitController.class);

    public void index(){

        NumberUser userdb = getBean(NumberUser.class);
        String sql = " from tnumber_user where 1=1 ";

        if(StrKit.notBlank(userdb.getUserName())){
            sql += " and user_name like '%"+userdb.getUserName()+"%' ";
        }
        if(StrKit.notBlank(userdb.getMsisdn())){
            sql += " and msisdn like '%"+userdb.getMsisdn()+"%'";
        }
        if(StrKit.notBlank(userdb.getImsi())){
            sql += " and imsi like '"+ userdb.getImsi()+"'";
        }
        if(StrKit.notBlank(userdb.getImei())){
            sql += " and imei like '%"+userdb.getImei()+"%'";
        }
        if(StrKit.notBlank(userdb.getIp())){
            sql += " and ip like '%"+userdb.getIp()+"%'";
        }
        if(StrKit.notBlank(userdb.getIpTime())){
            sql += " and ip_time like '%"+userdb.getIpTime()+"%'";
        }
        if(StrKit.notBlank(userdb.getIpPort())){
            sql += " and ip_port like '%"+userdb.getIpPort()+"%'";
        }
        if(StrKit.notBlank(userdb.getIpAddressField())){
            sql += " and ip_address_field like '%"+userdb.getIpAddressField()+"%'";
        }
        if(StrKit.notBlank(userdb.getNai())){
            sql += " and nai like '%"+userdb.getNai()+"%'";
        }
        if(StrKit.notBlank(userdb.getPhoneNumber())){
            sql += " and phone_number like '%"+userdb.getPhoneNumber()+"%'";
        }
        if(StrKit.notBlank(userdb.getImpi())){
            sql += " and impi like '%"+userdb.getImpi()+"%'";
        }
        if(StrKit.notBlank(userdb.getTel())){
            sql += " and id in (select number_user_id from tnumber_user_sub where impu_type='tel' and impu like '%"+userdb.getTel()+"%')";
        }
        if(StrKit.notBlank(userdb.getImei())){
            sql += " and id in (select number_user_id from tnumber_user_sub where impu_type='sip' and impu like '%"+userdb.getIp()+"%')";
        }
        setAttr("userdb",userdb);

        Page<NumberUser> userList = NumberUser.dao.paginate(getParaToInt("p", 1), PropKit.getInt("pageSize"),"select * ",sql);
        setAttr("userName", getPara("userName")==null?"":getPara("userName"));
//        setAttr("msisdn", getPara("msisdn")==null?"":getPara("msisdn"));
//        setAttr("imsi", getPara("imsi")==null?"":getPara("imsi"));
//        setAttr("imei", getPara("imei")==null?"":getPara("imei"));
//        setAttr("ip", getPara("ip")==null?"":getPara("ip"));
//        setAttr("ipTime", getPara("ipTime")==null?"":getPara("ipTime"));
//        setAttr("ipPort", getPara("ipPort")==null?"":getPara("ipPort"));
//        setAttr("ipAddressField", getPara("ipAddressField")==null?"":getPara("ipAddressField"));
//        setAttr("nai", getPara("nai")==null?"":getPara("nai"));
//        setAttr("phoneNumber", getPara("phoneNumber")==null?"":getPara("phoneNumber"));
//        setAttr("impi", getPara("impi")==null?"":getPara("impi"));
//        setAttr("tel", getPara("tel")==null?"":getPara("tel"));
//        setAttr("sip", getPara("sip")==null?"":getPara("sip"));

        setAttr("numberUserPage", userList);
        render("numberUser_list.html");
    }

    public void save(){
        NumberUser nu = getBean(NumberUser.class);
        Ret ret = srv.save(nu);
       // renderJson(ret);
       // index();
        redirect("/admin/numberUser");
    }


    public void update(){
        NumberUser nu = getBean(NumberUser.class);
        Ret ret = srv.update(nu);
        //index();
        redirect("/admin/numberUser");
       // renderJson(ret);
    }

    public void delete(){
        Ret ret = srv.delete(getParaToLong("id"));
//        renderJson(ret);
        index();
    }

    public void add(){
        render("numberUser_add.html");
    }

    public void edit(){
        NumberUser numberUser = srv.findById(getParaToInt("id"));
        setAttr("numberUser",numberUser);
        setAttr("numberUserSubList", NumberUserSub.dao.find("select * from tnumber_user_sub where number_user_id="+numberUser.getId()));
        render("numberUser_edit.html");
    }

    public void subSave(){
        NumberUserSub nus = getBean(NumberUserSub.class);
        Ret ret = srv.subSave(nus);
        renderJson(ret);

    }

    public void subDelete(){
        NumberUserSub nus = getBean(NumberUserSub.class);
        Ret ret = srv.subDelete(nus.getId());
        renderJson(ret);
//        List<Record> numberUserSubList = Db.query("select impu_type,impu from tnumber_user_sub where number_user_id = "+nus.getNumberUserId());
//        setAttr("comment",numberUserSubList);
//        renderJson();
    }
}
