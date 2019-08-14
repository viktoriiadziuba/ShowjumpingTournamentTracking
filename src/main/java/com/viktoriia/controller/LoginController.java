package com.viktoriia.controller;

import javax.inject.Inject;

import org.springframework.social.facebook.api.Facebook;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
	 
	private final Facebook facebook;
	
	@Inject
	public LoginController(Facebook facebook) {
		this.facebook = facebook;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		return "home";
	}
}
