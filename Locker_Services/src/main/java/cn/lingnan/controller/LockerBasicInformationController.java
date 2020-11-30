package cn.lingnan.controller;


import cn.lingnan.dto.LockerBasicInformation;
import cn.lingnan.exception.APIException;
import cn.lingnan.service.LockerBasicInformationService;
import cn.lingnan.util.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@RequestMapping("lockerbasicinformation")
@Controller
public class LockerBasicInformationController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private LockerBasicInformationService lockerBasicInformationService;

    public CommonResult<String> AddLocker(@RequestBody @Validated LockerBasicInformation record){
        int result=lockerBasicInformationService.insert(record);
        if (result!=1) {
            throw new APIException(500,"LBI记录插入异常！");
        }
        return CommonResult.success("LBI记录插入成功！");
    }

    public CommonResult<String> RemoveLocker(@RequestBody @Validated LockerBasicInformation record){
        int result=lockerBasicInformationService.deleteByPrimaryKey(record.getLockerId());
        if (result!=1) {
            throw new APIException(500,"LBI记录删除异常！");
        }
        return CommonResult.success("LBI记录删除成功！");
    }

    public CommonResult<String> EditLocker(@RequestBody @Validated LockerBasicInformation record){
        int result=lockerBasicInformationService.updateByPrimaryKey(record);
        if (result!=1) {
            throw new APIException(500,"LBI记录删除异常！");
        }
        return CommonResult.success("LBI记录删除成功！");
    }


}
