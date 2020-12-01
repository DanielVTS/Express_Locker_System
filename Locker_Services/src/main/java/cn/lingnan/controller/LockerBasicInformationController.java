package cn.lingnan.controller;


import cn.lingnan.dto.LockerBasicInformation;
import cn.lingnan.exception.APIException;
import cn.lingnan.service.LockerBasicInformationService;
import cn.lingnan.util.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@RequestMapping("lockerBasicInformation")
@Controller
public class LockerBasicInformationController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private LockerBasicInformationService lockerBasicInformationService;

    @PostMapping("addLocker")
    public CommonResult<String> AddLocker(@RequestBody @Validated LockerBasicInformation record){
        logger.debug("添加Locker ==>"+record.toString());
        int result=lockerBasicInformationService.insert(record);
        if (result!=1) {
            throw new APIException(500,"LBI记录插入异常！");
        }
        return CommonResult.success("LBI记录插入成功！");
    }

    @PostMapping("removeLocker")
    public CommonResult<String> RemoveLocker(@RequestBody @Validated LockerBasicInformation record){
        logger.debug("删除Locker ==>"+record.toString());
        int result=lockerBasicInformationService.deleteByPrimaryKey(record.getLockerId());
        if (result!=1) {
            throw new APIException(500,"LBI记录删除异常！");
        }
        return CommonResult.success("LBI记录删除成功！");
    }
    @PostMapping("editLocker")
    public CommonResult<String> EditLocker(@RequestBody @Validated LockerBasicInformation record){
        logger.debug("修改Locker ==>"+record.toString());
        int result=lockerBasicInformationService.updateByPrimaryKey(record);
        if (result!=1) {
            throw new APIException(500,"LBI记录修改异常！");
        }
        return CommonResult.success("LBI记录删除成功！");
    }

    @PostMapping("findLockerById")
    public CommonResult<?> FindLocker(int id){
        logger.debug("ID查找Locker ==>ID: "+id);
        LockerBasicInformation record=lockerBasicInformationService.selectByPrimaryKey(id);
        if (record!=null){
            return CommonResult.success(record);
        }else {
            return CommonResult.success("无此快递柜记录！");
        }
    }


}
