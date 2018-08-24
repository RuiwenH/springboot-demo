package com.reven.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.reven.controller.common.BaseController;

@Controller
public class IndexController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping({ "/index", "/" })
    public String index(Model model) {
        model.addAttribute("hello", "张三" + new Date());
        String ServerIp = getServerIpAddress();
        String UserIp = getIpAddress();
        model.addAttribute("ServerIp", ServerIp);
        model.addAttribute("UserIp", UserIp);
        logger.info("ServerIp={}", ServerIp);
        logger.info("UserIp={}", UserIp);
        return "index"; // 返回 /templates/index.html页面
    }
}
