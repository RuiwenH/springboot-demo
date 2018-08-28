package com.reven.service;
import com.reven.model.entity.Demo;

import java.io.OutputStream;

import com.reven.core.IBaseService;


/**
 * @author reven
 */
public interface DemoService extends IBaseService<Demo> {

	void exportExcel(OutputStream out);

}
