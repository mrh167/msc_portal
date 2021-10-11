package com.msc.fix.lisa.api.system;

import com.alibaba.cola.dto.SingleResponse;
import com.msc.fix.lisa.base.PageResponse;
import com.msc.fix.lisa.dto.system.SysDictDataQry;
import com.msc.fix.lisa.dto.system.cto.SysDictDataCo;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/29
 * Time: 16:18
 * Description: No Description
 */
public interface SysDictDataService {

    PageResponse<SysDictDataCo> pageList(SysDictDataQry sysDictDataQry);
}
