package com.reven.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reven.model.DemoCopy;
import com.reven.service.DemoCopyService;
import com.reven.controller.common.ResResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
* Created by CodeGenerator on 2018/07/27.
*/
@RestController
@RequestMapping("/demo/copy")
public class DemoCopyController {
    @Resource
    private DemoCopyService demoCopyService;

    @PostMapping("/add")
    public ResResult add(DemoCopy demoCopy) {
        demoCopyService.save(demoCopy);
        return ResResult.success();
    }

    @PostMapping("/delete")
    public ResResult delete(@RequestParam Integer id) {
        demoCopyService.deleteById(id);
        return ResResult.success();
    }

    @PostMapping("/update")
    public ResResult update(DemoCopy demoCopy) {
        demoCopyService.update(demoCopy);
        return ResResult.success();
    }

    @GetMapping("/detail")
    public ResResult detail(@RequestParam Integer id) {
        DemoCopy demoCopy = demoCopyService.findById(id);
        return ResResult.success(demoCopy);
    }

    @GetMapping("/list")
    public ResResult list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<DemoCopy> list = demoCopyService.findAll();
        PageInfo<DemoCopy> pageInfo = new PageInfo<DemoCopy>(list);
        return ResResult.success(pageInfo);
    }
}
