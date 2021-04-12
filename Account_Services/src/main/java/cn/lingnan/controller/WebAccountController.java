package cn.lingnan.controller;


import cn.lingnan.dto.WebAccount;
import cn.lingnan.exception.APIException;
import cn.lingnan.service.WebAccountService;
import cn.lingnan.util.CommonResult;
import cn.lingnan.util.PhoneFormatCheckUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.List;

@RequestMapping("account")
@Controller
public class WebAccountController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private WebAccountService webAccountService;

    @PostMapping("register")
    @ResponseBody
    public CommonResult<Object> register(@RequestBody @Validated WebAccount webAccount) {
        Calendar a = Calendar.getInstance();
        webAccount.setCreateTime(a.getTime());
        webAccount.setUpdateTime(a.getTime());
        webAccount.setStatusTime(a.getTime());
        a.add(Calendar.YEAR, 5);
        webAccount.setExpTime(a.getTime());
        int result = webAccountService.insertSelective(webAccount);
        if (result == 1) {
            return CommonResult.success();
        } else throw new APIException(500, "WebAccount记录插入异常！");
    }

    @PostMapping("login")
    @ResponseBody
    public CommonResult<Object> login(@RequestBody String username, String password) {
        List<WebAccount> result = null;
        if (PhoneFormatCheckUtils.isPhoneLegal(username)) {
            result = webAccountService.findByPhone(username);
        } else result = webAccountService.findByName(username);
        WebAccount account = null;
        if (result != null) {
            for (WebAccount a : result) {
                if (password.equals(a.getPassword())) {
                    account = a;
                }
            }
        }
        if (account != null) {
            logger.info(account.toString());
            return CommonResult.success();
        } else return CommonResult.failed("用户名或密码错误！");
    }


}
