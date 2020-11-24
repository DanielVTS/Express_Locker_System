package cn.lingnan.dto;

import java.util.Date;

import javax.validation.constraints.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Data
public class WebAccount {
    private Integer id;

    @NotNull(message = "用户账号不能为空")
    @Size(min = 6, max = 11, message = "账号长度必须是6-11个字符")
    private String username;

    @NotNull(message = "用户密码不能为空")
    @Size(min = 8, max = 20, message = "密码长度必须是8-20个字符")
    private String password;

    @NotNull(message = "手机号码不能为空")
    @Pattern(regexp = "0?(13|14|15|17|18|19)[0-9]{9}", message = "手机号码不符合规则")
    private String phone;

    private Integer status;

    private Date expTime;

    private Date createTime;

    private Date updateTime;

    private Date statusTime;

    private String authority;

    private String remark;
}