package com.msc.fix.lisa.controller.system;

import com.alibaba.cola.dto.SingleResponse;
import com.msc.fix.lisa.api.system.SysDictTypeService;
import com.msc.fix.lisa.base.AbstractController;
import com.msc.fix.lisa.base.PageResponse;
import com.msc.fix.lisa.dto.system.*;
import com.msc.fix.lisa.dto.system.cto.SysDictTypeCo;
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
 * Date: 2021/9/30
 * Time: 9:51
 * Description: No Description
 */

@Api(tags = "数据字典")
@RestController
@RequestMapping("/api")
public class SysDictTypeController extends AbstractController {

    @Autowired
    private SysDictTypeService sysDictTypeService;


    @ApiOperation("分页查询")
    @PostMapping("/sysDictType/pageList")
    public PageResponse<SysDictTypeCo> pageList(@RequestBody SysDictTypeQry pageQry){
        return sysDictTypeService.pageList(pageQry);
    }

    @ApiOperation("类型增加")
    @PostMapping("/sysDictType/add")
    public SingleResponse add(@RequestBody SysDictTypeAddCmd sysDictTypeAddCmd){
        sysDictTypeAddCmd.initCreate(getPin());
        return sysDictTypeService.add(sysDictTypeAddCmd);
    }

    @ApiOperation("手工删除")
    @PostMapping("/sysDictType/deleteBatch")
    public SingleResponse deleteBatch(@RequestBody SysDictTypeDeleteCmd sysDictTypeDeleteCmd){
        sysDictTypeDeleteCmd.initUpdate(getPin());
        return sysDictTypeService.deleteBatch(sysDictTypeDeleteCmd);
    }

    @ApiOperation("类型状态修改")
    @PostMapping("sysDictType/updateStatus")
    public SingleResponse updateStatus(@RequestBody UpdateStatusDataTypeCmd dataTypeCmd){
        return sysDictTypeService.updateStatus(dataTypeCmd);
    }

    @ApiOperation("字典类型编辑")
    @PostMapping("/sysDictType/edit")
    public SingleResponse edit(@RequestBody SysDictTypeEditCmd sysDictTypeEdit){
        sysDictTypeEdit.initUpdate(getPin());
        return sysDictTypeService.edit(sysDictTypeEdit);
    }




}
