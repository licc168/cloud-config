package com.licc.dove;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RefreshScope
public class WelcomeController {


	@Value("${dove.hello}")
	private String message;


	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		model.put("message", this.message);



		return "welcome";
	}

}