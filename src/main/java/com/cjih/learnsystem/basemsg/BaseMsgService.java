package com.cjih.learnsystem.basemsg;




import cn.jbolt.common.util.ArrayUtil;
import com.jfinal.club.common.model.BaseMsg;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

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
	+query+"order by title desc");
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
	 * 删除BaseMsg数据
	 */
	public Ret delete(final int id) {
		BaseMsg bm = dao.findById(id);
		if(bm.delete()) {
			return Ret.ok("msg", "基础消息 删除成功");
		}
		else {
			return Ret.ok("msg", "基础消息 删除失败");
		}
	}

	public void deleteIds(Integer ids[]){
		Db.delete("delete from tbase_msg where id in (0"+ArrayUtil.join(ids,",")+"0)");
	}

}
