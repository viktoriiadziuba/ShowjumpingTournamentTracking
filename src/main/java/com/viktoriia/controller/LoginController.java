package com.viktoriia.controller;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.viktoriia.model.UserBean;
import com.viktoriia.social.providers.FacebookProvider;

@Controller
public class LoginController {

	 @Autowired 
	 FacebookProvider facebookProvider;
	 
	 @RequestMapping(value = { "/","/login" })
	 public String login() {
		 return "login";
	 }
	 
	 @RequestMapping(value = "/facebook", method = RequestMethod.GET)
	 public String loginToFacebook(Model model) {
	 return facebookProvider.getFacebookUserData(model, new UserBean());
	 }
	 
}
