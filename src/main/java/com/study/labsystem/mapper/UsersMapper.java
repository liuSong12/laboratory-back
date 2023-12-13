package com.study.labsystem.mapper;

import com.study.labsystem.pojo.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.labsystem.pojo.UsersVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 裹个小脑
 * @since 2023-12-06
 */
public interface UsersMapper extends BaseMapper<Users> {
    Users loginGet(Users user);
    List<UsersVo> getUserList();
}
