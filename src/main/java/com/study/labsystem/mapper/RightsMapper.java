package com.study.labsystem.mapper;

import com.study.labsystem.pojo.Rights;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2023-12-01
 */
@Mapper
public interface RightsMapper extends BaseMapper<Rights> {
    List<Rights> getRights();
}
