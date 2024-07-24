package com.sys.manager.controller;

import com.sys.manager.domain.R;
import com.sys.manager.entity.FlightInfo;
import com.sys.manager.service.FlightInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flight")
@Api(tags = "航班信息接口")
public class FlightInfoController {

    @Autowired
    private FlightInfoService flightInfoService;

    @GetMapping("/selectFlight")
    @ApiOperation(value = "查询航班信息", httpMethod = "GET")
    public R<?> selectFlight(FlightInfo flightInfo,
                             @RequestParam(required = false, defaultValue = "1") Integer page,
                             @RequestParam(required = false, defaultValue = "10") Integer size) {
        return flightInfoService.selectFlight(flightInfo, page, size);
    }

    @GetMapping("/selectDict")
    @ApiOperation(value = "查询航班字典信息", httpMethod = "GET")
    public R<?> selectDict(@RequestParam(required = false, defaultValue = "") String name) {
        return flightInfoService.selectDict(name);
    }

    @PostMapping("/saveFlight")
    @ApiOperation(value = "修改/新增航班信息", httpMethod = "POST")
    public R<?> saveFlight(@RequestBody FlightInfo flightInfo) {
        return flightInfoService.saveFlight(flightInfo);
    }

    @PostMapping("/delFlight")
    @ApiOperation(value = "删除航班信息", httpMethod = "POST")
    public R<?> delFlight(@RequestParam Integer id) {
        return flightInfoService.delFlight(id);
    }
    
}
