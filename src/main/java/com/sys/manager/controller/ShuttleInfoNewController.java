package com.sys.manager.controller;

import com.sys.manager.domain.R;
import com.sys.manager.entity.AirportShuttle;
import com.sys.manager.entity.ShuttleInfoNew;
import com.sys.manager.service.ShuttleInfoNewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/newShuttle")
@Api(tags = "新摆渡车信息接口")
public class ShuttleInfoNewController {

    @Autowired
    private ShuttleInfoNewService shuttleInfoNewService;

    @GetMapping("/selectShuttleNew")
    @ApiOperation(value = "新查询摆渡车信息", httpMethod = "GET")
    public R<?> selectShuttleNew(ShuttleInfoNew shuttleInfoNew,
                                 @RequestParam(required = false, defaultValue = "1") Integer page,
                                 @RequestParam(required = false, defaultValue = "10") Integer size) {
        return shuttleInfoNewService.selecetShuttleNew(shuttleInfoNew, page, size);
    }

    @GetMapping("/selectDictNew")
    @ApiOperation(value = "新查询摆渡车字典信息", httpMethod = "GET")
    public R<?> selctDictNew(@RequestParam(required = false, defaultValue = "") String name) {
        return shuttleInfoNewService.selectDictNew(name);
    }

    @PostMapping("/saveSuttleNew")
    @ApiOperation(value = "新修改/新增摆渡车信息", httpMethod = "POST")
    public R<?> saveSuttleNew(@RequestBody ShuttleInfoNew shuttleInfoNew) {
        return shuttleInfoNewService.saveShuttleNew(shuttleInfoNew);
    }

    @PostMapping("/delSuttleNew")
    @ApiOperation(value = "删除摆渡车信息", httpMethod = "POST")
    public R<?> delSuttleNew(@RequestBody Integer id) {
        return shuttleInfoNewService.delShuttleNew(id);
    }

    @PostMapping("/isGoNew")
    @ApiOperation(value = "新发车/到站", httpMethod = "POST")
    public R<?> isGoNew(@RequestBody AirportShuttle airShuttle) {
        return shuttleInfoNewService.isGoNew(airShuttle);
    }

    @GetMapping("/airShuttleInfo")
    @ApiOperation(value = "扫码页面信息", httpMethod = "GET")
    public R<?> airShuttleInfo(@RequestParam Integer airShuttleId) {
        return shuttleInfoNewService.airShuttleInfoNew(airShuttleId);
    }

}
