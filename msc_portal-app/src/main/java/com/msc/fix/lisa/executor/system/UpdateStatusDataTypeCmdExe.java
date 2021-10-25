package com.msc.fix.lisa.executor.system;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.cola.command.Command;
import com.alibaba.cola.command.CommandExecutorI;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.msc.fix.lisa.common.BusinessException;
import com.msc.fix.lisa.domain.entity.system.SysDictData;
import com.msc.fix.lisa.domain.gateway.system.SysDictDataGateway;
import com.msc.fix.lisa.domain.gateway.system.SysDictTypeGateway;
import com.msc.fix.lisa.dto.system.UpdateStatusDataTypeCmd;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/10/11
 * Time: 10:14
 * Description: No Description
 */
@Command
public class UpdateStatusDataTypeCmdExe implements CommandExecutorI<SingleResponse, UpdateStatusDataTypeCmd> {


    @Autowired
    private SysDictTypeGateway sysDictTypeGateway;

    @Autowired
    private SysDictDataGateway sysDictDataGateway;

    @Override
    public SingleResponse execute(UpdateStatusDataTypeCmd statusCmd) {
        if (ObjectUtil.isNull(statusCmd)){
            throw new BusinessException("参数为空!!!");
        }
        LambdaQueryWrapper<SysDictData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDictData::getTypeId,statusCmd.getId());
        List<SysDictData> li = sysDictDataGateway.list(wrapper);
        List<Long> ids = li.stream().map(SysDictData::getId).collect(Collectors.toList());
        return sysDictTypeGateway.updateStatusDictData(statusCmd,ids);
    }
}
