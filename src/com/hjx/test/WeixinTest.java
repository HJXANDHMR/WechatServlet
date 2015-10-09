package com.hjx.test;

import net.sf.json.JSONObject;

import com.hjx.po.AccessToken;
import com.hjx.util.WeiXinUtil;

public class WeixinTest {

	public static void main(String[] args) {
		AccessToken token = WeiXinUtil.getAccessToken();
		System.out.println("票据:  " + token.getToken());
		System.out.println("有效时间: " + token.getExpires_in());

		/**
		 * 创建菜单时调用
		 */
		String menu = JSONObject.fromObject(WeiXinUtil.initMenu()).toString();
		int result = WeiXinUtil.createMenu(token.getToken(), menu);
		if (result == 0) {
			System.out.println("创建菜单成功");
		} else {
			System.out.println(" 错误码：" + result);
		}

		/**
		 * 删除菜单时调用
		 */
//		int result = WeiXinUtil.deleteMenu(token.getToken());
//		if (result == 0) {
//			System.out.println("删除菜单成功");
//		} else {
//			System.out.println(" 错误码：" + result);
//		}
		
		
	}
}
