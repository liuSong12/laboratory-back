package com.study.labsystem.service.impl;

import com.study.labsystem.mapper.LabsMapper;
import com.study.labsystem.pojo.Labs;
import com.study.labsystem.service.ILabsService;
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
 * @since 2023-12-13
 */
@Service
public class LabsServiceImpl extends ServiceImpl<LabsMapper, Labs> implements ILabsService {

    @Autowired
    private LabsMapper labsMapper;

    @Override
    public List<Labs> getPoints() {
        return labsMapper.getPoints();
    }
}
