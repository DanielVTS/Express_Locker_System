package cn.lingnan.controller;


import cn.lingnan.dto.WebAccount;
import cn.lingnan.util.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("account")
@Controller
public class WebAccountController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping("register")
    @ResponseBody
    public CommonResult<Object> register(@RequestBody @Validated WebAccount webAccount){

        return CommonResult.success();
    }





}
