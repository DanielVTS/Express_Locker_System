package cn.lingnan.controller;


import cn.lingnan.dto.LockerBasicInformation;
import cn.lingnan.exception.APIException;
import cn.lingnan.service.LockerBasicInformationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("lockerBasicInformation")
@Controller
public class LockerBasicInformationController {
    private final Logger logger = LoggerFactory.getLogger(LockerBasicInformationService.class);
    @Resource
    private LockerBasicInformationService lockerBasicInformationService;

    @PostMapping("addLocker")
    @ResponseBody
    public String AddLocker(@RequestBody @Validated LockerBasicInformation record) {
        logger.info("添加Locker ==>" + record.toString());
        int result = lockerBasicInformationService.insert(record);
        if (result != 1) {
            throw new APIException(500, "LBI记录插入异常！");
        }
        return "LBI记录插入成功！";
    }

    @PostMapping("removeLocker")
    @ResponseBody
    public String RemoveLocker(@RequestBody @Validated LockerBasicInformation record) {
        logger.info("删除Locker ==>" + record.toString());
        int result = lockerBasicInformationService.deleteByPrimaryKey(record.getLockerId());
        if (result != 1) {
            throw new APIException(500, "LBI记录删除异常！");
        }
        return "LBI记录删除成功！";
    }

    @PostMapping("editLocker")
    @ResponseBody
    public String EditLocker(@RequestBody @Validated LockerBasicInformation record) {
        logger.info("修改Locker ==>" + record.toString());
        int result = lockerBasicInformationService.updateByPrimaryKey(record);
        if (result != 1) {
            throw new APIException(500, "LBI记录修改异常！");
        }
        return "LBI记录修改成功！";
    }

    @GetMapping("findLockerById")
    @ResponseBody
    public Object FindLocker(String LockerId) {
        logger.info("ID查找Locker ==>ID: " + LockerId);
        LockerBasicInformation record = lockerBasicInformationService.selectByPrimaryKey(Long.parseLong(LockerId));
        if (record != null) {
            return record;
        } else {
            return "无此快递柜记录！";
        }
    }

    @PostMapping("getAllLocker")
    @ResponseBody
    public List<LockerBasicInformation> queryAll() {
        return this.lockerBasicInformationService.selectAll();
    }


}
