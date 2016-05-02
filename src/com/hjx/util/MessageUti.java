package com.hjx.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.hjx.po.News;
import com.hjx.po.NewsMessage;
import com.hjx.po.TextMessage;
import com.thoughtworks.xstream.XStream;

public class MessageUti {
	public static final String MESSAGE_NEWS = "news";
	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_LOCATION = "location";
	public static final String MESSAGE_EVENT = "event";
	public static final String MESSAGE_SUBSCRIBE = "subscribe";
	public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
	public static final String MESSAGE_CLICK = "CLICK";
	public static final String MESSAGE_VIEW = "VIEW";


	/**
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */

	public static Map<String, String> xmlToMap(HttpServletRequest request)
			throws IOException, DocumentException {
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();

		InputStream ins = request.getInputStream();
		Document doc = reader.read(ins);
		Element root = doc.getRootElement();
		List<Element> list = root.elements();
		for (Element e : list) {
			map.put(e.getName(), e.getText());
		}
		ins.close();
		return map;

	}

	/**
	 * 将文本消息对象转为消息对象
	 * 
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);

	}

	public static String initText(String toUserName, String fromUserName,
			String content) {
		TextMessage text = new TextMessage();
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setMsgType(MESSAGE_TEXT);
		text.setCreateTime(new Date().getTime() + " ");
		text.setContent(content);
		return textMessageToXml(text);
	}

	/**
	 * 主菜单
	 * 
	 * @return
	 */
	public static String menuText() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("欢迎你的关注，请按照菜单提示进行操作：\n\n");
		stringBuffer.append("1.微信公众号介绍\n");
		stringBuffer.append("2.慕课网介绍\n");
		stringBuffer.append("3.联系我吧\n\n");
		stringBuffer.append("回复0调出主菜单");
		return stringBuffer.toString();
	}

	public static String firstMenu() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("本公众号完全是由本人亲自开发的，只作为学习使用，不能用于任何商业用途");
		return stringBuffer.toString();
	}

	public static String secondMenu() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("邮箱：975464817@qq.com");
		return stringBuffer.toString();
	}
	
	

	/**
         * 提示消息，当用户随便输入text时，会调用这个方法
 	 * @return
	 */
	public static String tishi() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("请按照正常操作来执行，调出主菜单请按0");
		return stringBuffer.toString();
	}

	/**
	 * 图文消息转化为xml
	 * 
	 * @param newsMessage
	 * @return
	 */
	public static String newsMessageToXml(NewsMessage newsMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new News().getClass());
		return xstream.toXML(newsMessage);

	}
	/**
	 * 图文消息的组装
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */

	public static String initNewsMessage(String toUserName, String fromUserName) {
		String message = null;
		List<News> newsList = new ArrayList<News>();
		NewsMessage newsMessage = new NewsMessage();

		News news = new News();
		news.setTitle("慕课网介绍");
		news.setDescription("慕课网是垂直的互联网IT技能免费学习网站。以独家视频教程、在线编程工具、学习计划、问答社区为核心特色。在这里，你可以找到最好的互联网技术牛人，也可以通过免费的在线公开视频课程学习国内领先的互联网IT技术。慕课网课程涵盖前端开发、PHP、Html5、Android、iOS、Swift等IT前沿技术语言，包括基础课程、实用案例、高级分享三大类型，适合不同阶段的学习人群。以纯干货、短视频的形式为平台特点，为在校学生、职场白领提供了一个迅速提升技能、共同分享进步的学习平台。");
		news.setPicUrl("http://hjx.tunnel.mobi/WechatServlet/image/test1.png");
		news.setUrl("www.imooc.com");

		newsList.add(news);
		
		News news2 = new News();
		news2.setTitle("知乎精选");
		news2.setDescription("刚拿到驾照的人上高速有潜在危险吗？");
		news2.setPicUrl("http://hjx.tunnel.mobi/WechatServlet/image/test1.png");
		news2.setUrl("http://www.zhihu.com/question/29098290#answer-12688555");

		newsList.add(news2);

		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime() + " ");
		newsMessage.setMsgType(MESSAGE_NEWS);
		newsMessage.setArticles(newsList);
		newsMessage.setArticleCount(newsList.size());

		message = newsMessageToXml(newsMessage);
		return message;
	}

}
