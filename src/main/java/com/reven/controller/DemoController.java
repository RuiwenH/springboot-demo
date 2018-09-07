package com.reven.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.reven.controller.common.BaseController;
import com.reven.controller.common.JxlsExcelView;
import com.reven.controller.common.ResResult;
import com.reven.model.entity.Demo;
import com.reven.service.DemoService;

/**
 * @ClassName: DemoController
 * @author reven
 */
@RestController
@RequestMapping("/demo")
public class DemoController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(DemoController.class);
    @Resource
    private DemoService demoService;

    @GetMapping("/testException")
    public ResResult testException() {
        System.out.println(1 / 0);
        return ResResult.success();
    }

    @GetMapping("/testException1")
    public ResResult testException1() {
        Integer.parseInt("ssss");
        return ResResult.success();
    }

    /**
     * 测试参数绑定错误异常是否捕获
     * 
     * @param a
     * @return
     */
    @GetMapping("/testException2")
    public ResResult testException2(Date date) {
        System.out.println(date);
        return ResResult.success(date);
    }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @PostMapping("/add")
    public ResResult add(Demo demo) {
        demoService.save(demo);
        return ResResult.success();
    }

    @PostMapping("/delete")
    public ResResult delete(@RequestParam Integer id) {
        demoService.deleteById(id);
        return ResResult.success();
    }

    @PostMapping("/update")
    public ResResult update(Demo demo) {
        demoService.update(demo);
        return ResResult.success();
    }

    @GetMapping("/detail")
    public ResResult detail(@RequestParam Integer id) {
        Demo demo = demoService.findById(id);
        return ResResult.success(demo);
    }

    @GetMapping("/list")
    public ResResult list(@RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Demo> list = demoService.findAll();
        logger.info("ServerIp={}", getServerIpAddress());
        logger.info("UserIp={}", getIpAddress());
        logger.error(list.toString());
        logger.warn(list.toString());
        logger.info(list.toString());
        logger.debug(list.toString());
        logger.trace(list.toString());
        PageInfo<Demo> pageInfo = new PageInfo<Demo>(list);
        return ResResult.success(pageInfo);
    }

    @RequestMapping(value = "/exportExcel")
    public ModelAndView export() {
        Map<String, Object> model = new HashMap<>(4);
        model.put("name", "Reven001");
        model.put("age", 18);
        // queryUser()为数据获取的方法
        List<Demo> list = demoService.findAll();
        if (list == null || list.size() == 0) {
            // list为空，会报错
            model.put("demoList", new ArrayList<Demo>(0));
        } else {
            model.put("demoList", list);
        }
        return new ModelAndView(new JxlsExcelView("static/excel/t_template.xls", "demo导出"), model);
    }

    @RequestMapping(value = "/echartData")
    @ResponseBody
    public ModelAndView echart() {
        Map<String, Object> model = new HashMap<>(4);
        model.put("name", "Reven001");
        model.put("age", 18);
        // queryUser()为数据获取的方法
        List<Demo> list = demoService.findAll();
        if (list == null || list.size() == 0) {
            // list为空，会报错
            model.put("demoList", new ArrayList<Demo>(0));
        } else {
            model.put("demoList", list);
        }
        return new ModelAndView(new JxlsExcelView("static/excel/t_template.xls", "demo导出"), model);
    }
}
