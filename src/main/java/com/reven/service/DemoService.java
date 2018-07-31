package com.reven.service;
import com.reven.model.entity.Demo;

import java.io.OutputStream;

import com.reven.core.IBaseService;


/**
 * Created by CodeGenerator on 2018/07/27.
 */
public interface DemoService extends IBaseService<Demo> {

	void exportExcel(OutputStream out);

}
