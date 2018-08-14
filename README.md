# SpringBoot Demo
SpringBoot 常用功能演示

## 目录结构规范
* 参考  https://blog.csdn.net/ubuntu64fan/article/details/80555915

## 数据源配置——生产环境配置

## 多数据源配置

## 动态数据源配置

## SpringBoot-tomcat配置优化

## SpringBoot启动脚本——JVM优化
* 参考文档 [url](https://blog.csdn.net/vakinge/article/details/78706679)
* 参考文档 [url](https://github.com/junbaor/shell_script/blob/master/spring-boot.sh)

## mybatis 通用插件及分页插件

* 场景：
* 使用：

## Base类
* BaseController.java
* AbstractService.java IBaseService.java
    https://www.jianshu.com/p/99fcead32d35

## 代码生成器
* 功能：使用mybatis通用Mapper、自动生成dao、sql map文件。
* 功能：BaseService类、利用freemarker模板一键生成Controller、service、service实现类
* 功能：数据库中的字段写有注释, 希望注释能自动生成在model java中。
* 功能：entity自动实现类序列化接口并生成serialVersionUID
    属性文档注释
  toString方法
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
    
## spingmvc json数据日期格式化
* 问题：在springmvc返回json数据的时候默认日期字段显示的是long类型的时间戳、接收日期请求参数无法封装为date对象<br>
* 方案：<br>
* [参考文档]1(https://www.cnblogs.com/yhtboke/p/5653895.html)

## Spring-cache
* 运用场景：
* 使用注意事项：
* 如何使用demo：https://www.cnblogs.com/xiaoping1993/p/7761123.html


## 接口签名认证

## springmvc 基于模板导出excel
* 使用jxls通过模板导出excel
* 参考文档 [官网](http://jxls.sourceforge.net/reference/excel_markup.html)
* 参考文档 [JXLS 2.4.0系列教程（一）简单使用](https://www.cnblogs.com/foxlee1024/p/7616987.html)
* 参考文档 [JXLS 2.4.0系列教程（二）——循环导出一个链表的数据](http://www.cnblogs.com/foxlee1024/p/7617120.html)
作者还提供了其他更复杂的教程，可以通过文章的下一篇查阅，例如分Sheet、嵌套循环、统计、一些bug等
* 参考文档 [](https://blog.csdn.net/sinat_15769727/article/details/78898894)
* 参考文档 [springmvc导出](https://blog.csdn.net/zjl103/article/details/49666101)
## springmvc 无模板导出excel
* 场景：数据模板多、导出的数据统一的格式。例如导出10个表的数据。

## 定时任务
* 基本要求： 异步执行、有线程池控制最大并发执行数量。
* 动态配置场景：动态配置任务，修改任务执行时间后能马上生效（1分钟内也可以，即一天执行一次的任务改为1分钟执行一次，能迅速生效）;
* 参考文档 [动态配置Cron参数](https://blog.csdn.net/zhiweixlw/article/details/78563112)
https://blog.csdn.net/qq_35992900/article/details/80429245
https://blog.csdn.net/qq_36898043/article/details/79560793
https://blog.csdn.net/zhaoyahui_666/article/details/78835128

## echarts入门案例

* [参考文档](https://blog.csdn.net/qq_35641192/article/details/80616099)

##  Junit单元测试
