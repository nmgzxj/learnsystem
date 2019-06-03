package com.cjih.learnsystem.basemsg;




import cn.jbolt.common.util.ArrayUtil;
import com.jfinal.club.common.model.BaseMsg;
import com.jfinal.kit.Kv;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

import java.util.Date;

/**
 * 基础消息管理
 * @author ZhangXianJin
 *
 */
public class BaseMsgService {
	
	public static final BaseMsgService me = new BaseMsgService();
	private BaseMsg dao = new BaseMsg().dao();
	
	public Page<BaseMsg> paginate(int pageNum, String query) {
		return dao.paginate(pageNum, PropKit.getInt("pageSize"), "select *", "from tbase_msg where 1=1 "
	+query+" order by title desc");
	}

	public BaseMsg findById(int baseMsgId) {
		return dao.findById(baseMsgId);
	}

	public BaseMsg edit(int id) {
		return dao.findById(id);
	}
	
	public Ret save(BaseMsg bm) {
		bm.save();
		return Ret.ok();
	}




	/**
	 * 修改BaseMsg数据
	 */
	public Ret update(BaseMsg baseMsg) {
		baseMsg.update();
		return Ret.ok("msg", "基础消息更新成功");
	}

	/**
	 * 得到回收站内数量
	 * @return
	 */
	public int getDeleteCount() {
		return Db.queryInt("select count(*) from tbase_msg  where is_delete=true");
	}

	/**
	 * 删除BaseMsg数据
	 */
	public Ret delete(final int id, int accountId) {
		BaseMsg bm = dao.findById(id);
		bm.setIsDelete(true);
		bm.setDeleteBy(accountId);
		bm.setDeleteTime(new Date());
		if(bm.update()) {
			return Ret.ok("msg", "基础消息 删除成功");
		}
		else {
			return Ret.ok("msg", "基础消息 删除失败");
		}
	}

	/**
	 * 还原BaseMsg数据
	 */
	public Ret restore(final int id) {
		BaseMsg bm = dao.findById(id);
		bm.setIsDelete(false);
		if(bm.update()) {
			return Ret.ok("msg", "基础消息 还原成功");
		}
		else {
			return Ret.ok("msg", "基础消息 还原失败");
		}
	}

	public void deleteIds(Integer ids[], int accountId){
		String idStr = ArrayUtil.join(ids,",");
		if(idStr!=null) {
			Db.delete("update tbase_msg set is_delete=true,delete_by=" + accountId + ",delete_time=now() where id in (0" + idStr + "0)");
		}
	}

	public void restoreIds(Integer ids[]){
		String idStr = ArrayUtil.join(ids,",");
		if(idStr!=null) {
			Db.delete("update tbase_msg set is_delete=false where id in (0" + idStr + "0)");
		}
	}

}
