# Spring Boot
学习Spring Boot时写的Demo项目，使用Undertow、BootStrap + Thymeleaf、Druid、JPA/hibernate、Lombok、RESTful API、JSON响应示例（ResponseEntity实现）、Filter示例、Interceptor示例、Vue.js + Ajax异步加载页面示例、Swagger2示例  
# Swagger的使用

# JPA中使用POJO选择性更新表
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