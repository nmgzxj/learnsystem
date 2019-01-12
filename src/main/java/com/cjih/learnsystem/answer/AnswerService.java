package com.cjih.learnsystem.answer;




import com.jfinal.club.common.model.Answer;
import com.jfinal.club.common.model.Exam;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

/**
 * 试卷管理
 * @author ZhangXianJin
 *
 */
public class AnswerService {
	
	public static final AnswerService me = new AnswerService();
	private Answer dao = new Answer().dao();
	
	public Page<Exam> paginate(int pageNum, int accountId) {
		return Exam.dao.paginate(pageNum, PropKit.getInt("pageSize"), "select *", "from texam where is_readonly = '1' "
				+ "and id in (select exam_id from texam_user where user_id = '"+accountId+"')");
	}

	public Answer findById(int id) {
		return dao.findById(id);
	}

	public Answer edit(int id) {
		return dao.findById(id);
	}
	
	public Ret save(Answer answer) {
		answer.save();
		return Ret.ok();
	}

	/**
	 * 修改试卷数据
	 */
	public Ret update(Answer answer) {
		answer.update();
		return Ret.ok("msg", "试卷更新成功");
	}

	/**
	 * 删除试卷数据
	 */
	public Ret delete(final int id) {
		Answer answer = dao.findById(id);
		if(answer.delete()) {
			return Ret.ok("msg", "试卷 删除成功");
		}
		else {
			return Ret.ok("msg", "试卷 删除失败");
		}
	}

}

