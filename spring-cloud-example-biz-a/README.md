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
  ##### 新建[User.java](https://github.com/Jacky-MYD/spring-cloud-example/blob/master/spring-cloud-example-biz-a/src/main/java/com/example/project/Entity/User.java)
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
  ##### 新建[UserRepository.java](https://github.com/Jacky-MYD/spring-cloud-example/blob/master/spring-cloud-example-biz-a/src/main/java/com/example/project/Repository/UserRepository.java), 用于操作数据库
```UserRepository.java
  @Repository
  public interface UserRepository extends JpaRepository<User, Long> {
    List<User> getByUserName(String userName);
    List<User> findByPassword(String password);
  }
```
  ##### 3. Service
  ##### 新建[UserService.java](https://github.com/Jacky-MYD/spring-cloud-example/blob/master/spring-cloud-example-biz-a/src/main/java/com/example/project/Service/UserService.java)，Service用于处理客户端请求的一些业务逻辑。
```UserService.java
  @Service
  public class UserService {
    public Result<Long> login(String userName, String password) {
      Result<Long> result = new Result<>();
      List<User> list = userRepository.getByUserName(userName);
      User user = null;
      if(list != null && list.size() >0) {
        user = list.get(0);
      }
      if (user == null) {	 
        result.setErrCode(-1);
        result.setErrMsg("用户不存在");
      } else if (user.getPassword().equals(password)) {
        System.out.println(userName.toString());
        result.setErrCode(1);
        result.setData(user.getId());
      } else {
        result.setErrCode(-1);
        result.setErrMsg("密码错误");
      }
      return result;
    }
    ...
  }
 ```
   ##### 4. Controller
   ##### 新建[UsreController.java](https://github.com/Jacky-MYD/spring-cloud-example/blob/master/spring-cloud-example-biz-a/src/main/java/com/example/project/Controller/UsreController.java),controller用于编写暴露接口给客户端业务逻辑，并且衔接service层处理相关业务逻辑
```UsreController.java
  @RestController
  @RequestMapping("/api/account")
  public class UsreController {
    @PostMapping("/login")
    public String login(@RequestBody JSONObject data, HttpServletRequest request) {
      System.out.println("===========++======================="+data);
      System.out.println(data.getString("username") + Util.stringMD5(data.getString("password")));
      String username = data.getString("username");
      String password = Util.stringMD5(data.getString("password"));
      Token token = new Token();
      Result<Long> user = userService.login(username, password);
      if (user != null) { // 登录成功生成token并保存token
        String userAgent = request.getHeader("user-agent");
        String token1 = tokenService.generateToken(userAgent, username);
        tokenService.saveToken(token1, user);
        token.setIsLogin("true");
        token.setToken(token1);
        token.setTokenCreatedTime(System.currentTimeMillis());
        token.setTokenExpiryTime(System.currentTimeMillis() + 2*60*60*1000);
      } else {
        token.setIsLogin("false");
      }
      return JSONObject.toJSONString(token);
    }
  }
```
  ##### 5. 其他工具
  ##### 根据所需创建相关工具包[Util.java](https://github.com/Jacky-MYD/spring-cloud-example/blob/master/spring-cloud-example-biz-a/src/main/java/com/example/project/Utils/Util.java), 如，MD5密码加密
```Util.java
   public class Util {
     public static String stringMD5(String input) {
       try {
         // 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
	 MessageDigest messageDigest = MessageDigest.getInstance("MD5");
	 // 输入的字符串转换成字节数组
	 byte[] inputByteArray = input.getBytes();
	 // inputByteArray是输入字符串转换得到的字节数组
	 messageDigest.update(inputByteArray);
	 // 转换并返回结果，也是字节数组，包含16个元素
	 byte[] resultByteArray = messageDigest.digest();
	 // 字符数组转换成字符串返回
	 return byreArrayToHex(resultByteArray);
       } catch (NoSuchAlgorithmException e) {
	 // TODO: handle exception
	 return null;
       }
     }
   }
```
重新启动业务服务A，进行接口测试

   
