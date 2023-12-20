package com.study.labsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.labsystem.pojo.Labs;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 裹个小脑
 * @since 2023-12-13
 */
public interface LabsMapper extends BaseMapper<Labs> {
    List<Labs> getPoints();
}
