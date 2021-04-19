package cn.lingnan.controller;


import cn.lingnan.dto.PackageBoxInformation;
import cn.lingnan.dto.PackageInformation;
import cn.lingnan.exception.APIException;
import cn.lingnan.service.PackageBoxInformationService;
import cn.lingnan.service.PackageInformationService;
import cn.lingnan.util.CommonResult;
import cn.lingnan.util.MapToBean;
import cn.lingnan.util.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequestMapping("packageInformation")
@Controller
@CrossOrigin
//@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:8080", "http://api.danielvt.xyz", "http://express.danielvt.xyz"}, allowedHeaders = "*", methods = {}, allowCredentials = "true")
public class PackageInformationController {
    @Resource
    private PackageInformationService packageInformationService;
    @Resource
    private PackageBoxInformationService packageBoxInformationService;

    private final Logger logger = LoggerFactory.getLogger(PackageInformationService.class);

    @PostMapping("addPackage")
    @ResponseBody
    public String AddPackageInformation(@RequestBody PackageInformation record) {
        logger.info("添加PackageInformation ==>" + record.toString());
        int result = packageInformationService.insertSelective(record);
        if (result != 1) {
            throw new APIException(500, "PackageInformation记录插入异常！");
        }
        return "PackageInformation记录插入成功！";
    }

    @DeleteMapping("removePackage/{id}")
    @ResponseBody
    public String RemovePackageInformation(@PathVariable(name = "id") String id) {
        logger.info("删除PackageInformation ==>" + id);
        int result = packageInformationService.deleteByPrimaryKey(Long.valueOf(id));
        if (result != 1) {
            throw new APIException(500, "PackageInformation记录删除异常！");
        }
        return "PackageInformation记录删除成功！";
    }

    @PutMapping("editPackage")
    @ResponseBody
    public String EditPackageInformation(@RequestBody PackageInformation record) {
        Date now = new Date(System.currentTimeMillis());
        record.setUpdateTime(now);
        if (record.getStatus() == 5) {
            record.setStatusTime(now);
        }
        logger.info("修改PackageInformation ==>" + record);
        int result = packageInformationService.updateByPrimaryKeySelective(record);
        if (result != 1) {
            throw new APIException(500, "PackageInformation记录修改异常！");
        }
        return "PackageInformation记录修改成功！";
    }

    @Deprecated
    @PostMapping("findByExpressNumber")
    @ResponseBody
    public Object findByExpressNumber(String expressNumber) {
        logger.info("快递号查找PackageInformation ==>" + expressNumber);
        List<PackageInformation> record = packageInformationService.findByExpressNumber(expressNumber);
        if (!record.isEmpty()) {
            return record;
        } else {
            return "查无记录！";
        }
    }


    @PostMapping("postPackage")
    @ResponseBody
    public CommonResult<Object> postPackage(@RequestBody Map<String, Object> map) {
        String lockerID;
        String lockerBoxID;
        long randomNum;
        if (map.containsKey("lockerID")) {
            lockerID = map.get("lockerID").toString();
            map.remove("lockerID");
        } else {
            return CommonResult.failed();
        }
        if (map.containsKey("lockerBoxID")) {
            lockerBoxID = map.get("lockerBoxID").toString();
            map.remove("lockerBoxID");
        } else {
            return CommonResult.failed();
        }
        PackageInformation record = (PackageInformation) MapToBean.transMap2Bean(map, new PackageInformation());
        logger.info("添加PackageInformation ==>" + record);
        int result = packageInformationService.insertSelective(record);
        if (result != 1) {
            throw new APIException(500, "PackageInformation记录插入异常！");
        }
        List<PackageInformation> list = packageInformationService.findByExpressNumber(record.getExpressNumber());
        PackageInformation result2 = null;
        for (PackageInformation i : list) {
            if (i.getSenderPhone().equals(record.getSenderPhone()) && i.getReceiverPhone().equals(record.getReceiverPhone())) {
                result2 = i;
            }
        }
        if (result2 != null) {
            PackageBoxInformation packageBoxInformation = new PackageBoxInformation();
            packageBoxInformation.setPackageId(result2.getPackageId());
            packageBoxInformation.setLockerId(Long.parseLong(lockerID));
            packageBoxInformation.setLockerBoxId(lockerBoxID);
            while (true) {
                randomNum = System.currentTimeMillis();
                int ran3 = (int) (randomNum % (99999999 - 10000000) + 10000000);
                PackageBoxInformation pbi = packageBoxInformationService.findByCode(Integer.toString(ran3));
                if (pbi == null) {
                    packageBoxInformation.setPackageCode(Integer.toString(ran3));
                    break;
                }
            }
            Date date = new Date(randomNum);
            packageBoxInformation.setCreateTime(date);
            packageBoxInformation.setUpdateTime(date);
            packageBoxInformation.setStatusTime(date);
            packageBoxInformation.setStatus(0);
            packageBoxInformationService.insertSelective(packageBoxInformation);
            return CommonResult.success();
        } else return CommonResult.failed();
    }

    /**
     * @param query    快递号、手机号模糊查找
     * @param pagenum
     * @param pagesize
     * @return
     */
    @GetMapping("list")
    @ResponseBody
    public CommonResult<Object> findPackageByPage(
            @RequestParam(name = "query", required = false) String query,
            @RequestParam(name = "pagenum", defaultValue = "1") Integer pagenum,
            @RequestParam(name = "pagesize", defaultValue = "5") Integer pagesize) {

        if (pagenum <= 0 || pagesize <= 0) {
            return CommonResult.failed("参数有误！");
        }
        //PageResult<WebAccount> pageResult=webAccountService.findUserByPage(query,pagenum,pagesize);
        PageResult<PackageInformation> pageResult = packageInformationService.findPackageByPage(query, pagenum, pagesize);
        if (CollectionUtils.isEmpty(pageResult.getItems())) {
            return CommonResult.success();
        }
        return CommonResult.success(pageResult);
    }

}
