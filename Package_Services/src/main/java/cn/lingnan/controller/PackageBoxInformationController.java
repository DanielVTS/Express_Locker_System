package cn.lingnan.controller;

import cn.lingnan.dto.PackageBoxInformation;
import cn.lingnan.exception.APIException;
import cn.lingnan.service.PackageBoxInformationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@RequestMapping("packageBoxInformation")
@Controller
public class PackageBoxInformationController {
    @Resource
    private PackageBoxInformationService packageBoxInformationService;

    private final Logger logger = LoggerFactory.getLogger(PackageBoxInformationService.class);

    @PostMapping("add")
    @ResponseBody
    public String AddPackageBoxInformation(@RequestBody @Validated PackageBoxInformation record) {
        logger.info("添加PackageBoxInformation ==>" + record.toString());
        int result = packageBoxInformationService.insert(record);
        if (result != 1) {
            throw new APIException(500, "PackageBoxInformation记录插入异常！");
        }
        return "PackageBoxInformation记录插入成功！";
    }

    @PostMapping("remove")
    @ResponseBody
    public String RemovePackageBoxInformation(@RequestBody @Validated PackageBoxInformation record) {
        logger.info("删除PackageBoxInformation ==>" + record.toString());
        int result = packageBoxInformationService.deleteByPrimaryKey(record.getPbiId());
        if (result != 1) {
            throw new APIException(500, "PackageBoxInformation记录删除异常！");
        }
        return "PackageBoxInformation记录删除成功！";
    }

    @PostMapping("edit")
    @ResponseBody
    public String EditLockerBox(@RequestBody @Validated PackageBoxInformation record) {
        logger.info("修改PackageBoxInformation ==>" + record.toString());
        int result = packageBoxInformationService.updateByPrimaryKey(record);
        if (result != 1) {
            throw new APIException(500, "PackageBoxInformation记录修改异常！");
        }
        return "PackageBoxInformation记录修改成功！";
    }

    @PostMapping("findByPackageId")
    @ResponseBody
    public Object findByPackageId(Long packageId) {
        logger.info("快递号查找Package ==>" + packageId);
        PackageBoxInformation record = packageBoxInformationService.findByPackageId(packageId);
        if (record.getPbiId() != null && record.getPbiId() != "") {
            return record;
        } else {
            return "查无记录！";
        }
    }
}
