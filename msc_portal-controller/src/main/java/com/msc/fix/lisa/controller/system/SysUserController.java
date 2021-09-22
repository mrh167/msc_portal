package com.msc.fix.lisa.controller.system;

import com.alibaba.cola.dto.SingleResponse;
import com.msc.fix.lisa.api.system.SysUserService;
import com.msc.fix.lisa.base.AbstractController;
import com.msc.fix.lisa.base.PageResponse;
import com.msc.fix.lisa.domain.gateway.system.SysUserGateWay;
import com.msc.fix.lisa.dto.system.AddUserCmd;
import com.msc.fix.lisa.dto.system.DeleteIdsCmd;
import com.msc.fix.lisa.dto.system.SysUserQry;
import com.msc.fix.lisa.dto.system.UpdateStatusCmd;
import com.msc.fix.lisa.dto.system.cto.SysUserCo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/6
 * Time: 14:13
 * Description: No Description
 */
@Api(tags = "用户管理页面",produces = "用户管理页面")
@RequestMapping("/api")
@RestController
@Slf4j
public class SysUserController extends AbstractController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserGateWay sysUserGateWay;


    @ApiOperation(value = "分页查询")
    @PostMapping(value = "/pageList")
    @PreAuthorize("hasAnyRole('ROLE_admin:pageList')")
    public PageResponse<SysUserCo> pageList(@RequestBody SysUserQry sysUserQry){
        return sysUserService.pageList(sysUserQry);
    }

    @ApiOperation(value = "修改账号所在的状态")
    @PostMapping(value = "/updateStatus")
    public SingleResponse updateStatus(@RequestBody UpdateStatusCmd updateStatusCmd) {
        return sysUserService.updateStatus(updateStatusCmd);
    }

    @ApiOperation("校验账号")
    @GetMapping(value = "/checkAccount")
    public SingleResponse checkAccount(String account){
        return sysUserGateWay.checkAccount(account);
    }

    @ApiOperation("添加用户")
    @PostMapping(value = "/addUser")
    public SingleResponse addUser(@RequestBody AddUserCmd addUserCmd){
        return sysUserService.addUser(addUserCmd);
    }


    @ApiOperation("删除用户")
    @PostMapping(value = "/deleteIds")
    public SingleResponse deleteIds(@RequestBody DeleteIdsCmd deleteIds){
        deleteIds.initUpdate(getPin());
        String pin = getPin();
        log.info("用户信息为=> {}",pin);
        return sysUserService.deleteIds(deleteIds);
    }
}
