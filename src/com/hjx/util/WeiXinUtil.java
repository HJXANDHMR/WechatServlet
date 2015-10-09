package com.hjx.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.hjx.menu.Button;
import com.hjx.menu.ClickButton;
import com.hjx.menu.Menu;
import com.hjx.menu.ViewButton;
import com.hjx.po.AccessToken;

import net.sf.json.JSONObject;

public class WeiXinUtil {
	private static final String APPID = "wxc4982437783af6d7";
	private static final String APPSECRET = "35530b8f0cc3a2a808151c9784a65b88";

	private static final String ACCESS_TOKEN_URl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	private static final String CREATE_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	private static final String DELETE_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	/**
	 * get请求
	 * 
	 * @param url
	 * @return
	 */
	public static JSONObject doGetStr(String url) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		try {
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity, "UTF-8");
				jsonObject = JSONObject.fromObject(result);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	/**
	 * post请求
	 * 
	 * @param url
	 * @param outStr
	 * @return
	 */
	public static JSONObject doPostStr(String url, String outStr) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		JSONObject jsonObject = null;
		try {
			httpPost.setEntity(new StringEntity(outStr, "UTF-8"));
			HttpResponse response = httpClient.execute(httpPost);
			String result = EntityUtils.toString(response.getEntity(), "UTF-8");
			jsonObject = JSONObject.fromObject(result);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return jsonObject;
	}

	/**
	 * 获取accessToken
	 * 
	 * @return
	 */
	public static AccessToken getAccessToken() {
		AccessToken token = new AccessToken();
		String url = ACCESS_TOKEN_URl.replace("APPID", APPID).replace(
				"APPSECRET", APPSECRET);
		JSONObject jsonObject = doGetStr(url);
		if (jsonObject != null) {
			token.setToken(jsonObject.getString("access_token"));
			token.setExpires_in(jsonObject.getInt("expires_in"));
		}
		return token;
	}

	/**
	 * 组装菜单
	 * @return
	 */
	public static Menu initMenu() {
		Menu menu = new Menu();

		ClickButton button1 = new ClickButton();
		button1.setName("click菜单");
		button1.setType("click");
		button1.setKey("1");

		ViewButton button2 = new ViewButton();
		button2.setName("view菜单");
		button2.setType("view");
		button2.setUrl("http://www.imooc.com/learn/401");

		ClickButton button3 = new ClickButton();
		button3.setName("扫码事件");
		button3.setType("scancode_push");
		button3.setKey("3");

		ClickButton button4 = new ClickButton();
		button4.setName("地理位置");
		button4.setType("location_select");
		button4.setKey("4");

		Button button = new Button();
		button.setName("菜单");
		button.setSub_button(new Button[] { button3, button4 });

		menu.setButton(new Button[]{button1,button2,button});
		return menu;
	}
	
	/**
	 * 创建菜单
	 * @param token
	 * @param menu
	 * @return
	 */
	public static int createMenu(String token,String menu){
		int result =0;
		String url =CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject=doPostStr(url, menu);
		if (jsonObject!=null) {
			result =jsonObject.getInt("errcode");
		}
		return result;
		
	}
	
	public static int deleteMenu(String token){
		String url=DELETE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject=doGetStr(url);
		int result =0;
		if (jsonObject!=null) {
			result =jsonObject.getInt("errcode");
			
		}
		
		return result;
		
	}
}
