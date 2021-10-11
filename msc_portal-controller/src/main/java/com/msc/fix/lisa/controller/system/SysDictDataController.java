package com.msc.fix.lisa.controller.system;

import com.alibaba.cola.dto.SingleResponse;
import com.msc.fix.lisa.api.system.SysDictDataService;
import com.msc.fix.lisa.base.PageResponse;
import com.msc.fix.lisa.dto.system.SysDictDataQry;
import com.msc.fix.lisa.dto.system.cto.SysDictDataCo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/29
 * Time: 15:22
 * Description: No Description
 */
@Api(tags = "数据字典")
@RestController
@RequestMapping("/api")
public class SysDictDataController {


    @Autowired
    private SysDictDataService sysDictDataService;


    @ApiOperation("分页查询")
    @PostMapping("/sysDictData/pageList")
    public PageResponse<SysDictDataCo> pageList(@RequestBody SysDictDataQry sysDictDataQry){
        return sysDictDataService.pageList(sysDictDataQry);
    }
}