package com.msc.fix.lisa.executor.query.system;

import com.alibaba.cola.command.Command;
import com.alibaba.cola.command.CommandExecutorI;
import com.alibaba.cola.dto.SingleResponse;
import com.github.pagehelper.PageInfo;
import com.msc.fix.lisa.base.PageResponse;
import com.msc.fix.lisa.domain.entity.system.SysDictType;
import com.msc.fix.lisa.domain.gateway.system.SysDictTypeGateway;
import com.msc.fix.lisa.dto.system.SysDictTypeQry;
import com.msc.fix.lisa.dto.system.cto.SysDictTypeCo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/30
 * Time: 11:26
 * Description: No Description
 */
@Command
public class SysDictTypeQryExe implements CommandExecutorI<PageResponse<SysDictTypeCo>, SysDictTypeQry> {

    @Autowired
    private SysDictTypeGateway sysDictTypeGateway;

    @Override
    public PageResponse<SysDictTypeCo> execute(SysDictTypeQry pageQry) {
        PageInfo<SysDictType> pageInfo = sysDictTypeGateway.pageList(pageQry);

        return PageResponse.buildPageSuccess(pageInfo,SysDictTypeCo.class);
    }
}
