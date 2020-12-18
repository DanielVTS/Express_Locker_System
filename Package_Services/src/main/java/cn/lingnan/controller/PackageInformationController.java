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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("packageInformation")
@Controller
public class PackageInformationController {
    @Resource
    private PackageInformationService packageInformationService;

    private final Logger logger = LoggerFactory.getLogger(PackageInformationService.class);

    @PostMapping("addPackage")
    @ResponseBody
    public String AddPackageInformation(@RequestBody @Validated PackageInformation record) {
        logger.info("添加PackageInformation ==>" + record.toString());
        int result = packageInformationService.insert(record);
        if (result != 1) {
            throw new APIException(500, "PackageInformation记录插入异常！");
        }
        return "PackageInformation记录插入成功！";
    }

    @PostMapping("removePackage")
    @ResponseBody
    public String RemovePackageInformation(@RequestBody @Validated PackageInformation record) {
        logger.info("删除PackageInformation ==>" + record.toString());
        int result = packageInformationService.deleteByPrimaryKey(record.getPackageId());
        if (result != 1) {
            throw new APIException(500, "PackageInformation记录删除异常！");
        }
        return "PackageInformation记录删除成功！";
    }

    @PostMapping("editPackage")
    @ResponseBody
    public String EditPackageInformation(@RequestBody @Validated PackageInformation record) {
        logger.info("修改PackageInformation ==>" + record.toString());
        int result = packageInformationService.updateByPrimaryKey(record);
        if (result != 1) {
            throw new APIException(500, "PackageInformation记录修改异常！");
        }
        return "PackageInformation记录修改成功！";
    }

    @PostMapping("findByExpressNumber")
    @ResponseBody
    public Object findByExpressNumber(Long expressNumber) {
        logger.info("快递号查找PackageInformation ==>" + expressNumber);
        List<PackageInformation> record = packageInformationService.findByExpressNumber(expressNumber);
        if (!record.isEmpty()) {
            return record;
        } else {
            return "查无记录！";
        }
    }


}
