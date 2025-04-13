package com.lightereb.hrms.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * MyBatis-Plus配置类
 */
@Configuration
public class MyBatisPlusConfig {

	/**
	 * 自动填充处理器
	 */
	@Component
	public static class MyMetaObjectHandler implements MetaObjectHandler {

		@Override
		public void insertFill(MetaObject metaObject) {
			// 创建时间
			this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
			// 更新时间
			this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
			// 逻辑删除标志
			this.strictInsertFill(metaObject, "isDeleted", Integer.class, 0);
		}

		@Override
		public void updateFill(MetaObject metaObject) {
			// 更新时间
			this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
		}
	}
}