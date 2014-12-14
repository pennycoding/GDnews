package com.gdnews.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DemoCommentData {

	public ArrayList<Map<String,String>> list;
	
	public DemoCommentData()
	{
		
		list=new ArrayList<Map<String,String>>();
		
		Map<String ,String> map=new HashMap<String,String>();
		map.put("friendName", "强而有力");
		map.put("comment", "很好很强大，很无聊，很。。。。");
		map.put("staus", "10顶10赞");
		map.put("timestamp", "2014年11月17日");
		
		list.add(map);
		
		Map<String ,String> map2=new HashMap<String,String>();
		map2.put("friendName", "浑身是劲");
		map2.put("comment", "说不出是哪年用过的网名，但是觉得还是记忆犹新");
		map2.put("staus", "10顶10赞");
		map2.put("timestamp", "2014年11月17日");
		
		list.add(map2);
		
		Map<String ,String> map3=new HashMap<String,String>();
		map3.put("friendName", "动力澎湃");
		map3.put("comment", "我实在是无话可说了，太强大了");
		map3.put("staus", "10顶10赞");
		map3.put("timestamp", "2014年11月17日");
		
		list.add(map3);
		
		
	}
	
}
