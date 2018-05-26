package com.bkk.common.msg;

import java.util.HashMap;
import java.util.Map;

import com.bkk.common.G_MessageUtil;

import net.sf.json.JSONObject;

/** 文本消息 */
public class TextMessage extends BaseMessage {
	// 消息内容
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String toRespJson() {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("ToUserName", getToUserName());
		m.put("msgtype", G_MessageUtil.MESSSAGE_TYPE_TEXT);
		Map<String, String> text = new HashMap<String, String>();
		text.put("content", this.content);
		m.put("text", text);
		JSONObject json = JSONObject.fromObject(m);
		return json.toString();
	}

}
