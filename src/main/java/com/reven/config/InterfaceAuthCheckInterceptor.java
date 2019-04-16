package com.reven.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.reven.controller.common.ResResult;
import com.reven.controller.common.ResResultCode;

/**
     * 微服务间接口访问密钥验证
     * @author xiaochangwei
     *
     */
public class InterfaceAuthCheckInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(getClass());

//    @Autowired
//    StringRedisTemplate stringRedisTemplate;

    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {

    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
        String key = request.getParameter("key");
        if (StringUtils.isEmpty(key)) {
            ResResult result = new ResResult();
            result.setCode(ResResultCode.UNAUTHORIZED).setMessage("签名认证失败001");
            responseResult(response, result);
            return false;
        } else {
//            logger.info("test redis import :" + stringRedisTemplate.opsForValue().get(key));
            logger.info("验证逻辑");
            // TODO 验证逻辑
            return true;
        }
    }

    private void responseResult(HttpServletResponse response, ResResult result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }

}