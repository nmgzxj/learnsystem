package com.jfinal.club.common.model;



public class _KingGenerator {

	private static String pkgName = "com.cjih.learnsystem.answerlog";
	private static String controllerName = "AnswerLofController";
	private static String serviceName = "AnswerLogService";
	private static String modelName = "AnswerLog";
	private static String instanceName = "answerLog";
	private static String tableName = "tanswer_log";
	private static String chineseName = "考试日志";
	private static String folderName = "answerLog";

	public static String genController() {
		StringBuffer rtn = new StringBuffer();
		rtn.append("package ").append(pkgName).append(";\n");
		rtn.append("\n");
		rtn.append("import java.util.Date;\n");
		rtn.append("import com.jfinal.aop.Before;\n");
		rtn.append("import com.jfinal.club.common.controller.BaseController;\n");
		rtn.append("import com.jfinal.club.common.model."+modelName+";\n");
		rtn.append("import com.jfinal.kit.PropKit;\n");
		rtn.append("import com.jfinal.kit.Ret;\n");
		rtn.append("import com.jfinal.plugin.activerecord.Page;\n");
		rtn.append("\n");
		rtn.append("public class "+controllerName+" extends BaseController{\n");
		rtn.append("	\n");
		rtn.append(serviceName).append(" srv = ").append(serviceName).append(".me;\n");
		rtn.append("	\n");
		rtn.append("	public void index() {\n");
		rtn.append("		Page<"+modelName+"> "+instanceName+"Page = srv.paginate(getParaToInt(\"p\", 1));\n");
		rtn.append("		setAttr(\""+instanceName+"Page\", "+instanceName+"Page);\n");
		rtn.append("		render(\"index.html\");\n");
		rtn.append("	}\n");
		rtn.append("	\n");
		rtn.append("	/**\n");
		rtn.append("	 * 修改\n");
		rtn.append("	 */\n");
		rtn.append("	public void edit() {\n");
		rtn.append("		setAttr(\""+instanceName+"\", srv.edit(getParaToInt(\"id\")));\n");
		rtn.append("		render(\"edit.html\");\n");
		rtn.append("	}\n");
		rtn.append("\n");
		rtn.append("	/**\n");
		rtn.append("	 * 提交修改\n");
		rtn.append("	 */\n");
		rtn.append("	@Before("+modelName+"Validator.class)\n");
		rtn.append("	public void update() {\n");
		rtn.append("		"+modelName+" "+instanceName+" = getBean("+modelName+".class);\n");
		rtn.append("		"+instanceName+".setCreator(\"1\");\n");
		rtn.append("		Ret ret = srv.update("+instanceName+");\n");
		rtn.append("		renderJson(ret);\n");
		rtn.append("	}\n");
		rtn.append("\n");
		rtn.append("	public void save() {\n");
		rtn.append("		"+modelName+" "+instanceName+" = getBean("+modelName+".class);\n");
		rtn.append("		"+instanceName+".setCreator(Integer.toString(getLoginAccountId()));\n");
		rtn.append("		"+instanceName+".setCreateTime(new Date());\n");
		rtn.append("		Ret ret = srv.save("+instanceName+");\n");
		rtn.append("		renderJson(ret);\n"); 
		rtn.append("	}\n" ); 
		rtn.append( "\n" ); 
		rtn.append("	public void delete() {\n");
		rtn.append("		Ret ret = srv.delete(getParaToInt(\"id\"));\n");
		rtn.append("		renderJson(ret);\n");
		rtn.append("	}\n");
		rtn.append("\n");
		rtn.append("	public void add() {\n");
		rtn.append("		render(\"add.html\");\n");
		rtn.append("	}\n");
		rtn.append("}\n");
		return rtn.toString();
	}

	public static String genService() {
		String rtn = "package "+pkgName+";\n" + 
				"\n" + 
				"\n" + 
				"\n" + 
				"\n" + 
				"import com.jfinal.club.common.model."+modelName+";\n" + 
				"import com.jfinal.kit.PropKit;\n" + 
				"import com.jfinal.kit.Ret;\n" + 
				"import com.jfinal.plugin.activerecord.Page;\n" + 
				"\n" + 
				"/**\n" + 
				" * "+chineseName+"管理\n" + 
				" * @author ZhangXianJin\n" + 
				" *\n" + 
				" */\n" + 
				"public class "+serviceName+" {\n" + 
				"	\n" + 
				"	public static final "+serviceName+" me = new "+serviceName+"();\n" + 
				"	private "+modelName+" dao = new "+modelName+"().dao();\n" + 
				"	\n" + 
				"	public Page<"+modelName+"> paginate(int pageNum) {\n" + 
				"		return dao.paginate(pageNum, PropKit.getInt(\"pageSize\"), \"select *\", \"from "+tableName+" order by id desc\");\n" + 
				"	}\n" + 
				"\n" + 
				"	public "+modelName+" findById(int id) {\n" + 
				"		return dao.findById(id);\n" + 
				"	}\n" + 
				"\n" + 
				"	public "+modelName+" edit(int id) {\n" + 
				"		return dao.findById(id);\n" + 
				"	}\n" + 
				"	\n" + 
				"	public Ret save("+modelName+" "+instanceName+") {\n" + 
				"		"+instanceName+".save();\n" + 
				"		return Ret.ok();\n" + 
				"	}\n" + 
				"\n" + 
				"	/**\n" + 
				"	 * 修改"+chineseName+"数据\n" + 
				"	 */\n" + 
				"	public Ret update("+modelName+" "+instanceName+") {\n" + 
				"		"+instanceName+".update();\n" + 
				"		return Ret.ok(\"msg\", \""+chineseName+"更新成功\");\n" + 
				"	}\n" + 
				"\n" + 
				"	/**\n" + 
				"	 * 删除"+chineseName+"数据\n" + 
				"	 */\n" + 
				"	public Ret delete(final int id) {\n" + 
				"		"+modelName+" "+instanceName+" = dao.findById(id);\n" + 
				"		if("+instanceName+".delete()) {\n" + 
				"			return Ret.ok(\"msg\", \""+chineseName+" 删除成功\");\n" + 
				"		}\n" + 
				"		else {\n" + 
				"			return Ret.ok(\"msg\", \""+chineseName+" 删除失败\");\n" + 
				"		}\n" + 
				"	}\n" + 
				"\n" + 
				"}\n" + 
				"";
		return rtn;
	}
	
	public static String genValidator() {
		String rtn = "package "+pkgName+";\n" + 
				"\n" + 
				"import com.jfinal.core.Controller;\n" + 
				"import com.jfinal.validate.Validator;\n" + 
				"\n" + 
				"public class "+modelName+"Validator extends Validator {\n" + 
				"\n" + 
				"	@Override\n" + 
				"	protected void validate(Controller c) {\n" + 
				"		setShortCircuit(true);\n" + 
				"\n" + 
				"		// TODO Auto-generated method stub\n" + 
				"		//validateRequiredString(\"字段\", \"msg\", \"消息名称不能为空\");\n" + 
				"//		checkSensitiveWords(c.getPara(\"project.name\"), \"项目名称 name包含敏感词\");\n" + 
				"//		checkSensitiveWords(c.getPara(\"project.title\"), \"项目标题 title 包含敏感词\");\n" + 
				"//		checkSensitiveWords(c.getPara(\"project.content\"), \"项目内容 content 名包含敏感词\");\n" + 
				"//\n" + 
				"//		validateString(\"project.name\", 3, 20, \"msg\", \"项目名称长度要求在3到20个字符\");\n" + 
				"//\n" + 
				"//		String projectName = c.getPara(\"project.name\");\n" + 
				"//		// 创建项目\n" + 
				"//		if (\"save\".equals(getActionMethod().getName())) {\n" + 
				"//			if (MyProjectService.me.isProjectNameExists(projectName)) {\n" + 
				"//				addError(\"msg\", \"项目名称已经存在，请使用其她名称\");\n" + 
				"//			}\n" + 
				"//		}\n" + 
				"//		// 修改项目\n" + 
				"//		else if (\"update\".equals(getActionMethod().getName())) {\n" + 
				"//			int projectId = c.getParaToInt(\"project.id\");\n" + 
				"//			if (MyProjectService.me.isProjectNameExists(projectId, projectName)) {\n" + 
				"//				addError(\"msg\", \"项目名称已经存在，请使用其她名称\");\n" + 
				"//			}\n" + 
				"//		} else {\n" + 
				"//			addError(\"msg\", \"MyProjectValidator 只能用于 save、update 方法\");\n" + 
				"//		}\n" + 
				"//\n" + 
				"//		validateString(\"project.title\", 3, 100, \"msg\", \"标题长度要求在3到100个字符\");\n" + 
				"//		validateString(\"project.content\", 19, 65536, \"msg\", \"正文内容太少啦，多写点哈\");\n" + 
				""
				+ "	}\n" + 
				"\n" + 
				"	@Override\n" + 
				"	protected void handleError(Controller c) {\n" + 
				"		// TODO Auto-generated method stub\n" + 
				"		\n" + 
				"	}\n" + 
				"\n" + 
				"}\n" + 
				"";
		return rtn;
	}
	
	public static String genIndexPage() {
		String rtn = "#set(seoTitle=\""+chineseName+"管理\")\n" + 
				"#@adminLayout()\n" + 
				"#define main()\n" + 
				"<div class=\"jfa-header-box\" id=\"jfa-header-box\">\n" + 
				"	<div class=\"jfa-crumbs\" id=\"jfa-crumbs\">\n" + 
				"		#(seoTitle)\n" + 
				"	</div>\n" + 
				"	<div class=\"jfa-search-box\"></div>\n" + 
				"	#include(\"/_view/_admin/common/_header_right.html\")\n" + 
				"</div>\n" + 
				"\n" + 
				"### 内容区域\n" + 
				"<div class=\"jfa-content-box\" id=\"jfa-content-box\">\n" + 
				"	<div class=\"jfa-content\" id=\"jfa-content\">\n" + 
				"		<div class=\"jfa-toolbar\">\n" + 
				"			<a class=\"btn btn-primary btn-sm\" href=\"/"+folderName+"/add\">\n" + 
				"				<i class=\"fa fa-plus\"></i>\n" + 
				"				创建\n" + 
				"			</a>\n" + 
				"		</div>\n" + 
				"		<div class=\"jfa-table-box margin-top-30\">\n" + 
				"                        <table class=\"table table-bordered table-hover margin-bottom-10\">\n" + 
				"                                <thead>\n" + 
				"                                <tr>\n" + 
				"\n" + 
				"                                        <th>消息名</th>\n" + 
				"                                        <th>接口</th>\n" + 
				"                                        <th>操作</th>\n" + 
				"                                </tr>\n" + 
				"                                </thead>\n" + 
				"                                <tbody>\n" + 
				"                                #for(x:"+instanceName+"Page.getList())\n" + 
				"                                <tr class=\"gradeX\">\n" + 
				"                                        <td>#(x.title)</td>\n" + 
				"                                        <td>#(x.port)</td>\n" + 
				"                                        <td>\n" + 
				"                                            <a href=\"/"+folderName+"/edit?id=#(x.id)\" >\n" + 
				"                                                    <i class=\"fa fa-pencil\" title=\"修改\">修改</i>\n" + 
				"                                            </a>\n" + 
				"	                                                    \n" + 
				"					                        <a data-delete\n" + 
				"											   data-title=\"#escape(x.title)\"\n" + 
				"											   data-action=\"/"+folderName+"/delete?id=#(x.id)\">\n" + 
				"												<i class=\"fa fa-trash\" title=\"删除\">删除</i>\n" + 
				"											</a>\n" + 
				"                                        </td>\n" + 
				"                                </tr>\n" + 
				"                                #end\n" + 
				"                                <!-- more data -->\n" + 
				"                                </tbody>\n" + 
				"                        </table>\n" + 
				"                        <div>\n" + 
				"								#@adminPaginate("+instanceName+"Page.pageNumber, "+instanceName+"Page.totalPage, \"/"+folderName+"?p=\")\n" + 
				"						</div>\n" + 
				"		</div>\n" + 
				"	</div><!-- END OF jfa-content -->\n" + 
				"</div><!-- END OF jfa-content-box -->\n" + 
				"\n" + 
				"#end";
		return rtn;
	}
	
	public static String genAddPage() {
		String rtn = "#set(seoTitle=\""+chineseName+"管理\")\n" + 
				"#@adminLayout()\n" + 
				"#define main()\n" + 
				"<div class=\"jfa-header-box\" id=\"jfa-header-box\">\n" + 
				"	<div class=\"jfa-crumbs\" id=\"jfa-crumbs\">\n" + 
				"		"+chineseName+"添加\n" + 
				"	</div>\n" + 
				"	<div class=\"jfa-search-box\"></div>\n" + 
				"	#include(\"/_view/_admin/common/_header_right.html\")\n" + 
				"</div>\n" + 
				"\n" + 
				"### 内容区域\n" + 
				"<div class=\"jfa-content-box\" id=\"jfa-content-box\">\n" + 
				"	<div class=\"jfa-content\" id=\"jfa-content\">\n" + 
				"         #include(\"_form.html\", action=\"/"+folderName+"/save\")\n" + 
				"\n" + 
				"	</div><!-- END OF jfa-content -->\n" + 
				"</div><!-- END OF jfa-content-box -->\n" + 
				"\n" + 
				"<style type=\"text/css\">\n" + 
				"	#myArticleForm {\n" + 
				"		width: 500px;\n" + 
				"	}\n" + 
				"\n" + 
				"	.form-group {\n" + 
				"		margin-bottom: 25px;\n" + 
				"	}\n" + 
				"\n" + 
				"	.jfa-content label {\n" + 
				"		line-height: 1;\n" + 
				"		vertical-align: baseline;\n" + 
				"		color: #23527c;\n" + 
				"		font-size: 20px;\n" + 
				"		font-weight: normal;\n" + 
				"		margin-bottom: 8px;;\n" + 
				"	}\n" + 
				"</style>\n" + 
				"\n" + 
				"<script type=\"text/javascript\">\n" + 
				"	$(document).ready(function() {\n" + 
				"		$(\"#myArticleForm\").ajaxForm({\n" + 
				"			dataType: \"json\"\n" + 
				"			, beforeSubmit: function(formData, jqForm, options) {}\n" + 
				"			, success: function(ret) {\n" + 
				"				if (ret.state == \"ok\") {\n" + 
				"					location.href = \"/basemsg\";\n" + 
				"				} else {\n" + 
				"					showFailMsg(ret.msg);\n" + 
				"				}\n" + 
				"			}\n" + 
				"			, error: function(ret) {alert(ret.statusText);}\n" + 
				"			, complete: function(ret) {} 	      // 无论是 success 还是 error，最终都会被回调\n" + 
				"		});\n" + 
				"	});\n" + 
				"</script>\n" + 
				"\n" + 
				"\n" + 
				"#end\n" + 
				"";
		return rtn;
	}
	
	public static String genEditPage() {
		String rtn = "#set(seoTitle=\""+chineseName+"管理\")\n" + 
				"#@adminLayout()\n" + 
				"#define main()\n" + 
				"<div class=\"jfa-header-box\" id=\"jfa-header-box\">\n" + 
				"	<div class=\"jfa-crumbs\" id=\"jfa-crumbs\">\n" + 
				"		"+chineseName+"修改\n" + 
				"	</div>\n" + 
				"	<div class=\"jfa-search-box\"></div>\n" + 
				"	#include(\"/_view/_admin/common/_header_right.html\")\n" + 
				"</div>\n" + 
				"\n" + 
				"### 内容区域\n" + 
				"<div class=\"jfa-content-box\" id=\"jfa-content-box\">\n" + 
				"	<div class=\"jfa-content\" id=\"jfa-content\">\n" + 
				"         #include(\"_form.html\", action=\"/"+folderName+"/update\")\n" + 
				"\n" + 
				"	</div><!-- END OF jfa-content -->\n" + 
				"</div><!-- END OF jfa-content-box -->\n" + 
				"\n" + 
				"<style type=\"text/css\">\n" + 
				"	#myArticleForm {\n" + 
				"		width: 500px;\n" + 
				"	}\n" + 
				"\n" + 
				"	.form-group {\n" + 
				"		margin-bottom: 25px;\n" + 
				"	}\n" + 
				"\n" + 
				"	.jfa-content label {\n" + 
				"		line-height: 1;\n" + 
				"		vertical-align: baseline;\n" + 
				"		color: #23527c;\n" + 
				"		font-size: 20px;\n" + 
				"		font-weight: normal;\n" + 
				"		margin-bottom: 8px;;\n" + 
				"	}\n" + 
				"</style>\n" + 
				"\n" + 
				"<script type=\"text/javascript\">\n" + 
				"	$(document).ready(function() {\n" + 
				"		$(\"#myArticleForm\").ajaxForm({\n" + 
				"			dataType: \"json\"\n" + 
				"			, beforeSubmit: function(formData, jqForm, options) {}\n" + 
				"			, success: function(ret) {\n" + 
				"				if (ret.state == \"ok\") {\n" + 
				"					location.href = \"/basemsg\";\n" + 
				"				} else {\n" + 
				"					showFailMsg(ret.msg);\n" + 
				"				}\n" + 
				"			}\n" + 
				"			, error: function(ret) {alert(ret.statusText);}\n" + 
				"			, complete: function(ret) {} 	      // 无论是 success 还是 error，最终都会被回调\n" + 
				"		});\n" + 
				"	});\n" + 
				"</script>\n" + 
				"\n" + 
				"\n" + 
				"#end\n" + 
				"";
		return rtn;
	}
	
	public static String genFormPage() {
		String rtn = "<form  class=\"form-horizontal margin-top-30\" id=\"myArticleForm\" action=\"#(action)\" method=\"post\">\n" + 
				"#if("+instanceName+".id??)\n" + 
				"		<input type=\"hidden\" name=\""+instanceName+".id\" value=\"#("+instanceName+".id ??)\" />\n" + 
				"#end\n" + 
				"		<table class=\"table table-bordered table-hover margin-bottom-10\">\n" + 
				"			<tbody>\n" + 
				"				<tr class=\"gradeX\">\n" + 
				"					<td style=\"text-align: left\"><font color=\"#ff0000\">*</font> \n" + 
				"					消息名:<input type=\"text\" name=\""+instanceName+".title\" required\n" + 
				"						value=\"#("+instanceName+".title ??)\" class=\"ipt\" /></td>\n" + 
				"\n" + 
				"				</tr>\n" + 
				"				<tr>\n" + 
				"					<td style=\"text-align: left\"><font color=\"#ff0000\">*</font> \n" + 
				"					端口： \n" + 
				"		<select id=\""+instanceName+".port\" name=\""+instanceName+".port\" style=\"width:150px\">\n" + 
				"			<option checked>#("+instanceName+".port ??)</option>\n" + 
				"			<option>x1</option>\n" + 
				"			<option>x2</option>\n" + 
				"			<option>x3</option>\n" + 
				"		</select></td>\n" + 
				"		</tr>\n" + 
				"		<tr class=\"even gradeC\">\n" + 
				"			<td colspan=\"2\" style=\"text-align: left\"><font\n" + 
				"				color=\"#ff0000\">* </font> 消息体：<textarea\n" + 
				"					name=\""+instanceName+".msg\" id=\""+instanceName+".msg\"\n" + 
				"					style=\"width: 61.5%; height: 250px\">#("+instanceName+".msg ??)</textarea>\n" + 
				"			</td>\n" + 
				"		</tr>\n" + 
				"				<tr class=\"\">\n" + 
				"					<td colspan=\"2\" style=\"text-align: left\">\n" + 
				"					<input type=\"submit\" value=\"确认提交\"  class=\"btn btn-primary\" />\n" + 
				"					</td>\n" + 
				"				</tr>\n" + 
				"				<!-- more data -->\n" + 
				"			</tbody>\n" + 
				"		</table>\n" + 
				"	</form>\n" + 
				"								";
		return rtn;
	}
	
	
	public static void main(String args[]) {
		//创建数据库表格
		//生成model
//		System.out.println(genService());
//		System.out.println(genValidator());
//		System.out.println(genController());
		System.out.println(genIndexPage());
//		System.out.println(genAddPage());
//		System.out.println(genEditPage());
//		System.out.println(genFormPage());
	}
}
