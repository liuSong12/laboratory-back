package com.study.labsystem.controller;

import com.study.labsystem.common.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/adminapi/upload")
public class UploadController {
    @PostMapping
    public Result<String> upload(){
        System.out.println("上传");
        return Result.success("上传成功");
    }
}
