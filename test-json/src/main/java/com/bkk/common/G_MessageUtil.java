package com.bkk.common;

import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.bkk.common.msg.ImageMessage;
import com.bkk.common.msg.TextMessage;
import com.bkk.common.msg.VideoMessage;
import com.bkk.controller.G_CenterController;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 各种消息的处理类
 * 
 */

public class G_MessageUtil {
	private final static Logger log = Logger.getLogger(G_MessageUtil.class);
	/**
	 * 文本类型
	 */
	public static final String MESSSAGE_TYPE_TEXT = "text";
	/**
	 * 音乐类型
	 */
	public static final String MESSSAGE_TYPE_MUSIC = "music";
	/**
	 * 图文类型
	 */
	public static final String MESSSAGE_TYPE_NEWS = "news";

	/**
	 * 视频类型
	 */
	public static final String MESSSAGE_TYPE_VIDEO = "video";
	/**
	 * 图片类型
	 */
	public static final String MESSSAGE_TYPE_IMAGE = "image";
	/**
	 * 链接类型
	 */
	public static final String MESSSAGE_TYPE_LINK = "link";
	/**
	 * 地理位置类型
	 */
	public static final String MESSSAGE_TYPE_LOCATION = "location";
	/**
	 * 音频类型
	 */
	public static final String MESSSAGE_TYPE_VOICE = "voice";
	/**
	 * 推送类型
	 */
	public static final String MESSSAGE_TYPE_EVENT = "event";
	/**
	 * 事件类型：subscribe（订阅）
	 */
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
	/**
	 * 事件类型：unsubscribe（取消订阅）
	 */
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
	/**
	 * 事件类型：click（自定义菜单点击事件）
	 */
	public static final String EVENT_TYPE_CLICK = "CLICK";

	/**
	 * 解析微信发来的请求 XML
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> pareXml(HttpServletRequest request) throws Exception {

		// 将解析的结果存储在HashMap中
		Map<String, String> reqMap = new HashMap<String, String>();

		// 从request中取得输入流
		InputStream inputStream = request.getInputStream();
//		byte b[] = new byte[1024];   
//        int len = 0;   
//        int temp=0;          //所有读取的内容都使用temp接收   
//        while((temp=inputStream.read())!=-1){    //当没有读取完时，继续读取   
//            b[len]=(byte)temp;   
//            len++;   
//        }   
//		log.info("content=====>>"+new String(b,0,len));
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

	/**
	 * 响应消息转换成xml返回 文本对象转换成xml
	 */
	public static String textMessageToXml(TextMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	/**
	 * 视频对象的转换成xml
	 * 
	 */
	public static String videoMessageToXml(VideoMessage videoMessage) {
		xstream.alias("xml", videoMessage.getClass());
		return xstream.toXML(videoMessage);
	}

	/**
	 * 图片对象转换成xml
	 * 
	 */

	public static String imageMessageToXml(ImageMessage imageMessage) {
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);

	}

	/**
	 * 拓展xstream，使得支持CDATA块
	 * 
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有的xml节点的转换都增加CDATA标记
				boolean cdata = true;

				@SuppressWarnings("unchecked")
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});

}
