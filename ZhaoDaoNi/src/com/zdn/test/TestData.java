package com.zdn.test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.zdn.bean.RecentChat;
import com.zdn.util.FileUtil;

public class TestData {
	private static String[] names={"songhuiqiao.jpg","zhangzetian.jpg","songqian.jpg","hangxiaozhu.jpg","jingtian.jpg"
		,"liuyifei.jpg","kangyikun.jpg","dengziqi.jpg"};
	private static String dir=FileUtil.getRecentChatPath();
	public static List<RecentChat> getRecentChats(){
		String path=FileUtil.getRecentChatPath();
		List<RecentChat> chats=new ArrayList<RecentChat>();
		chats.add(new RecentChat("宋慧", "好好学习天天向上", "12:30", path+names[0]));
		chats.add(new RecentChat("章泽", "好好学习天天向上", "16:30", path+names[1]));
		chats.add(new RecentChat("宋茜", "好好学习天天向上", "17:30", path+names[2]));
		chats.add(new RecentChat("韩孝", "好好学习天天向上", "昨天", path+names[3]));
		chats.add(new RecentChat("景甜", "好好学习天天向上", "星期", path+names[4]));
		chats.add(new RecentChat("刘亦", "好好学习天天向上", "17:30", path+names[5]));
		chats.add(new RecentChat("康", "好好学习天天向上",  "昨天", path+names[6]));
		chats.add(new RecentChat("邓紫", "好好学习天天向上", "星期", path+names[7]));
		return chats;
	}
	
	public static HashMap<String, String> getFriends(){
		HashMap<String, String> maps=new HashMap<String, String>();
		maps.put("宋慧", dir+"songhuiqiao.jpg");
		maps.put("章泽", dir+"zhangzetian.jpg");
		maps.put("宋茜", dir+"songqian.jpg");
		maps.put("韩孝", dir+"hangxiaozhu.jpg");
		maps.put("景甜", dir+"jingtian.jpg");
		maps.put("刘亦", dir+"liuyifei.jpg");
		maps.put("康", dir+"kangyikun.jpg");
		maps.put("邓紫", dir+"dengziqi.jpg");
		
		return maps;
	}
}
