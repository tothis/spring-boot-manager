package com.example.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 新增或修改时填充数据
 *
 * @author 李磊
 */
@Slf4j
@Component
public class DefaultFillHandler implements MetaObjectHandler {

    private static final String CREATE_BY = "createBy";
    private static final String CREATE_TIME = "createTime";
    private static final String UPDATE_BY = "updateBy";
    private static final String UPDATE_TIME = "updateTime";
    private static final String STATE = "state";

    @Override
    public void insertFill(final MetaObject o) {
        log.info("--- 开始填充插入数据 ---");
        // 此处类型与实体属性类型保持一至 实体属性类型为包装类此处也必须为包装类
        this.strictInsertFill(o, CREATE_BY, () -> 0L, Long.class);
        this.strictInsertFill(o, CREATE_TIME, LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(o, STATE, () -> (byte) 0, Byte.class);
    }

    @Override
    public void updateFill(final MetaObject o) {
        log.info("--- 开始填充修改数据 ---");
        this.strictUpdateFill(o, UPDATE_BY, () -> 1L, Long.class);
        this.strictUpdateFill(o, UPDATE_TIME, LocalDateTime::now, LocalDateTime.class);
    }
}
