package com.gdnews.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gdnews.bean.ConfessionItem;
import com.gdnews.bean.NewsItem;

public class WebIssue {

	public ArrayList<ConfessionItem> lists;

	public WebIssue(JSONArray array) {
		lists = new ArrayList<ConfessionItem>();

		for (int i = 0; i < array.length(); i++) {
			JSONObject cbject;
			try {

				cbject = array.getJSONObject(i);

				ConfessionItem item = new ConfessionItem(cbject.getString("confession_to"),
						cbject.getString("confession_content"),
						cbject.getString("confession_from"),
						cbject.getString("confession_date"));

				lists.add(item);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}
