package com.qingge.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingge.springboot.entity.Formula;
import com.qingge.springboot.entity.Parameters;
import com.qingge.springboot.service.IParametersService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author demo
 * @since 2022-05-13
 */
@RestController
@RequestMapping("/parameters")
public class ParametersController {
    @Resource
    private IParametersService parametersService;

    @PostMapping("/alter")
    public Boolean save(@RequestBody HashMap<String, String> map) {
        System.out.print(map);
        QueryWrapper<Parameters> queryWrapper = new QueryWrapper<>();
        String species = map.get("species");
        queryWrapper.eq("species", species);
        int formula_index = Integer.parseInt(map.get("formula_index"));
        queryWrapper.eq("formula", formula_index);
        Parameters parameters = parametersService.getOne(queryWrapper);
        double parametersOne = Double.parseDouble(map.get("parametersOne"));
        double parametersTwo = Double.parseDouble(map.get("parametersTwo"));
        parameters.setParametersOne(parametersOne);
        parameters.setParametersTwo(parametersTwo);
        if (formula_index == 4) {
            double parametersThree = Double.parseDouble(map.get("parametersThree"));
            parameters.setParametersOne(parametersThree);
        }
        return parametersService.saveOrUpdate(parameters);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return parametersService.removeById(id);
    }

    @PostMapping("/delete/batch")
    public boolean deleteUserByIdBatch(@RequestBody List<Integer> ids) {
        return parametersService.removeBatchByIds(ids);
    }

    @GetMapping("/all")
    public List<Formula> findAll(@RequestParam Integer formula) {
        return parametersService.findAll(formula);
    }

    @GetMapping("/{id}")
    public Parameters findOne(@PathVariable Integer id) {
        return parametersService.getById(id);
    }

    @GetMapping("/page")
    public Page<Parameters> findPage(@RequestParam Integer pageNum,
                                     @RequestParam Integer pageSize) {
        IPage<Parameters> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Parameters> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return parametersService.page(new Page<>(pageNum, pageSize));
    }
}

