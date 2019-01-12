/**
 * 请勿将俱乐部专享资源复制给其他人，保护知识产权即是保护我们所在的行业，进而保护我们自己的利益
 * 即便是公司的同事，也请尊重 JFinal 作者的努力与付出，不要复制给同事
 * 
 * 如果你尚未加入俱乐部，请立即删除该项目，或者现在加入俱乐部：http://jfinal.com/club
 * 
 * 俱乐部将提供 jfinal-club 项目文档与设计资源、专用 QQ 群，以及作者在俱乐部定期的分享与答疑，
 * 价值远比仅仅拥有 jfinal club 项目源代码要大得多
 * 
 * JFinal 俱乐部是五年以来首次寻求外部资源的尝试，以便于有资源创建更加
 * 高品质的产品与服务，为大家带来更大的价值，所以请大家多多支持，不要将
 * 首次的尝试扼杀在了摇篮之中
 */

package com.jfinal.club._admin.common;

import com.jfinal.club._admin.auth.AdminAuthInterceptor;
import com.jfinal.club._admin.permission.PermissionAdminController;
import com.jfinal.club._admin.role.RoleAdminController;
import com.jfinal.club._admin.feedback.FeedbackAdminController;
import com.jfinal.club._admin.index.IndexAdminController;
import com.cjih.learnsystem.answer.AnswerController;
import com.cjih.learnsystem.answerlog.AnswerLogController;
import com.cjih.learnsystem.baseflow.BaseFlowController;
import com.cjih.learnsystem.basemsg.BaseMsgController;
import com.cjih.learnsystem.controller.SystemController;
import com.cjih.learnsystem.exam.ExamController;
import com.cjih.learnsystem.question.QuestionController;
import com.cjih.learnsystem.savedAnswer.AnswerdController;
import com.cjih.learnsystem.savedAnswer.JudgeAnswerController;
import com.cjih.learnsystem.savedAnswer.SavedAnswerController;
import com.cjih.learnsystem.simulate.MsgTempletController;
import com.cjih.learnsystem.simulate.UnitController;
import com.jfinal.club._admin.account.AccountAdminController;
import com.jfinal.club._admin.project.ProjectAdminController;
import com.jfinal.club._admin.share.ShareAdminController;
import com.jfinal.config.Routes;

/**
 * 后台管理路由
 * 注意：自 jfinal 3.0 开始，baesViewPath 改为在 Routes 中独立配置
 *      并且支持 Routes 级别的 Interceptor，这类拦截器将拦截所有
 *      在此 Routes 中添加的 Controller，行为上相当于 class 级别的拦截器
 *      Routes 级别的拦截器特别适用于后台管理这样的需要统一控制权限的场景
 *      减少了代码冗余
 */
public class AdminRoutes extends Routes {

	public void config() {
		// 添加后台管理拦截器，将拦截在此方法中注册的所有 Controller
		addInterceptor(new AdminAuthInterceptor());
		addInterceptor(new PjaxInterceptor());

		setBaseViewPath("/_view");
		
		add("/admin", IndexAdminController.class, "/_admin/index");
		add("/admin/account", AccountAdminController.class, "/_admin/account");
		add("/admin/project", ProjectAdminController.class, "/_admin/project");
		add("/admin/share", ShareAdminController.class, "/_admin/share");
		add("/admin/feedback", FeedbackAdminController.class, "/_admin/feedback");

		add("/admin/role", RoleAdminController.class, "/_admin/role");
		add("/admin/permission", PermissionAdminController.class, "/_admin/permission");
		


		
		
		//考试系统
		add("/admin/unit",UnitController.class,"/simulation");
		add("/admin/exam",ExamController.class,"/exam");
		add("/admin/question",QuestionController.class,"/question");
		add("/admin/answer",AnswerController.class,"/answer");
		add("/admin/answerd",AnswerdController.class,"/answerd");
		add("/admin/savedanswer", SavedAnswerController.class,"/savedanswer");
		add("/admin/judgeAnswer", JudgeAnswerController.class, "/judgeanswer");
		add("/admin/templet", MsgTempletController.class,"/simulation");
		add("/admin/system", SystemController.class,"/system");
		add("/admin/basemsg", BaseMsgController.class,"/basemsg");
		add("/admin/baseflow", BaseFlowController.class,"/baseflow");
		add("/admin/answerlog", AnswerLogController.class, "/answerlog");
	}
}





