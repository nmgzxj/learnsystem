package com.cjih.learnsystem.question;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.cjih.learnsystem.common.util.DateKit;
import com.cjih.learnsystem.common.util.Encrypt;
import com.jfinal.aop.Before;
import com.jfinal.club.common.controller.BaseController;
import com.jfinal.club.common.model.BaseFlow;
import com.jfinal.club.common.model.Question;
import com.jfinal.club.common.model.QuestionFlow;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

public class QuestionController extends BaseController{
	
	QuestionService srv = QuestionService.me;
	
	public void index() {
		Page<Question> questionPage = srv.paginate(getParaToInt("p", 1));
		setAttr("questionPage", questionPage);
		setAttr("p", getPara("p"));
		render("index.html");
	}
	
	/**
	 * 修改
	 */
	public void edit() {
		setAttr("question", srv.edit(getParaToInt("id")));
	  Question q = Question.dao.findById(getParaToInt("id"));
		//加密
		q.setQuestionTitle(Encrypt.decode(q.getQuestionTitle()));
		q.setQuestionItems(Encrypt.decode(q.getQuestionItems()));
		q.setQuestionAnswer(Encrypt.decode(q.getQuestionAnswer()));
		setAttr("question", q);
		setAttr("p", getPara("p"));
	  if(q.getQuestionItems() != null) {
	  		setAttr("items", q.getQuestionItems().split(";"));
	  }
	  setAttr("flows", QuestionFlow.dao.find("select * from tquestion_flow where relationId='"+q.getRelationId()+"'"));
		render("edit.html");
	}

	/**
	 * 提交修改
	 */
	@Before(QuestionValidator.class)
	public void update() {
		Question question = getBean(Question.class);
		String isTrue = getPara("isTrue");
		//选择题才有选项问题
		if("radio".equals(question.getQuestionType())||"checkbox".equals(question.getQuestionType())) {
			//添加选项
			if(StrKit.isBlank(question.getQuestionItems())) {
				question.setQuestionItems(getPara("question.item"));
			}
			else {
				question.setQuestionItems(question.getQuestionItems()+";"+getPara("question.item"));
			}
			//当前答案是正确答案
			if("yes".equals(isTrue)) {
				if("radio".equals(question.getQuestionType())){
					question.setQuestionAnswer(getPara("question.item"));
				}
				else if("checkbox".equals(question.getQuestionType())) {
					if(StrKit.isBlank(question.getQuestionAnswer())) {
						question.setQuestionAnswer(getPara("question.item"));
					}
					else {
						question.setQuestionAnswer(question.getQuestionAnswer()+";"+getPara("question.item"));
					}
				}
			}

		//删除选项
		String del_item = getPara("del_item");
		String items[] = question.getQuestionItems().split(";");
		String temp_str = "";
		for(int i=0;i<items.length;i++) {
			if(!del_item.equals(items[i])) {
				temp_str += items[i];
				if(i!=items.length-1) {
					temp_str += ";";
				}
			}
			
		}
		//删除选项后处理答案
		if(StrKit.notBlank(question.getQuestionAnswer())) {
		String answers[] = question.getQuestionAnswer().split(";");
		String temp_answers = "";
		for(int i=0;i<answers.length;i++) {
			if(!del_item.equals(answers[i])) {
				temp_answers += answers[i];
				if(i!=answers.length-1) {
					temp_answers += ";";
				}
			}
		}
		question.setQuestionAnswer(temp_answers);
		}
		question.setQuestionItems(temp_str);
		}
		question.setModifyBy(Integer.toString(getLoginAccountId()));
		question.setModifyTime(new Date());
		//加密
		question.setQuestionTitle(Encrypt.encode(question.getQuestionTitle()));
		question.setQuestionItems(Encrypt.encode(question.getQuestionItems()));
		question.setQuestionAnswer(Encrypt.encode(question.getQuestionAnswer()));
		srv.update(question);
		//renderJson(ret);
		edit();
	}

	public void save() {
		Question question = getBean(Question.class);
		question.setRelationId(UUID.randomUUID().toString());
		question.setCreateBy(Integer.toString(getLoginAccountId()));
		question.setCreateTime(new Date());
		//加密
		question.setQuestionTitle(Encrypt.encode(question.getQuestionTitle()));
		question.setQuestionItems(Encrypt.encode(question.getQuestionItems()));
		question.setQuestionAnswer(Encrypt.encode(question.getQuestionAnswer()));
		
		Ret ret = srv.save(question);
		renderJson(ret);
	}

	public void delete() {
		Db.update("delete from tquestion_flow where question_id="+getParaToInt("id"));
		Ret ret = srv.delete(getParaToInt("id"));
		renderJson(ret);
	}

	public void add() {
		Question question = getBean(Question.class);
		question.setQuestionType(getPara("questionType"));
		question.setRelationId(UUID.randomUUID().toString());
		question.setIsPassed("0");
		question.setCreateBy(Integer.toString(getLoginAccountId()));
		//加密
		question.setQuestionTitle(Encrypt.encode(question.getQuestionTitle()));
		question.setQuestionItems(Encrypt.encode(question.getQuestionItems()));
		question.setQuestionAnswer(Encrypt.encode(question.getQuestionAnswer()));
		srv.save(question);
		setAttr("question", Question.dao.findById(question.getId()));
		render("edit.html");
	}
	
	public void question_audit_pass() {
		Db.update("update tquestion set isPassed='1' where id="+getPara("id"));
		setAttr("p", getPara("p"));
		index();
	}
	
	public void question_audit_nopass() {
		Db.update("update tquestion set isPassed='-1' where id="+getPara("id"));
		setAttr("p", getPara("p"));
		index();
	}

	public void flowmsg_edit(){
		QuestionFlow flowmsg = QuestionFlow.dao.findById(getPara("flowMsgId"));
		flowmsg.setMsg(Encrypt.decode(flowmsg.getMsg()));
		setAttr("sub", flowmsg);
		setAttr("p", getPara("p"));
		render("/_view/question/flowmsg_edit.html");
	}
	
	public void flowmsg_edit_readonly(){
		QuestionFlow flowmsg = QuestionFlow.dao.findById(getPara("flowMsgId"));
		flowmsg.setMsg(Encrypt.decode(flowmsg.getMsg()));
		setAttr("sub", flowmsg);
		setAttr("p", getPara("p"));
		render("/_view/question/flowmsg_readonly.html");
	}

	public void flow_list_choice() {
		Integer page = getParaToInt("page");
		if(page==null || page < 0) {
			page = 1;
		}
		Integer pageSize = PropKit.getInt("pageSize");
		String selectSql = "select *";
		String fromSql = " from tbase_flow ";
		Page<BaseFlow> flows = BaseFlow.dao.paginate(page,pageSize,selectSql, fromSql);
		setAttr("flowList", flows.getList());
	    keepPara();
	    setAttr("p", getPara("p"));
		render("flow_list_choice.html");
	}
	
	public void flowmsg_edit_save(){
		QuestionFlow qf = QuestionFlow.dao.findById(getPara("flowMsgId"));
		qf.setTitle(getPara("title"));
		qf.setMsg(Encrypt.encode(getPara("msg")));
		
		qf.setModifyBy(Integer.toString(getLoginAccountId()));
		qf.setModifyTime(new Date());
		qf.update();
		setAttr("questionFlow", QuestionFlow.dao.findById(getPara("flowMsgId")));
		setAttr("p", getPara("p"));
		renderText("消息编辑完成，请刷新父页面。");
	}
	
	public void flowmsg_delete() {
		QuestionFlow qf = QuestionFlow.dao.findById(getPara("flowMsgId"));
		if(qf.delete()) {
			renderJson(Ret.ok("msg", "流程删除成功"));
		}
		else {
			renderJson(Ret.fail("msg", "流程删除失败。"));
		}
	}


	public void flow_edit_choice() {
		String flowId = getPara("flow.id");
		if(StrKit.isBlank(flowId)) {
			flowId = getPara("flowId");
		}
		BaseFlow bf = BaseFlow.dao.findById(flowId);
		setAttr("baseFlow", bf);
		
		String sql = "select * from tbase_flow_msg where flow_id ="
		+ flowId + " order by taxis";
		List<Record> flowMsgs = Db.find(sql);
		setAttr("questionId", getPara("questionId"));
		setAttr("flowMsgs", flowMsgs);
		setAttr("p", getPara("p"));
		render("flow_edit_choice.html");
	}
	
	      public void add_msg_to_question() {
		  	String[] ids = getParaValues("flowMsgId");
		  	String questionId = getPara("questionId");
		  	String relationId = Question.dao.findById(questionId).getRelationId();
		  	setAttr("p", getPara("p"));
		  	if(ids==null) {
		  		renderText("未能获得flowMsgId");
		  	}
		  	else {
	    	  	for(int i=0; i<ids.length; i++) {
	    	  		Db.update("insert into tquestion_flow (question_id,relationId,title,msg,port,taxis,creator,create_time) "
	    	  				+ "select '"+questionId+"','"+relationId+"',title,msg,port,'"+i+"','"+getLoginAccountId()+"',now() from tbase_flow_msg where id = "+ids[i]);
	    	  	}
	    	  	renderText("添加完毕，请关闭当前窗口，刷新父窗口。");
		  	}
	      }



//package com.cjih.learnsystem.question;
//
//import com.cjih.learnsystem.common.util.PageUtil;
//import com.jfinal.club.common.model.Question;
//import com.jfinal.club.common.model.QuestionFlow;
//import com.jfinal.core.Controller;
//import com.jfinal.ext.kit.DateKit;
//import com.jfinal.kit.PathKit;
//import com.jfinal.kit.PropKit;
//import com.jfinal.kit.StrKit;
//import com.jfinal.plugin.activerecord.Db;
//import com.jfinal.plugin.activerecord.Page;
//import com.jfinal.upload.UploadFile;
//
//import java.io.BufferedOutputStream;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.util.Date;
//import java.util.List;
//
///**
// * Created by ZhangXianjin on 2017/7/10.
// */
//public class QuestionController extends Controller{
//
//	static Question dao = new Question().dao();
//	
//    public void index(){
//		Integer page = getParaToInt("page");
//		String orderBy = getPara("orderBy");
//		if(page==null||page<0) {
//			page = 1;
//		}
//		Integer pageSize = PropKit.getInt("pageSize");
//	    	String selectSql = "select *";
//	    	String whereSql = " from tquestion where 1=1";
//	    	String searchKW = getPara("searchKW", "");
//	    	if(null!=searchKW&& !"".equals(searchKW)) {
//	    		whereSql += " and keywords||questionTitle||questionId like '%"+searchKW+"%'";
//	    	}
//	    	if(StrKit.isBlank(orderBy)) {
//	    		whereSql += " order by id desc";
//	    	}
//	    	else {
//	    		whereSql += " order by "+orderBy;
//	    	}
//	    	String url = "/question/question_list?searchKW="+searchKW+"&page=";
//		Page<Question> questions = dao.paginate(page, pageSize, selectSql, whereSql);
//		String rtn = PageUtil.getPageString(url, page, questions.getTotalPage());
//		setAttr("questions", questions);
//	    setAttr("questionList", questions.getList());
//	    setAttr("pageString", rtn);
//	    render("/_view/question/index.html");
//    }
//
//
//   
//
////    @RequestMapping("/answerpage")
////    public String getQuestion(HttpServletRequest request){
////        List<Question> questions = questionService.getQuestionList();
////        String page = getPara("page");
////        if(page==null && "".equals(page)){
////            page = "0";
////        }
////        setAttr("question_size",questions.size());
////        setAttr("question",questions.get(Integer.valueOf(page).intValue()));
////        return "/learn/answerpage";
////    }
//
//    public void edit(){
//        String id = getPara("id");
//        Question q = dao.findById(id);
//        setAttr("question", q);
//        if(q.getQuestionItems() != null) {
//        		setAttr("items", q.getQuestionItems().split(";"));
//        }
//        setAttr("flows", QuestionFlow.dao.find("select * from tquestion_flow where question_id="+id));
//        render("/_view/question/edit.html");
//    }
//
//    public void question_delete(){
//        String id = getPara("id");
//        dao.deleteById(id);
//        index();
//    }
//
//    public void add(){
//    		Question q = new Question();
//    		q.setQuestionType(getPara("questionType"));
//    		q.setIsPassed("0");
//    		q.save();
//    		setAttr("question", dao.findById(q.getId()));
//        render("/_view/edit.html");
//    }
//
//
//
//    public void edit_save(){
//        Question q = new Question();
//        q.setId(getParaToLong("id"));
//        q.setQuestionId(getPara("questionId"));
//        q.setQuestionTitle(getPara("questionTitle"));
////        q.setQuestionType(getPara("questionType"));
//        q.setQuestionItems(getPara("questionItems"));
//        q.setQuestionAnswer(getPara("questionAnswer"));
//        q.update();
//        edit();
//    }
//
//  
//    public void add_save(){
//        Question q = new Question();
////        q.setId(getParaToLong("id"));
//        q.setQuestionId(getPara("questionId"));
//        q.setQuestionTitle(getPara("questionTitle"));
//        q.setQuestionType(getPara("questionType"));
//        q.setQuestionItems(getPara("questionItems"));
//        q.setQuestionAnswer(getPara("questionAnswer"));
//        q.save();
//        index();
//    }
//
////    public void index() {
////    		Page<Question> questionPage = dao.paginate(getParaToInt("p", 1), 100, "select *", "from tquestion order by id desc");
////		setAttr("questionPage", questionPage);
////    		setAttr("questionList",Question.dao.find("select * from tquestion"));
////        renderJsp("/learn/question_list.jsp");
////    }
//    
//	public static void main(String[] args) {
//		int pageNumber = 5;
//		String searchKW = "aaa";
//		StringBuffer rtn = new StringBuffer("<div>\n");
//		rtn.append("    <ul class=\"am-pagination am-pagination-centered\">\n");
//		if (pageNumber == 1) {
//			rtn.append("        <li class=\"am-disabled\">");
//		} else {
//			rtn.append("        <li>");
//		}
//		rtn.append("<a href=\"/question/question_list?page=");
//		rtn.append(String.valueOf(pageNumber - 1));
//		rtn.append("&searchKW=");
//		rtn.append(searchKW);
//		rtn.append("\">上一页</a ></li>\n");
//		for (int i = 1; i <= 16; i++) {
//			if (i == pageNumber) {
//				rtn.append("        <li class=\"am-active\"><a href=\"/question/question_list?page=");
//				rtn.append(i);
//				rtn.append("&searchKW=" + searchKW + "\">" + i + "</a></li>\n");
//			} else {
//				if (i > 3 && i < pageNumber) {
//					rtn.append("        <li class=\"am-disabled\"><a href=#>...</a></li>\n");
//					i = pageNumber-1;
//				}
//				// else if(i==pageNumber) {
//				// rtn.append(" <li class=\"am-active\"><a
//				// href=\"/question/question_list?page=");
//				// rtn.append(i);
//				// rtn.append("&searchKW="+searchKW+"\">"+i+"</a></li>\n");
//				// }
//				else if ((i > pageNumber && i < (16 - 2))) {
//					rtn.append("        <li class=\"am-disabled\"><a href=#>...</a></li>\n");
//					i = 16 - 2;
//				} else {
//					rtn.append("        <li><a href=\"/question/question_list?page=" + i + "&searchKW=");
//					rtn.append(searchKW);
//					rtn.append("\">" + i + "</a ></li>\n");
//				}
//			}
//
//		}
//		if (pageNumber == 16) {
//			rtn.append("        <li class=\"am-disabled\">");
//		} else {
//			rtn.append("        <li>");
//		}
//
//		rtn.append("<a href=\"/question/question_list?page=");
//		rtn.append(pageNumber + 1);
//		rtn.append("&searchKW=");
//		rtn.append(searchKW);
//
//		rtn.append("\">下一页</a ></li>\n");
//		rtn.append("    </ul>\n</div>");
//
//		System.out.println(rtn);
//
//	}
//	
//	
	public static String processField(String field) {
		StringBuffer rtn=new StringBuffer();
		if(null==field) {
			return null;
		}
		else {
			rtn.append("'");
			rtn.append(field.replace("\r\n", "%"));
			rtn.append("'");
			return rtn.toString();
		}
	}

	public void export()  {
		String filePath = "/download/export"+DateKit.toStr(new Date(), "yyyyMMddHHmmss")+".sql";
		List<Question> questions = Question.dao.find("select * from tquestion");
		List<QuestionFlow> questionFlows = QuestionFlow.dao.find("select * from tquestion_flow");
		File file =new File(PathKit.getWebRootPath()+filePath);
		FileOutputStream outSTr;
		try {
			outSTr = new FileOutputStream(file);
			BufferedOutputStream buff = new BufferedOutputStream(outSTr);
			StringBuffer line = new StringBuffer();
	        for (Question q:questions) {
	        		line = new StringBuffer();
	        				line.append("insert into tquestion(questionId,questionTitle,questionItems,questionAnswer,"
	        						+ "questionType,keywords,isPassed,createBy,createTime,modifyBy,modifyTime,auditBy,auditTime,relationId) values (");
	        				line.append(processField(q.getQuestionId()));line.append(',');
	        				line.append(processField(q.getQuestionTitle()));line.append(',');
	        				line.append(processField(q.getQuestionItems()));line.append(',');
	        				line.append(processField(q.getQuestionAnswer()));line.append(',');
	        				line.append(processField(q.getQuestionType()));line.append(',');
	        				line.append(processField(q.getKeywords()));line.append(',');
	        				line.append(processField(q.getIsPassed()));line.append(',');
	        				line.append(processField(q.getCreateBy()));line.append(',');
	        				line.append(processField(DateKit.toStr(q.getCreateTime(), DateKit.timeStampPattern)));line.append(',');
	        				line.append(processField(q.getModifyBy()));line.append(',');
	        				line.append(processField(DateKit.toStr(q.getModifyTime(), DateKit.timeStampPattern)));line.append(',');
	        				line.append(processField(q.getAuditBy()));line.append(',');
	        				line.append(processField(DateKit.toStr(q.getAuditTime(), DateKit.timeStampPattern)));line.append(',');
	        				line.append(processField(q.getRelationId()));
	        				line.append(");\n");
	            buff.write(line.toString().getBytes());
	        }
	        for (QuestionFlow qf:questionFlows) {
	        		line = new StringBuffer();
	        		line.append("INSERT INTO tquestion_flow(question_id, port, title, msg, taxis, creator, create_time, "
	        				+ "modify_by, modify_time, relationId) VALUES (");
	        		line.append(qf.getQuestionId());line.append(',');
	        		line.append(processField(qf.getPort()));line.append(',');
	        		line.append(processField(qf.getTitle()));line.append(',');
	        		line.append(processField(qf.getMsg()));line.append(',');
	        		line.append(qf.getTaxis());line.append(',');
	        		line.append(processField(qf.getCreator()));line.append(',');
	        		line.append(processField(DateKit.toStr(qf.getCreateTime(), DateKit.timeStampPattern)));line.append(',');
	        		line.append(processField(qf.getModifyBy()));line.append(',');
    				line.append(processField(DateKit.toStr(qf.getModifyTime(), DateKit.timeStampPattern)));line.append(',');
    				line.append(processField(qf.getRelationId()));
    				line.append(");\n");
    				buff.write(line.toString().getBytes());
	        }
	        buff.write("update tquestion_flow set question_id=(select id from tquestion where relationId=tquestion_flow.relationId);".getBytes());
	        buff.flush();
	        buff.close();
	        renderFile("../"+filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}   
	}
//	
	public void import_page() {
		render("question_import_page.html");
	}
	
	public void question_import() {
		UploadFile file = getFile();
		String fileName =file.getFileName();
		readFileByLines(PathKit.getWebRootPath()+"/upload/"+fileName);
		renderText(PathKit.getWebRootPath()+"/upload/"+fileName);
	}

	/** 
     * 以行为单位读取文件，常用于读面向行的格式化文件 
     */  
    public static void readFileByLines(String fileName) {  
        File file = new File(fileName);  
        BufferedReader reader = null;  
        try {  
            System.out.println("以行为单位读取文件内容，一次读一整行：");  
            reader = new BufferedReader(new FileReader(file));  
            String tempString = null;  
            // 一次读入一行，直到读入null为文件结束  
            while ((tempString = reader.readLine()) != null) {  
                System.out.println(Db.update("insert into tquestion(questionId,questionTitle,questionItems,questionAnswer,questionType,keywords,isPassed,createBy,createTime,modifyBy,modifyTime,auditBy,auditTime) "
                		+ "values ("+ tempString.replace("%", "\n")+");"));  
            }  
            reader.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (reader != null) {  
                try {  
                    reader.close();  
                } catch (IOException e1) {  
                }  
            }  
        }  
    }  

//	
//	public String readToString(String fileName) {  
//        String encoding = "UTF-8";  
//        File file = new File(PathKit.getWebRootPath()+"/upload/temp/"+fileName);  
//        Long filelength = file.length();  
//        byte[] filecontent = new byte[filelength.intValue()];  
//        try {  
//            FileInputStream in = new FileInputStream(file);  
//            in.read(filecontent);  
//            in.close();  
//        } catch (FileNotFoundException e) {  
//            e.printStackTrace();  
//        } catch (IOException e) {  
//            e.printStackTrace();  
//        }  
//        try {  
//        	System.out.println("file content is:"+new String(filecontent));
//            return new String(filecontent, encoding);  
//        } catch (UnsupportedEncodingException e) {  
//            System.err.println("The OS does not support " + encoding);  
//            e.printStackTrace();  
//            return null;  
//        }  
//    }
//	
//	public void flowmsg_edit(){
//		setAttr("questionFlow", QuestionFlow.dao.findById(getPara("flowMsgId")));
//		renderJsp("/learn/flow_msg_edit.jsp");
//	}
//
//	public void flowmsg_edit_readonly(){
//		setAttr("questionFlow", QuestionFlow.dao.findById(getPara("flowMsgId")));
//		renderJsp("/learn/flow_msg_readonly.jsp");
//	}
//

//	
//	
//    public void flow_del() {
//    		Db.update("delete from tquestion_flow where id="+getPara("del_msg_id"));
//    		edit();
//    }
//}
}