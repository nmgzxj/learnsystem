package com.cjih.learnsystem.savedAnswer;

import com.jfinal.club.common.model.SavedAnswer;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

/**
 * 判卷试卷管理
 * @author ZhangXianJin
 *
 */
public class JudgeAnswerService {
	
	public static final JudgeAnswerService me = new JudgeAnswerService();
	private SavedAnswer dao = new SavedAnswer().dao();
	
	public Page<SavedAnswer> paginate(int pageNum) {
		return dao.paginate(pageNum, PropKit.getInt("pageSize"), 
				"select user_id, exam_id,(select exam_name from texam where id=tsaved_answer.exam_id) as exam_name, "
				+ "answer_no," + 
				"(select user_score/exam_score from tanswer_log where answer_no=tsaved_answer.answer_no)as user_percent," +
						"(select user_score from tanswer_log where answer_no=tsaved_answer.answer_no)as user_score," +
						"(select exam_score from tanswer_log where answer_no=tsaved_answer.answer_no)as exam_score," +
						"(select judge_time from tanswer_log where answer_no=tsaved_answer.answer_no)as judge_time," +
				"(select nickName from account where id=(select official_id from tanswer_log where answer_no=tsaved_answer.answer_no))as official_name "," from tsaved_answer group by answer_no,user_id,exam_id ");
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
	 * 试卷数据
	 */
	public Ret update(SavedAnswer savedAnswer) {
		savedAnswer.update();
		return Ret.ok("msg", "未提交试卷更新成功");
	}



}

