package cn.lingnan.controller;

import cn.lingnan.dto.LockerBoxInformation;
import cn.lingnan.exception.APIException;
import cn.lingnan.service.LockerBoxInformationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("lockerBoxInformation")
@Controller
public class LockerBoxInformationController {
    private final Logger logger = LoggerFactory.getLogger(LockerBoxInformationService.class);
    @Resource
    private LockerBoxInformationService lockerBoxInformationService;

    @PostMapping("addLockerBox")
    @ResponseBody
    public String AddLockerBox(@RequestBody @Validated LockerBoxInformation record) {
        logger.info("添加LockerBox ==>" + record.toString());
        int result = lockerBoxInformationService.insert(record);
        if (result != 1) {
            throw new APIException(500, "Box记录插入异常！");
        }
        return "Box记录插入成功！";
    }

    @PostMapping("removeLockerBox")
    @ResponseBody
    public String RemoveLockerBox(@RequestBody @Validated LockerBoxInformation record) {
        logger.info("删除Locker ==>" + record.toString());
        int result = lockerBoxInformationService.deleteByPrimaryKey(record.getLockerId());
        if (result != 1) {
            throw new APIException(500, "Box记录删除异常！");
        }
        return "Box记录删除成功！";
    }

    @PostMapping("editLockerBox")
    @ResponseBody
    public String EditLockerBox(@RequestBody @Validated LockerBoxInformation record) {
        logger.info("修改Locker ==>" + record.toString());
        int result = lockerBoxInformationService.updateByPrimaryKey(record);
        if (result != 1) {
            throw new APIException(500, "Box记录修改异常！");
        }
        return "Box记录修改成功！";
    }

    @GetMapping("findBoxListInOneLocker")
    @ResponseBody
    public Object FindBoxListInOneLocker(String lockerId) {
        logger.info("ID查找Locker ==>ID: " + lockerId);
        List<LockerBoxInformation> record = lockerBoxInformationService.findBoxListInOneLocker(Long.parseLong(lockerId));
        if (!record.isEmpty()) {
            return record;
        } else {
            return "查无记录！";
        }
    }
}
