package com.msc.fix.lisa.service.system;

import com.alibaba.cola.command.CommandBusI;
import com.alibaba.cola.dto.SingleResponse;
import com.msc.fix.lisa.api.system.SysDictDataService;
import com.msc.fix.lisa.base.PageResponse;
import com.msc.fix.lisa.dto.system.SysDictDataQry;
import com.msc.fix.lisa.dto.system.UpdateStatusDataTypeCmd;
import com.msc.fix.lisa.dto.system.cto.SysDictDataCo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/29
 * Time: 16:20
 * Description: No Description
 */
@Service
public class SysDictDataServiceImpl implements SysDictDataService {

    @Autowired
    private CommandBusI commandBusI;

    @Override
    public PageResponse<SysDictDataCo> pageList(SysDictDataQry sysDictDataQry) {
        return (PageResponse<SysDictDataCo>) commandBusI.send(sysDictDataQry);
    }

    @Override
    public SingleResponse updateStatus(UpdateStatusDataTypeCmd statusDataTypeCmd) {
        return (SingleResponse) commandBusI.send(statusDataTypeCmd);
    }
}
