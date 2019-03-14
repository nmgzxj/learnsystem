package com.jfinal.club.common.model;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

/**
 * Generated by JFinal, do not modify this file.
 * <pre>
 * Example:
 * public void configPlugin(Plugins me) {
 *     ActiveRecordPlugin arp = new ActiveRecordPlugin(...);
 *     _MappingKit.mapping(arp);
 *     me.add(arp);
 * }
 * </pre>
 */
public class _MappingKit {
	
	public static void mapping(ActiveRecordPlugin arp) {
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
		arp.addMapping("session", "id", Session.class);
		arp.addMapping("share", "id", Share.class);
		arp.addMapping("share_reply", "id", ShareReply.class);
		arp.addMapping("tanswer", "id", Answer.class);
		arp.addMapping("tanswer_log", "id", AnswerLog.class);
		arp.addMapping("task_list", "id", AskList.class);
		arp.addMapping("tbase_flow", "id", BaseFlow.class);
		arp.addMapping("tbase_flow_msg", "id", BaseFlowMsg.class);
		arp.addMapping("tbase_msg", "id", BaseMsg.class);
		arp.addMapping("tbug", "id", Bug.class);
		arp.addMapping("texam", "id", Exam.class);
		arp.addMapping("texam_question", "id", ExamQuestion.class);
		arp.addMapping("texam_user", "id", ExamUser.class);
		arp.addMapping("tmenu", "id", Menu.class);
		arp.addMapping("tmsg_templet", "id", MsgTemplet.class);
		arp.addMapping("tmsg_templet_sub", "id", MsgTempletSub.class);
		arp.addMapping("tnumber", "id", Number.class);
		arp.addMapping("tnumber_user", "id", NumberUser.class);
		arp.addMapping("tonline", "id", Online.class);
		arp.addMapping("tquestion", "id", Question.class);
		arp.addMapping("tquestion_copy1", "id", QuestionCopy1.class);
		arp.addMapping("tquestion_copy2", "id", QuestionCopy2.class);
		arp.addMapping("tquestion_flow", "id", QuestionFlow.class);
		arp.addMapping("tresource", "ID", Resource.class);
		arp.addMapping("tresource_info", "resource_id", ResourceInfo.class);
		arp.addMapping("tresource_info_copy1", "resource_id", ResourceInfoCopy1.class);
		arp.addMapping("tresource_type", "type_id", ResourceType.class);
		arp.addMapping("trole_tresource", "id", RoleTresource.class);
		arp.addMapping("tsaved_answer", "id", SavedAnswer.class);
		arp.addMapping("ttrue_unit", "id", TrueUnit.class);
		arp.addMapping("tunit", "id", Unit.class);
		arp.addMapping("tuser", "id", User.class);
		arp.addMapping("tuser_trole", "id", UserTrole.class);
		arp.addMapping("tx1msg", "id", X1msg.class);
		arp.addMapping("tx2msg", "id", X2msg.class);
		arp.addMapping("tx3msg", "id", X3msg.class);
	}
}

