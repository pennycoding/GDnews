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
		map.put("friendName", "ǿ������");
		map.put("comment", "�ܺú�ǿ�󣬺����ģ��ܡ�������");
		map.put("staus", "10��10��");
		map.put("timestamp", "2014��11��17��");
		
		list.add(map);
		
		Map<String ,String> map2=new HashMap<String,String>();
		map2.put("friendName", "�����Ǿ�");
		map2.put("comment", "˵�����������ù������������Ǿ��û��Ǽ�������");
		map2.put("staus", "10��10��");
		map2.put("timestamp", "2014��11��17��");
		
		list.add(map2);
		
		Map<String ,String> map3=new HashMap<String,String>();
		map3.put("friendName", "��������");
		map3.put("comment", "��ʵ�����޻���˵�ˣ�̫ǿ����");
		map3.put("staus", "10��10��");
		map3.put("timestamp", "2014��11��17��");
		
		list.add(map3);
		
		
	}
	
}
