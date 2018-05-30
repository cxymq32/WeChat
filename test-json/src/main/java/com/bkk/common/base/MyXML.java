package com.bkk.common.base;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class MyXML {
	/** 解析微信发来的请求 XML-返回map */
	public static Map<String, String> pareXml(InputStream inputStream) throws Exception {
		// 将解析的结果存储在HashMap中
		Map<String, String> reqMap = new HashMap<String, String>();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();
		// 遍历所有的子节点取得信息类容
		for (Element elem : elementList) {
			reqMap.put(elem.getName(), elem.getText());
		}
		// 释放资源
		inputStream.close();
		inputStream = null;
		return reqMap;
	}

	/** 解析XML-返回固节点值 */
	public static String parseXML(String xml, String key) throws Exception {
		// 创建SAXReader的对象reader
		SAXReader reader = new SAXReader();
		// 通过reader对象的read方法加载books.xml文件,获取docuemnt对象。
		Document document = reader.read(new ByteArrayInputStream(xml.getBytes()));
		// 通过document对象获取根节点bookstore
		Element bookStore = document.getRootElement();
		// 通过element对象的elementIterator方法获取迭代器
		Iterator it = bookStore.elementIterator();
		// 遍历迭代器，获取根节点中的信息（书籍）
		while (it.hasNext()) {
			Element next = (Element) it.next();
			if (key.equals(next.getName())) {
				return next.getTextTrim();
			}
		}
		return null;
	}
}
