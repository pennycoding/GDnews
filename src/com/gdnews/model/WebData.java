package com.gdnews.model;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gdnews.bean.NewsItem;

public class WebData {

	public ArrayList<NewsItem> list;

	public WebData(JSONArray array) {
		list = new ArrayList<NewsItem>();

		for (int i = 0; i < array.length(); i++) {
			JSONObject object;
			try {

				object = array.getJSONObject(i);

				NewsItem item = new NewsItem(object.getString("title"),
						object.getString("desc"),
						object.getInt("catid"),
						object.getString("thumb"),
						object.getString("url"),
						object.getString("username"),
						object.getInt("inputtime"));

				list.add(item);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}