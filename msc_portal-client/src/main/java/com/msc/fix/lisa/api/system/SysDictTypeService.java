package com.msc.fix.lisa.api.system;

import com.alibaba.cola.dto.SingleResponse;
import com.msc.fix.lisa.base.PageResponse;
import com.msc.fix.lisa.dto.system.SysDictTypeAddCmd;
import com.msc.fix.lisa.dto.system.SysDictTypeDeleteCmd;
import com.msc.fix.lisa.dto.system.SysDictTypeQry;
import com.msc.fix.lisa.dto.system.cto.SysDictTypeCo;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/30
 * Time: 11:24
 * Description: No Description
 */
public interface SysDictTypeService {
    PageResponse<SysDictTypeCo> pageList(SysDictTypeQry pageQry);

    SingleResponse add(SysDictTypeAddCmd sysDictTypeAddCmd);

    SingleResponse deleteBatch(SysDictTypeDeleteCmd sysDictTypeDeleteCmd);
}
