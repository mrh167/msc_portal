package com.msc.fix.lisa.domain.gateway.system;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.msc.fix.lisa.domain.entity.system.SysDictType;
import com.msc.fix.lisa.dto.system.SysDictTypeAddCmd;
import com.msc.fix.lisa.dto.system.SysDictTypeEditCmd;
import com.msc.fix.lisa.dto.system.SysDictTypeQry;
import com.msc.fix.lisa.dto.system.UpdateStatusDataTypeCmd;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/30
 * Time: 11:29
 * Description: No Description
 */
public interface SysDictTypeGateway extends IService<SysDictType> {
    PageInfo<SysDictType> pageList(SysDictTypeQry pageQry);

    SingleResponse add(SysDictTypeAddCmd cmd);

    SingleResponse updateStatusDictData(UpdateStatusDataTypeCmd statusCmd, List<Long> ids);

    SingleResponse edit(SysDictTypeEditCmd cmd);
}
