package com.hjx.po;

public class AccessToken {
 private String token;
 private int expires_in;
public String getToken() {
	return token;
}
public void setToken(String token) {
	this.token = token;
}
public int getExpires_in() {
	return expires_in;
}
public void setExpires_in(int expires_in) {
	this.expires_in = expires_in;
} 
}
