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

package com.jfinal.club.common;

import java.sql.Connection;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;

import com.jfinal.club._admin.permission.PermissionDirective;
import com.jfinal.club._admin.role.RoleDirective;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.json.MixedJsonFactory;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.cron4j.Cron4jPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.JsonRender;
import com.jfinal.template.Engine;
import com.jfinal.template.source.ClassPathSourceFactory;
import com.jfinal.club._admin.common.AdminRoutes;
import com.jfinal.club.common.handler.UrlSeoHandler;
import com.jfinal.club.common.kit.DruidKit;
import com.jfinal.club.common.model.Account;
import com.jfinal.club.common.model.Answer;
import com.jfinal.club.common.model.AnswerLog;
import com.jfinal.club.common.model.AskList;
import com.jfinal.club.common.model.AskRunLog;
import com.jfinal.club.common.model.AuthCode;
import com.jfinal.club.common.model.BaseFlow;
import com.jfinal.club.common.model.BaseFlowMsg;
import com.jfinal.club.common.model.BaseMsg;
import com.jfinal.club.common.model.Bug;
import com.jfinal.club.common.model.Document;
import com.jfinal.club.common.model.Download;
import com.jfinal.club.common.model.DownloadLog;
import com.jfinal.club.common.model.Exam;
import com.jfinal.club.common.model.ExamQuestion;
import com.jfinal.club.common.model.ExamUser;
import com.jfinal.club.common.model.Favorite;
import com.jfinal.club.common.model.Feedback;
import com.jfinal.club.common.model.FeedbackReply;
import com.jfinal.club.common.model.Message;
import com.jfinal.club.common.model.MsgTemplet;
import com.jfinal.club.common.model.MsgTempletSub;
import com.jfinal.club.common.model.NewsFeed;
import com.jfinal.club.common.model.Number;
import com.jfinal.club.common.model.Permission;
import com.jfinal.club.common.model.Project;
import com.jfinal.club.common.model.Question;
import com.jfinal.club.common.model.QuestionCopy1;
import com.jfinal.club.common.model.QuestionFlow;
import com.jfinal.club.common.model.ReferMe;
import com.jfinal.club.common.model.Remind;
import com.jfinal.club.common.model.Resource;
import com.jfinal.club.common.model.ResourceInfo;
import com.jfinal.club.common.model.ResourceInfoCopy1;
import com.jfinal.club.common.model.ResourceType;
import com.jfinal.club.common.model.Role;
import com.jfinal.club.common.model.SavedAnswer;
import com.jfinal.club.common.model.Session;
import com.jfinal.club.common.model.Share;
import com.jfinal.club.common.model.ShareReply;
import com.jfinal.club.common.model.TaskList;
import com.jfinal.club.common.model.TrueUnit;
import com.jfinal.club.common.model.Unit;
import com.jfinal.club.common.model.User;
import com.jfinal.club.common.model.X1msg;
import com.jfinal.club.common.model.X2msg;
import com.jfinal.club.common.model.X3msg;
import com.jfinal.club.common.model._MappingKit;
import com.jfinal.club.common.interceptor.LoginSessionInterceptor;
import com.jfinal.club.login.LoginService;



/**
 * LearnSystemConfig
 */
public class LearnSystemConfig extends JFinalConfig {
	
	// 先加载开发环境配置，再追加生产环境的少量配置覆盖掉开发环境配置
	private static Prop p = PropKit.use("config.properties");
	
	private WallFilter wallFilter;
	
	/**
	 * 启动入口，运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 * 
	 * 使用本方法启动过第一次以后，会在开发工具的 debug、run configuration 中自动生成
	 * 一条启动配置项，可对该自动生成的配置再继续添加更多的配置项，例如 VM argument 可配置为：
	 * -XX:PermSize=64M -XX:MaxPermSize=256M 
	 * 上述 VM 配置可以缓解热加载功能出现的异常
	 */
	public static void main(String[] args) {
		/*
		 * 特别注意：Eclipse 之下建议的启动方式
		 * JFinal.start("web", 8080, "/", 5);
		 * 特别注意：IDEA 之下建议的启动方式，仅比 eclipse 之下少了最后一个参数
		 */
		 JFinal.start("src/main/web", 8080, "/", 5);
//		UndertowServer.start(LearnSystemConfig.class);
	}
	
    public void configConstant(Constants me) {
        me.setDevMode(p.getBoolean("devMode", false));
		me.setJsonFactory(MixedJsonFactory.me());
		me.setError404View("/_view/404.html");
		me.setError500View("/_view/500.html");
    }
    
    /**
     * 路由拆分到 FrontRutes 与 AdminRoutes 之中配置的好处：
     * 1：可分别配置不同的 baseViewPath 与 Interceptor
     * 2：避免多人协同开发时，频繁修改此文件带来的版本冲突
     * 3：避免本文件中内容过多，拆分后可读性增强
     * 4：便于分模块管理路由
     */
    public void configRoute(Routes me) {
	    me.add(new FrontRoutes());
	    me.add(new AdminRoutes());
    }

    /**
     * 配置模板引擎，通常情况只需配置共享的模板函数
     */
    public void configEngine(Engine me) {
    	me.setDevMode(p.getBoolean("engineDevMode", false));

		me.addDirective("role", RoleDirective.class);
		me.addDirective("permission", PermissionDirective.class);
		me.addDirective("perm", PermissionDirective.class);		// 配置一个别名指令

	    	me.addSharedFunction("/_view/common/__layout.html");
	    	me.addSharedFunction("/_view/common/_paginate.html");

	    me.addSharedFunction("/_view/_admin/common/__admin_layout.html");
		me.addSharedFunction("/_view/_admin/common/_admin_paginate.html");
		
    }
    
    /**
     * 抽取成独立的方法，便于 _Generator 中重用该方法，减少代码冗余
     */
	public static DruidPlugin getDruidPlugin() {
		return new DruidPlugin(p.get("jdbcUrl"), p.get("user"), p.get("password").trim());
	}
	
    public void configPlugin(Plugins me) {
	    DruidPlugin druidPlugin = getDruidPlugin();
	    wallFilter = new WallFilter();              // 加强数据库安全
	    wallFilter.setDbType("mysql");
	    druidPlugin.addFilter(wallFilter);
	    druidPlugin.addFilter(new StatFilter());    // 添加 StatFilter 才会有统计数据
	    me.add(druidPlugin);
	    
	    ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
	    arp.setTransactionLevel(Connection.TRANSACTION_READ_COMMITTED);
	    
	    arp.addMapping("account", "id", Account.class);
		arp.addMapping("auth_code", "id", AuthCode.class);
		// Composite Primary Key order: mainMenu,subMenu
		arp.addMapping("document", "mainMenu,subMenu", Document.class);
		arp.addMapping("download", "id", Download.class);
		arp.addMapping("download_log", "id", DownloadLog.class);
		arp.addMapping("favorite", "id", Favorite.class);
		arp.addMapping("feedback", "id", Feedback.class);
		arp.addMapping("feedback_reply", "id", FeedbackReply.class);
		arp.addMapping("message", "id", Message.class);
		arp.addMapping("news_feed", "id", NewsFeed.class);
		arp.addMapping("permission", "id", Permission.class);
		arp.addMapping("project", "id", Project.class);
		arp.addMapping("refer_me", "id", ReferMe.class);
		arp.addMapping("remind", "accountId", Remind.class);
		arp.addMapping("role", "id", Role.class);
		arp.addMapping("session", "id", Session.class);
		arp.addMapping("share", "id", Share.class);
		arp.addMapping("share_reply", "id", ShareReply.class);
		arp.addMapping("task_list", "id", TaskList.class);
		
		arp.addMapping("tanswer", "id", Answer.class);
		arp.addMapping("tanswer_log", "id", AnswerLog.class);
		arp.addMapping("task_list", "id", AskList.class);
		arp.addMapping("task_run_log", "id", AskRunLog.class);
		arp.addMapping("tbase_flow", "id", BaseFlow.class);
		arp.addMapping("tbase_flow_msg", "id", BaseFlowMsg.class);
		arp.addMapping("tbase_msg", "id", BaseMsg.class);
		arp.addMapping("tbug", "id", Bug.class);
		arp.addMapping("texam", "id", Exam.class);
		arp.addMapping("texam_question", "id", ExamQuestion.class);
		arp.addMapping("texam_user", "id", ExamUser.class);
		arp.addMapping("tmsg_templet", "id", MsgTemplet.class);
		arp.addMapping("tmsg_templet_sub", "id", MsgTempletSub.class);
		arp.addMapping("tnumber", "id", Number.class);
		arp.addMapping("tquestion", "id", Question.class);
		arp.addMapping("tquestion_copy1", "id", QuestionCopy1.class);
		arp.addMapping("tquestion_flow", "id", QuestionFlow.class);
		arp.addMapping("tresource", "ID", Resource.class);
		arp.addMapping("tresource_info", "resource_id", ResourceInfo.class);
		arp.addMapping("tresource_info_copy1", "resource_id", ResourceInfoCopy1.class);
		arp.addMapping("tresource_type", "type_id", ResourceType.class);
		arp.addMapping("tsaved_answer", "id", SavedAnswer.class);
		arp.addMapping("ttrue_unit", "id", TrueUnit.class);
		arp.addMapping("tunit", "id", Unit.class);
		arp.addMapping("tuser", "id", User.class);
		arp.addMapping("tx1msg", "id", X1msg.class);
		arp.addMapping("tx2msg", "id", X2msg.class);
		arp.addMapping("tx3msg", "id", X3msg.class);
	    _MappingKit.mapping(arp);
	    // 强制指定复合主键的次序，避免不同的开发环境生成在 _MappingKit 中的复合主键次序不相同
	    arp.setPrimaryKey("document", "mainMenu,subMenu");
	    me.add(arp);
        arp.setShowSql(p.getBoolean("devMode", false));
        arp.getEngine().setSourceFactory(new ClassPathSourceFactory());
        arp.addSqlTemplate("/sql/all_sqls.sql");
        
	    me.add(new EhCachePlugin());
	    me.add(new Cron4jPlugin(p));
    }
    
    public void configInterceptor(Interceptors me) {
	    me.add(new LoginSessionInterceptor());
    }
    
    public void configHandler(Handlers me) {
	    me.add(DruidKit.getDruidStatViewHandler()); // druid 统计页面功能
	    me.add(new UrlSeoHandler());             	// index、detail 两类 action 的 url seo
    }
    
    /**
     * 本方法会在 jfinal 启动过程完成之后被回调，详见 jfinal 手册
     */
	public void afterJFinalStart() {
		// 调用不带参的 renderJson() 时，排除对 loginAccount、remind 的 json 转换
		JsonRender.addExcludedAttrs(
                LoginService.loginAccountCacheName,
                LoginSessionInterceptor.remindKey
        );
		
		// 让 druid 允许在 sql 中使用 union
		// https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE-wallfilter
		wallFilter.getConfig().setSelectUnionCheck(false);
	}
}






