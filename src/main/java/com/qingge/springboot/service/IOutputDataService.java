package com.qingge.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingge.springboot.entity.OutputData;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author demo
 * @since 2022-04-24
 */
public interface IOutputDataService extends IService<OutputData> {
    double calculate(OutputData outputData);
    double forecast(OutputData outputData);
}
