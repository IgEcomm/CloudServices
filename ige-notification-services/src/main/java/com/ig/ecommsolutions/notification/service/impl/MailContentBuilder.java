package com.ig.ecommsolutions.notification.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.ig.ecommsolutions.notification.constants.Constants;


@Service
public class MailContentBuilder {
	
	private TemplateEngine templateEngine;
	
	@Autowired
	public MailContentBuilder(TemplateEngine templateEngine){
		this.templateEngine = templateEngine;
	}

	public String build(){
		Context context = new Context();
		context.setVariable("name", "Ecomm");
		return templateEngine.process(Constants.ECOMM_TEMPLATE, context);
	}
}
