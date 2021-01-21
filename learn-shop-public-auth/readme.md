@[TOC](目录)

源码地址：[看这里，看这里，看这里](https://github.com/Xiao-Y/learn/tree/3.x)

>gateway为微服务的网关，所有的访问服务的api都要通过网关转发到内网对应的服务。spring security、oauth2为认证和授权中心，负责整个微服务api的安全

版本说明：
```xml
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.4.RELEASE</version>
    </parent>
```
```xml
	<dependency>
	    <groupId>org.springframework.cloud</groupId>
	    <artifactId>spring-cloud-dependencies</artifactId>
	    <version>Hoxton.SR8</version>
	    <type>pom</type>
	    <scope>import</scope>
	</dependency>
```


# 1）learn-shop-public-auth（认证中心）
>认证中心，主要是用于用户的登陆认证，发放token

## 1、生成JKS
使用jdk的keytool生成jks,记住生成的时填入的密码
```
keytool -genkeypair
```
将生成的 jwt.jks 文件放到 resources 目录下

## 2、添加pom.xml中主要依赖
 ```xml
		<dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-security</artifactId>
     </dependency>
     <dependency>
         <groupId>org.springframework.cloud</groupId>
         <artifactId>spring-cloud-starter-oauth2</artifactId>
     </dependency>
		<dependency>
         <groupId>com.nimbusds</groupId>
         <artifactId>nimbus-jose-jwt</artifactId>
         <version>8.16</version>
     </dependency>
 ```
## 3、添加主要类
### 3.1 目录结构及主要类说明
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201224114116554.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2x5b25nMTIyMw==,size_16,color_FFFFFF,t_70)
### 3.2 WebSecurityConfig
下面展示一些 `内联代码片`。

```java
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthProperties authProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                // 配置的白名单
                .antMatchers(ArrayUtil.toArray(authProperties.getWhiteList(), String.class)).permitAll()
                .anyRequest().authenticated();


    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 密码加密
        return new BCryptPasswordEncoder();
    }
}
```
3.3 Oauth2ServerConfig 配置
```java
@Configuration
@EnableAuthorizationServer
public class Oauth2ServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private AuthProperties authProperties;
    @Autowired
    private UserDetailsService customUserDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenEnhancer jwtTokenEnhancer;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 客户端访问方式配置数据在数据库中
        clients.withClientDetails(customClientDetailsService());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        delegates.add(jwtTokenEnhancer);
        delegates.add(accessTokenConverter());
        enhancerChain.setTokenEnhancers(delegates); //配置JWT的内容增强器
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(customUserDetailsService) //配置加载用户信息的服务
                .accessTokenConverter(accessTokenConverter())
                .tokenEnhancer(enhancerChain);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setKeyPair(keyPair());
        return jwtAccessTokenConverter;
    }

    @Bean
    public KeyPair keyPair() {
        TokenProperties token = authProperties.getToken();
        String jwtFileName = token.getJwtFileName();
        String jwtPassword = token.getJwtPassword();
        String alias = token.getAlias();
        //从classpath下的证书中获取秘钥对
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource(jwtFileName), jwtPassword.toCharArray());
        return keyStoreKeyFactory.getKeyPair(alias, jwtPassword.toCharArray());
    }

    @Bean
    public ClientDetailsService customClientDetailsService() {
        return new JdbcClientDetailsService(dataSource);
    }
}
```

### 3.4 JwtTokenEnhancer
```java
@Component
public class JwtTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        Map<String, Object> info = new HashMap<>();
        //把用户ID设置到JWT中
        info.put("id", securityUser.getId());
        info.put("usercode",securityUser.getUsercode());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
    }
}
```
### 3.5 CustomUserDetailsService
```java
public class CustomUserDetailsService implements UserDetailsService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户的权限(我是写死的)
        Set<GrantedAuthority> userAuthotities = new HashSet<>();
        userAuthotities.add(new SimpleGrantedAuthority("query-demo"));
        UserPo userPo = new UserPo("admin",passwordEncoder.encode("admin"));
        // 注意如果在WebSecurityConfig.java 中使用的密文时，这个地方也要使用密文
        return new SecurityUser(userPo , userAuthotities);
    }
}
```

## 4、添加公钥访问地址
```java
@RestController
public class KeyPairController {

    @Autowired
    private KeyPair keyPair;

    /**
     * 获取RSA公钥
     *
     * @return
     */
    @GetMapping("/rsa/publicKey")
    public Map<String, Object> getKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }
}
```
## 5、添加配置文件
```yml
auth:
  token:
    alias: jwt
    jwtFileName: jwt.jks
    jwtPassword: 123456789
  white-list:
    - "/rsa/publicKey" # 获取公钥
```

# 2）learn-cloud-gateway（资源中心）
> 资源中心主要于鉴定用户是否有权限访问该资源
## 1、目录结构
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201224144152573.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2x5b25nMTIyMw==,size_16,color_FFFFFF,t_70)
## 2、AuthorizationManager
```java
@Slf4j
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    private final static String PERMISSION = RedisCst.ROLE_PERMISSION_KEY;
    private final static String DICTIONARY = RedisCst.COMM_DICTIONARY_SYS_MODULE;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        //从Redis中获取当前路径可访问角色列表
        URI uri = authorizationContext.getExchange().getRequest().getURI();
        String targetURI = uri.getPath();
        log.info("<<<=== targetURI:{}", targetURI);
        // 获取 redis 中数据字典的数据
        Map<String, String> dictionaryMap = redisUtils.getArray(DICTIONARY + DictionaryType.SYS_MC_SYSTEM_MODULE, DataDictionaryVo.class)
                .stream()
                .filter(f -> DictionaryType.SYS_FC_SYSTEM_MODULE.equals(f.getFieldType()))
                .collect(Collectors.toMap(DataDictionaryVo::getFieldValue, DataDictionaryVo::getFieldDisplay));

        //认证通过且角色匹配的用户可访问当前路径
        return mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(role -> {
                    role = role.replace(AuthConstant.AUTHORITY_PREFIX, "");
                    List<PermissionVo> permissionVos = redisUtils.getArray(PERMISSION + role, PermissionVo.class);
                    if (permissionVos == null) {
                        return false;
                    }
                    for (PermissionVo permissionVo : permissionVos) {
                        if (ToolsUtils.isEmpty(permissionVo.getSystemModule())) {
                            String sourceURI = permissionVo.getUrl();
                            log.info("===>>> sourceURI:{}", sourceURI);
                            if (antPathMatcher.match(sourceURI, targetURI)) {
                                log.info("\n===>>> sourceURI:{},targetURI:{} <<<===\n", sourceURI, targetURI);
                                return true;
                            }
                        } else {
                            String[] split = permissionVo.getSystemModule().split(",");
                            for (String s : split) {
                                String sourceURI = "/" + dictionaryMap.get(s) + permissionVo.getUrl();
                                log.info("===>>> sourceURI:{}", sourceURI);
                                if (antPathMatcher.match(sourceURI, targetURI)) {
                                    log.info("\n===>>> sourceURI:{},targetURI:{} <<<===\n", sourceURI, targetURI);
                                    return true;
                                }
                            }
                        }
                    }
                    return false;
                })
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

}
```

## 3、ResourceServerConfig
```java
@AllArgsConstructor
@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfig {
    private final AuthorizationManager authorizationManager;
    private final SecurityProperties securityProperties;
    private final RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    // 暂时不能用
//    private final WhiteListRemoveJwtFilter ignoreUrlsRemoveJwtFilter;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        // jwt 增加
        http.oauth2ResourceServer().jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter());
        //自定义处理JWT请求头过期或签名错误的结果
        http.oauth2ResourceServer().authenticationEntryPoint(restAuthenticationEntryPoint);
        //对白名单路径，直接移除JWT请求头
//        http.addFilterBefore(ignoreUrlsRemoveJwtFilter, SecurityWebFiltersOrder.AUTHENTICATION);
        // 白名单
        List<String> whiteList = securityProperties.getWhiteList();
        if (!CollectionUtils.isEmpty(whiteList)) {
            //白名单配置
            http.authorizeExchange().pathMatchers(ArrayUtil.toArray(whiteList, String.class)).permitAll();
        }
        // 要权限的
        List<String> needCheck = securityProperties.getNeedCheck();
        http.authorizeExchange()
                .pathMatchers(ArrayUtil.toArray(needCheck, String.class))
                .authenticated()
                .anyExchange().access(authorizationManager)//鉴权管理器配置
                .and().exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)//处理未授权
                .authenticationEntryPoint(restAuthenticationEntryPoint)//处理未认证
                .and().csrf().disable();
        return http.build();
    }

    @Bean
    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(AuthConstant.AUTHORITY_PREFIX);
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(AuthConstant.AUTHORITY_CLAIM_NAME);
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }

}
```
## 4、配置文件
```yml
spring:
  application:
    name: learn-cloud-gateway
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
          lower-case-service-id: true
      routes:
        - id: public-auth
          # lb代表从注册中心获取服务，且已负载均衡方式转发
          uri: lb://learn-shop-public-auth
          predicates:
            - Path=/public-auth/**
          # 加上StripPrefix=1，否则转发到后端服务时会带上consumer前缀
          filters:
            - StripPrefix=1
        - id: admin-system
          # lb代表从注册中心获取服务，且已负载均衡方式转发
          uri: lb://learn-shop-admin-system
          predicates:
            - Path=/admin-system/**
  security:
    oauth2:
      resourceserver:
        jwt:
          jwk-set-uri: 'http://localhost:8999/rsa/publicKey' # 获取公钥

# 自己定义安全配置
secure:
  client: # 登陆客户端配置
    client-id: webapp
    client-secret: webapp
    scope: webapp
    grant-type: password
    access-token-uri: "http://learn-shop-public-auth/oauth/token"
  white-list:
    - "/actuator/**"              # 健康检查
    - "/userApi/*"                # 获取用户信息
    - "/public-auth/oauth/token"  # 获取token或者刷新token
  need-check:
    - "/**/*Api/**"
    - "/*Api/**"

```

源码地址：[看这里，看这里，看这里](https://github.com/Xiao-Y/learn/tree/3.x)

测试请参考
[Spring Cloud 之 Zuul、Spring Security、Oauth2 的整合](https://blog.csdn.net/lyong1223/article/details/84261089)