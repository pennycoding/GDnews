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

				NewsItem item = new NewsItem(object.getString("news_title"),
						object.getString("news_summary"),
						object.getInt("news_time"),
						object.getInt("news_comment"),
						object.getString("news_images"),
						object.getString("news_url"));

				list.add(item);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}