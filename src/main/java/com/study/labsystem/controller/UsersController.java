package com.study.labsystem.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.study.labsystem.common.Result;
import com.study.labsystem.pojo.Roles;
import com.study.labsystem.pojo.Users;
import com.study.labsystem.pojo.UsersVo;
import com.study.labsystem.service.IRolesService;
import com.study.labsystem.service.IUsersService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 裹个小脑
 * @since 2023-12-06
 */
@RestController
@RequestMapping("/adminapi/users")
public class UsersController {
    @Autowired
    private IUsersService usersService;

    @PostMapping("/login")
    public Result<UsersVo> userLogin(@RequestBody Users user){
        Users findUser = usersService.loginGet(user);
        if (findUser!=null){
            UsersVo usersVo = new UsersVo();
            BeanUtils.copyProperties(findUser,usersVo);
            return Result.success("登录成功",usersVo);
        }else {
            return Result.err("用户名密码不匹配");
        }
    }
    @GetMapping
    public Result<List<UsersVo>> getUserList(){
        List<UsersVo> userList = usersService.getUserList();
        return Result.success(userList);
    }

    @PostMapping
    public Result<String> addUser(@RequestBody Users user){
        try {
            usersService.save(user);
        }catch (Exception e){
            throw new RuntimeException("用户名，密码，身份不能为空");
        }
        return Result.success("添加用户成功");
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteUser(@PathVariable("id") Integer id){
        boolean b = usersService.removeById(id);
        if (b){
            return Result.success("删除用户成功");
        }else {
            throw new RuntimeException("无此用户");
        }
    }

    @PutMapping("/{id}")
    public Result<String> updateUserById(@PathVariable("id") Integer id,@RequestBody Users user){
        user.setId(id);
        usersService.updateById(user);
        return Result.success("修改用户信息成功");
    }


}
