package com.cjih.learnsystem.common.util;

import org.apache.log4j.Logger;

import com.jfinal.club.common.model.Answer;
import com.jfinal.club.common.model.BaseFlowMsg;
import com.jfinal.club.common.model.MsgTempletSub;
import com.jfinal.club.common.model.Question;
import com.jfinal.club.common.model.SavedAnswer;
import com.jfinal.club.common.model.X1msg;
import com.jfinal.club.common.model.X2msg;
import com.jfinal.club.common.model.X3msg;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.druid.DruidPlugin;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

/**
* 加密工具类
*
* md5加密出来的长度是32位
*
* sha加密出来的长度是40位
*
* @author zhangxianjin
*
*/
public class Encrypt {

private static final Logger logger = Logger.getLogger(Encrypt.class);

/**
* 测试
*
* @param args
*/
public static void main1(String[] args) {
// md5加密测试
String md5_1 = md5("123456");
String md5_2 = md5("孙宇");
logger.debug(md5_1 + "\n" + md5_2);
// sha加密测试
String sha_1 = sha("123456");
String sha_2 = sha("孙宇");
logger.debug(sha_1 + "\n" + sha_2);

}

/**
* 加密
*
* @param inputText
* @return
*/
public static String e(String inputText) {
return md5(inputText);
}

/**
* 二次加密，应该破解不了了吧？
*
* @param inputText
* @return
*/
public static String md5AndSha(String inputText) {
return sha(md5(inputText));
}

/**
* md5加密
*
* @param inputText
* @return
*/
public static String md5(String inputText) {
return encrypt(inputText, "md5");
}

/**
* sha加密
*
* @param inputText
* @return
*/
public static String sha(String inputText) {
return encrypt(inputText, "sha-1");
}

/**
* md5或者sha-1加密
*
* @param inputText
*            要加密的内容
* @param algorithmName
*            加密算法名称：md5或者sha-1，不区分大小写
* @return
*/
private static String encrypt(String inputText, String algorithmName) {
if (inputText == null || "".equals(inputText.trim())) {
    throw new IllegalArgumentException("请输入要加密的内容");
}
if (algorithmName == null || "".equals(algorithmName.trim())) {
    algorithmName = "md5";
}
String encryptText = null;
try {
    MessageDigest m = MessageDigest.getInstance(algorithmName);
    m.update(inputText.getBytes("UTF8"));
    byte s[] = m.digest();
    // m.digest(inputText.getBytes("UTF8"));
    return hex(s);
} catch (NoSuchAlgorithmException e) {
    e.printStackTrace();
} catch (UnsupportedEncodingException e) {
    e.printStackTrace();
}
return encryptText;
}

/**
* 返回十六进制字符串
*
* @param arr
* @return
*/
private static String hex(byte[] arr) {
StringBuffer sb = new StringBuffer();
for (int i = 0; i < arr.length; ++i) {
    sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1, 3));
}
return sb.toString();
}

/**
 * 数据解密
 * @param str
 * @return
 */
public static String decode(String str) {
	if(null==str) {
		return null;
	}
	return new String(Base64.getDecoder().decode(new String(Base64.getDecoder().decode(str))));
//	return Base64Util.decode(Base64Util.decode(str, "UTF-8"), "UTF-8");
}

public static String encode(String str) {
	if(null==str) {
		return null;
	}
//	return Base64Util.encode(Base64Util.encode(str, "UTF-8"), "UTF-8");
	return Base64.getEncoder().encodeToString((Base64.getEncoder().encodeToString(str.getBytes()).getBytes()));
}

public static void main(String args[]) {
	Prop p = PropKit.use("jfinal_club_config_dev.txt")
			.appendIfExists("jfinal_club_config_pro.txt");
	DruidPlugin dp = new DruidPlugin(p.get("jdbcUrl"), p.get("user"), p.get("password").trim()); 
	ActiveRecordPlugin arp = new ActiveRecordPlugin(dp); 
	arp.addMapping("tbase_flow_msg", BaseFlowMsg.class);
	arp.addMapping("tquestion", Question.class);
	arp.addMapping("tanswer", Answer.class);
	arp.addMapping("tsaved_answer", SavedAnswer.class);
	arp.addMapping("tmsg_templet_sub", MsgTempletSub.class);
	arp.addMapping("tx1msg", X1msg.class);
	arp.addMapping("tx2msg", X2msg.class);
	arp.addMapping("tx3msg", X3msg.class);
	dp.start();
	arp.start();
	
	List<X1msg> list = X1msg.dao.find("select * from tx1msg");
	for(X1msg r:list) {
		Db.update("update tx1msg set memo='"+Encrypt.encode(r.getMemo())+"'"
				+ " where id="+r.getId()+";");
	}
	Db.update("update tx1msg set memo=null where memo='null';");

	
//	List<MsgTempletSub> list = MsgTempletSub.dao.find("select * from tmsg_templet_sub");
//	for(MsgTempletSub r:list) {
//		Db.update("update tmsg_templet_sub set msg='"+Encrypt.encode(r.getMsg())+"'"
//				+ " where id="+r.getId()+";");
//	}
//	Db.update("update tmsg_templet_sub set msg=null where msg='null';");

//	System.out.println(Encrypt.decode("TVRJeE16TjNaWGRsTVRFeA=="));
//	List<SavedAnswer> list = SavedAnswer.dao.find("select * from tsaved_answer");
//	for(SavedAnswer r:list) {
//		System.out.println("update tsaved_answer set answer='"+Encrypt.encode(r.getAnswer())+"'"
//				+ " where id="+r.getId()+";");
//	}
//	System.out.println("update tsaved_answer set answer=null where answer='null';");

//	List<Answer> list = Answer.dao.find("select * from tanswer");
//	for(Answer r:list) {
//		System.out.println("update tanswer set answer='"+Encrypt.encode(r.getAnswer())+"'"
//				+ " where id="+r.getId()+";");
//	}
//	System.out.println("update set tanswer=null where tanswer='null';");
//	List<Question> list = Question.dao.find("select * from tquestion");
//	for(Question r:list) {
//		System.out.println("update tquestion set questionTitle='"+Encrypt.encode(r.getQuestionTitle())+"',"
//				+ " questionItems='"+Encrypt.encode(r.getQuestionItems())+"',"
//						+ "questionAnswer='"+Encrypt.encode(r.getQuestionAnswer())+"'"
//				+ " where id="+r.getId()+";");
//	}
//	System.out.println("update set questionTitle=null where questionTitle='null';");
//	System.out.println("update set questionItems=null where questionItems='null';");
//	System.out.println("update set questionAnswer=null where questionAnswer='null';");
//	List<BaseFlowMsg> list = BaseFlowMsg.dao.find("select msg,id from tbase_flow_msg");
//	for(BaseFlowMsg r:list) {
//		System.out.println("update tbase_flow_msg set msg='"+Encrypt.encode(r.getMsg())+"' where id="+r.getId()+";");
//	}
}

}
