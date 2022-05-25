package com.qingge.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingge.springboot.entity.Formula;
import com.qingge.springboot.entity.Parameters;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author demo
 * @since 2022-05-13
 */
public interface IParametersService extends IService<Parameters> {

    List<Formula> findAll(Integer formula);
}
