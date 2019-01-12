package com.cjih.learnsystem.answerlog;




import com.jfinal.club.common.model.AnswerLog;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

/**
 * 考试日志管理
 * @author ZhangXianJin
 *
 */
public class AnswerLogService {
	
	public static final AnswerLogService me = new AnswerLogService();
	private AnswerLog dao = new AnswerLog().dao();
	
	public Page<AnswerLog> paginate(int pageNum) {
		return dao.paginate(pageNum, PropKit.getInt("pageSize"), "select *,"
				+ "user_score/exam_score as user_percent,"
				+ "(select nickName from account where id=tanswer_log.account_id) as account_name,"
				+ "(select nickName from account where id=tanswer_log.official_id) as official_name ", "from tanswer_log order by id desc");
	}

	public AnswerLog findById(int id) {
		return dao.findById(id);
	}

	public AnswerLog edit(int id) {
		return dao.findById(id);
	}
	
	public Ret save(AnswerLog answerLog) {
		answerLog.save();
		return Ret.ok();
	}

	/**
	 * 修改考试日志数据
	 */
	public Ret update(AnswerLog answerLog) {
		answerLog.update();
		return Ret.ok("msg", "考试日志更新成功");
	}

	/**
	 * 删除考试日志数据
	 */
	public Ret delete(final int id) {
		AnswerLog answerLog = dao.findById(id);
		if(answerLog.delete()) {
			return Ret.ok("msg", "考试日志 删除成功");
		}
		else {
			return Ret.ok("msg", "考试日志 删除失败");
		}
	}

}

