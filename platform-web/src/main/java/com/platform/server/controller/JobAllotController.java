package com.platform.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date 2019-07-17
 * @Author lou
 */
@RestController
public class JobAllotController {

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test(){
		return "test";
	}
}
