package com.study.labsystem.controller;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.study.labsystem.common.Result;
import com.study.labsystem.pojo.LabCollegeName;
import com.study.labsystem.pojo.LabTypeName;
import com.study.labsystem.pojo.LabNameAndColloege;
import com.study.labsystem.pojo.Labs;
import com.study.labsystem.service.ICollegeNameService;
import com.study.labsystem.service.ILabNameService;
import com.study.labsystem.service.ILabsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 裹个小脑
 * @since 2023-12-13
 */
@RestController
@RequestMapping("/adminapi/labs")
public class LabsController {
    @Autowired
    private ILabsService labsService;
    @Autowired
    private ILabNameService labNameService;
    @Autowired
    private ICollegeNameService collegeNameService;

    @GetMapping
    public Result<LabNameAndColloege> getLabs(){
        List<LabTypeName> labNameList = labNameService.list();
        List<LabCollegeName> collegeNameList = collegeNameService.list();
        LabNameAndColloege labNameAndColloege = new LabNameAndColloege(labNameList,collegeNameList);
        return Result.success(labNameAndColloege);
    }

    @PostMapping
    public Result<String> addLab(@RequestBody Labs lab){
        try {
            labsService.save(lab);
        }catch (Exception e){
            throw new RuntimeException("部分字段为空");
        }
        return Result.success("添加实验室成功");
    }

    @GetMapping("/getPoints")
    public Result<List<Labs>> getPoints(){
        List<Labs> points = labsService.getPoints();
        return Result.success(points);
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteLab(@PathVariable("id") Integer id){
        boolean b = labsService.removeById(id);
        if (b){
            return Result.success("删除实验室成功");
        }else {
            throw new RuntimeException("删除实验室失败");
        }
    }
    @PutMapping
    public Result<String> updateLab(@RequestBody Labs lab){
        LambdaUpdateWrapper<Labs> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Labs::getId,lab.getId()).set(Labs::getLabName,lab.getLabName()).set(Labs::getCapacity,lab.getCapacity()).set(Labs::getLabId,lab.getLabId()).set(Labs::getCollegeId,lab.getCollegeId());
        boolean update = labsService.update(wrapper);
        if (update){
            return Result.success("修改实验室成功");
        }else {
            throw new RuntimeException("修改实验室失败");
        }
    }

}
