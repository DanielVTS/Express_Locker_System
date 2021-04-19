package cn.lingnan.controller;


import cn.lingnan.dto.LockerBasicInformation;
import cn.lingnan.exception.APIException;
import cn.lingnan.service.LockerBasicInformationService;
import cn.lingnan.service.LockerBoxInformationService;
import cn.lingnan.util.CommonResult;
import cn.lingnan.util.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequestMapping("lockerBasicInformation")
@Controller
@CrossOrigin
//@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:8080", "http://api.danielvt.xyz", "http://express.danielvt.xyz"}, allowedHeaders = "*", allowCredentials = "true")
public class LockerBasicInformationController {
    private final Logger logger = LoggerFactory.getLogger(LockerBasicInformationService.class);
    @Resource
    private LockerBasicInformationService lockerBasicInformationService;
    @Resource
    private LockerBoxInformationService lockerBoxInformationService;

    @PostMapping("addLocker")
    @ResponseBody
    public CommonResult<Object> AddLocker(@RequestBody Map<String, String> map) {
        LockerBasicInformation record = new LockerBasicInformation();
        String province = map.get("province");
        String city = map.get("city");
        String lockerName = map.get("lockerName");
        int row_num = Integer.parseInt(map.get("row"));
        int column_num = Integer.parseInt(map.get("column"));
        int total_box = Integer.parseInt(map.get("totalBox"));
        if (lockerBasicInformationService.findLockerByParam(province, city, lockerName, total_box) != null) {
            return CommonResult.failed("已有重复快递柜存在！");
        }
        record.setProvince(province);
        record.setCity(city);
        record.setLockerName(lockerName);
        record.setRowNum(row_num);
        record.setColumnNum(column_num);
        record.setTotalBox(total_box);
        record.setUsedBox(0);
        record.setCreateTime(new Date(System.currentTimeMillis()));
        logger.info("添加Locker ==>" + record);
        int result = lockerBasicInformationService.insertSelective(record);
        if (result != 1) {
            throw new APIException(500, "LBI记录插入异常！");
        }
        return CommonResult.success(lockerBasicInformationService.findLockerByParam(province, city, lockerName, total_box));
    }

    @GetMapping("findLockerByParam")
    @ResponseBody
    public CommonResult<Object> findLockerByParam(
            @RequestParam(name = "province") String province,
            @RequestParam(name = "city") String city,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "total_box") String total_box) {
        int totalBox = 0;
        try {
            totalBox = Integer.parseInt(total_box);
        } catch (NumberFormatException ignored) {

        }
        return CommonResult.success(lockerBasicInformationService.findLockerByParam(province, city, name, totalBox));
    }

    @DeleteMapping("removeLocker/{id}")
    @ResponseBody
    public CommonResult<Object> RemoveLocker(@PathVariable(name = "id") String id) {
        logger.info("删除Locker ==>" + id);
        LockerBasicInformation lockerBasicInformation = lockerBasicInformationService.selectByPrimaryKey(Long.valueOf(id));
        if (lockerBasicInformation.getUsedBox() != 0) {
            return CommonResult.failed();
        }
        int result = lockerBoxInformationService.deleteByLockerId(Long.valueOf(id));

        if (result > lockerBasicInformation.getTotalBox()) {
            throw new APIException(500, "LBI记录删除异常！");
        }
        result = lockerBasicInformationService.deleteByPrimaryKey(Long.valueOf(id));
        if (result != 1) {
            throw new APIException(500, "LBI记录删除异常！");
        } else {
            return CommonResult.success();
        }

    }

    @PutMapping("editLocker")
    @ResponseBody
    public String EditLocker(@RequestBody LockerBasicInformation record) {
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

    @GetMapping("list")
    @ResponseBody
    public CommonResult<PageResult<LockerBasicInformation>> queryLocker(
            @RequestParam(name = "query", required = false) String query,
            @RequestParam(name = "pagenum", defaultValue = "1") Integer pageNum,
            @RequestParam(name = "pagesize", defaultValue = "5") Integer pageSize) {
        long id;
        if (pageNum <= 0 || pageSize <= 0) {
            return CommonResult.failed("参数有误！");
        }
        if (query != null && query.length() == 18) {
            try {
                id = Long.parseLong(query);
                List<LockerBasicInformation> list = new ArrayList<>();
                LockerBasicInformation temp = lockerBasicInformationService.selectByPrimaryKey(id);
                if (temp.getLockerId() == null) {
                    return CommonResult.success(new PageResult<>(1L, 0, null));
                }
                list.add(temp);
                PageResult<LockerBasicInformation> pageResult = new PageResult<>(1L, 1, list);
                return CommonResult.success(pageResult);
            } catch (NumberFormatException ignored) {

            }

        }
        PageResult<LockerBasicInformation> pageResult = this.lockerBasicInformationService.findLockerByPage(query, pageNum, pageSize);
        return CommonResult.success(pageResult);
    }


}
