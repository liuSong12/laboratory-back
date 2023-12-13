package com.study.labsystem.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.study.labsystem.common.Result;
import com.study.labsystem.pojo.Rights;
import com.study.labsystem.service.IRightsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 裹个小脑
 * @since 2023-12-01
 */

@Api(tags="路由权限管理")
@RestController
@RequestMapping("/adminapi/rights")
public class RightsController {
    @Autowired
    private IRightsService rightsService;

    @ApiOperation(value = "获取路由")
    @GetMapping
    public Result<List<Rights>> getRightsList(){
        return Result.success(rightsService.getRights());
    }

    @ApiOperation(value = "更新路由")
    @PutMapping("/{id}")
    public Result updateRight(@PathVariable("id") Integer id, @RequestBody Rights right){
        right.setId(id);
        LambdaUpdateWrapper<Rights> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Rights::getId,id).set(Rights::getTitle,right.getTitle()).set(Rights::getIcon,right.getIcon());
        rightsService.update(wrapper);
        return Result.success();
    }

    @ApiOperation("删除权限")
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable("id") Integer id){
        LambdaQueryWrapper<Rights> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Rights::getId,id).or().eq(Rights::getParentId,id);
        rightsService.remove(wrapper);
        return Result.success();
    }



}
