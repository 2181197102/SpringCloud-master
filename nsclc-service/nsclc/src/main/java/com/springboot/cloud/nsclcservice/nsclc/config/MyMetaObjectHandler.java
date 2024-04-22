package com.springboot.cloud.nsclcservice.nsclc.config;

import com.springboot.cloud.common.web.handler.PoMetaObjectHandler;
import org.springframework.stereotype.Component;

@Component
public class MyMetaObjectHandler extends PoMetaObjectHandler {
    // 这个类继承了PoMetaObjectHandler类，PoMetaObjectHandler类是一个抽象类，它实现了MyBatis Plus的MetaObjectHandler接口。
    // 在MyBatis Plus中，MetaObjectHandler接口用于处理实体字段的自动填充问题，例如，在插入或更新记录时自动设置createTime或updateTime字段的值。

}