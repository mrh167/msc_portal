package com.msc.fix.lisa.executor.system;

import com.alibaba.cola.command.Command;
import com.alibaba.cola.command.CommandExecutorI;
import com.alibaba.cola.dto.SingleResponse;
import com.msc.fix.lisa.domain.gateway.system.SysUserGateWay;
import com.msc.fix.lisa.dto.system.AddUserCmd;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/9
 * Time: 18:43
 * Description: No Description
 */
@Command
public class AddUserCmdExe implements CommandExecutorI<SingleResponse, AddUserCmd> {

    @Autowired
    private SysUserGateWay sysUserGateWay;

    @Override
    public SingleResponse execute(AddUserCmd cmd) {
        return sysUserGateWay.addUser(cmd);
    }
}
