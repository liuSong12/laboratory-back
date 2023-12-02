package com.study.labsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.labsystem.pojo.Rights;
import com.study.labsystem.mapper.RightsMapper;
import com.study.labsystem.service.IRightsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2023-12-01
 */
@Service
public class RightsServiceImpl extends ServiceImpl<RightsMapper, Rights> implements IRightsService {
    @Autowired
    private RightsMapper rightsMapper;

    public List<Rights> getRights(){
        return rightsMapper.getRights();
    }

}
