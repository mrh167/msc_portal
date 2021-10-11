package com.msc.fix.lisa.executor.query.system;

import com.alibaba.cola.command.Command;
import com.alibaba.cola.command.CommandExecutorI;
import com.github.pagehelper.PageInfo;
import com.msc.fix.lisa.base.PageResponse;
import com.msc.fix.lisa.domain.entity.system.SysDictData;
import com.msc.fix.lisa.domain.gateway.system.SysDictDataGateway;
import com.msc.fix.lisa.dto.system.SysDictDataQry;
import com.msc.fix.lisa.dto.system.cto.SysDictDataCo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/29
 * Time: 16:21
 * Description: No Description
 */
@Command
public class SysDictDataQryExe implements CommandExecutorI<PageResponse<SysDictDataCo>, SysDictDataQry> {

    @Autowired
    private SysDictDataGateway sysDictDataGateway;

    @Override
    public PageResponse<SysDictDataCo> execute(SysDictDataQry pageQry) {
        PageInfo<SysDictData> pageInfo = sysDictDataGateway.pageList(pageQry);
        return PageResponse.buildPageSuccess(pageInfo,SysDictDataCo.class);
    }
}
