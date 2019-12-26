### 业务服务B
  该服务将阐述Jwt生成token和校验的过程，从新编写登录注册等接口。
##### [pom.xml](https://github.com/Jacky-MYD/spring-cloud-example/blob/master/spring-cloud-example-biz-b/pom.xml)添加所需的依赖包，如：mysql、jdbc、jpa等等
```pom.xml
<!-- mysql -->
<dependency>
  <groupId>mysql</groupId>
  <artifactId>mysql-connector-java</artifactId>
</dependency>

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
##### [bootstrap.yml](https://github.com/Jacky-MYD/spring-cloud-example/blob/master/spring-cloud-example-biz-b/src/main/resources/bootstrap.yml)连接数据库,配置jpa等等
```bootstrap.yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/autoTest?characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: root
    password: 123456
...
```
##### 创建所需的目录，目录结构如下：
![目录结构](https://github.com/Jacky-MYD/spring-cloud-example/blob/master/spring-cloud-example-biz-b/1577326960207.jpg)
##### 代码编写
- [Jwt](https://github.com/Jacky-MYD/spring-cloud-example/blob/master/spring-cloud-example-biz-b/src/main/java/com/example/project/Utils/JwtUtil.java)的引入，生成token以及token的校验
```JwtUtil.java
public class JwtUtil {
	// 过期时间为1小时
	private static final long EXPIRE_TIME = 1*60*60*1000;
	// token私钥
	private static final String TOKEN_SECRET = "abc";
	/**
	 * 生成签名，1小时后过期
	 * @param username
	 * @param id
	 * @return
	 */
	public static String sign(String username, Long id) {
		// 过期时间
		Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
		// 私钥及加密算法
		Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
		// 设置头信息
		HashMap<String, Object> header = new HashMap<>(2);
		header.put("typ", "JWT");
		header.put("alg", "HS256");
		// 附带username和userId生成签名
		return JWT.create().withHeader(header).withClaim("username", username)
				.withClaim("userId", id).withExpiresAt(date).sign(algorithm);
	}
	/**
   * token校验
   */
	@SuppressWarnings("unused")
	public static boolean verity(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
			JWTVerifier verifier = JWT.require(algorithm).build();
			DecodedJWT jwt = verifier.verify(token);
			return true;
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
			return false;
		} catch (JWTVerificationException e) {
			// TODO: handle exception
			return false;
		}
	}
}
```
- 对密码进行[MD5](https://github.com/Jacky-MYD/spring-cloud-example/blob/master/spring-cloud-example-biz-b/src/main/java/com/example/project/Utils/Util.java)加密
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
	
	public static String byreArrayToHex(byte[] byteArray) {
		// 首先初始化一个字符数组，用来存放每个16进制字符
		char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F' };
		// new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
		char[] resultCharArray = new char[byteArray.length * 2];
		// 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
		int index = 0;
		for (byte b : byteArray) {
			resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];
			resultCharArray[index++] = hexDigits[b & 0xf];
		}
		// 字符数组组合成字符串返回
		return new String(resultCharArray);
	}
}
```
- [Entity实体](https://github.com/Jacky-MYD/spring-cloud-example/blob/master/spring-cloud-example-biz-b/src/main/java/com/example/project/Entity/User.java)，定义表结构
```User.java
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
	/** id */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** 昵称 */
	@Column(nullable = false)
	private String username;
	
	/** 手机 */
	@Column(nullable = false)
	private String mobile;

	/** 密码 */
	@Column(nullable = false)
	private String password;
	
	/** 创建时间 */
	@JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
//	@Column(nullable = false)
	private Date created; 
}
...
```
- [Repository](https://github.com/Jacky-MYD/spring-cloud-example/blob/master/spring-cloud-example-biz-b/src/main/java/com/example/project/Repository/UserRepository.java)
```UserRepository.java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	List<User> getByUsername(String username);
}
```
- [AjaxResult](https://github.com/Jacky-MYD/spring-cloud-example/blob/master/spring-cloud-example-biz-b/src/main/java/com/example/project/Dto/AjaxResult.java)，用于定义接口的response
```AjaxResult.java
public class AjaxResult extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	public static final String CODE_TAG = "code";
	public static final String MSG_TAG = "msg";
	public static final String DATA_TAG = "data";
	// 状态类型
	public enum Type {
		SUCCESS(0), // 成功
		FAIL(1),    // 失败
		WARN(301),  // 警告
		ERROR(500); // 错误
		private final int value;
		Type(int value) {
			this.value = value;
		}
		public int value() {
			return this.value;
		}
  }
  ...
}
```
- [Service](https://github.com/Jacky-MYD/spring-cloud-example/tree/master/spring-cloud-example-biz-b/src/main/java/com/example/project/Service)，编写接口的service层。
```UserService.java
@Service
public class UserService {
	@Autowired
	UserRepository userRepository;
	/**
	 * 用户登录
	 * @param username
	 * @return
	 */
	public User checkUser(String username) {
		List<User> list = userRepository.getByUsername(username);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	public User insert(User user) {
		String username = user.getUsername();
		Long time = System.currentTimeMillis();
		user.setCreated(new Date(time));
		String password = Util.stringMD5(user.getPassword()); // 对注册密码进行MD5加密
		user.setPassword(password);
		userRepository.save(user);
		return this.geUser(username);
  }
}
```
- [Controller](https://github.com/Jacky-MYD/spring-cloud-example/blob/master/spring-cloud-example-biz-b/src/main/java/com/example/project/Controller/UserController.java)，对接口进行暴露及token的使用
```UserController.java
@RestController
public class UserController {
	@Autowired
	UserService userService;
	
	@PostMapping("/login")
	@ResponseBody
	public AjaxResult login(@RequestBody Map<String, String> map) {
		String username = map.get("username");
		String password = map.get("password");
		User user = userService.checkUser(username);
		if (user != null) {
			String pwd = Util.stringMD5(password);
			String upwd = user.getPassword();
			if (upwd.equals(pwd)) {
				// 返回token
				String token = JwtUtil.sign(username, user.getId()); // 对登录名和用户ID进行token生成
				if (token != null) {
					return AjaxResult.success("成功", token);
				}
			} else {
				return AjaxResult.fail("密码不正确");
			}
		}
		return AjaxResult.fail("用户不存在");
	}
	// 用户注册
	@PostMapping("/register")
	@ResponseBody
	public AjaxResult register(@RequestBody User map) {
		String username = map.getUsername();
		String password = map.getPassword();
		if (username != null && password != null) {
			User user = userService.checkUser(username);
			if (user == null) {
				User u = userService.insert(map);
				return AjaxResult.success("注册成功", u);
			} else {
				return AjaxResult.fail("用户名已存在");
			}
		} else {
			return AjaxResult.fail("注册失败：用户名和密码不能为空");
		}
	}
  ...
}
```
以上是相关的逻辑代码示例，下面进行接口测试
##### 注册用户
![注册](https://github.com/Jacky-MYD/spring-cloud-example/blob/master/spring-cloud-example-biz-b/1577330226040.jpg)
##### 数据库插入
![插入表](https://github.com/Jacky-MYD/spring-cloud-example/blob/master/spring-cloud-example-biz-b/1577330408333.jpg)
##### 登录生成token
![登录](https://github.com/Jacky-MYD/spring-cloud-example/blob/master/spring-cloud-example-biz-b/1577330271183.jpg)
##### 查询用户，校验token有效期（只是单纯的测试案例）
![设置请求头](https://github.com/Jacky-MYD/spring-cloud-example/blob/master/spring-cloud-example-biz-b/1577330521909.jpg)
![查询用户](https://github.com/Jacky-MYD/spring-cloud-example/blob/master/spring-cloud-example-biz-b/1577330527563.jpg)

