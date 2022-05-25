package com.qingge.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingge.springboot.entity.Formula;
import com.qingge.springboot.entity.Parameters;
import com.qingge.springboot.mapper.ParametersMapper;
import com.qingge.springboot.service.IParametersService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author demo
 * @since 2022-05-13
 */
@Service
public class ParametersServiceImpl extends ServiceImpl<ParametersMapper, Parameters> implements IParametersService {
    public List<Formula> findAll(Integer formula) {
        List<Formula> result = new ArrayList<>();
        QueryWrapper<Parameters> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("formula", formula);
        List<Parameters> parameters = this.list(queryWrapper);

        for (Parameters p : parameters) {
            Formula f = new Formula();
            f.setFormula_index(p.getFormula());
            f.setSpecies(p.getSpecies());
            switch (formula) {
                case 1:
                    f.setFormula("BEF = " + p.getParametersOne().toString() + " * V + " + p.getParametersTwo().toString());
                    break;
                case 2:
                    f.setFormula("BEF = " + "x^" + p.getParametersOne().toString() + " * " + p.getParametersTwo().toString());
                    break;
                case 3:
                    f.setFormula("BEF = " + p.getParametersOne().toString() + " * V + " + p.getParametersTwo().toString());
                    break;
                case 4:
                    f.setFormula("BEF = [V * " + p.getParametersOne().toString() + " * " + p.getParametersTwo().toString()
                            + "] * (1 + " + p.getParametersThree().toString() + ")");
                    break;
            }

            result.add(f);
        }
        return result;
    }
}
