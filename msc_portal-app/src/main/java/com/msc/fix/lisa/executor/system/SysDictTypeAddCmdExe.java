package com.msc.fix.lisa.executor.system;

import com.alibaba.cola.command.Command;
import com.alibaba.cola.command.CommandExecutorI;
import com.alibaba.cola.dto.SingleResponse;
import com.msc.fix.lisa.domain.gateway.system.SysDictTypeGateway;
import com.msc.fix.lisa.dto.system.SysDictTypeAddCmd;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/30
 * Time: 13:52
 * Description: No Description
 */
@Command
public class SysDictTypeAddCmdExe implements CommandExecutorI<SingleResponse, SysDictTypeAddCmd> {


    @Autowired
    private SysDictTypeGateway sysDictTypeGateway;


    @Override
    public SingleResponse execute(SysDictTypeAddCmd cmd) {

        return sysDictTypeGateway.add(cmd);
    }
}
