package com.ys.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ys.service.ImgService;

@Controller
public class ImgController {
	@Resource
	private ImgService imgServiceImpl;
	@RequestMapping("show")
	public String show(Model model){
		model.addAttribute("list", imgServiceImpl.selAll());
		
		return "index.jsp";
	}
	
	
}
