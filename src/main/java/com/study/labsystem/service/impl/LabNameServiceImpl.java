package com.study.labsystem.service.impl;

import com.study.labsystem.pojo.LabTypeName;
import com.study.labsystem.mapper.LabNameMapper;
import com.study.labsystem.service.ILabNameService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 实验室名字 服务实现类
 * </p>
 *
 * @author 裹个小脑
 * @since 2023-12-13
 */
@Service
public class LabNameServiceImpl extends ServiceImpl<LabNameMapper, LabTypeName> implements ILabNameService {

}
