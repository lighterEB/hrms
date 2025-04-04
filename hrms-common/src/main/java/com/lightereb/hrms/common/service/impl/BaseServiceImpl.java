package com.lightereb.hrms.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lightereb.hrms.common.mapper.BaseMapper;
import com.lightereb.hrms.common.service.BaseService;

/**
 * 基础Service实现类，所有ServiceImpl都需要继承
 * @param <M> Mapper类型
 * @param <T> 实体类型
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {
} 