package com.cjih.learnsystem.baseflow;




import com.jfinal.club.common.model.BaseFlow;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

/**
 * 基础流程管理
 * @author ZhangXianJin
 *
 */
public class BaseFlowService {
	
	public static final BaseFlowService me = new BaseFlowService();
	private BaseFlow dao = new BaseFlow().dao();
	
	public Page<BaseFlow> paginate(int pageNum) {
		return dao.paginate(pageNum, PropKit.getInt("pageSize"), "select *", "from tbase_flow order by id desc");
	}

	public BaseFlow findById(int id) {
		return dao.findById(id);
	}

	public BaseFlow edit(int id) {
		return dao.findById(id);
	}
	
	public Ret save(BaseFlow baseFlow) {
		baseFlow.save();
		return Ret.ok();
	}

	/**
	 * 修改基础流程数据
	 */
	public Ret update(BaseFlow baseFlow) {
		baseFlow.update();
		return Ret.ok("msg", "基础流程更新成功");
	}

	/**
	 * 删除基础流程数据
	 */
	public Ret delete(final int id) {
		BaseFlow baseFlow = dao.findById(id);
		if(baseFlow.delete()) {
			return Ret.ok("msg", "基础流程 删除成功");
		}
		else {
			return Ret.ok("msg", "基础流程 删除失败");
		}
	}

}


