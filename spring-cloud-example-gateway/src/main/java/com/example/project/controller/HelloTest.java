/**
 * @authour Jacky
 * @data Dec 21, 2019
 */
package com.example.project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jacky
 *
 */
@RestController
public class HelloTest {

	@GetMapping("hello")
	public String test() {
		return "hello";
	}
}
