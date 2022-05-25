package com.qingge.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingge.springboot.entity.OutputData;
import com.qingge.springboot.entity.Parameters;
import com.qingge.springboot.mapper.OutputDataMapper;
import com.qingge.springboot.service.IOutputDataService;
import com.qingge.springboot.service.IParametersService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author demo
 * @since 2022-04-24
 */
@Service
public class OutputDataServiceImpl extends ServiceImpl<OutputDataMapper, OutputData> implements IOutputDataService {
    @Resource
    private IParametersService parametersService;

    public double calculate(OutputData outputData) {
        double output = 0;
        switch (outputData.getFormula()) {
            case 1:
                output = calculateByFormulaOne(outputData.getSpecies(), outputData.getAccumulation());
                break;
            case 2:
                output = calculateByFormulaTwo(outputData.getSpecies(), outputData.getAccumulation());
                break;
            case 3:
                output = calculateByFormulaThree(outputData.getSpecies(), outputData.getAccumulation());
                break;
            case 4:
                output = calculateByFormulaFour(outputData.getSpecies(), outputData.getAccumulation());
                break;
            default:
        }
        outputData.setOutput(output);
        save(outputData);
        return output;
    }

    private double calculateByFormulaOne(String species, Double accumulation) {
        double output = 0;
        QueryWrapper<Parameters> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("formula", 1);
        Parameters parameters = new Parameters();
        queryWrapper.eq("species", species);
        parameters = parametersService.getOne(queryWrapper);
        output = parameters.getParametersOne() * accumulation + parameters.getParametersTwo();
        return output;
    }

    private double calculateByFormulaTwo(String species, Double accumulation) {
        double output = 0;
        QueryWrapper<Parameters> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("formula", 2);
        Parameters parameters = new Parameters();
        queryWrapper.eq("species", species);
        parameters = parametersService.getOne(queryWrapper);
        output = parameters.getParametersTwo() * Math.pow(accumulation, parameters.getParametersOne());
        return output;
    }

    private double calculateByFormulaThree(String species, Double accumulation) {
        double output = 0;
        QueryWrapper<Parameters> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("formula", 3);
        Parameters parameters = new Parameters();
        queryWrapper.eq("species", species);
        parameters = parametersService.getOne(queryWrapper);
        output = parameters.getParametersOne() * accumulation + parameters.getParametersTwo();
        return output;
    }

    private double calculateByFormulaFour(String species, Double accumulation) {
        double output = 0;
        QueryWrapper<Parameters> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("formula", 4);
        Parameters parameters = new Parameters();
        queryWrapper.eq("species", species);
        parameters = parametersService.getOne(queryWrapper);
        output = accumulation * parameters.getParametersOne() *
                parameters.getParametersTwo() * (parameters.getParametersThree() + 1);
        return output;
    }

    public double forecast(OutputData outputData) {
        double output = 0;
        double year = Double.parseDouble(outputData.getYear());
        switch (outputData.getSpecies()) {
            case "songshu":
                output = year * 3.289 - 6588.167;
                break;
            case "huamuke":
                output = year * 2.851 - 5632.328;
                break;
            case "yangliuke":
                output = year * 1.227 - 2376.751;
                break;
            case "shanmu":
                output = year * 3.364 - 6674.848;
                break;
            case "douke":
                output = year * 3.289 - 6588.167;
                break;
            case "zhangshu":
                output = year * 3.289 - 6588.167;
                break;
            case "mulanke":
                output = year * 3.289 - 6588.167;
                break;
            default:
                output = year * 3.289 - 6588.167;
                break;
        }
        outputData.setOutput(output);

        return output;
    }
}
