package com.study.labsystem.service.impl;

import com.study.labsystem.pojo.Users;
import com.study.labsystem.mapper.UsersMapper;
import com.study.labsystem.pojo.UsersVo;
import com.study.labsystem.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 裹个小脑
 * @since 2023-12-06
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public Users loginGet(Users user) {
        return usersMapper.loginGet(user);
    }

    @Override
    public List<UsersVo> getUserList() {
        return usersMapper.getUserList();
    }
}
