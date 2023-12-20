package com.study.labsystem.service.impl;

import com.study.labsystem.pojo.LabCollegeName;
import com.study.labsystem.mapper.CollegeNameMapper;
import com.study.labsystem.service.ICollegeNameService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学院名称 服务实现类
 * </p>
 *
 * @author 裹个小脑
 * @since 2023-12-13
 */
@Service
public class CollegeNameServiceImpl extends ServiceImpl<CollegeNameMapper, LabCollegeName> implements ICollegeNameService {

}
