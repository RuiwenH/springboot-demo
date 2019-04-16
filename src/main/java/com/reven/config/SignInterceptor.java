package com.reven.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.reven.controller.common.ResResult;
import com.reven.controller.common.ResResultCode;

@Component
public class SignInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(SignInterceptor.class);

    private static String secret;// 默认密钥，主要用于登陆接口

    @Value("${system.app.secret}")
    public void setSecret(String secret) {
        SignInterceptor.secret = secret;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 验证签名
        boolean pass = validateSign(request);
        if (pass) {
            return true;
        } else {
            logger.warn("签名认证失败，请求接口：{}，请求IP：{}，请求参数：{}", request.getRequestURI(), getIpAddress(request),
                    JSON.toJSONString(request.getParameterMap()));

            ResResult result = new ResResult();
            result.setCode(ResResultCode.UNAUTHORIZED).setMessage("签名认证失败");
            responseResult(response, result);
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
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

    /**
     * 签名认证，规则：
     * 验证时间戳是否有效，比如是服务器时间戳 5分钟之前的请求视为无效；
     * 根据 api_key 得到 sercurity_key
     * 将请求参数按ascii码排序
     * 拼接为a=value&b=value...这样的字符串（不包含sign）
     * 混合密钥（secret）进行md5获得签名，与请求的签名进行比较
     */
    private boolean validateSign(HttpServletRequest request) {
        /**验证时间戳是否有效 **/
        String timestampStr = request.getParameter("timestemp");
        if (StringUtils.isEmpty(timestampStr)) {
            return false;
        }
        long timestamp = -300001;
        try {
            timestamp = Long.parseLong(timestampStr);
        } catch (NumberFormatException e) {
        }
        long nowTime = System.currentTimeMillis(); 
        // 接口请求的时间在300秒钟之前的直接抛弃
        if ((nowTime - timestamp) > 300000) {
            return false;
        }
        String requestSign = request.getParameter("sign");// 获得请求签名，如sign=19e907700db7ad91318424a97c54ed57
        String appkey = request.getParameter("userId");
        String mySecret = secret;// 默认密钥，主要用于登陆接口
        /**根据 api_key 得到 sercurity_key **/
        if (!StringUtils.isEmpty(appkey) && !"/login/chooseCompany".equals(request.getRequestURI())
                && !"/appContacts/shareDetail".equals(request.getRequestURI())
                && !"/resource/shareDetail".equals(request.getRequestURI())
                && !"/customer/shareDetail".equals(request.getRequestURI())) {
            // 发放给不同userid的secret;
            /** TODO 账号更换设备时，将对方挤下来  **/
            String deviceid = request.getParameter("deviceId");
            mySecret = DigestUtils.md5Hex(deviceid + appkey).toUpperCase();
        }

        if (StringUtils.isEmpty(requestSign)) {
            return false;
        }
        List<String> keys = new ArrayList<String>(request.getParameterMap().keySet());
        keys.remove("sign");// 排除sign参数
        Collections.sort(keys);// 排序

        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            sb.append(key).append("=").append(request.getParameter(key)).append("&");// 拼接字符串
        }
        String linkString = sb.toString();
        linkString = StringUtils.substring(linkString, 0, linkString.length() - 1);// 去除最后一个'&'
        // logger.info("服务端拼成参数串{}", linkString);
        // String sign = DigestUtils.md5Hex(linkString + secret);// 混合密钥md5
        //System.out.println("linkString:" + linkString + "----" + "secret:" + mySecret);
        String sign = DigestUtils.md5Hex(linkString + mySecret).toUpperCase();// 混合密钥md5
        //System.out.println("requestSign:" + requestSign);
        //System.out.println("sign:" + sign + "----" + "secret:" + mySecret);
        // logger.info("服务端生成的签名{}", sign);
        return StringUtils.equals(sign, requestSign);// 比较
    }

    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 如果是多级代理，那么取第一个ip为客户端ip
        if (ip != null && ip.indexOf(",") != -1) {
            ip = ip.substring(0, ip.indexOf(",")).trim();
        }

        return ip;
    }

    public static void main(String[] args) {
        System.out.println(DigestUtils
                .md5Hex("00000000-4b87-fd8c-ffff-ffffb965c75310EC8704-34B3-4901-8AB1-8B39D58CFEBA").toUpperCase());
    }
}
