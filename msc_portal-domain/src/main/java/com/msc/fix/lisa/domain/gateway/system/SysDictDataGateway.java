package com.msc.fix.lisa.domain.gateway.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.msc.fix.lisa.domain.entity.system.SysDictData;
import com.msc.fix.lisa.dto.system.SysDictDataQry;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/29
 * Time: 16:25
 * Description: No Description
 */
public interface SysDictDataGateway extends IService<SysDictData> {

    PageInfo<SysDictData> pageList(SysDictDataQry pageQry);
}
