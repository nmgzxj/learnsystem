package com.cjih.learnsystem.simulate;

import com.jfinal.club.common.controller.BaseController;
import com.jfinal.club.common.model.NumberUser;
import com.jfinal.kit.Ret;
import org.apache.log4j.Logger;

import java.util.List;

public class UserdbController extends BaseController {

    UserdbService srv = UserdbService.me;

    private static final Logger logger = Logger.getLogger(UnitController.class);

    public void index(){
        List<NumberUser> userList = NumberUser.dao.find("select * from tnumber_user ");
        setAttr("userdbList", userList);
        render("userdb_list.html");
    }

    public void save(){
        NumberUser nu = getBean(NumberUser.class,"userdb");
        Ret ret = srv.save(nu);
       // renderJson(ret);
        index();
    }

    public void update(){
        NumberUser nu = getBean(NumberUser.class,"userdb");
        Ret ret = srv.update(nu);
        index();
       // renderJson(ret);
    }

    public void delete(){
        Ret ret = srv.delete(getParaToLong("id"));
//        renderJson(ret);
        index();
    }
}
