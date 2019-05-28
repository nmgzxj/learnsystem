package com.cjih.learnsystem.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.jfinal.ext.kit.DateKit;

public class BCD {

	
	/**
	 * @功能:测试用例
	 * @参数: 参数
	 */
	public static void main(String[] args) {
		byte[] b = str2rBcd("861001141");
		
		
		for(byte str:b) {
			System.out.printf( "%2X\n", str);
		}
//		System.out.println(rbcd2Str(b));
//		System.out.println(str2rBcdstr("861001141"));
		System.out.println(str2HexStr("LIC1"));
		System.out.println(BCD.getCurrentTime());
		System.out.println(BCD.getTimerBcd(BCD.getCurrentTime()));
	}
	
	public static String getCurrentTime() {
		SimpleDateFormat format = new SimpleDateFormat(DateKit.timeStampPattern);
		return format.format(new Date());
	}
	
	public static String getTimerBcd(String time) {
		String rtn=time.replace("-", "");
		rtn = rtn.replace(":", "");
		rtn = rtn.replace(" ", "");
		rtn = BCD.str2rBcdstr(rtn);
		return rtn;
	}
	
	public static String getTargetType(String numberFormat) {
		String rtn = "";
		if("msisdn".equalsIgnoreCase(numberFormat)) {
			rtn = "00"; 
		}
		else if("imsi".equalsIgnoreCase(numberFormat)) {
			rtn = "01";
		}
		else if("imei".equalsIgnoreCase(numberFormat)) {
			rtn = "02";
		}
		else if("all".equalsIgnoreCase(numberFormat)) {
			rtn = "03";
		}
		else {
			rtn = "ff";
		}
		return rtn;
	}
	
	public static String str2HexStr(String str) {
		String rtn = "";
		for(int i=0;i<str.length();i++) {
			rtn += Integer.toHexString(str.charAt(i));
			if(i<str.length()-1) {
				rtn += " ";
			}
		}
		return rtn;
	}
	
	public static String str2rBcdstr(String str) {
		if(str==null){
			return "";
		}
		byte[] b = str2rBcd(str);
		String rtn = "";
		String tmp = "";
		for(int i=0;i<b.length;i++) {
			tmp = Integer.toHexString(b[i]);
			if(tmp.length()>2)
				tmp = tmp.substring(tmp.length()-2, tmp.length());
			if(tmp.length()<2)
				tmp = "0"+tmp;
			rtn += tmp;
			if(i<b.length-1)
				rtn += " ";
		}
		return rtn; 
	}

	/**
	 * @功能: BCD码转为10进制串(阿拉伯数据)
	 * @参数: BCD码
	 * @结果: 10进制串
	 */
	public static String bcd2Str(byte[] bytes) {
		StringBuffer temp = new StringBuffer(bytes.length * 2);
		byte b = 0;
		for (int i = 0; i < bytes.length; i++) {
			b = (byte) ((bytes[i] & 0xf0) >>> 4);
			if(b!=0x0F && bytes.length-1 != i)//去掉末位的F
				temp.append(b);
			b = (byte) (bytes[i] & 0x0f);
			if(b!=0x0F && bytes.length-1 != i)//去掉末位的F
				temp.append(b);
		}
		String rtn = temp.toString();
		return rtn.substring(0, 1).equalsIgnoreCase("0") ? rtn.substring(1) : rtn;
	}
	
	/**
	 * @功能: BCD码转为10进制串(阿拉伯数据)
	 * @参数: 反序BCD码
	 * @结果: 10进制串
	 */

	public static String rbcd2Str(byte[] bytes) {
		StringBuffer temp = new StringBuffer(bytes.length * 2);
		byte b = 0;
		for (int i = 0; i < bytes.length; i++) {
			b = (byte) (bytes[i] & 0x0f);
			temp.append(b);
			b = (byte) ((bytes[i] & 0xf0) >>> 4);
			if(b!=0x0F && bytes.length-1 != i)//去掉末位的F
				temp.append(b);
		}
		String rtn = temp.toString();
		return rtn.substring(0, 1).equalsIgnoreCase("0") ? rtn.substring(1) : rtn;
	}

	
	/**
	 * @功能: 10进制串转为BCD码
	 * @参数: 10进制串
	 * @结果: BCD码
	 */
	public static byte[] str2Bcd(String asc) {
		int len = asc.length();
		int mod = len % 2;
		if (mod != 0) {
			asc =   asc +"f";
			len = asc.length();
		}
		byte abt[] = new byte[len];
		if (len >= 2) {
			len = len / 2;
		}
		byte bbt[] = new byte[len];
		abt = asc.getBytes();
		int j, k;
		for (int p = 0; p < asc.length() / 2; p++) {
			if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
				j = abt[2 * p] - '0';
			} else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
				j = abt[2 * p] - 'a' + 0x0a;
			} else {
				j = abt[2 * p] - 'A' + 0x0a;
			}
			if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
				k = abt[2 * p + 1] - '0';
			} else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
				k = abt[2 * p + 1] - 'a' + 0x0a;
			} else {
				k = abt[2 * p + 1] - 'A' + 0x0a;
			}
			int a = (j << 4) + k;
			byte b = (byte) a;
			bbt[p] = b;
		}
		return bbt;
	}
	
	
	/**
	 * @功能: 10进制串转为BCD码
	 * @参数: 10进制串
	 * @结果: 反序BCD码
	 */
	public static byte[] str2rBcd(String asc) {
		int len = asc.length();
		int mod = len % 2;
		if (mod != 0) {
			asc =   asc +"f";
			len = asc.length();
		}
		byte abt[] = new byte[len];
		if (len >= 2) {
			len = len / 2;
		}
		byte bbt[] = new byte[len];
		abt = asc.getBytes();
		int j, k;
		for (int p = 0; p < asc.length() / 2; p++) {
			if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
				j = abt[2 * p] - '0';
			} else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
				j = abt[2 * p] - 'a' + 0x0a;
			} else {
				j = abt[2 * p] - 'A' + 0x0a;
			}
			if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
				k = abt[2 * p + 1] - '0';
			} else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
				k = abt[2 * p + 1] - 'a' + 0x0a;
			} else {
				k = abt[2 * p + 1] - 'A' + 0x0a;
			}
			int a = (k << 4) + j;
			byte b = (byte) a;
			bbt[p] = b;
		}
		return bbt;
	}
//	public static void shortToByte_LH(short shortVal, byte[] b, int offset) {  
//		b[0 + offset] = (byte) (shortVal & 0xff);  
//		b[1 + offset] = (byte) (shortVal >> 8 & 0xff);  
//	}
//	
//	public static short byteToShort_HL(byte[] b, int offset)
//	{
//		short result;
//		result = (short)((((b[offset + 1]) << 8) & 0xff00 | b[offset] & 0x00ff));
//		return result;
//	}
//	
//	public static void intToByte_LH(int intVal, byte[] b, int offset) {  
//		b[0 + offset] = (byte) (intVal & 0xff);  
//		b[1 + offset] = (byte) (intVal >> 8 & 0xff);  
//		b[2 + offset] = (byte) (intVal >> 16 & 0xff);  
//		b[3 + offset] = (byte) (intVal >> 24 & 0xff);  
//	}   
//	
//	public static int byteToInt_HL(byte[] b, int offset)
//	{
//		int result;
//		result = (((b[3 + offset] & 0x00ff) << 24) & 0xff000000)
//				| (((b[2 + offset] & 0x00ff) << 16) & 0x00ff0000)
//				| (((b[1 + offset] & 0x00ff) << 8) & 0x0000ff00)
//				| ((b[0 + offset] & 0x00ff));
//		return result;
//	}
}