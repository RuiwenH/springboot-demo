package com.reven.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.reven.core.Mapper;
import com.reven.model.entity.Demo;

/**
 * @ClassName:  DemoMapper   
 * @author reven
 */
public interface DemoMapper extends Mapper<Demo> {

    List<Demo> findMy(@Param("orderBy")String orderBy);
}