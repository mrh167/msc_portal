package com.msc.fix.lisa.executor.system;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.cola.command.Command;
import com.alibaba.cola.command.CommandExecutorI;
import com.alibaba.cola.dto.SingleResponse;
import com.msc.fix.lisa.domain.gateway.system.SysDictTypeGateway;
import com.msc.fix.lisa.dto.system.SysDictTypeEditCmd;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/10/18
 * Time: 19:25
 * Description: No Description
 */
@Command
public class SysDictTypeEditCmdExe implements CommandExecutorI<SingleResponse, SysDictTypeEditCmd> {

    @Autowired
    private SysDictTypeGateway sysDictTypeGateway;

    @Override
    public SingleResponse execute(SysDictTypeEditCmd cmd) {
        if (ObjectUtil.isNull(cmd)){
            return null;
        }
        return sysDictTypeGateway.edit(cmd);
    }
}
