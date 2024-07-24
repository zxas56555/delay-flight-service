package com.sys.manager.controller;

import com.sys.manager.domain.R;
import com.sys.manager.entity.HotelInfo;
import com.sys.manager.service.HotelInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotel")
@Api(tags = "酒店信息接口")
public class HotelInfoController {

    @Autowired
    private HotelInfoService hotelInfoService;

    @GetMapping("/selectHotel")
    @ApiOperation(value = "查询酒店信息", httpMethod = "GET")
    public R<?> selectHotel(HotelInfo hotelInfo,
                            @RequestParam(required = false, defaultValue = "1") Integer page,
                            @RequestParam(required = false, defaultValue = "10") Integer size) {
        return hotelInfoService.selectHotel(hotelInfo, page, size);
    }

    @GetMapping("/selectDict")
    @ApiOperation(value = "查询酒店字典信息", httpMethod = "GET")
    public R<?> selectDict(@RequestParam(required = false, defaultValue = "") String name) {
        return hotelInfoService.selectDict(name);
    }

    @PostMapping("/saveHotel")
    @ApiOperation(value = "修改/新增酒店信息", httpMethod = "POST")
    public R<?> saveHotel(@RequestBody HotelInfo hotelInfo) {
        return hotelInfoService.saveHotel(hotelInfo);
    }

    @PostMapping("/delHotel")
    @ApiOperation(value = "删除酒店信息", httpMethod = "POST")
    public R<?> delHotel(@RequestParam Integer id) {
        return hotelInfoService.delHotel(id);
    }
    
}
