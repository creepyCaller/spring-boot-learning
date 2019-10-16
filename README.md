# Spring Boot
学习Spring Boot时写的Demo项目，使用Undertow、BootStrap + Thymeleaf、Druid、JPA/hibernate、Lombok、RESTful API、JSON响应示例（ResponseEntity实现）、Filter示例、Interceptor示例、Vue.js + Ajax异步加载页面示例、Swagger2示例  
# Swagger的使用
# 什么是Swagger？
事一个强大的工具，功能有：  
&emsp;&emsp;1. 生成api文档：  
&emsp;&emsp;&emsp;&emsp;一个类有什么方法、什么属性？  
&emsp;&emsp;&emsp;&emsp;类里边某个方法的作用、属性的意义？  
&emsp;&emsp;&emsp;&emsp;一个或多个(重载)方法的参数是什么，返回值是什么，方法的作者对方法的描述又是什么？  
&emsp;&emsp;生成Mock.js模拟数据：  
&emsp;&emsp;&emsp;&emsp;提供给前端开发人员使用。  
&emsp;&emsp;生成API测试页：  
&emsp;&emsp;&emsp;&emsp;同时提供给前端、后端、测试人员。  
&emsp;&emsp;2. 情境：  
&emsp;&emsp;&emsp;&emsp;从前：  
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;只能预先写好/生成文档，但是如果有改动，API的调用者拿到的就不是最新的文档，可能会出错。  
&emsp;&emsp;&emsp;&emsp;伟大转折：  
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;路人A想，文档不应该提前写好的，而是在用户请求文档时才实时生成文档，如此必定保证用户拿到的是最新版的文档，但是该怎么做？  
&emsp;&emsp;&emsp;&emsp;自从有了Swagger：  
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;API调用文档和测试框架可以靠它生成了！  
# 与Spring Boot整合
## 导入依赖
方法一：导入官方指定的包
```xml
<!-- swagger -->
<!-- https://mvnrepository.com/artifact/io.springfox/springfox-swagger2 -->
<dependency>
 <groupId>io.springfox</groupId>
 <artifactId>springfox-swagger2</artifactId>
 <version>2.9.2</version>
</dependency>
<!-- swagger-ui -->
<!-- https://mvnrepository.com/artifact/io.springfox/springfox-swagger-ui -->
<dependency>
 <groupId>io.springfox</groupId>
 <artifactId>springfox-swagger-ui</artifactId>
 <version>2.9.2</version>
</dependency>
<!-- JSON API documentation for spring based applications -->
<!-- https://mvnrepository.com/artifact/io.springfox/springfox-bean-validators -->
<dependency>
 <groupId>io.springfox</groupId>
 <artifactId>springfox-bean-validators</artifactId>
 <version>2.9.2</version>
</dependency>
```
方法二：使用Starter
```xml
<!-- https://mvnrepository.com/artifact/com.spring4all/swagger-spring-boot-starter -->
<dependency>
    <groupId>com.spring4all</groupId>
    <artifactId>swagger-spring-boot-starter</artifactId>
    <version>1.9.0.RELEASE</version>
</dependency>
```
## 配置
创建配置类SwaggerConfig：
```java
package config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger配置类
 */
@Configuration // 声明此类为配置类
@EnableSwagger2 // 启用Swagger
public class SwaggerConfig {

    /**
     * 配置生成RESTful api的测试接口
     * @return Docket
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(this.apiInfo()).select().apis(RequestHandlerSelectors.basePackage("controller")).paths(PathSelectors.any()).build();
    }

    /**
     * 配置API上显示的信息
     * @return ApiInfo
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Swagger Test").contact(new Contact("creepyCaller", "https://github.com/creepyCaller", "")).version("0.0.1").description("API 描述文档").build();
    }

}
```
在Spring Boot的main方发上的@SpringBootApplication里添加属性scanBasePackages：
```java
@SpringBootApplication(scanBasePackages = 这里填写包名)
public class BootStrap {

    public static void main(String[] args) {
        SpringApplication.run(BootStrap.class, args);
    }

}
```
## 测试是否导入成功
1. 启动Spring Boot
2. 访问地址[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
3. 看看是否加载成功：  
![](https://upload-images.jianshu.io/upload_images/19785935-db405a50bc40fafc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)  
# 使用对类/方法的注解令其自动生成文档（常用注解）
## @Api()  
![](https://upload-images.jianshu.io/upload_images/19785935-0fcf0f05a64ec4c8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)  
## @ApiOperation()
注解在方法上，说明方法的作用，每一个URI资源的定义  
![](https://upload-images.jianshu.io/upload_images/19785935-41e99af105cc7ca7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)  
## @ApiImplicitParams({...})
注解在方法上，包含一组ApiImplicitParam
## @ApiImplicitParam()
注解在方法上（如果只需要一个参数）。
注解在@ApiImplicitParams中（多个参数）。
指定一个HTTP请求参数的配置信息。
name：参数名
value：参数的汉字说明、解释
required：参数是否必须传
paramType：参数放在哪个地方
&emsp;= header --> 请求参数的获取：@RequestHeader
&emsp;= query --> 请求参数的获取：@RequestParam
&emsp;= path --> 请求参数的获取：@PathVariable
dataType：参数类型，默认String，其它值dataType="Integer" &emsp;  
defaultValue：参数的默认值
## 综合示例
对订单这个资源的五个HTTP动词具体实现、Swagger注解的示例：
```java
package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Want;
import model.ResultModel;
import service.WantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@Controller
@RequestMapping("/wants")
@Api("订单控制器，用于对订单资源的GET、POST、PATCH、PUT、DELETE操作")
public class WantsController {

    private static final int PAGE_SIZE = 10;

    private final WantService wantService;

    private ObjectMapper mapper;

    public WantsController(WantService wantService) {
        this.mapper = new ObjectMapper();
        this.wantService = wantService;
    }

    /**
     * 异步加载want
     * @param page 第 {page} 页
     * @return 包含第 {page} 页的订单信息的JSON字符串
     * */
    @GetMapping(value = "/page/{page}")
    @ApiOperation(value = "获取第{page}页的订单列表", notes = "返回Page<T>对象")
    @ApiImplicitParam(name = "page", required = false, dataType = "Integer", paramType = "path")
    public ResponseEntity<ResultModel> getWantPage(@PathVariable(value = "page") Integer page) {
        Page<Want> pager = null;
        if (page < 1) {
            // 如果请求的页号小于1，则到第一页
            pager = wantService.findAll(0, PAGE_SIZE);
        } else {
            pager = wantService.findAll(page - 1, PAGE_SIZE);
            int total = pager.getTotalPages();
            if (page > total) {
                // 如果请求的页号大于总页数，则到最后一页
                pager = wantService.findAll(total - 1, PAGE_SIZE);
            }
        }
        return new ResponseEntity<>(ResultModel.ok(1, pager), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "获取id为{id}的订单详情", notes = "返回更新后的订单，标准的返回格式：{\"id\":1,\"name\":\"62LT\",\"amount\":0,\"price\":0.0,\"remark\":\"无\",\"date\":\"2019-10-09T10:23:56.000+0000\",\"status\":0}")
    @ApiImplicitParam(name = "id", dataType = "Integer", paramType = "path") // paramType = "path"用于@PathVariable注解的参数的获取
    public ResponseEntity<ResultModel> getWant(@PathVariable(value = "id") Integer id) {
        return new ResponseEntity<>(ResultModel.ok(1, wantService.findById(id)), HttpStatus.OK);
    }

    @ApiOperation(value = "完全更新id为{id}的订单", notes = "传入一个完整描述订单的POJO，返回更新后的订单，标准的返回格式：{\"id\":1,\"name\":\"62LT\",\"amount\":0,\"price\":0.0,\"remark\":\"无\",\"date\":\"2019-10-09T10:23:56.000+0000\",\"status\":0}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "param", dataType = "String", paramType = "query")
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<ResultModel> updateWantComplete(@PathVariable(value = "id") Integer id, @RequestParam("param") String param) {
        Want want = null;
        try {
            want = mapper.readValue(param, Want.class); // 传入的JSON字符串转为对象
        } catch (IOException e) {
            return new ResponseEntity<>(ResultModel.error(0), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(1, wantService.update(id, want)), HttpStatus.OK);
    }

    @ApiOperation(value = "部分更新id为{id}的订单", notes = "传入部分描述订单的POJO，返回更新后的订单，标准的返回格式：{\"id\":1,\"name\":\"62LT\",\"amount\":0,\"price\":0.0,\"remark\":\"无\",\"date\":\"2019-10-09T10:23:56.000+0000\",\"status\":0}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "param", dataType = "String", paramType = "query")
    })
    @PatchMapping(value = "/{id}")
    public ResponseEntity<ResultModel> updateWantPart(@PathVariable(value = "id") Integer id, @RequestParam("param") String param) {
        Want want = null;
        try {
            want = mapper.readValue(param, Want.class); // 传入的JSON字符串转为对象
        } catch (IOException e) {
            return new ResponseEntity<>(ResultModel.error(0), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(1, wantService.update(id, want)), HttpStatus.OK);
    }

    @ApiOperation(value = "删除id为{id}的订单", notes = "无返回")
    @ApiImplicitParam(name = "id", dataType = "Integer", paramType = "path")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ResultModel> deleteWant(@PathVariable(value = "id") Integer id) {
        wantService.delete(new Want(id));
        return new ResponseEntity<>(ResultModel.ok(1), HttpStatus.OK);
    }

}
```
# Swagger使用导论
1. 启动Spring Boot
2. 访问地址[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
3. 点开wants-controller，这里就做PATCH的示范把  
![](https://upload-images.jianshu.io/upload_images/19785935-7f2fa76b7b6ab50d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)  
4. 点开 [PATCH /wants/{id} ...]后，点击[Try it out]  
![](https://upload-images.jianshu.io/upload_images/19785935-7068cb9a9877df8f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)  
5. 因为这里是PATCH，所以不需要在param表示的JSON对象中表示所有属性
6. 这里先去[GET /wants/{id}]里获取id = 1的表项的数据
![](https://upload-images.jianshu.io/upload_images/19785935-0849e42f5442d87c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)  
在id框的Description中输入1，表示请求{ServiceRoot}/wants/1，点击[Execute]
![](https://upload-images.jianshu.io/upload_images/19785935-06e42ec2d36a1435.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)  
可以看到，这个请求成功的完成了，响应报文的响应体中，是自定义的响应实体，id为1的订单的JSON对象在实体的content中可以找到。
7. 在PATCH中修改它的名字
![](https://upload-images.jianshu.io/upload_images/19785935-6fb5da031e79d427.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)  
根据返回格式酌情配置传入后端的JSON对象，点击[Execute]。
![](https://upload-images.jianshu.io/upload_images/19785935-c6d0a4fac6db5131.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
可以看到，这个PATCH动作已经成功的完成了，返回了修改后的对应订单的JSON格式对象。
8.  为了确定PATCH动作成功，笔者决定再访问一次id为1的订单
与上述不同的是这次笔者决定直接在浏览器地址栏请求，访问[http://localhost:8080/wants/1](http://localhost:8080/wants/1)后，浏览器接收到了这些字符串：  
```
{"code":"OK","timestamp":1571212229214,"status":1,"message":"success","content":{"id":1,"name":"62式轻型坦克","amount":0,"price":0.0,"remark":"无","date":"2019-10-09T10:23:56.000+0000","status":0}}
```
可以在它的content的name中看到，名字已经成功的修改，说明PATCH动作成功完成。
# 附0：不用Spring MVC的方法传入参数(..., HttpSession session)获取HttpSession的方法  
因为使用Swagger测试需要手动指定传入的参数，但是session和model这种东西基本不可能靠手敲来实现，所以需要把他们从方法的参量表中移走，令辟一条路实现他们。
##### 在控制器中使用构造器传入HttpSession对象：
```java
private final HttpSession session;

public XXXController(HttpSession session) {
    this.session = session;
}
```
之后就可以在这个控制器中的任意类访问session了。
# 附1：不用Spring MVC的方法传入参数(..., Model model)将某个对象放入model中的方法
用注解"@ModelAttribute()"，这里直接放代码，自己看吧。
```java
package controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/user")
@Api("访问用户的API")
public class UserController {

    /**
     * 访问{ServerRoot}/user/{username}，
     * 转发至用户详情页，
     * 详情页用model传入的用户名在GET {ServerRoot}/users/{username}，
     * 异步加载用户实体对应JSON对应对象，
     * 再由Vue.js绘制至DOM组件
     * @param username 地址传入的请求用户名
     * @return 需要映射的页面地址到视图解析器
     */
    @GetMapping(value = "/{username}")
    @ApiOperation(value = "获取用户名为{username}的详细信息")
    @ApiImplicitParam(name = "username", dataType = "String", paramType = "path")
    public String userInfo(@PathVariable(value = "username") @ModelAttribute(value = "username") String username) {
        // 怎么把username传到info页，info页再通过它使用Ajax从UsersController::getUserInfo加载用户信息，model?
        return "user/info";
    }

}
```
# 在JPA选择性的UPDATE一个表项？
众所周知，JPA是Sun公司搞的一套接口，这套接口好是好用，就是默认没有提供选择性更新的方法，它自带的save(T t)会保存t这个POJO里的所有属性到数据库里，这就很恼火了。今天笔者也遇到了这个问题，因为大多数时候，进行PUT动作需要更新数据库里的字段信息，前端传过来的JSON不会包含原POJO里的所有数据，比如这样：
```
PUT {ServerRoot}/XXX/{id = 3}
param = {"name":"武装120重型坦克"}
```
传到后端进行JSON转对象以后，是这样的：
```
Want(id=3, name=武装120重型坦克, amount=null, price=null, remark=null, date=null, status=null)
```
可以看到除了传到后端的对象只有在前端指定的数据，直接执行
```
wantService.save(want);
```
肯定会报错，那该怎么办？  
思路1：  
&emsp;&emsp;看看JPA的库有没有提供这个方法?  
答：  
&emsp;&emsp;没有。  
思路2：  
&emsp;&emsp;看看网上有没有现成的解决方案？  
答：  
&emsp;&emsp;基本都是三言两语，没有完整的解决方案。  
笔者的解决方案：  
&emsp;&emsp;使用对象克隆，源是XXX，目标是使用id查询到的oldXXX，当遇到空值则不拷贝，最后将oldXXX存入数据库，以此模拟更新行为。  
# 行动
既然我有了解决方案，那就立即着手开始解决问题把：
## Spring框架提供的BeanUtils
笔者最开始准备使用org.springframework.beans.BeanUtils的public static void copyProperties(Object source, Object target, String... ignoreProperties)方法，其中String... ignoreProperties是过滤属性参数，也就是不参与复制的参数名，但是这个问题来了，该怎么获取属性为null的属性名，再转为一个个String作为这个方法的参数，这个参数也有毛病，为什么不用Collection、List这种集合类，而是搞可变参。感觉到困难重重，遂放弃。
## Hutool工具包的BeanUtil
使用Hutool工具包中的BeanUtil中的这个方法（参考文档[Hutool工具包文档]([https://hutool.cn/docs/](https://hutool.cn/docs/)
)）中：
```java
public static <T> T fillBean(T bean, ValueProvider<String> valueProvider, CopyOptions copyOptions);
```
可以看到它需求参数：类型为T的bean、ValueProvider<String>的值提供者、CopyOptions选项。
下边将一个一个讲怎么做。
### bean的实现
```java
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Want {

    public Want(int id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private Integer amount;

    private Double price;

    private String remark;

    private Date date;

    private Integer status;

}
```
再向它对应的服务接口添加方法：
```java
    /**
     * @param id 前端传入的path变量id
     * @param want 待更新的数据
     * @return 更新后的POJO
     */
    Want update(int id, Want want);
```
向接口实现类添加实现：
```java
    /**
     * @param id 前端传入的path变量id
     * @param want 待更新的数据
     * @return 更新后的POJO
     */
    @Override
    public Want update(int id, Want want) {
        Want oldWant = this.findById(id); // 通过id获取表项对应的POJO
        System.out.println("待合并的POJO： " + want.toString()); // 这个sout和下边那个记得删掉
        WantValueProvider wantValueProvider = new WantValueProvider(); // 实例化一个ValueProvider
        wantValueProvider.setValueProvider(want); // 设置值提供者为want
        BeanUtil.fillBean(oldWant, wantValueProvider, CopyOptions.create().ignoreNullValue()); // 复制目标为oldWant，选项选择无视空值（即不对want的空值操作）
        oldWant.setId(id); // 设置id
        System.out.println("合并后的POJO： " + oldWant.toString());
        return wantRepository.save(oldWant);
    }
```

### ValueProvider的实现
首先它要继承ValueProvider<T>这个接口，通过反编译可以看到这个接口长这样：
```java
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.hutool.core.bean.copier;

import java.lang.reflect.Type;

public interface ValueProvider<T> {
    Object value(T var1, Type var2);

    boolean containsKey(T var1);
}
```
fillBean方法已经指定这个泛型T是String了，所以这里继承 ValueProvider<String>：
```java
import cn.hutool.core.bean.copier.ValueProvider;
import domain.Want;
import java.lang.reflect.Type;

public class WantValueProvider implements ValueProvider<String> {

    private Want valueProvider = null; // 指定值提供者为Wantl类型

    /**
     * 需要在service里设置好
     * @param valueProvider 值提供者，系前端传来的JSON转的bean
     */
    public void setValueProvider(Want valueProvider) {
        this.valueProvider = valueProvider;
    }

    /**
     * Key-Value
     * @param s Key，属性名
     * @param type 数据类型
     * @return Value，属性值
     */
    @Override
    public Object value(String s, Type type) {
        switch (s) {
            case "id":
                return this.valueProvider.getId();
            case "name":
                return this.valueProvider.getName();
            case "amount":
                return this.valueProvider.getAmount();
            case "price":
                return this.valueProvider.getPrice();
            case "remark":
                return this.valueProvider.getRemark();
            case "date":
                return this.valueProvider.getDate();
            case "status":
                return this.valueProvider.getStatus();
        }
        return null;
    }

    /**
     *
     * @param s 待确认是否包含在Bean里的属性名
     * @return  是否包含有名为s的属性 ? true : false
     */
    @Override
    public boolean containsKey(String s) {
        switch (s) {
            case "id":
            case "name":
            case "amount":
            case "price":
            case "remark":
            case "date":
            case "status":
                return true;
        }
        return false;
    }
}
```
笔者这里为了保证复用性，确实搞得有些麻烦了，这里其实也可以用匿名的内部类，效果一样的，像这样：
```java
        BeanUtil.fillBean(oldWant, new ValueProvider<String>() {

            @Override
            public Object value(String s, Type type) {
                // 方法实现
                return null;
            }

            @Override
            public boolean containsKey(String s) {
                // 方法实现
                return false;
            }

        } ,CopyOptions.create().ignoreNullValue());
```
方法实现参考上边。
### CopyOptions选项
这里因为是需要把从前端获取的JSON转对象选择性的保存入表中，所以需要设置为空值不保存，也就是传入：
```
CopyOptions.create().ignoreNullValue()
```
## 测试
运行，点击链接[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)打开Swagger UI测试界面 ，展开需要测试的控制器：  
![](https://upload-images.jianshu.io/upload_images/19785935-bfe8d42408a65466.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)  
点击'Try it out'按钮：  
![](https://upload-images.jianshu.io/upload_images/19785935-9ecd4994b966852c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)  
 输入数据，点击Execute，得到返回值：  

![](https://upload-images.jianshu.io/upload_images/19785935-9fd012f1a6b65b2d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)  
查看控制台：  
```
待合并的POJO： Want(id=0, name=辣条, amount=5, price=0.5, remark=null, date=null, status=null)
合并后的POJO： Want(id=25, name=辣条, amount=5, price=0.5, remark=加满油, date=2019-10-13 01:23:26.0, status=1)
```
数据库中的原始数据：  
![](https://upload-images.jianshu.io/upload_images/19785935-1f04232b8b56beb3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)  
刷新后：  
![](https://upload-images.jianshu.io/upload_images/19785935-512248ad1a838328.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)  


说明合并成功了。
也说明选择性更新成功。