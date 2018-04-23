# Spring Security Demo
> 一个基于Spring Security5搭建的简单Demo。

## Installation
将项目fork或者clone，下载到本地后如果想直接运行，命令行下进入 `permission-management/springsecurity/target` 输出命令：`java -jar springsecurity-0.0.1-SNAPSHOT.jar` 即可启动项目。

相关api：

    localhost:8080/         项目主路径，不进行权限验证
    localhost:8080/test1    需要权限验证，只允许admin（密码admin）用户通过
    localhost:8080/logout   注销
    
## Configuration
jdk 1.8
Spring Boot 2.0
Spring Security 5.0
Maven 3.5

## Related
博客：[Java安全——一步步搭建Spring Security环境][1]

## End
感谢您的观看，若是项目对您有所帮助，还望给个star，这是对我最大的鼓励。

  [1]: https://blog.csdn.net/honhong1024/article/details/80056394