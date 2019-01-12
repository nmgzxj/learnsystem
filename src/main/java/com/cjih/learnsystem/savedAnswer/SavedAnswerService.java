package com.cjih.learnsystem.savedAnswer;




import com.jfinal.club.common.model.SavedAnswer;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

/**
 * 未提交试卷管理
 * @author ZhangXianJin
 *
 */
public class SavedAnswerService {
	
	public static final SavedAnswerService me = new SavedAnswerService();
	private SavedAnswer dao = new SavedAnswer().dao();
	
	public Page<SavedAnswer> paginate(int pageNum, int loginAccountId) {
		
		return dao.paginate(pageNum, PropKit.getInt("pageSize"), 
				"select exam_id,(select exam_name from texam where id=tanswer.exam_id) as exam_name,answer_no", 
				"from tanswer where user_id='"+loginAccountId+"' and answer_no not in (select answer_no from tsaved_answer) group by exam_id,answer_no ");
	}

	public SavedAnswer findById(int id) {
		return dao.findById(id);
	}

	public SavedAnswer edit(int id) {
		return dao.findById(id);
	}
	
	public Ret save(SavedAnswer savedAnswer) {
		savedAnswer.save();
		return Ret.ok();
	}

	/**
	 * 修改未提交试卷数据
	 */
	public Ret update(SavedAnswer savedAnswer) {
		savedAnswer.update();
		return Ret.ok("msg", "未提交试卷更新成功");
	}

	/**
	 * 删除未提交试卷数据
	 */
	public Ret delete(final int id) {
		SavedAnswer savedAnswer = dao.findById(id);
		if(savedAnswer.delete()) {
			return Ret.ok("msg", "未提交试卷 删除成功");
		}
		else {
			return Ret.ok("msg", "未提交试卷 删除失败");
		}
	}

}

