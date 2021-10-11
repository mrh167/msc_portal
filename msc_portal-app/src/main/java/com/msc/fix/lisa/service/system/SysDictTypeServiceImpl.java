package com.msc.fix.lisa.service.system;

import com.alibaba.cola.command.CommandBusI;
import com.alibaba.cola.dto.SingleResponse;
import com.msc.fix.lisa.api.system.SysDictTypeService;
import com.msc.fix.lisa.base.PageResponse;
import com.msc.fix.lisa.dto.system.SysDictTypeAddCmd;
import com.msc.fix.lisa.dto.system.SysDictTypeDeleteCmd;
import com.msc.fix.lisa.dto.system.SysDictTypeQry;
import com.msc.fix.lisa.dto.system.cto.SysDictTypeCo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/30
 * Time: 11:25
 * Description: No Description
 */
@Service
public class SysDictTypeServiceImpl implements SysDictTypeService {

    @Autowired
    private CommandBusI commandBusI;

    @Override
    public PageResponse<SysDictTypeCo> pageList(SysDictTypeQry pageQry) {
        return (PageResponse<SysDictTypeCo>) commandBusI.send(pageQry);
    }

    @Override
    public SingleResponse add(SysDictTypeAddCmd sysDictTypeAddCmd) {
        return (SingleResponse) commandBusI.send(sysDictTypeAddCmd);
    }

    @Override
    public SingleResponse deleteBatch(SysDictTypeDeleteCmd sysDictTypeDeleteCmd) {
        return (SingleResponse) commandBusI.send(sysDictTypeDeleteCmd);
    }
}
