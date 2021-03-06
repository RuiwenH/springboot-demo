# SpringBoot Demo
SpringBoot 常用功能演示及使用规范，最佳实践。
功能清单：
* 数据源配置——生产环境配置
* 多数据源配置
* 动态数据源配置
* maven构建常用设置
* SpringBoot 启动脚本app.sh
* mybatis 通用插件及分页插件
* Base类——web开发常用基础类
* generator代码生成器
* 基于模板导出excel(jxls)
* 通用excel导入导出工具类
* 定时任务
* springBoot的监控和管理
* xss实践
* sql注入
* 邮件发送（纯文本、html、图片、附件）
* logback不同业务的日志打印到不同文件

## 目录结构规范
* 参考  https://blog.csdn.net/ubuntu64fan/article/details/80555915

## 数据源配置——生产环境配置
* 通用场景配置：
https://blog.csdn.net/u011924665/article/details/78966752

## 多数据源配置

## 动态数据源配置

## maven构建常用设置
* 功能：install忽略单元测试、指定打包的名字、指定编译的java版本和编码格式
* 功能：打包时，输出到指定文件夹
* 功能：本地引用的jar（有些第三方jar，担心其变更代码不修改版本，将其jar放入工程lib目录）
* 功能：打包时，排除部分resources文件
* 功能：将指定文件（一般是部署时的配置文件和shell脚本）复制到指定文件夹
* 功能：打包时指定.js和.css的版本
* 功能：assembly压缩文档
* demo: 详见pom文件

## SpringBoot 启动脚本app.sh
* 功能：设置JVM参数（指定程序运行名称、时区、内存大小、GC回收器设置、gc日志文件）
* 功能：指定配置文件的环境变量
* 功能：start 启动时检查程序是否已经启动
* 功能：stop 停止程序、stop之前会先dump
* 功能：dump系统状态和jvm信息到文件中
* 功能：增加远程调试接口
* 实现：见app.sh
* 参考文档 [url](https://blog.csdn.net/vakinge/article/details/78706679)
* 参考文档 [url](https://github.com/junbaor/shell_script/blob/master/spring-boot.sh)

## mybatis 通用插件及分页插件
* 功能：通用增删改查（By主键、By属性等），见Base类
* 功能：分页查询
* 场景：通用增加、删除、更新示例；通过主键查找；通过属性查找列表；
* 场景：分页查询、分页排序查询
* 示例：demo待完善

## Base类——web开发常用基础类
* BaseController.java   ——基础Controller类
* IBaseService.java AbstractService.java ——基础Service类
* Mapper.java   ——通用Dao成接口类
* ServiceException.java ——自定义业务异常类
* GlobalExceptionHandler.java ——异常处理类
* ResResult.java ——json格式返回包装结果类
* ResResultCode.java ——json格式返回成功失败状态类
* 参考文章：https://www.jianshu.com/p/99fcead32d35

## generator代码生成器
* 功能：使用mybatis通用Mapper、自动生成实体类、XXXMapper.java Dao接口类、XXXMapper.xml sql映射文件。
* 功能：AbstractService类、利用freemarker模板一键生成Controller、service、service实现类
* 功能：数据库中的字段写有注释, 注释能自动生成在实体java类中。
* 功能：entity自动实现类序列化接口并生成serialVersionUID
* 参考文档：

    https://baijiahao.baidu.com/s?id=1590009209762921197&wfr=spider&for=pc
    https://cloud.tencent.com/developer/article/1046135
    https://mapperhelper.github.io/docs/3.usembg/
    https://blog.csdn.net/zsg88/article/details/77620345 OK
    https://blog.csdn.net/u011781521/article/details/78164098 OK
    https://www.cnblogs.com/ygjlch/p/6471924.html xml配置
    https://blog.csdn.net/isea533/article/details/42102297
    
* [MyBatis Generator 配置详解](https://blog.csdn.net/zsq520520/article/details/50952830)
* 最佳实践——关于自定义的sql写在哪里? <br/>
    自动生存的sql难以满足所有的需求，需要增加sql，如果写在mbg生成的文件中，后期重新生成sql map文件时会被覆盖掉（后期需要新增字段等，不建议手动修改文件，容易遗漏）。<br/>
    最佳实践，可以在mbg生成的mapper接口中增加相应方法，mbg重新生成的接口文件无需重新覆盖。自定义的sql文件编写到新的一个xml文件中，例如UserMapperExt.xml文件中，namespace指向同一个mapper接口文件UserMapper.java。这样重复生成覆盖内容将不会影响到原先的内容。<br/>
  自定义的UserMapperExt.xml可以共用mbg生成的BaseResultMap 和BaseConlumnList，即同一个namespace的sql map文件，最终它们就像一个合并起来的文件。<br/>
  也可以考虑把一些公共的sql抽到common_sql.xml文件等小技巧。
  
## 基于模板导出excel(jxls)
* 示例
* 使用jxls通过模板导出excel
* 参考文档 [官网](http://jxls.sourceforge.net/reference/excel_markup.html)
* 参考文档 [JXLS 2.4.0系列教程（一）简单使用](https://www.cnblogs.com/foxlee1024/p/7616987.html)
* 参考文档 [JXLS 2.4.0系列教程（二）——循环导出一个链表的数据](http://www.cnblogs.com/foxlee1024/p/7617120.html)作者还提供了其他更复杂的教程，可以通过文章的下一篇查阅，例如分Sheet、嵌套循环、统计、一些bug等
* [参考文档](https://blog.csdn.net/sinat_15769727/article/details/78898894)
* 参考文档 [springmvc导出](https://blog.csdn.net/zjl103/article/details/49666101)
* [参考文档](https://blog.csdn.net/u010447549/article/details/80787673)
* 参考文档 [官网http://jxls.sourceforge.net](http://jxls.sourceforge.net)
* http测试入口：http://localhost:8082/demo/exportExcel

## 通用excel导入导出工具类
* 场景：导出相关数据，导出的数据统一的格式。例如要导出10个表的数据，如果用模板导出，要写十个模板，而且不方便增加列
* [参考文档] https://blog.csdn.net/l1028386804/article/details/79659605
* 功能特点：指定列头、指定字段、
* 测试用例：main方法测试类TestExcelUtil.java
* 测试用例：excel导出http测试入口,中文文件名场景http://localhost:8082/demo/exportExcelUtil?filename=我的用户
* 测试用例：excel导出http测试入口,特殊字符场景http://localhost:8082/demo/exportExcelUtil?filename=sdk@是的开 关#机
* 测试用例：excel导入测试,http://localhost:8082/static/page/importExcel.html,正常文件场景.xlsx,中间有空行.xlsx，完全为空.xlsx，部分空数据.xlsx，错误格式数据.xlsx，导入第二个sheet页.xlsx

## 定时任务
* 基本要求： 异步执行、有线程池控制最大并发执行数量。
* 动态配置场景：动态配置任务，修改任务执行时间后能马上生效（1分钟内也可以，即一天执行一次的任务改为1分钟执行一次，能迅速生效）;
* 参考文档 [动态配置Cron参数](https://blog.csdn.net/zhiweixlw/article/details/78563112)
https://blog.csdn.net/qq_35992900/article/details/80429245
https://blog.csdn.net/qq_36898043/article/details/79560793
https://blog.csdn.net/zhaoyahui_666/article/details/78835128

## springBoot的监控和管理
* [spring-boot-actuator参考文档](https://blog.csdn.net/l_sail/article/details/70495601)

## xss跨站脚本攻击防护
* 参考文章 https://blog.csdn.net/xingbaozhen1210/article/details/78860079
* 功能：使用jsoup的clean方法清除危险的html代码、或使用HtmlUtils.htmlEscape()将将html代码转义
* 功能：增加开关项是否开启xss拦截器
* 功能：增加开关项，清除还是转义html代码
* 功能：配置忽略的资源或URL——使用正则表达式匹配
* 功能：配置忽略的参数，例如content、*WithHtml
* 代码清单：XssFilter.java、XssHttpServletRequestWrapper.java、JsoupUtil.java
* 配置：WebConfiguration.java 配置XssFilter， application.yml配置见xss.*
* 测试场景：get请求、post请求、文件上传、
* 测试页面：http://localhost:8082/static/page/test_xss.html

## sql注入
* 参考链接：https://www.evernote.com/l/AKrAUxJXmG1E2pjc0fQqId7VsYUvTcUhSfQ/
* sql编写使用预编译的方式。在无法避免使用预编译的地方（order by 、动态表名等），使用sql注入字符串过滤工具类过滤参数
* 工具类：SqlInjectionUtil.java
* 测试入口：SqlInjectionController.java

## 邮件发送（纯文本、html、图片、附件）
* 参考文档 https://www.cnblogs.com/muliu/p/6017622.html
* 参考文档 https://www.cnblogs.com/ysocean/p/7666061.html
* 参考文档 https://blog.csdn.net/sinat_26342009/article/details/89425836

## CSRF攻击

## logback不同业务的日志打印到不同文件
* 参考文档：https://blog.csdn.net/mggwct/article/details/77718122
* 关键配置：定义一个logger ，通过<appender-ref ref="xssAppender" />指定appender

```
<logger name="INFO_XSS" additivity="false" level="INFO">
    <appender-ref ref="xssAppender" />
</logger>
<!--不同业务逻辑的日志打印到不同文件 -->
<appender name="xssAppender"
    class="ch.qos.logback.core.rolling.RollingFileAppender">
    <!-- 正在记录的日志文档的路径及文档名 -->
    <file>${log.path}/xss.log</file>
    <append>true</append>
    <!--日志文档输出格式 -->
    <encoder>
        <pattern>${file.log.pattern}</pattern>
        <charset>UTF-8</charset>
    </encoder>
    <!-- 日志记录器的滚动策略，按日期，按大小记录 https://logback.qos.ch/manual/appenders.html#TimeBasedRollingPolicy -->
    <rollingPolicy
        class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
        <!-- 每天日志归档路径以及格式 -->
        <fileNamePattern>${log.path}/xss-%d{yyyy-MM-dd}.%i.log
        </fileNamePattern>
        <timeBasedFileNamingAndTriggeringPolicy
            class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
            <!-- 避免单个日志过大，不方便打开或下载等 -->
            <maxFileSize>50MB</maxFileSize>
        </timeBasedFileNamingAndTriggeringPolicy>
        <!--日志文档保留个数，如果每天生成一个日志文件，根据日志归档%d{yyyy-MM-dd}的格式，将会保留90天 -->
        <maxHistory>90</maxHistory>
        <!-- 如果该appender的总日志大小超过5GB将会删除最久的日志文件 -->
        <totalSizeCap>5GB</totalSizeCap>
        <!-- 如果保存日志90天（安全规范），可以将totalSizeCap、maxFileSize取消设置，fileNamePattern日期格式为%d{yyyy-MM-dd} -->
    </rollingPolicy>
    <filter class="ch.qos.logback.classic.filter.LevelFilter">
        <level>INFO</level>
        <onMatch>ACCEPT</onMatch>
        <onMismatch>DENY</onMismatch>
    </filter>
</appender>
```
* 使用： 

```
private static Logger loggerXss = LoggerFactory.getLogger("INFO_XSS");
loggerXss.info(url);
```



_________________________________________________________________________________________


## springboot 访问静态资源
https://blog.csdn.net/weixin_42456466/article/details/80688681

## 如何彻底实现前后端彻底分离
https://blog.csdn.net/larger5/article/details/81047869
https://blog.csdn.net/larger5/article/details/81063438

https://blog.csdn.net/wabiaozia/article/details/75092623
## 无模板页面抽出公共部分之js脚本实现方案
https://blog.csdn.net/wabiaozia/article/details/75092623
## 无模板页面抽出公共部分之SSI方案--如何找到源文件
https://www.cnblogs.com/ITGirl00/archive/2013/02/23/SSI.html
https://www.cnblogs.com/NeverCtrl-C/p/8191920.html
https://blog.csdn.net/fgsgsgfgsg/article/details/46860049

# AOP
## 全局的错误处理方式
* 功能：定制错误页面，根据ajax或浏览器页面请求返回不同的结果
* ajax请求错误：http://localhost/testAjax.html
* demo返回错误页面：http://localhost/demo/testException
* demo返回错误页面：参数封装错误http://localhost/demo/testException2?date=20180830
* [参考文章](https://www.cnblogs.com/okokabcd/p/9175797.html)

## Web访问日志记录
* 功能： 利用AOP技术，在所有的controller请求增加切面，记录
* 缺陷：抛出异常时，如何处理？
* [参考文章](https://my.oschina.net/sdlvzg/blog/1517729)

## 日志输出
* 功能：动态修改日志级别——actuator模块
* 功能：日志分级别输出，日志详细设置说明
* 功能：彩色日志
* 实现：见logback-spring.xml、动态修改见：
* 参考文章 [logback 基础使用篇](https://www.cnblogs.com/lixuwu/p/5804793.html)
* 参考文章 [动态修改日志级别](http://www.cnblogs.com/heqiyoujing/p/9470752.html)
* 参考文章 [动态修改日志级别](https://www.cnblogs.com/ncyhl/p/7553067.html)
* 参考文章 [Spring Boot 1.5.x新特性：动态修改日志级别](https://blog.csdn.net/dyc87112/article/details/54866244/)
   设置 management.security.enabled: false
    查看包com.reven 的日志级别，get请求： http://localhost/loggers/com.reven
    更改日志级别，post请求：http://localhost/loggers/com.reven,body：{"configuredLevel": "DEBUG"}

# 缓存使用
* 功能：本地缓存使用
* 功能：多级缓存示例caffine+redis，部分场景可以通过redis监听功能刷新本地缓存
* 功能：spring cache使用示例，使用规范：cacheName使用当前系统名字作为前缀之一，key值使用SpEL表达式包含targetClass
* 缓存场景：缓存的使用最重要的是场景
* 如何使用demo：https://www.cnblogs.com/xiaoping1993/p/7761123.html


# 通用流水号生成（通用单据编号生成）
* 功能：例如生成流水号： 前缀+YYYY+序号，order_2018080001、order_2018080001
* 功能：可以指定前缀，是否使用年份、月份、日期为流水号的一部分。
* demo：http://localhost:8082/serial/number/getSn
* demo2：http://localhost:8082/serial/number/getSnByDate



## SpringBoot-tomcat配置优化

## spingmvc json数据日期格式化
* 问题：在springmvc返回json数据的时候默认日期字段显示的是long类型的时间戳、接收日期请求参数无法封装为date对象<br>
* 方案：<br>
* [参考文档]1(https://www.cnblogs.com/yhtboke/p/5653895.html)

## echarts入门案例
* [参考文档](https://blog.csdn.net/qq_35641192/article/details/80616099)

##  Junit单元测试

## 接口签名认证