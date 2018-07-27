package com.reven.service.impl;

import com.reven.dao.DemoCopyMapper;
import com.reven.model.DemoCopy;
import com.reven.service.DemoCopyService;
import com.reven.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/07/27.
 */
@Service
@Transactional
public class DemoCopyServiceImpl extends AbstractService<DemoCopy> implements DemoCopyService {
    @Resource
    private DemoCopyMapper demoCopyMapper;

}
