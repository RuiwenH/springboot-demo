package com.reven.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.google.common.collect.Maps;

/**
 * @ClassName: WebConfiguration
 * @Description: web配置 相当于web.xml
 * @author huangruiwen
 * @date 2019年4月9日
 */
@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Value("${xss.on}")
    private boolean xssOn;

    @Value("${xss.url.excludes}")
    private String xssUrlExcludes;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/ueditor/jsp/upload/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/upload");
    }

    /**
     * xss过滤拦截器
     */
    @Bean
    public FilterRegistrationBean xssFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new XssFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.setEnabled(xssOn);
        filterRegistrationBean.addUrlPatterns("/*");
        Map<String, String> initParameters = Maps.newHashMap();
        initParameters.put("excludes", xssUrlExcludes);
        initParameters.put("excludeParameter", "context,*WithHtml");
        filterRegistrationBean.setInitParameters(initParameters);
        return filterRegistrationBean;
    }
}
