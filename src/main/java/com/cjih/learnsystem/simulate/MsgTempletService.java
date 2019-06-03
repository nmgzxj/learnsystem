package com.cjih.learnsystem.simulate;

import cn.jbolt.common.util.ArrayUtil;
import com.jfinal.club.common.model.BaseMsg;
import com.jfinal.club.common.model.MsgTemplet;
import com.jfinal.club.common.model.MsgTempletSub;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

import java.util.Date;

public class MsgTempletService {

    public static final MsgTempletService me = new MsgTempletService();

    public Page<MsgTemplet> paginate(int page, String keywords, boolean isDelete){
        Integer pageSize = PropKit.getInt("pageSize");
        String selectSql = "select *";
        String fromSql = " from tmsg_templet where 1=1 ";
        if(StrKit.notBlank(keywords)) {
            fromSql += " and templet_name like '%"+keywords+"%'";
        }
        fromSql += " and is_delete = "+isDelete;
        return MsgTemplet.dao.paginate(page, pageSize, selectSql, fromSql);
    }

    /**
     * 得到回收站内数量
     * @return
     */
    public int getDeleteCount() {
        return Db.queryInt("select count(*) from tmsg_templet  where is_delete=true");
    }

    /**
     * 删除数据
     */
    public Ret delete(final int id, int accountId) {
        MsgTemplet bm = MsgTemplet.dao.findById(id);
        bm.setIsDelete(true);
        bm.setDeleteBy(accountId);
        bm.setDeleteTime(new Date());
        if(bm.update()) {
            return Ret.ok("msg", "基础流程 删除成功");
        }
        else {
            return Ret.ok("msg", "基础流程 删除失败");
        }
    }

    /**
     * 还原数据
     */
    public Ret restore(final int id) {
        MsgTemplet bm = MsgTemplet.dao.findById(id);
        bm.setIsDelete(false);
        if(bm.update()) {
            return Ret.ok("msg", "基础流程 还原成功");
        }
        else {
            return Ret.ok("msg", "基础流程 还原失败");
        }
    }

    public void deleteIds(Integer[] ids, Integer accountId ) {
        String idStr = ArrayUtil.join(ids,",");
        if(idStr!=null) {
            //Db.delete("update tmsg_templet_sub where templet_id in (0"+ ArrayUtil.join(ids,",")+"0)");
            Db.update("update tmsg_templet set is_delete=true,delete_by=" + accountId + ",delete_time=now() where id in (0" + ArrayUtil.join(ids, ",") + "0)");
        }
    }

    public void restoreIds(Integer[] ids){
        String idStr = ArrayUtil.join(ids,",");
        if(idStr!=null) {
            Db.update("update tmsg_templet set is_delete=false where id in (0" + idStr + "0)");
        }
    }

    /**
     * 删除数据
     */
    public Ret deleteMsg(final int id, int accountId) {
        MsgTempletSub bm = MsgTempletSub.dao.findById(id);
        bm.setIsDelete(true);
        bm.setDeleteBy(accountId);
        bm.setDeleteTime(new Date());
        if(bm.update()) {
            return Ret.ok("msg", "基础流程消息 删除成功");
        }
        else {
            return Ret.ok("msg", "基础流程消息 删除失败");
        }
    }

    /**
     * 还原数据
     */
    public Ret restoreMsg(final int id) {
        MsgTempletSub bm = MsgTempletSub.dao.findById(id);
        bm.setIsDelete(false);
        if(bm.update()) {
            return Ret.ok("msg", "基础流程消息 还原成功");
        }
        else {
            return Ret.ok("msg", "基础流程消息 还原失败");
        }
    }

    public void deleteMsgIds(Integer[] ids, Integer accountId ) {
        String idStr = ArrayUtil.join(ids,",");
        if(idStr!=null) {
            Db.update("update tmsg_templet_sub set is_delete=true,delete_by=" + accountId + ",delete_time=now() where id in (0" + ArrayUtil.join(ids, ",") + "0)");
        }
    }

    public void restoreMsgIds(Integer[] ids){
        String idStr = ArrayUtil.join(ids,",");
        if(idStr!=null) {
            Db.update("update tmsg_templet_sub set is_delete=false where id in (0" + idStr + "0)");
        }
    }
}
