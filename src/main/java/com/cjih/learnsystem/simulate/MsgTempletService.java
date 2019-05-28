package com.cjih.learnsystem.simulate;

import com.jfinal.club.common.model.MsgTemplet;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;

public class MsgTempletService {

    public static final MsgTempletService me = new MsgTempletService();

    public Page<MsgTemplet> paginate(int page, String keywords){
        Integer pageSize = PropKit.getInt("pageSize");
        String selectSql = "select *";
        String fromSql = " from tmsg_templet where 1=1 ";
        if(StrKit.notBlank(keywords)) {
            fromSql += " and templet_name like '%"+keywords+"%'";
        }

        return MsgTemplet.dao.paginate(page, pageSize, selectSql, fromSql);
    }
}
