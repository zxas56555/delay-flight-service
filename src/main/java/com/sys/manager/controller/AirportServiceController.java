package com.sys.manager.controller;

import com.sys.manager.entity.AirportService;
import com.sys.manager.domain.R;
import com.sys.manager.service.AirportServiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 接机/送机服务表(AirportService)表控制层
 *
 * @author makejava
 * @since 2024-07-23 11:48:21
 */
@RestController
@RequestMapping("/api/airport")
@Api(tags = "接机/送机服务接口")
public class AirportServiceController {

    /**
     * 服务对象
     */
    @Autowired
    private AirportServiceService airportServiceService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param airportService 查询实体
     * @return 所有数据
     */
    @GetMapping("/query")
    @ApiOperation(value = "查询服务", httpMethod = "GET")
    public R<?> selectAll(AirportService airportService,
                          @RequestParam(required = false, defaultValue = "1") Integer page,
                          @RequestParam(required = false, defaultValue = "10") Integer size) {
        return airportServiceService.selectAll(airportService, page, size);
    }

    /**
     * 新增数据
     *
     * @param airportService 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveAirport")
    @ApiOperation(value = "新增服务", httpMethod = "POST")
    public R<?> saveAirport(@RequestBody AirportService airportService) {
        return airportServiceService.saveAirport(airportService);
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除结果
     */
    @PostMapping("/delAirport")
    @ApiOperation(value = "删除服务", httpMethod = "POST")
    public R<?> delAirport(@RequestParam Integer id) {
        return airportServiceService.delAirport(id);
    }

}

