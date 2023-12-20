package com.study.labsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.labsystem.pojo.Labs;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 裹个小脑
 * @since 2023-12-13
 */

public interface ILabsService extends IService<Labs> {
    List<Labs> getPoints();
}
