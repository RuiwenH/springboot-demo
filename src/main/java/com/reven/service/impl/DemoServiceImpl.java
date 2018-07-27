package com.reven.service.impl;

import com.reven.dao.DemoMapper;
import com.reven.model.entity.Demo;
import com.reven.service.DemoService;
import com.reven.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/07/27.
 */
@Service
@Transactional
public class DemoServiceImpl extends AbstractService<Demo> implements DemoService {
    @Resource
    private DemoMapper demoMapper;

}
