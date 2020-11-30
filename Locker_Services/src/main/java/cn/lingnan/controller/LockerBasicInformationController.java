package cn.lingnan.controller;


import cn.lingnan.dto.LockerBasicInformation;
import cn.lingnan.util.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("lockerbasicinformation")
@Controller
public class LockerBasicInformationController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public boolean AddLocker(@RequestBody @Validated LockerBasicInformation record){

        return false;
    }
}
