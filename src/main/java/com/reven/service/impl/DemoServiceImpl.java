package com.reven.service.impl;

import java.io.OutputStream;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reven.core.AbstractService;
import com.reven.dao.DemoMapper;
import com.reven.model.entity.Demo;
import com.reven.service.DemoService;

/**
 * Created by CodeGenerator on 2018/07/27.
 */
@Service
@Transactional
public class DemoServiceImpl extends AbstractService<Demo> implements DemoService {
	@Resource
	private DemoMapper demoMapper;

	@Override
	public void exportExcel(OutputStream out) {
		//TODO
	}

}
