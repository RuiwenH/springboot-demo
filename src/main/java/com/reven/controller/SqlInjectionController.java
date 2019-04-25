package com.reven.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.reven.controller.common.BaseController;
import com.reven.controller.common.ResResult;
import com.reven.model.entity.Demo;
import com.reven.service.IDemoService;

/**
 * @ClassName:  SqlInjectionController   
 * @Description:sql注入测试
 * @author huangruiwen
 * @date   2019年4月25日
 */
@RestController
@RequestMapping("/sql")
public class SqlInjectionController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(SqlInjectionController.class);
    @Resource
    private IDemoService demoService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**   
     * 测试用例
     *   http://localhost:8082/sql/list?orderBy= name' ; select 1'
     *   http://localhost:8082/sql/list?orderBy=name; drop table t_demo2
     *   http://localhost:8082/sql/list?orderBy=name'; drop table t_demo2;
     *   http://localhost:8082/sql/list?orderBy=name LIMIT ? ;drop table t_demo2 ; --
     * @param page
     * @param size
     * @param orderBy
     * @return      
     */
    @GetMapping("/list")
    public ResResult list(@RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size ,String orderBy) {
        logger.info(orderBy);
        PageInfo<Demo> pageInfo = demoService.find(page, size ,orderBy);
        return ResResult.success(pageInfo);
    }
    
    /**   
     * 测试用例
     *   http://localhost:8082/sql/listMy?orderBy=name desc
     *   http://localhost:8082/sql/listMy?orderBy= name' ; select 1'
     *   http://localhost:8082/sql/listMy?orderBy=name; drop table t_demo2
     *   http://localhost:8082/sql/listMy?orderBy=name'; drop table t_demo2;
     *   http://localhost:8082/sql/listMy?orderBy=name LIMIT ? ;drop table t_demo2 ; --
     * @param page
     * @param size
     * @param orderBy
     * @return      
     */
    @GetMapping("/listMy")
    public ResResult listMy(@RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size ,String orderBy) {
        logger.info(orderBy);
        PageInfo<Demo> pageInfo = demoService.findMy(page, size ,orderBy);
        return ResResult.success(pageInfo);
    }

}
