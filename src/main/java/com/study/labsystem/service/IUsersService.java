package com.study.labsystem.service;

import com.study.labsystem.pojo.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import com.study.labsystem.pojo.UsersVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 裹个小脑
 * @since 2023-12-06
 */
public interface IUsersService extends IService<Users> {
    Users loginGet(Users user);

    List<UsersVo> getUserList();
}
