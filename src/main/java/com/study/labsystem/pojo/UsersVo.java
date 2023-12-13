package com.study.labsystem.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
/**
 * <p>
 *
 * </p>
 *
 * @author 裹个小脑
 * @since 2023-12-06
 */
@Data
public class UsersVo {

    private Integer id;

    private String username;

    private Integer roleId;

    private Integer isDefault;

    private Roles role;


}
