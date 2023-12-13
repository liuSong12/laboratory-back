package com.study.labsystem.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *  Right实体类
 * </p>
 *
 * @author study
 * @since 2023-12-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rights")
@ApiModel(value="Rights对象", description="")
public class Rights implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id值")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("权限名称")
    private String title;

    @ApiModelProperty("权限路径")
    private String path;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("父路由")
    @JsonIgnore
    @TableField("parent_id")
    private Integer parentId;

    @JsonIgnore
    @TableField("is_left")
    private Integer isLeaf;

    @ApiModelProperty("子路由")
    @TableField(exist = false)
    private List<Rights> children;


}
