package com.qingge.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author demo
 * @since 2022-05-13
 */
@Getter
@Setter
@ApiModel(value = "Parameters对象", description = "")
public class Parameters implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer formula;

    private String species;

    private Double parametersOne;

    private Double parametersTwo;

    private Double parametersThree;


}
