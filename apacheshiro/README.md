# Apache Shiro Demo
> 一个基于Apache Shiro搭建的简单Demo，可以在此基础上进行二次开发。

## Installation
sql

创建 `test` 数据库后执行sql如下：

    -- 权限表
    CREATE TABLE permission (
      pid int(11) NOT NULL AUTO_INCREMENT,
      name VARCHAR(255) NOT NULL DEFAULT '',
      url VARCHAR(255) DEFAULT '',
      PRIMARY KEY (pid)
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8;
    
    INSERT INTO permission VALUES ('1', 'add', '');
    INSERT INTO permission VALUES ('1', 'delete', '');
    INSERT INTO permission VALUES ('1', 'edit', '');
    INSERT INTO permission VALUES ('1', 'query', '');
    
    -- 用户表
    CREATE TABLE user (
      uid int(11) NOT NULL AUTO_INCREMENT,
      username VARCHAR(255) NOT NULL DEFAULT '',
      password VARCHAR(255) NOT NULL DEFAULT '',
      PRIMARY KEY (uid)
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8;
    
    INSERT INTO user VALUES ('1', 'admin', '123');
    INSERT INTO user VALUES ('2', 'demo', '123');
    
    -- 角色表
    CREATE TABLE role (
      rid int(11) NOT NULL AUTO_INCREMENT,
      rname VARCHAR(255) NOT NULL DEFAULT '',
      PRIMARY KEY (rid)
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8;
    
    INSERT INTO role VALUES ('1', 'admin');
    INSERT INTO role VALUES ('2', 'customer');
    
    -- 权限角色关系表
    CREATE TABLE permission_role (
      rid int(11) NOT NULL,
      pid int(11) NOT NULL,
      KEY idx_rid(rid),
      KEY idx_pid(pid)
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8;
    
    INSERT INTO permission_role VALUES ('1', '1');
    INSERT INTO permission_role VALUES ('1', '2');
    INSERT INTO permission_role VALUES ('1', '3');
    INSERT INTO permission_role VALUES ('1', '4');
    INSERT INTO permission_role VALUES ('2', '1');
    INSERT INTO permission_role VALUES ('2', '4');
    
    -- 用户角色关系表
    CREATE TABLE user_role (
      uid int(11) NOT NULL,
      rid int(11) NOT NULL,
      KEY idx_rid(rid),
      KEY idx_uid(uid)
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8;
    
    INSERT INTO user_role VALUES (1, 1);
    INSERT INTO user_role VALUES (2, 2);

将项目fork或者clone，下载到本地后如果想直接运行，修改 `application.yml` 文件中数据库登录用户信息后，命令行下进入 `permission-management/apacheshiro/target` 输出命令：`java -jar apacheshiro-0.0.1-SNAPSHOT.jar` 即可启动项目。

相关api：

    localhost:8080/login         登录页面
    localhost:8080/loginUser     登录请求，提供的sql有admin和demo两个用户，密码都是123
    localhost:8080/index         首页，要求登录验证
    localhost:8080/logout        注销
    localhost:8080/unauthorized  没有验证时的提示页面
    localhost:8080/edit          只允许具有edit权限的用户才能访问（admin）
    localhost:8080/admin         只允许具有admin角色的用户才能访问（admin）
    
## Configuration
jdk 1.8

Spring Boot 2.0

Maven 3.5

## Related
博客：[Java安全——Spring Boot集成Apache Shiro环境][1]

## End
感谢您的观看，若是项目对您有所帮助，还望给个star，这是对我最大的鼓励。


  [1]: https://blog.csdn.net/honhong1024/article/details/80155725