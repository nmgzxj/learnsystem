package com.cjih.learnsystem.exam;

import java.util.Date;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.club.common.controller.BaseController;
import com.jfinal.club.common.model.Exam;
import com.jfinal.club.common.model.ExamQuestion;
import com.jfinal.club.common.model.ExamUser;
import com.jfinal.club.common.model.Question;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

public class ExamController extends BaseController{
	
ExamService srv = ExamService.me;
	
	public void index() {
		Page<Exam> examPage = srv.paginate(getParaToInt("p", 1));
		setAttr("examPage", examPage);
		render("index.html");
	}
	
	/**
	 * 修改
	 */
	public void edit() {
		setAttr("exam", srv.edit(getParaToInt("exam.id")));
		
	  Integer p = getParaToInt("p",1);

	  Page<Question> questionPage = srv.paginate_question(p, this);
	  setAttr("questionPage", questionPage);
	  setAttr("p",p);

		//已选问题列表
		  List<ExamQuestion> selectedQuestionList = ExamQuestion.dao.find("select * from texam_question where exam_id ="+getParaToInt("exam.id"));
		  setAttr("selectedQuestionIds", selectedQuestionList);
		  setAttr("propKit",PropKit.getProp());
		  keepPara();
		render("edit.html");
	}


	/**
	 * 提交修改
	 */
	@Before(ExamValidator.class)
	public void update() {
		Exam exam = getBean(Exam.class);
		exam.setExamName(getPara("exam.examName"));
		srv.update(exam);;
//		renderJson(ret);
		edit();
	}

//	public void savebak() {
//		Exam exam = getBean(Exam.class);
//		exam.setCreator(Integer.toString(getLoginAccountId()));
//		exam.setCreateTime(new Date());
//		Ret ret = srv.save(exam);
//		renderJson(ret);
//	}

	public void delete() {
		Ret ret = srv.delete(getParaToInt("exam.id"));
		renderJson(ret);
	}

	public void add() {
//      Long examId = getParaToLong("examId");
      Exam e = new Exam();
//      e.setId(examId);
      e.setExamName("未命名试卷");
      e.setIsReadonly("0");
      e.setScore(0);
      e.setCreator(Integer.toString(getLoginAccountId()));
      e.setCreateTime(new Date());
      e.save();//.insert(e);
      setAttr("exam",e);
      render("add.html");
	}
	
	  public void save(){
	      Long examId = getParaToLong("exam.id");

	      Exam e = Exam.dao.findById(examId);
	      e.setExamName(getPara("exam.examName"));
	      e.setScore(0);
	      e.setScore(countScore(examId));
	      e.update();

	      Page<Question> questionPage = Question.dao.paginate(1, PropKit.getInt("pageSize"), "select * ", " from tquestion where isPassed='1'");
		  setAttr("questionPage", questionPage);
		  setAttr("exam", e);

	      render("edit.html");
	  }

  /*
  向试卷中增加当前题目
   */
//  @RequestMapping("/add_save")
//  @ResponseBody
  public void add_save(){
      Long examId = getParaToLong("exam.id");
      Long question_id = getParaToLong("question_id");
      if(null != question_id ){
          ExamQuestion eq = new ExamQuestion();
//          eq.setId(UUID.randomUUID().toString());
          eq.setExamId(examId.intValue());
          eq.setQuestionId(question_id);
          eq.save();
      }
      Exam e = Exam.dao.findById(examId);
      e.setScore(countScore(examId));
      e.update();

      Page<Question> questionPage = Question.dao.paginate(1, PropKit.getInt("pageSize"), "select * ", " from tquestion where isPassed='1'");
	  setAttr("questionPage", questionPage);
	  setAttr("exam", e);

      render("edit.html");

  }

  /*
  从试卷中删除当前题目
   */
  public void delete_save(){
      Long examId = getParaToLong("exam.id");
      Long question_id = getParaToLong("question_id");
      Db.update("delete from texam_question\n" + 
      		"    where exam_id = '"+examId+"' and question_id = '"+question_id+"'");
      Exam e = Exam.dao.findById(examId);
      e.setScore(countScore(examId));
      e.update();
      setAttr("message", "题目删除成功");
      setAttr("status", "success");
      // setAttr("examList",examService.getExamList());
      renderJson();// json.toJSONString();
  }

public Integer countScore(Long examId) {
	Integer rtn = 0;
	List<Question> list = Question.dao.find("select * from tquestion where id in (select question_id from texam_question  where exam_id = '"+examId+"')");
	for(Question eq:list) {
		if("radio".equals(eq.getQuestionType())) {
			rtn += PropKit.getInt("radio_score");
		}
		else if("checkbox".equals(eq.getQuestionType())) {
			rtn += PropKit.getInt("checkbox_score");
		}
		else if("text".equals(eq.getQuestionType())) {
			rtn += PropKit.getInt("text_score");
		}
		else if("bigtext".equals(eq.getQuestionType())) {
			rtn += PropKit.getInt("bigtext_score");
		}
		else if("flow".equals(eq.getQuestionType())) {
			rtn += PropKit.getInt("flow_score");
		}
	}
	return rtn;
}
/**
* 更新试卷中的问题
*/

public void update_question() {
		Long examId = getParaToLong("exam.id");
		Long ids[] = getParaValuesToLong("id");
		String searchKW = getPara("searchKW");
		//找到当前页中，属于当前试卷的试题，清除
		Page<Question> questionPage = srv.paginate_question(getParaToInt("p"),this);
		List<ExamQuestion> eqList = ExamQuestion.dao.find("select * from texam_question where exam_id = "+examId);
		for(ExamQuestion eq:eqList) {
			if(getParaToInt("exam.id") == eq.getExamId()) {
				for(Question q:questionPage.getList()) {
					if(q.getId().equals(eq.getQuestionId())) {
						System.out.println(eq.getQuestionId()+" deleted."+eq.getId()+" "+eq.getExamId());
						eq.delete();
					}
				}
			}
			
		}

		ExamQuestion eq = new ExamQuestion();
		if(examId!=null&&ids!=null&&ids.length>0){
	     	for(Long id:ids) {
	         eq = new ExamQuestion();
	//         eq.setId(UUID.randomUUID().toString());
	         eq.setExamId(examId.intValue());
	         eq.setQuestionId(id);
	         eq.save();
     	}
	 }
	 Exam e = Exam.dao.findById(examId);
	 e.setScore(countScore(examId));
	 e.update();
	redirect("/admin/exam/edit?exam.id="+examId+"&p="+getPara("p"));
}

/**
 * 预览试卷
 */
public void preview(){
	setAttr("exam", srv.edit(getParaToInt("exam.id")));

	List<Question> questionList = Question.dao.find("select * from tquestion where id in (select question_id from texam_question where exam_id ="+
			getParaToInt("exam.id")+")");
	setAttr("questionList", questionList);

	setAttr("propKit",PropKit.getProp());
	keepPara();
	render("preview.html");
}

/**
 * 复制试卷
 */
public void copy() {
	Exam e = Exam.dao.findById(getPara("exam.id"));
	Exam ne = new Exam();
	ne.setExamName("copy "+e.getExamName());
	ne.setIsReadonly("0");
	ne.setScore(e.getScore());
	ne.setCreator(Integer.toString(getLoginAccountId()));
	ne.setCreateTime(new Date());
	ne.save();
	Db.update("insert into texam_question (exam_id,question_id) select "+ne.getId()+",question_id from texam_question where exam_id="+e.getId());
	index();
}

/**
 * 试卷审核通过
 */
	public void setReadonly() {
		Db.update("update texam set is_readonly='1' where id = '"+getPara("exam.id")+"'");
		index();
	}
	
	public void allocate() {
		Exam e = Exam.dao.findById(getPara("exam.id"));
		String sql = "select * from texam_user where exam_id="+e.getId();
		List<ExamUser> examUserList = ExamUser.dao.find(sql);
		setAttr("exam", e);
		setAttr("examUserList", examUserList);
		//角色为学员的用户列表
		setAttr("userList", Db.find("select * from  account where id in (select accountId from account_role where roleId in ('4','5'))"));
		render("allocate.html");
	}

	public void exam_allocate_save() {
		Exam e = Exam.dao.findById(getPara("exam.id"));
		Integer[] users = getParaValuesToInt("userId");
		Db.update("delete from texam_user where exam_id="+e.getId());
		ExamUser eu;
		for(Integer user:users) {
			eu  = new ExamUser();
			eu.setExamId(e.getId().intValue());
			eu.setUserId(user);
			eu.setCreator(Integer.toString(getLoginAccountId()));
			eu.setCreateTime(new Date());
			eu.save();
		}
		allocate();
	}



}


