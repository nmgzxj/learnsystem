package cn.jbolt.common.util;

import com.jfinal.kit.StrKit;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

public class ArrayUtil {
	/**
	 * 列表内的数据拼接成字符串
	 * @param array
	 * @param split
	 * @return
	 */
	public static String join2(List<Serializable> array,String split){
		if(array==null||array.size()==0){return null;}
		StringBuilder sb=new StringBuilder();
		sb.append(split);
		for(Serializable s:array){
			sb.append(s.toString()).append(split);
		}
		return sb.toString();
	}
	/**
	 * 列表内的数据拼接成字符串
	 * @param array
	 * @param split
	 * @return
	 */
	public static String join(List<String> array,String split){
		if(array==null||array.size()==0){return null;}
		StringBuilder sb=new StringBuilder();
		sb.append(split);
		for(String s:array){
			sb.append(s).append(split);
		}
		return sb.toString();
	}
	/**
	 * 列表内的数据拼接成字符串
	 * @param list
	 * @param split
	 * @return
	 */
	public static String joinWithIntList(List<Integer> list,String split){
		if(list==null||list.size()==0){return null;}
		StringBuilder sb=new StringBuilder();
		sb.append(split);
		for(Integer s:list){
			sb.append(s).append(split);
		}
		return sb.toString();
	}
	/**
	 * 数组内的数据拼接成字符串
	 * @param array
	 * @param split
	 * @return
	 */
	public static String join(Serializable[] array,String split){
		if(array==null||array.length==0){return null;}
		StringBuilder sb=new StringBuilder();
		sb.append(split);
		for(Serializable s:array){
			sb.append(s.toString()).append(split);
		}
		return sb.toString();
	}
	
	/**
	 * 数组内的数据拼接成字符串
	 * @param array
	 * @param split
	 * @return
	 */
	public static String join(String[] array,String split){
		if(array==null||array.length==0){return null;}
		StringBuilder sb=new StringBuilder();
		sb.append(split);
		for(String s:array){
			sb.append(s).append(split);
		}
		return sb.toString();
	}
	/**
	 * 从字符串分割有效数组
	 * @param str
	 * @param split
	 * @return
	 */
	public static String[] from(String str,String split){
		if(StrKit.isBlank(str)){return new String[0];}
		String[] array=str.split(split);
		if(array.length==0){return array;}
		List<String> list=new ArrayList<String>();
		for(String s:array){
			if(StrKit.isBlank(s)){continue;}
			list.add(s.trim());
		}
		return list.toArray(new String[list.size()]);
	}
	/**
	 * 从字符串分割有效数组 转为Integer
	 * @param str
	 * @param split
	 * @return
	 */
	public static Integer[] toInt(String str,String split){
		if(StrKit.isBlank(str)){return new Integer[0];}
		String[] array=str.split(split);
		if(array.length==0){return new Integer[0];}
		List<Integer> list=new ArrayList<Integer>();
		for(String s:array){
			if(StrKit.isBlank(s)){continue;}
			list.add(Integer.valueOf(s.trim()));
		}
		return list.toArray(new Integer[list.size()]);
	}
	/**
	 * 从字符串分割有效数组 去重版
	 * @param str
	 * @param split
	 * @return
	 */
	public static String[] from2(String str,String split){
		String[] array=from(str, split);
		Set<String> set = new HashSet<String>(Arrays.asList(array));
		return set.toArray(new String[set.size()]);
	}
	/**
	 * 从字符串分割有效数组 去重版
	 * @param str
	 * @param split
	 * @return
	 */
	public static Integer[] toDisInt(String str,String split){
		Integer[] array=toInt(str, split);
		Set<Integer> set = new HashSet<Integer>(Arrays.asList(array));
		return set.toArray(new Integer[set.size()]);
	}
	/**
	 * 判断数组是否包含指定内容
	 * @param arrays
	 * @param targetValue
	 * @return
	 */
	public static  boolean contains(String[] arrays,String targetValue){
		if(arrays==null){
			return false;
		}
		Set<String> set = new HashSet<String>(Arrays.asList(arrays));
		return set.contains(targetValue);
	}
    /**
     * 判断数组是否包含指定内容
     * @param arrays
     * @param targetValue
     * @return
     */
    public static  boolean contains(String[] arrays,Integer targetValue){
    	if(targetValue==null||arrays==null||arrays.length==0){return false;}
        return contains(arrays,targetValue.toString());
    }
    /**
     * List中所有Item对象的指定字段提取构成数组
     * @param list
     * @param columnName
     * @return
     */
    public static <E> Integer[] getIntegerArray(List<E> list,String columnName){
		if(list.size()>0){
			Integer[] arrays=new Integer[list.size()];
			try{
				Class clazz=list.get(0).getClass();
				Field field=clazz.getDeclaredField(columnName);
				if(field==null){
					return null;
				}
				field.setAccessible(true);
				for(int i=0;i<list.size();i++){
					arrays[i]=(Integer) field.get(list.get(i));
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return arrays;
		}else{
			return null;
		}
	}
    /**
     * List中所有Item对象的指定字段提取构成数组
     * @param list
     * @param columnName
     * @return
     */
    public static <E> String[] getStringArray(List<E> list,String columnName){
    	if(list.size()>0){
    		String[] arrays=new String[list.size()];
    		try{
    			Class clazz=list.get(0).getClass();
    			Field field=clazz.getDeclaredField(columnName);
    			if(field==null){
    				return null;
    			}
    			field.setAccessible(true);
    			for(int i=0;i<list.size();i++){
    				arrays[i]=(String) field.get(list.get(i));
    			}
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    		return arrays;
    	}else{
    		return null;
    	}
    }

	public static boolean notEmpty(Serializable[] param) {
		return param!=null&&param.length>0;
	}
	public static boolean isEmpty(Serializable[] param) {
		return param==null||param.length==0;
	}
	public static String merge(String str1,String str2,String split){
		if(StrKit.isBlank(str1)&& StrKit.isBlank(str2)){return null;}
		if(StrKit.isBlank(str1)&& StrKit.notBlank(str2)){return str2;}
		if(StrKit.notBlank(str1)&& StrKit.isBlank(str2)){return str1;}
		String result=null;
		String[] arr1=from(str1, split);
		String[] arr2=from(str2, split);
		if((arr1==null||arr1.length==0)&&(arr2==null||arr2.length==0)){return null;}
		if((arr1==null||arr1.length==0)&&(arr2!=null&&arr2.length>0)){return str2;}
		if((arr1!=null&&arr1.length>0)&&(arr2==null||arr2.length==0)){return str1;}
		Set<String> ids=new HashSet<String>();
		for(String s1:arr1){
			ids.add(s1);
		}
		for(String s2:arr2){
			ids.add(s2);
		}
		String array[]=ids.toArray(new String[ids.size()]);
		result=join(array, split);
		return result;
	}
	public static List<String> listFrom(String str, String split) {
		if(StrKit.isBlank(str)){return Collections.emptyList();}
		String[] array=str.split(split);
		if(array.length==0){return Collections.emptyList();}
		List<String> list=new ArrayList<String>();
		for(String s:array){
			if(StrKit.isBlank(s)){continue;}
			list.add(s.trim());
		}
		return list;
	}

}
