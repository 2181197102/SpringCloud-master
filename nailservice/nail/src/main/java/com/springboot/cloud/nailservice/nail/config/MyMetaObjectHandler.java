package com.springboot.cloud.nailservice.nail.config;

import com.springboot.cloud.common.web.handler.PoMetaObjectHandler;
import org.springframework.stereotype.Component;

@Component  // @Component注解表明这个类是一个组件类，Spring容器会在启动时自动扫描加载这个类中的Bean定义。
            // 这样做的好处是可以在其他组件中通过@Autowired注解来引用这个组件。
public class MyMetaObjectHandler extends PoMetaObjectHandler {
    // 这个类继承了PoMetaObjectHandler类，PoMetaObjectHandler类是一个抽象类，它实现了MyBatis Plus的MetaObjectHandler接口。
    // 在MyBatis Plus中，MetaObjectHandler接口用于处理实体字段的自动填充问题，例如，在插入或更新记录时自动设置createTime或updateTime字段的值。

}