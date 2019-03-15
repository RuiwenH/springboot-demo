package com.reven.service.impl;

import com.reven.dao.SystemUpdateHistoryMapper;
import com.reven.model.entity.SystemUpdateHistory;
import com.reven.service.ISystemUpdateHistoryService;
import com.reven.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/11/04.
 */
@Service
@Transactional
public class SystemUpdateHistoryServiceImpl extends AbstractService<SystemUpdateHistory> implements ISystemUpdateHistoryService {
    @Resource
    private SystemUpdateHistoryMapper systemUpdateHistoryMapper;

}
