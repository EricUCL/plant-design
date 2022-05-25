package com.qingge.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2022-04-24
 */
@Getter
@Setter
@TableName("output_data")
@ApiModel(value = "OutputData对象", description = "")
public class OutputData implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String species;

    private String year;

    private Double accumulation;

    private Integer formula;

    private Double output;

}
