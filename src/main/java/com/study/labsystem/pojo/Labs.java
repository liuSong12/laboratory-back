package com.study.labsystem.pojo;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 裹个小脑
 * @since 2023-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("labs")
@ApiModel(value="Labs对象", description="")
public class Labs implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String labName;

    private Integer capacity;

    private Integer collegeId;

    private BigDecimal lat;

    private BigDecimal lng;

    private Integer labId;

    @TableField(exist = false)
    private String labTypeName;

    @TableField(exist = false)
    private String labCollegeName;

}
