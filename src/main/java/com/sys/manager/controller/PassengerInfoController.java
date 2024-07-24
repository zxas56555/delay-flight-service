package com.sys.manager.controller;

import com.sys.manager.domain.R;
import com.sys.manager.entity.PassengerInfo;
import com.sys.manager.service.PassengerInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/passenger")
@Api(tags = "旅客信息接口")
public class PassengerInfoController {

    @Autowired
    private PassengerInfoService passengerInfoService;

    @GetMapping("/selectPassenger")
    @ApiOperation(value = "查询旅客信息", httpMethod = "GET")
    public R<?> selectPassenger(PassengerInfo passenger,
                                @RequestParam(required = false, defaultValue = "1") Integer page,
                                @RequestParam(required = false, defaultValue = "10") Integer size) {
        return passengerInfoService.selectPassenger(passenger, page, size);
    }

    @PostMapping("/import")
    @ApiOperation(value = "导入旅客信息", httpMethod = "POST")
    public R<?> importPassenger(@RequestPart MultipartFile file) throws IOException {
        return passengerInfoService.importPassenger(file);
    }

}
