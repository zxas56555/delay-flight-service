package com.sys.manager.controller;

import com.sys.manager.domain.R;
import com.sys.manager.entity.AirportShuttle;
import com.sys.manager.entity.ShuttleInfo;
import com.sys.manager.service.ShuttleInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shuttle")
@Api(tags = "摆渡车信息接口")
public class ShuttleInfoController {

    @Autowired
    private ShuttleInfoService shuttleInfoService;

    @GetMapping("/selectShuttle")
    @ApiOperation(value = "查询摆渡车信息", httpMethod = "GET")
    public R<?> selectShuttle(ShuttleInfo shuttleInfo,
                              @RequestParam(required = false, defaultValue = "1") Integer page,
                              @RequestParam(required = false, defaultValue = "10") Integer size) {
        return shuttleInfoService.selectShuttle(shuttleInfo, page, size);
    }

    @GetMapping("/selectDict")
    @ApiOperation(value = "查询摆渡车字典信息", httpMethod = "GET")
    public R<?> selectDict(@RequestParam(required = false, defaultValue = "") String name) {
        return shuttleInfoService.selectDict(name);
    }

    @PostMapping("/saveShuttle")
    @ApiOperation(value = "修改/新增摆渡车信息", httpMethod = "POST")
    public R<?> saveShuttle(@RequestBody ShuttleInfo shuttleInfo) {
        return shuttleInfoService.saveShuttle(shuttleInfo);
    }

    @PostMapping("/delShuttle")
    @ApiOperation(value = "删除摆渡车信息", httpMethod = "POST")
    public R<?> delShuttle(@RequestParam Integer id) {
        return shuttleInfoService.delShuttle(id);
    }

    @PostMapping("/isGo")
    @ApiOperation(value = "发车/到站", httpMethod = "POST")
    public R<?> isGo(@RequestBody AirportShuttle airShuttle) {
        return shuttleInfoService.isGo(airShuttle);
    }

    @GetMapping("/airShuttleInfo")
    @ApiOperation(value = "扫码页面信息", httpMethod = "GET")
    public R<?> airShuttleInfo(@RequestParam Integer airShuttleId) {
        return shuttleInfoService.airShuttleInfo(airShuttleId);
    }

}
