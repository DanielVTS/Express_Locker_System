package cn.lingnan.controller;

import cn.lingnan.dto.PackageBoxInformation;
import cn.lingnan.exception.APIException;
import cn.lingnan.service.PackageBoxInformationService;
import cn.lingnan.util.CommonResult;
import cn.lingnan.util.PageResult;
import cn.lingnan.util.UUIDCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("packageBoxInformation")
@Controller
@CrossOrigin
//@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:8080", "http://api.danielvt.xyz", "http://express.danielvt.xyz"}, allowedHeaders = "*", methods = {}, allowCredentials = "true")
public class PackageBoxInformationController {
    @Resource
    private PackageBoxInformationService packageBoxInformationService;

    private final Logger logger = LoggerFactory.getLogger(PackageBoxInformationService.class);

    @PostMapping("add")
    @ResponseBody
    @Deprecated
    public String AddPackageBoxInformation(@RequestBody @Validated PackageBoxInformation record) {
        logger.info("添加PackageBoxInformation ==>" + record.toString());
        int result = packageBoxInformationService.insert(record);
        if (result != 1) {
            throw new APIException(500, "PackageBoxInformation记录插入异常！");
        }
        return "PackageBoxInformation记录插入成功！";
    }

    @DeleteMapping("remove")
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
    public String EditPackageBoxInformation(@RequestBody @Validated PackageBoxInformation record) {
        logger.info("修改PackageBoxInformation ==>" + record.toString());
        int result = packageBoxInformationService.updateByPrimaryKey(record);
        if (result != 1) {
            throw new APIException(500, "PackageBoxInformation记录修改异常！");
        }
        return "PackageBoxInformation记录修改成功！";
    }

    @PostMapping("findByPackageId")
    @ResponseBody
    public Object findByPackageId(@RequestParam Long packageId) {
        logger.info("packageId查找Package ==>" + packageId);
        PackageBoxInformation record = packageBoxInformationService.findByPackageId(packageId);

        if (record != null) {
            return record;
        } else {
            return "查无记录！";
        }
    }

    @GetMapping("findByCode")
    @ResponseBody
    public Object findByCode(@RequestParam String code) {
        logger.info("Code查找Package ==>" + code);
        PackageBoxInformation record = packageBoxInformationService.findByCode(code);
        if (record != null) {
            return record;
        } else {
            return "查无记录！";
        }

    }


    /**
     * @param query    UUID,packageId,packageCode
     * @param pagenum
     * @param pagesize
     * @return
     */
    @GetMapping("list")
    public CommonResult<PageResult<PackageBoxInformation>> queryPBIByPage(
            @RequestParam(name = "query", required = false) String query,
            @RequestParam(name = "pagenum", defaultValue = "1") Integer pagenum,
            @RequestParam(name = "pagesize", defaultValue = "5") Integer pagesize) {

        PageResult<PackageBoxInformation> pageResult;
        if (pagenum <= 0 || pagesize <= 0) {
            return CommonResult.failed("参数有误！");
        }
        if (query != null && query.length() == 18) {
            try {
                List<PackageBoxInformation> a = new ArrayList<>();
                a.add(packageBoxInformationService.findByPackageId(Long.parseLong(query)));
                return CommonResult.success(new PageResult<>(1L, 1, a));
            } catch (NumberFormatException ignored) {
            }
        }
        if (query != null && UUIDCheck.isValidUUID(query)) {
            PackageBoxInformation temp = packageBoxInformationService.selectByPrimaryKey(query);
            if (temp.getPbiId() != null) {
                List<PackageBoxInformation> list = new ArrayList<>();
                list.add(temp);
                return CommonResult.success(new PageResult<>(1L, 1, list));
            }
        }
        pageResult = packageBoxInformationService.findByPage(query, pagenum, pagesize);
        return CommonResult.success(pageResult);

    }
}
