package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.ExampleItems;
import com.example.demo.dao.UpdateDemoData;

@RestController
@RequestMapping("tests")
public class OrganizationController {
	@Autowired
	private UpdateDemoData updateDemoData;
	@Autowired
	private ExampleItems exampleItems;
	@PostMapping(value="/prepareObject")
	public String createDemoDatas() {
		updateDemoData.save();
		return "sucess";
	}
	@PostMapping(value="/delDemoDatas")
	public String delDemoDatas() {
		updateDemoData.deleteAll();
		return "sucess";
	}
	@GetMapping(value="/count")
	public int sumOrderQuantityByDepartment(@RequestParam String name) {
		int count = exampleItems.sumOrderQuantityByDepartment(name);
		return count;
	}
}
