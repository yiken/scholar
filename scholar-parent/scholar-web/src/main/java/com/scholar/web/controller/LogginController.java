package com.scholar.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/** 
 *
 * @author yilean
 * @date 2018年5月2日 下午1:24:01  
 */
@Controller
public class LogginController {

    @RequestMapping("/login")
    public Object login() {
        return "index";
    }
}
