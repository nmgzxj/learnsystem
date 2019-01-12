package com.cjih.learnsystem.exam;




import com.jfinal.club.common.model.Exam;
import com.jfinal.club.common.model.ExamQuestion;
import com.jfinal.club.common.model.Question;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;

/**
 * 试卷管理
 * @author ZhangXianJin
 *
 */
public class ExamService {
	
	public static final ExamService me = new ExamService();
	private Exam dao = new Exam().dao();
	
	public Page<Exam> paginate(int pageNum) {
		return dao.paginate(pageNum, PropKit.getInt("pageSize"), "select *", "from texam order by id desc");
	}

	public Page<Question> paginate_question(int pageNum, String searchKW) {
		String from_sql = "from tquestion where isPassed='1' ";
		if(StrKit.notBlank(searchKW)) {
			pageNum = 1;
			from_sql += " and (keywords like '%"+searchKW+"%' or questionTitle like '%"+searchKW+"%' or questionId like '%"+searchKW+"%' ) ";
		} 
//		from_sql += " order by id desc";
		return Question.dao.paginate(pageNum, PropKit.getInt("pageSize"), "select *", from_sql);
	}

	public Exam findById(int id) {
		return dao.findById(id);
	}

	public Exam edit(int id) {
		return dao.findById(id);
	}
	
	public Ret save(Exam exam) {
		exam.save();
		return Ret.ok();
	}

	/**
	 * 修改试卷数据
	 */
	public Ret update(Exam exam) {
		exam.update();
		return Ret.ok("msg", "试卷更新成功");
	}

	/**
	 * 删除试卷数据
	 */
	public Ret delete(final int id) {
		Exam exam = dao.findById(id);
		if(exam.delete()) {
			return Ret.ok("msg", "试卷 删除成功");
		}
		else {
			return Ret.ok("msg", "试卷 删除失败");
		}
	}

}

