package com.msc.fix.lisa.api;

import com.alibaba.cola.dto.SingleResponse;
import com.msc.fix.lisa.dto.system.cto.BookCo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/11/15
 * Time: 15:00
 * Description: No Description
 */
public interface MultiMatchService {
    SingleResponse<List<BookCo>> multiMatch(String indexQry);
}
