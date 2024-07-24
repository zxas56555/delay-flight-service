package com.sys.manager.controller;

import com.sys.manager.domain.R;
import com.sys.manager.entity.UnitInfo;
import com.sys.manager.service.UnitInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 单位(组织架构)管理
 *
 * @author qmy
 * @since 2023-01-31
 */
@RestController
@RequestMapping("/api/unitInfo")
@Api(tags = "单位管理接口")
public class UnitInfoController {

    @Autowired
    private UnitInfoService unitInfoService;

    @GetMapping("/getUnitInfo")
    @ApiOperation(value = "查询单位管理表", httpMethod = "GET")
    public R<?> findUnitInfo(UnitInfo unitInfo,
                             @RequestParam(required = false, defaultValue = "1") Integer page,
                             @RequestParam(required = false, defaultValue = "10") Integer size,
                             Date startTime,
                             Date endTime) {
        return unitInfoService.findUnitInfo(unitInfo, page, size, startTime, endTime);
    }

    @GetMapping("/getUnitInfoTree")
    @ApiOperation(value = "查询单位树", httpMethod = "GET")
    public R<?> findUnitInfoTree() {
        return unitInfoService.findUnitInfoTree();
    }

    @PostMapping("/saveUnit")
    @ApiOperation(value = "根据id新增或修改单位管理", httpMethod = "POST")
    public R<?> saveOrUpdateUnitInfo(@RequestBody UnitInfo unitInfo) {
        return unitInfoService.saveOrUpdateUnitInfo(unitInfo);
    }

    @PostMapping("/deleteUnit")
    @ApiOperation(value = "删除单位管理表表中信息", httpMethod = "POST")
    public R<?> deleteUnitInfo(@RequestParam Integer id) {
        return unitInfoService.deleteUnitInfo(id);
    }

//    @PostMapping("/importUnit")
//    @ApiOperation(value = "excel导入单位",httpMethod = "POST")
//    public R<?> importUnitFromExcel(@RequestPart MultipartFile file,
//                                    @RequestParam String tableYear){
//        return unitInfoService.importUnitFromExcel(file, tableYear);
//    }

    @GetMapping("/queryChildrenUnit")
    @ApiOperation(value = "查询一个单位的子级单位", httpMethod = "GET")
    public R<?> queryChildrenUnit(@RequestParam Integer unitId) {
        return unitInfoService.queryChildrenUnit(unitId);
    }

}
