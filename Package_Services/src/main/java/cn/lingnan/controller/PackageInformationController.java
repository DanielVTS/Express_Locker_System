package cn.lingnan.controller;


import cn.lingnan.dto.PackageInformation;
import cn.lingnan.exception.APIException;
import cn.lingnan.service.PackageInformationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@RequestMapping("packageInformation")
@Controller
public class PackageInformationController {
    @Resource
    private PackageInformationService packageInformationService;

    private final Logger logger = LoggerFactory.getLogger(PackageInformationService.class);

    @PostMapping("addPackage")
    public String AddLockerBox(@RequestBody @Validated PackageInformation record) {
        logger.info("添加LockerBox ==>" + record.toString());
        int result = packageInformationService.insert(record);
        if (result != 1) {
            throw new APIException(500, "Package记录插入异常！");
        }
        return "Package记录插入成功！";
    }

    @PostMapping("removePackage")
    public String RemoveLockerBox(@RequestBody @Validated PackageInformation record) {
        logger.info("删除Locker ==>" + record.toString());
        int result = packageInformationService.deleteByPrimaryKey(record.getPackageId());
        if (result != 1) {
            throw new APIException(500, "Package记录删除异常！");
        }
        return "Package记录删除成功！";
    }

    @PostMapping("editPackage")
    public String EditLockerBox(@RequestBody @Validated PackageInformation record) {
        logger.info("修改Locker ==>" + record.toString());
        int result = packageInformationService.updateByPrimaryKey(record);
        if (result != 1) {
            throw new APIException(500, "Package记录修改异常！");
        }
        return "Package记录修改成功！";
    }

}
