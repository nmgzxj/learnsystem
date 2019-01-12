package com.cjih.learnsystem.controller;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.jfinal.club.common.model.ResourceInfo;
import com.jfinal.club.common.model.ResourceType;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.druid.DruidPlugin;

public class ResourceInfoController extends Controller{

	/**
	 * 通用组件，根据资源类型和资源ID来获取资源名
	 * @param typeId
	 * @param resourceId
	 * @return
	 */
	public static String getResourceName(String typeId, String resourceId) {
		return Db.queryStr("select resource_name from tresource_info where type_id='"+typeId+"' and resource_id='"+resourceId+"'");
	}
	
	/**
	 * 通用组件，根据资源类型获取资源哈希表
	 * @param typeId
	 * @return
	 */
	public static Hashtable<String,String> getResourceList(String typeId){
		List<Record> records= Db.find("select resource_id,resource_name from tresource_info where type_id='"+typeId+"'");
		Hashtable<String,String> ret = new Hashtable<String,String>();
		for(Record record:records) {
			ret.put((String)record.get("resource_id"),(String)record.get("resource_name"));
		}
		return ret;
	}
	
	/**
	 * 通用组件，根据资源类型获取下拉资源列表
	 * @param typeId
	 * @return
	 */
	public static List<ResourceInfo> getResourceInfoList(String typeId){
		return ResourceInfo.dao.find("select resource_id,resource_name from tresource_info where type_id='"+typeId+"'");
	}
	
	public void resource_list() {
		setAttr("resourceList", ResourceType.dao.find("select * from tresource_type where is_show = 1"));
		render("/system/resource_list.jsp");
	}
	
	public static void main(String args[]) {
		PropKit.use("config.properties");
		//配置数据库连接池插件
		DruidPlugin dbPlugin=new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password"));
		//orm映射 配置ActiveRecord插件
		ActiveRecordPlugin arp=new ActiveRecordPlugin(dbPlugin);
		arp.setShowSql(PropKit.getBoolean("devMode"));
		dbPlugin.start();
		arp.start();
		System.out.println(getResourceName("PUB001", "1"));
		Hashtable<String, String> a = getResourceList("PUB001");
		System.out.println(a.get("2"));
		for(Iterator<Entry<String, String>> iterator=a.entrySet().iterator();iterator.hasNext();){
			Entry<String,String> entry=iterator.next();
			System.out.println("key---------"+entry.getKey());
			System.out.println("value------------"+entry.getValue());
			}
	}
}
