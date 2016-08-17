#springmvc-api-example------java示例项目
## 项目技术
    基于PalmCall利用SpringMVC Maven SpringJDBC mysql
     spring-data-redis logback elk日志收集 jetty 测试用例等技术开发的示例项目

## 项目功能
- 纯接口 返回json内容
- 上传图片，错误异常处理
- 数据保存和查询 在mysql和redis
- spring注解定时任务，多个tomcat执行定时器时，保证同一时间只有一个执行
- 生成日志文件方便统计 日志收集

## maven运行使用介绍
```
clean compile com.jtool:codegen-builder-plugin:build -X #生成接口的使用文档
clean jetty:run  #jetty运行项目
#需要不同环境启动项目时，修改web.xml里spring.profiles.default的值，根据config包下定义的名字
```

## 测试环境运行
```
cd shell
./uploadSource.sh  #上传代码到测试环境
./restartTomcat.sh #重新启动tomcat
```
