package com.wed.tools;

import java.util.UUID;

public class RandomUtils {

	
	/**
	 * 可用来生成数据库主键
	 */
	public static String getUUID(){ 
        String s = UUID.randomUUID().toString(); 
        System.out.println(s);
        //去掉“-”符号 
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
    } 
	
}
