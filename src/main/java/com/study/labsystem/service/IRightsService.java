package com.study.labsystem.service;

import com.study.labsystem.pojo.Rights;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2023-12-01
 */
public interface IRightsService extends IService<Rights> {
     List<Rights> getRights();

}
