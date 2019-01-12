package com.cjih.learnsystem.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateKit extends com.jfinal.ext.kit.DateKit {

	public static String toStr(Date date, String pattern) {
		if(null==date) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	
}
