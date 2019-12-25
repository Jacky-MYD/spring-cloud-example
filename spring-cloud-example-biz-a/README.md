## 业务服务
- 导入相关依赖包
首先我们先导入需要用到的依赖包，如[pom.xml](https://github.com/Jacky-MYD/spring-cloud-example/blob/master/spring-cloud-example-biz-a/pom.xml)配置
```pom.xml
  <!-- jdbc -->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
  </dependency>
  <!-- jpa -->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
  </dependency>
  ...
```
- 修改[bootstrap.yml](https://github.com/Jacky-MYD/spring-cloud-example/blob/master/spring-cloud-example-biz-a/src/main/resources/bootstrap.yml)配置文件

  连接mysql数据库，使用官方jpa
```
  spring:
    datasource:
      url: jdbc:mysql://localhost:3306/mysqlTest?characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8
      driver-class-name: com.mysql.cj.jdbc.Driver
      username: root
      password: 123456
    jpa:
      show-sql: true
      hibernate:
        ddl-auto: update
    cloud:
      config:
        name: spring-cloud-example-biz-a #配置文件名称，多个通过逗号分隔
        uri: http://localhost:8000 #Config Server服务地址
```
- 创建相关[目录](https://github.com/Jacky-MYD/spring-cloud-example/tree/master/spring-cloud-example-biz-a/src/main/java/com/example/project)，如：Controller、Service、Entity等等
  ##### 1. Entity(实体)
  ##### 新建[User实体](https://github.com/Jacky-MYD/spring-cloud-example/blob/master/spring-cloud-example-biz-a/src/main/java/com/example/project/Entity/User.java)
```Usre.java
  @Entity
  @NoArgsConstructor
  @AllArgsConstructor
  @Table(name = "user")
  public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String userName;
    @Column
    private String password;
    // 省略了set和get方法
  }
```
  ##### 2. Repository
  ##### 新建[UserRepository](https://github.com/Jacky-MYD/spring-cloud-example/blob/master/spring-cloud-example-biz-a/src/main/java/com/example/project/Repository/UserRepository.java), 用于操作数据库
```
  @Repository
  public interface UserRepository extends JpaRepository<User, Long> {
    List<User> getByUserName(String userName);
    List<User> findByPassword(String password);
  }
```
