/**
 * @authour Jacky
 * @data Dec 19, 2019
 */
package com.example.project.Service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "bizb")
public interface RemoteService {
	/**
     * 调用服务B的hello方法
     *
     * @return
     */
    @GetMapping("/hello")
    String sayHello();
}
