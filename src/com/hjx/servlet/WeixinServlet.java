package com.hjx.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.hjx.po.TextMessage;
import com.hjx.util.CheckUtility;
import com.hjx.util.MessageUti;

public class WeixinServlet extends HttpServlet {

	/**
	 * 微信服务器将发送GET请求到填写的服务器地址URL上，GET请求携带四个参数signature,timestamp, nonce,echostr
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		PrintWriter out = resp.getWriter();
		if (CheckUtility.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		try {
			Map<String, String> map = MessageUti.xmlToMap(req);
			String fromUserName = map.get("FromUserName");
			String toUserName = map.get("ToUserName");
			String msgType = map.get("MsgType");
			String content = map.get("Content");
			String message = null;

			if (MessageUti.MESSAGE_TEXT.equals(msgType)) {
				if ("1".equals(content)) {
					message = MessageUti.initText(toUserName, fromUserName,
							MessageUti.firstMenu());
				} else if ("2".equals(content)) {
					message = MessageUti.initNewsMessage(toUserName,
							fromUserName);
				} else if ("3".equals(content)) {
					message = MessageUti.initText(toUserName, fromUserName,
							MessageUti.secondMenu());
				} else if ("0".equals(content)) {
					message = MessageUti.initText(toUserName, fromUserName,
							MessageUti.menuText());
				} else if (MessageUti.MESSAGE_TEXT.equals(msgType)) {
					message = MessageUti.initText(toUserName, fromUserName,
							MessageUti.tishi());
				}
			} else if (MessageUti.MESSAGE_EVENT.equals(msgType)) {
				String eventType = map.get("Event");
				if (MessageUti.MESSAGE_SUBSCRIBE.equals(eventType)) {
					message = MessageUti.initText(toUserName, fromUserName,
							MessageUti.menuText());
				}
			}
			System.out.println(message);
			out.print(message);
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}
}
