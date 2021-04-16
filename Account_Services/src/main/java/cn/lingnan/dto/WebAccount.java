package cn.lingnan.dto;

import java.util.Date;

import javax.validation.constraints.*;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Data
public class WebAccount {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @NotNull(message = "用户账号不能为空")
    @Length(min = 6, max = 11, message = "账号长度必须是6-11个字符")
    private String username;

    @NotNull(message = "用户密码不能为空")
    @Length(min = 8, max = 20, message = "密码长度必须是8-20个字符")
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