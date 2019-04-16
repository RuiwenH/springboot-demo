package com.reven.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.reven.controller.common.ResResult;
import com.reven.model.entity.SystemUpdateHistory;
import com.reven.service.ISystemUpdateHistoryService;

/**
* Created by CodeGenerator on 2018/11/04.
*/
@RestController
@RequestMapping("/system/update/history")
public class SystemUpdateHistoryController {
    @Resource
    private ISystemUpdateHistoryService systemUpdateHistoryService;


    @GetMapping("/list")
    public ResResult list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PageHelper.startPage(page, size);
        List<SystemUpdateHistory> list = systemUpdateHistoryService.findAll();
        PageInfo<SystemUpdateHistory> pageInfo = new PageInfo<SystemUpdateHistory>(list);
        return ResResult.success(pageInfo);
    }
}
