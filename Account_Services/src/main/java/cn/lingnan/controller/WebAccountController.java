package cn.lingnan.controller;


import cn.lingnan.dto.WebAccount;
import cn.lingnan.exception.APIException;
import cn.lingnan.service.WebAccountService;
import cn.lingnan.util.CommonResult;
import cn.lingnan.util.PageResult;
import cn.lingnan.util.PhoneFormatCheckUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@RequestMapping("account")
@RestController
public class WebAccountController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private WebAccountService webAccountService;

    @PostMapping("register")
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
    public CommonResult<Object> login(@RequestBody Map<String, String> map) {
        String username=null,password=null;
        if(map.containsKey("username")){
            username = map.get("username").toString();
        }else{
            return CommonResult.failed();
        }
        if(map.containsKey("password")){
            password = map.get("password").toString();
        }else{
            return CommonResult.failed();
        }
        List<WebAccount> result;
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
        } else return CommonResult.validateFailed("用户名或密码错误！");
    }

    @GetMapping("list")
    public CommonResult<Object> queryUserByPage(
            @RequestParam(name = "query", required = false) String query,
            @RequestParam(name = "pagenum", defaultValue = "1") Integer pagenum,
            @RequestParam(name = "pagesize", defaultValue = "5") Integer pagesize){

        if (pagenum <= 0 || pagesize <= 0) {
            return CommonResult.failed("参数有误！");
        }
        PageResult<WebAccount> pageResult=webAccountService.findUserByPage(query,pagenum,pagesize);

        if (CollectionUtils.isEmpty(pageResult.getItems())){
            return CommonResult.success();
        }
        return CommonResult.success(pageResult);
    }


}
