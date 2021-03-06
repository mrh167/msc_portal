package com.msc.fix.lisa.service.system;

import com.alibaba.cola.command.CommandBusI;
import com.alibaba.cola.dto.SingleResponse;
import com.msc.fix.lisa.api.system.SysUserService;
import com.msc.fix.lisa.base.PageResponse;
import com.msc.fix.lisa.dto.system.*;
import com.msc.fix.lisa.dto.system.cto.SysUserCo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/6
 * Time: 14:24
 * Description: No Description
 */
@Service
public class SysUserServiceImpl implements SysUserService {


    @Autowired
    private CommandBusI commandBusI;

    @Override
    public PageResponse<SysUserCo> pageList(SysUserQry sysUserQry) {
        return (PageResponse<SysUserCo>) commandBusI.send(sysUserQry);
    }

    @Override
    public SingleResponse updateStatus(UpdateStatusCmd updateStatusCmd) {
        return (SingleResponse) commandBusI.send(updateStatusCmd);
    }

    @Override
    public SingleResponse addUser(AddUserCmd addUserCmd) {
        return (SingleResponse) commandBusI.send(addUserCmd);
    }

    @Override
    public SingleResponse deleteIds(DeleteIdsCmd deleteIds) {
        return (SingleResponse) commandBusI.send(deleteIds);
    }

    @Override
    public SingleResponse<SysUserCo> editGetUser(UpdateUserCmd userCmd) {
        return (SingleResponse<SysUserCo>) commandBusI.send(userCmd);
    }

    @Override
    public SingleResponse editUser(UpdateUserCmd updateUserCmd) {
        return (SingleResponse) commandBusI.send(updateUserCmd);
    }
}
