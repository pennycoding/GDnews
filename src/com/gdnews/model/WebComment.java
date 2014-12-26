package com.gdnews.model;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gdnews.bean.CommentItem;;

public class WebComment {

	public ArrayList<CommentItem> list;

	public WebComment(JSONArray array) {
		list = new ArrayList<CommentItem>();

		for (int i = 0; i < array.length(); i++) {
			JSONObject object;
			try {

				object = array.getJSONObject(i);

				CommentItem item = new CommentItem(object.getString("comment_content"),
						object.getString("user_name"),
						object.getString("user_image"),
						object.getString("comment_date"));

				list.add(item);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
