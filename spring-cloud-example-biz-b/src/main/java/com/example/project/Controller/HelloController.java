/**
 * @authour Jacky
 * @data Dec 19, 2019
 */
package com.example.project.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jacky
 *
 */
@RestController
//@RequestMapping("/hello")
public class HelloController {
	/**
     * 示例方法
     *
     * @return
     */
    @GetMapping("/hello")
    public String sayHello() {
    	System.out.println("===============hello=============");
        return "Hello,This is Biz-B Service.";
    }
}
