package com.cjih.learnsystem.common.util;

public class PageUtil {

	public static String getPageString(String url, Integer page, Integer totalPage) {
		if(page==null||page<0) {
			page = 1;
		}
		StringBuffer rtn = new StringBuffer("<div>\n");
		rtn.append("    <ul class=\"am-pagination am-pagination-centered\">\n");
		if (page == 1) {
			rtn.append("        <li class=\"am-disabled\">");
		} else {
			rtn.append("        <li>");
		}
		rtn.append("<a href=\""+url);
		rtn.append(page - 1);
		rtn.append("\">上一页</a ></li>\n");
		for (int i = 1; i <= totalPage; i++) {
			if (i == page) {
				rtn.append("        <li class=\"am-active\"><a href=\""+url);
				rtn.append(i);
				rtn.append("\">" + i + "</a></li>\n");
			} else {
				if (i > 2 && i < page - 1) {
					rtn.append("        <li class=\"am-disabled\"><a href=#>...</a></li>\n");
					i = page - 2;
				} else if (i > page + 1 && i < (totalPage - 1)) {
					rtn.append("        <li class=\"am-disabled\"><a href=#>...</a></li>\n");
					i = totalPage - 2;
				} else {
					rtn.append("        <li><a href=\"" + url );
					rtn.append(i);
					rtn.append("\">" + i + "</a ></li>\n");
				}
			}

		}
		if (page == totalPage) {
			rtn.append("        <li class=\"am-disabled\">");
		} else {
			rtn.append("        <li>");
		}

		rtn.append("<a href=\""+url);
		rtn.append(page + 1);

		rtn.append("\">下一页</a ></li>\n");
		rtn.append("    </ul>\n</div>");
		return rtn.toString();
	} 
}
