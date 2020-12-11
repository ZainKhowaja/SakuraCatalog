package com.app.sakura.service.impl;

import com.app.sakura.service.EmailService;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.resource.Emailv31;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
	
	@Value("${mailjet.apiKey}")
	private String apiKey;
	
	@Value("${mailjet.apiSecret}")
	private String apiSecret;
	
	@Value("${config.email}")
	private String email;
	
	@Value("${config.emailName}")
	private String name;
	
	@Override
	@Async
	public void sendLoginAlert() {
		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy HH:mm z");
		String loginDate = formatter.format(new Date());
		MailjetClient client;
	    MailjetRequest request;
	    MailjetResponse response = null;
	    client = new MailjetClient(apiKey, apiSecret, new ClientOptions("v3.1"));
	    request = new MailjetRequest(Emailv31.resource)
	    .property(Emailv31.MESSAGES, new JSONArray()
	    .put(new JSONObject()
	    .put(Emailv31.Message.FROM, new JSONObject()
	    .put("Email", "loginalert@mailinator.com")
	    .put("Name", "Login Alert"))
	    .put(Emailv31.Message.TO, new JSONArray()
	    .put(new JSONObject()
	    .put("Email", email)
	    .put("Name", name)))
	    .put(Emailv31.Message.SUBJECT, "Login Alert")
	    .put(Emailv31.Message.TEXTPART, "Login Alert")
	    .put(Emailv31.Message.HTMLPART, "<center><h3>LOGIN ALERT !!!</h3><br />Someone logged in Sakura Application From! ON : "+loginDate+"</center>")
	    .put(Emailv31.Message.CUSTOMID, "AppGettingStartedTest")));
	    try {
			response = client.post(request);
		} catch (MailjetException | MailjetSocketTimeoutException e) {
			e.printStackTrace();
		}
	    System.out.println(response.getStatus());
	    System.out.println(response.getData());
	}
}