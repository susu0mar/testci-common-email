package org.apache.commons.mail;

import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;

public class EmailConcrete extends Email {

	public Email setMsg(String msg) throws EmailException{
		
		return null;
	}
	
	public Map<String, String> getHeaders(){
		
		return this.headers;
	}
	
	public String getContentType() {
		
		return this.contentType;
	}
	
	public List<InternetAddress> getReplyList(){
		
		return this.replyList;
	}
}
