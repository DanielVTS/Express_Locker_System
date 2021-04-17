package cn.lingnan.controller;


import cn.lingnan.dto.WebAccount;
import cn.lingnan.exception.APIException;
import cn.lingnan.service.WebAccountService;
import cn.lingnan.util.CommonResult;
import cn.lingnan.util.PageResult;
import cn.lingnan.util.PhoneFormatCheckUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequestMapping("account")
@RestController
@CrossOrigin
public class WebAccountController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private WebAccountService webAccountService;

    @PostMapping("register")
    public CommonResult<Object> register(@RequestBody  WebAccount webAccount) {
        if(webAccountService.findByPhone(webAccount.getPhone()).size()>0){
            return CommonResult.phone_failed();
        }
        Calendar a = Calendar.getInstance();
        webAccount.setStatus(1);
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
        String username,password;
        if(map.containsKey("username")){
            username = map.get("username");
        }else{
            return CommonResult.failed();
        }
        if(map.containsKey("password")){
            password = map.get("password");
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
            WebAccount update= new WebAccount();
            update.setId(account.getId());
            update.setStatusTime(new Date(System.currentTimeMillis()));
            webAccountService.updateByPrimaryKeySelective(update);
            return CommonResult.success(account);
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

    @PutMapping("update")
    //public CommonResult<Object> update(@RequestBody Map<String, String> map) {
    public CommonResult<Object> update(@RequestBody WebAccount webAccount) {
        //WebAccount webAccount = null;
        if(webAccount.getId()==null){
            return CommonResult.failed();
        }
        if(!webAccountService.findByPhone(webAccount.getPhone()).isEmpty()){
            return CommonResult.phone_failed();
        }
        Calendar a = Calendar.getInstance();
        webAccount.setUpdateTime(a.getTime());
        a.add(Calendar.YEAR, 5);
        webAccount.setExpTime(a.getTime());
        int result = webAccountService.updateByPrimaryKeySelective(webAccount);
        if (result == 1) {
            return CommonResult.success();
        } else throw new APIException(500, "WebAccount记录插入异常！");
    }

    @PutMapping("updatePassword")
    public CommonResult<Object> updatePassword(@RequestBody Map<String, String> map) {
        WebAccount webAccount = new WebAccount();
        webAccount.setId(Long.valueOf(map.get("id")));
        if(webAccountService.selectByPrimaryKey(
                webAccount.getId()).
                getPassword().equals(map.get("password1"))){
            webAccount.setPassword(map.get("password2"));
            if(webAccount.getId()==null){
                return CommonResult.failed();
            }
            Calendar a = Calendar.getInstance();
            webAccount.setUpdateTime(a.getTime());
            a.add(Calendar.YEAR, 5);
            webAccount.setExpTime(a.getTime());
            int result = webAccountService.updateByPrimaryKeySelective(webAccount);
            if (result == 1) {
                return CommonResult.success();
            } else throw new APIException(500, "WebAccount记录插入异常！");
        }else{
            return CommonResult.failed();
        }
    }

    @DeleteMapping("delete/{id}")
    public CommonResult<Object> delete(@PathVariable(name = "id", required = true) String id){
        int result=0;
        try {
            result=webAccountService.deleteByPrimaryKey(Long.parseLong(id));
        }catch (NumberFormatException e){
            return CommonResult.failed("ID格式有误！");
        }
        if(result==0){
            return CommonResult.failed();
        }else {
            return CommonResult.success();
        }
    }

}
