package com.project.shop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller 
public class adminItemsController {

	@GetMapping("/admin/newItem")	
		public String newItem() {
		return "/admin/newItem";
	}
	
	@GetMapping("/admin/modifyItem")
		public String modifyItem() {
		return "/admin/modifyItem";
	}
	
	
}