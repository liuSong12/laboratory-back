package com.study.labsystem.controller;


import com.study.labsystem.pojo.Rights;
import com.study.labsystem.service.IRightsService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2023-12-01
 */

@Api(tags="权限管理")
@RestController
@RequestMapping("/adminapi/rights")
public class RightsController {
    @Autowired
    private IRightsService rightsService;

    @GetMapping
    public List<Rights> getRightsList(){
        return rightsService.getRights();
    }

}
