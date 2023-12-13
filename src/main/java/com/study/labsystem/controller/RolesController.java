package com.study.labsystem.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.study.labsystem.common.Result;
import com.study.labsystem.pojo.Roles;
import com.study.labsystem.service.IRolesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Role;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 裹个小脑
 * @since 2023-12-04
 */
@RestController
@Api(tags = "角色查询")
@RequestMapping("/adminapi/roles")
public class RolesController {
    @Autowired
    private IRolesService rolesService;


    @GetMapping
    @ApiOperation("查询所有角色")
    public Result<List<Roles>> getRoles(){
        List<Roles> rolesList = rolesService.list();
        return Result.success(rolesList);
    }

    @PutMapping("/{id}")
    public Result<String> updateRight(@PathVariable("id") Integer id,@RequestBody  Roles roles){
        LambdaUpdateWrapper<Roles> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Roles::getId,id).set(Roles::getRoleName,roles.getRoleName()).set(Roles::getRights,roles.getRights());
        rolesService.update(wrapper);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Roles> deleteById(@PathVariable("id") Integer id){
        rolesService.removeById(id);
        return Result.success("删除成功");
    }
}
