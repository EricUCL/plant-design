package com.qingge.springboot.controller;


import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingge.springboot.common.MyMap;
import com.qingge.springboot.entity.OutputData;
import com.qingge.springboot.entity.User;
import com.qingge.springboot.service.IOutputDataService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author demo
 * @since 2022-04-24
 */
@RestController
@RequestMapping("/output-data")
public class OutputDataController {
    @Resource
    private IOutputDataService outputDataService;

//    @PostMapping("/calculate")
//    public Boolean save(@RequestParam("species")String species,
//                        @RequestParam("formula")String formula,
//                        @RequestParam("accumulation")String accumulation) {
//        OutputData outputData=new OutputData();
//        System.out.println(outputData);
//        return outputDataService.saveOrUpdate(outputData);
//    }

    @PostMapping("/calculate")
    public OutputData calculate(@RequestBody Map<String, String> params) {

        OutputData outputData = new OutputData();
        outputData.setAccumulation(Double.parseDouble(params.get("accumulation")));
        outputData.setSpecies(params.get("species"));
        outputData.setFormula(Integer.parseInt(params.get("formula")));
        outputData.setYear(params.get("year"));
        System.out.println(outputData);
        double output = outputDataService.calculate(outputData);
        outputData.setOutput(output);
        return outputData;
    }

    @PostMapping("/forecast")
    public OutputData forecast(@RequestBody Map<String, String> params) {
        OutputData outputData = new OutputData();
        outputData.setSpecies(params.get("species"));
        outputData.setYear(params.get("year"));
        System.out.println(outputData);
        double output = outputDataService.forecast(outputData);
        outputData.setOutput(output);
        return outputData;
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return outputDataService.removeById(id);
    }

    @PostMapping("/delete/batch")
    public boolean deleteUserByIdBatch(@RequestBody List<Integer> ids) {
        return outputDataService.removeBatchByIds(ids);
    }

    @GetMapping
    public List<OutputData> findAll() {
        return outputDataService.list();
    }

    @GetMapping("/{id}")
    public OutputData findOne(@PathVariable Integer id) {
        return outputDataService.getById(id);
    }

    @GetMapping("/page")
    public Page<OutputData> findPage(@RequestParam Integer pageNum,
                                     @RequestParam Integer pageSize) {
        IPage<OutputData> page = new Page<>(pageNum, pageSize);
        QueryWrapper<OutputData> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return outputDataService.page(new Page<>(pageNum, pageSize));
    }

    @GetMapping("/export")
    public void exportUser(HttpServletResponse response) throws Exception {

        List<OutputData> rows = outputDataService.list();
        for (OutputData outputData : rows) {
            outputData.setSpecies(MyMap.PLANT.get(outputData.getSpecies()));
        }
        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter(true);
// 合并单元格后的标题行，使用默认标题样式
        writer.merge(6, "蓄积量统计");
// 一次性写出内容，使用默认样式，强制输出标题
        writer.write(rows, true);


        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=test.xlsx");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(out);

        // 关闭writer，释放内存
        writer.close();

    }

    @PostMapping("/import")
    public void imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<User> all = reader.readAll(User.class);
        System.out.println(all);
    }
}

