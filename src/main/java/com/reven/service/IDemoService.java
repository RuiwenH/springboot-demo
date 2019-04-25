package com.reven.service;
import java.io.OutputStream;

import com.github.pagehelper.PageInfo;
import com.reven.core.IBaseService;
import com.reven.model.entity.Demo;


/**
 * @author reven
 */
public interface IDemoService extends IBaseService<Demo> {

	void exportExcel(OutputStream out);

	PageInfo<Demo> findAll(Integer page, Integer size);

    PageInfo<Demo> find(Integer page, Integer size, String orderBy);

    PageInfo<Demo> findMy(Integer page, Integer size, String orderBy);

}
