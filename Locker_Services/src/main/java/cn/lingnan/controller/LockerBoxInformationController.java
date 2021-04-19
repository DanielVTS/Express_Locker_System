package cn.lingnan.controller;

import cn.lingnan.dto.LockerBoxInformation;
import cn.lingnan.exception.APIException;
import cn.lingnan.service.LockerBoxInformationService;
import cn.lingnan.util.CommonResult;
import cn.lingnan.util.PageResult;
import cn.lingnan.util.UUIDCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequestMapping("lockerBoxInformation")
@Controller
@CrossOrigin
//@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:8080", "http://api.danielvt.xyz", "http://express.danielvt.xyz"}, allowedHeaders = "*", allowCredentials = "true")
public class LockerBoxInformationController {
    private final Logger logger = LoggerFactory.getLogger(LockerBoxInformationService.class);
    @Resource
    private LockerBoxInformationService lockerBoxInformationService;

    @Deprecated
    @PostMapping("addLockerBoxRow")
    @ResponseBody
    public CommonResult<Object> AddLockerBoxRow(@RequestBody Map<String, String> map) {
        try {
            Long lockerId = Long.valueOf(map.get("lockerId"));
            int row = Integer.parseInt(map.get("row"));
            int column = Integer.parseInt(map.get("column"));
            int boxType = Integer.parseInt(map.get("boxType"));
            List<LockerBoxInformation> list = lockerBoxInformationService.findBoxListInOneLocker(lockerId);
            for (LockerBoxInformation t : list) {
                if (t.getLockerRow() == row) {
                    throw new APIException("此快递柜该行已有数据");
                }
            }
            LockerBoxInformation a = new LockerBoxInformation();
            Date date = new Date(System.currentTimeMillis());
            a.setLockerId(lockerId);
            a.setLockerRow(row);
            a.setBoxType(boxType);
            a.setDoorStatus(1);
            a.setBoxIsEmpty(1);
            a.setStatus(1);
            a.setCreateTime(date);
            a.setUpdateTime(date);
            a.setStatusTime(date);
            for (int i = 1; i <= column; i++) {
                a.setLockerColumn(i);
                if (lockerBoxInformationService.insert(a) != 1) {
                    throw new APIException("Box记录插入异常");
                }
            }
        } catch (NullPointerException e) {
            return CommonResult.failed("参数缺失！");
        } catch (NumberFormatException e) {
            return CommonResult.failed("参数有误！");
        } catch (APIException e) {
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success();
    }

    @PostMapping("autoAddLockerBox")
    @ResponseBody
    public CommonResult<Object> AddLockerBoxColumn(@RequestBody Map<String, String> map) {
        try {
            Long lockerId = Long.valueOf(map.get("id"));
            int row = Integer.parseInt(map.get("row"));
            int column = Integer.parseInt(map.get("column"));
            int big = Integer.parseInt(map.get("big"));
            int normal = Integer.parseInt(map.get("normal"));
            int small = Integer.parseInt(map.get("small"));
            int terminalPosition = Integer.parseInt(map.get("terminalPosition"));
            if (big + normal + small != row) {
                throw new APIException("传入参数有误！");
            }
            int height = big * 4 + normal * 2 + small;
            List<LockerBoxInformation> list = lockerBoxInformationService.findBoxListInOneLocker(lockerId);
            for (LockerBoxInformation t : list) {
                if (t.getLockerColumn() == column) {
                    throw new APIException("此快递柜该列已有数据");
                }
            }
            LockerBoxInformation a = new LockerBoxInformation();
            Date date = new Date(System.currentTimeMillis());
            a.setLockerId(lockerId);
            a.setLockerColumn(column);
            a.setDoorStatus(1);
            a.setBoxIsEmpty(1);
            a.setStatus(1);
            a.setCreateTime(date);
            a.setUpdateTime(date);
            a.setStatusTime(date);
            for (int x = 1; x <= column; x++) {
                a.setLockerColumn(x);
                if (x == terminalPosition) {
                    for (int y = 1; y <= height - 8; y++) {
                        a.setLockerRow(y);
                        a.setBoxType(3);
                        if (lockerBoxInformationService.insertSelective(a) != 1) {
                            throw new APIException("Box记录插入异常");
                        }

                    }
                } else {
                    for (int y = 1; y <= row; ) {
                        for (int i = 1; i <= big; i++) {
                            a.setLockerRow(y);
                            a.setBoxType(1);
                            if (lockerBoxInformationService.insertSelective(a) != 1) {
                                throw new APIException("Box记录插入异常");
                            }
                            y++;
                        }
                        for (int i = 1; i <= normal; i++) {
                            a.setLockerRow(y);
                            a.setBoxType(2);
                            if (lockerBoxInformationService.insertSelective(a) != 1) {
                                throw new APIException("Box记录插入异常");
                            }
                            y++;
                        }
                        for (int i = 1; i <= small; i++) {
                            a.setLockerRow(y);
                            a.setBoxType(3);
                            if (lockerBoxInformationService.insertSelective(a) != 1) {
                                throw new APIException("Box记录插入异常");
                            }
                            y++;
                        }
                    }
                }
            }

        } catch (NullPointerException e) {
            return CommonResult.failed("参数缺失！");
        } catch (NumberFormatException e) {
            return CommonResult.failed("参数有误！");
        } catch (APIException e) {
            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.success();
    }


    @Deprecated
    @PostMapping("addLockerBox")
    @ResponseBody
    public String AddLockerBox(@RequestBody LockerBoxInformation record) {
        logger.info("添加LockerBox ==>" + record.toString());
        record.setDoorStatus(1);
        record.setBoxIsEmpty(1);
        Date date = new Date(System.currentTimeMillis());
        record.setCreateTime(date);
        record.setUpdateTime(date);
        record.setStatusTime(date);
        int result = lockerBoxInformationService.insertSelective(record);
        if (result != 1) {
            throw new APIException(500, "Box记录插入异常！");
        }
        return "Box记录插入成功！";
    }

    @DeleteMapping("removeLockerBox/{id}")
    @ResponseBody
    public CommonResult<Object> RemoveLockerBox(@PathVariable(name = "id") String id) {
        logger.info("删除Locker ==>" + id);
        int result = lockerBoxInformationService.deleteByPrimaryKey(id);
        if (result != 1) {
            throw new APIException(500, "Box记录删除异常！");
        }
        return CommonResult.success();
    }

    @PutMapping("editLockerBox")
    @ResponseBody
    public CommonResult<Object> EditLockerBox(@RequestBody LockerBoxInformation record) {
        if (record.getLockerBoxId() == null) {
            return CommonResult.failed("Box记录id空！");
        }
        logger.info("修改Locker ==>" + record);
        int result = lockerBoxInformationService.updateByPrimaryKey(record);
        if (result != 1) {
            throw new APIException(500, "Box记录修改异常！");
        }
        return CommonResult.success();
    }


    /**
     * @param query    Locker ID精确搜索，为空则列出全表
     * @param pagenum
     * @param pagesize
     * @return
     */
    @GetMapping("listBox")
    @ResponseBody
    public CommonResult<PageResult<LockerBoxInformation>> FindBoxList(
            @RequestParam(name = "query", required = false) String query,
            @RequestParam(name = "pagenum", defaultValue = "1") Integer pagenum,
            @RequestParam(name = "pagesize", defaultValue = "5") Integer pagesize) {
        if (query == null || !UUIDCheck.isValidUUID(query)) {
            return CommonResult.success(lockerBoxInformationService.findBoxByPage(query, pagenum, pagesize));
        } else {
            List<LockerBoxInformation> list = new ArrayList<>();
            LockerBoxInformation b = lockerBoxInformationService.selectByPrimaryKey(query);
            if (b.getLockerBoxId() == null) {
                return CommonResult.success(new PageResult<>(1L, 0, null));
            } else {
                list.add(b);
                return CommonResult.success(new PageResult<>(1L, 1, list));
            }

        }

    }

    @GetMapping("findBoxForPost")
    @ResponseBody
    public CommonResult<Object> findBoxForPost(
            @RequestParam(name = "lockerId", defaultValue = "1") Long lockerId,
            @RequestParam(name = "boxType", defaultValue = "5") Integer boxType) {
        LockerBoxInformation a = lockerBoxInformationService.findBoxForPost(lockerId, boxType);
        if (a.getLockerBoxId() == null) {
            return CommonResult.failed();
        } else return CommonResult.success(a);
    }
}
