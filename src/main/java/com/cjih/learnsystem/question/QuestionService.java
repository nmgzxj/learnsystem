package com.cjih.learnsystem.question;




import com.jfinal.club.common.model.Question;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

/**
 * 试题管理
 * @author ZhangXianJin
 *
 */
public class QuestionService {
	
	public static final QuestionService me = new QuestionService();
	private Question dao = new Question().dao();
	
	public Page<Question> paginate(int pageNum) {
		return dao.paginate(pageNum, PropKit.getInt("pageSize"), "select *", "from tquestion order by id desc");
	}

	public Question findById(int id) {
		return dao.findById(id);
	}

	public Question edit(int id) {
		return dao.findById(id);
	}
	
	public Ret save(Question question) {
		question.save();
		return Ret.ok();
	}

	/**
	 * 修改试题数据
	 */
	public Ret update(Question question) {
		question.update();
		return Ret.ok("msg", "试题更新成功");
	}

	/**
	 * 删除试题数据
	 */
	public Ret delete(final int id) {
		Question question = dao.findById(id);
		if(question.delete()) {
			return Ret.ok("msg", "试题 删除成功");
		}
		else {
			return Ret.ok("msg", "试题 删除失败");
		}
	}

}

