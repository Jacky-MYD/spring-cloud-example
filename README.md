# spring-cloud-example
微服务开发


[1.微服务配置中心(config)和注册中心(eureka)](https://github.com/Jacky-MYD/spring-cloud-example)

[2.登录注册接口实例](https://github.com/Jacky-MYD/spring-cloud-example/tree/master/spring-cloud-example-biz-a)

[3.token生成与校验](https://github.com/Jacky-MYD/spring-cloud-example/tree/master/spring-cloud-example-biz-b)

##### 微服务
- 单一职责：每一个微服务应该是单一独立模块的功能，一个微服务解决一个业务问题(并非只一个接口)，这就呈现了“微”的体现。
- 面向服务：每个服务将自己业务功能进行封装并暴露对外提供服务，服务与服务之间可以相辅相成的作用。

##### 配置中心
![配置中心](https://github.com/Jacky-MYD/spring-cloud-example/blob/master/1577241472170.jpg)

##### 微服务架构
![微服务架构](https://github.com/Jacky-MYD/spring-cloud-example/blob/master/1577241452760.jpg)

### 微服务搭建
#### 1. 创建Maven父项目spring-cloud-examples,用于管理项目依赖包版本。[pox.xml配置](https://github.com/Jacky-MYD/spring-cloud-example/blob/master/pom.xml)
```pox.xml配置
  <parent>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-parent</artifactId>
      <version>2.1.4.RELEASE</version>
  </parent>
  ...
  <dependencyManagement>
      <dependencies>
          <dependency>
              <groupId>org.springframework.cloud</groupId>
              <artifactId>spring-cloud-dependencies</artifactId>
              <version>${spring.cloud.version}</version>
              <type>pom</type>
              <scope>import</scope>
          </dependency>
      </dependencies>
  </dependencyManagement>
```
#### 2. 配置中心
- 在spring-cloud-examples项目下创建子项目spring-cloud-example-config，并添加spring-cloud-config-server核心依赖包。[pox.xml配置](https://github.com/Jacky-MYD/spring-cloud-example/blob/master/spring-cloud-example-config/pom.xml)
```
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-server</artifactId>
  </dependency>
```
- 在项目中的resources目录下添加[application.yml](https://github.com/Jacky-MYD/spring-cloud-example/blob/master/spring-cloud-example-config/src/main/resources/application.yml)，配置如下：
```application.yml
  spring:
    application:
      name: spring-cloud-example-config
    profiles:
      active: native #启用本地配置文件
    cloud:
      config:
        server:
          native:
            search-locations: classpath:/configs/ #配置文件扫描目录

  server:
    port: 8000 #服务端口
```
