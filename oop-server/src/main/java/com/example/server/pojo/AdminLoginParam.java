package com.example.server.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Jiaweiaaa
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value= "AdminLoginObject", description = "")

public class AdminLoginParam {
    @ApiModelProperty(value = "username",required = true)
    private String username;
    @ApiModelProperty(value = "password",required = true)
    private String password;
}
